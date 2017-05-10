using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace NapChat
{
    public class App : Application
    {
        public App()
        {
            // The root page is the LoginPage of NapChat
            /*TODO:Find the proper way to load to the Login Page seeing as it goes to some blank black page before opening it.
             */  
            MainPage = new LoginPage();

           
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
