﻿using System;
using UserNotifications;
using System.Diagnostics;
using ObjCRuntime;
using NapChat.Pages;

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

        public override async void DidReceiveNotificationResponse(UNUserNotificationCenter center, UNNotificationResponse response, Action completionHandler)
        {
            if (response.ActionIdentifier == "open")
            {
                Debug.WriteLine("Action \"Open\"");
            }
            else if (response.IsDefaultAction)
            {

                //Should pass in the alarm to the AlarmView page in order to display the time and dismiss/snooze the right alarm.
                UNNotification[] notifications = await center.GetDeliveredNotificationsAsync();
                Debug.WriteLine("Notification array"+notifications.ToString());
                UNNotification ncnotification = notifications[0];
                UNNotificationRequest request = ncnotification.Request;
                Debug.WriteLine("Request Identifier for first notification"+request.Identifier.ToString() + "Notification Title: " + request.Content.Title);
                string[] identifier = { "random" };
                App.Current.MainPage = new AlarmView(identifier);


                Debug.WriteLine("Is Default Action");
            }

            completionHandler();

        }
        #endregion
    }
}
