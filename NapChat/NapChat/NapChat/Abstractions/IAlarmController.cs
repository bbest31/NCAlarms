using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Model;

namespace NapChat.Abstractions
{
    public interface IAlarmController
    {
        void scheduleAlarm(Alarm alarm);
        void scheduleRepeatingAlarm();
    }
}
