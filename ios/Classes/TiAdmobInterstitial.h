/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "GADInterstitial.h"
#include "TiApp.h"

@interface TiAdmobInterstitial : UIViewController

-(void)showMe:(id)args;
-(void)prepareInterstitial:(id)args;

@property(nonatomic, strong) GADInterstitial *interstitial;
@property(nonatomic, strong) NSString *adUnitID;

@end