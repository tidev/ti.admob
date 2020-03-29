//
//  GADResponseInfo.h
//  Google Mobile Ads SDK
//
//  Copyright 2019 Google LLC. All rights reserved.
//

#import <Foundation/Foundation.h>

/// Ad network class name for ads returned from Google's ad network.
extern NSString *_Nonnull const GADGoogleAdNetworkClassName;

/// Ad network class name for custom event ads.
extern NSString *_Nonnull const GADCustomEventAdNetworkClassName;

/// Key into NSError.userInfo mapping to a GADResponseInfo object. When ads fail to load, errors
/// returned contain an instance of GADResponseInfo.
extern NSString *_Nonnull GADErrorUserInfoKeyResponseInfo;

/// Information about a response to an ad request.
@interface GADResponseInfo : NSObject

/// Unique identifier of the ad response.
@property(nonatomic, readonly, nullable) NSString *responseIdentifier;

/// A class name that identifies the ad network that returned the ad. Nil if no ad was returned.
@property(nonatomic, readonly, nullable) NSString *adNetworkClassName;

@end
