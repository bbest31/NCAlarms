using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.WindowsAzure.MobileServices;
using System.Threading.Tasks;

namespace NapChat.Abstractions
{
    /// <summary>
    /// Interface for login flow that is implemented by platform specific class
    /// to handle login funcion LoginAsync().
    /// </summary>
    public interface ILoginProvider
    {
        /// <summary>
        /// Interface login Task using the client.
        /// </summary>
        /// <param name="client"></param>
        /// <returns></returns>
        Task LoginAsync(MobileServiceClient client);
    }
}
