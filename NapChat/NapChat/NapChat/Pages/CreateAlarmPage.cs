using System;
using System.Collections.Generic;
using System.Text;

using Xamarin.Forms;
using NapChat.Abstractions;
using NapChat.RenderedViews;
using NapChat.Model;

namespace NapChat.Pages
{
    public class CreateAlarmPage : ContentPage
    {
        //Views
        CustomTimePicker timePicker;
        Button cancelButton;
        Button createAlarmButton;
        Button createAndActivateButton;
        StackLayout layout;
        StackLayout vibrateLayout;
        ScrollView scrollView;
        Switch vibrateSwitch;
        Label vibrateLabel;

        //Binded Attributes
        TimeSpan pickerTime;
        Boolean awakeNotify;
        Boolean isVibrate;

        public CreateAlarmPage()
        {
            Title = "Create Alarm";

            timePicker = new CustomTimePicker()
            { 
                HorizontalOptions = LayoutOptions.Fill,
                VerticalOptions = LayoutOptions.Start,
                BindingContext = pickerTime,
                HeightRequest = 100,
                BackgroundColor = Color.Lavender,
            };
            timePicker.SetBinding(Xamarin.Forms.TimePicker.TimeProperty, new Binding("Time") { Mode = BindingMode.OneWayToSource });

            vibrateLabel = new Label()
            {
                HorizontalOptions = LayoutOptions.Start,
                Text = "Vibrate on Alarm:",
                TextColor = Color.Purple,

            };
            vibrateSwitch = new Switch()
            {
                //HorizontalOptions = LayoutOptions.End,
                VerticalOptions = LayoutOptions.End,
                IsToggled = false,
                BindingContext = isVibrate,
            };

            vibrateLayout = new StackLayout()
            {
                Orientation = StackOrientation.Horizontal,
                Children =
                {
                    vibrateLabel,
                    vibrateSwitch,
                }
            };


            vibrateSwitch.SetBinding(Xamarin.Forms.Switch.IsToggledProperty, new Binding("IsToggled") { Mode = BindingMode.OneWayToSource });

            cancelButton = new Button()
            {
                Text = "Cancel",
                TextColor = Color.White,
                BackgroundColor = Color.Red,
                HorizontalOptions = LayoutOptions.Fill,
                VerticalOptions = LayoutOptions.End,
                HeightRequest = 60,
            };
            cancelButton.Clicked += CancelButton_Clicked;

            createAlarmButton = new Button()
            {
                Text = "Create",
                TextColor = Color.LightSkyBlue,
                BackgroundColor = Color.White,
                HorizontalOptions = LayoutOptions.Fill,
                VerticalOptions = LayoutOptions.End,
                HeightRequest = 60,
                BorderColor = Color.LightSkyBlue,
                BorderWidth = 1
            };

            createAlarmButton.Clicked += CreateAlarmButton_Clicked;

            createAndActivateButton = new Button()
            {
                Text = "Create and Activate",
                TextColor = Color.White,
                BackgroundColor = Color.Blue,
                HorizontalOptions = LayoutOptions.Fill,
                VerticalOptions = LayoutOptions.End,
                HeightRequest = 60,
            };

            createAndActivateButton.Clicked += CreateAndActivateButton_Clicked;

            layout = new StackLayout()
            {
                Children =
                {
                    timePicker,
                    vibrateLayout,
                    createAlarmButton,
                    createAndActivateButton,
                    cancelButton,
                }
            };

            scrollView = new ScrollView();
            scrollView.Content = layout;

            this.Content = scrollView;
        }

        private void CreateAndActivateButton_Clicked(object sender, EventArgs e)
        {
            var alarmController = DependencyService.Get<IAlarmController>();
            //get the set options of the alarm from other views

            //create alarm by passing in the alarm attributes

            alarmController.createAlarm();

            //Save alarm to User

            Navigation.PopAsync();
        }


        //OnClick methods
        private void CreateAlarmButton_Clicked(object sender, EventArgs e)
        {
            //Creates alarm

            //Adds to User

            Navigation.PopAsync();
        }

        private void CancelButton_Clicked(object sender, EventArgs e)
        {
            Navigation.PopAsync();
        }
    }
}
