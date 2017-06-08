using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Foundation;
using UIKit;
using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using NapChat.iOS.Renderers;
using NapChat.Pages;

[assembly: ExportRenderer(typeof(NapTimerPage), typeof(NapTimerPageRenderer))]

namespace NapChat.iOS.Renderers
{
    /// <summary>
    /// iOS Page Renderer for NapTimerPage.
    /// </summary>
    public class NapTimerPageRenderer : PageRenderer
    {
        protected override void OnElementChanged(VisualElementChangedEventArgs e)
        {
            base.OnElementChanged(e);
            if (e.OldElement != null || Element == null)
            {
                return;
            }

            //Plaftorm specific code goes here.
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

            timePicker.SetBinding(Xamarin.Forms.TimePicker.TimeProperty, new Binding("Property") { Mode = BindingMode.OneWayToSource });

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
            StackLayout iOSTimerLayout = new StackLayout
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

        //Local variables
       
        Boolean isRepeating = false;
        Boolean awakeNotify;
        TimeSpan pickerTime;
        long alarmLength = 0;

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
               // alarmLength = convertToLong(pickerTime);
                createAlarm();
            }
            else
            {
                //alarmLength = convertToLong(pickerTime);
                //createRepeatingAlarm(alarmLength, 0);
            }
        }

        private void createAlarm()
        {
            UILocalNotification notification = new UILocalNotification();

            NSDate.FromTimeIntervalSinceNow(15);
            notification.AlertAction = "View Alert";
            notification.AlertBody = "Your 15 second alert has fired";
            notification.SoundName = UILocalNotification.DefaultSoundName;
            //Schedule notificaiton
            UIApplication.SharedApplication.ScheduleLocalNotification(notification);
        }

        private void createRepeatingAlarm()
        {

        }
    }
}