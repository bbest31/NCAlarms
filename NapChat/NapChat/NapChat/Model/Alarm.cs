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
        private TimeSpan triggerTime { get; set; }
        private Boolean vibrateOnAlarm { get; set; }
        private int snoozeLength { get; set; }

        /// <summary>
        /// Constructor for Alarm object.
        /// </summary>
        /// <param name="time"></param>
        /// <param name="triggerTime"></param>
        /// <param name="vibrate"></param>
        public Alarm(TimeSpan time, int snooze , Boolean vibrate)
        {
            this.triggerTime = time;
            this.snoozeLength = snooze;
            this.vibrateOnAlarm = vibrate;

        }
        //=====SETTERS=======
        /// <summary>
        /// Sets the time the Alarm triggers at.
        /// </summary>
        /// <param name="newTime"></param>
        public void setTime(TimeSpan newTime)
        {
            this.triggerTime = newTime;
        }

        /// <summary>
        /// Sets whether the alarm vibrates on activation or not.
        /// </summary>
        /// <param name="vibr"></param>
        public void setVibrateSettings(Boolean vibr)
        {
            this.vibrateOnAlarm = vibr;
        }

        /// <summary>
        /// Sets the time length of the snooze in minutes.
        /// </summary>
        /// <param name="snooze"></param>
        public void setSnooze(int snooze)
        {
            this.snoozeLength = snooze;
        }
        //========================

        //=======GETTERS==========
        /// <summary>
        /// Returns trigger time of alarm as a TimeSpan type.
        /// </summary>
        /// <returns></returns>
        public TimeSpan getTriggerTime()
        {
            return this.triggerTime;
        }
        /// <summary>
        /// Returns a boolean indicating whether the alarm vibrates on activation or not.
        /// </summary>
        /// <returns></returns>
        public Boolean getVibrateSettings()
        {
            return this.vibrateOnAlarm;
        }
        /// <summary>
        /// Returns the time length of the alarm snooze for minutes as an integer.
        /// </summary>
        /// <returns></returns>
        public int getSnoozeLength()
        {
            return this.snoozeLength;
        }
    }
}
