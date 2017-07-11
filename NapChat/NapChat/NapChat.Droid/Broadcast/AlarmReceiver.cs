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
            //string pckname;
            //RemoteViews contentViews = new RemoteViews(Package.,Resource.);
            /*
             * contentView.setTextView(R.id.title,<take in string of trigger time>)
             * */
            //Get Extras
            Boolean vibrate = intent.GetBooleanExtra("Vibrate", false);
            //System.Diagnostics.Debug.WriteLine("Vibrate Bool Given to AlarmReceiver: " + vibrate);
            string ringtone = intent.GetStringExtra("Uri");
            //System.Diagnostics.Debug.WriteLine("Ringtone Given to AlarmReceiver: " + ringtone);
            int ID = intent.GetIntExtra("Id",0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.SetCategory(Notification.CategoryAlarm)
                    .SetSmallIcon(Resource.Drawable.Icon)
                    //.SetContent(contentViews)
                    .SetContentTitle("NapChat Alarm")
                    .SetContentText("Time to wake up!")
                    //.AddAction(Resource.Drawable.Icon, "Snooze", pendingIntent)
                    //.AddAction(Resource.Drawable.Icon, "Dismiss", pendingIntent)
                    .SetContentInfo("")
                    .SetVisibility((int)NotificationVisibility.Public)
                    .SetPriority((int)NotificationPriority.Max);

            if (ringtone == "default")
            {
                builder.SetSound(RingtoneManager.GetDefaultUri(RingtoneType.Alarm));
            }
            else {
                Android.Net.Uri uri = Android.Net.Uri.Parse(ringtone);
                builder.SetSound(uri);
            }

            if(vibrate == true)
            {
                /*Vibrate pattern is in milliseconds. First number indicates the time to wait
                 * to start vibrating when notification fires. Second number is the time to vibrate
                 * and then turn off. Subsequent numbers indicate times that the vibration is off,on,off,etc.
                  */
               // builder.SetVibrate(new long[] { 0 , 1000 , 500 , 1000 , 500 , 1000, 500, 1000 });
                builder.SetDefaults((int)NotificationDefaults.Vibrate);
            }
                   

           
            NotificationManager manager = (NotificationManager)context.GetSystemService(Context.NotificationService);
            manager.Notify(ID, builder.Build());
        }
    }
}