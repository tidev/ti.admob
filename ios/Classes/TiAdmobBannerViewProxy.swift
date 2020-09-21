//
//  TiAdmobBannerViewProxy.swift
//  TiAdmob
//
//  Created by Jan Vennemann
//  Copyright (c) 2019 Axway Appcelerator. All rights reserved.
//

import Foundation
import TitaniumKit
import GoogleMobileAds

@objc(TiAdmobBannerViewProxy)
class TiAdmobBannerViewProxy : TiUIViewProxy, AdProxy {
  @objc public var adSize = TiAdmobAdSizeProxy(kGADAdSizeBanner)

  @objc public var adUnitId = ""

  @objc(load:)
  public func load(args: [Any]?) {
    guard let view = view as? TiAdmobBannerView else {
      return
    }

    guard let args = args, let options = args.first as? [String: Any] else {
      view.bannerView.load(GADRequest())
      return;
    }

    let request = createRequest(options)

    view.bannerView.load(request)
  }
}

// MARK: GADBannerViewDelegate

extension TiAdmobBannerViewProxy : GADBannerViewDelegate {
  func adViewDidReceiveAd(_ bannerView: GADBannerView) {
    if (!self._hasListeners("load")) {
      return
    }

    self.fireEvent("load")
  }

  func adView(_ bannerView: GADBannerView, didFailToReceiveAdWithError error: GADRequestError) {
    if (!self._hasListeners("fail")) {
      return
    }

    self.fireEvent("fail", with: nil, errorCode: error.code, message: error.localizedDescription)
  }

  func adViewWillPresentScreen(_ bannerView: GADBannerView) {
    if (!self._hasListeners("open")) {
      return
    }

    self.fireEvent("open")
  }

  func adViewDidDismissScreen(_ bannerView: GADBannerView) {
    if (!self._hasListeners("close")) {
      return
    }

    self.fireEvent("close")
  }

  func adViewWillLeaveApplication(_ bannerView: GADBannerView) {
    if (!self._hasListeners("leftapp")) {
      return
    }

    self.fireEvent("leftapp")
  }
}
