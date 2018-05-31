//
//  LandingPageViewController.swift
//  NapchatAlarms
//
//  Created by Peter Weckend on 2018-05-05.
//  Copyright © 2018 Napchat Alarms. All rights reserved.
//

import UIKit
import Firebase
import GoogleSignIn

class LandingPageViewController: UIViewController, GIDSignInUIDelegate {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        checkUserLoggedIn()

        
        // TODO(developer) Configure the sign-in button look/feel
        // ...
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
    
    @IBOutlet weak var GoogleSignInButton: GIDSignInButton!
    
    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error?) {
        // ...
        print("1")
        if let error = error {
            // ...
            print("2")

            return
        }
        print("3")

        guard let authentication = user.authentication else { return }
        let credential = GoogleAuthProvider.credential(withIDToken: authentication.idToken,
                                                       accessToken: authentication.accessToken)
        // ...
        Auth.auth().signInAndRetrieveData(with: credential) { (authResult, error) in
            if let error = error {
                // ...
                print("4")

                return
            }
            // User is signed in
            // ...
            print("signedin")
            self.performSegue(withIdentifier: "segueLandingToHome", sender: self)
            
        }
    }
    
    func checkUserLoggedIn() {
        if Auth.auth().currentUser?.uid != nil {
            //user is logged in
            print("logged in")
            self.performSegue(withIdentifier: "segueLandingToHome", sender: self)
        
        }else{
            //user is not logged in
            print("not logged in")
            GIDSignIn.sharedInstance().uiDelegate = self
            GIDSignIn.sharedInstance().signIn()
        }
    }
    

}
