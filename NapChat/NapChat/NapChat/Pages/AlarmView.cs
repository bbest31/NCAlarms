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

        String TimeDisplayText;
      
#if __IOS__
        UserNotifications.UNNotificationRequest request;
#endif

        public AlarmView (
#if __IOS__
            UserNotifications.UNNotificationRequest request
#endif
            )
		{
#if __IOS__
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

            dismissBtn.Clicked += DismissBtn_Clicked;

            alarmViewStackLayout = new StackLayout()
            {
                Children = {
                    timeDisplayLabel,
                    dismissBtn,
                    snoozeBtn,
                }
            };
            Content = alarmViewStackLayout;
#endif   
        }

#if __IOS__
        /// <summary>
        /// Snooze button click method.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private  void SnoozeBtn_Clicked(object sender, EventArgs e)
        {
            NapChat.iOS.Services.iOSAlarmController alarmController = new iOS.Services.iOSAlarmController();
            //Get alarm by id so we can grab the set snooze length.
            alarmController.snoozeAlarm(request);
            Navigation.PopAsync();

        }
        /// <summary>
        /// Dismiss button click method.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DismissBtn_Clicked(object sender, EventArgs e)
        {
            NapChat.iOS.Services.iOSAlarmController alarmController = new iOS.Services.iOSAlarmController();

            alarmController.dismissAlarm(request.Identifier);
            Navigation.PopAsync();
        }
#endif
    }
}