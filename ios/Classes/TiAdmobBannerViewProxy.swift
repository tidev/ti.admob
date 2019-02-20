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
class TiAdmobBannerViewProxy : TiUIViewProxy {
  @objc public var adSize = TiAdmobAdSizeProxy(kGADAdSizeBanner)
  
  @objc public var adUnitId = ""
  
  @objc(load:)
  public func load(args: [Any]?) {
    guard let view = view as? TiAdmobBannerView else {
      return
    }
    
    guard let args = args, let options = args.first as? NSDictionary else {
      view.bannerView.load(GADRequest())
      return;
    }
    
    let request = GADRequest();
    
    if let keywords = options["keywords"] as? [String] {
      request.keywords = keywords
    }
    // @todo how to deal with extras?
    /*
    if let extras = options["extras"] as? [String: Any] {
      request.register(T##extras: GADAdNetworkExtras##GADAdNetworkExtras)
    }
    */
    if let contentUrl = options["contentUrl"] as? String {
      request.contentURL = contentUrl
    }
    if let tagForChildDirectedTreatment = options["tagForChildDirectedTreatment"] as? Bool {
      request.tag(forChildDirectedTreatment: tagForChildDirectedTreatment)
    }
    if let requestAgent = options["requestAgent"] as? String {
      request.requestAgent = requestAgent
    }
    if let testDevices = options["testDevices"] as? [String] {
      request.testDevices = testDevices
    }
    
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
