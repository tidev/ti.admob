//
//  MTGNativeAdvancedAdDelegate.h
//  MTGSDK
//
//  Copyright © 2020 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>
@class MTGNativeAdvancedAd;

NS_ASSUME_NONNULL_BEGIN

@protocol MTGNativeAdvancedAdDelegate <NSObject>


/**
 This method is called when ad is loaded successfully.
 */
- (void)nativeAdvancedAdLoadSuccess:(MTGNativeAdvancedAd *)nativeAd;
 
/**
 This method is called when ad failed to load.
 */
- (void)nativeAdvancedAdLoadFailed:(MTGNativeAdvancedAd *)nativeAd error:(NSError * __nullable)error;

/**
 Sent immediately before the impression of an MTGNativeAdvancedAd object will be logged.
 */
- (void)nativeAdvancedAdWillLogImpression:(MTGNativeAdvancedAd *)nativeAd;
 
/**
 This method is called when ad is clicked.
 */
- (void)nativeAdvancedAdDidClicked:(MTGNativeAdvancedAd *)nativeAd;
 
/**
 Called when the application is about to leave as a result of tapping.
 Your application will be moved to the background shortly after this method is called.
 */
- (void)nativeAdvancedAdWillLeaveApplication:(MTGNativeAdvancedAd *)nativeAd;
 
/**
 Will open the full screen view
 Called when opening storekit or opening the webpage in app

 */
- (void)nativeAdvancedAdWillOpenFullScreen:(MTGNativeAdvancedAd *)nativeAd;
 
/**
 Close the full screen view
 Called when closing storekit or closing the webpage in app
 */
- (void)nativeAdvancedAdCloseFullScreen:(MTGNativeAdvancedAd *)nativeAd;

/**
 This method is called when ad is Closed.
 */
- (void)nativeAdvancedAdClosed:(MTGNativeAdvancedAd *)nativeAd;

@end

NS_ASSUME_NONNULL_END
