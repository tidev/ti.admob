/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobInterstitial.h"

@implementation TiAdmobInterstitial

-(void)prepareInterstitial:(id)args {
  ENSURE_UI_THREAD(prepareInterstitial, nil);
  ENSURE_UI_THREAD(showMe, nil);
  NSLog(@"[jo jasper] Prepare was called on admob");
  self.interstitial = [[GADInterstitial alloc] init];
  self.interstitial.adUnitID = self.adUnitID;
  
  GADRequest *request = [GADRequest request];
  // Requests test ads on simulators.
  request.testDevices = @[ GAD_SIMULATOR_ID ];
  [self.interstitial loadRequest:request];
}

-(void)showMe:(id)args {
  NSLog(@"[jo jasper] Show me was called on interstitial wrapper");
  [self.interstitial presentFromRootViewController:[[TiApp app] controller]];
}

@end