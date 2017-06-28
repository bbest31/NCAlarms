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
    public partial class HomePage : ContentPage
    {
        public HomePage()
        {
            InitializeComponent();
            BindingContext = new HomePageViewModel();
        }

        private void Button_Clicked(object sender, EventArgs e)
        {

        }
    }
}
