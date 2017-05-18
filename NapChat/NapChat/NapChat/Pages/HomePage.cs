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
            //views
            Label userCredentials = new Label
            {
                Text = "Print out user credentials here",
                HorizontalOptions = LayoutOptions.CenterAndExpand,
                VerticalOptions = LayoutOptions.Center,
                FontSize = 500,
            };


            //Layout
            StackLayout homeLayout = new StackLayout
            {
                Children =
                {
                    userCredentials,
                }
            };
        }
    }
}
