using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using NapChat.Pages;
using NapChat.Abstractions;
using NapChat.Services;


namespace NapChat.ViewModel
{
    public class HomePageViewModel : BaseViewModel
    {
        ICloudService CloudService;

        public HomePageViewModel()
        {
            CloudService = new AzureCloudService();
            Title = "Home Page";
           // DisplayUserName = new Command(async () => await ExecuteDisplayUserName());
           // DisplayUserName.Execute(null);
        }

        public Command DisplayUserName { get; }

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
        }

    }
}
