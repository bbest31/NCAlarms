//
//  AlarmModel.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-04-28.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit

class AlarmModel {
    var timeString: String // temporary until a proper timestamp is used
    var isEnabled: Bool
    
    init(timeString: String, isEnabled: Bool) {
        self.timeString = timeString
        self.isEnabled = isEnabled
    }
}
