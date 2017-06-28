using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using NapChat.Model;

//https://adrianhall.github.io/develop-mobile-apps-with-csharp-and-azure/chapter1/firstapp_pc/
namespace NapChat.Abstractions
{
    /// <summary>
    /// Interface for Cloud Service class to get Tables, Login users, and receive user identity
    /// once they are authenticated.
    /// </summary>
    public interface ICloudService
    {
        //used for initializing the connection and getting a table definition
        ICloudTable<T> GetTable<T>() where T : TableData;

        Task LoginAsync();
        
        Task LoginAsync(User user);

        Task<AppServiceIdentity> GetIdentityAsync();
    }
}
