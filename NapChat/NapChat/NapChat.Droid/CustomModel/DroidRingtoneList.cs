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
using NapChat.Abstractions;
using NapChat.Droid.CustomModel;
using Android.Media;
using System.Threading.Tasks;
using Xamarin.Forms;

[assembly: Xamarin.Forms.Dependency(typeof(DroidRingtoneList))]
namespace NapChat.Droid.CustomModel
{
    public class DroidRingtoneList : IRingtoneList
    {
        public string uri { get; set; }

        public string getURI()
        {
            return this.uri;
        }

        public void setURI(string newUri)
        {
            this.uri = newUri;
        }

        /// <summary>
        /// Goes to a ringtone picker activity and retrieves the selected tones uri as a string.
        /// </summary>
        public void pickRingtone()
        {
            
            Intent intent = new Intent(RingtoneManager.ActionRingtonePicker);
            intent.PutExtra(RingtoneManager.ExtraRingtoneShowSilent, false);
            intent.PutExtra(RingtoneManager.ExtraRingtoneTitle, "Select a ringtone");
            intent.PutExtra(RingtoneManager.ExtraRingtoneShowDefault, false);
            intent.PutExtra(RingtoneManager.ExtraRingtoneType, (int)RingtoneType.Alarm);
            intent.PutExtra(RingtoneManager.ExtraRingtoneExistingUri, RingtoneManager.GetDefaultUri(RingtoneType.Alarm));
            //intent.SetFlags(ActivityFlags.);

            var activity = Xamarin.Forms.Forms.Context as Activity;
            

            activity.StartActivityForResult(intent, 0); 
            
                     
        }

        

      


    }
}