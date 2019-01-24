/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javax.ejb.Remote;
import logic.TTripDTO;

@Remote
public interface GuestAgencyManagerRemote {
    
    List<TTripDTO> getCheapestTrips(int count);
    
}