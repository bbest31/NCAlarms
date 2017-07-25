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

            #region Get Extras
            Boolean vibrate = intent.GetBooleanExtra("Vibrate", false);         
            string ringtone = intent.GetStringExtra("Uri");
            int ID = intent.GetIntExtra("Id", 0);
            int snoozeLength = intent.GetIntExtra("Snooze", 5);
            string displayTime = intent.GetStringExtra("Time");
            string displayMeridian = intent.GetStringExtra("Meridian");
            #endregion

            #region Pass parameters to AlarmActivity so it can have same settings for snooze refire.
            Intent alarmIntent = new Intent(context, typeof(AlarmActivity));
            alarmIntent.PutExtra("SNOOZE", snoozeLength);
            alarmIntent.PutExtra("VIBRATE", vibrate);
            alarmIntent.PutExtra("URI", ringtone);
            alarmIntent.PutExtra("ID", ID);
            alarmIntent.PutExtra("TIME", displayTime);
            alarmIntent.PutExtra("MERIDIAN", displayMeridian);
            #endregion
            PendingIntent pendingAlarmIntent = PendingIntent.GetActivity(context, ID, alarmIntent, PendingIntentFlags.OneShot);
     

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            #region Build Notification
            builder.SetCategory(Notification.CategoryAlarm)
                    .SetSmallIcon(Resource.Drawable.Icon)
                    .SetFullScreenIntent(pendingAlarmIntent, true)
                    .SetContentIntent(pendingAlarmIntent)
                    .SetContentTitle("Napchat Alarm")
                    .SetContentText("Open Alarm")    
                    .SetVisibility((int)NotificationVisibility.Public)
                    .SetPriority((int)NotificationPriority.Max);
            #endregion

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

        /// <summary>
        /// Cancels a current alarm.
        /// </summary>
        /// <param name="ID"></param>
        /// <param name="context"></param>
        public void Cancel(int ID, Context context)
        {
            NotificationManager manager = (NotificationManager)context.GetSystemService(Context.NotificationService);

            manager.Cancel(ID);

        }
    }
}