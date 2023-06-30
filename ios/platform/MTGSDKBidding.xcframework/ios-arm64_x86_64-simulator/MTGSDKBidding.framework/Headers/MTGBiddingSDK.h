//
//  MTGBiddingSDK.h
//  MTGSDKBidding
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>



#define MTGBiddingSDKVersion @"7.3.8"


typedef NS_ENUM(NSInteger,MintegralAdType) {
    MintegralNativeAd = 42,
    MintegralRewardVideoAd = 94,
    MintegralBannerAd = 296,
    MintegralSplashAd = 297,
    MintegralIntersitialAd = 287,
    MintegralNativeAdVanceAd = 298
};

@interface MTGBiddingSDK : NSObject

/* BuyerUID is required when you decide to request a bid response on your own server. */
+ (NSString *)buyerUID;

/*
 BuyerUID is required when you decide to request a bid response on your own server.
 unitID dictionary:your ad unit id.
 */
+ (NSString *)buyerUIDWithUnitID:(NSString *)unitID;

/*
  BuyerUID is required when you decide to request a bid response on your own server.
  Parameter dictionary:
  e.g:
  @{
    @"placementId":@"your ad placement id",
    @"unitId":@"your ad unit id",
    @"adType":@(MintegralAdType)
   }
 */
+ (NSString *)buyerUIDWithDictionary:(NSDictionary *)dictionary;

@end

