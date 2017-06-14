using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using NapChat.Pages;
using NapChat.Abstractions;
using NapChat.Services;
using NapChat.Helpers;
using Xamarin.Forms;


namespace NapChat.ViewModel
{
    public class HomePageViewModel : BaseViewModel
    {
       // ICloudService CloudService;

        public HomePageViewModel()
        {
            //CloudService = new AzureCloudService();
       //     CloudService = NapChatSingletons.CloudService;
            Title = "Home Page";
           // DisplayUserName = new Command(async () => await ExecuteDisplayUserName());
           // DisplayUserName.Execute(null);
        }

        /// <summary>
        /// Clicked event method for Menu button to navigate to NapTimerPage.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="args"></param>
        protected async void napTimerClicked(object sender, EventArgs args)
        {
            //goes to the nap timer page
            Page homepage = Application.Current.MainPage;
            await homepage.Navigation.PushAsync(new NapTimerPage());
        }

      /*  public Command DisplayUserName { get; }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        async Task ExecuteDisplayUserName()
        {
            if (IsBusy)
                return;
            IsBusy = true;

            try
            {
                var identity = await CloudService.GetIdentityAsync();
                if (identity != null)
                {
                    var name = identity.UserClaims.Find(c => c.Type.Equals("name")).Value;
                     Title = $"Tasks for {name}";
                }
                //var list = await Table.ReadAllItemsAsync();
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
        }*/

    }
}
