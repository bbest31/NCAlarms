using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace NapChat.Helpers
{
    public class WeekDayList : List<WeekDayChoice>
    {

        public WeekDayList()
        {
            this.Add(new WeekDayChoice { Day = "Sunday", Active = false });
            this.Add(new WeekDayChoice { Day = "Monday" , Active = false});
            this.Add(new WeekDayChoice { Day = "Tuesday", Active = false });
            this.Add(new WeekDayChoice { Day = "Wednesday", Active = false });
            this.Add(new WeekDayChoice { Day = "Thursday", Active = false });
            this.Add(new WeekDayChoice { Day = "Friday", Active = false });
            this.Add(new WeekDayChoice { Day = "Saturday", Active = false });
        }

    }
}
