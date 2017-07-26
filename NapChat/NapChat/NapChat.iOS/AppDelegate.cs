﻿using System;
using System.Collections.Generic;
using System.Linq;

using Foundation;
using UIKit;
using Xamarin.Forms.Platform.iOS;
using UserNotifications;
using NapChat.iOS.Services;


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

			UNUserNotificationCenter.Current.RequestAuthorization(UNAuthorizationOptions.Alert, (approved, err) =>
			{
				// Handle approval

			});

           /* // //Get current notification settings
            UNUserNotificationCenter.Current.GetNotificationSettings(( settings) =>
            {
            	var alertsAllowed = (settings.AlertSetting == UNNotificationSetting.Enabled);
            });*/

            // Watch for notifications while the app is active
            UNUserNotificationCenter.Current.Delegate = new UserNotificationCenterDelegate();

			//return true;

            return base.FinishedLaunching (app, options);
		}

        public override void ReceivedLocalNotification(UIApplication application, UILocalNotification notification)
        {
            
            //reset our badge
            UIApplication.SharedApplication.ApplicationIconBadgeNumber = 0;

            //base.ReceivedLocalNotification(application, notification);

        }
    }
}
