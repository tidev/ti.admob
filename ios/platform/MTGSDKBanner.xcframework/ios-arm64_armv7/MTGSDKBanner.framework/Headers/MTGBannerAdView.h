//
//  MTGBannerAdView.h
//  MTGSDK
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <UIKit/UIKit.h>
#if __has_include(<MTGSDK/MTGSDK.h>)
    #import <MTGSDK/MTGBool.h>
    #import <MTGSDK/MTGAdSize.h>
#else
    #import "MTGBool.h"
    #import "MTGAdSize.h"
#endif

#import "MTGBannerAdViewDelegate.h"


#define MTGBannerSDKVersion @"7.3.8"


NS_ASSUME_NONNULL_BEGIN

@interface MTGBannerAdView : UIView

/**
 Automatic refresh time, the time interval of banner view displaying new ads, is set in the range of 10s~180s.
 If set 0, it will not be refreshed.
 You need to set it before loading ad.
 */
@property(nonatomic,assign) NSInteger autoRefreshTime;

/**
 Whether to show the close button
 MTGBoolNo means off,MTGBoolYes means on
 */
@property(nonatomic,assign) MTGBool showCloseButton;

/**
placementId
*/
@property(nonatomic,copy,readonly) NSString *_Nullable placementId;

/**
 unitId
 */
@property(nonatomic,copy,readonly) NSString * _Nonnull unitId;

/**
* get the id of this request ad,call  after adViewLoadSuccess
*/
@property(nonatomic,copy,readonly) NSString * _Nullable requestId;

/**
 the delegate
 */
@property(nonatomic,weak,nullable) id <MTGBannerAdViewDelegate> delegate;

/**
 The current ViewController of display ad.
 */
@property (nonatomic, weak) UIViewController * _Nullable  viewController;

/**
 This is a method to initialize an MTGBannerAdView with the given unit id
 
 @param adSize The size of the banner view.
 @param placementId The id of the ad placement id. You can create your ad placement from our Portal.
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param rootViewController The view controller that will be used to present full screen ads.
 
 */
- (nonnull instancetype)initBannerAdViewWithAdSize:(CGSize)adSize
                                       placementId:(nullable NSString *)placementId
                                            unitId:(nonnull NSString *) unitId
                                rootViewController:(nullable UIViewController *)rootViewController;

/**
 This is a method to initialize an MTGBannerAdView with the given unit id
 
 @param bannerSizeType please refer to enum MTGBannerSizeType.
 @param placementId The id of the ad placement id. You can create your ad placement from our Portal.
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param rootViewController The view controller that will be used to present full screen ads.

 */
- (nonnull instancetype)initBannerAdViewWithBannerSizeType:(MTGBannerSizeType)bannerSizeType
                                               placementId:(nullable NSString *)placementId
                                                    unitId:(nonnull NSString *) unitId
                                        rootViewController:(nullable UIViewController *)rootViewController;
/**
 Begin to load banner ads
 */
- (void)loadBannerAd;

/*!
 This method is used to request ads with the token you got previously
 
 @param bidToken    - the token from bid request within MTGBidFramework.
 */

- (void)loadBannerAdWithBidToken:(nonnull NSString *)bidToken;

/**
 Call this method when you want to relase MTGBannerAdView. It's optional.
 
 NOTE: After calling this method, if you need to continue using the MTGBannerAdView, you must reinitialize a MTGBannerAdView
 */
- (void)destroyBannerAdView;

@end

NS_ASSUME_NONNULL_END
