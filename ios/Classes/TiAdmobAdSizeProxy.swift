//
//  TiAdmobAdSizeProxy.swift
//  TiAdmob
//
//  Created by Jan Vennemann on 20.02.19.
//

import Foundation
import TitaniumKit
import GoogleMobileAds

class TiAdmobAdSizeProxy : TiProxy {
  var adSize: GADAdSize
  
  @objc var height: CGFloat {
    get {
      return adSize.size.height
    }
  }
  
  @objc var width: CGFloat {
    get {
      return adSize.size.width
    }
  }
  
  init(_ adSize: GADAdSize) {
    self.adSize = adSize
  }
  
  override func toString(_ args: Any!) -> Any! {
    return NSStringFromGADAdSize(adSize)
  }
}
