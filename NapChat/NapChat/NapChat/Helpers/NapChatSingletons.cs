using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Abstractions;
using NapChat.Services;
using NapChat.Model;
using Microsoft.WindowsAzure.MobileServices;

namespace NapChat.Helpers
{
    /// <summary>
    /// Singleton manager class. Holds: AzureCloudService,
    /// </summary>
    /// 
    public static class NapChatSingletons
    {
        public static ICloudService CloudService { get; set; }

        public static User MainUser { get; } 

        public static void createSingletons()
        {
            //return CloudService = new AzureCloudService();


        }

        public static void setMainUser(MobileServiceClient client)
        {
            MainUser.Id = client.CurrentUser.UserId;
            
        }
        

    }
}
