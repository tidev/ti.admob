//
//  MTGRewardAdInfo.h
//  MTGSDK
//

#import <Foundation/Foundation.h>

@interface MTGRewardAdInfo : NSObject

/**
 *  The ID of the reward as defind on Self Service
 */
@property (nonatomic, copy  ) NSString  *rewardId;

/**
 *  The reward name as defined on Self Service
 */
@property (nonatomic, copy  ) NSString  *rewardName;

/**
 *  Amount of reward type given to the user
 */
@property (nonatomic, assign) NSInteger rewardAmount;


@end
