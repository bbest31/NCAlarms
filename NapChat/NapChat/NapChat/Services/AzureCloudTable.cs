using System;
using System.Collections.Generic;
using System.Text;
using System.Collections.ObjectModel;
using System.Threading.Tasks;
using Microsoft.WindowsAzure.MobileServices;
using NapChat.Abstractions;
//https://adrianhall.github.io/develop-mobile-apps-with-csharp-and-azure/chapter1/firstapp_pc/

namespace NapChat.Services
{
    public class AzureCloudTable<T> : ICloudTable<T> where T : TableData
    {
        MobileServiceClient client;
        IMobileServiceTable<T> table;

        public AzureCloudTable(MobileServiceClient client)
        {
            this.client = client;
            this.table = client.GetTable<T>();
        }

        #region ICloudTable implementation
        public async Task<T> CreateUserAsync(T user)
        {
            await table.InsertAsync(user);
            return user;
        }

        //Short hand example: public async Task DeleteItemAsync(T item) => await table.DeleteAsync(item);
        public async Task DeleteUserAsync(T user)
        {
            await table.DeleteAsync(user);
        }

        public async Task<ICollection<T>> ReadAllUsersAsync()
        {
            return await table.ToListAsync();
        }

        public async Task<T> ReadUserAsync(string id)
        {
            return await table.LookupAsync(id);
        }

        public async Task<T> UpdateUserAsync(T user)
        {
            await table.UpdateAsync(user);
            return user;
        }
        #endregion
    }
}
