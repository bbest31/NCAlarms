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

namespace NapChat.Droid.Broadcast
{
    [BroadcastReceiver(Enabled =true)]
    public class AlarmReceiver : BroadcastReceiver
    {
        public override void OnReceive(Context context, Intent intent)
        {
            /*
            // Set up an intent so that tapping the notifications returns to this app:
            Intent intent = new Intent(this, typeof(MainActivity));

            // Create a PendingIntent; we're only using one PendingIntent (ID = 0):
            const int pendingIntentId = 0;
            PendingIntent pendingIntent =
                PendingIntent.GetActivity(this, pendingIntentId, intent, PendingIntentFlags.OneShot);
             */
            //Toast.MakeText(context, "Received intent!", ToastLength.Short).Show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.SetDefaults((int)NotificationDefaults.Sound | (int)NotificationDefaults.Vibrate)
                    .SetCategory(Notification.CategoryAlarm)
                    .SetSmallIcon(Resource.Drawable.Icon)
                    .SetContentTitle("NapChat Alarm")
                    .SetContentText("Time to wake up!")
                    .SetVisibility((int)NotificationVisibility.Public)
                    .SetContentInfo("Info");
                    //.SetContentIntent(pendingIntent);
            //to set ringtone to default or set sound
            //builder.SetSound (RingtoneManager.GetDefaultUri(RingtoneType.Alarm));
            //builder.SetSound (RingtoneManager.GetDefaultUri(RingtoneType.Ringtone));

            //Make the notification link to a certain page in the app
          
            NotificationManager manager = (NotificationManager)context.GetSystemService(Context.NotificationService);
            manager.Notify(1, builder.Build());
        }
    }
}