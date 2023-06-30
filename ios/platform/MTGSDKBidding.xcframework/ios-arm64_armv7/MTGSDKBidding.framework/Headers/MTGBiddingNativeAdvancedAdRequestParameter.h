//
//  MTGBiddingNativeAdvancedAdRequestParameter.h
//  MTGSDKBidding
//
//  Copyright © 2020 Mintegral. All rights reserved.
//

#import "MTGBiddingRequestParameter.h"

NS_ASSUME_NONNULL_BEGIN

@interface MTGBiddingNativeAdvancedAdRequestParameter : MTGBiddingRequestParameter
/**
    unit size
 */
@property(nonatomic,assign,readonly)CGSize unitSize;

/**
  Initialize an MTGBiddingNativeAdvanceAdRequestParameter object
  @param unitId unitId
  @param basePrice The optional value provided to this method should be double,the requested bid should not be lower than this price if use this value
  @param unitSize  unit size
 */
- (instancetype)initWithPlacementId:(nullable NSString *)placementId
                             unitId:(nonnull NSString *) unitId
                          basePrice:(nullable NSNumber *)basePrice
                           unitSize:(CGSize)unitSize;
@end

NS_ASSUME_NONNULL_END
