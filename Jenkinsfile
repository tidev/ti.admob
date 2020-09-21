#!groovy
library 'pipeline-library'

def isMaster = env.BRANCH_NAME.equals('master')

buildModule {
	sdkVersion = '9.0.0.v20200205134519' // use a master build with ARM64 support
	iosLabels = 'osx && xcode-11'
	npmPublish = isMaster // By default it'll do github release on master anyways too
	npmPublishArgs = '--access public --dry-run'
}
