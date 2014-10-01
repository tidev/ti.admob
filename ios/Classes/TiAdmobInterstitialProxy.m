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

-(void)prepare:(id)args
{
  ENSURE_UI_THREAD(prepare, nil);
  ENSURE_UI_THREAD(showMe, nil);
  NSLog(@"[jo jasper]viewDidLoad was called on TiAdmobInterstitialView with key %@", [self valueForKey:@"adUnitId"]);
  interstitialView = [[TiAdmobInterstitial alloc] init];
  interstitialView.adUnitID = [self valueForKey:@"adUnitId"];
  
  NSLog(@"[jo jasper] Will call prepare");
  [interstitialView prepareInterstitial:nil];
  NSLog(@"[jo jasper] Called prepare");
}

-(void)showMe:(id)args
{
  NSLog(@"[jo jasper] Request to show was received");
  [interstitialView showMe:nil];
}

@end
