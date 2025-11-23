package models;

import utils.ClientDAO;
import models.Client;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("Gabriel", "23/11/2000", "gabriel@email.com", "Gabriel@123456", "5542999909");
        client.setCep("85892-000");
        client.setCidade("Santa Helena");
        client.setEstado("PR");
        client.setPais("Brasil");

        try {
            ClientDAO.insert(client);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar cliente:");
            e.printStackTrace();
        }
    }
}


