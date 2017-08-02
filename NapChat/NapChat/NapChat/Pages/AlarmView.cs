using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NapChat.Abstractions;

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
        String[] alarmID;

		public AlarmView (string[] identifier)
		{
            alarmID = identifier;
            TimeDisplayText = System.DateTime.Now.ToString("h:mm tt");

            timeDisplayLabel = new Label()
            {
                Text = TimeDisplayText,
                HorizontalOptions = LayoutOptions.Center,
                TextColor = Color.Aquamarine,
                FontSize = 55,
            };

            dismissBtn = new Button()
            {
                HorizontalOptions = LayoutOptions.Center,
                Text = "Dismiss",
                BackgroundColor = Color.Purple,
                TextColor = Color.White,
                
            };

            snoozeBtn = new Button()
            {
                HorizontalOptions = LayoutOptions.Center,
                Text = "Snooze",
                BackgroundColor = Color.Purple,
                TextColor = Color.White,
            };

            snoozeBtn.Clicked += SnoozeBtn_Clicked;
#if __IOS__
            dismissBtn.Clicked += DismissBtn_Clicked;
#endif
            alarmViewStackLayout = new StackLayout()
            {
                Children = {
                    timeDisplayLabel,
                    dismissBtn,
                    snoozeBtn,
                }
            };
            Content = alarmViewStackLayout;
			
		}

        private void SnoozeBtn_Clicked(object sender, EventArgs e)
        {
            throw new NotImplementedException();
        }
#if __IOS__
        private void DismissBtn_Clicked(object sender, EventArgs e)
        {
            NapChat.iOS.Services.iOSAlarmController alarmController = new iOS.Services.iOSAlarmController();

            alarmController.dismissAlarm(alarmID);
        }
#endif
    }
}