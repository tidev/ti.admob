# Ti.Admob.View

## Description

A view for displaying ads delivered through Admob.

You should explicitly size the ad to one of the following predefined sizes (width x height). An appropriate
ad will be loaded from the server automatically:

* 320x50
* 300x250
* 468x60
* 728x90

## Properties

### string publisherId

The publisher ID for admob.

### string adBackgroundColor

The background color used for the ad.

### boolean testing

Whether or not admob should be run in testing mode.  Running in testing mode
returns test ads to the simulator when running in simulator, and the current
device when running from device.

### Date dateOfBirth

A date object representing the user's date of birth that should be used
for determining ad delivery.

### string gender

The user's gender for the purpose of determining ad delivery. This should be "male" or "female", in lower case.

### string keywords

Keywords used to determine ad delivery.

### object location

A dictionary with the location of the user for location-based ads:

* float latitude
* float longitude
* float accuracy

## Events

### didReceiveAd

 Sent when an ad request loaded an ad.  This is a good opportunity to add this
 view to the hierarchy if it has not yet been added.  If the ad was received
 as a part of the server-side auto refreshing, you can examine the
 hasAutoRefreshed property of the view.

### didFailToReceiveAd

 Sent when an ad request failed.  Normally this is because no network
 connection was available or no ads were available (i.e. no fill).

 ### willPresentScreen

Sent just before presenting the user a full screen view, such as a browser,
in response to clicking on an ad.  Use this opportunity to stop animations,
time sensitive interactions, etc.

Normally the user looks at the ad, dismisses it, and control returns to your
application by firing off "didDismissScreen":.  However if the user hits the
Home button or clicks on an App Store link your application will end. In that case,
"willLeaveApplication" would fire.

### willDismissScreen

Sent just before dismissing a full screen view.

### didDismissScreen

Sent just after dismissing a full screen view.  Use this opportunity to
restart anything you may have stopped as part of "willPresentScreen".

### willLeaveApplication

Sent just before the application will background or terminate because the
user clicked on an ad that will launch another application (such as the App
Store).
