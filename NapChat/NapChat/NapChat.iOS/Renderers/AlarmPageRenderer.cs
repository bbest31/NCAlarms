using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Foundation;
using UIKit;
using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using NapChat.iOS.Renderers;
using NapChat.Pages;
using NapChat.Model;

[assembly: ExportRenderer(typeof(AlarmPage), typeof(AlarmPageRenderer))]
namespace NapChat.iOS.Renderers
{
    public class AlarmPageRenderer : PageRenderer
    {

        //View Declarations
        UIScrollView AlarmScrollView;

        protected override void OnElementChanged(VisualElementChangedEventArgs e)
        {
           
            base.OnElementChanged(e);
            if (e.OldElement != null || Element == null)
            {

                return;
            }
            try
            {
                RetrieveAlarms();
                SetUpUI();
                SetUpEventHandlers();
             
              
                
            } catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Custome Renderer Failed: Error = {ex.Message}");
            }

           /* ListView alarmListView = new ListView();
            
            //Get saved alarms from User object.
            List<Alarm> alarms = new List<Alarm>();
            Alarm alarm1 = new Alarm();
            alarms.Add(alarm1);
            alarmListView.ItemsSource = alarms;

            var alarmTemplate = new DataTemplate(typeof(SwitchCell));
            alarmTemplate.SetBinding(SwitchCell.TextProperty, "alarmTime");
            
            alarmListView.ItemTemplate = alarmTemplate;*/
           // alarmListView.ItemTemplate.SetBinding(SwitchCell.TextProperty, "alarmTime");

            //Event Hanndler when switch is activated we turn on that alarm and schedule and alarm using platform specific implmented methods.
            //alarmListView.ItemSelected += AlarmListView_ItemSelected;
            
        }
        /// <summary>
        /// Sets up the iOS AlarmPage UI.
        /// </summary>
        void SetUpUI()
        {
            //Set up boundary on ScrollView
            var topLeftX = View.Bounds.X + 25;
            var topRightX = View.Bounds.X - 65;
            
            //Initialize View and give properties.
            AlarmScrollView = new UIScrollView()
            {
                
            };

           
        }

        /// <summary>
        /// Assigns event handlers to Views on AlarmsPage.
        /// </summary>
       void SetUpEventHandlers()
        {

        }

        void RetrieveAlarms()
        {

        }

    }
}