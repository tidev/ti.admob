//
//  MTGUserInfo.h
//  MTGSDK
//
//  Copyright © 2017年 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>


typedef NS_ENUM(NSInteger, MTGUserPrivateType) {
    MTGUserPrivateType_ALL         = 0,
    MTGUserPrivateType_GeneralData = 1,
    MTGUserPrivateType_DeviceId    = 2,
    MTGUserPrivateType_Gps         = 3,
    MTGUserPrivateType_Other       = 4
};


@interface MTGUserPrivateTypeInfo : NSObject

@property (nonatomic,assign)  BOOL isGeneralDataAllowed;
@property (nonatomic,assign)  BOOL isDeviceIdAllowed;
@property (nonatomic,assign)  BOOL isGpsAllowed;
@property (nonatomic,assign)  BOOL isOtherAllowed;

@end

typedef void (^MTGUserPrivateInfoTipsResultBlock)(MTGUserPrivateTypeInfo *userPrivateTypeInfo,NSError *error);


typedef NS_ENUM(NSUInteger, MTGGender) {
    MTGGender_Unknown = 0,
    MTGGender_Man     = 1,
    MTGGender_Woman   = 2,
};

typedef NS_ENUM(NSUInteger, MTGUserPayType) {
    MTGUserPayType_Unpaid  = 0,
    MTGUserPayType_Pay     = 1,
    MTGUserPayType_Unknown = 2,
};

@interface MTGUserInfo : NSObject

@property (nonatomic,assign) MTGGender gender;
@property (nonatomic,assign) NSInteger age;
@property (nonatomic,assign) MTGUserPayType pay;
@property (nonatomic,  copy) NSString *custom;
@property (nonatomic,  copy) NSString *longitude;
@property (nonatomic,  copy) NSString *latitude;

@end
