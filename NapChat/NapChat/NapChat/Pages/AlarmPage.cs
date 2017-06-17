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

        public async Task backToHome()
        {
            await Navigation.PopAsync();
        }
    }
}