using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Abstractions;
using NapChat.Services;

namespace NapChat.Helpers
{
    public static class NapChatSingletons
    {
        public static ICloudService CloudService { get; set; }

        static NapChatSingletons()
        {
            CloudService = new AzureCloudService();

        }

    }
}
