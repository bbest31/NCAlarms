using System;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Facebook.LoginKit;
using NapChat.Abstractions;
using UIKit;
using Microsoft.WindowsAzure.MobileServices;
using NapChat.iOS.Services;
using Newtonsoft.Json.Linq;

[assembly: Xamarin.Forms.Dependency(typeof(iOSLoginProvider))]
namespace NapChat.iOS.Services
{
    public class iOSLoginProvider : ILoginProvider
    {
        public async Task LoginAsync(MobileServiceClient client)
        {
            var accessToken = await LoginFacebookAsync();
            var zumoPayload = new JObject()
            {
                ["access_token"] = accessToken
            };
            await client.LoginAsync("facebook", zumoPayload);
        }

        public UIViewController RootView => UIApplication.SharedApplication.KeyWindow.RootViewController;
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
}
