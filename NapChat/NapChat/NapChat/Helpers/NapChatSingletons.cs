using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Abstractions;
using NapChat.Services;

namespace NapChat.Helpers
{
    /// <summary>
    /// Singleton manager class. Holds: AzureCloudService,
    /// </summary>
    /// 
    public static class NapChatSingletons
    {
        public static ICloudService CloudService { get; set; }

        public static ICloudService createSingletons()
        {
            return CloudService = new AzureCloudService();

        }

    }
}
