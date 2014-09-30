/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobInterstitialProxy.h"

#import "TiUtils.h"
#import "TiApp.h"
#import "TiUtils.h"

@implementation TiAdmobInterstitialProxy

-(void)initializeProperty:(NSString *)name defaultValue:(id)value {
  [super initializeProperty:name defaultValue:value];
  NSLog(@"[jo jasper]viewDidLoad was called on TiAdmobInterstitialView with key %@", [self valueForKey:@"adUnitId"]);
  interstitial = [[GADInterstitial alloc] init];
  interstitial.adUnitID = [self valueForKey:@"adUnitId"];

  GADRequest *request = [GADRequest request];
  // Requests test ads on simulators.
  request.testDevices = @[ GAD_SIMULATOR_ID ];
  [interstitial loadRequest:request];
}

-(void)showMe
{
  NSLog([@"[jo jasper] Request to show was received"]);
  [interstitial presentFromRootViewController:self];
}

@end
