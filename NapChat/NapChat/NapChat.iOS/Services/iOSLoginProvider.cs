using System;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Facebook.LoginKit;
using NapChat.Abstractions;
using Foundation;
using UIKit;
using Microsoft.WindowsAzure.MobileServices;
using NapChat.iOS.Services;
using Newtonsoft.Json.Linq;
using Xamarin.Auth;

[assembly: Xamarin.Forms.Dependency(typeof(iOSLoginProvider))]
namespace NapChat.iOS.Services
{
    public class iOSLoginProvider : ILoginProvider
    {
        public AccountStore AccountStore { get; private set; }
        
        
        public iOSLoginProvider()
        {
            AccountStore = AccountStore.Create();
        }
        

        public async Task LoginAsync(MobileServiceClient client)
        {
            /* var accessToken = await LoginFacebookAsync();
             var zumoPayload = new JObject()
             {
                 ["access_token"] = accessToken
             };
             */
            //RetrieveTokenFromSecureStore();

            var user = await client.LoginAsync(RootView, MobileServiceAuthenticationProvider.Facebook);

            //StoreTokenInSecureStore(user);
        }

        public UIViewController RootView => UIApplication.SharedApplication.KeyWindow.RootViewController;

        public MobileServiceUser RetrieveTokenFromSecureStore()
        {
            var accounts = AccountStore.FindAccountsForService("napchat");
            if (accounts != null)
            {
                foreach (var acct in accounts)
                {
                    string token;

                    if (acct.Properties.TryGetValue("token", out token))
                    {
                        return new MobileServiceUser(acct.Username)
                        {
                            MobileServiceAuthenticationToken = token
                        };
                    }
                }
            }
            return null;
        }
        
        public void StoreTokenInSecureStore(MobileServiceUser user)
        {
            var account = new Account(user.UserId);
            account.Properties.Add("token", user.MobileServiceAuthenticationToken);
            AccountStore.Save(account, "napchat");
        }


        public void RemoveTokenFromSecureStore()
        {
            var accounts = AccountStore.FindAccountsForService("tasklist");
            if (accounts != null)
            {
                foreach (var acct in accounts)
                {
                    AccountStore.Delete(acct, "tasklist");
                }
            }
        }
        /*
        #region Facebook Client Flow
        private TaskCompletionSource<string> fbtcs;

        public async Task<string> LoginFacebookAsync()
        {
            fbtcs = new TaskCompletionSource<string>();
            var loginManager = new LoginManager();

            loginManager.LogInWithReadPermissions(new[] { "public_profile" }, RootView, LoginTokenHandler);
            return await fbtcs.Task;
        }

        private void LoginTokenHandler(LoginManagerLoginResult loginResult, NSError error)
        {
            if (loginResult.Token != null)
            {
                fbtcs.TrySetResult(loginResult.Token.TokenString);
            }
            else
            {
                fbtcs.TrySetException(new Exception("Facebook Client Flow Login Failed"));
            }
        }
        #endregion
        */
    }


}
