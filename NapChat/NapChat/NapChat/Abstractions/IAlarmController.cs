using System;
using System.Collections.Generic;
using System.Text;

namespace NapChat.Abstractions
{
    public interface IAlarmController
    {
        void createAlarm();
        void createRepeatingAlarm();
    }
}
