using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NapChat
{
    class Friend : Patron
    {
        private String username { get; }
        //Constructor      
        public Friend(String username) { this.username = username; }
        
    }
}
