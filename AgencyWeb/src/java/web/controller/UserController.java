
package web.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import logic.NoPermissionException;
import logic.TTripDTO;
import logic.TUserDTO;
import logic.UsersManagement.UsersManagerLocal;

@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private UsersManagerLocal userManager;
    
    private String username;
    private float amountToDepositTemp;
    
    
    public UserController() {
    }
    
    @PostConstruct
    private void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{sessionController}", SessionController.class);

        SessionController sessionController = (SessionController)vex.getValue(ctx.getELContext());
        this.username = sessionController.getUsername();
        
        //System.out.println("\n\n\n\n USERNAME ON POSTCONTRUCT: " + username);
    }
    
    public String depositToAccount(){
        userManager.depositToAccount((float) amountToDepositTemp, username);
        amountToDepositTemp = 0;
        return null;
    }
    
    public Double getBalance()
    {
        return userManager.getBalance(username);
    }

    public float getAmountToDepositTemp() {
        return amountToDepositTemp;
    }

    public void setAmountToDepositTemp(float amountToDepositTemp) {
        this.amountToDepositTemp = amountToDepositTemp;
    }
    
    public List<TUserDTO> getAllUsers(){
        return userManager.findAllUsers();
    }
    public String processAcceptUser(TUserDTO user) throws NoPermissionException {
	    
        userManager.acceptTUser(user, username);
	return null;
    }
    
    public TUserDTO findUser(int id) {
        return userManager.findUser(id);
    }
}