using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using NapChat.Abstractions;
using NapChat.Services;
using Xamarin.Forms;

namespace NapChat.Pages
{
   public partial class HomePage : ContentPage
    {
        ICloudService cloudService = new AzureCloudService();
        Label userNameLabel;

        public HomePage()
        {
            BackgroundColor = Color.White;

            userNameLabel.Text = "Username {name}";
            //views
            Label userCredentials = new Label
            {
                Text = "Username: {name}",
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
                    userNameLabel,
                    napTimerButton,

                }
            };

            DisplayUserName();
        }

        async Task DisplayUserName()
        {
            if (IsBusy)
                return;
            IsBusy = true;

            try
            {
                var identity = await cloudService.GetIdentityAsync();
                if (identity != null)
                {
                    var name = identity.UserClaims.Find(c => c.Type.Equals("name")).Value;
                    
                     userNameLabel.Text = $"Tasks for {name}";
                }
               // var list = await Table.ReadAllItemsAsync();
                //Items.ReplaceRange(list);
            }
            catch (Exception ex)
            {
                await Application.Current.MainPage.DisplayAlert("Items Not Loaded", ex.Message, "OK");
            }
            finally
            {
                IsBusy = false;
            }
        }

    }
}
