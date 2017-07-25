using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace NapChat.Pages
{
	public class RingtonePage : ContentPage
	{
        //Views
        ListView ringtonesListView;

		public RingtonePage ()
		{
            Title = "Ringtones";

            ringtonesListView = new ListView()
            {
                
            };

			Content = new StackLayout {
				Children = {
					new Label { Text = "Welcome to Xamarin Forms!" }
				}
			};

            
		}
	}
}