﻿using System;
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
            // RemoteViews remoteViews = new RemoteViews(getPackageName(),AlarmNotification.xml);
            //Get Extras
            Boolean vibrate = intent.GetBooleanExtra("VIBRATE", false);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.SetCategory(Notification.CategoryAlarm)
                    .SetSmallIcon(Resource.Drawable.Icon)
                    //.SetContent(remoteViews)
                    .SetContentTitle("NapChat Alarm")
                    .SetContentText("Time to wake up!")
                    //.AddAction(Resource.Drawable.Icon, "Snooze", pendingIntent)
                    //.AddAction(Resource.Drawable.Icon, "Dismiss", pendingIntent)
                    .SetContentInfo("")
                    .SetVisibility((int)NotificationVisibility.Public)
                    .SetPriority((int)NotificationPriority.Max)
                    .SetSound(RingtoneManager.GetDefaultUri(RingtoneType.Alarm));

            if(vibrate == true)
            {
                /*Vibrate pattern is in milliseconds. First number indicates the time to wait
                 * to start vibrating when notification fires. Second number is the time to vibrate
                 * and then turn off. Subsequent numbers indicate times that the vibration is off,on,off,etc.
                  */
                builder.SetVibrate(new long[] { 0 , 1000 , 500 , 1000 , 500 , 1000, 500, 1000 });
                //builder.SetDefaults((int)NotificationDefaults.Vibrate);
            }
                   

           
            NotificationManager manager = (NotificationManager)context.GetSystemService(Context.NotificationService);
            manager.Notify(1, builder.Build());
        }
    }
}