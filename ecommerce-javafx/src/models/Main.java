package models;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Client c1 = new Client("Gabriel", "02/09/2006", "gabriel@123.com", "Gabriel@1398", "(11) 987907654");

        Product notebook = new Product("Notebook", "Ultrabook leve", 3500.0f, 10,
                "Eletrônicos", "Dell", "XPS 13", "Dell Inc.", LocalDate.of(2023, 5, 10));

        Product mouse = new Product("Mouse", "Mouse sem fio", 150.0f, 20,
                "Periféricos", "Logitech", "M185", "Logitech", LocalDate.of(2023, 1, 15));

        Order order = new Order(c1);
        order.addProduct(notebook, 2);
        order.addProduct(mouse, 3);

        System.out.println(order);
        order.confirmOrder();
        System.out.println("Estoque notebook: " + notebook.getQuantityInStock());
        System.out.println("Estoque mouse: " + mouse.getQuantityInStock());
    }
}

