//
//  IMODTargetingService.h
//  InMobiSDK
//
//  Copyright © 2022 InMobi. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Class to integrate On-Device Targeting Service
 */

@interface IMODTargetingService : NSObject

/**
 * Update user's email to IMODTargetingService in md5 hashed form.
 * This method is used for identifying the user for user targeted ads.
 * Expected to be called on every user login.
 * @param hashId Represents user email in md5 hashed format
 */
+ (void)push:(NSString *)hashId;

@end

NS_ASSUME_NONNULL_END
