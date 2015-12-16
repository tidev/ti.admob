/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobView.h"
#import "TiApp.h"
#import "TiUtils.h"

@implementation TiAdmobView

#pragma mark - Ad Lifecycle

-(void)refreshAd:(CGRect)bounds
{
    if (ad != nil) {
        [ad removeFromSuperview];
        RELEASE_TO_NIL(ad);
    }
    
    ad = [[GADBannerView alloc] initWithFrame:bounds];
    
    // Initiate a generic request to load it with an ad.
    GADRequest* request = [GADRequest request];
    
    // Specify the ad's "unit identifier." This is your AdMob Publisher ID.
    [ad setAdUnitID:[self.proxy valueForKey:@"adUnitId"]];
    
    // Let the runtime know which UIViewController to restore after taking
    // the user wherever the ad goes and add it to the view hierarchy.
    [ad setRootViewController:[[TiApp app] controller]];
    
    // Go through the configurable properties, populating our request with their values (if they have been provided).
    [request setKeywords:[self.proxy valueForKey:@"keywords"]];
     [request setBirthday:[self.proxy valueForKey:@"dateOfBirth"]];
     [request setTestDevices:[self.proxy valueForKey:@"testDevices"]];
  
    NSString* backgroundColor = [self.proxy valueForKey:@"adBackgroundColor"];
    if (backgroundColor != nil) {
        [ad setBackgroundColor:[[TiUtils colorValue:backgroundColor] _color]];
    }
    
    NSDictionary* location = [self.proxy valueForKey:@"location"];
    if (location != nil) {
        [request setLocationWithLatitude:[[location valueForKey:@"latitude"] floatValue]
                               longitude:[[location valueForKey:@"longitude"] floatValue]
                                accuracy:[[location valueForKey:@"accuracy"] floatValue]];
    }
    
    NSString* gender = [self.proxy valueForKey:@"gender"];
    if ([gender isEqualToString:@"male"]) {
        [request setGender:kGADGenderMale];
    } else if ([gender isEqualToString:@"female"]) {
        [request setGender:kGADGenderFemale];
    } else {
        [request setGender:kGADGenderUnknown];
    }
    
    NSDictionary* extras = [self.proxy valueForKey:@"extras"];
    if (extras != nil) {
        GADExtras *extraInfos = [[GADExtras alloc] init];
        [extraInfos setAdditionalParameters:extras];
        [request registerAdNetworkExtras:extraInfos];
    }
    
    NSString *contentURL = [self.proxy valueForKey:@"contentURL"];
    if (contentURL != nil) {
        [request setContentURL:contentURL];
    }
    
    NSString *requestAgent = [self.proxy valueForKey:@"requestAgent"];
    if (requestAgent != nil) {
        [request setRequestAgent:requestAgent];
    }
    
    id tagForChildDirectedTreatment = [self.proxy valueForKey:@"tagForChildDirectedTreatment"];
    ENSURE_TYPE_OR_NIL(tagForChildDirectedTreatment, NSNumber);
    
    if (tagForChildDirectedTreatment != nil) {
        [request tagForChildDirectedTreatment:[TiUtils boolValue:tagForChildDirectedTreatment]];
    }
    
    [self addSubview:ad];
    
    [ad setDelegate:self];
    [ad loadRequest:request];
}

-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
    [self refreshAd:bounds];
}

-(void)dealloc
{
    if (ad != nil) {
        [ad removeFromSuperview];
        RELEASE_TO_NIL(ad);
    }
    [super dealloc];
}

#pragma mark - Deprecated / removed properties

-(void)setPublisherId:(id)value
{
    NSLog(@"[ERROR] Ti.Admob: The property `publisherId` has been removed. Use `adUnitId` instead.");
}

-(void)setTesting:(id)value
{
    NSLog(@"[ERROR] Ti.Admob: The property `testing` has been removed. Use `testDevices` instead");
}

#pragma mark - Ad Delegate

- (void)adViewDidReceiveAd:(GADBannerView *)view
{
    [self.proxy fireEvent:@"didReceiveAd"];
}

- (void)adView:(GADBannerView *)view didFailToReceiveAdWithError:(GADRequestError *)error
{
    [self.proxy fireEvent:@"didFailToReceiveAd"];
}

- (void)adViewWillPresentScreen:(GADBannerView *)adView
{
    [self.proxy fireEvent:@"willPresentScreen"];
}

- (void)adViewWillDismissScreen:(GADBannerView *)adView
{
    [self.proxy fireEvent:@"willDismissScreen"];
}

- (void)adViewDidDismissScreen:(GADBannerView *)adView
{
    [self.proxy fireEvent:@"didDismissScreen"];
}

- (void)adViewWillLeaveApplication:(GADBannerView *)adView
{
    [self.proxy fireEvent:@"willLeaveApplication"];
}


@end
