using System;
using System.Collections.Generic;
using System.Text;

namespace NapChat.Helpers
{
    public class Vibrations : Dictionary<string,long[]>
    {
        public Vibrations()
        {
            this.Add("Standard", new long[] {0, 1000, 500, 1000, 500, 1000, 500 });
            this.Add("Constant", new long[] { 0, 30000 });
            
        }
    }
}
