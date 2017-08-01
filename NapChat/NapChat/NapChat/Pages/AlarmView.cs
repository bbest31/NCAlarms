using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace NapChat.Pages
{
	public class AlarmView : ContentPage
	{
        #region Views
        Label timeDisplayLabel;
        Button snoozeBtn;
        Button dismissBtn;
        StackLayout alarmViewStackLayout;
        #endregion

        String TimeDisplayText = "HI";

		public AlarmView ()
		{
            timeDisplayLabel = new Label()
            {
                Text = TimeDisplayText,
                HorizontalOptions = LayoutOptions.Center,
                TextColor = Color.Aquamarine,
                FontSize = 55,
            };

            alarmViewStackLayout = new StackLayout()
            {
                Children = {
                    timeDisplayLabel,
                }
            };
            Content = alarmViewStackLayout;
			
		}
	}
}