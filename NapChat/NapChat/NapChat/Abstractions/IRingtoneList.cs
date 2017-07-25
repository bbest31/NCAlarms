using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace NapChat.Abstractions
{
    public interface IRingtoneList
    {
        string uri { get; set; }
        void pickRingtone();
       
      
    }
}
