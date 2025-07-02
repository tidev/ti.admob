/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import <CoreMedia/CoreMedia.h>
#import <FBANVideoViewSwift/FBANVideoViewSwift-Swift.h>
#import <Foundation/Foundation.h>
#import "FBMediaViewVideoRenderer.h"

@class FBAdMediaViewVideoRendererConfig;
@class FBNativeAdDataModel;

NS_ASSUME_NONNULL_BEGIN

@interface FBANAdVideoRenderer : FBMediaViewVideoRenderer <FBANAdMediaViewProvider>

+ (instancetype)new NS_UNAVAILABLE;
- (instancetype)init NS_UNAVAILABLE;

@property (nonatomic, weak) id<FBANAdMediaViewProviderDelegate> delegate;
@property (nonatomic, strong, nullable) FBAdMediaViewVideoRendererConfig *config;
@property (nonatomic, assign) CMTime forcedViewTime;

- (instancetype)initWithDataProvider:(FBNativeAdDataModel *)dataProvider NS_DESIGNATED_INITIALIZER;
- (void)loadMedia;
- (void)start;
- (void)pause;
- (void)resume;
- (void)skip;
- (void)addMediaObserverForInterval:(CMTime)interval queue:(dispatch_queue_t)queue using:(void (^)(CMTime time))block;

@end

NS_ASSUME_NONNULL_END
