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
using Android.Support.V7.App;
using Android.Media;
using NapChat.Model;

namespace NapChat.Droid.Broadcast
{
    [BroadcastReceiver(Enabled =true)]
    public class AlarmReceiver : BroadcastReceiver
    {
        
        

        public override void OnReceive(Context context, Intent intent)
        {

            /*//Make the notification link to a certain page in the app
            // Set up an intent so that tapping the notifications returns to this app:
            Intent localIntent = new Intent(this, typeof(MainActivity));

            // Create a PendingIntent; we're only using one PendingIntent (ID = 0):
            int pendingIntentId = (int)SystemClock.ElapsedRealtime();
            PendingIntent pendingIntent =
                PendingIntent.GetActivity(, pendingIntentId, localIntent, PendingIntentFlags.OneShot);
            
            //Custom notification layout
            

            */
            //Get Extras
            Boolean vibrate = intent.GetBooleanExtra("Vibrate", false);
            //System.Diagnostics.Debug.WriteLine("Vibrate Bool Given to AlarmReceiver: " + vibrate);
            string ringtone = intent.GetStringExtra("Uri");
            //System.Diagnostics.Debug.WriteLine("Ringtone Given to AlarmReceiver: " + ringtone);
            int ID = intent.GetIntExtra("Id", 0);
            int snoozeLength = intent.GetIntExtra("Snooze", 5);
            string displayTime = intent.GetStringExtra("Time");

            //Pass parameters to AlarmActivity so it can have same settings for snooze refire.
            Intent alarmIntent = new Intent(context, typeof(AlarmActivity));
            alarmIntent.PutExtra("SNOOZE", snoozeLength);
            alarmIntent.PutExtra("VIBRATE", vibrate);
            alarmIntent.PutExtra("URI", ringtone);
            alarmIntent.PutExtra("ID", ID);
            alarmIntent.PutExtra("TIME", displayTime);

            PendingIntent pendingAlarmIntent = PendingIntent.GetActivity(context, ID, alarmIntent, PendingIntentFlags.OneShot);

           // Notification.Action action = new Notification.Action.Builder(Resource.Drawable.Icon, "Dismiss", null).Build();      

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.SetCategory(Notification.CategoryAlarm)
                    .SetSmallIcon(Resource.Drawable.Icon)
                    .SetFullScreenIntent(pendingAlarmIntent, true)
                    .SetContentIntent(pendingAlarmIntent)
                    .SetContentTitle("Napchat Alarm")
                    .SetContentText("Open Alarm")    
                    .SetVisibility((int)NotificationVisibility.Public)
                    .SetPriority((int)NotificationPriority.Max);
          

            #region Setting Alarm Ringtone
            if (ringtone == "default")
            {
                builder.SetSound(RingtoneManager.GetDefaultUri(RingtoneType.Alarm));
            }
            else {
                Android.Net.Uri uri = Android.Net.Uri.Parse(ringtone);
                builder.SetSound(uri);
            }
            #endregion

            #region Setting Vibrate 
            if (vibrate == true)
            {
                /*Vibrate pattern is in milliseconds. First number indicates the time to wait
                 * to start vibrating when notification fires. Second number is the time to vibrate
                 * and then turn off. Subsequent numbers indicate times that the vibration is off,on,off,etc.
                  */
               // builder.SetVibrate(new long[] { 0 , 1000 , 500 , 1000 , 500 , 1000, 500, 1000 });
                builder.SetDefaults((int)NotificationDefaults.Vibrate);
                builder.SetDefaults((int)NotificationDefaults.Vibrate);
            }
            #endregion


            NotificationManager manager = (NotificationManager)context.GetSystemService(Context.NotificationService);
            manager.Notify(ID, builder.Build());
            
        }

        public void Cancel(Alarm alarm, Context context)
        {
            NotificationManager manager = (NotificationManager)context.GetSystemService(Context.NotificationService);

            manager.Cancel(alarm.getID());

        }
    }
}