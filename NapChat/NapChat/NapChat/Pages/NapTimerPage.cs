﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace NapChat.Pages
{
    /// <summary>
    /// Custom Renderer Page for Nap-Timer.
    /// </summary>
	public class NapTimerPage : ContentPage
	{
        public async Task backToHome()
        {
            await Navigation.PopAsync();
        }
		
		}
	}
