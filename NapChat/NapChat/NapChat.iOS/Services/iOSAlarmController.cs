using System;
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
			var content = new UNMutableNotificationContent();
			content.Title = "Notification Title";
			content.Subtitle = "Notification Subtitle";
			content.Body = "This is the message body of the notification.";
			content.Badge = 1;

            var nsDate = new NSDateComponents();
            TimeSpan ts = alarm.getTriggerTime();


            //DateTime dt = DateTime.Today + ts;
            //nsDate.Day = dt.Day;
            //nsDate.Month = dt.Month;
            //nsDate.Year = dt.Year;
            //nsDate.Hour = dt.Hour;
            //nsDate.Minute = dt.Minute;
			nsDate.Hour = ts.Hours;
			nsDate.Minute = ts.Minutes;

            var trigger = UNCalendarNotificationTrigger.CreateTrigger(nsDate ,false);
			var requestID = alarm.getID().ToString();
			var request = UNNotificationRequest.FromIdentifier(requestID, content, trigger);

			UNUserNotificationCenter.Current.AddNotificationRequest(request, (err) =>
			{
				if (err != null)
				{
                    // Do something with error
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