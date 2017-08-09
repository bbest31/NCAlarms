using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

//https://adrianhall.github.io/develop-mobile-apps-with-csharp-and-azure/chapter1/firstapp_pc/
namespace NapChat.Abstractions
{
   public interface ICloudTable<T> where T : TableData
    {
        Task<T> CreateUserAsync(T user);
        Task<T> ReadUserAsync(string id);
        Task<T> UpdateUserAsync(T user);
        Task DeleteUserAsync(T user);

        //May remove this
        Task<ICollection<T>> ReadAllUsersAsync();
    }
}
