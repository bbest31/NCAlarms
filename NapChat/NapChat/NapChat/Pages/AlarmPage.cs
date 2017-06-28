using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace NapChat.Pages
{
	public class AlarmPage : ContentPage
	{
        
        
        /// <summary>
        /// Opens page to add or edit an alarm.
        /// </summary>
        /// <returns></returns>
        public async Task openNapTimer()
        {
            await Navigation.PushAsync(new NapTimerPage());
        }

        /// <summary>
        /// Goes back to Home Page
        /// </summary>
        /// <returns></returns>
        public async Task backToHome()
        {
            await Navigation.PopAsync();
        }
    }
}