/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
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
    RELEASE_TO_NIL(ad);
    
    ad = [[GADBannerView alloc] initWithFrame:bounds];
    
    // Specify the ad's "unit identifier." This is your AdMob Publisher ID.
    ad.adUnitID = [self.proxy valueForKey:@"publisherId"];
    
    // Let the runtime know which UIViewController to restore after taking
    // the user wherever the ad goes and add it to the view hierarchy.
    ad.rootViewController = [[TiApp app] controller];
    
    // Initiate a generic request to load it with an ad.
    GADRequest* request = [GADRequest request];
    
    // Go through the configurable properties, populating our request with their values (if they have been provided).
    request.keywords = [self.proxy valueForKey:@"keywords"];
    request.birthday = [self.proxy valueForKey:@"dateOfBirth"];
    request.testing = [TiUtils boolValue:[self.proxy valueForKey:@"testing"] def:NO];
    
    NSString* backgroundColor = [self.proxy valueForKey:@"adBackgroundColor"];
    if (backgroundColor != nil) {
        ad.backgroundColor = [[TiUtils colorValue:backgroundColor] _color];
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
    
    [self addSubview:ad];
    ad.delegate = self;
    [ad loadRequest:request];
}

-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
    [self refreshAd:bounds];
}

-(void)dealloc
{
    RELEASE_TO_NIL(ad);
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


@end
