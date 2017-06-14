using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Abstractions;

namespace NapChat.Model
{
    /// <summary>
    /// User class that holds the User's username and allows access to data.
    /// </summary>
    public class User : TableData
    {
        public string username { get; set; }

        public List<Alarm> alarmList { get; set; }

        public List<NapAlert> napAlerts { get; set; }


    }
}
