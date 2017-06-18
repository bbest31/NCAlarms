using System;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Facebook.LoginKit;
using NapChat.Abstractions;
using Foundation;
using UIKit;
using Microsoft.WindowsAzure.MobileServices;
using Microsoft.IdentityModel.Clients.ActiveDirectory;
using NapChat.iOS.Services;
using NapChat.Helpers;
using Newtonsoft.Json.Linq;
using Xamarin.Auth;
using Auth0;

[assembly: Xamarin.Forms.Dependency(typeof(iOSLoginProvider))]
namespace NapChat.iOS.Services
{
    public class iOSLoginProvider : ILoginProvider
    {
        // public AccountStore AccountStore { get; private set; }


        /* public iOSLoginProvider()
         {
             AccountStore = AccountStore.Create();
         }
         */
        /// <summary>
        /// Login via ADAL
        /// </summary>
        /// <returns>(async) token from the ADAL process</returns>
        public async Task<string> LoginADALAsync(UIViewController view)
        {
            Uri returnUri = new Uri(Locations.AadRedirectUri);

            var authContext = new AuthenticationContext(Locations.AadAuthority);
            if (authContext.TokenCache.ReadItems().Count() > 0)
            {
                authContext = new AuthenticationContext(authContext.TokenCache.ReadItems().First().Authority);
            }
            var authResult = await authContext.AcquireTokenAsync(
                Locations.AppServiceUrl, /* The resource we want to access  */
                Locations.AadClientId,   /* The Client ID of the Native App */
                returnUri,               /* The Return URI we configured    */
                new PlatformParameters(view));
            return authResult.AccessToken;
        }
        public async Task LoginAsync(MobileServiceClient client)
        {
            /*//Client Flow Auth0
            var accessToken = LoginAuth0Async();

            var zumoPayload = new JObject();
            zumoPayload["access_token"] = accessToken;
            await client.LoginAsync("auth0", zumoPayload);*/
            /* var accessToken = await LoginFacebookAsync();
             var zumoPayload = new JObject()
             {
                 ["access_token"] = accessToken
             };
             */
            /*
           //AAD Client Flow
           var rootView = UIApplication.SharedApplication.KeyWindow.RootViewController;
           var accessToken = await LoginADALAsync(rootView);
           var zumoPayload = new JObject();
           zumoPayload["access_token"] = accessToken;
           await client.LoginAsync("aad", zumoPayload);
           */
            //Server Flow for AAD
            //await client.LoginAsync(RootView, "aad");
            await client.LoginAsync(RootView, "facebook");
        }

        public UIViewController RootView => UIApplication.SharedApplication.KeyWindow.RootViewController;
        /*
        public async Task<string> LoginAuth0Async()
        {
            var auth0 = new Auth0.Core.Client(
                "bbest.auth0.com",
                "KxRmN2M134uVMkHOYxNMvdFY0LhIRsI6");
            var user = await auth0.LoginAsync(RootView, scope : "openid email");
            return user.IdToken;
        }*/
        /* public MobileServiceUser RetrieveTokenFromSecureStore()
         {
             var accounts = AccountStore.FindAccountsForService("tasklist");
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
             AccountStore.Save(account, "tasklist");
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
