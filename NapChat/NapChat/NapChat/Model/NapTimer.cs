using System;
using System.Collections.Generic;
using System.Text;

namespace NapChat.Model
{
    public class NapTimer
    {
        //Will be the alarm length by getting the set alarm time from the TimerPicker and then comparing to the current time.
        private TimeSpan alarmLength;

        private DateTime currentTime;

        /*Alarm time set by the User*/
        private DateTime alarmTime;

        //Message sent in push notifications
        private string napMessage;

        //Optional context added by User
        private string context;

        private bool awakeNotify;

        //private Group attachedGroup;

        public DateTime getCurrentTime()
        {
            currentTime = currentTime.ToLocalTime();
            return currentTime;
        }
       
        public void setAlarmTime(DateTime alarm)
        {
            //Alarm time was set to same time as current time
            //We therefore make the alarm system know they mean for the next day
            //Or if the time set was earlier than currentTime then we add a day to the set time.
            if (alarm.CompareTo(getCurrentTime()) == 0 || (alarm.CompareTo(getCurrentTime()) < 0))
            {
                alarm.AddDays(1);
                alarmTime = alarm;
            }
            //Greater than zero means the alarm time was set to a time later in the day
            else if (alarm.CompareTo(getCurrentTime()) > 0)
            {
                alarmTime = alarm;
            }
        }

        public DateTime getAlarmTime()
        {
            return alarmTime;
        }

        public void setAlarmLength()
        {
            alarmLength = getAlarmTime().Subtract(getCurrentTime());
        }

        public TimeSpan getAlarmLength()
        {
            return alarmLength;
        }
        public string getContext()
        {
            return context;
        }
        public string createNapMessage()
        {
            //here we get the username, attach context and then add timestamp.
            //Maybe also indicate if the awake notification is on or not.
            return napMessage;
        }

        public void turnOnAwakeNotify()
        {
            awakeNotify = true;
        }

        public void turnOffAwakeNotify()
        {
            awakeNotify = false;
        }
    }
}
