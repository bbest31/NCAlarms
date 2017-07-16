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

			var nsDate = new NSDateComponents();
			TimeSpan ts = alarm.getTriggerTime();

			nsDate.Hour = ts.Hours;
			nsDate.Minute = ts.Minutes;

            string meridiem;
            if (nsDate.Hour >= 0 && nsDate.Hour < 12)
            {
                meridiem = " AM";
            }
            else
            {
                meridiem = " PM";
            }


			var content = new UNMutableNotificationContent();
			content.Title = nsDate.Hour.ToString() + ":" + nsDate.Minute.ToString() + meridiem;
			//content.Subtitle = "Notification Subtitle";
			content.Body = "Time to wake up!";
			content.Badge = 1;

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
                    Debug.WriteLine("Notification Scheduled, {0}", request);
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