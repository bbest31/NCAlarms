﻿using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using NapChat.Abstractions;
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
                Application.Current.MainPage = new NavigationPage(new Pages.HomePage());
            }
            catch(Exception ex)
            {
                Debug.WriteLine($"[Login] Error = {ex.Message}");
            }
            finally
            {
                IsBusy = false;
            }
        }
    }
}