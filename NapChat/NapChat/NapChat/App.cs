using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using Microsoft.WindowsAzure.MobileServices;


namespace NapChat
{
    public interface IAuthenticate
    {
        Task<bool> Authenticate();
    }

   

    public class App : Application
    {
        //Singletons

        //This is the link to the database/server
        private static MobileServiceClient client;
        //This is the user
        private static MobileServiceUser user;


        //Controllers

         public static IAuthenticate Authenticator { get; private set; }

         public static void Init(IAuthenticate authenticator)
         {
             Authenticator = authenticator;
         }
         
        public App()
        {
            // The root page is the LoginPage of NapChat  
            MainPage = new LoginPage();

            client =  new MobileServiceClient("http://napchat.azurewebsites.net");




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
