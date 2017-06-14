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

[assembly: ExportRenderer(typeof(AlarmPage), typeof(AlarmPageRenderer))]
namespace NapChat.iOS.Renderers
{
    public class AlarmPageRenderer : PageRenderer
    {
        protected override void OnElementChanged(VisualElementChangedEventArgs e)
        {
            base.OnElementChanged(e);
            if (e.OldElement != null || Element == null)
            {
                return;

            }

            ListView alarmListView = new ListView();
            
            //Get saved alarms from User object.
            List<string> alarms = new List<string>() { "First", "Second", "Third" };
            alarmListView.ItemsSource = alarms;

            alarmListView.ItemSelected += AlarmListView_ItemSelected;
            
        }

        private  void AlarmListView_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            throw new NotImplementedException();
        }
    }
}