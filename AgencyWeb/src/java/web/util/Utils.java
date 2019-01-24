/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;

import logic.SignInValue;

public class Utils {
    
    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
    
    public static String getSignUpValueString(SignInValue value, String username) {
        switch (value) {    
            case NOT_FOUND:
                return "No account found with this username.";
                
            case NOT_ACCEPTED:
                return "Account is stil in validation process.";
                
            case WRONG_CREDENTIALS:
                return "Username or Password wrong.";
                
            default:
                throw new IllegalStateException("Unexpected value found:" + value);
        }
    }
}