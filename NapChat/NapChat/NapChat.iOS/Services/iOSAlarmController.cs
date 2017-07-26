﻿using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Foundation;
using UIKit;
using NapChat.Abstractions;
using NapChat.iOS.Services;
using NapChat.Model;
using UserNotifications;
using System.Threading.Tasks;

using Xamarin.Forms.Platform.iOS;

[assembly: Xamarin.Forms.Dependency(typeof(iOSAlarmController))]
namespace NapChat.iOS.Services
{
	//https://developer.xamarin.com/guides/ios/platform_features/user-notifications/enhanced-user-notifications/
	public class iOSAlarmController : IAlarmController
    {
        public void scheduleAlarm(Alarm alarm)
        {
            ////Notifications Actions
            //var DismissActionId = "dismiss";
            //var DismissTitle = "Dismiss";
            //var DismissAction = UNNotificationAction.FromIdentifier(DismissActionId, DismissTitle, UNNotificationActionOptions.Foreground);

            //var SnoozeActionId = "snooze";
            //var SnoozeTitle = "Snooze";
            //var SnoozeAction = UNNotificationAction.FromIdentifier(SnoozeActionId, SnoozeTitle, UNNotificationActionOptions.None);

            ////Category of Actions
            //var ActionCategoryID = "AlarmButtons";
            //var actions = new UNNotificationAction[] { DismissAction, SnoozeAction };
            //var intentIDs = new string[] { };
            //var ActionCategoryOptions = new UNNotificationCategoryOptions[] { UNNotificationCategoryOptions.CustomDismissAction};
            //var ActionCategory = UNNotificationCategory.FromIdentifier(ActionCategoryID, actions, intentIDs, ActionCategoryOptions[0]);

            ////Register Category
            //var ActionCategories = new UNNotificationCategory[] { ActionCategory };

            //UNUserNotificationCenter.Current.SetNotificationCategories(new NSSet<UNNotificationCategory>(ActionCategories));


            var nsDate = new NSDateComponents();
			TimeSpan ts = alarm.getTriggerTime();

			nsDate.Hour = ts.Hours;
			nsDate.Minute = ts.Minutes;

 

            var timeDisplay = new DateTime(ts.Ticks).ToString("h:mm tt");

			var content = new UNMutableNotificationContent();

			content.Title = timeDisplay;
			//content.Subtitle = "Notification Subtitle";
			content.Body = "Open Alarm.";
			content.Badge = 1;
            content.CategoryIdentifier = "AlarmButtons";

            if (alarm.getRingTone() == "default")
            {
                content.Sound = UNNotificationSound.Default;
            }else
            {
                content.Sound = UNNotificationSound.GetSound(alarm.getRingTone());
            }

           


 


			// trigger time
			var trigger = UNCalendarNotificationTrigger.CreateTrigger(nsDate ,false);

            // Notification ID for updates
			var requestID = alarm.getID().ToString();
			var request = UNNotificationRequest.FromIdentifier(requestID, content, trigger);
                                                
			UNUserNotificationCenter.Current.AddNotificationRequest(request, (err) =>
			{
				if (err != null)
				{
                    Debug.WriteLine("error, {0}", err);
                    // Do something with error
				}
                else
                {
                    
                }
			});
        }

        public void scheduleRepeatingAlarm()
        {

        }

        public void unscheduleAlarm(Alarm alarm)
        {
			var requests = new string[] { alarm.getID().ToString() };
			UNUserNotificationCenter.Current.RemovePendingNotificationRequests(requests);
        }
    }
}