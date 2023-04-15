/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;

/**
 * This class is left for backwards compatibility until
 * the deprecated createView() method for BannerView ad
 * is removed from the module. Users should use createBannerView()
 */
@Kroll.proxy(creatableInModule = AdmobModule.class)
public class ViewProxy extends BannerViewProxy {

    protected static final String TAG = "AdMobViewProxy";

    public ViewProxy() {
        Log.w(TAG, "ViewProxy has been deprecated. Use createBannerView instead.");
    }
}
