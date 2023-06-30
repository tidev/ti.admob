//
//  MTGBiddingRequest.h
//  MTGSDKBidding
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "MTGBiddingHeader.h"
#import "MTGBiddingResponse.h"
#import "MTGBiddingRequestParameter.h"

NS_ASSUME_NONNULL_BEGIN

@interface MTGBiddingRequest : NSObject

/**
 Get Mintegral Bid for current ad unit

 @param basePrice  The optional value provided to this method should be double,the requested bid should not be lower than this price if use this value
 */

+(void)getBidWithUnitId:(nonnull NSString *)unitId basePrice:(nullable NSNumber *)basePrice completionHandler:(void(^)(MTGBiddingResponse *bidResponse))completionHandler DEPRECATED_MSG_ATTRIBUTE("Use`[MTGBiddingRequest getBidWithRequestParameter:completionHandler` instead");

/**
  Get Mintegral Bid for current ad unit
  @param requestParameter
 
  NOTE:requestParameter --You need to construct an MTGBiddingRequestParameter object or his subclass object.
       If it is banner ad, you need to construct an MTGBiddingBannerRequestParameter object.
  */
+(void)getBidWithRequestParameter:(nonnull __kindof MTGBiddingRequestParameter *)requestParameter completionHandler:(void(^)(MTGBiddingResponse *bidResponse))completionHandler;

@end

NS_ASSUME_NONNULL_END
