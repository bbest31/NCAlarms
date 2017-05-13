using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace NapChat.Model
{
    public class User
    {
        //inherits username and ID attributes
        
        public string id { get; set; }
        [JsonProperty("username")]
        public string username { get; set; }
        

        //private List<Friend> friendList { get; }

       

        //Will need a list of Groups and a Nap-Log which is a list of Nap Alerts

     



    }
}
