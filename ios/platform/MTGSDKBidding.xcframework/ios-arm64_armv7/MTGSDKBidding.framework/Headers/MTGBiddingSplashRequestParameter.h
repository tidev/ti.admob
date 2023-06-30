//
//  MTGBiddingSplashRequestParameter.h
//  MTGSDKBidding
//
//  Copyright © 2020 Mintegral. All rights reserved.
//

#import "MTGBiddingRequestParameter.h"

#if __has_include(<MTGSDK/MTGSDK.h>)
    #import <MTGSDK/MTGBool.h>
#else
    #import "MTGBool.h"
#endif
NS_ASSUME_NONNULL_BEGIN

@interface MTGBiddingSplashRequestParameter : MTGBiddingRequestParameter

/* corresponding preload mark when you initialize the object. */
@property (nonatomic, readonly, assign) BOOL preload;

/* corresponding customViewSize when you initialize the object. */
@property (nonatomic, readonly, assign) CGSize customViewSize;

/* corresponding preferred orientation when you initialize the object. */
@property (nonatomic, readonly, assign) NSInteger preferredOrientation;

/**
 Initialize an MTGBiddingSplashRequestParameter object
 @param unitId unitId string.
 @param basePrice The optional value provided to this method should be double,the requested bid should not be lower than this price if this value provided.
 @param preload whether or not this load is a preload. If preload marked to YES, you should call `[MTGSplashAD preloadWithBidToken:]` to load ad.
 */
- (instancetype)initWithPlacementId:(nullable NSString *)placementId
                             unitId:(nonnull NSString *) unitId
                          basePrice:(nullable NSNumber *)basePrice
                            preload:(BOOL)preload;

/**
 Initialize an MTGBiddingSplashRequestParameter object
 @param unitId unitId
 @param basePrice The optional value provided to this method should be double,the requested bid should not be lower than this price if this value provided.
 @param preload whether or not this load is a preload. If preload marked to YES, you should call `[MTGSplashAD preloadWithBidToken:]` to load ad.
 @param customViewSize if you want to display your own custom view on the ad area, you should pass the corresponding CGSize of your custome view.
 @param preferredOrientation specify preferred orientation to show the ad.
 
 @note  1. when you showing ad on the portrait mode, the height of the customViewSize should not           greater than 25% of the device's height.
        2. when you showing ad on the landscape mode, the width of the customViewSize should not greater than 25% of the device's width.
 */
- (instancetype)initWithPlacementId:(nullable NSString *)placementId
                             unitId:(NSString *)unitId
                     basePrice:(NSNumber *)basePrice
                       preload:(BOOL)preload
                customViewSize:(CGSize)customViewSize
          preferredOrientation:(MTGInterfaceOrientation)preferredOrientation;


@end

NS_ASSUME_NONNULL_END
