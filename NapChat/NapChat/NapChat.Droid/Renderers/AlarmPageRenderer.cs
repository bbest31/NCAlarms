using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Xamarin.Forms.Platform.Android;
using Xamarin.Forms;
using NapChat.Pages;
using NapChat.Helpers;
using NapChat.Droid.Renderers;
using NapChat.Droid.Broadcast;
using System.Threading.Tasks;

[assembly: ExportRenderer(typeof(AlarmPage), typeof(AlarmPageRenderer))]
namespace NapChat.Droid.Renderers
{
    /// <summary>
    /// Android Page Renderer for NapTimerPage.
    /// </summary>
    public class AlarmPageRenderer : PageRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Page> e)
        {
            base.OnElementChanged(e);
            if (e.OldElement != null || Element == null)
            {
                return;
            }
        }
    }
}