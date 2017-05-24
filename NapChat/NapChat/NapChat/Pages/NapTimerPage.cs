using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Emit;
using System.Text;

using Xamarin.Forms;

namespace NapChat.Pages
{
	public class NapTimerPage : ContentPage
	{
		public NapTimerPage ()
		{
            //Views
            TimePicker timerpicker = new TimePicker
            {
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center,
            };

            Picker groupPicker = new Picker
            {
                HorizontalOptions = LayoutOptions.Center,
                HeightRequest = 100,

            };

            Editor contextEditor = new Editor
            {
                HorizontalOptions = LayoutOptions.Center,
                HeightRequest = 300,

            };

            Switch awakeSwitch = new Switch
            {
                HorizontalOptions = LayoutOptions.Center,

            };

            Button setTimerButton = new Button
            {
                Text = "OK",
                TextColor = Color.Black,
                BackgroundColor = Color.White,
                BorderColor = Color.Purple,
                HorizontalOptions = LayoutOptions.Center,

            };

            //Layout
			Content = new StackLayout {
				Children = {
					timerpicker,
                    groupPicker,
                    contextEditor,
                    awakeSwitch,
                    setTimerButton,

				}
			};
		}
	}
}
