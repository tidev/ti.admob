/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiProxy.h"
//#import "TiAdmobInterstitial.h"
#import "GADInterstitial.h"
#import "GADInterstitialDelegate.h"
#import "TiAdmobInterstitial.h"

@interface TiAdmobInterstitialProxy : TiProxy<GADInterstitialDelegate> {
  TiAdmobInterstitial *interstitialView;
}
-(void)prepare:(id)args;
-(void)showMe:(id)args;
@end
