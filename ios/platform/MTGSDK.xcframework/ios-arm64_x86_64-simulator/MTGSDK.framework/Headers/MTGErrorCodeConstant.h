//
//  MTGErrorCodeConstant.h
//  MTGSDK
//
//  Copyright © 2017年 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>
NS_ASSUME_NONNULL_BEGIN

extern NSString * const kMTGErrorDomain;

typedef NS_ENUM (NSInteger, MTGErrorCode) {

    kMTGErrorCodeUnknownError                                   = 129300060,

    kMTGErrorCodeRewardVideoFailedToLoadVideoData               = 129300061,
    kMTGErrorCodeRewardVideoFailedToLoadPlayable                = 129300062,
    kMTGErrorCodeRewardVideoFailedToLoadTemplateImage           = 129300063,
    kMTGErrorCodeRewardVideoFailedToLoadPlayableURLFailed       = 129300064,
    kMTGErrorCodeRewardVideoFailedToLoadPlayableURLReadyTimeOut = 129300065,
    kMTGErrorCodeRewardVideoFailedToLoadPlayableURLReadyNO      = 129300066,
    kMTGErrorCodeRewardVideoFailedToLoadPlayableURLInvalid      = 129300067,
    kMTGErrorCodeRewardVideoFailedToLoadMd5Invalid              = 129300068,
    kMTGErrorCodeRewardVideoFailedToSettingInvalid              = 129300069,
    KMTGErrorCodeEmptyUnitId                                    = 129300001,
    KMTGErrorCodeEmptyBidToken                                  = 129300002,

    kMTGErrorCodeNoAds                                          = 12930001,
    kMTGErrorCodeConnectionLost                                 = 12930002,
    kMTGErrorCodeURLisEmpty                                     = 12930003,
    kMTGErrorCodeNoAdsAvailableToPlay                           = 12930004,
    kMTGErrorCodeFailedToPlay                                   = 12930005,
    kMTGErrorCodeFailedToLoad                                   = 12930006,
    kMTGErrorCodeFailedToShow                                   = 12930007,
    kMTGErrorCodeFailedToShowCbp                                = 12930008,
    kMTGErrorCodeDailyLimit                                     = 12930009,
    kMTGErrorCodeLoadAdsTimeOut                                 = 12930010,
    kMTGErrorCodeMaterialLoadFailed                             = 12930011,
    kMTGErrorCodeOfferExpired                                   = 12930012,
    kMTGErrorCodeImageURLisEmpty                                = 12930013,

    
    kMTGErrorCodeNoSupportPopupWindow                           = 12940001,
    
    kMTGErrorCodeFailedDiskIO                                   = 12950001,
    kMTGErrorCodeSocketIO                                   = 12960001, // tcp 相关

};

@interface MTGErrorCodeConstant : NSObject

@end
NS_ASSUME_NONNULL_END
