/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobInterstitialView.h"
#import "TiApp.h"
#import "TiUtils.h"

@implementation TiAdmobInterstitialView

#pragma mark -
#pragma mark Ad Lifecycle

- (void)viewDidLoad {
  [super viewDidLoad];
  interstitial = [[GADInterstitial alloc] init];
  interstitial.adUnitID = [self.proxy valueForKey:@"adUnitId"];
  
  GADRequest *request = [GADRequest request];
  // Requests test ads on simulators.
  request.testDevices = @[ GAD_SIMULATOR_ID ];
  [interstitial loadRequest:request];
}

@end
