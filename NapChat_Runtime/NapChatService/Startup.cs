using Microsoft.Owin;
using Owin;

[assembly: OwinStartup(typeof(NapChatService.Startup))]

namespace NapChatService
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureMobileApp(app);
        }
    }
}