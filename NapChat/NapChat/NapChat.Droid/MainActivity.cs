using System;
using Xamarin.Forms;
using NapChat.Droid.Services;
using NapChat.Abstractions;
using Android.App;
using Android.Content.PM;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Microsoft.WindowsAzure.MobileServices;
using Android.Content;
using Android.Media;
using Android.Util;

namespace NapChat.Droid
{
    [Activity(Label = "NapChat", Icon = "@drawable/icon", MainLauncher = true, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation)]
    public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsApplicationActivity
    {
        protected override void OnCreate(Bundle bundle)
        {

            base.OnCreate(bundle);
            Microsoft.WindowsAzure.MobileServices.CurrentPlatform.Init();

            global::Xamarin.Forms.Forms.Init(this, bundle);

            ((DroidLoginProvider)DependencyService.Get<ILoginProvider>()).Init(this);

            LoadApplication(new NapChat.App());
        }

        protected override void OnActivityResult(int requestCode, [GeneratedEnum] Result resultCode, Intent data)
        {
            base.OnActivityResult(requestCode, resultCode, data);
            if (resultCode != Result.Ok)
            {

                return;
            }
            else
            {
                Android.Net.Uri uri = (Android.Net.Uri)data.GetParcelableExtra(RingtoneManager.ExtraRingtonePickedUri);
                Log.Debug("onActivityResult====", "" + uri);

                Toast.MakeText(this, uri + "", ToastLength.Short).Show();
                if (uri != null)
                {
                    switch (requestCode)
                    {
                        case 0:
                            MessagingCenter.Send<MainActivity, string>(this,"URI", uri.ToString());
                            break;
                    }
                }
            }
        }
    }
}



