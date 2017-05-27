using System;
using System.Collections.Generic;
using System.Text;
//https://adrianhall.github.io/develop-mobile-apps-with-csharp-and-azure/chapter1/firstapp_pc/
namespace NapChat.Abstractions
{
    public abstract class TableData
    {
        public string Id { get; set; }
        public DateTimeOffset? UpdatedAt { get; set; }
        public DateTimeOffset? CreatedAt { get; set; }
        public byte[] Version { get; set; }
    }
}
