//
//  LandingPageViewController.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-05-05.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit

class LandingPageViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    // MARK: - Actions
    @IBAction func loginButtonPressed(_ sender: UIButton) {
        self.performSegue(withIdentifier: "segueToLogin", sender: self)
    }
    
    @IBAction func unwindToLandingPageWithSegue(segue:UIStoryboardSegue) {
        
    }
    

}
