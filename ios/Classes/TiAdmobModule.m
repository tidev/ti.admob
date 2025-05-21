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
//#import <PersonalizedAdConsent/PersonalizedAdConsent.h>
#import <UserMessagingPlatform/UserMessagingPlatform.h>

#import <FBAudienceNetwork/FBAdSettings.h>
#import <InMobiAdapter/InMobiAdapter.h>

#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 140000
#import <AppTrackingTransparency/ATTrackingManager.h>
#endif

@implementation TiAdmobModule

- (id)moduleGUID
{
  return @"0d005e93-9980-4739-9e41-fd1129c8ff32";
}

- (NSString *)moduleId
{
  return @"ti.admob";
}

#pragma mark Public API's


// Check if the TC string last updated date was more than 13 months ago
// https://developers.google.com/admob/ios/privacy/gdpr#troubleshooting
/**
 * This function checks the date of last consent, which is base64-encoded in digits 1..7 of a string that is stored
 * in userDefaults under the key "IABTCF_TCString".
 *
 * If this date is older than 365 days, the entry with that key will be removed from userDefaults. With the IABTCF
 * configuration now being invalid, the CMP should re-display the consent dialog the next time it is instantiated.
 *
 * This should avoid errors of any used ad solution, which is supposed to consider consent older than 13 months "outdated".
 *
 * Inspired by @bocops code: https://github.com/bocops/UMP-workarounds/blob/main/detect_outdated_consent/android/java/detect_outdated_consent.java
 */
- (void)deleteTCStringIfOutdated {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    // Retrieve the IABTCF string from userDefaults
    NSString *tcString = [userDefaults stringForKey:@"IABTCF_TCString"];
    
    // Return early if the TC string does not exist
    if (!tcString) {
        NSLog(@"[DEBUG] The IABTCF_TCString does not exist.");
        return;
    }
    
    // Base64 alphabet used to store data in IABTCF string
    NSString *base64 = @"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
    
    // Date is stored in digits 1..7 of the IABTCF string
    NSString *dateSubstring = [tcString substringWithRange:NSMakeRange(1, 6)];
    
    // Interpret date substring as base64-encoded integer value
    long long timestamp = 0;
    for (int i = 0; i < dateSubstring.length; i++) {
        unichar c = [dateSubstring characterAtIndex:i];
        NSUInteger value = [base64 rangeOfString:[NSString stringWithFormat:@"%C", c]].location;
        timestamp = timestamp * 64 + value;
    }
    
    // Timestamp is given in deci-seconds, convert to milliseconds
    timestamp *= 100;
    
    // Calculate age in days by comparing with the current timestamp
    long daysAgo = (long)(([[NSDate date] timeIntervalSince1970] * 1000 - timestamp) / (1000 * 60 * 60 * 24));
    
    // Delete TC string if it is over a year old
    if (daysAgo > 365) {
        [userDefaults removeObjectForKey:@"IABTCF_TCString"];
        [userDefaults synchronize];
        NSLog(@"[DEBUG] Ti.AdMob: TC string removed as it was %ld days old.", daysAgo);
    } else {
        NSLog(@"[DEBUG] Ti.AdMob: TC string is not outdated. Last updated %ld days ago.", daysAgo);
    }
}

- (void)disableSDKCrashReporting:(id)unused
{
  [GADMobileAds.sharedInstance disableSDKCrashReporting];
}
- (void)requestConsentInfoUpdateWithParameters:(id)args
{
  ENSURE_SINGLE_ARG(args, NSDictionary);

  NSArray<NSString *> *testDeviceIdentifiers = [args objectForKey:@"testDeviceIdentifiers"];
  KrollCallback *callback = [args objectForKey:@"callback"];

  // Create a UMPRequestParameters object.
  UMPRequestParameters *parameters = [[UMPRequestParameters alloc] init];

  // Create a UMPDebugSettings object.
  UMPDebugSettings *debugSettings = [[UMPDebugSettings alloc] init];
  debugSettings.testDeviceIdentifiers = testDeviceIdentifiers ?: @[];
  debugSettings.geography = [TiUtils intValue:@"geography" properties:args def:0];
  parameters.debugSettings = debugSettings;

  // Set tag for under age of consent. Here NO means users are not under age.
  parameters.tagForUnderAgeOfConsent = [TiUtils boolValue:@"tagForUnderAgeOfConsent" properties:args def:NO];;
  
  // The TCF string may be outdated, so proceed with removal if needed.
  [self deleteTCStringIfOutdated];

  // Request an update to the consent information.
  [[UMPConsentInformation sharedInstance] requestConsentInfoUpdateWithParameters:parameters
                                                                        completionHandler:^(NSError *_Nullable error) {
                                                                          // The consent information has updated.
                                                                          if (error != nil) {
                                                                            // Handle the error.
                                                                            [callback call:@[ @{@"success" : @NO,
                                                                              @"error" : error.localizedDescription} ]
                                                                                thisObject:self];
                                                                            return;
                                                                          } else {
                                                                            // The consent information state was updated.
                                                                            // You are now ready to see if a form is available.
                                                                            UMPFormStatus formStatus =
                                                                              UMPConsentInformation.sharedInstance
                                                                                  .formStatus;
                                                                              NSNumber *status = @(formStatus);
                                                                              if (formStatus == UMPFormStatusAvailable) {
                                                                                NSLog(@"[INFO] Ti.AdMob: formStatus is available!");
                                                                              }
                                                                              if (callback != nil) {                                                                            
                                                                                [callback call:@[ @{
                                                                                  @"success" : @YES,
                                                                                  @"status" : status} ]
                                                                                  thisObject:self];
                                                                              }
                                                                          }                                                                          
                                                                        }];
}

-(void)loadForm:(id)args
{
    ENSURE_SINGLE_ARG(args, NSDictionary);
    KrollCallback *callback = [args objectForKey:@"callback"];

    TiThreadPerformOnMainThread(
        ^{
            UIViewController *viewController = [[[TiApp app] controller] topPresentedController];
             // Automatically load and present the form if required
            [UMPConsentForm loadAndPresentIfRequiredFromViewController:viewController
                                                  completionHandler:^(NSError *loadError) {
                if (loadError) {
                    // Handle the error
                    [callback call:@[ @{ @"loadError" : loadError.localizedDescription } ] thisObject:self];
                } else {
                    // Return the consent status
                    NSNumber *status = @(UMPConsentInformation.sharedInstance.consentStatus);
                    [callback call:@[ @{@"status" : status} ] thisObject:self];
                }
            }];
            
            /*[UMPConsentForm loadWithCompletionHandler:^(UMPConsentForm *form,
                                                        NSError *loadError) {
              if (loadError) {
                // Handle the error.
                [callback call:@[ @{ @"loadError" : loadError.localizedDescription } ] thisObject:self];
              } else {
                // Present the form. You can also hold on to the reference to present
                // later.
                NSNumber *status = @(UMPConsentInformation.sharedInstance.consentStatus);
                if (UMPConsentInformation.sharedInstance.consentStatus ==
                    UMPConsentStatusRequired) {
                  [form
                      presentFromViewController:[[[TiApp app] controller] topPresentedController]
                              completionHandler:^(NSError *_Nullable dismissError) {
                                if (UMPConsentInformation.sharedInstance.consentStatus ==
                                    UMPConsentStatusObtained) {
                                  // App can start requesting ads.
                                  [callback call:@[ @{
                                    @"status" : @(UMPConsentInformation.sharedInstance.consentStatus), // update here because it can change between the above assignment
                                    @"dismissError" : NULL_IF_NIL(dismissError.localizedDescription)
                                  } ]
                                      thisObject:self];
                                  return;
                                }
                              }];
                } else {
                  [callback call:@[ @{
                    @"status" : status
                  } ]
                      thisObject:self];
                }
              }
            }];*/
        },
        NO);
}

-(void)presentPrivacyOptionsForm:(id)args
{
    ENSURE_SINGLE_ARG(args, NSDictionary);
    KrollCallback *callback = [args objectForKey:@"callback"];

    TiThreadPerformOnMainThread(^{
        UIViewController *viewController = [[[TiApp app] controller] topPresentedController];

        [UMPConsentForm presentPrivacyOptionsFormFromViewController:viewController
                                                   completionHandler:^(NSError *error) {
            if (error) {
                // Handle the error
                NSLog(@"[ERROR] presentPrivacyOptionsForm: %@", error.localizedDescription);
                NSDictionary *result = @{ @"error": error.localizedDescription };
                if (callback) {
                     [callback call:@[result] thisObject:self];
                }
               
            } else {
                NSDictionary *result = @{ @"success": @(YES) };
                 if (callback) {
                     [callback call:@[result] thisObject:self];
                }
            }
        }];
    },
    NO);
}

- (void)requestConsentInfoUpdateForPublisherIdentifiers:(id)args
{
  DEPRECATED_REMOVED(@"Admob.requestConsentInfoUpdateForPublisherIdentifiers", @"5.0.0", @"5.0.0 (removed by Google. Use new UMP https://developers.google.com/admob/ump/ios/)");
  /*
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
  */
}

/**
 * This functions check if the user has granted the minimum requirements to be able to view the ads,
 * (https://support.google.com/admob/answer/9760862?ref_topic=10303737) and if he has chosen to see 
 * personalized or non-personalized ones.
 *
 * Inspired by https://stackoverflow.com/questions/65351543/how-to-implement-ump-sdk-correctly-for-eu-consent/68310602#68310602
 */

- (NSNumber *)isGDPR:(id)unused
{
    NSUserDefaults *settings = [NSUserDefaults standardUserDefaults];
    NSInteger gdpr = [settings integerForKey:@"IABTCF_gdprApplies"];
    return @(gdpr == 1);
}

- (NSNumber *)canShowAds:(id)unused
{
    NSUserDefaults *settings = [NSUserDefaults standardUserDefaults];
    
    NSString *purposeConsent = [settings stringForKey:@"IABTCF_PurposeConsents"] ?: @"";
    NSString *vendorConsent = [settings stringForKey:@"IABTCF_VendorConsents"] ?: @"";
    NSString *vendorLI = [settings stringForKey:@"IABTCF_VendorLegitimateInterests"] ?: @"";
    NSString *purposeLI = [settings stringForKey:@"IABTCF_PurposeLegitimateInterests"] ?: @"";
    
    NSInteger googleId = 755;
    BOOL hasGoogleVendorConsent = [self hasAttribute:vendorConsent atIndex:googleId];
    BOOL hasGoogleVendorLI = [self hasAttribute:vendorLI atIndex:googleId];
    
    return @([self hasConsentFor:@[@(1)] purposeConsent:purposeConsent hasVendorConsent:hasGoogleVendorConsent]
             && [self hasConsentOrLegitimateInterestFor:@[@(2), @(7), @(9), @(10)] purposeConsent:purposeConsent purposeLI:purposeLI hasVendorConsent:hasGoogleVendorConsent hasVendorLI:hasGoogleVendorLI]);
}

- (NSNumber *)canShowPersonalizedAds:(id)unused
{
    NSUserDefaults *settings = [NSUserDefaults standardUserDefaults];
    
    NSString *purposeConsent = [settings stringForKey:@"IABTCF_PurposeConsents"] ?: @"";
    NSString *vendorConsent = [settings stringForKey:@"IABTCF_VendorConsents"] ?: @"";
    NSString *vendorLI = [settings stringForKey:@"IABTCF_VendorLegitimateInterests"] ?: @"";
    NSString *purposeLI = [settings stringForKey:@"IABTCF_PurposeLegitimateInterests"] ?: @"";
    
    NSInteger googleId = 755;
    BOOL hasGoogleVendorConsent = [self hasAttribute:vendorConsent atIndex:googleId];
    BOOL hasGoogleVendorLI = [self hasAttribute:vendorLI atIndex:googleId];
    
    return @([self hasConsentFor:@[@(1), @(3), @(4)] purposeConsent:purposeConsent hasVendorConsent:hasGoogleVendorConsent]
             && [self hasConsentOrLegitimateInterestFor:@[@(2), @(7), @(9), @(10)] purposeConsent:purposeConsent purposeLI:purposeLI hasVendorConsent:hasGoogleVendorConsent hasVendorLI:hasGoogleVendorLI]);
}

- (BOOL)hasAttribute:(NSString *)input atIndex:(NSInteger)index
{
    return input.length >= index && [[input substringWithRange:NSMakeRange(index - 1, 1)] isEqualToString:@"1"];
}

- (BOOL)hasConsentFor:(NSArray *)purposes purposeConsent:(NSString *)purposeConsent hasVendorConsent:(BOOL)hasVendorConsent
{
    for (NSNumber *purpose in purposes) {
        NSInteger i = [purpose integerValue];
        if (![self hasAttribute:purposeConsent atIndex:i]) {
            return NO;
        }
    }
    return hasVendorConsent;
}

- (BOOL)hasConsentOrLegitimateInterestFor:(NSArray *)purposes purposeConsent:(NSString *)purposeConsent purposeLI:(NSString *)purposeLI hasVendorConsent:(BOOL)hasVendorConsent hasVendorLI:(BOOL)hasVendorLI
{
    for (NSNumber *purpose in purposes) {
        NSInteger i = [purpose integerValue];
        if (([self hasAttribute:purposeLI atIndex:i] && hasVendorLI) ||
            ([self hasAttribute:purposeConsent atIndex:i] && hasVendorConsent)) {
            continue;
        } else {
            return NO;
        }
    }
    return YES;
}

- (NSNumber*)isPrivacyOptionsRequired:(id)args
{
    UMPPrivacyOptionsRequirementStatus status = UMPConsentInformation.sharedInstance.privacyOptionsRequirementStatus;
    return NUMBOOL(status == UMPPrivacyOptionsRequirementStatusRequired);
}

- (NSNumber *)canRequestAds:(id)args
{
    return NUMBOOL(UMPConsentInformation.sharedInstance.canRequestAds);
}

- (void)showConsentForm:(id)args
{
  DEPRECATED_REMOVED(@"Admob.showConsentForm", @"5.0.0", @"5.0.0 (Removed since Ti.Admob 5.0.0 in favor of new UMP method Admob.requestConsentInfoUpdateWithParameters())");
  /*
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
  */
}

- (NSNumber *)consentStatus
{
  DEPRECATED_REMOVED(@"Admob.adProviders", @"5.0.0", @"5.0.0 (Removed since Ti.Admob 5.0.0 because deprecated by Google https://developers.google.com/admob/ios/eu-consent)");
  //return @([[PACConsentInformation sharedInstance] consentStatus]);
}

- (NSArray *)adProviders
{
  DEPRECATED_REMOVED(@"Admob.adProviders", @"5.0.0", @"5.0.0 (Removed since Ti.Admob 5.0.0 because deprecated by Google https://developers.google.com/admob/ios/eu-consent)");
  /*
  NSArray *adProviders = [[PACConsentInformation sharedInstance] adProviders];
  NSMutableArray *result = [NSMutableArray arrayWithCapacity:adProviders.count];

  for (PACAdProvider *adProvider in adProviders) {
    [result addObject:@{ @"identifier" : adProvider.identifier,
      @"name" : adProvider.name,
      @"privacyPolicyURL" : adProvider.privacyPolicyURL }];
  }

  return result;
  */
}

- (NSArray *)debugIdentifiers
{
  DEPRECATED_REMOVED(@"Admob.debugIdentifiers", @"5.0.0", @"5.0.0 (Removed since Ti.Admob 5.0.0. You can set 'testDeviceIdentifiers' parameter in Admob.requestConsentInfoUpdateWithParameters() )");
  //return [[PACConsentInformation sharedInstance] debugIdentifiers] ?: @[];
}

- (void)setDebugIdentifiers:(id)debugIdentifiers
{
  DEPRECATED_REMOVED(@"Admob.setDebugIdentifiers", @"5.0.0", @"5.0.0 (Removed since Ti.Admob 5.0.0. You can set 'testDeviceIdentifiers' parameter in Admob.requestConsentInfoUpdateWithParameters() )");
  //[[PACConsentInformation sharedInstance] setDebugIdentifiers:debugIdentifiers];
}

- (NSNumber *)debugGeography
{
  DEPRECATED_REMOVED(@"Admob.debugGeography", @"5.0.0", @"5.0.0 (Removed since Ti.Admob 5.0.0. You can set 'geography' parameter in Admob.requestConsentInfoUpdateWithParameters() )");
  //return @([[PACConsentInformation sharedInstance] debugGeography]);
}

- (void)resetConsent:(id)unused
{
  [[UMPConsentInformation sharedInstance] reset];
}

- (void)setTagForUnderAgeOfConsent:(id)tagForUnderAgeOfConsent
{
  DEPRECATED_REMOVED(@"Admob.setTagForUnderAgeOfConsent", @"5.0.0", @"5.0.0 (Removed since Ti.Admob 5.0.0. You can set 'tagForUnderAgeOfConsent' parameter in Admob.requestConsentInfoUpdateWithParameters() or in Admob.createView() )");
  /*
  ENSURE_TYPE(tagForUnderAgeOfConsent, NSNumber);
  [[PACConsentInformation sharedInstance] setTagForUnderAgeOfConsent:[TiUtils boolValue:tagForUnderAgeOfConsent]];
  */
}

- (NSNumber *)isTaggedForUnderAgeOfConsent:(id)unused
{
  DEPRECATED_REMOVED(@"Admob.isTaggedForUnderAgeOfConsent", @"5.0.0", @"5.0.0 (Removed since Ti.Admob 5.0.0. You can set 'tagForUnderAgeOfConsent' parameter in Admob.requestConsentInfoUpdateWithParameters() or in Admob.createView() )");
  //return @([[PACConsentInformation sharedInstance] isTaggedForUnderAgeOfConsent]);
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
  DEPRECATED_REMOVED(@"advertiserTrackingEnabled", @"7.1.0", @"8.0.0 The setAdvertiserTrackingEnabled flag is not used for Audience Network SDK 6.15.0+ on iOS 17+ as the Audience Network SDK 6.15.0+ on iOS 17+ now relies on [ATTrackingManager trackingAuthorizationStatus] to accurately represent ATT permission for users of your app);");  
  
  // this method is required by Facebook Audience Network for iOS >= 14
  if (@available(iOS 14, *)) {
    ENSURE_TYPE(advertiserTrackingEnabled, NSNumber);      
    if ([TiUtils boolValue:advertiserTrackingEnabled]) {      
      [FBAdSettings setAdvertiserTrackingEnabled:YES];
      NSLog(@"[DEBUG] Ti.AdMob: setAdvertiserTrackingEnabled --> YES");
    } else {      
      [FBAdSettings setAdvertiserTrackingEnabled:NO];
      NSLog(@"[DEBUG] Ti.AdMob: setAdvertiserTrackingEnabled --> NO");
    }
  } else {
    NSLog(@"[WARN] Ti.AdMob: The function `setAdvertiserTrackingEnabled` should be used on ios version 14 and above only");
  }
}

- (void)setInMobi_updateGDPRConsent:(id)updateGDPRConsent
{
  NSMutableDictionary *consentObject = [[NSMutableDictionary alloc] init];

  ENSURE_TYPE(updateGDPRConsent, NSNumber);
  if ([TiUtils boolValue:updateGDPRConsent]) {
    // this method is required by InMobi to set GDPR    
    [consentObject setObject:@"1" forKey:@"gdpr"];
    [consentObject setObject:@"true" forKey:IMCommonConstants.IM_GDPR_CONSENT_AVAILABLE];
    NSLog(@"[DEBUG] Ti.AdMob: inMobi_updateGDPRConsent --> true");
  }  else {    
    [consentObject setObject:@"0" forKey:@"gdpr"];
    [consentObject setObject:@"true" forKey:IMCommonConstants.IM_GDPR_CONSENT_AVAILABLE];
    NSLog(@"[DEBUG] Ti.AdMob: inMobi_updateGDPRConsent --> false");
  }

  [GADMInMobiConsent updateGDPRConsent:consentObject];
}

#pragma mark Constants

MAKE_SYSTEM_PROP(TRACKING_AUTHORIZATION_STATUS_NOT_DETERMINED, 0);
MAKE_SYSTEM_PROP(TRACKING_AUTHORIZATION_STATUS_RESTRICTED, 1);
MAKE_SYSTEM_PROP(TRACKING_AUTHORIZATION_STATUS_DENIED, 2);
MAKE_SYSTEM_PROP(TRACKING_AUTHORIZATION_STATUS_AUTHORIZED, 3);

MAKE_SYSTEM_PROP(CONSENT_FORM_STATUS_UNKNOWN, UMPFormStatusUnknown); // 0
MAKE_SYSTEM_PROP(CONSENT_FORM_STATUS_AVAILABLE, UMPFormStatusAvailable); // 1
MAKE_SYSTEM_PROP(CONSENT_FORM_STATUS_UNAVAILABLE, UMPFormStatusUnavailable); // 2
MAKE_SYSTEM_PROP(CONSENT_STATUS_UNKNOWN, UMPConsentStatusUnknown); // 0
MAKE_SYSTEM_PROP(CONSENT_STATUS_REQUIRED, UMPConsentStatusRequired); // 1
MAKE_SYSTEM_PROP(CONSENT_STATUS_NOT_REQUIRED, UMPConsentStatusNotRequired); // 2
MAKE_SYSTEM_PROP(CONSENT_STATUS_OBTAINED, UMPConsentStatusObtained); // 3

//MAKE_SYSTEM_PROP(CONSENT_STATUS_UNKNOWN, PACConsentStatusUnknown);
//MAKE_SYSTEM_PROP(CONSENT_STATUS_NON_PERSONALIZED, PACConsentStatusNonPersonalized); deleted from 5.0.0
//MAKE_SYSTEM_PROP(CONSENT_STATUS_PERSONALIZED, PACConsentStatusPersonalized); deleted from 5.0.0

//MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_DISABLED, PACDebugGeographyDisabled);
//MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_EEA, PACDebugGeographyEEA);
//MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_NOT_EEA, PACDebugGeographyNotEEA);

MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_DISABLED, UMPDebugGeographyDisabled); // 0
MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_EEA,  UMPDebugGeographyEEA); // 1
//MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_NOT_EEA, UMPDebugGeographyNotEEA); // 2 (Deprecated and deleted from 8.0.0)
MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_REGULATED_US_STATE, UMPDebugGeographyRegulatedUSState); // 3
MAKE_SYSTEM_PROP(DEBUG_GEOGRAPHY_OTHER, UMPDebugGeographyOther); // 4

//MAKE_SYSTEM_STR(SIMULATOR_ID, GADSimulatorID); deleted from 8.0.0
//MAKE_SYSTEM_PROP(GENDER_MALE, kGADGenderMale); deleted from 4.5.0
//MAKE_SYSTEM_PROP(GENDER_FEMALE, kGADGenderFemale); deleted from 4.5.0
//MAKE_SYSTEM_PROP(GENDER_UNKNOWN, kGADGenderUnknown); deleted from 4.5.0
MAKE_SYSTEM_PROP(AD_TYPE_BANNER, TiAdmobAdTypeBanner);
MAKE_SYSTEM_PROP(AD_TYPE_INTERSTITIAL, TiAdmobAdTypeInterstitial);
MAKE_SYSTEM_PROP(AD_TYPE_REWARDED_VIDEO, TiAdmobAdTypeRewardedVideo);
MAKE_SYSTEM_PROP(AD_TYPE_APP_OPEN, TiAdmobAdTypeAppOpen);

MAKE_SYSTEM_STR(MAX_AD_CONTENT_RATING_GENERAL, GADMaxAdContentRatingGeneral);
MAKE_SYSTEM_STR(MAX_AD_CONTENT_RATING_PARENTAL_GUIDANCE, GADMaxAdContentRatingParentalGuidance);
MAKE_SYSTEM_STR(MAX_AD_CONTENT_RATING_TEEN, GADMaxAdContentRatingTeen);
MAKE_SYSTEM_STR(MAX_AD_CONTENT_RATING_MATURE_AUDIENCE, GADMaxAdContentRatingMatureAudience);

@end
