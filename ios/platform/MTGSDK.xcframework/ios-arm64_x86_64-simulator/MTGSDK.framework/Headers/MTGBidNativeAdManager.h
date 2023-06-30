//
//  MTGBidNativeAdManager.h
//  MTGSDK
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import "MTGNativeAd.h"




#pragma mark - MTGBidNativeAdManager

@interface MTGBidNativeAdManager : NSObject

/*!
 @property
 
 @abstract The delegate
 
 @discussion All delegate method will be called in main thread.
 */
@property (nonatomic, weak, nullable) id <MTGBidNativeAdManagerDelegate> delegate;

/*!
 @property
 
 @discussion Show the  loading view when to click on ads.
 The default is yes
 */
@property (nonatomic, assign) BOOL showLoadingView;

/*!
@property

@discussion ad current placementId .
*/

@property (nonatomic, readonly) NSString *_Nullable placementId;

/*!
 @property
 
 @discussion ad current UnitId .
 */
@property (nonatomic, readonly) NSString * _Nonnull currentUnitId;

/**
* get the id of this request ad,call  after nativeAdsLoaded.
*/
@property (nonatomic, readonly) NSString *_Nullable requestId;

/*!
 @property
 
 @discussion The current ViewController of display ad.
 the "ViewController" parameters are assigned as calling the init or Registerview method
 */
@property (nonatomic, weak) UIViewController * _Nullable  viewController;


/*!
 
 Initialize the native ads manager which is for loading ads. (MTGCampaign)
 
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param viewController The UIViewController that will be used to present SKStoreProductViewController
 (iTunes Store product information) or the in-app browser. If not set, it will be the root viewController of your current UIWindow. But it may failed to present our view controller if your rootViewController is presenting other view controller. So set this property is necessary.
 */
- (nonnull instancetype)initWithPlacementId:(nullable NSString *)placementId
                                     unitID:(nonnull NSString *)unitId
              presentingViewController:(nullable UIViewController *)viewController;


/*!
 
 Initialize the native ads manager which is for loading ads with more options.
 
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param autoCacheImage If you pass YES, SDK will download the image resource automatically when you get the campaign. The default is NO.
 @param viewController The UIViewController that will be used to present SKStoreProductViewController
 (iTunes Store product information) or the in-app browser. If not set, it will be the root viewController of your current UIWindow. But it may failed to present our view controller if your rootViewController is presenting other view controller. So set this property is necessary.
 */
- (nonnull instancetype)initWithPlacementId:(nullable NSString *)placementId
                                     unitID:(nonnull NSString *)unitId
                        autoCacheImage:(BOOL)autoCacheImage
              presentingViewController:(nullable UIViewController *)viewController;


/*!
 This method is used to request ads with the token you got previously
 
 @param bidToken    - the token from bid request within MTGBidFramework.
 */
- (void)loadWithBidToken:(nonnull NSString *)bidToken;


/*!
 
 This is a method to associate a MTGCampaign with the UIView you will use to display the native ads.
 
 @param view The UIView you created to render all the native ads data elements.
 @param campaign The campaign you associate with the view.
 
 @discussion The whole area of the UIView will be clickable.
 */
- (void)registerViewForInteraction:(nonnull UIView *)view
                      withCampaign:(nonnull MTGCampaign *)campaign;

/*!
 
 This is a method to disconnect a MTGCampaign with the UIView you used to display the native ads.
 
 @param view The UIView you created to render all the native ads data elements.
 
 */
- (void)unregisterView:(nonnull UIView *)view;

/*!
 
 This is a method to associate a MTGCampaign with the UIView you will use to display the native ads and set clickable areas.
 
 @param view The UIView you created to render all the native ads data elements.
 @param clickableViews An array of UIView you created to render the native ads data element, e.g. CallToAction button, Icon image, which you want to specify as clickable.
 @param campaign The campaign you associate with the view.
 
 */
- (void)registerViewForInteraction:(nonnull UIView *)view
                withClickableViews:(nonnull NSArray *)clickableViews
                      withCampaign:(nonnull MTGCampaign *)campaign;

/*!
 
 This is a method to disconnect a MTGCampaign with the UIView you used to display the native ads.
 
 @param view The UIView you created to render all the native ads data elements.
 @param clickableViews An array of UIView you created to render the native ads data element, e.g. CallToAction button, Icon image, which you want to specify as clickable.
 
 */
- (void)unregisterView:(nonnull UIView *)view clickableViews:(nonnull NSArray *)clickableViews;

/*!
 
 This is a method to clean the cache nativeAd.
 
 */
- (void)cleanAdsCache;

/*!
 
 Set the video display area size.
 
 @param size The display area size.
 
 */
-(void)setVideoViewSize:(CGSize)size;

/*!
 
 Set the video display area size.
 
 @param width The display area width.
 @param height The display area height.
 */
-(void)setVideoViewSizeWithWidth:(CGFloat)width height:(CGFloat)height;


@end



