using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NapChat
{
    class User : Patron
    {
        //inherits username and ID attributes
        private String ID { get; set; }
        private String username { get; set; }
        private List<Friend> friendList { get; }

       

        //Will need a list of Groups and a Nap-Log which is a list of Nap Alerts

        //Constructor
        public User(String id, String username)
        {
            this.ID = id;
            this.username = username;
            this.friendList = new List<Friend>();
        }



    }
}
