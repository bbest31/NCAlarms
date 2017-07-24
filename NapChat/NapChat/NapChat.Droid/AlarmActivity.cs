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

namespace NapChat.Droid
{
    [Activity(Label = "AlarmActivity")]
    public class AlarmActivity : Activity
    {
        string timeDisplayString;
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

            #region Grabs alarm info for snooze reschedule
            int snoozeLength = this.Intent.GetIntExtra("SNOOZE", 5);
            int ID = this.Intent.GetIntExtra("ID",0);
            bool vibrate = this.Intent.GetBooleanExtra("VIBRATE", false);
            string ringtoneURI = this.Intent.GetStringExtra("URI");
            
            string meridian = ringtoneURI[];

            timeDisplayString = this.Intent.GetStringExtra("TIME");
            #endregion

            //Displays the Alarm time in the TextView
            timeDisplay.SetText(timeDisplayString,TextView.BufferType.Normal);
            meridianDisplay.SetText();
            
        }


    }
}