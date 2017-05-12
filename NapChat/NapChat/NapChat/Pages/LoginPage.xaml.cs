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
        //Move to NapChat singleton
        public static MobileServiceClient MobileService = new MobileServiceClient("https://napchat.azurewebsites.net");

        private string ClientID = "827964794033926";

        public LoginPage()
        {




            //Views

            Image napChatLogo = new Image
            {
                Source = "napchaticon",
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

            var loginButton = new Button
            {
                Text = "Login with Facebook",
                TextColor = Color.White,
                BackgroundColor = Color.FromHex("#01579B"),
                Font = Font.BoldSystemFontOfSize(26),
                FontSize = 26
            };

            loginButton.Clicked += LoginWithFacebook_Clicked;

            //StackLayout Used 
            StackLayout stackLayout = new StackLayout
            {
                Children =
                {
                    welcomeLabel,
                    loginUsernameEntry,
                    loginButton
                }
            };
            InitializeComponent();
        }

            private async void LoginWithFacebook_Clicked(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new MainPage());
        }

    
    }
    }

