using System;
using System.Collections.Generic;
using System.Text;

namespace NapChat.Model
{
    /// <summary>
    /// The alert object that holds the information sent in push notifications to Friends. 
    /// </summary>
    public class NapAlert
    {
        public NapAlert(string sender,string message,bool awakeNotify)
        {
            this.senderName = sender;
            this.napMessage = message;
            this.awakeNotify = awakeNotify;
        }
        /// <summary>
        /// Username of the User who set the alarm.
        /// </summary>
        private string senderName;
       

        /// <summary>
        /// Message sent in push notifications.
        /// Will be received by friends.
        /// </summary>
        private string napMessage;


        /// <summary>
        /// Boolean indicator if there is an awake notification to be sent out once the alarm is ended.
        /// </summary>
        private bool awakeNotify;

       

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
