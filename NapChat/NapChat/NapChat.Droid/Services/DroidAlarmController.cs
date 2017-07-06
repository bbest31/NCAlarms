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
using NapChat.Droid.Services;
using NapChat.Abstractions;
using NapChat.Droid.Broadcast;

[assembly: Xamarin.Forms.Dependency(typeof(DroidAlarmController))]
namespace NapChat.Droid.Services
{
    public class DroidAlarmController : IAlarmController
    {
        Context context = Android.App.Application.Context;


        public  void createAlarm()
        {


            AlarmManager manager = ((AlarmManager)context.GetSystemService(Context.AlarmService));
            Intent myIntent = new Intent(context, typeof(AlarmReceiver));
            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.GetBroadcast(context, 0, myIntent, 0);
            
            /*TODO:
             * Use parameter to pass in the alarm time length for method calls to replace SystemClock.ElapsedRealtime...
             Also we will use a Builder class to make Nap-Alerts for the Nap-Log and Push notifications.*/
            
            manager.Set(AlarmType.RtcWakeup, SystemClock.ElapsedRealtime() + 10000/*alarmlengthMilli*/, pendingIntent);

            Toast.MakeText(context, "Alarm Created!", ToastLength.Long).Show();

            //Call builder class here.
            // NapAlertFactory.buildNapAlert();
            
            //Send notifications here.


        }

        public void createRepeatingAlarm()
        {

        }

        
    }
}