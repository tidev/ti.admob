//
//  MTGTemplate.h
//  MTGSDK
//

#import <Foundation/Foundation.h>
#import "MTGCampaign.h"

/*!
 @class MTGTemplate
 
 @abstract This class defines what type of ads and how many ads you want to retrive in one template.
 */
@interface MTGTemplate : NSObject

/*!
 @property
 
 @abstract It is an enumeration value. The default value is MTGAD_TEMPLATE_ONLY_ICON. It defines what type of ads you want to retrive in one template.
 */
@property (nonatomic, assign) MTGAdTemplateType templateType;

/*!
 @property
 
 @abstract It defines how many ads you want to retrive in one template.
 */
@property (nonatomic, assign) NSUInteger adsNum;

/**
 *
 @method
 
 @abstract The method defines which kinds of template you want to retrive.
 
 @param templateType It is an enumeration value. The default value is MTGAD_TEMPLATE_ONLY_ICON. It defines what type of ads you want to retrive in one template.
 @param adsNum It defines how many ads you want to retrive in one template.
 */
+ (MTGTemplate *)templateWithType:(MTGAdTemplateType)templateType adsNum:(NSUInteger)adsNum;

@end
