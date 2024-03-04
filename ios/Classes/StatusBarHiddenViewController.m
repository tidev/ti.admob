#import "StatusBarHiddenViewController.h"

@implementation StatusBarHiddenViewController

- (BOOL)prefersStatusBarHidden {
    return YES;
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];

    // Present the ad here
    [self.ad presentFromRootViewController:self];
}

@end