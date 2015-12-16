/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobView.h"
#import "TiApp.h"
#import "TiUtils.h"

@implementation TiAdmobView

#pragma mark - Ad Lifecycle

-(GADRequest*)request
{
    if (request == nil) {
        request = [GADRequest request];
    }
    
    return request;
}

-(GADBannerView*)bannerView
{
    if (bannerView == nil) {
        // Create the view with dynamic width and height specification.
        bannerView = [[GADBannerView alloc] initWithAdSize:[self generateHeight]];
        
        // Set the delegate to receive the internal events
        [bannerView setDelegate:self];
        [bannerView setInAppPurchaseDelegate:self];

        // Let the runtime know which UIViewController to restore after taking
        // the user wherever the ad goes and add it to the view hierarchy.
        [bannerView setRootViewController:[[TiApp app] controller]];
        
        // Add the view to the view hirarchie
        [self addSubview:[self bannerView]];
    }
    
    return bannerView;
}

-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
    [self loadRequest:nil];
}

-(void)dealloc
{
    if (bannerView != nil) {
        [bannerView removeFromSuperview];
    }
    
    RELEASE_TO_NIL(bannerView);
    RELEASE_TO_NIL(request);
    
    [super dealloc];
}

#pragma mark - Public API's

- (void)loadRequest:(id)unused
{
    [[self bannerView] loadRequest:[self request]];
}

- (void)setAdUnitId_:(id)value
{
    ENSURE_TYPE(value, NSString);
    
    id debugEnabled = [[self proxy] valueForKey:@"debugEnabled"];
    
    if (debugEnabled != nil && [TiUtils boolValue:debugEnabled] == YES) {
        [[self bannerView] setAdUnitID:@"ca-app-pub-0123456789012345/0123456789"]; // Provided test id by Google
        return;
    }
    
    [[self bannerView] setAdUnitID:[TiUtils stringValue:value]];
}

- (void)setAutoloadEnabled:(id)value
{
    ENSURE_TYPE(value, NSNumber);
    [[self bannerView] setAutoloadEnabled:[TiUtils boolValue:value]];
}

- (void)setKeywords_:(id)value
{
    if ([value isKindOfClass:[NSString class]]) {
        [[self request] setKeywords:@[[TiUtils stringValue:value]]];
        NSLog(@"[WARN] Ti.Admob: The property `keywords` for string values is deprecated. Please use an array of string values instead.");
    } else if ([value isKindOfClass:[NSArray class]]) {
        [[self request] setKeywords:value];
    } else {
        NSLog(@"[ERROR] Ti.Admob: The property `keywords` must be either a String or an Array.");
    }
}

- (void)setDateOfBirth_:(id)value
{
    ENSURE_TYPE(value, NSDate);
    [[self request] setBirthday:value];
}

- (void)setTestDevices_:(id)value
{
    ENSURE_TYPE(value, NSArray);
    [[self request] setTestDevices:value];
}

- (void)setAdBackgroundColor_:(id)value
{
    [[self bannerView] setBackgroundColor:[[TiUtils colorValue:value] _color]];
}

- (void)setTagForChildDirectedTreatment_:(id)value
{
    ENSURE_TYPE(value, NSNumber);
    [[self request] tagForChildDirectedTreatment:[TiUtils boolValue:value]];
}

- (void)setRequestAgent_:(id)value
{
    ENSURE_TYPE(value, NSString);
    [[self request] setRequestAgent:[TiUtils stringValue:value]];
}

- (void)setContentURL_:(id)value
{
    ENSURE_TYPE(value, NSString);
    
    if ([self validateUrl:value] == NO) {
        NSLog(@"[WARN] Ti.Admob: The value of the property `contentURL` looks invalid.");
    }
    
    [[self request] setContentURL:[TiUtils stringValue:value]];
}

- (void)setExtras_:(id)args
{
    ENSURE_TYPE(args, NSDictionary);
    
    GADExtras *extras = [[GADExtras alloc] init];
    [extras setAdditionalParameters:args];
    [[self request] registerAdNetworkExtras:extras];
}

- (void)setGender_:(id)value
{
    if ([value isKindOfClass:[NSString class]]) {
        NSLog(@"[WARN] Ti.Admob: String values for `gender` are deprecated in 2.0.0, use the `GENDER_MALE` or `GENDER_FEMALE` constant instead.");
        
        if ([value isEqualToString:@"male"]) {
            [[self request] setGender:kGADGenderMale];
        } else if ([value isEqualToString:@"female"]) {
            [[self request] setGender:kGADGenderFemale];
        } else {
            [[self request] setGender:kGADGenderUnknown];
        }
        
        return;
    }
    
    ENSURE_TYPE(value, NSNumber);
    [[self request] setGender:[TiUtils intValue:value def:kGADGenderUnknown]];
}

- (void)setLocation_:(id)args
{
    ENSURE_TYPE(args, NSDictionary);
    
    [[self request] setLocationWithLatitude:[[args valueForKey:@"latitude"] floatValue]
                           longitude:[[args valueForKey:@"longitude"] floatValue]
                            accuracy:[[args valueForKey:@"accuracy"] floatValue]];
}

#pragma mark - Deprecated / removed API's

- (void)setPublisherId_:(id)value
{
    NSLog(@"[ERROR] Ti.Admob: The property `publisherId` has been removed in 2.0.0, use `adUnitId` instead.");
}

- (void)setTesting_:(id)value
{
    NSLog(@"[ERROR] Ti.Admob: The property `testing` has been removed in 2.0.0, use `testDevices` instead.");
}

#pragma mark - Utilities

// http://stackoverflow.com/a/3819561/5537752
- (BOOL)validateUrl:(NSString *)candidate {
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

#pragma mark - Ad Delegate

- (void)adViewDidReceiveAd:(GADBannerView *)view
{
    [self.proxy fireEvent:@"didReceiveAd"];
}

- (void)adView:(GADBannerView *)view didFailToReceiveAdWithError:(GADRequestError *)error
{
    [self.proxy fireEvent:@"didFailToReceiveAd" withObject:@{@"error":error.localizedDescription}];
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

- (void)didReceiveInAppPurchase:(GADInAppPurchase *)purchase
{
    [self.proxy fireEvent:@"didReceiveInAppPurchase" withObject:@{
        @"productID": purchase.productID,
        @"quantity": [NSNumber numberWithInteger:purchase.quantity]
    }];
}


@end
