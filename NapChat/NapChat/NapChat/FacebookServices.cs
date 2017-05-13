using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NapChat.Model;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Net.Http;

namespace FacebookLogin.Services
{
    public class FacebookServices
    {

        public async Task<User> GetFacebookProfileAsync(string accessToken)
        {
            var requestUrl =
                "https://graph.facebook.com/v2.7/me/?fields=name,email,first_name,is_verified,languages&access_token="
                + accessToken;

            var httpClient = new HttpClient();

            var userJson = await httpClient.GetStringAsync(requestUrl);

            var facebookProfile = JsonConvert.DeserializeObject<User>(userJson);

            return facebookProfile;
        }
    }
}