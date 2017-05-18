using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

//https://adrianhall.github.io/develop-mobile-apps-with-csharp-and-azure/chapter1/firstapp_pc/
namespace NapChat.Abstractions
{
    public interface ICloudService
    {
        //used for initializing the connection and getting a table definition
        ICloudTable<T> GetTable<T>() where T : TableData;

        Task LoginAsync();
    }
}
