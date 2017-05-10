using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.WindowsAzure;

using Xamarin.Forms;
using Microsoft.WindowsAzure.MobileServices;

namespace NapChat
{
    public partial class LoginPage : ContentPage
    {
        //This is our link to the server/database
        public static MobileServiceClient MobileService = new MobileServiceClient("https://napchat.azurewebsites.net");

        public LoginPage()
        {




            //Views

            Image napChatLogo = new Image
            {
                Source="napchaticon",
                HorizontalOptions = LayoutOptions.Center,
            };

            Label welcomeLabel = new Label
            {
                Text = "Welcome to NapChat",
                TextColor = Color.White,
                FontAttributes = FontAttributes.Bold,
                HorizontalOptions = LayoutOptions.Center,
            };

            Entry loginUsernameEntry = new Entry
            {
                Placeholder = "Username",
                PlaceholderColor = Color.DimGray,
                
            };

            //StackLayout Used 
            StackLayout stackLayout = new StackLayout
            {
                Children =
                {
                    welcomeLabel
                }
            };
            InitializeComponent();


        }
    }
}
