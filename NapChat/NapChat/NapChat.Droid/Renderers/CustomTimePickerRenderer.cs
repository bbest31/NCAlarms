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
using NapChat.Droid.Broadcast;
using System.Threading.Tasks;
using NapChat.RenderedViews;
using NapChat.Droid.Renderers;
using NapChat;
using NapChat.Droid;

[assembly: ExportRenderer (typeof(CustomTimePicker),typeof(CustomTimePickerRenderer))]

namespace NapChat.Droid.Renderers
{
    public class CustomTimePickerRenderer : TimePickerRenderer
    {
        
        protected override void OnElementChanged (ElementChangedEventArgs<Xamarin.Forms.TimePicker> e)
        {
            base.OnElementChanged(e);
            this.Control.SetTextColor(Android.Graphics.Color.Purple);
            Control.SetBackgroundColor(Android.Graphics.Color.Transparent);
            
        }

        
    }
}