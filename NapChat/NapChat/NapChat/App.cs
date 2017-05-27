using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NapChat.Abstractions;
using NapChat.Services;
using NapChat.Model;
using Xamarin.Forms;
using NapChat.Pages;

namespace NapChat
{
	public class App : Application
	{
        //Might want to implement a singleton manager class
        public static ICloudService CloudService { get; set; }
		public App ()
		{
            CloudService = new AzureCloudService();

            // The root page of your application
            MainPage = new NavigationPage(new LoginPage());
		}

		protected override void OnStart ()
		{
			// Handle when your app starts
		}

		protected override void OnSleep ()
		{
			// Handle when your app sleeps
		}

		protected override void OnResume ()
		{
			// Handle when your app resumes
		}
	}
}
