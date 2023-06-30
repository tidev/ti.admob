//
//  MTGSDK.h
//  MTGSDK
//



#import <UIKit/UIKit.h>
#import "MTGNativeAdManager.h"
#import "MTGBidNativeAdManager.h"
#import "MTGCampaign.h"
#import "MTGTemplate.h"
#import "MTGFrame.h"
#import "MTGMediaView.h"
#import "MTGUserInfo.h"
#import "MTGBool.h"

#define MTGSDKVersion @"7.3.8"



@interface MTGSDK : NSObject


/**
* The version of the SDK.
*
* @return The current version of the SDK.
*/
+(NSString *_Nonnull)sdkVersion;

/**
 * The shared instance of the SDK.
 *
 * @return The SDK singleton.
 */
+ (nonnull instancetype)sharedInstance;

/**
 * Set the AppID and ApiKey.
 *  This must be called after set the authorization of user privacy information collection if you need to keep GDPR terms.
 *  This must be called before any ads are requested .
 *
 * @param appID  T application Id registered on the our portal.
 * @param apiKey The API Key generated on the our Portal.
 */
- (void)setAppID:(nonnull NSString *)appID ApiKey:(nonnull NSString *)apiKey;

@property (nonatomic, assign) BOOL autoSetAudioCategory;

/**
 Set user GDPR authorization information
 
 Set YES to indicate the user's data will be collected otherwise NO. Default to be YES.
 
@abstract According to the GDPR, set method of this property must be called before "setAppID: ApiKey:", or by default will collect user's information.
 @Attention Do not mix the usage of `setConsentStatus:` and `setUserPrivateInfoType:agree` simultaneously in your app.
 */
@property (nonatomic, assign) BOOL consentStatus;

/// Set the COPPA status of the user. YES means follow the coppa rules, NO means no need to follow the coppa rules, default is Unknown (depend on your app's coppa setting on the dev setting page).
@property (nonatomic, assign) MTGBool coppa;

/// Set user GDPR authorization IDFV information, YES means disable the collection of idfv. Default to be NO.
@property (nonatomic, assign) BOOL disableIDFV;

/// Set this to NO to disable SDK to call system framework to collect device id(include idfa and idfv), and device id can be passed to SDK by calling "setDeviceIdfa:" and "setDeviceIdfv:". Default to be YES.
/// Attention: this mthdod must be called before "setAppID: ApiKey:"
@property (nonatomic, assign) BOOL canCallingDeviceIDApi;

/// Pass idfa to SDK when `canCallingDeviceIDApi` is NO.
/// @param idfa collected from your side by calling `[ASIdentifierManager sharedManager].advertisingIdentifier.UUIDString`.
- (void)setDeviceIdfa:(nonnull NSString *)idfa;

/// Pass idfv to SDK when `canCallingDeviceIDApi` is NO.
/// @param idfv collected from your side by calling `[UIDevice currentDevice].identifierForVendor.UUIDString`.
- (void)setDeviceIdfv:(nonnull NSString *)idfv;

/**
 If set to YES, the server will not display personalized ads based on the user's personal information
 When receiving the user's request, and will not synchronize the user's information to other third-party partners.
 Default is NO
 */
@property (nonatomic, assign) BOOL doNotTrackStatus;

- (void)setUserInfo:(nonnull MTGUserInfo *)userInfo;

/**
 *
 @method
 
 @abstract The method that kicks off the preloading of native ads. It may be called again in the future to refresh the ads manually.
 
 @param placementId The id of the ad placement. You can create your placement id from our Portal.
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param templates This array contains objects of MTGTemplate. See more detail in definition of MTGTemplate.
 @param autoCacheImage If you pass YES, SDK will download the image resource automatically when you get the campaign.
 @param adCategory Decide what kind of ads you want to retrieve. Games, apps or all of them. The default is All.
 */
- (void)preloadNativeAdsWithPlacementId:(nullable NSString *)placementId
                                 unitId:(nonnull NSString *)unitId
                     supportedTemplates:(nullable NSArray *)templates
                         autoCacheImage:(BOOL)autoCacheImage
                             adCategory:(MTGAdCategory)adCategory;

/**
 *
 @method Set user GDPR authorization information
 
 @abstract According to the GDPR, this method must be called before "setAppID: ApiKey:", or by default will collect user information;
 
 @param type The type of the data that you want to configure.
 
 @param agree whether this type of data should be collect.
 */
- (void)setUserPrivateInfoType:(MTGUserPrivateType)type agree:(BOOL)agree DEPRECATED_MSG_ATTRIBUTE("Use `[MTGSDK sharedInstance] setConsentStatus:` instead");

/**
 *
 @method
 
 @abstract You can get all status for current 'MTGUserPrivateType' by using this method.
 */
- (MTGUserPrivateTypeInfo *_Nonnull)userPrivateInfo DEPRECATED_MSG_ATTRIBUTE("Use `[MTGSDK sharedInstance] consentStatus` instead");

/**
 *
 @method
 
 @abstract The method that kicks off the preloading of native ads. It may be called again in the future to refresh the ads manually.
 
 @param placementId The id of the ad placement. You can create your placement id from our Portal.
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param fbPlacementId The Facebook PlacementID is used to request ads from Facebook. You can also set the placementID in our portal. The ID you set in our web portal will replace the ID you set here in future.
 @param numAdsRequested The number of ads you would like to preload. Max number is 10. If you pass any number bigger than 10, it will be reset to 10.
 */
- (void)preloadNativeAdsWithPlacementId:(nullable NSString *)placementId
                                 unitId:(nonnull NSString *)unitId
                          fbPlacementId:(nullable NSString *)fbPlacementId
                     forNumAdsRequested:(NSUInteger)numAdsRequested DEPRECATED_ATTRIBUTE;

/**
 *
 @method
 
 @abstract The method that kicks off the preloading of native ads. It may be called again in the future to refresh the ads manually.
 
 @param placementId The id of the ad placement. You can create your placement id from our Portal.
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param fbPlacementId The Facebook PlacementID is used to request ads from Facebook. You can also set the placementID in our portal. The ID you set in our web portal will replace the ID you set here in future.
 @param videoSupport If the support video ads, set videoSupport  to yes.
 @param numAdsRequested The number of ads you would like to preload. Max number is 10. If you pass any number bigger than 10, it will be reset to 10.
 */
- (void)preloadNativeAdsWithPlacementId:(nullable NSString *)placementId
                                 unitId:(nonnull NSString *)unitId
                          fbPlacementId:(nullable NSString *)fbPlacementId
                           videoSupport:(BOOL)videoSupport
                     forNumAdsRequested:(NSUInteger)numAdsRequested DEPRECATED_ATTRIBUTE;

/**
 *
 @method
 
 @abstract The method that kicks off the preloading of native ads. It may be called again in the future to refresh the ads manually.
 
 @param placementId The id of the ad placement. You can create your placement id from our Portal.
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param fbPlacementId The Facebook PlacementID is used to request ads from Facebook. You can also set the placementID in our portal. The ID you set in our web portal will replace the ID you set here in future.
 @param templates This array contains objects of MTGTemplate. See more detail in definition of MTGTemplate.
 @param autoCacheImage If you pass YES, SDK will download the image resource automatically when you get the campaign.
 @param adCategory Decide what kind of ads you want to retrieve. Games, apps or all of them. The default is All.
 */
- (void)preloadNativeAdsWithPlacementId:(nullable NSString *)placementId
                                 unitId:(nonnull NSString *)unitId
                          fbPlacementId:(nullable NSString *)fbPlacementId
                     supportedTemplates:(nullable NSArray *)templates
                         autoCacheImage:(BOOL)autoCacheImage
                             adCategory:(MTGAdCategory)adCategory DEPRECATED_ATTRIBUTE;

/**
 *
 @method
 
 @abstract The method that kicks off the preloading of native frames. It may be called again in the future to refresh the frames manually.
 
 @param placementId The id of the ad placement. You can create your placement id from our Portal.
 @param unitId The id of the ad unit. You can create your unit id from our Portal.
 @param fbPlacementId The Facebook PlacementID is used to request ads from Facebook. You can also set the placementID in our portal. The ID you set in our web portal will replace the ID you set here in future.
 @param templates This array contains objects of MTGTemplate. See more detail in definition of MTGTemplate.
 @param autoCacheImage If you pass YES, SDK will download the image resource automatically when you get the campaign.
 @param adCategory Decide what kind of ads you want to retrieve. Games, apps or all of them. The default is All.
 @param frameNum The number of frames you would like to preload. Max number is 10. If you pass any number bigger than 10, it will be reset to 10.
 */
- (void)preloadNativeFramesWithPlacementId:(nullable NSString *)placementId
                                    unitId:(nonnull NSString *)unitId
                             fbPlacementId:(nullable NSString *)fbPlacementId
                   supportedFrameTemplates:(nullable NSArray *)templates
                            autoCacheImage:(BOOL)autoCacheImage
                                adCategory:(MTGAdCategory)adCategory
                                  frameNum:(NSUInteger)frameNum DEPRECATED_ATTRIBUTE;


@end
