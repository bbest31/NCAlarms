using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Text;

namespace NapChat.Helpers
{
    public sealed class ServiceLocator
    {
        static readonly Lazy<ServiceLocator> instance = new Lazy<ServiceLocator>(() => new ServiceLocator());
        readonly Dictionary<Type, Lazy<object>> registeredServices = new Dictionary<Type, Lazy<object>>();
        /// <summary>
        /// Singleton instance for the default service locator
        /// </summary>
        public static ServiceLocator Instance => instance.Value;

        /// <summary>
        /// Add a new service implementation
        /// </summary>
        /// <typeparam name="TContract">The type of service</typeparam>
        /// <typeparam name="TService">The service implementation</typeparam>
        public void Add<TContract, TService>() where TService : new()
        {
            registeredServices[typeof(TContract)] = new Lazy<object>(() => Activator.CreateInstance(typeof(TService)));
        }

        /// <summary>
        /// Resolve the service type into the implementation.  This assumes the key used to register the
        /// object is of the appropriate type - throws InvalidCastException if you get this wrong (at runtime)
        /// </summary>
        /// <typeparam name="T">The type of service</typeparam>
        /// <returns>The service implementation</returns>
        public T Resolve<T>() where T : class
        {
            Lazy<object> service;
            if (registeredServices.TryGetValue(typeof(T), out service))
            {
                return (T)service.Value;
            }
            return null;
        }
    }

    /// <summary>
    /// Helper class that holds important Url's, Uri's, etc.
    /// </summary>
    public static class Locations
    {
        /// <summary>
        /// App Service Url: https://napchat.azurewebsties.net
        /// </summary>
        public static readonly string AppServiceUrl = "https://napchat.azurewebsites.net";

        // public static readonly string AadClientId = "0c3309fe-e392-4ca5-8d54-55f69ae1e0f8";

        // public static readonly string AadRedirectUri = "https://zumobook-chapter2.azurewebsites.net/.auth/login/done";

        // public static readonly string AadAuthority = "https://login.windows.net/photoadrianoutlook.onmicrosoft.com";
    }
}
