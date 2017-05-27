using System;
using System.Collections.Generic;
using System.Text;
using NapChat.Abstractions;
using Microsoft.WindowsAzure.MobileServices;
using Xamarin.Forms;
using System.Threading.Tasks;
using NapChat.Model;
using Newtonsoft.Json.Linq;

namespace NapChat.Services
{
   public class AzureCloudService : ICloudService
    {
        MobileServiceClient client;

        public AzureCloudService()
        {
            client = new MobileServiceClient("https://napchat.azurewebsites.net");
        }

        public ICloudTable<T> GetTable<T>() where T : TableData
        {
            return new AzureCloudTable<T>(client);
        }

        /*The method looks up the platform dependent version of the login provider and executes the login method,
        passing along the client */ 
        public Task LoginAsync()
        {
            var loginProvider = DependencyService.Get<ILoginProvider>();
            return loginProvider.LoginAsync(client);
        }

       /* List<AppServiceIdentity> identities = null;

        public Task LoginAsync(User user)
        {
            return client.LoginAsync("custom", JObject.FromObject(user));
        }

        public async Task<AppServiceIdentity> GetIdentityAsync()
        {
            if (client.CurrentUser == null || client.CurrentUser?.MobileServiceAuthenticationToken == null)
            {
                throw new InvalidOperationException("Not Authenticated");
            }

            if (identities == null)
            {
                identities = await client.InvokeApiAsync<List<AppServiceIdentity>>("/.auth/me");
            }

            if (identities.Count > 0)
                return identities[0];
            return null;
        }
        */
    }
}
