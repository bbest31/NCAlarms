using System;
using System.Collections.Generic;
using System.Text;

using Xamarin.Forms;
using NapChat.Abstractions;
using NapChat.RenderedViews;
using NapChat.Helpers;
using NapChat.Model;
using NapChat.ViewTemplates;

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
        StackLayout snoozeLayout;
        ScrollView scrollView;
        Switch vibrateSwitch;
        Label vibrateLabel;
        Label snoozeLengthLabel;
        Button attachUsersButton;
        Button alarmToneButton;
        Picker snoozeLengthPicker;
        Dictionary<string, int> snoozeLength;
        Entry napMessageEntry;
        ListView alarmRepeatList;
        WeekDayList weekDayList = new WeekDayList();     


        //Binded Attributes
        TimeSpan pickerTime;
        Boolean isVibrate;
        int SnoozeLengthInt;

        public CreateAlarmPage()
        {
            Title = "Create Alarm";
            BackgroundColor = Color.Lavender;
            timePicker = new CustomTimePicker()
            { 
                HorizontalOptions = LayoutOptions.Fill,
                VerticalOptions = LayoutOptions.Start,
                BindingContext = pickerTime,
                HeightRequest = 100,
                BackgroundColor = Color.Lavender,
            };
            timePicker.SetBinding(Xamarin.Forms.TimePicker.TimeProperty, new Binding("Time") { Mode = BindingMode.OneWayToSource });

            alarmRepeatList = new ListView()
            {
                ItemsSource = weekDayList,
                RowHeight = 80,
                ItemTemplate = new DataTemplate(typeof(WeekdayListItem)),
            
            };

            alarmRepeatList.ItemSelected += AlarmRepeatList_ItemSelected;

            vibrateLabel = new Label()
            {
                HorizontalOptions = LayoutOptions.Start,
                Text = "Vibrate on Alarm:",
                TextColor = Color.Purple,

            };
            vibrateSwitch = new Switch()
            {
                HorizontalOptions = LayoutOptions.End,
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

            attachUsersButton = new Button()
            {
                Text = "Attach Friends",
                TextColor = Color.White,
                FontAttributes = FontAttributes.Bold,
                BackgroundColor = Color.MediumPurple,
                BorderColor = Color.White,
                HeightRequest = 40,
                WidthRequest = 120,
                HorizontalOptions = LayoutOptions.Center,
                
            };

            attachUsersButton.Clicked += AttachUsersButton_Clicked;

            napMessageEntry = new Entry()
            {
                HorizontalOptions = LayoutOptions.Fill,
                Placeholder = "Nap Message...",
                PlaceholderColor = Color.DimGray,
                IsVisible = false,

            };

            alarmToneButton = new Button()
            {
                Text = "Alarm Tone",
                TextColor = Color.White,
                FontAttributes = FontAttributes.Bold,
                BackgroundColor = Color.MediumPurple,
                BorderColor = Color.White,
                HeightRequest = 40,
                WidthRequest = 125,
                HorizontalOptions = LayoutOptions.Center,
            };

            alarmToneButton.Clicked += AlarmToneButton_Clicked;

            snoozeLength = new Dictionary<string, int>
            {
                {"5",5 }, {"10",10 }, {"15",15 }, {"30",30 }
            };

            snoozeLengthLabel = new Label()
            {
                HorizontalOptions = LayoutOptions.Start,
                Text = "Snooze Length:",
                TextColor = Color.Purple,
            };

            snoozeLengthPicker = new Picker()
            {
                Title = "Snooze Length",
                HorizontalOptions = LayoutOptions.Center,
            };

            foreach (string time in snoozeLength.Keys)
            {
                snoozeLengthPicker.Items.Add(time);
            }

            snoozeLengthPicker.SelectedIndexChanged += SnoozeLengthPicker_SelectedIndexChanged;

            snoozeLayout = new StackLayout()
            {
                Orientation = StackOrientation.Horizontal,
                Children = {
                    snoozeLengthLabel,
                    snoozeLengthPicker,
                }
            };

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
                    alarmToneButton,
                    attachUsersButton,
                    napMessageEntry,
                    snoozeLayout,
                    createAlarmButton,
                    createAndActivateButton,
                    cancelButton,
                }
            };

            scrollView = new ScrollView();
            scrollView.Content = layout;

            this.Content = scrollView;
        }

        private void AlarmRepeatList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            if(e.SelectedItem == null)
            {
                var SelectedWeekday = e.SelectedItem as WeekdayListItem;
                SelectedWeekday.View.BackgroundColor = Color.White;
                
            } else
            {
                var SelectedWeekday = e.SelectedItem as WeekdayListItem;
                SelectedWeekday.View.BackgroundColor = Color.Gray;
            }

        }

        private void SnoozeLengthPicker_SelectedIndexChanged(object sender, EventArgs e)
        {
            if(snoozeLengthPicker.SelectedIndex == -1)
            {
                SnoozeLengthInt = 5;
            }
            else{
                string timeLength = snoozeLengthPicker.Items[snoozeLengthPicker.SelectedIndex];
                SnoozeLengthInt = snoozeLength[timeLength];
            }
            System.Diagnostics.Debug.WriteLine("Snooze Length is set to: "+ SnoozeLengthInt.ToString());
        }

        private void AlarmToneButton_Clicked(object sender, EventArgs e)
        {
            //push to page to view all phone tones, music, and Napchat tones.
        }

        private void AttachUsersButton_Clicked(object sender, EventArgs e)
        {
            //Pops up modal to choose to add a group of friends or pick a selection of Friends.

            //Shows the context entry
            napMessageEntry.IsVisible = true;
        }

        private void CreateAndActivateButton_Clicked(object sender, EventArgs e)
        {
            var alarmController = DependencyService.Get<IAlarmController>();
            //get the set options of the alarm from other views

            //create alarm by passing in the alarm attributes
            //IsVibrate, SnoozeLengthInt, pickerTime => long int, Group, ringtone, NapMessage, startTime 
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
