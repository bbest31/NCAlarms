using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NapChat.ViewModel;

using Xamarin.Forms;

namespace NapChat.Pages
{
	public partial class HomePage : ContentPage
	{
		public HomePage ()
		{
			InitializeComponent ();
            BindingContext = new HomePageViewModel();
		}
	}
}
