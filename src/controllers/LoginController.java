package controllers;

import models.Client;
import utils.DataStore;

public class LoginController {
    
    private DataStore dataStore;
    
    public LoginController() {
        dataStore = DataStore.getInstance();
    }
    
    public boolean login(String email, String password) {
        Client client = dataStore.authenticateClient(email, password);
        
        if (client != null) {
            dataStore.setCurrentClient(client);
            return true;
        }
        
        return false;
    }
}
