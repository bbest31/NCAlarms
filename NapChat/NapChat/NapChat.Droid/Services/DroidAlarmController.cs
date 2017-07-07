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


[assembly: Xamarin.Forms.Dependency(typeof(DroidAlarmController))]
namespace NapChat.Droid.Services
{
    public class DroidAlarmController : IAlarmController
    {
        Context context = Android.App.Application.Context;


        public  void createAlarm(Alarm alarm)
        {


            AlarmManager manager = ((AlarmManager)context.GetSystemService(Context.AlarmService));
            Intent myIntent = new Intent(context, typeof(AlarmReceiver));
            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.GetBroadcast(context, 0, myIntent, 0);

            DateTime dt = new DateTime(alarm.getTriggerTime().Ticks);
            dt = dt.ToUniversalTime();
            


            manager.SetExact(AlarmType.RtcWakeup, Java.Lang.JavaSystem.CurrentTimeMillis() + 5*1000 , pendingIntent);

            Toast.MakeText(context, "Alarm Created!", ToastLength.Long).Show();
            
            //Send notifications here.


        }

        public void createRepeatingAlarm()
        {

        }

        
    }
}