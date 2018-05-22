/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobModule.h"
#import "TiAdmobTypes.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"
#import <GoogleMobileAds/GoogleMobileAds.h>

@implementation TiAdmobModule

// this is generated for your module, please do not change it
-(id)moduleGUID
{
	return @"0d005e93-9980-4739-9e41-fd1129c8ff32";
}

// this is generated for your module, please do not change it
-(NSString*)moduleId
{
	return @"ti.admob";
}

#pragma mark Lifecycle

-(void)startup
{
	// this method is called when the module is first loaded
	// you *must* call the superclass
	[super startup];
	
	NSLog(@"[DEBUG] Ti.AdMob loaded",self);
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

#pragma mark Constants

MAKE_SYSTEM_STR(SIMULATOR_ID, kGADSimulatorID);
MAKE_SYSTEM_PROP(GENDER_MALE, kGADGenderMale);
MAKE_SYSTEM_PROP(GENDER_FEMALE, kGADGenderFemale);
MAKE_SYSTEM_PROP(GENDER_UNKNOWN, kGADGenderUnknown);
MAKE_SYSTEM_PROP(AD_TYPE_BANNER, TiAdmobAdTypeBanner);
MAKE_SYSTEM_PROP(AD_TYPE_INTERSTITIAL, TiAdmobAdTypeInterstitial)

@end
