package controllers;

import models.Client;
import utils.ClientDAO;

public class RegisterController {

    public String register(String name, String birthDate, String email, String password, String phone) {
        try {
            // Verificar se email já existe no banco
            if (ClientDAO.emailExists(email)) {
                return "Este e-mail já está cadastrado!";
            }

            // Criar cliente
            Client client = new Client(name, birthDate, email, password, phone);

            // Inserir no banco
            ClientDAO.insert(client);

            return "SUCCESS";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Erro ao criar conta: " + e.getMessage();
        }
    }
}

