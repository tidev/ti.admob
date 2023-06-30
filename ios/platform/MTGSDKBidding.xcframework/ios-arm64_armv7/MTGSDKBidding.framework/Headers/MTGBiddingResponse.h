//
//  MTGBiddingResponse.h
//  MTGBiddingResponse
//
//  Copyright © 2019 Mintegral. All rights reserved.
//


#import "MTGBiddingHeader.h"



@interface MTGBiddingResponse :NSObject


@property (nonatomic,assign,readonly) BOOL success;
@property (nonatomic,strong,readonly) NSError *error;




@property (nonatomic,assign,readonly) double price;

/**
 Default is USD
 */
@property (nonatomic,copy,readonly) NSString *currency;

/**
 You will need to use this value when you request the ads
 */
@property (nonatomic,copy,readonly) NSString *bidToken;



-(void)notifyWin;

-(void)notifyLoss:(MTGBidLossedReasonCode)reasonCode;


@end


