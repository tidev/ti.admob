//
//  TiAdmobInterstitialAdProxy.swift
//  TiAdmob
//
//  Created by Jan Vennemann on 20.02.19.
//

import Foundation
import TitaniumKit
import GoogleMobileAds

class TiAdmobInterstitialAdProxy : TiProxy, AdProxy {
  
  @objc public var adUnitId = ""
  
  @objc public var isReady: Bool {
    get {
      return interstitial?.isReady ?? false
    }
  }
  
  var interstitial: GADInterstitial?
  
  @objc(load:)
  public func load(args: [Any]?) {
    interstitial = GADInterstitial(adUnitID: adUnitId)
    guard let interstitial = interstitial else {
      return
    }
    interstitial.delegate = self
    
    guard let args = args, let options = args.first as? [String: Any] else {
      interstitial.load(GADRequest())
      return;
    }
    
    let request = createRequest(options)
    interstitial.load(request)
  }
  
  @objc
  public func show() {
    guard let interstitial = interstitial, let topController = TiApp.controller()?.topPresentedController() else {
      return
    }
    
    interstitial.present(fromRootViewController: topController)
  }
}

// MARK: GADInterstitialDelegate

extension TiAdmobInterstitialAdProxy
: GADInterstitialDelegate {
  func interstitialDidReceiveAd(_ ad: GADInterstitial) {
    if (!self._hasListeners("load")) {
      return
    }
    
    self.fireEvent("load")
  }
  
  func interstitial(_ ad: GADInterstitial, didFailToReceiveAdWithError error: GADRequestError) {
    if (!self._hasListeners("fail")) {
      return
    }
    
    self.fireEvent("fail", with: nil, errorCode: error.code, message: error.localizedDescription)
  }
  
  func interstitialWillPresentScreen(_ ad: GADInterstitial) {
    if (!self._hasListeners("open")) {
      return
    }
    
    self.fireEvent("open")
  }
  
  func interstitialDidDismissScreen(_ ad: GADInterstitial) {
    if (!self._hasListeners("close")) {
      return
    }
    
    self.fireEvent("close")
  }
  
  func interstitialWillLeaveApplication(_ ad: GADInterstitial) {
    if (!self._hasListeners("leftapp")) {
      return
    }
    
    self.fireEvent("leftapp")
  }
}
