//
//  MTGAdSize.h
//  MTGSDK
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN
typedef NS_ENUM(NSInteger,MTGBannerSizeType) {
    /*Represents the fixed banner ad size - 320pt by 50pt.*/
    MTGStandardBannerType320x50,
    
    /*Represents the fixed banner ad size - 320pt by 90pt.*/
    MTGLargeBannerType320x90,
    
    /*Represents the fixed banner ad size - 300pt by 250pt.*/
    MTGMediumRectangularBanner300x250,
    
    /*if device is iphone,Represents the fixed banner ad size - 320pt by 50pt;
      if device is ipad,  Represents the fixed banner ad size - 728pt by 90pt*/
    MTGSmartBannerType
};

@interface MTGAdSize : NSObject

+(CGSize)getSizeBySizeType:(MTGBannerSizeType)sizeType;

@end

NS_ASSUME_NONNULL_END
