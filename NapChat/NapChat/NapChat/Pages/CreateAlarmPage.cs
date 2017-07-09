using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Text;

using Xamarin.Forms;
using NapChat.Abstractions;
using NapChat.RenderedViews;
using NapChat.Helpers;
using NapChat.Model;
using NapChat.ViewTemplates;
using System.Diagnostics;

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
        Xamarin.Forms.Switch vibrateSwitch;
        Label vibrateLabel;
        Label snoozeLengthLabel;
        Button attachUsersButton;
        Button alarmToneButton;
        Picker snoozeLengthPicker;
        Dictionary<string, int> snoozeLength;
        Entry napMessageEntry;
        ListView alarmRepeatList;
        WeekDayList weekDayList = new WeekDayList();

        //Binded Variables
        TimeSpan pickerTime;
        Boolean isVibrate = false;
        int SnoozeLengthInt;
        string ringToneURI = "default";

        

        public CreateAlarmPage()
        {
            Title = "Create Alarm";
            BackgroundColor = Color.Lavender;
            NavigationPage.SetHasBackButton(this, false);
            timePicker = new CustomTimePicker()
            { 
                HorizontalOptions = LayoutOptions.Fill,
                VerticalOptions = LayoutOptions.Start,
                BindingContext = pickerTime,
                HeightRequest = 100,
                BackgroundColor = Color.Lavender,
            };

            timePicker.PropertyChanged += TimePicker_PropertyChanged;

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
            vibrateSwitch = new Xamarin.Forms.Switch()
            {
                HorizontalOptions = LayoutOptions.End,
                VerticalOptions = LayoutOptions.End,
                IsToggled = false,
               
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


            vibrateSwitch.Toggled += VibrateSwitch_Toggled;
            
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
                Text = "Alarm Tone: "+"Default",
                TextColor = Color.Black,
                FontAttributes = FontAttributes.Bold,
                BackgroundColor = Color.White,
                BorderColor = Color.Transparent,
                HeightRequest = 40,
                HorizontalOptions = LayoutOptions.Fill,
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

        private void TimePicker_PropertyChanged(object sender, System.ComponentModel.PropertyChangedEventArgs e)
        {
            pickerTime = timePicker.Time;
            Debug.WriteLine("TimeSpan in time picker:" + pickerTime.ToString()+". In milliseconds:"+pickerTime.TotalMilliseconds.ToString());
        }

        /// <summary>
        /// Assigns toggled state of VibrateSwitch to isVibrate boolean variable.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void VibrateSwitch_Toggled(object sender, ToggledEventArgs e)
        {
            isVibrate = e.Value;
   
            Debug.WriteLine("isvibrate = " + isVibrate.ToString());
        }

        /// <summary>
        /// Updates UI to reflect selection of alarm repeat days. Changes the list item background to gray.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
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

        /// <summary>
        /// Databinding method to match the selection in the Picker to the snooze length variable.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
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
            System.Diagnostics.Debug.WriteLine("Snooze Length is set to: "+ SnoozeLengthInt.ToString()+ "and isVibrate = "+isVibrate.ToString());
        }

        /// <summary>
        /// Displays an Action sheet to select alarm tone from phone, music or set default ringtone.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private async void AlarmToneButton_Clicked(object sender, EventArgs e)
        {
            //Should input some visual cue that they clicked it.
            string action = await DisplayActionSheet("Alarm Tones", "Cancel", null, "Default", "Ringtones", "From Music");
            if(action == "Default")
            {
                
                alarmToneButton.Text = "Alarm Tone: " + action;

            } else if(action == "Ringtones")
            {
                var ringtToneList = DependencyService.Get<IRingtoneList>();
                Debug.WriteLine("Ringtone Uri: " + ringToneURI);
                ringtToneList.pickAndReceiveRingtone(ringToneURI);
                Debug.WriteLine("Ringtone Uri: " + ringToneURI);
                alarmToneButton.Text = "Alarm Tone: " + action;
                
            } else if (action == "From Music")
            {

            }
             
        }

        /// <summary>
        /// Pushes to page to attach a Group or a selection of Friends.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AttachUsersButton_Clicked(object sender, EventArgs e)
        {
            //Pops up modal to choose to add a group of friends or pick a selection of Friends.

            //Shows the context entry
            napMessageEntry.IsVisible = true;
        }
        /// <summary>
        /// Creates Alarm object and saves to User. Sends parameters to platform AlarmController to schedule alarm.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CreateAndActivateButton_Clicked(object sender, EventArgs e)
        {
            var alarmController = DependencyService.Get<IAlarmController>();
            //get or set options of the alarm from other views

            //create alarm by passing in the alarm attributes
            var alarm = new Alarm(pickerTime, SnoozeLengthInt, isVibrate, ringToneURI);

            //Schedule alarm with AlarmController
            //TODO: Group, ringtoneURI, NapMessage, messageTime 
            alarmController.scheduleAlarm(alarm);

            //Save alarm to User

            Navigation.PopAsync();
        }


        /// <summary>
        /// Creates and saves Alarm object to User.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CreateAlarmButton_Clicked(object sender, EventArgs e)
        {
            //Creates Alarm
            var alarm = new Alarm(pickerTime, SnoozeLengthInt, isVibrate, ringToneURI);

            //Adds to User

            Navigation.PopAsync();
        }

        /// <summary>
        /// Returns to AlarmPage.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CancelButton_Clicked(object sender, EventArgs e)
        {
           
            Navigation.PopAsync();
        }
    }
}
