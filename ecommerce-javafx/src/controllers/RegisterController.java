package controllers;

import models.Client;
import utils.DataStore;

public class RegisterController {
    
    private DataStore dataStore;
    
    public RegisterController() {
        dataStore = DataStore.getInstance();
    }
    
    public String register(String name, String birthDate, String email, String password, String phone) {
        try {
            // Verificar se email já existe
            if (dataStore.emailExists(email)) {
                return "Este e-mail já está cadastrado!";
            }
            
            // Criar cliente
            Client client = new Client(name, birthDate, email, password, phone);
            
            // Adicionar ao datastore
            dataStore.addClient(client);
            
            return "SUCCESS";
            
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Erro ao criar conta: " + e.getMessage();
        }
    }
}
