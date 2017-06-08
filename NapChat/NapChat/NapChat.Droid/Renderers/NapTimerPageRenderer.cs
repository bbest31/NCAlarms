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
using NapChat.Helpers;
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

           
            //Views
            Label alarmLabel = new Label
            {
                Text = "Set Alarm",
                TextColor = Color.White,
                HorizontalOptions = LayoutOptions.Center,
            };
           
          
            Xamarin.Forms.TimePicker timePicker = new Xamarin.Forms.TimePicker()
            {
                Time = new TimeSpan(12, 0, 0),
                TextColor = Color.Black,
                HorizontalOptions = LayoutOptions.CenterAndExpand,
                BindingContext = pickerTime,
               

            };
            
            timePicker.SetBinding(Xamarin.Forms.TimePicker.TimeProperty, new Binding("Property") {Mode = BindingMode.OneWayToSource });
            
            //Change to OneWayToSource data binding.
            Xamarin.Forms.Switch awakeSwitch = new Xamarin.Forms.Switch
            {
                HorizontalOptions = LayoutOptions.End,
                VerticalOptions = LayoutOptions.End,

            };

            awakeSwitch.Toggled += AwakeSwitch_Toggled;
            
            Xamarin.Forms.Button setAlarmButton = new Xamarin.Forms.Button
            {
                Text = "Confirm",
                TextColor = Color.White,
                BackgroundColor = Color.Purple,
                BorderColor = Color.Black,
                HorizontalOptions = LayoutOptions.CenterAndExpand,

            };

            setAlarmButton.Clicked += SetAlarmButton_Clicked;

            //Layout
            StackLayout droidTimerLayout = new StackLayout
            {
                BackgroundColor = Color.White,
                VerticalOptions = LayoutOptions.Center,

                Children =
                    {
                    alarmLabel,
                    timePicker,
                    setAlarmButton,
                   // awakeSwitch,
                    }

            };
        }
       
        /// <summary>
        /// Sets the repeating alarm boolean indicator to make the switch toggle.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AwakeSwitch_Toggled(object sender, ToggledEventArgs e)
        {
            awakeNotify = e.Value;
        }

        /// <summary>
        ///  Calls getAlarmLength to get time set by user in TimePicker,
        ///  Passes attached Group name (if any) and NapMessage (if any),
        ///  Determines whether to create a one-time alarm or a repeating alarm
        ///  and then passes the necessary parameters to create the alarm.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void SetAlarmButton_Clicked(object sender, EventArgs e)
        {
             
            //Get Group

            //Get Context

            //Determine if repeating.
            if (!isRepeating)
            {
                alarmLength = convertToLong(pickerTime);
                createAlarm(alarmLength);
            }
            else
            {
                alarmLength = convertToLong(pickerTime);
                createRepeatingAlarm(alarmLength, 0);
            }
        }


        Context context;
        Boolean isRepeating = false;
        Boolean awakeNotify;
        TimeSpan pickerTime;
        long alarmLength = 0;
        /// <summary>
        /// Converts DateTime object to type long.
        /// </summary>
        /// <param name="dateTime"></param>
        /// <returns>long int object</returns>
        public long convertToLong(TimeSpan timeSpan)
        {
            
            long timeLong = timeSpan.Ticks;
            return timeLong;
        }
        /// <summary>
        /// Uses an alarm manager to set the alarm for broadcast on the device.
        /// Also builds NapAlert to then update the NapLog and then finally sends the push notifications.
        /// </summary>
        /// <param name="isRepeating"></param>
        private void createAlarm(long alarmlengthMilli)
        {

            AlarmManager manager = ((AlarmManager)Context.GetSystemService(Context.AlarmService));
            Intent myIntent = new Intent(context, typeof(AlarmReceiver));
            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.GetBroadcast(context, 0, myIntent, 0);

            /*TODO:
             * Use parameter to pass in the alarm time length for method calls to replace SystemClock.ElapsedRealtime...
             Also we will use a Builder class to make Nap-Alerts for the Nap-Log and Push notifications.*/
            
             manager.Set(AlarmType.RtcWakeup, SystemClock.ElapsedRealtime()+3000/*alarmlengthMilli*/, pendingIntent);


            //Call builder class here.
         // NapAlertFactory.buildNapAlert();
            //Update Nap-Log here.

            //Send notifications here.




             ((NapTimerPage)Element).backToHome();

        }
        /// <summary>
        /// Uses the AlarmManager to create a alarm for broadcast on the device that repeats at certains day(s)/time(s).
        /// Also builds NapAlert to then update the NapLog and then finally sends the push notifications.
        /// </summary>
        /// <param name="alarmLengthMilli"></param>
        /// <param name="alarmInterval"></param>
        private void createRepeatingAlarm(long alarmLengthMilli, long alarmInterval)
        {
            AlarmManager manager = ((AlarmManager)Context.GetSystemService(Context.AlarmService));
            Intent myIntent = new Intent(context, typeof(AlarmReceiver));
            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.GetBroadcast(context, 0, myIntent, 0);

            manager.SetRepeating(AlarmType.RtcWakeup, SystemClock.ElapsedRealtime()+3000/*alarmLengthMilli*/, 60 * 1000, pendingIntent);

            //Call builder class here.

            //Update Nap-Log here.

            //Send notifications here.




            ((NapTimerPage)Element).backToHome();

        }
    }         
    }
