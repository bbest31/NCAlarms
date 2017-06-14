using System;
using System.Collections.Generic;
using System.Text;

namespace NapChat.Model
{
    /// <summary>
    /// Alarm object that holds all the information of a saved alarm excluding contents sent in the Nap-Alert.
    /// </summary>
    public class Alarm
    {
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
        /// Returns the trigger time of the alarm.
        /// </summary>
        /// <returns>long triggerTime</returns>
        public long getTriggerTime()
        {
            return triggerTime;
        }
    }
}
