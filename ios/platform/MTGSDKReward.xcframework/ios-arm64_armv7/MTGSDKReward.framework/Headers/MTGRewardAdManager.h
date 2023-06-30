//
//  MTGRewardAdManager.h
//  MTGSDK
//

#import <Foundation/Foundation.h>
#import "MTGRewardAd.h"

@interface MTGRewardAdManager : NSObject

/* Play the video mute or not in the beginning, defult is NO. */
@property (nonatomic, assign) BOOL  playVideoMute;

/**
* this method is used to open RewardPlus for RewardVideo
* please set this before loadVideo
* openRewardPlus default false
*/
@property (nonatomic,assign) BOOL openRewardPlus;

/**
 * The shared instance of the video.
 *
 * @return The video singleton.
 */
+ (nonnull instancetype)sharedInstance;

/**
*  Called when load the video
 
*  @param placementId   - the placementId string of the Ad that display.
*  @param unitId      - the unitId string of the Ad that was loaded.
*  @param delegate    - reference to the object that implements MTGRewardAdLoadDelegate protocol; will receive load events for the given unitId.
*/
- (void)loadVideoWithPlacementId:(nullable NSString *)placementId
                          unitId:(nonnull NSString *)unitId
                        delegate:(nullable id <MTGRewardAdLoadDelegate>)delegate;

/**
*  Called when show the video
*
*  @param placementId         - the placementId string of the Ad that display.
*  @param unitId         - the unitId string of the Ad that display.
*  @param rewardId       - the reward info you can set in mintegral portal
*  @param userId       - The user's unique identifier in your system
*  @param delegate       - reference to the object that implements MTGRewardAdShowDelegate protocol; will receive show events for the given unitId.
*  @param viewController - UIViewController that shouold be set as the root view controller for the ad
*/
- (void)showVideoWithPlacementId:(nullable NSString *)placementId
                          unitId:(nonnull NSString *)unitId
                    withRewardId:(nullable NSString *)rewardId
                          userId:(nullable NSString *)userId
                        delegate:(nullable id <MTGRewardAdShowDelegate>)delegate
                  viewController:(nonnull UIViewController*)viewController;

/**
*  Called when show the video
*
*  @param placementId         - the placementId string of the Ad that display.
*  @param unitId         - the unitId string of the Ad that display.
*  @param userId       - The user's unique identifier in your system
*  @param delegate       - reference to the object that implements MTGRewardAdShowDelegate protocol; will receive show events for the given unitId.
*  @param viewController - UIViewController that shouold be set as the root view controller for the ad
*/
- (void)showVideoWithPlacementId:(nullable NSString *)placementId
                          unitId:(nonnull NSString *)unitId
                          userId:(nullable NSString *)userId
                        delegate:(nullable id <MTGRewardAdShowDelegate>)delegate
                  viewController:(nonnull UIViewController*)viewController;

/**
*  Called when show the video
*
*  @param placementId         - the placementId string of the Ad that display.
*  @param unitId         - the unitId string of the Ad that display.
*  @param userId       - The user's unique identifier in your system
*  @param delegate       - reference to the object that implements MTGRewardAdShowDelegate protocol; will receive show events for the given unitId.
*  @param userExtra    Optional userExtra string to include in the  reward server-to-server callback. 
*  @param viewController - UIViewController that shouold be set as the root view controller for the ad
*/
- (void)showVideoWithPlacementId:(nullable NSString *)placementId
                          unitId:(nonnull NSString *)unitId
                          userId:(nullable NSString *)userId
                       userExtra:(nullable NSString *)userExtra
                        delegate:(nullable id <MTGRewardAdShowDelegate>)delegate
                  viewController:(nonnull UIViewController*)viewController;

/**
 *  Will return whether the given unitId is loaded and ready to be shown.
 *
 *  @param placementId - adPositionId value in Self Service
 *  @param unitId - adPositionId value in Self Service
 *
 *  @return - YES if the unitId is loaded and ready to be shown, otherwise NO.
 */
- (BOOL)isVideoReadyToPlayWithPlacementId:(nullable NSString *)placementId unitId:(nonnull NSString *)unitId;

/**
 *  Clean all the video file cache from the disk.
 */
- (void)cleanAllVideoFileCache;

/**
* get the id of this request ad,call  after onAdLoadSuccess.
*/
- (NSString *_Nullable)getRequestIdWithUnitId:(nonnull NSString *)unitId;


/**
*  Set  alertView text,if you want to change the alertView text.
*
* @param title  alert title
* @param content    alertcontent
* @param confirmText    confirm button text
* @param cancelText     cancel button text
* @param unitId     unitId

 NOTE:called before loadAd
*/
- (void)setAlertWithTitle:(NSString *_Nullable)title
                  content:(NSString *_Nullable)content
              confirmText:(NSString *_Nullable)confirmText
               cancelText:(NSString *_Nullable)cancelText
                   unitId:(NSString *_Nullable)unitId;
@end
