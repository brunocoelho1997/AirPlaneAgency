/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import web.util.Utils;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
         // Retrieve the value passed to this method
         String confirmPassword = (String) value;
    
        // Retrieve the temporary value from the password field
        UIInput passwordInput = (UIInput) component.findComponent("signUpPassword");
    
        String password = (String) passwordInput.getLocalValue();
        if (Utils.isEmpty(password) || Utils.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {
            String message = context.getApplication().evaluateExpressionGet(context, "#{msgs['nomatch']}", String.class);
            FacesMessage facesMessage = new FacesMessage(message);
            
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            throw new ValidatorException(facesMessage);
        }
    }
    
}