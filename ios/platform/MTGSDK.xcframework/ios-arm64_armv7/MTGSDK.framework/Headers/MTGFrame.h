//
//  MTGFrame.h
//  MTGSDK
//

#import <Foundation/Foundation.h>
#import "MTGCampaign.h"

__deprecated_msg("Class is deprecated.")

@interface MTGFrame : NSObject

/*!
 @property
 
 @abstract The dataTemplate of the frame.
 */
@property (nonatomic, assign) MTGAdTemplateType templateType;

/*!
 @property
 
 @abstract The ad source of the frame.
 */
@property (nonatomic, assign) MTGAdSourceType sourceType;

/*!
 @property
 
 @abstract The timestap of the frame.
 */
@property (nonatomic, assign) double      timestamp;

/*!
 @property
 
 @abstract The id of the frame.
 */
@property (nonatomic, strong) NSString      *frameId;

/*!
 @property
 
 @abstract The native ads contained in this frame. Array of MTGCampaign objects.
 */
@property (nonatomic, strong) NSArray *nativeAds;

@end
