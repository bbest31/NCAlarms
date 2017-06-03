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
using System.Threading.Tasks;

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

            this.SetBackgroundColor(Android.Graphics.Color.Indigo);

            Label alarmLabel = new Label
            {
                Text = "Set Alarm",
                TextColor = Color.White,
                HorizontalOptions = LayoutOptions.Center,
            };
            /*WILLY TODO:
             * May switch the Android TimePicker, See which one is easier to grab time from.
             * Seems the AlarmManager uses long ints for milliseconds to determine alarm length.
            */
            Xamarin.Forms.TimePicker timePicker = new Xamarin.Forms.TimePicker()
            {
                Time = new TimeSpan(12, 0, 0),
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
            /*WILLY TODO:
             * Create a onClick method that refers to the SetAlarm() method below.
             */
            //setAlarmButton.Clicked += SetAlarm();

            StackLayout droidTimerLayout = new StackLayout
            {
                BackgroundColor = Color.White,
                VerticalOptions = LayoutOptions.Center,

                Children =
                    {
                    alarmLabel,
                    timePicker,
                    setAlarmButton
                    }

            };
        }

        Context context;
        /// <summary>
        /// Uses an alarm manager to set the alarm for broadcast on the device.
        /// The isRepearing boolean indicates whether the alarm repeats or not.
        /// </summary>
        /// <param name="isRepeating"></param>
        private void SetAlarm(bool isRepeating)
        {

            AlarmManager manager = ((AlarmManager)Context.GetSystemService(Context.AlarmService));
            Intent myIntent = new Intent(context, typeof(AlarmReceiver));
            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.GetBroadcast(context, 0, myIntent, 0);

            /*WILLY TODO:
             * Add a parameter to pass in the alarm time length for method calls to replace SystemClock.ElapsedRealtime...*/
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
