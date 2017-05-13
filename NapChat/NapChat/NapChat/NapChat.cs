using Microsoft.WindowsAzure.MobileServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NapChat
{
    /*This class holds all the singletons for the application.
     */
    public class NapChat
    {
        //Singletons

        //This is the link to the database/server
        private static MobileServiceClient client { get; set; }
        //This is the user
        private static MobileServiceUser user { get; set; }


        //Controllers

        //Constructor
        public NapChat()
        {
            client = new MobileServiceClient("http://napchat.azurewebsites.net");
        }

        
    }
}
