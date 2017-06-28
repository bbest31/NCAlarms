using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NapChat.Model;

using Foundation;
using UIKit;

namespace NapChat.iOS.Renderers
{
    public class AlarmTableSource : UITableViewSource
    {
        List<Alarm> Alarms;
        string CellIdentifier = "TableCell";

        public AlarmTableSource(List<Alarm> UserAlarms)
        {
            Alarms = UserAlarms;
        }

        public override nint RowsInSection(UITableView tableview, nint section)
        {
            return Alarms.Count;
        }

        public override UITableViewCell GetCell(UITableView tableView, NSIndexPath indexPath)
        {
            UITableViewCell cell = tableView.DequeueReusableCell(CellIdentifier);
            Alarm alarm = Alarms[indexPath.Row];

            if(cell == null)
            {
                //Will change style to subtitle to indicate Days it repeats or not.
                cell = new UITableViewCell(UITableViewCellStyle.Default, CellIdentifier);
            }

            cell.TextLabel.Text = alarm.timeDisplay;
            cell.AccessoryView = new UISwitch();

            return cell;
        }
    }
}