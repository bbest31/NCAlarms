using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Abstractions;

namespace NapChat.Model
{
    public class User : TableData
    {
        public string username { get; set; }
    }
}
