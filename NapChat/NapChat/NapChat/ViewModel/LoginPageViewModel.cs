using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using NapChat.Abstractions;
using NapChat.Helpers;
using Xamarin.Forms;

namespace NapChat.ViewModel
{
    public class LoginPageViewModel : BaseViewModel
    {
        public LoginPageViewModel()
        {
            Title = "NapChat";
        }
        Command loginCmd;
        public Command LoginCommand => loginCmd ?? (loginCmd = new Command(async () => await ExecuteLoginCommand().ConfigureAwait(false)));

        async Task ExecuteLoginCommand()
        {
            if (IsBusy)
                return;
            IsBusy = true;

            try
            {
                var cloudService = ServiceLocator.Instance.Resolve<ICloudService>();
                await cloudService.LoginAsync();
                Application.Current.MainPage = new NavigationPage(new Pages.HomePage());
            }

            catch(Exception ex)
            {
                Debug.WriteLine($"[ExecuteLoginCommand] Error = {ex.Message}");
            }
            finally
            {
                IsBusy = false;
            }
        }
    }
}
