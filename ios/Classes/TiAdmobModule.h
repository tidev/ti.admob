/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiModule.h"

@interface TiAdmobModule : TiModule

- (void)disableSDKCrashReporting:(id)unused;

- (void)requestConsentInfoUpdateWithParameters:(id)args;

- (void)loadForm:(id)args;

- (void)presentPrivacyOptionsForm:(id)args;

- (NSNumber *)isGDPR:(id)unused;

- (NSNumber *)canShowAds:(id)unused;

- (NSNumber *)canShowPersonalizedAds:(id)unused;

- (NSNumber *)isPrivacyOptionsRequired:(id)args;

- (NSNumber *)canRequestAds:(id)args;

- (void)requestConsentInfoUpdateForPublisherIdentifiers:(id)args; // REMOVED

- (void)showConsentForm:(id)args; // REMOVED

- (NSNumber *)consentStatus; // REMOVED

- (NSArray *)adProviders; // REMOVED

- (NSArray *)debugIdentifiers; // REMOVED

- (void)setDebugIdentifiers:(id)debugIdentifiers; // REMOVED

- (NSNumber *)debugGeography; // REMOVED

- (void)resetConsent:(__unused id)unused;

- (void)setTagForUnderAgeOfConsent:(id)tagForUnderAgeOfConsent; // REMOVED

- (NSNumber *)isTaggedForUnderAgeOfConsent:(__unused id)unused; // REMOVED

- (NSNumber *)trackingAuthorizationStatus;

- (void)requestTrackingAuthorization:(id)args;

- (void)setAdvertiserTrackingEnabled:(id)advertiserTrackingEnabled;

- (void)setInMobi_updateGDPRConsent:(id)updateGDPRConsent;

@end
