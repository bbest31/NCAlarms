using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;
using NapChat.ViewModel;
using NapChat.Abstractions;

namespace NapChat.Pages
{
    public partial class LoginPage : ContentPage
    {
        public LoginPage()
        {
            LoginPageViewModel loginViewModel = new LoginPageViewModel();

            //Views
            Image NCLogo = new Image
            {
                Source = "",
            };

            Label welcomeLabel = new Label
            {
                Text = "Welcome to NapChat",
                TextColor = Color.White,
                HorizontalOptions = LayoutOptions.Center,
                FontSize = 26,                
            };

            Button facebookLoginButton = new Button
            {
                Text = "Login using Facebook",
                TextColor  = Color.White,
                BackgroundColor = Color.DarkBlue,
                HorizontalOptions = LayoutOptions.CenterAndExpand,
                WidthRequest = 250,
                
            };
            //Need to have the onClick method for facebookLoginButton be the LoginCommand from LoginViewModel
           // facebookLoginButton.Clicked += loginViewModel.LoginCommand;

            //Layout
            StackLayout loginLayout = new StackLayout
            {
                Children =
                {
                    welcomeLabel,
                    facebookLoginButton,
                }
            };

            BindingContext = new LoginPageViewModel();
        }
    }
}
