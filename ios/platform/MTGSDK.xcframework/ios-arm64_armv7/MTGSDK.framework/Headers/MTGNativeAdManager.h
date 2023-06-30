//
//  MTGNativeAdManager.h
//  MTGSDK
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import "MTGNativeAd.h"


#pragma mark - MTGNativeAdManager

@interface MTGNativeAdManager : NSObject

/*!
 @property
 
 @abstract The delegate
 
 @discussion All delegate method will be called in main thread.
 */
@property (nonatomic, weak, nullable) id <MTGNativeAdManagerDelegate> delegate;

/*!
 @property
 
 @discussion Show the  loading view when to click on ads.
 The default is yes
 */
@property (nonatomic, assign) BOOL showLoadingView;

/*!
 @property
 
 @discussion DEPRECATED_ATTRIBUTE
 Mintegral support configuration： https://www.mintegral.net
 */
@property (nonatomic, readonly) BOOL videoSupport DEPRECATED_ATTRIBUTE;

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
 
 Initialize the native ads manager which is for loading ads with more options.
 
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param templates This array contains objects of MTGTemplate. See more detail in definition of MTGTemplate.
 @param autoCacheImage If you pass YES, SDK will download the image resource automatically when you get the campaign. The default is NO.
 @param adCategory Decide what kind of ads you want to retrieve. Games, apps or all of them. The default is All.
 @param viewController The UIViewController that will be used to present SKStoreProductViewController
 (iTunes Store product information) or the in-app browser. If not set, it will be the root viewController of your current UIWindow. But it may failed to present our view controller if your rootViewController is presenting other view controller. So set this property is necessary.
 */
- (nonnull instancetype)initWithPlacementId:(nullable NSString *)placementId
                                     unitID:(nonnull NSString *)unitId
                         supportedTemplates:(nullable NSArray *)templates
                             autoCacheImage:(BOOL)autoCacheImage
                                 adCategory:(MTGAdCategory)adCategory
                   presentingViewController:(nullable UIViewController *)viewController;

/*!
 
 The method that kicks off the loading of ads. It may be called again in the future to refresh the ads manually.
 
 @discussion It only works if you init the manager by the 2 method above.
 */
- (void)loadAds;


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



/*!
 
 The method that kicks off the loading of frames. It may be called again in the future to refresh the frames manually.
 
 @discussion It only works if you init the manager by the the method above.
 
 @deprecated This method has been deprecated.
 */
- (void)loadFrames DEPRECATED_ATTRIBUTE;


/*!
 Initialize the native ads manager which is for loading frames (MTGFrame).
 
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param fbPlacementId The Facebook PlacementID is used to request ads from Facebook. You can also set the placementID in our portal. The ID you set in our web portal will replace the ID you set here in future.
 @param frameNum The number of frame you would like the native ad manager to retrieve.
 @param templates This array contains objects of MTGTemplate. See more detail in definition of MTGTemplate.
 @param autoCacheImage If you pass YES, SDK will download the image resource automatically when you get the campaign. The default is NO.
 @param adCategory Decide what kind of ads you want to retrieve. Games, apps or all of them. The default is All.
 @param viewController The UIViewController that will be used to present SKStoreProductViewController
 (iTunes Store product information) or the in-app browser. If not set, it will be the root viewController of your current UIWindow. But it may failed to present our view controller if your rootViewController is presenting other view controller. So set this property is necessary.
 
 @discussion It's different with the method initWithUnitID:fbPlacementId:forNumAdsRequested:presentingViewController: We will return arrays of MTGFrame rather than MTGCampaign to you. A MTGFrame may contain multiple MTGCampaigns. See more detail in MTGFrame.
 
 @deprecated This method has been deprecated.
 */
- (nonnull instancetype)initWithPlacementId:(nullable NSString *)placementId
                                     unitID:(nonnull NSString *)unitId
                              fbPlacementId:(nullable NSString *)fbPlacementId
                                   frameNum:(NSUInteger)frameNum
                         supportedTemplates:(nullable NSArray *)templates
                             autoCacheImage:(BOOL)autoCacheImage
                                 adCategory:(MTGAdCategory)adCategory
                   presentingViewController:(nullable UIViewController *)viewController DEPRECATED_ATTRIBUTE;


/*!
 
 Initialize the native ads manager which is for loading ads. (MTGCampaign)
 
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param fbPlacementId The Facebook PlacementID is used to request ads from Facebook. You can also set the placementID in our portal. The ID you set in our web portal will replace the ID you set here in future.
 @param numAdsRequested The number of ads you would like the native ad manager to retrieve. Max number is 10. If you pass any number bigger than 10, it will be reset to 10.
 @param viewController The UIViewController that will be used to present SKStoreProductViewController
 (iTunes Store product information) or the in-app browser. If not set, it will be the root viewController of your current UIWindow. But it may failed to present our view controller if your rootViewController is presenting other view controller. So set this property is necessary.
 */
- (nonnull instancetype)initWithPlacementId:(nullable NSString *)placementId
                                     unitID:(nonnull NSString *)unitId
                              fbPlacementId:(nullable NSString *)fbPlacementId
                         forNumAdsRequested:(NSUInteger)numAdsRequested
                   presentingViewController:(nullable UIViewController *)viewController DEPRECATED_ATTRIBUTE;

/*!
 
 Initialize the native ads manager which is for loading ads. (MTGCampaign)
 
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param fbPlacementId The Facebook PlacementID is used to request ads from Facebook. You can also set the placementID in our portal. The ID you set in our web portal will replace the ID you set here in future.
 @param videoSupport DEPRECATED_ATTRIBUTE Mintegral support configuration： https://www.mintegral.net
 @param numAdsRequested The number of ads you would like the native ad manager to retrieve. Max number is 10. If you pass any number bigger than 10, it will be reset to 10.
 @param viewController The UIViewController that will be used to present SKStoreProductViewController
 (iTunes Store product information) or the in-app browser. If not set, it will be the root viewController of your current UIWindow. But it may failed to present our view controller if your rootViewController is presenting other view controller. So set this property is necessary.
 */
- (nonnull instancetype)initWithPlacementId:(nullable NSString *)placementId
                                     unitID:(nonnull NSString *)unitId
                              fbPlacementId:(nullable NSString *)fbPlacementId
                               videoSupport:(BOOL)videoSupport
                         forNumAdsRequested:(NSUInteger)numAdsRequested
                   presentingViewController:(nullable UIViewController *)viewController DEPRECATED_ATTRIBUTE;

/*!
 
 Initialize the native ads manager which is for loading ads with more options.
 
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param fbPlacementId The Facebook PlacementID is used to request ads from Facebook. You can also set the placementID in our portal. The ID you set in our web portal will replace the ID you set here in future.
 @param templates This array contains objects of MTGTemplate. See more detail in definition of MTGTemplate.
 @param autoCacheImage If you pass YES, SDK will download the image resource automatically when you get the campaign. The default is NO.
 @param adCategory Decide what kind of ads you want to retrieve. Games, apps or all of them. The default is All.
 @param viewController The UIViewController that will be used to present SKStoreProductViewController
 (iTunes Store product information) or the in-app browser. If not set, it will be the root viewController of your current UIWindow. But it may failed to present our view controller if your rootViewController is presenting other view controller. So set this property is necessary.
 */
- (nonnull instancetype)initWithPlacementId:(nullable NSString *)placementId
                                     unitID:(nonnull NSString *)unitId
                              fbPlacementId:(nullable NSString *)fbPlacementId
                         supportedTemplates:(nullable NSArray *)templates
                             autoCacheImage:(BOOL)autoCacheImage
                                 adCategory:(MTGAdCategory)adCategory
                   presentingViewController:(nullable UIViewController *)viewController DEPRECATED_ATTRIBUTE;



@end

