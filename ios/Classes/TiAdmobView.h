/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiUIView.h"
#import "GADInterstitial.h"
#import "GADInterstitialDelegate.h"

@interface TiAdmobView : TiUIView<GADInterstitialDelegate> {
  
@private
  GADInterstitial *interstitial;
}

@end

//
//
///**
// * Appcelerator Titanium Mobile
// * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
// * Licensed under the terms of the Apache Public License
// * Please see the LICENSE included with this distribution for details.
// */
//
//#import "TiUIView.h"
//#import "GADBannerView.h"
//#import "GADBannerViewDelegate.h"
//
//@interface TiAdmobView : TiUIView<GADBannerViewDelegate> {
//
//@private
//	GADBannerView *ad;
//}
//
//@end
