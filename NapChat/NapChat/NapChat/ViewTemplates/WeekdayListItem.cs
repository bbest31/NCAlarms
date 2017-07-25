using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace NapChat.ViewTemplates
{
     public class WeekdayListItem : ViewCell
    {
        public WeekdayListItem()
        {
            Label weekDay = new Label
            {
                HorizontalOptions = LayoutOptions.Start,
                FontSize = 25,
                TextColor = Color.Black,
            };

            weekDay.SetBinding(Label.TextProperty, "Day");

            StackLayout itemLayout = new StackLayout()
            {
                HorizontalOptions = LayoutOptions.StartAndExpand,
                Orientation = StackOrientation.Horizontal,
                Children = { weekDay }
            };

            View = itemLayout;
            
            
        }
    }
}
