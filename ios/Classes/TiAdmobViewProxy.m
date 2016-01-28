/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAdmobViewProxy.h"
#import "TiUtils.h"

@implementation TiAdmobViewProxy

- (TiAdmobView*)admobView
{
    return (TiAdmobView*)[self view];
}

- (void)receive:(id)unused
{    
    [[self admobView] initialize];
}

@end
