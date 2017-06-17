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
        UITableView AlarmTableView;
        AlarmTableSource tableSource;
        //List<Alarm> userAlarms;


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
               
                
                Frame = new CoreGraphics.CGRect(0f,0f,320f,View.Bounds.Height),
                SeparatorColor = UIColor.Purple,
                SeparatorStyle = UITableViewCellSeparatorStyle.SingleLine,
                BackgroundColor = UIColor.White,
                //Source = tableSource,

            };

            View.Add(AlarmTableView);
        }

        /// <summary>
        /// Assigns event handlers to Views on AlarmsPage.
        /// </summary>
       void SetUpEventHandlers()
        {

        }
        /// <summary>
        /// Gets saved Alarms from the User.
        /// </summary>
        void RetrieveAlarms()
        {
            
        }

    }
}