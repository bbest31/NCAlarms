using System;
using System.Threading.Tasks;
using NapChat.Abstractions;
using Xamarin.Forms;
using NapChat.Helpers;
using NapChat.Services;
using NapChat.Pages;
using System.Diagnostics;

namespace NapChat.ViewModel
{
    public class LoginPageViewModel : BaseViewModel
    {
        public LoginPageViewModel()
        {
            Title = "NapChat";
            LoginCommand = new Command(async () => await ExecuteLoginCommand());
        }
         
        public Command LoginCommand { get; }

        async Task ExecuteLoginCommand()
        {
            if (IsBusy)
                return;
            IsBusy = true;

            try
            {
                //using = NapChatSingletons.CloudService; breaks auth for some reason.
                var cloudService = new AzureCloudService();
                await cloudService.LoginAsync();
                
                Application.Current.MainPage = new NavigationPage(new AlarmPage());
            }

            catch(Exception ex)
            {
                Debug.WriteLine($"[ExecuteLoginCommand] Error = {ex.Message}");
                await Application.Current.MainPage.DisplayAlert("Login Failed", ex.Message, "OK");
            }
            finally
            {
                IsBusy = false;
            }
        }
    }
}
