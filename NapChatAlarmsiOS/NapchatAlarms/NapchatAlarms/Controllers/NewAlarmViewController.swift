//
//  NewAlarmViewController.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-05-13.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit

class NewAlarmViewController: UIViewController {

    @IBOutlet weak var alarmDatePicker: UIDatePicker!
    var alarm: AlarmModel?
    
    @IBOutlet weak var alarmSwitch: UISwitch!
    var alarmTime: Date? {
        didSet {
//            if alarmTime != nil {
//                createButton.isEnabled = true
//                createButton.backgroundColor = #colorLiteral(red: 0.2588235438, green: 0.7568627596, blue: 0.9686274529, alpha: 1)
//            } else {
//                createButton.isEnabled = false
//                createButton.backgroundColor = #colorLiteral(red: 0.8039215803, green: 0.8039215803, blue: 0.8039215803, alpha: 1)
//            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        if let alarm = alarm {
            self.title = "Edit Alarm"
            print(alarm.time)
            alarmDatePicker.setDate(alarm.time, animated: true)
            alarmSwitch.setOn(alarm.isEnabled, animated: false)
            
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        super.prepare(for: segue, sender: sender)
        
        if alarmTime != nil {
            //            let formatter = DateFormatter()
            //            formatter.dateFormat = "HH:mm"
            //            let formattedTime = formatter.date(from: alarmTime!.description)
            
            alarm = AlarmModel(time: alarmTime!, timeString: alarmTime!.description, isEnabled: alarmSwitch.isOn)
            
            print(alarm?.timeString)
            print(alarm?.isEnabled)
        } else {
            return
        }
    }
    
    @IBAction func cancelButtonSelected(_ sender: UIBarButtonItem) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func timeChanged(_ sender: UIDatePicker) {
        alarmTime = sender.date
    }
    
    
}




