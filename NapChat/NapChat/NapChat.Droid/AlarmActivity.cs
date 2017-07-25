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
using NapChat.Droid.Services;

namespace NapChat.Droid
{
    [Activity(Label = "AlarmActivity")]
    public class AlarmActivity : Activity
    {
        string timeDisplayString;
        int ID;
        int snoozeLength;
        bool vibrate;
        string ringtoneURI;
        string meridianDisplayString;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Window.AddFlags(WindowManagerFlags.TurnScreenOn);
            Window.AddFlags(WindowManagerFlags.ShowWhenLocked);
            Window.AddFlags(WindowManagerFlags.DismissKeyguard);

            SetContentView(Resource.Layout.alarmlayout);

            #region Reference Activity Views
            Button dismissButton = (Button)FindViewById(Resource.Id.dismiss_button);
            Button snoozeButton = (Button)FindViewById(Resource.Id.snooze_button);
            TextView timeDisplay = (TextView)FindViewById(Resource.Id.timedisplay);
            TextView meridianDisplay = (TextView)FindViewById(Resource.Id.merdian_textView);
            #endregion

            dismissButton.Click += DismissButton_Click;
            snoozeButton.Click += SnoozeButton_Click;

            #region Grabs alarm info for snooze reschedule
            snoozeLength = this.Intent.GetIntExtra("SNOOZE", 5);
            ID = this.Intent.GetIntExtra("ID",0);
            vibrate = this.Intent.GetBooleanExtra("VIBRATE", false);
            ringtoneURI = this.Intent.GetStringExtra("URI");
            meridianDisplayString = this.Intent.GetStringExtra("MERIDIAN");
            

            timeDisplayString = this.Intent.GetStringExtra("TIME");
            #endregion

            //Displays the Alarm time in the TextView
            timeDisplay.SetText(timeDisplayString,TextView.BufferType.Normal);

            meridianDisplay.SetText(meridianDisplayString, TextView.BufferType.Normal);

            
        }

        /// <summary>
        /// Click method for Snooze Button that snoozes the alarm for the indicated time.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void SnoozeButton_Click(object sender, EventArgs e)
        {
            DroidAlarmController controller = new DroidAlarmController();
            controller.snoozeAlarm(ID, vibrate, snoozeLength, ringtoneURI);
            Finish();
        }

        /// <summary>
        /// Click method for Dismiss Button that dismiss' the current alarm.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DismissButton_Click(object sender, EventArgs e)
        {
            DroidAlarmController controller = new DroidAlarmController();
            controller.cancelAlarm(ID);
            Finish();
        }


    }
}