using System;
using System.Collections.Generic;
using System.Text;

namespace NapChat.Model
{
    /// <summary>
    /// The timer object that holds the alarm lengths, nap message, nap settings,
    /// and also contributes to the nap log.
    /// </summary>
    public class NapAlert
    {
        public NapAlert(string sender, long triggertime,string message,bool awakeNotify)
        {
            this.senderName = sender;
            this.triggerTime = triggertime;
            this.napMessage = message;
            this.awakeNotify = awakeNotify;
        }
        /// <summary>
        /// Username of the User who set the alarm.
        /// </summary>
        private string senderName;
        /// <summary>
        /// Will be the alarm length by getting the set alarm time from the TimerPicker and then comparing to the current time.
        /// </summary>
        private long triggerTime;

       

        /// <summary>
        /// Alarm time set by the User.
        /// Could be receieved by friends if we want to just display text that says when they set the alarm for.
        /// </summary>
        private DateTime alarmTime;

        /// <summary>
        /// Message sent in push notifications.
        /// Will be received by friends.
        /// </summary>
        private string napMessage;


        /// <summary>
        /// Boolean indicator if there is an awake notification to be sent out once the alarm is ended.
        /// </summary>
        private bool awakeNotify;

        //private Group attachedGroup;

      /*  /// <summary>
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
        */
        /*
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
        */

        //--START-- Getters and Setters

        /// <summary>
        /// Returns the name of the User who set the Alarm.
        /// </summary>
        /// <returns></returns>
        public string getSenderName()
        {
            return senderName;
        }

        /// <summary>
        /// Returns the trigger time of the alarm.
        /// </summary>
        /// <returns>long triggerTime</returns>
        public long getTriggerTime()
        {
            return triggerTime;
        }
        
        /// <summary>
        /// Method uses Username, context and timestamp to create the Nap-Alert to send to the attached Group.
        /// </summary>
        /// <returns>String Nap-Alert</returns>
        public string getNapMessage()
        {
            return napMessage;
        }

        /// <summary>
        /// Returns the boolean representing if there is an awake time given or not.
        /// </summary>
        /// <returns>boolean</returns>
        public bool getAwakeNotify()
        {
            return awakeNotify;
        }

        //--END-- Getters and Setters
    }
}
