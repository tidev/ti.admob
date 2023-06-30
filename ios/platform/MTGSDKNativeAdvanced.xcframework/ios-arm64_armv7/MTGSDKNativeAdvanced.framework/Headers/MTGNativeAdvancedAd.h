//
//  MTGNativeAdvancedAd.h
//  MTGSDK
//
//  Copyright © 2020 Mintegral. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "MTGNativeAdvancedAdDelegate.h"


#define MTGNativeAdvancedSDKVersion @"7.3.8"


NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSInteger, MTGNativeAdvancedAdVideoPlayType) {
    MTGVideoPlayTypeOnlyWiFi = 1,// the video will play only if the network is WiFi
    MTGVideoPlayTypeJustTapped = 2,// the video will play when user tap the adView
    MTGVideoPlayTypeAuto = 3,//Default value, the video will play as long as the internet is available
};


@interface MTGNativeAdvancedAd : NSObject

/** Set delegate to receive protocol event. */
@property(nonatomic,weak) id <MTGNativeAdvancedAdDelegate> delegate;
 
/** The type to control ad video play. */
@property(nonatomic,assign) MTGNativeAdvancedAdVideoPlayType autoPlay;

/**
 Whether or not to mute the video player.
 You should set YES if you want to mute the video player, otherwise NO.
*/
@property(nonatomic,assign) BOOL mute;

/**
 Whether or not to show the close button.
 You should set YES if you want to show the close button, otherwise NO.
*/
@property(nonatomic,assign) BOOL showCloseButton;

/** The current ViewController of display ad. */
@property(nonatomic,weak) UIViewController * _Nullable viewController;

@property(nonatomic,copy,readonly) NSString *_Nullable placementId;

@property(nonatomic,copy,readonly) NSString * _Nonnull unitId;

/**
* get the id of this request ad,call  after nativeAdvancedAdLoadSuccess.
*/
@property(nonatomic,copy,readonly) NSString *_Nullable requestId;

/**
This is a method to initialize.

@param adSize The nativeAdvanced ad size.
@param placementID The id of the ad placement id. You can create your ad placement from the portal of mintegral.
@param unitID The id of the ad unit. You can create your unit id from the portal of mintegral.
@param rootViewController The view controller that will be used to present full screen ads.
 
*/
- (nonnull instancetype)initWithPlacementID:(nullable NSString *)placementID
                                 unitID:(nonnull NSString *)unitID
                                 adSize:(CGSize)adSize
                     rootViewController:(nullable UIViewController *)rootViewController;


/**
 This is a method to decorate the elements for the ad content.
 
 @param style  The setting for the elements of the ad content.
 @note For specific examples, please refer to the site:
    https://dev.mintegral.com/doc/index.html?file=sdk-m_sdk-ios&lang=en
*/
- (void)setAdElementsStyle:(NSDictionary *)style;

/**
 Request a NativeAdvanced Ad.
*/
- (void)loadAd;

/**
 Whether or not if there was a available ad to show.

 @return YES means there was a available ad, otherwise NO.
*/
- (BOOL)isAdReady;


/**
 Request a NativeAdvanced Ad via in-app header bidding

 @param bidToken token from bid request within MTGBidFramework.
*/
- (void)loadAdWithBidToken:(nonnull NSString *)bidToken;

/**
 Whether or not if there was a available bidding ad to show.

 @return YES means there was a available bidding ad, otherwise NO.
*/
- (BOOL)isBiddingAdReady;


/**
 Fetch the adView

 @note If get the adView before loadSuccess, you will get a UIView without a ad, which will be  attached a ad after loadSuccess
*/
- (UIView *)fetchAdView;


/**
 Call this method when you want to relase the ad, and the adView will be removed from your presenting view.
 
 @note After calling this method, if you need to continue using the MTGNativeAdvancedAd, you must reinitialize a MTGNativeAdvancedAd
*/
- (void)destroyNativeAd;


@end

NS_ASSUME_NONNULL_END
