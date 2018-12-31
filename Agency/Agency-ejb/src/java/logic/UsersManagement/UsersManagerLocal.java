/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.UsersManagement;

import javax.ejb.Local;
import logic.TUserDTO;

/**
 *
 * @author bruno
 */
@Local
public interface UsersManagerLocal {
    boolean signIn(String username, String password);
    boolean signUp(TUserDTO userDTO);
    boolean acceptTUser(TUserDTO userDTO, String username);
    TUserDTO getTUserDTO(String username);
    TUser getTUserByUsername(String usernameOfWantedUser);
}
