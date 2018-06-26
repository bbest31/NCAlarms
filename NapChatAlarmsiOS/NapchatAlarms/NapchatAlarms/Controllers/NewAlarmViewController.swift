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
    @IBOutlet weak var createButton: UIButton!
    var alarm: AlarmModel?
    var alarmTime: Date? {
        didSet {
            if alarmTime != nil {
                createButton.isEnabled = true
                createButton.backgroundColor = #colorLiteral(red: 0.2588235438, green: 0.7568627596, blue: 0.9686274529, alpha: 1)
            } else {
                createButton.isEnabled = false
                createButton.backgroundColor = #colorLiteral(red: 0.8039215803, green: 0.8039215803, blue: 0.8039215803, alpha: 1)
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
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
            
            alarm = AlarmModel(time: alarmTime!, timeString: alarmTime!.description, isEnabled: true)
            
            print(alarm?.timeString)
        } else {
            return
        }
    }
    
    
    @IBAction func cancelButtonTapped(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func timeChanged(_ sender: UIDatePicker) {
        alarmTime = sender.date
    }
}




