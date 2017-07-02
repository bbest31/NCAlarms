using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Foundation;
using UIKit;
using NapChat.Abstractions;
using NapChat.iOS.Services;
using Xamarin.Forms.Platform.iOS;

[assembly: Xamarin.Forms.Dependency(typeof(iOSAlarmController))]
namespace NapChat.iOS.Services
{
    public class iOSAlarmController : IAlarmController
    {
        public void createAlarm()
        {
            UILocalNotification notification = new UILocalNotification();
            NSDate.FromTimeIntervalSinceNow(15);

            notification.AlertAction = "View Alert";
            notification.AlertBody = "Your 15 second alert has fired";
            notification.SoundName = UILocalNotification.DefaultSoundName;
            Debug.WriteLine("about to schedule notification");
            UIApplication.SharedApplication.ScheduleLocalNotification(notification);
        }

        public void createRepeatingAlarm()
        {

        }
    }
}