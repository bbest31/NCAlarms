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
using NapChat.Droid.Broadcast;

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
       
        
        Label alarmLabel = new Label
        {
            Text = "Set Alarm",
            TextColor = Color.White,
            HorizontalOptions = LayoutOptions.Center,
        };

        Xamarin.Forms.TimePicker timePicker = new Xamarin.Forms.TimePicker()
        {
            Time = new TimeSpan (12,0,0),
            TextColor = Color.Black,

        };

        Xamarin.Forms.Button setAlarmButton = new Xamarin.Forms.Button
        {
            Text = "Confirm",
            TextColor = Color.White,
            BackgroundColor = Color.Purple,
            BorderColor = Color.Black,
            HorizontalOptions = LayoutOptions.CenterAndExpand,
            
        };
        StackLayout droidTimerLayout = new StackLayout()
        {
           
        };
       
        /// <summary>
        /// Uses an alarm manager to set the alarm for broadcast on the device.
        /// The isRepearing boolean indicates whether the alarm repeats or not.
        /// </summary>
        /// <param name="isRepeating"></param>
          private void SetAlarm(bool isRepeating)
        {

            AlarmManager manager = (AlarmManager)GetSystemService(Context.AlarmService);
            Intent myIntent = new Intent(this,typeof(AlarmReceiver));
            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.GetBroadcast(this, 0, myIntent, 0);

            
            if (!isRepeating)
            {
                manager.Set(AlarmType.RtcWakeup, SystemClock.ElapsedRealtime() + 3000, pendingIntent);
            }
            else
            {
                manager.SetRepeating(AlarmType.RtcWakeup, SystemClock.ElapsedRealtime() + 3000, 60 * 1000, pendingIntent);
            }

            ((NapTimerPage)Element).backToHome();

        }
         
    }
}