using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace NapChat.Pages
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class NapTimerPage : ContentPage
	{
		public NapTimerPage ()
		{
			InitializeComponent ();
		}

        private void BackButton_Clicked(object sender, EventArgs e)
        {
            Navigation.PopAsync();
        }
    }
}