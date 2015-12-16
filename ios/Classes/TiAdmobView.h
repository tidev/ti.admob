/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiUIView.h"
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface TiAdmobView : TiUIView<GADBannerViewDelegate> {

@private
	GADBannerView *ad;
}

@end
