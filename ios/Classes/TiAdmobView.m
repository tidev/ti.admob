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

#pragma mark -
#pragma mark Ad Lifecycle

-(void)refreshAd:(CGRect)bounds
{
    NSNumber *value = [self.proxy valueForKey:@"isInterstitial"];
    BOOL isInterstitial = [value boolValue];
    
    
    if (adInterstitial != nil) {
        RELEASE_TO_NIL(adInterstitial);
    }
    
    if (adBanner != nil) {
        [adBanner removeFromSuperview];
        RELEASE_TO_NIL(adBanner);
    }
    
    if(isInterstitial)
    {
        adInterstitial = [[GADInterstitial alloc] init];
        
        // Specify the ad's "unit identifier." This is your AdMob Publisher ID.
        adInterstitial.adUnitID = [self.proxy valueForKey:@"adUnitId"];
    }
    else
    {
        adBanner = [[GADBannerView alloc] initWithFrame:bounds];
        // Specify the ad's "unit identifier." This is your AdMob Publisher ID.
        adBanner.adUnitID = [self.proxy valueForKey:@"adUnitId"];
        
        // Let the runtime know which UIViewController to restore after taking
        // the user wherever the ad goes and add it to the view hierarchy.
        adBanner.rootViewController = [[TiApp app] controller];
    }
    
    
    // Initiate a generic request to load it with an ad.
    GADRequest* request = [GADRequest request];
    
    if ([self.proxy valueForKey:@"publisherId"]) {
        NSLog(@"`publisherId` has been removed. Use `adUnitId` instead.");
    }
    if ([TiUtils boolValue:[self.proxy valueForKey:@"testing"] def:NO]) {
        NSLog(@"`testing` has been deprecated. Use `testDevices` instead.");
        // testing is deprecated
        request.testing = YES;
    }
    
    // Go through the configurable properties, populating our request with their values (if they have been provided).
    request.keywords = [self.proxy valueForKey:@"keywords"];
    request.birthday = [self.proxy valueForKey:@"dateOfBirth"];
    request.testDevices = [self.proxy valueForKey:@"testDevices"];
  
    NSString* backgroundColor = [self.proxy valueForKey:@"adBackgroundColor"];
    if (backgroundColor != nil) {
        if(isInterstitial)
            ;
        else
            adBanner.backgroundColor = [[TiUtils colorValue:backgroundColor] _color];
    }
    
    NSDictionary* location = [self.proxy valueForKey:@"location"];
    if (location != nil) {
        [request setLocationWithLatitude:[[location valueForKey:@"latitude"] floatValue]
                               longitude:[[location valueForKey:@"longitude"] floatValue]
                                accuracy:[[location valueForKey:@"accuracy"] floatValue]];
    }
    
    NSString* gender = [self.proxy valueForKey:@"gender"];
    if ([gender isEqualToString:@"male"]) {
        request.gender = kGADGenderMale;
    } else if ([gender isEqualToString:@"female"]) {
        request.gender = kGADGenderFemale;
    } else {
        request.gender = kGADGenderUnknown;
    }
    
    if(isInterstitial)
    {
        adInterstitial.delegate = self;
        [adInterstitial loadRequest:request];        
    }
    else
    {
        [self addSubview:adBanner];
        adBanner.delegate = self;
        [adBanner loadRequest:request];                
    }

}

-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
    [self refreshAd:bounds];
}

-(void)dealloc
{
    if (adBanner != nil) {
        [adBanner removeFromSuperview];
        RELEASE_TO_NIL(adBanner);
    }
    
    if (adInterstitial != nil) {
        RELEASE_TO_NIL(adInterstitial);
    }
    
    [super dealloc];
}

#pragma mark -
#pragma mark Ad Delegate

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

#pragma mark -
#pragma mark Interstitial Delegate
- (void)interstitialDidReceiveAd:(GADInterstitial *)ad
{
    [self.proxy fireEvent:@"didReceiveAd"];
    if ([adInterstitial isReady]) {
        id rootVC = [[[[[UIApplication sharedApplication] keyWindow] subviews] objectAtIndex:0] nextResponder];
        [adInterstitial presentFromRootViewController:rootVC];
    }
}

- (void)interstitial:(GADInterstitial *)ad didFailToReceiveAdWithError:(GADRequestError *)error
{
    [self.proxy fireEvent:@"didFailToReceiveAd"];    
}

- (void)interstitialWillPresentScreen:(GADInterstitial *)ad
{
    [self.proxy fireEvent:@"willPresentScreen"];
}

- (void)interstitialWillDismissScreen:(GADInterstitial *)ad
{
    [self.proxy fireEvent:@"willDismissScreen"];
}

- (void)interstitialDidDismissScreen:(GADInterstitial *)ad
{
    [self.proxy fireEvent:@"didDismissScreen"];
}

- (void)interstitialWillLeaveApplication:(GADInterstitial *)ad
{
    [self.proxy fireEvent:@"willLeaveApplication"];
}

@end
