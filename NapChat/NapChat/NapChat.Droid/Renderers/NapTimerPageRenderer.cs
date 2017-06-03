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
using NapChat.Pages;
using NapChat.Droid.Renderers;

//https://github.com/eddydn/XamarinAlarmDemo

[assembly: ExportRenderer (typeof(NapTimerPage),typeof(NapTimerPageRenderer))]
namespace NapChat.Droid.Renderers
{
    /// <summary>
    /// Android Page Renderer for NapTimerPage.
    /// </summary>
    public class NapTimerPageRenderer : PageRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Page> e)
        {
            base.OnElementChanged(e);
            if (e.OldElement != null || Element == null)
            {
                return;
            }
        }
        /*  private void SetAlarm(Context packagedContext)
        {
            AlarmManager manager = (AlarmManager)GetSystemService(Context.AlarmService);
            Intent myIntent = new Intent(packagedContext,typeof(AlarmReceiver));
            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.GetBroadcast(packagedContext, 0, myIntent, 0);

            manager.Set(AlarmType.RtcWakeup, SystemClock.ElapsedRealtime() + 3000, pendingIntent);

            TimePicker timepicker;

        }
         */
    }
}