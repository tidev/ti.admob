//
//  MTGBannerAdViewDelegate.h
//  MTGSDK
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>


@class MTGBannerAdView;

@protocol MTGBannerAdViewDelegate <NSObject>
/**
 This method is called when adView ad slot is loaded successfully.
 
 @param adView : view for adView
 */
- (void)adViewLoadSuccess:(MTGBannerAdView *)adView;

/**
 This method is called when adView ad slot failed to load.
 
 @param adView : view for adView
 @param error : error
 */
- (void)adViewLoadFailedWithError:(NSError *)error adView:(MTGBannerAdView *)adView;

/**
 Sent immediately before the impression of an MTGBannerAdView object will be logged.
 
 @param adView An MTGBannerAdView object sending the message.
 */
- (void)adViewWillLogImpression:(MTGBannerAdView *)adView;

/**
 This method is called when ad is clicked.
 
 @param adView : view for adView
 */
- (void)adViewDidClicked:(MTGBannerAdView *)adView;

/**
 Called when the application is about to leave as a result of tapping.
 Your application will be moved to the background shortly after this method is called.
 
@param adView : view for adView
 */
- (void)adViewWillLeaveApplication:(MTGBannerAdView *)adView;

/**
 Will open the full screen view
 Called when opening storekit or opening the webpage in app
 
 @param adView : view for adView
 */
- (void)adViewWillOpenFullScreen:(MTGBannerAdView *)adView;

/**
 Close the full screen view
 Called when closing storekit or closing the webpage in app
 
 @param adView : view for adView
 */
- (void)adViewCloseFullScreen:(MTGBannerAdView *)adView;

/**
 This method is called when ad is Closed.

 @param adView : view for adView
 */
- (void)adViewClosed:(MTGBannerAdView *)adView;


@end

