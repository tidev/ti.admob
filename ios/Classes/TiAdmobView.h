/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import "TiUIView.h"
#import "AdMobView.h"
#import "AdMobDelegateProtocol.h"

@interface TiAdmobView : TiUIView<AdMobDelegate> {

@private
	AdMobView *adview;
	NSTimer *refreshTimer;
	CGFloat refreshPeriod;
	UIViewController* controller;
}

@end
