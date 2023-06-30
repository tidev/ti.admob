//
//  MTGCampaign.h
//  MTGSDK
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, MTGAdSourceType) {
    MTGAD_SOURCE_API_OFFER = 1,
    MTGAD_SOURCE_MY_OFFER  = 2,
    MTGAD_SOURCE_FACEBOOK  = 3,
    MTGAD_SOURCE_Mintegral = 4,
    MTGAD_SOURCE_PUBNATIVE = 5,
    MTGAD_SOURCE_MYTARGET  = 7,
    MTGAD_SOURCE_NATIVEX   = 8,
    MTGAD_SOURCE_APPLOVIN  = 9,
};

typedef NS_ENUM(NSInteger, MTGAdTemplateType) {
    MTGAD_TEMPLATE_BIG_IMAGE  = 2,
    MTGAD_TEMPLATE_ONLY_ICON  = 3,
};

@interface MTGCampaign : NSObject

/*!
 @property
 
 @abstract The unique id of the ad
 */
@property (nonatomic, copy  ) NSString       *adId;

/*!
 @property
 
 @abstract The app's package name of the campaign
 */
@property (nonatomic, copy  ) NSString       *packageName;

/*!
 @property
 
 @abstract The app name of the campaign
 */
@property (nonatomic, copy  ) NSString       *appName;

/*!
 @property
 
 @abstract The description of the campaign
 */
@property (nonatomic, copy  ) NSString       *appDesc;

/*!
 @property
 
 @abstract The app size of the campaign
 */
@property (nonatomic, copy  ) NSString       *appSize;

/*!
 @property
 
 @abstract The icon url of the campaign
 */
@property (nonatomic, copy  ) NSString       *iconUrl;

/*!
 @property
 
 @abstract The image url of the campaign. The image size will be 1200px * 627px.
 */
@property (nonatomic, copy  ) NSString       *imageUrl;

/*!
 @property
 
 @abstract The string to show in the clickbutton
 */
@property (nonatomic, copy  ) NSString       *adCall;

/*!
 @property
 
 @abstract The ad source of the campaign
 */
@property (nonatomic, assign) MTGAdSourceType type;

/*!
 @property
 
 @abstract The timestap of the campaign
 */
@property (nonatomic, assign) double      timestamp;

/*!
 @property
 
 @abstract The dataTemplate of the campaign
 */
@property (nonatomic,assign) MTGAdTemplateType    dataTemplate;

/* The size info about adChoice icon */
@property (nonatomic) CGSize adChoiceIconSize;

/*!
@property

@abstract The video  duration of the campaign
*/
@property (nonatomic,assign) NSInteger     videoLength;

/*!
 @method
 
 @abstract
 Loads an icon image from self.iconUrl over the network, or returns the cached image immediately.
 
 @param block Block to handle the loaded image. The image may be nil if error happened.
 */
- (void)loadIconUrlAsyncWithBlock:(void (^)(UIImage *image))block;

/*!
 @method
 
 @abstract
 Loads an image from self.imageUrl over the network, or returns the cached image immediately.
 
 @param block Block to handle the loaded image. The image may be nil if error happened.
 */
- (void)loadImageUrlAsyncWithBlock:(void (^)(UIImage *image))block;


@end
