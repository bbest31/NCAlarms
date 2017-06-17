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
using Android.Content;
using Microsoft.IdentityModel.Clients.ActiveDirectory;

namespace NapChat.Droid
{
	[Activity (Label = "NapChat", Icon = "@drawable/icon", MainLauncher = true, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation)]
	public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsApplicationActivity
	{
		protected override void OnCreate (Bundle bundle)
		{

			base.OnCreate (bundle);
            Microsoft.WindowsAzure.MobileServices.CurrentPlatform.Init();

			global::Xamarin.Forms.Forms.Init (this, bundle);

            ((DroidLoginProvider)DependencyService.Get<ILoginProvider>()).Init(this);

            LoadApplication (new NapChat.App ());
		}

        protected override void OnActivityResult(int requestCode, Result resultCode, Intent data)
        {
            base.OnActivityResult(requestCode, resultCode, data);
            AuthenticationAgentContinuationHelper.SetAuthenticationAgentContinuationEventArgs(requestCode, resultCode, data);
        }
    }

}

