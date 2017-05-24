using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;
using NapChat.Services;
using NapChat.Abstractions;
using System.Linq;
using System.Threading.Tasks;
using System.Diagnostics;

namespace NapChat.ViewModel
{
    public class HomePageViewModel : BaseViewModel
    {
        ICloudService cloudService;

        public Command LogoutCommand { get; }

        public HomePageViewModel()
        {
            Debug.WriteLine("In TaskListViewMOdel");
         

        }



    }
}
