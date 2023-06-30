//
//  MTGBool.h
//  MTGSDK
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

/**
 Tri-state boolean.
 */
typedef NS_ENUM(NSInteger, MTGBool) {
    /* No */
    MTGBoolNo = -1,
    
    /* Unknown */
    MTGBoolUnknown = 0,
    
    /* Yes */
    MTGBoolYes = 1,
};


typedef NS_ENUM(NSUInteger, MTGInterfaceOrientation) {
    MTGInterfaceOrientationAll = 0,       // to use current orientation of the device.
    MTGInterfaceOrientationPortrait = 1,  // to force to use portrait mode.
    MTGInterfaceOrientationLandscape = 2, // to force to use landscape mode.
};


