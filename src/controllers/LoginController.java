package controllers;

import models.Client;
import utils.DataStore;
import utils.ClientDAO;

public class LoginController {

    private DataStore dataStore;

    public LoginController() {
        dataStore = DataStore.getInstance();
    }

    public boolean login(String email, String password) {
        try {
            Client client = ClientDAO.authenticate(email, password);

            if (client != null) {
                dataStore.setCurrentClient(client);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}

