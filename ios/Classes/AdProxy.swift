//
//  AdProxy.swift
//  TiAdmob
//
//  Created by Jan Vennemann on 20.02.19.
//

import Foundation
import GoogleMobileAds

protocol AdProxy {
  var adUnitId: String { get set }
  func createRequest(_ options: [String: Any]) -> GADRequest
}

extension AdProxy {
  func createRequest(_ options: [String: Any]) -> GADRequest {
    let request = GADRequest();

    if let keywords = options["keywords"] as? [String] {
      request.keywords = keywords
    }
    if let extrasParameters = options["extras"] as? [String: Any] {
      let extras = GADExtras()
      extras.additionalParameters = extrasParameters
      request.register(extras)
    }
    if let contentUrl = options["contentUrl"] as? String {
      request.contentURL = contentUrl
    }
    if let tagForChildDirectedTreatment = options["tagForChildDirectedTreatment"] as? NSNumber {
      request.tag(forChildDirectedTreatment: tagForChildDirectedTreatment.boolValue)
    }
    if let requestAgent = options["requestAgent"] as? String {
      request.requestAgent = requestAgent
    }
    if let testDevices = options["testDevices"] as? [String] {
      request.testDevices = testDevices
    }

    return request
  }
}
