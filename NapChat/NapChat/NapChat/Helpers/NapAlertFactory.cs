using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Model;

namespace NapChat.Helpers
{
    public static class NapAlertFactory
    {
        public static NapAlert buildNapAlert(string sender, string context, bool isAwakeIndicate)
        {
            string napMessage;
            //Build nap message
            if (context == "") {  
                 napMessage = sender + "has gone to sleep!";
                //Check to see if we attach an indication of the awaketime
            }else
            {
                 napMessage = sender + " " + context;
            }
            NapAlert napAlert = new NapAlert(sender, napMessage, isAwakeIndicate);
            return napAlert;
        }
    }
}
