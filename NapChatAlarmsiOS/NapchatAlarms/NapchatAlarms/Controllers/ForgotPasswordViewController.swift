//
//  ForgotPasswordViewController.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-05-28.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit
import Firebase

class ForgotPasswordViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var resetPasswordUserFeedback: UILabel!
    @IBOutlet weak var resetPasswordEmailField: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()

        resetPasswordEmailField.delegate = self
        
        //Looks for single or multiple taps.
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(ForgotPasswordViewController.dismissKeyboard))
        
        view.addGestureRecognizer(tap)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        // Hide the keyboard.
        textField.resignFirstResponder()
        return true
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        // do something with the text entered into the text field
    }
    
    //Calls this function when the tap is recognized.
    @objc func dismissKeyboard() {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        view.endEditing(true)
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    @IBAction func submitButtonPressed(_ sender: UIButton) {
        if let resetPasswordEmail = resetPasswordEmailField.text {
            Auth.auth().sendPasswordReset(withEmail: resetPasswordEmail) { error in
                if let error = error {
                    self.resetPasswordUserFeedback.text = error.localizedDescription
                    return
                } else {
                    self.dismiss(animated: true, completion: nil)
                }
            }
        } else {
            self.resetPasswordUserFeedback.text = "Email Address Required."
            return
        }
    }
    
    
    @IBAction func backButtonPressed(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
}
