﻿using System;
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
        

        private string ClientID = "827964794033926";

        public LoginPage()
        {

            this.BackgroundColor = Color.Purple;



            //Views

            Image napChatLogo = new Image
            {
                Source = "NapChat_Logo.png",   
                Aspect = Aspect.AspectFit,
                HorizontalOptions = LayoutOptions.Center,
                HeightRequest = 100,
                WidthRequest = 100,
                
            };

            Label welcomeLabel = new Label
            {
                Text = "Welcome to NapChat",
                TextColor = Color.White,
                FontAttributes = FontAttributes.Bold,
                HorizontalOptions = LayoutOptions.Center,
            };


            var loginButton = new Button
            {
                Text = "Login with Facebook",
                TextColor = Color.White,
                BackgroundColor = Color.FromHex("#01579B"),
                Font = Font.SystemFontOfSize(26, FontAttributes.Bold),
                FontSize = 26
            };

          

            //StackLayout Used 
            StackLayout stackLayout = new StackLayout
            {
                Children =
                {
                    welcomeLabel,
                    napChatLogo,
                    loginButton
                }
            };

            this.Content = stackLayout;

            
        }

    }
}

