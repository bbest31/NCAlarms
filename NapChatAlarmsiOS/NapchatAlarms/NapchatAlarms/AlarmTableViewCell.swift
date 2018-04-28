//
//  AlarmTableViewCell.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-04-28.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit

class AlarmTableViewCell: UITableViewCell {

    @IBOutlet weak var alarmEnabledSwitch: UISwitch!
    @IBOutlet weak var alarmLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
