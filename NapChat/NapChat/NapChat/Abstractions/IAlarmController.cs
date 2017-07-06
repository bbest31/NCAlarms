using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Model;

namespace NapChat.Abstractions
{
    public interface IAlarmController
    {
        void createAlarm(Alarm alarm);
        void createRepeatingAlarm();
    }
}
