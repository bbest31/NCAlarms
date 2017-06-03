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
            //Toast.MakeText(context, "Received intent!", ToastLength.Short).Show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.SetDefaults((int)NotificationDefaults.All)
                    .SetSmallIcon(Resource.Drawable.Icon)
                    .SetContentTitle("Alarm activated!")
                    .SetContentText("Time to wake up!")
                    .SetContentInfo("Info");

            NotificationManager manager = (NotificationManager)context.GetSystemService(Context.NotificationService);
            manager.Notify(1, builder.Build());
        }
    }
}