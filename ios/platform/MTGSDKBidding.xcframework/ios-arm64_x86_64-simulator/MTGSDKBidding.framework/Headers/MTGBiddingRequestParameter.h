//
//  MTGBiddingRequestParameter.h
//  MTGSDKBidding
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface MTGBiddingRequestParameter : NSObject


@property(nonatomic,copy,readonly)NSString *placementId;

@property(nonatomic,copy,readonly)NSString *unitId;

@property(nonatomic,readonly)NSNumber *basePrice;

@property(nonatomic,assign,readonly) BOOL openRewardPlus;

/**
 Initialize an MTGBiddingRequestParameter object
 @param placementId placementId
 @param unitId unitId
 @param basePrice The optional value provided to this method should be double,the requested bid should not be lower than this price if use this value
 */
- (instancetype)initWithPlacementId:(nullable NSString *)placementId
                             unitId:(nonnull NSString *) unitId
                          basePrice:(nullable NSNumber *)basePrice;

/**
Initialize an MTGBiddingRequestParameter object
@param placementId placementId
@param unitId unitId
@param basePrice The optional value provided to this method should be double,the requested bid should not be lower than this price if use this value
@param openRewardPlus  is used to open RewardPlus for RewardVideo,defalut NO
*/
- (instancetype)initWithPlacementId:(nullable NSString *)placementId
                             unitId:(nonnull NSString *) unitId
                          basePrice:(nullable NSNumber *)basePrice
                     openRewardPlus:(BOOL)openRewardPlus;
@end

NS_ASSUME_NONNULL_END
