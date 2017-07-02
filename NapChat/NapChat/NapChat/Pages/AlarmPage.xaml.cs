using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NapChat.ViewModel;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace NapChat.Pages
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class AlarmPage : ContentPage
	{
		public AlarmPage ()
		{
			InitializeComponent ();
            BindingContext = new AlarmPageViewModel();
		}

        private void TapGestureRecognizer_Tapped(object sender, EventArgs e)
        {
            
            Navigation.PushAsync(new CreateAlarmPage());
        }
    }
}