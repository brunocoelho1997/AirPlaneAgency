/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencyclientweb;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import logic.websearch.Flight;

public class ClientRS {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://192.168.56.175:8080/AgencyClientWebServ/webresources";

    public ClientRS() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("flight");
    }
    
    public List<Flight> getAllFlights() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("/listall");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Flight>> () {});
    }
    
    public List<Flight> getFlights(String origin, String destiny) throws ClientErrorException {
        WebTarget resource = webTarget;
        
        resource = resource
                .path("/list")
                .queryParam("origin", origin)
                .queryParam("destiny", destiny);
        
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Flight>> () {});
    }

    public void close() {
        client.close();
    }
 
}