/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobViewProxy.h"
#import "TiAdmobTypes.h"
#import "TiApp.h"
#import "TiUtils.h"

@implementation TiAdmobViewProxy

- (TiAdmobView *)admobView
{
  return (TiAdmobView *)[self view];
}

- (void)receive:(id)unused
{
  [[self admobView] initialize];
}

- (void)showRewardedVideo:(id)args {
  if ([[GADRewardBasedVideoAd sharedInstance] isReady]) {
    [GADRewardBasedVideoAd.sharedInstance presentFromRootViewController:TiApp.app.controller.topPresentedController];
  }
}

- (void)loadRewardedVideo:(id)adUnitId
{
  id adType = [self valueForKey:@"adType"];
  if (adType != nil && [TiUtils boolValue:adType def:TiAdmobAdTypeBanner] != TiAdmobAdTypeRewardedVideo) {
    return;
  }

  ENSURE_SINGLE_ARG(adUnitId, NSString);
  [GADRewardBasedVideoAd.sharedInstance loadRequest:self.admobView.request withAdUnitID:adUnitId];
}

@end
