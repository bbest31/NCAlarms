using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace NapChat.Pages
{
   public partial class HomePage : ContentPage
    {
        public HomePage()
        {
            BackgroundColor = Color.NavajoWhite;

            //views
            Label userCredentials = new Label
            {
                Text = "Print out user credentials here",
                HorizontalOptions = LayoutOptions.CenterAndExpand,
                VerticalOptions = LayoutOptions.Center,
                FontSize = 500,
            };

            Button napTimerButton = new Button {
                HorizontalOptions = LayoutOptions.Center,
                WidthRequest = 200,
                Text = "Naptimer",
                TextColor = Color.White,
                BackgroundColor = Color.MediumPurple,

            };

            napTimerButton.Clicked += async (sender, args) => await Navigation.PushAsync(new NapTimerPage());


            //Layout
            StackLayout homeLayout = new StackLayout
            {
                Children =
                {
                    userCredentials,
                    napTimerButton,

                }
            };
        }
    }
}
