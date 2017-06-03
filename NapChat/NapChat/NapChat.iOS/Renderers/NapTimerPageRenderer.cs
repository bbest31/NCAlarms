using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Foundation;
using UIKit;
using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using NapChat.iOS.Renderers;
using NapChat.Pages;

[assembly: ExportRenderer(typeof(NapTimerPage), typeof(NapTimerPageRenderer))]

namespace NapChat.iOS.Renderers
{
    /// <summary>
    /// iOS Page Renderer for NapTimerPage.
    /// </summary>
    public class NapTimerPageRenderer : PageRenderer
    {
        protected override void OnElementChanged(VisualElementChangedEventArgs e)
        {
            base.OnElementChanged(e);
            if (e.OldElement != null || Element == null)
            {
                return;
            }

            //Plaftorm specific code goes here.

        }
    }
}