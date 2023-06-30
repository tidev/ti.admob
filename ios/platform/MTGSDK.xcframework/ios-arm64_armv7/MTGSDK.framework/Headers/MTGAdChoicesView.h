//
//  MTGAdChoicesView.h
//  MTGSDK
//
//  Copyright © 2018 Mintegral. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@class MTGCampaign;

/**
MTGAdChoicesView offers a simple way to display a AdChoice icon.
Since the image icon's size changes, you need to update this view's size too. Additional size info can be pulled from the `MTGCampaign` instance.
 */
@interface MTGAdChoicesView : UIView

/**
 Initialize this view with a given frame.

 @param frame For best user experience, keep the size of this view the same as AdChoiceIcon's, which can be pulled from MTGCampaign's -adChoiceIconSize
 */
- (instancetype)initWithFrame:(CGRect)frame;

/**
 The campaign obj that provides AdChoices info, such as the image url, and click url.
 */
@property (nonatomic, weak, readwrite, nullable) MTGCampaign *campaign;


@end

NS_ASSUME_NONNULL_END
