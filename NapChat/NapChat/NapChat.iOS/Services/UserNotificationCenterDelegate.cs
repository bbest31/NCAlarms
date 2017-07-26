﻿using System;
using UserNotifications;
using System.Diagnostics;
using ObjCRuntime;

namespace NapChat.iOS.Services
{
    public class UserNotificationCenterDelegate : UNUserNotificationCenterDelegate
    {

		#region Constructors
		public UserNotificationCenterDelegate()
		{
		}
		#endregion

		#region Override Methods
		public override void WillPresentNotification(UNUserNotificationCenter center, UNNotification notification, Action<UNNotificationPresentationOptions> completionHandler)
		{
			// Do something with the notification
			Debug.WriteLine("Active Notification: {0}", notification);

			// Tell system to display the notification anyway or use
			// `None` to say we have handled the display locally.
			completionHandler(UNNotificationPresentationOptions.Alert);
		}

        public override void DidReceiveNotificationResponse(UNUserNotificationCenter center, UNNotificationResponse response, Action completionHandler)
        {
            if (response.ActionIdentifier == "open"){
                Debug.WriteLine("Action \"Open\"");
            }
            else if(response.IsDefaultAction)
            {
                //App.Current.MainPage = new;

                Debug.WriteLine("Is Default Action");
            }

            completionHandler();

        }
        #endregion
    }
}
