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
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Window.AddFlags(WindowManagerFlags.TurnScreenOn);
            Window.AddFlags(WindowManagerFlags.ShowWhenLocked);
            Window.AddFlags(WindowManagerFlags.DismissKeyguard);

            SetContentView(Resource.Layout.alarmlayout);

            // Create your application here
            Button dismissButton = (Button)FindViewById(Resource.Id.dismiss_button);
            Button snoozeButton = (Button)FindViewById(Resource.Id.snooze_button);
            TextView timeDisplay = (TextView)FindViewById(Resource.Id.timedisplay);

            #region Grabs alarm info for snooze reschedule
            this.Intent.GetIntExtra("SNOOZE", 5);
            this.Intent.GetIntExtra("ID",0);
            this.Intent.GetBooleanExtra("VIBRATE", false);
            this.Intent.GetStringExtra("URI");
            this.Intent.GetStringExtra("TIME");
            #endregion


            
        }


    }
}