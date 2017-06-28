using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Foundation;
using UIKit;
using CoreGraphics;
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
        UITableView AlarmTableView;
        AlarmTableSource tableSource;
        UINavigationBar AlarmNavBar;

        //List<Alarm> userAlarms;

        //TEST
        UIButton tempNapTimerButton;

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
                System.Diagnostics.Debug.WriteLine($"Custom Renderer Failed: Error = {ex.Message}");
            }

            
        }
        /// <summary>
        /// Sets up the Alarm Page UI and gives the TableView its item source.
        /// </summary>
        void SetUpUI()
        {
            //Set up boundary on ScrollView
            var topLeftX = View.Bounds.X + 25;
            var topRightX = View.Bounds.X - 65;
            
            //Test code
            
            //Alarm firstalarm = new Alarm();
            //userAlarms.Add(firstalarm);
            //tableSource = new AlarmTableSource(userAlarms);

            //Initialize View and give properties.
            AlarmTableView = new UITableView()
            {
                //Small code to place colored background with custom image.
                /*BackgroundColor = UIColor.Clear,
                BackgroundView = ...*/
               
                
                Frame = new CoreGraphics.CGRect(20f,20f,320f,View.Bounds.Height),
                SeparatorColor = UIColor.Purple,
                SeparatorStyle = UITableViewCellSeparatorStyle.SingleLine,
                BackgroundColor = UIColor.FromRGB(261f,191f,216f),
                //Source = tableSource,

            };
            UIBarButtonItem newAlarmButton = new UIBarButtonItem()
            {
                
            };
            //TEMP------------
             tempNapTimerButton = new UIButton()
            {
                Frame = new CoreGraphics.CGRect(topLeftX,View.Bounds.Top+15,37,37),
                

            };
            tempNapTimerButton.SetTitle("Set-Alarms", UIControlState.Normal);
            tempNapTimerButton.SetTitleColor(UIColor.White, UIControlState.Normal);
            tempNapTimerButton.BackgroundColor = UIColor.Red;
            
            //------------
            AlarmNavBar = new UINavigationBar()
            {
                BarTintColor = UIColor.Purple,
                TintColor = UIColor.White,
                Translucent = true,
                TitleTextAttributes = new UIStringAttributes()
                {
                    
                },
               // NavigationItem.RightBarButtonItem.
                Frame = new CoreGraphics.CGRect(0f,0f,View.Bounds.Width,40f)
                
            };
            View.Add(AlarmTableView);
            View.Add(AlarmNavBar);
            View.Add(tempNapTimerButton);
        }

       

        /// <summary>
        /// Assigns event handlers to Views on AlarmsPage.
        /// </summary>
        void SetUpEventHandlers()
        {
            //TEMP
            tempNapTimerButton.TouchUpInside += TempNapTimerButton_TouchUpInside;

        }

        private void TempNapTimerButton_TouchUpInside(object sender, EventArgs e)
        {
            openAlarmCreator();
        }

        /// <summary>
        /// Gets saved Alarms from the User.
        /// </summary>
        void RetrieveAlarms()
        {
            
        }

        void openAlarmCreator()
        {
            
        }
    }
}