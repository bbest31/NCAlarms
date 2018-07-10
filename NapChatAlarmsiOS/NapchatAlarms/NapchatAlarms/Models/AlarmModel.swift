//
//  AlarmModel.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-04-28.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit
import os.log

class AlarmModel: NSObject, NSCoding {
    
    // MARK: Properties
    struct PropertyKey {
        static let timeString = "timeString"
        static let time = "time"
        static let repeatInd = "repeatInd"
        static let alarmSoundInd = "alarmSoundInd"
        static let isEnabled = "isEnabled"
    }
    
    static let DocumentsDirectory = FileManager().urls(for: .documentDirectory, in: .userDomainMask).first!
    static let ArchiveURL = DocumentsDirectory.appendingPathComponent("alarms")
    
    var timeString: String // temporary until a proper timestamp is used
    var time: Date // in UTC
    var repeatInd: String?
    var alarmSoundInd: String?
    var isEnabled: Bool
    
    init(time: Date, timeString: String, repeatInd: String? = nil, alarmSoundInd: String? = nil, isEnabled: Bool) {
        self.time = time
        self.timeString = timeString
        self.repeatInd = nil
        self.alarmSoundInd = nil
        self.isEnabled = isEnabled
    }
    
    func encode(with aCoder: NSCoder) {
        aCoder.encode(timeString, forKey: PropertyKey.timeString)
        aCoder.encode(time, forKey: PropertyKey.time)
        aCoder.encode(repeatInd, forKey: PropertyKey.repeatInd)
        aCoder.encode(alarmSoundInd, forKey: PropertyKey.alarmSoundInd)
        aCoder.encode(isEnabled, forKey: PropertyKey.isEnabled)
    }
    
    required convenience init?(coder aDecoder: NSCoder) {
        guard let timeString = aDecoder.decodeObject(forKey: PropertyKey.timeString) as? String else {
            os_log("Unable to decode the timeString for an AlarmModel object.", log: OSLog.default, type: .debug)
            return nil
        }
        
        guard let time = aDecoder.decodeObject(forKey: PropertyKey.time) as? Date else {
            os_log("Unable to decode the time for an AlarmModel object.", log: OSLog.default, type: .debug)
            return nil
        }
        
        let repeatInd = aDecoder.decodeObject(forKey: PropertyKey.repeatInd) as? String
        
        let alarmSoundInd = aDecoder.decodeObject(forKey: PropertyKey.alarmSoundInd) as? String
        
        guard let isEnabled = aDecoder.decodeBool(forKey: PropertyKey.isEnabled) as? Bool else {
            os_log("Unable to decode the isEnabled property for an AlarmModel object.", log: OSLog.default, type: .debug)
            return nil
        }
        
        
        self.init(time: time, timeString: timeString, repeatInd: repeatInd, alarmSoundInd: alarmSoundInd, isEnabled: isEnabled)
    }
}


