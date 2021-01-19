/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobModule.h"
#import "TiAdmobTypes.h"
#import "TiApp.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"
#import <GoogleMobileAds/GoogleMobileAds.h>
#import <PersonalizedAdConsent/PersonalizedAdConsent.h>
#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 140000
#import <AppTrackingTransparency/ATTrackingManager.h>
#import <FBAudienceNetwork/FBAdSettings.h>
#endif

@implementation TiAdmobModule

// this is generated for your module, please do not change it
- (id)moduleGUID
{
  return @"0d005e93-9980-4739-9e41-fd1129c8ff32";
}

// this is generated for your module, please do not change it
- (NSString *)moduleId
{
  return @"ti.admob";
}

#pragma mark Lifecycle

- (void)startup
{
  // this method is called when the module is first loaded
  // you *must* call the superclass
  [super startup];

  NSLog(@"[DEBUG] Ti.AdMob loaded", self);
}

#pragma mark Public API's

- (void)disableSDKCrashReporting:(id)unused
{
  [GADMobileAds disableSDKCrashReporting];
}

- (void)disableAutomatedInAppPurchaseReporting:(id)unused
{
  [GADMobileAds disableAutomatedInAppPurchaseReporting];
}

- (void)requestConsentInfoUpdateForPublisherIdentifiers:(id)args
{
  ENSURE_SINGLE_ARG(args, NSDictionary);

  NSArray<NSString *> *publisherIdentifiers = [args objectForKey:@"publisherIdentifiers"];
  KrollCallback *callback = [args objectForKey:@"callback"];

  [[PACConsentInformation sharedInstance] requestConsentInfoUpdateForPublisherIdentifiers:publisherIdentifiers
                                                                        completionHandler:^(NSError *_Nullable error) {
                                                                          if (error != nil) {
                                                                            [callback call:@[ @{@"success" : @NO,
                                                                              @"error" : error.localizedDescription} ]
                                                                                thisObject:self];
                                                                            return;
                                                                          }

                                                                          [callback call:@[ @{@"success" : @YES} ] thisObject:self];
                                                                        }];
}

- (void)showConsentForm:(id)args
{
  ENSURE_SINGLE_ARG(args, NSDictionary);

  if ([args objectForKey:@"privacyURL"] == nil) {
    [self throwException:NSLocalizedString(@"Missing \"privacyURL\" argument", nil) subreason:@"Cannot show consent form" location:CODELOCATION];
    return;
  }

  NSURL *privacyURL = [TiUtils toURL:[args objectForKey:@"privacyURL"] proxy:self];
  KrollCallback *callback = [args objectForKey:@"callback"];

  PACConsentForm *form = [[PACConsentForm alloc] initWithApplicationPrivacyPolicyURL:privacyURL];
  form.shouldOfferPersonalizedAds = [TiUtils boolValue:@"shouldOfferPersonalizedAds" properties:args def:YES];
  form.shouldOfferNonPersonalizedAds = [TiUtils boolValue:@"shouldOfferNonPersonalizedAds" properties:args def:YES];
  form.shouldOfferAdFree = [TiUtils boolValue:@"shouldOfferAdFree" properties:args def:NO];

  TiThreadPerformOnMainThread(
      ^{
        [form loadWithCompletionHandler:^(NSError *_Nullable error) {
          if (error != nil) {
            [callback call:@[ @{ @"error" : error.localizedDescription } ] thisObject:self];
            return;
          }

          [form presentFromViewController:[[[TiApp app] controller] topPresentedController]
                        dismissCompletion:^(NSError *_Nullable error, BOOL userPrefersAdFree) {
                          [callback call:@[ @{
                            @"userPrefersAdFree" : @(userPrefersAdFree),
                            @"error" : NULL_IF_NIL(error.localizedDescription)
                          } ]
                              thisObject:self];
                        }];
        }];
      },
      NO);
}

- (NSNumber *)consentStatus
{
  return @([[PACConsentInformation sharedInstance] consentStatus]);
}

- (NSArray *)adProviders
{
  NSArray *adProviders = [[PACConsentInformation sharedInstance] adProviders];
  NSMutableArray *result = [NSMutableArray arrayWithCapacity:adProviders.count];

  for (PACAdProvider *adProvider in adProviders) {
    [result addObject:@{ @"identifier" : adProvider.identifier,
      @"name" : adProvider.name,
      @"privacyPolicyURL" : adProvider.privacyPolicyURL }];
  }

  return result;
}

- (NSArray *)debugIdentifiers
{
  return [[PACConsentInformation sharedInstance] debugIdentifiers] ?: @[];
}

- (void)setDebugIdentifiers:(id)debugIdentifiers
{
  [[PACConsentInformation sharedInstance] setDebugIdentifiers:debugIdentifiers];
}

- (NSNumber *)debugGeography
{
  return @([[PACConsentInformation sharedInstance] debugGeography]);
}

- (void)resetConsent:(id)unused
{
  [[PACConsentInformation sharedInstance] reset];
}

- (void)setTagForUnderAgeOfConsent:(id)tagForUnderAgeOfConsent
{
  ENSURE_TYPE(tagForUnderAgeOfConsent, NSNumber);
  [[PACConsentInformation sharedInstance] setTagForUnderAgeOfConsent:[TiUtils boolValue:tagForUnderAgeOfConsent]];
}

- (NSNumber *)isTaggedForUnderAgeOfConsent:(id)unused
{
  return @([[PACConsentInformation sharedInstance] isTaggedForUnderAgeOfConsent]);
}

- (NSNumber *)trackingAuthorizationStatus
{
  if (@available(iOS 14, *)) {
    return @([ATTrackingManager trackingAuthorizationStatus]);
  } else {
    NSLog(@"[WARN] Ti.AdMob: The property `trackingAuthorizationStatus` should be used on ios version 14 and above only");
  }
  return @3;
}

- (void)requestTrackingAuthorization:(id)args
{
  ENSURE_SINGLE_ARG(args, NSDictionary);

  KrollCallback *callback = [args objectForKey:@"callback"];
  if (@available(iOS 14, *)) {
    [ATTrackingManager requestTrackingAuthorizationWithCompletionHandler:^(ATTrackingManagerAuthorizationStatus status) {
      NSNumber *trackingStatus = @(status);
      if (callback != nil) {
        [callback call:@[ @{ @"status" : trackingStatus } ] thisObject:self];
      }
    }];
    return;
  } else {
    NSLog(@"[WARN] Ti.AdMob: The function `requestTrackingAuthorization` should be used on ios version 14 and above only");
  }
  if (callback != nil) {
    [callback call:@[ @{ @"status" : @3 } ] thisObject:self];
  }
}

- (void)setAdvertiserTrackingEnabled:(id)advertiserTrackingEnabled
{
  // this method is required by Facebook Audience Network for iOS >= 14
  if (@available(iOS 14, *)) {
    ENSURE_TYPE(advertiserTrackingEnabled, NSNumber);
    [FBAdSettings setAdvertiserTrackingEnabled:[TiUtils boolValue:advertiserTrackingEnabled]];
  } else {
    NSLog(@"[WARN] Ti.AdMob: The function `setAdvertiserTrackingEnabled` should be used on ios version 14 and above only");
  }
}

#pragma mark Constants

MAKE_SYSTEM_PROP(TRACKING_AUTHORIZATION_STATUS_NOT_DETERMINED, 0);
MAKE_SYSTEM_PROP(TRACKING_AUTHORIZATION_STATUS_RESTRICTED, 1);
MAKE_SYSTEM_PROP(TRACKING_AUTHORIZATION_STATUS_DENIED, 2);
MAKE_SYSTEM_PROP(TRACKING_AUTHORIZATION_STATUS_AUTHORIZED, 3);

MAKE_SYSTEM_PROP(CONSENT_STATUS_UNKNOWN, PACConsentStatusUnknown);
MAKE_SYSTEM_PROP(CONSENT_STATUS_NON_PERSONALIZED, PACConsentStatusNonPersonalized);
MAKE_SYSTEM_PROP(CONSENT_STATUS_PERSONALIZED, PACConsentStatusPersonalized);

MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_DISABLED, PACDebugGeographyDisabled);
MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_EEA, PACDebugGeographyEEA);
MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_NOT_EEA, PACDebugGeographyNotEEA);

MAKE_SYSTEM_STR(SIMULATOR_ID, kGADSimulatorID);
MAKE_SYSTEM_PROP(GENDER_MALE, kGADGenderMale);
MAKE_SYSTEM_PROP(GENDER_FEMALE, kGADGenderFemale);
MAKE_SYSTEM_PROP(GENDER_UNKNOWN, kGADGenderUnknown);
MAKE_SYSTEM_PROP(AD_TYPE_BANNER, TiAdmobAdTypeBanner);
MAKE_SYSTEM_PROP(AD_TYPE_INTERSTITIAL, TiAdmobAdTypeInterstitial);
MAKE_SYSTEM_PROP(AD_TYPE_REWARDED_VIDEO, TiAdmobAdTypeRewardedVideo);

@end
