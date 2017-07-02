using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Foundation;
using UIKit;
using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using NapChat.RenderedViews;
using NapChat.iOS.Renderers;
using NapChat.iOS;

[assembly: ExportRenderer (typeof (CustomTimePicker), typeof (CustomTimePickerRenderer))]
namespace NapChat.iOS.Renderers
{
    public class CustomTimePickerRenderer : TimePickerRenderer
    {
       protected override void OnElementChanged(ElementChangedEventArgs<TimePicker> e)
        {
            base.OnElementChanged(e);
            Control.BackgroundColor = UIColor.White;
            Control.BorderStyle = UITextBorderStyle.None;
            Control.TextColor = UIColor.Purple;
            Control.TextAlignment = UITextAlignment.Center;
            Control.TintColor = UIColor.LightGray;

        }
    }
}