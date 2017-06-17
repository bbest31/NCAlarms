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
        /// Constructor for Alarm object.
        /// </summary>
        /// <param name="time"></param>
        /// <param name="triggerTime"></param>
        /// <param name="vibrate"></param>
        public Alarm(/*TimeSpan time, long triggerTime, Boolean vibrate*/)
        {
            this.alarmTime = DateTime.Now;
            this.timeDisplay = alarmTime.ToString();
            this.vibrateOnAlarm = false;

        }

        String timeDisplay;

        /// <summary>
        /// Will be the alarm length by getting the set alarm time from the TimerPicker and then comparing to the current time.
        /// </summary>
        private long triggerTime;

        /// <summary>
        /// Alarm time set by the User.
        /// Could be receieved by friends if we want to just display text that says when they set the alarm for.
        /// </summary>
        public DateTime alarmTime { get; set; }

        /// <summary>
        /// Indicates whether the alarm will also vibrate on trigger.
        /// </summary>
        private Boolean vibrateOnAlarm;
         
        /// <summary>
        /// Returns the trigger time of the alarm.
        /// </summary>
        /// <returns>long triggerTime</returns>
        public long getTriggerTime()
        {
            return triggerTime;
        }
        /// <summary>
        /// Returns the alarm time of type DateTime for the TimePicker to display.
        /// </summary>
        /// <returns>DateTime alarmTime</returns>
        public DateTime getDateTime()
        {
            return alarmTime;
        }

        /// <summary>
        /// Toggles the state of whether or not the vibrate is on when alarm is triggered.
        /// </summary>
        public void toggleVibrate()
        {
            if(vibrateOnAlarm == true)
            {
                vibrateOnAlarm = false;
            }else
            {
                vibrateOnAlarm = true;
            }
        }
        /// <summary>
        /// Returns the vibration inidicating boolean.
        /// </summary>
        /// <returns></returns>
        public Boolean getVibrate()
        {
            return vibrateOnAlarm;
        }


    }
}
