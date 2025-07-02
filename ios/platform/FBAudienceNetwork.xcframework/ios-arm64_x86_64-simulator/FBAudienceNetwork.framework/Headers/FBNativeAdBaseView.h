/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import <UIKit/UIKit.h>

#import <FBAudienceNetwork/FBNativeAd.h>
#import "FBAdDefines.h"

NS_ASSUME_NONNULL_BEGIN

@class FBNativeAdViewAttributes;

/**
 The FBNativeAdBaseView creates prebuilt native ad base template views and manages native ads.
 */
FB_CLASS_EXPORT
@interface FBNativeAdBaseView : UIView

/**
 A view controller that is used to present modal content. If nil, the view searches for a view controller.
 */
@property (nonatomic, weak, nullable) UIViewController *rootViewController;

@end

NS_ASSUME_NONNULL_END
