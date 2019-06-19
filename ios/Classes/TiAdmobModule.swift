//
//  TiAdmobModule.swift
//  ti.admob
//
//  Created by Jan Vennemann
//  Copyright (c) 2019 Axway Appcelerator. All rights reserved.
//

import UIKit
import TitaniumKit
import GoogleMobileAds
import PersonalizedAdConsent

@objc(TiAdmobModule)
class TiAdmobModule: TiModule {

  // MARK: Constants
  @objc public let CONSENT_STATUS_UNKNOWN = PACConsentStatus.unknown.rawValue
  @objc public let CONSENT_STATUS_NON_PERSONALIZED = PACConsentStatus.nonPersonalized.rawValue
  @objc public let CONSENT_STATUS_PERSONALIZED = PACConsentStatus.personalized.rawValue

  @objc public let DEBUG_GEOGRAPHY_DISABLED = PACDebugGeography.disabled.rawValue
  @objc public let DEBUG_GEOGRAPHY_EEA = PACDebugGeography.EEA.rawValue
  @objc public let DEBUG_GEOGRAPHY_NOT_EEA = PACDebugGeography.notEEA.rawValue

  @objc public let SIMULATOR_ID = kGADSimulatorID

  @objc public let AD_SIZE_BANNER = TiAdmobAdSizeProxy(kGADAdSizeBanner)
  @objc public let AD_SIZE_FLUID = TiAdmobAdSizeProxy(kGADAdSizeFluid)
  @objc public let AD_SIZE_FULL_BANNER = TiAdmobAdSizeProxy(kGADAdSizeFullBanner)
  @objc public let AD_SIZE_LARGE_BANNER = TiAdmobAdSizeProxy(kGADAdSizeLargeBanner)
  @objc public let AD_SIZE_LEADERBOARD = TiAdmobAdSizeProxy(kGADAdSizeLeaderboard)
  @objc public let AD_SIZE_MEDIUM_RECTANGLE = TiAdmobAdSizeProxy(kGADAdSizeMediumRectangle)
  @objc public let AD_SIZE_SMART_BANNER = TiAdmobAdSizeProxy(kGADAdSizeSmartBannerPortrait)
  @objc public let AD_SIZE_SKYSCRAPER = TiAdmobAdSizeProxy(kGADAdSizeSkyscraper)

  func moduleGUID() -> String {
    return "0d005e93-9980-4739-9e41-fd1129c8ff32"
  }

  override func moduleId() -> String! {
    return "ti.admob"
  }

  @objc(initialize:)
  public func initialize(args: [Any]?) {
    guard let args = args, let options = args.first as? [String:Any], let appId = options["appId"] as? String else {
      return;
    }

    GADMobileAds.configure(withApplicationID: appId)
  }

  @objc(createInterstitialAd:)
  public func createInterstitialAd(args: [Any]?) -> TiAdmobInterstitialAdProxy {
    return TiAdmobInterstitialAdProxy()._init(withPageContext: self.pageContext, args: args)
  }
}

// MARK: GDPR compliance

extension TiAdmobModule {
  @objc
  public var consentStatus: NSNumber {
    get {
      return NSNumber(value: PACConsentInformation.sharedInstance.consentStatus.rawValue)
    }
  }

  @objc
  public var adProviders: [[String:Any]] {
    get {
      guard let adProviders = PACConsentInformation.sharedInstance.adProviders else {
        return []
      }

      var result: [[String:Any]] = []
      for adProvider in adProviders {
        result.append([
          "identifier": adProvider.identifier,
          "name": adProvider.name,
          "privacyPolicyURL": adProvider.privacyPolicyURL.absoluteString
        ])
      }
      return result
    }
  }

  @objc
  public var debugIdentifiers: [String] {
    get {
      return PACConsentInformation.sharedInstance.debugIdentifiers ?? []
    }
    set {
      PACConsentInformation.sharedInstance.debugIdentifiers = newValue
    }
  }

  @objc
  public var debugGeography: NSNumber {
    get {
      return NSNumber(value: PACConsentInformation.sharedInstance.debugGeography.rawValue)
    }
    set {
      PACConsentInformation.sharedInstance.debugGeography = PACDebugGeography(rawValue: newValue.intValue) ?? PACDebugGeography.disabled
    }
  }
  
  @objc(setTagForUnderAgeOfConsent:)
  public func setTagForUnderAgeOfConsent(arg: Any?) {
    if let value = arg as? NSNumber {
      PACConsentInformation.sharedInstance.isTaggedForUnderAgeOfConsent = value.boolValue
    }
  }
  
  @objc
  public func isTaggedForUnderAgeOfConsent() -> NSNumber {
    return NSNumber(value: PACConsentInformation.sharedInstance.isTaggedForUnderAgeOfConsent)
  }

  @objc(requestConsentInfoUpdateForPublisherIdentifiers:)
  public func requestConsentInfoUpdateForPublisherIdentifiers(args: [Any]?) {
    guard let args = args, let options = args.first as? [String:Any] else {
      return;
    }

    guard let publisherIdentifiers = options["publisherIdentifiers"] as? [String] else {
      return;
    }

    PACConsentInformation.sharedInstance.requestConsentInfoUpdate(forPublisherIdentifiers: publisherIdentifiers) { (error) in
      guard let callback = options["callback"] as? KrollCallback else {
        return
      }

      if let error = error {
        callback.call([["success": false, "error": error.localizedDescription]], thisObject: self)
        return;
      }

      callback.call([["success": true]], thisObject: self)
    }
  }

  @objc(showConsentForm:)
  public func showConsentForm(args: [Any]?) {
    guard let args = args, let options = args.first as? [String:Any] else {
      return;
    }

    guard let privacyUrl = options["privacyURL"] as? URL else {
      self.throwException("Missing \"privacyURL\" argument", subreason: "Cannot show consent form", location: CODELOCATION)
      return
    }

    let form = PACConsentForm.init(applicationPrivacyPolicyURL: privacyUrl)!
    form.shouldOfferPersonalizedAds = TiUtils.boolValue("shouldOfferPersonlizedAds", properties: options, def: true)
    form.shouldOfferNonPersonalizedAds = TiUtils.boolValue("shouldOfferNonPersonalizedAds", properties: options, def: true)
    form.shouldOfferAdFree = TiUtils.boolValue("shouldOfferAdFree", properties: options, def: false)

    form.load { (error) in
      guard let callback = options["callback"] as? KrollCallback else {
        return
      }

      if let error = error {
        callback.call([["error": error.localizedDescription]], thisObject: self)
        return
      }

      form.present(from: TiApp.controller()!.topPresentedController(), dismissCompletion: { (error, userPrefersAdFree) in
        if let error = error {
          callback.call([["userPrefersAdFree": NSNumber.init(value: userPrefersAdFree), "error": error.localizedDescription]], thisObject: self)
          return
        }
        callback.call([["userPrefersAdFree": NSNumber.init(value: userPrefersAdFree)]], thisObject: self)
      })
    }
  }
}

// MARK: Reporting options

extension TiAdmobModule {
  @objc
  public func disableAutomatedInAppPurchaseReporting() {
    GADMobileAds.disableAutomatedInAppPurchaseReporting()
  }

  @objc
  public func disableSDKCrashReporting() {
    GADMobileAds.disableSDKCrashReporting()
  }
}
