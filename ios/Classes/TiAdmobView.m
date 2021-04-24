/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobView.h"
#import "TiAdmobTypes.h"
#import "TiApp.h"
#import "TiUtils.h"

@implementation TiAdmobView

#pragma mark - Ad Lifecycle

- (GADBannerView *)bannerView
{
  if (bannerView == nil) {
    // Create the view with dynamic width and height specification.
    bannerView = [[GADBannerView alloc] initWithAdSize:[self generateHeight]];

    // Set the delegate to receive the internal events
    [bannerView setDelegate:self];

    // Let the runtime know which UIViewController to restore after taking
    // the user wherever the ad goes and add it to the view hierarchy.
    [bannerView setRootViewController:[[TiApp app] controller]];

    // Add the view to the view hirarchie
    [self addSubview:[self bannerView]];
  }

  return bannerView;
}

- (void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
  id adType = [[self proxy] valueForKey:@"adType"];
  ENSURE_TYPE_OR_NIL(adType, NSNumber);

  if ([TiUtils intValue:adType def:TiAdmobAdTypeBanner] == TiAdmobAdTypeBanner) {
    [[self bannerView] setAdSize:GADAdSizeFromCGSize(bounds.size)];
  }

  [self initialize];
}

- (void)dealloc
{
  if (bannerView != nil) {
    [bannerView removeFromSuperview];
  }

  RELEASE_TO_NIL(bannerView);
  RELEASE_TO_NIL(interstitialAd);
  RELEASE_TO_NIL(rewardedAd);

  [super dealloc];
}

#pragma mark - Public API's

- (void)initialize
{
  ENSURE_UI_THREAD_0_ARGS
  id adType = [[self proxy] valueForKey:@"adType"];
  ENSURE_TYPE_OR_NIL(adType, NSNumber);
  
  id debugEnabled = [self.proxy valueForKey:@"debugEnabled"];
  adUnitId = [self.proxy valueForKey:@"adUnitId"];
  if (debugEnabled != nil && [TiUtils boolValue:debugEnabled def:NO]) {
    adUnitId = self.exampleAdId;
  }

  if ([TiUtils intValue:adType def:TiAdmobAdTypeBanner] == TiAdmobAdTypeBanner) {
    [self loadBanner];
  } else if ([TiUtils intValue:adType def:TiAdmobAdTypeBanner] == TiAdmobAdTypeInterstitial) {
    [self loadInterstitial];
  } else if ([TiUtils intValue:adType def:TiAdmobAdTypeBanner] == TiAdmobAdTypeRewardedVideo) {
    [self loadRewardedVideo];
  }
}

- (void)setAdUnitId_:(id)value
{
  ENSURE_TYPE(value, NSString);

  id adType = [[self proxy] valueForKey:@"adType"];
  id debugEnabled = [[self proxy] valueForKey:@"debugEnabled"];

  if (adType != nil && [TiUtils boolValue:adType def:TiAdmobAdTypeBanner] == TiAdmobAdTypeInterstitial) {
    return;
  }

  if (debugEnabled != nil && [TiUtils boolValue:debugEnabled] == YES) {
    value = [self exampleAdId];
  }

  [[self bannerView] setAdUnitID:[TiUtils stringValue:value]];
}

- (void)setKeywords_:(id)value
{
  if ([value isKindOfClass:[NSString class]]) {
    [[GADRequest request] setKeywords:@[ [TiUtils stringValue:value] ]];
    NSLog(@"[WARN] Ti.AdMob: The property `keywords` with string values is deprecated. Please use an array of string values instead.");
  } else if ([value isKindOfClass:[NSArray class]]) {
    [[GADRequest request] setKeywords:value];
  } else {
    NSLog(@"[ERROR] Ti.AdMob: The property `keywords` must be either a String or an Array.");
  }
}

- (void)setAdBackgroundColor_:(id)value
{
  id adType = [[self proxy] valueForKey:@"adType"];
  if (adType != nil && [TiUtils boolValue:adType def:TiAdmobAdTypeBanner] != TiAdmobAdTypeBanner) {
    return;
  }

  [[self bannerView] setBackgroundColor:[[TiUtils colorValue:value] _color]];
}

- (void)setTagForChildDirectedTreatment_:(id)value
{
  ENSURE_TYPE(value, NSNumber);
  [GADMobileAds.sharedInstance.requestConfiguration tagForChildDirectedTreatment:[TiUtils boolValue:value]];
}

- (void)setRequestAgent_:(id)value
{
  ENSURE_TYPE(value, NSString);
  [[GADRequest request] setRequestAgent:[TiUtils stringValue:value]];
}

- (void)setContentURL_:(id)value
{
  ENSURE_TYPE(value, NSString);

  if ([self validateUrl:value] == NO) {
    NSLog(@"[WARN] Ti.AdMob: The value of the property `contentURL` looks invalid.");
  }

  [[GADRequest request] setContentURL:[TiUtils stringValue:value]];
}

- (void)setExtras_:(id)args
{
  ENSURE_TYPE(args, NSDictionary);

  GADExtras *extras = [[GADExtras alloc] init];
  [extras setAdditionalParameters:args];
  [[GADRequest request] registerAdNetworkExtras:extras];

  RELEASE_TO_NIL(extras);
}

- (void)setLocation_:(id)args
{
  ENSURE_TYPE(args, NSDictionary);

  [[GADRequest request] setLocationWithLatitude:[[args valueForKey:@"latitude"] floatValue]
                                longitude:[[args valueForKey:@"longitude"] floatValue]
                                 accuracy:[[args valueForKey:@"accuracy"] floatValue]];
}

- (void)loadBanner
{
  [[self bannerView] loadRequest:[GADRequest request]];
}

- (void)loadRewardedVideo
{
  [GADRewardedAd loadWithAdUnitID:adUnitId request:[GADRequest request] completionHandler:^(GADRewardedAd * _Nullable rewardedAd, NSError * _Nullable error) {
    if (error) {
      [self.proxy fireEvent:@"adfailedtoload" withObject:@{ @"message": error.localizedDescription }];
      return;
    }
    
    rewardedAd = rewardedAd;
    rewardedAd.fullScreenContentDelegate = self;

    [self showRewardedVideo];
  }];
}

- (void)loadInterstitial
{
  [GADInterstitialAd loadWithAdUnitID:adUnitId
                               request:[GADRequest request]
                     completionHandler:^(GADInterstitialAd *ad, NSError *error) {
     if (error) {
       [self.proxy fireEvent:@"adfailedtoload" withObject:@{ @"message": error.localizedDescription }];
       return;
     }

     interstitialAd = ad;
     interstitialAd.fullScreenContentDelegate = self;

    [self showInterstitial];
   }];
}

- (void)showInterstitial
{
  NSError *error;
  BOOL canPresent = interstitialAd && [interstitialAd canPresentFromRootViewController:[[[TiApp app] controller] topPresentedController] error:&error];

  if (canPresent) {
    [interstitialAd presentFromRootViewController:[[[TiApp app] controller] topPresentedController]];
  } else {
    NSLog(@"[WARN] Cannot show interstitial: %@", error.localizedDescription);
  }
}

- (void)showRewardedVideo
{
  NSError *error;
  BOOL canPresent = rewardedAd && [rewardedAd canPresentFromRootViewController:[[[TiApp app] controller] topPresentedController] error:&error];

  if (canPresent) {
    [rewardedAd presentFromRootViewController:[[[TiApp app] controller] topPresentedController] userDidEarnRewardHandler:^{
      GADAdReward *reward = rewardedAd.adReward;

      // TODO: Which event to fire for best backwards compatibility
    }];
  } else {
    NSLog(@"[WARN] Cannot show rewarded video: %@", error.localizedDescription);
  }
}

#pragma mark - Deprecated / removed API's

- (void)setPublisherId_:(id)value
{
  NSLog(@"[ERROR] Ti.AdMob: The property `publisherId` has been removed in 2.0.0, use `adUnitId` instead.");
}

- (void)setTesting_:(id)value
{
  NSLog(@"[ERROR] Ti.AdMob: The property `testing` has been removed in 2.0.0, use `testDevices` instead.");
}

- (void)setGender_:(id)value
{
  DEPRECATED_REMOVED(@"Admob.gender", @"3.0.0", @"4.0.0 (removed by Google)")
}

- (void)setDateOfBirth_:(id)value
{
  DEPRECATED_REMOVED(@"Admob.dateOfBirth", @"3.0.0", @"4.0.0 (removed by Google)")
}

- (void)setTestDevices_:(id)value
{
  DEPRECATED_REMOVED(@"Admob.testDevices", @"4.0.0", @"4.0.0 (removed by Google)")
}

#pragma mark - Utilities

// http://stackoverflow.com/a/3819561/5537752
- (BOOL)validateUrl:(NSString *)candidate
{
  NSString *urlRegEx = @"(http|https)://((\\w)*|([0-9]*)|([-|_])*)+([\\.|/]((\\w)*|([0-9]*)|([-|_])*))+";
  NSPredicate *urlTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", urlRegEx];
  return [urlTest evaluateWithObject:candidate];
}

- (GADAdSize)generateHeight
{
  id height = [[self proxy] valueForKey:@"height"];

  if (height != nil) {
    return GADAdSizeFullWidthPortraitWithHeight([TiUtils floatValue:height]);
  }

  return kGADAdSizeFluid;
}

- (NSString *)exampleAdId
{
  return @"ca-app-pub-3940256099942544/1712485313";
}

#pragma mark - GADFullScreenContentDelegate

// NOTE: I tried to map them as best as possible, but for example "adDidRecordImpression" is new
// and "willPresentScreen" is not available anymore

- (void)adDidRecordImpression:(id<GADFullScreenPresentingAd>)ad
{
  [self.proxy fireEvent:@"didRecordImpression" withObject:@{ @"adUnitId": adUnitId }];
}

- (void)ad:(id<GADFullScreenPresentingAd>)ad didFailToPresentFullScreenContentWithError:(NSError *)error
{
  [self.proxy fireEvent:@"didFailToReceiveAd" withObject:@{ @"adUnitId" : adUnitId, @"error" : error.localizedDescription }];
}

- (void)adDidPresentFullScreenContent:(id<GADFullScreenPresentingAd>)ad
{
  [self.proxy fireEvent:@"didPresentScreen" withObject:@{ @"adUnitId": adUnitId }];
}

- (void)adWillDismissFullScreenContent:(id<GADFullScreenPresentingAd>)ad
{
  [self.proxy fireEvent:@"willDismissScreen" withObject:@{ @"adUnitId": adUnitId }];
}

- (void)adDidDismissFullScreenContent:(id<GADFullScreenPresentingAd>)ad
{
  [self.proxy fireEvent:@"didDismissScreen" withObject:@{ @"adUnitId": adUnitId }];
}

@end
