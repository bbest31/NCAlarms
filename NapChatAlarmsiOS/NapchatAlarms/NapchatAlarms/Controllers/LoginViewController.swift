//
//  LoginViewController.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-05-05.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit
import Firebase

class LoginViewController: UIViewController {

    

    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var passwordField: UITextField!
    @IBOutlet weak var errorMessageLabel: UILabel!
    
    
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
        Auth.auth().signIn(withEmail: emailField.text!, password: passwordField.text!) { (user, error) in
            if let error = error {
                self.errorMessageLabel.text = error.localizedDescription
                return
            } else {
                self.performSegue(withIdentifier: "segueToAppFromLogin", sender: self)
                self.emailField.text = ""
                self.passwordField.text = ""
            }
        }

    }
    

}
