'use strict';
const path = require('path');
const fs = require('fs-extra');

function projectManagerHook(projectManager) {
	projectManager.once('prepared', function () {
		const tiapp = path.join(this.karmaRunnerProjectPath, 'tiapp.xml');
		var contents = fs.readFileSync(tiapp, 'utf8');
		contents = contents.replace('</dict>', `<key>GADIsAdManagerApp</key>
		<true/>
		</dict>`);
		fs.writeFileSync(tiapp, contents.replace('</manifest>', `<application>
		  <meta-data
			  android:name="com.google.android.gms.ads.APPLICATION_ID"
			  android:value="ca-app-pub-3940256099942544~3347511713"/>
		</application>
	  </manifest>`), 'utf8');
	});
}
projectManagerHook.$inject = [ 'projectManager' ];

module.exports = config => {
	config.set({
		basePath: '../..',
		frameworks: [ 'jasmine', 'projectManagerHook' ],
		files: [
			'test/unit/specs/**/*spec.js'
		],
		reporters: [ 'mocha', 'junit' ],
		plugins: [
			'karma-*',
			{
				'framework:projectManagerHook': [ 'factory', projectManagerHook ]
			}
		],
		titanium: {
			sdkVersion: config.sdkVersion || '9.1.0.v20200219062647'
		},
		customLaunchers: {
			android: {
				base: 'Titanium',
				browserName: 'Android AVD',
				displayName: 'android',
				platform: 'android'
			},
			ios: {
				base: 'Titanium',
				browserName: 'iOS Emulator',
				displayName: 'ios',
				platform: 'ios'
			}
		},
		browsers: [ 'android', 'ios' ],
		client: {
			jasmine: {
				random: false
			}
		},
		singleRun: true,
		retryLimit: 0,
		concurrency: 1,
		captureTimeout: 300000,
		logLevel: config.LOG_DEBUG
	});
};
