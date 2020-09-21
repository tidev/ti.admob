//
//  TiAdmobBannerView.swift
//  TiAdmob
//
//  Created by Jan Vennemann
//  Copyright (c) 2019 Axway Appcelerator. All rights reserved.
//

import Foundation
import TitaniumKit
import GoogleMobileAds

@objc(TiAdmobBannerView)
class TiAdmobBannerView : TiUIView {
  var bannerView: GADBannerView!

  override func configurationSet() {
    super.configurationSet()

    guard let proxy = proxy as? TiAdmobBannerViewProxy else {
      return;
    }

    if (proxy.adUnitId == "") {
      self.throwException("Ad unit ID missing", subreason: "", location: CODELOCATION)
      return
    }

    bannerView = GADBannerView(adSize: proxy.adSize.adSize)
    bannerView.adUnitID = proxy.adUnitId
    bannerView.rootViewController = TiApp.controller()
    bannerView.delegate = proxy
    bannerView.translatesAutoresizingMaskIntoConstraints = false
    self.addSubview(bannerView)
    self.addConstraints([
      NSLayoutConstraint(item: bannerView,
                         attribute: .bottom,
                         relatedBy: .equal,
                         toItem: self,
                         attribute: .bottom,
                         multiplier: 1,
                         constant: 0),
      NSLayoutConstraint(item: bannerView,
                         attribute: .centerX,
                         relatedBy: .equal,
                         toItem: self,
                         attribute: .centerX,
                         multiplier: 1,
                         constant: 0)
    ])
  }
}
