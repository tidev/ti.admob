/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobView.h"
#import "TiApp.h"
#import "TiUtils.h"

@implementation TiAdmobView

-(void)dealloc
{
	[refreshTimer invalidate];
	RELEASE_TO_NIL(refreshTimer);
	RELEASE_TO_NIL(adview);
	[super dealloc];
}

// Request a new ad. If a new ad is successfully loaded, it will be animated into location.
- (void)refreshAd:(NSTimer *)timer 
{
	[adview requestFreshAd];
}

-(void)willMoveToSuperview:(UIView *)newSuperview
{
	if ([newSuperview isKindOfClass:[TiUIView class]]) {
		TiUIView* currentView = (TiUIView*)newSuperview;
		while (currentView != nil && ![newSuperview isKindOfClass:[TiUIWindow class]]) {
			currentView = (TiUIView*)[newSuperview superview];
		}
		controller = [(TiWindowProxy*)[(TiUIWindow*)currentView proxy] navController];
		if (controller == nil) {
			controller = [(TiWindowProxy*)[(TiUIWindow*)currentView proxy] controller];
		}
	}
	else {
		controller = nil;
	}
}

#pragma mark Public APIs

-(void)initializeState
{
	[super initializeState];

	adview = [[AdMobView requestAdWithDelegate:self] retain];
	
	NSLog(@"[DEBUG] created admob view: %@",adview);
	
	// allow refreshing of ads
	refreshPeriod = [TiUtils floatValue:[self.proxy valueForKey:@"refreshPeriod"] def:0];
	if (refreshPeriod > 0)
	{
		refreshTimer = [[NSTimer scheduledTimerWithTimeInterval:refreshPeriod/1000 target:self selector:@selector(refreshAd:) userInfo:nil repeats:YES] retain];
	}
}


#pragma mark Delegates

- (NSString *)publisherIdForAd:(AdMobView*)ad
{
	return [self.proxy valueForKey:@"publisherId"];
}

// Return the current view controller (AdMobView should be part of its view heirarchy).
// Make sure to return the root view controller (e.g. a UINavigationController, not
// the UIViewController attached to it).  If there is no UIViewController return nil.
- (UIViewController *)currentViewControllerForAd:(AdMobView*)ad
{
	return controller;
}


// Sent when an ad request loaded an ad; this is a good opportunity to add
// this view to the hierachy, if it has not yet been added.
// Note that this will only ever be sent once per AdMobView, regardless of whether
// new ads are subsequently requested in the same AdMobView.
- (void)didReceiveAd:(AdMobView *)adView
{
	NSLog(@"[DEBUG] admob did receive ad");
		  
	[super addSubview:adView];
	if (refreshTimer!=nil)
	{
		[refreshTimer invalidate];
		RELEASE_TO_NIL(refreshTimer);
		refreshTimer = [[NSTimer scheduledTimerWithTimeInterval:refreshPeriod/1000 target:self selector:@selector(refreshAd:) userInfo:nil repeats:YES] retain];
	}
}

// Sent when a AdMobView successfully makes a subsequent ad request (via requestFreshAd).
// For example an AdView object that shows three ads in its lifetime will see the following
// methods called:  didReceiveAd:, didReceiveRefreshedAd:, and didReceiveRefreshedAd:.
- (void)didReceiveRefreshedAd:(AdMobView *)adView
{
	NSLog(@"[DEBUG] admob did receive refreshed ad");
}

// Sent when an ad request failed to load an ad.
// Note that this will only ever be sent once per AdMobView, regardless of whether
// new ads are subsequently requested in the same AdMobView.
- (void)didFailToReceiveAd:(AdMobView *)adView
{
	NSLog(@"[DEBUG] admob failed to receive ad");
}

// Sent when subsequent AdMobView ad requests fail (via requestFreshAd).
- (void)didFailToReceiveRefreshedAd:(AdMobView *)adView
{
	NSLog(@"[DEBUG] admob failed to receive refreshed ad");
}

// Sent just before presenting the user a full screen view, such as a canvas page or an embedded webview,
// in response to clicking on an ad. Use this opportunity to stop animations, time sensitive interactions, etc.
- (void)willPresentFullScreenModal
{
	NSLog(@"[DEBUG] admob will present fullscreen modal");
}

// Sent just after presenting the user a full screen view, such as a canvas page or an embedded webview,
// in response to clicking on an ad.
- (void)didPresentFullScreenModal
{
	NSLog(@"[DEBUG] admob did present fullscreen modal");
}

// Sent just before dismissing a full screen view.
- (void)willDismissFullScreenModal
{
	NSLog(@"[DEBUG] admob will dismiss fullscreen modal");
}

// Sent just after dismissing a full screen view. Use this opportunity to
// restart anything you may have stopped as part of -willPresentFullScreenModal:.
- (void)didDismissFullScreenModal
{
	NSLog(@"[DEBUG] admob did dismiss fullscreen modal");
}

// Send just before the application will close because the user clicked on an ad.
// Clicking on any ad will either call this or willPresentFullScreenModal.
// The normal UIApplication applicationWillTerminate: delegate method will be called
// after this.
- (void)applicationWillTerminateFromAd:(AdMobView*)ad
{
	NSLog(@"[DEBUG] admob will terminate from ad");
}


// Specifies the ad background color, for tile+text ads.
// Defaults to [UIColor colorWithRed:0.443 green:0.514 blue:0.631 alpha:1], which is a chrome-y color.
// Note that the alpha channel in the provided color will be ignored and treated as 1.
// We recommend against using a white or very light color as the background color, but
// if you do, be sure to implement primaryTextColor and secondaryTextColor.
// Grayscale colors won't function correctly here. Use e.g. [UIColor colorWithRed:0 green:0 blue:0 alpha:1]
// instead of [UIColor colorWithWhite:0 alpha:1] or [UIColor blackColor].
- (UIColor *)adBackgroundColorForAd:(AdMobView*)ad
{
	TiColor* color = [TiUtils colorValue:[self.proxy valueForKey:@"adBackgroundColor"]];
	if (color!=nil)
	{
		return [color _color];
	}
	return [UIColor colorWithRed:0 green:0 blue:0 alpha:1];
}

// Specifies the primary text color for ads.
// Defaults to [UIColor whiteColor].
- (UIColor *)primaryTextColorForAd:(AdMobView*)ad
{
	TiColor* color = [TiUtils colorValue:[self.proxy valueForKey:@"primaryTextColor"]];
	if (color!=nil)
	{
		return [color _color];
	}
	return [UIColor whiteColor];
}

// Specifies the secondary text color for ads.
// Defaults to [UIColor whiteColor].
- (UIColor *)secondaryTextColor:(AdMobView*)ad
{
	TiColor* color = [TiUtils colorValue:[self.proxy valueForKey:@"secondaryTextColor"]];
	if (color!=nil)
	{
		return [color _color];
	}
	return [UIColor whiteColor];
}


// Test ads are returned to these devices.  Device identifiers are the same used to register
// as a development device with Apple.  To obtain a value open the Organizer 
// (Window -> Organizer from Xcode), control-click or right-click on the device's name, and
// choose "Copy Device Identifier".  Alternatively you can obtain it through code using
// [UIDevice currentDevice].uniqueIdentifier.
//
// For example:
//    - (NSArray *)testDevices {
//      return [NSArray arrayWithObjects:
//              ADMOB_SIMULATOR_ID,                             // Simulator
//              //@"28ab37c3902621dd572509110745071f0101b124",  // Test iPhone 3GS 3.0.1
//              //@"8cf09e81ef3ec5418c3450f7954e0e95db8ab200",  // Test iPod 2.2.1
//              nil];
//    }
- (NSArray *)testDevices
{
	bool testing = [TiUtils boolValue:[self.proxy valueForKey:@"testmode"] def:NO];
	if (testing)
	{
		// in test mode, we support testing on device and in the simulator
		return [NSMutableArray arrayWithObjects:ADMOB_SIMULATOR_ID,[UIDevice currentDevice].uniqueIdentifier,nil];
	}
	return nil;
}

// If implemented, lets you specify the action type of the test ad. Defaults to @"url" (web page).
// Does nothing if testDevices is not implemented or does not map to the current device.
// Acceptable values are @"url", @"app", @"movie", @"itunes", @"call", @"canvas".  For interstitials
// use "video_int".
//
// Normally, the adservers restricts ads appropriately (e.g. no click to call ads for iPod touches).
// However, for your testing convenience, they will return any type requested for test ads.
//- (NSString *)testAdAction;


// If your application uses CoreLocation you can provide the current coordinates to help
// provide more relevant ads to your users.  Note it is against Apple's policy to use 
// CoreLocation just for serving ads.
//
// For example:
//    - (double)locationLatitude {
//      return myCLLocationManager.location.coordinate.latitude;
//    }
//    - (double)locationLongitude {
//      return myCLLocationManager.location.coordinate.longitude;
//    }
//    - (NSDate *)locationTimestamp {
//      return myCLLocationManager.location.timestamp;
//    }
// - (double)locationLatitude;
//- (double)locationLongitude;
//- (NSDate *)locationTimestamp;

// The following functions, if implemented, provide extra information
// for the ad request. If you happen to have this information, providing it will
// help select better targeted ads and will improve monetization.
//
// Keywords and search terms should be provided as a space separated string
// like "iPhone monetization San Mateo". We strongly recommend that
// you NOT hard code keywords or search terms.
//
// Keywords are used to select better ads; search terms _restrict_ the available
// set of ads. Note, then, that providing a search string may seriously negatively
// impact your fill rate; we recommend using it only when the user is submitting a
// free-text search request and you want to _only_ display ads relevant to that search.
// In those situations, however, providing a search string can yield a significant
// monetization boost.
//
// For all of these methods, if the information is not available at the time of
// the call, you should return nil.
- (NSString *)postalCode
{
	return [self.proxy valueForKey:@"postalCode"];
}
- (NSString *)areaCode
{
	return [self.proxy valueForKey:@"areaCode"];
}
- (NSDate *)dateOfBirth
{
	return [self.proxy valueForKey:@"dateOfBirth"];
}
- (NSString *)gender
{
	return [self.proxy valueForKey:@"gender"];
}
- (NSString *)keywords
{
	return [self.proxy valueForKey:@"keywords"];
}
- (NSString *)searchString
{
	return [self.proxy valueForKey:@"searchString"];
}


@end
