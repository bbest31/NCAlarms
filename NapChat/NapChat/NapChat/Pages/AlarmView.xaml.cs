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
	public partial class AlarmView : ContentPage
	{
        String TimeDisplayText = "hi";

		public AlarmView ()
		{
			InitializeComponent ();
            this.BindingContext = TimeDisplayText;
		}


	}
}