/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.UsersManagement;

/**
 *
 * @author bruno
 */
public class Client extends User{
    
    private float balance;
    private boolean accepted;
    private String name;
    
    public Client(String username, String password, String name) {
        super(username, password);
        
        this.balance = (float) 0.0;
        this.accepted = false;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
