using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.WindowsAzure.MobileServices;


using Xamarin.Forms;

namespace NapChat
{
    public partial class MainPage : ContentPage
    {
        //This is our link to the server/database
        //Move to NapChat singleton


        public MainPage()
        {
            BackgroundColor = Color.MediumPurple;

            // Track whether the user has authenticated.
            bool authenticated = false;

            //label to display username that has logged in
            Label userLabel = new Label
            {
                Text = "Username",
                TextColor = Color.Black,
                HorizontalOptions = LayoutOptions.Start,
                VerticalOptions = LayoutOptions.Start,
            };

            Label id = new Label {
                Text = "ID",
                TextColor = Color.Black,
                HorizontalOptions = LayoutOptions.Start,
                VerticalOptions = LayoutOptions.Start,
            };

            //Layout
            StackLayout homeLayout = new StackLayout
            {
                BackgroundColor = Color.White,
                Children =
                {
                    userLabel,
                    id
                }
            };

            this.Content = homeLayout;
        }


    }
}
