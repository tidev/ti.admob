//
//  MTGBiddingHeader.h
//  MTGSDKBidding
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#ifndef MTGBiddingHeader_h
#define MTGBiddingHeader_h


typedef NS_ENUM (NSInteger, MTGBidErrorCode) {
    
    kMTGBidErrorCodeUnknownError                             = 12930001,
    kMTGBidErrorCodeInvalidInput                             = 12930002,
    kMTGBidErrorCodeConnectionLost                           = 12930003,
    kMTGBidErrorCodeResponseParametersInvalid                = 12930004,
    
    
};



typedef NS_ENUM (NSInteger, MTGBidLossedReasonCode) {
    
    MTGBidLossedReasonCodeLowPrice                           = 1,
    MTGBidLossedReasonCodeBidTimeout                         = 2,
    MTGBidLossedReasonCodeWonNotShow                         = 3,
    
};


#endif /* MTGBiddingHeader_h */
