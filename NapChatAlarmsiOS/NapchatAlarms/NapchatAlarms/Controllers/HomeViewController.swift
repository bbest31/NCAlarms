//
//  HomeViewController.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-04-22.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit

class HomeViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var alarmTableView: UITableView!
    var alarms = [AlarmModel]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        alarms = initializeFakeAlarms()
        
        alarmTableView.dataSource = self
        alarmTableView.delegate = self
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return alarms.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = alarmTableView.dequeueReusableCell(withIdentifier: "alarmCell", for: indexPath) as? AlarmTableViewCell else {
            fatalError("The dequeued cell is not an instance of AlarmTableViewCell")
        }
        print("cells created")
        let alarm = alarms[indexPath.row]
        cell.alarmLabel.text = alarm.timeString
        cell.alarmEnabledSwitch.isOn = alarm.isEnabled
        
        return cell
    }

    // create fake alarms until real ones are created by the user
    func initializeFakeAlarms() -> [AlarmModel] {
        let alarm1 = AlarmModel(timeString: "10:23 AM", isEnabled: true)
        let alarm2 = AlarmModel(timeString: "7:45 AM", isEnabled: false)
        return [alarm1, alarm2]
    }

}

