using System;
using System.Collections.Generic;
using System.Linq;

using Foundation;
using UIKit;

namespace NapChat.iOS
{
	// The UIApplicationDelegate for the application. This class is responsible for launching the 
	// User Interface of the application, as well as listening (and optionally responding) to 
	// application events from iOS.
	[Register("AppDelegate")]
	public partial class AppDelegate : global::Xamarin.Forms.Platform.iOS.FormsApplicationDelegate
	{
		//
		// This method is invoked when the application has loaded and is ready to run. In this 
		// method you should instantiate the window, load the UI into it and then make the window
		// visible.
		//
		// You have 17 seconds to return from this method, or iOS will terminate your application.
		//
		public override bool FinishedLaunching(UIApplication app, NSDictionary options)
		{
            Microsoft.WindowsAzure.MobileServices.CurrentPlatform.Init();
            global::Xamarin.Forms.Forms.Init ();
			LoadApplication (new NapChat.App ());

            var settings = UIUserNotificationSettings.GetSettingsForTypes(
            UIUserNotificationType.Alert | UIUserNotificationType.Badge | UIUserNotificationType.Sound, null);
            UIApplication.SharedApplication.RegisterUserNotificationSettings(settings);

            // check for a notification
    if (options != null)
                {
                    // check for a local notification
                    if (options.ContainsKey(UIApplication.LaunchOptionsLocalNotificationKey))
                    {
                        var localNotification = options[UIApplication.LaunchOptionsLocalNotificationKey] as UILocalNotification;
                        if (localNotification != null)
                        {
                            UIAlertController dismissAlertController = UIAlertController.Create(localNotification.AlertAction, localNotification.AlertBody, UIAlertControllerStyle.Alert);
                            dismissAlertController.AddAction(UIAlertAction.Create("Dismiss", UIAlertActionStyle.Cancel, null));
                         //   dismissAlertController.AddAction(UIAlertAction.Create("Snooze", UIAlertActionStyle.Default, snoozeAlarm()));
                            Window.RootViewController.PresentViewController(dismissAlertController, true, null);

                            // reset our badge
                            UIApplication.SharedApplication.ApplicationIconBadgeNumber = 0;
                        }
                    }
                }
            return base.FinishedLaunching (app, options);
		}

        public override void ReceivedLocalNotification(UIApplication application, UILocalNotification notification)
        {
            //show an alert
            UIAlertController dismissAlertController = UIAlertController.Create(notification.AlertAction, notification.AlertBody, UIAlertControllerStyle.Alert);
            //Should implement a second AlertAction for Snooze and the third param in Create is the event handler when clicked.
            dismissAlertController.AddAction(UIAlertAction.Create("Dismiss", UIAlertActionStyle.Cancel, null));
            Window.RootViewController.PresentViewController(dismissAlertController, true, null);

            //reset our badge
            UIApplication.SharedApplication.ApplicationIconBadgeNumber = 0;

            base.ReceivedLocalNotification(application, notification);
        }
    }
}
