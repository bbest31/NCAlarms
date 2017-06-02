using System;
using System.Collections.Generic;
using System.Text;

namespace NapChat.Model
{
    /// <summary>
    /// The timer object that holds the alarm lengths, nap message, nap settings,
    /// and also contributes to the nap log.
    /// </summary>
    public class NapTimer
    {
        /// <summary>
        /// Will be the alarm length by getting the set alarm time from the TimerPicker and then comparing to the current time.
        /// </summary>
        private TimeSpan alarmLength;

        private DateTime currentTime;

        /// <summary>
        /// Alarm time set by the User.
        /// </summary>
        private DateTime alarmTime;

        /// <summary>
        /// Message sent in push notifications.
        /// </summary>
        private string napMessage;

        /// <summary>
        /// Optional context added by User, to explain Nap-Alert.
        /// </summary>
        private string context;

        /// <summary>
        /// Boolean indicator if there is an awake notification to be sent out once the alarm is ended.
        /// </summary>
        private bool awakeNotify;

        //private Group attachedGroup;

        /// <summary>
        /// Returns the current local time.
        /// </summary>
        /// <returns>The current local time as type DateTime</returns>
        public DateTime getCurrentTime()
        {
            currentTime = currentTime.ToLocalTime();
            return currentTime;
        }
       
        /// <summary>
        /// Takes in the DateTime from the UI and sets the appropriate alarm time.
        /// </summary>
        /// <param name="alarm"></param>
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

        /// <summary>
        /// Returns the time the alarm is set to go off on.
        /// </summary>
        /// <returns>Alarm time.</returns>
        public DateTime getAlarmTime()
        {
            return alarmTime;
        }
        /// <summary>
        /// Finds the alarm length based off the alarm time set and the current local time.
        /// </summary>
        public void setAlarmLength()
        {
            alarmLength = getAlarmTime().Subtract(getCurrentTime());
        }
        /// <summary>
        /// Returns the length of the alarm as type TimeSpan
        /// </summary>
        /// <returns>TimeSpan alarmlength</returns>
        public TimeSpan getAlarmLength()
        {
            return alarmLength;
        }
        /// <summary>
        /// Returns the optional string context set by the user to accompany the Nap-Alert.
        /// </summary>
        /// <returns>String context</returns>
        public string getContext()
        {
            return context;
        }
        /// <summary>
        /// Method uses Username, context and timestamp to create the Nap-Alert to send to the attached Group.
        /// </summary>
        /// <returns>String Nap-Alert</returns>
        public string createNapMessage()
        {
            //here we get the username, attach context and then add timestamp.
            //Maybe also indicate if the awake notification is on or not.
            return napMessage;
        }

        /// <summary>
        /// Turns the boolean awake notifier on.
        /// </summary>
        public void turnOnAwakeNotify()
        {
            awakeNotify = true;
        }

        /// <summary>
        /// Turns the boolean awake notifier off.
        /// </summary>
        public void turnOffAwakeNotify()
        {
            awakeNotify = false;
        }
    }
}
