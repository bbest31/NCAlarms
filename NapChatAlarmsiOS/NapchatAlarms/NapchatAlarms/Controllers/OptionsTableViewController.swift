//
//  OptionsTableViewController.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-05-20.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit
import Firebase

class OptionsTableViewController: UITableViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 3
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return OptionsConstants.optionsStrings.count
        } else if section == 1 {
            return OptionsConstants.supportStrings.count
        } else {
            return OptionsConstants.aboutStrings.count
        }
    }


    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var optionName = ""
        
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "OptionsTableViewCell", for: indexPath) as? OptionsTableViewCell else {
            fatalError("The dequeued cell is not an instance of OptionsTableViewCell")
        }
        
        if indexPath.section == 0 {
            optionName = OptionsConstants.optionsStrings[indexPath.row]
        } else if indexPath.section == 1 {
            optionName = OptionsConstants.supportStrings[indexPath.row]
        } else {
            optionName = OptionsConstants.aboutStrings[indexPath.row]
        }
        
        cell.menuItemLabel.text = optionName
        return cell
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return OptionsConstants.sectionIdentifiers[section]
        
    }


    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "unwindToLandingPageSegue" {
            let firebaseAuth = Auth.auth()
            do {
                try firebaseAuth.signOut()
            } catch let signOutError as NSError {
                print ("Error signing out: %@", signOutError)
            }
        }
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        var segueName: String
        if indexPath.section == 0 {
            segueName = OptionsConstants.optionsSegueIdentifiers[indexPath.row]
        } else if indexPath.section == 1 {
            segueName = OptionsConstants.optionsSegueIdentifiers[indexPath.row]
        } else {
            segueName = OptionsConstants.optionsSegueIdentifiers[indexPath.row]
        }
        
        if segueName == "unwindToIntroWithSegue" {
            performSegue(withIdentifier: "unwindToLandingPageSegue", sender: self)
            //let introController = self.storyboard?.instantiateViewController(withIdentifier: "introPage")
            //self.present(introController!, animated:true, completion:nil)
        }
        
        self.performSegue(withIdentifier: segueName, sender: self)
        
    }
}
