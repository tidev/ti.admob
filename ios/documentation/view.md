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