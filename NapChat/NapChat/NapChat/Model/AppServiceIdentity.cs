using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace NapChat.Model
{
    /// <summary>
    /// Holds the information about the authenticated User provided by the Identity Provider.
    /// </summary>
    public class AppServiceIdentity
    {
        [JsonProperty(PropertyName = "id_token")]
        public string IdToken { get; set; }

        [JsonProperty(PropertyName = "provider_name")]
        public string ProviderName { get; set; }

        [JsonProperty(PropertyName = "user_id")]
        public string UserId { get; set; }

        [JsonProperty(PropertyName = "user_claims")]
        public List<UserClaim> UserClaims { get; set; }
    }

    public class UserClaim
    {
        [JsonProperty(PropertyName = "typ")]
        public string Type { get; set; }

        [JsonProperty(PropertyName = "val")]
        public string Value { get; set; }
    }
}
