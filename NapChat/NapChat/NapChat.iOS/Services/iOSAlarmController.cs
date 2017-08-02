﻿﻿using System;
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

            var OpenActionId = "open";
            var OpenTitle = "Open";
            var OpenAction = UNNotificationAction.FromIdentifier(OpenActionId, OpenTitle, UNNotificationActionOptions.None);

            var AlarmCategoryID = "alarm";
            var actions = new UNNotificationAction[] { OpenAction};
            var intentIDs = new string[] { };
            var categoryOptions = new UNNotificationCategoryOptions[] { };
            var category = UNNotificationCategory.FromIdentifier(AlarmCategoryID, actions, intentIDs, UNNotificationCategoryOptions.None);

            var categories = new UNNotificationCategory[] { category };
            UNUserNotificationCenter.Current.SetNotificationCategories(new NSSet<UNNotificationCategory>(categories));

            


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
            content.CategoryIdentifier = "alarm";

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

        public void unscheduleAlarm(string[] alarmID)
        {
			
			UNUserNotificationCenter.Current.RemovePendingNotificationRequests(alarmID);
        }

        public void dismissAlarm(string[] alarmID)
        {
            Debug.WriteLine("What dismiss method is receiving"+alarmID.ToString());
            //UNUserNotificationCenter.Current.RemoveDeliveredNotifications(alarmID);
        }

        public void snoozeAlarm(Alarm alarm)
        {

        }
    }
}