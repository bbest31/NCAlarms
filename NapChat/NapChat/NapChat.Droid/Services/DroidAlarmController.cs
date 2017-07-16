using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Xamarin.Forms.Platform.Android;
using Xamarin.Forms;
using NapChat.Model;
using NapChat.Droid.Services;
using NapChat.Abstractions;
using NapChat.Droid.Broadcast;
using System.Diagnostics;
using Android.Media;

[assembly: Xamarin.Forms.Dependency(typeof(DroidAlarmController))]
namespace NapChat.Droid.Services
{
    public class DroidAlarmController : IAlarmController
    {
        Context context = Android.App.Application.Context;

        /// <summary>
        /// Schedules an alarm with the AlarmManager.
        /// </summary>
        /// <param name="alarm"></param>
        public  void scheduleAlarm(Alarm alarm)
        {
            alarm.Activate();

            var triggerTime = UTCMilliseconds(alarm.getTriggerTime());
            DateTime Dtime = DateTime.Today + alarm.getTriggerTime();
            string time = Dtime.TimeOfDay.ToString();

            AlarmManager manager = ((AlarmManager)context.GetSystemService(Context.AlarmService));
            Intent myIntent = new Intent(context, typeof(AlarmReceiver));
            

            //Provide Settings
            //System.Diagnostics.Debug.WriteLine("Vibrate Settings on Pass: " + alarm.getVibrateSettings().ToString());
            myIntent.PutExtra("Vibrate", alarm.getVibrateSettings());
            myIntent.PutExtra("Id", alarm.getID());
            myIntent.PutExtra("Snooze", alarm.getSnoozeLength());
            myIntent.PutExtra("Time", time);
           // System.Diagnostics.Debug.WriteLine("Ringtone Stored on Pass: " + alarm.getRingTone());
            myIntent.PutExtra("Uri", alarm.getRingTone());

            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.GetBroadcast(context, 0 , myIntent, PendingIntentFlags.UpdateCurrent);

            manager.SetExact(AlarmType.RtcWakeup, triggerTime , pendingIntent);

            Toast.MakeText(context, "Alarm Created!", ToastLength.Long).Show();

        }

        public void scheduleRepeatingAlarm()
        {

        }
        /// <summary>
        /// Returns the trigger time of the alarm in milliseconds with respect to UTC.
        /// </summary>
        /// <param name="ts"></param>
        /// <returns></returns>
        public long UTCMilliseconds(TimeSpan ts)
        {
            DateTime dt = DateTime.Today + ts;
            DateTime dtNow = DateTime.Now;
            /*Check TimeSpan value that was set to see if the hours is less than the current hours.
             If so we add one day to DateTime to schedule it for tomorrow.*/ 
            if(dt.TimeOfDay.CompareTo(dtNow.TimeOfDay) < 0)
            {
                dt = dt.AddDays(1);
            }

            dt = dt.ToUniversalTime();
            DateTime dtUTC = new DateTime(1970, 1, 1);
            TimeSpan newTS = new TimeSpan(dt.Ticks - dtUTC.Ticks);
            return (long)newTS.TotalMilliseconds;
        }
        
        public void cancelAlarm()
        {
            AlarmManager manager = ((AlarmManager)context.GetSystemService(Context.AlarmService));
        }
    }
}