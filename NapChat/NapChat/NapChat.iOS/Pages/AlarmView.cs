using System;
using System.Drawing;

using CoreGraphics;
using Foundation;
using UIKit;

namespace NapChat.iOS.Pages
{
    [Register("AlarmView")]
    public class AlarmView : UIView
    {
        public AlarmView()
        {
            Initialize();
        }

        public AlarmView(RectangleF bounds) : base(bounds)
        {
            Initialize();
        }

        void Initialize()
        {
            BackgroundColor = UIColor.White;
        }
    }
}