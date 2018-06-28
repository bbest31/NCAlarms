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
        alarmTableView.setEditing(isEditing, animated: true)
        alarms = initializeFakeAlarms()
        
        alarmTableView.dataSource = self
        alarmTableView.delegate = self
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        
        switch(segue.identifier ?? "") {
        case "addAlarm":
            print("adding alarm")
            
        case "showAlarm":
            print("viewing alarm")
            guard let newAlarmViewController = segue.destination as? NewAlarmViewController else {
                fatalError("Unexpected destination: \(segue.destination)")
            }
            
            guard let selectedAlarmCell = sender as? AlarmTableViewCell else {
                fatalError("Unexpected sender: \(sender)")
            }
            
            guard let indexPath = alarmTableView.indexPath(for: selectedAlarmCell) else {
                fatalError("The selected cell is not being displayed by the table")
            }
            
            let selectedAlarm = alarms[indexPath.row]
            newAlarmViewController.alarm = selectedAlarm
            
        default:
            print("default")
        }
    }
    
    // MARK: - Table View Methods
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return alarms.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = alarmTableView.dequeueReusableCell(withIdentifier: "alarmCell", for: indexPath) as? AlarmTableViewCell else {
            fatalError("The dequeued cell is not an instance of AlarmTableViewCell")
        }

        let alarm = alarms[indexPath.row]
        cell.alarmLabel.text = alarm.timeString
        cell.alarmEnabledSwitch.isOn = alarm.isEnabled
        
        return cell
    }
    
//    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
//        return true
//    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if (editingStyle == UITableViewCellEditingStyle.delete) {
            alarms.remove(at: indexPath.row)
            alarmTableView.reloadData()
        }
    }

    // create fake alarms until real ones are created by the user
    func initializeFakeAlarms() -> [AlarmModel] {
        let alarm1 = AlarmModel(time: Date(), timeString: "10:23 AM", isEnabled: true)
        let alarm2 = AlarmModel(time: Date(), timeString: "7:45 AM", isEnabled: false)
        return [alarm1, alarm2]
    }
    
    private func saveAlarms() {
        let isSuccessfulSave = NSKeyedArchiver.archiveRootObject(alarms, toFile: AlarmModel.ArchiveURL.path)
        
        if isSuccessfulSave {
            print("Alarms successfully saved.")
        } else {
            print("Failed to save alarms...")
        }
    }
    
    private func loadAlarms() -> [AlarmModel]? {
        return NSKeyedUnarchiver.unarchiveObject(withFile: AlarmModel.ArchiveURL.path) as? [AlarmModel]
    }
    
    @IBAction func unwindToAlarmList(sender: UIStoryboardSegue) {
        if let sourceViewController = sender.source as? NewAlarmViewController, let alarm = sourceViewController.alarm {
            
            let newIndexPath = IndexPath(row: alarms.count, section: 0)
            
            alarms.append(alarm)
            alarmTableView.insertRows(at: [newIndexPath], with: .automatic)
            
        }
    }
    
}



