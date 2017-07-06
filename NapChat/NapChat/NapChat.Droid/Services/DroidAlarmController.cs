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
            
            manager.SetExact(AlarmType.RtcWakeup, alarm.getTriggerTime().Ticks , pendingIntent);

            Toast.MakeText(context, "Alarm Created!", ToastLength.Long).Show();
            
            //Send notifications here.


        }

        public void createRepeatingAlarm()
        {

        }

        
    }
}