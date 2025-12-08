-- Script SQL para criar as tabelas do banco de dados E-Commerce
-- Execute este script no seu Gerenciador MySQL para criar a estrutura necessária

CREATE DATABASE IF NOT EXISTS Ecommerce;
USE Ecommerce;

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    cep VARCHAR(10),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    pais VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity_in_stock INT NOT NULL DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    category VARCHAR(100),
    brand VARCHAR(100),
    model VARCHAR(100),
    manufacturer VARCHAR(100),
    manufacture_date DATE,
    discount DECIMAL(5, 2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_email VARCHAR(255) NOT NULL,
    order_date DATE NOT NULL,
    total DECIMAL(10, 2),
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_email) REFERENCES clients(email) ON DELETE CASCADE
);

-- Tabela de Itens do Pedido
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY
    client_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
    );

Use ecommerce;

INSERT INTO products (name, description, price, quantity_in_stock, category, brand, model, manufacturer, manufacture_date, active, discount)
VALUES
-- Notebooks
('Notebook Dell XPS 13', 'Ultrabook leve e potente, ideal para trabalho e estudos', 4500.0, 15, 'Notebooks', 'Dell', 'XPS 13', 'Dell Inc.', '2023-05-10', 1, 0.0),
('Notebook Lenovo ThinkPad', 'Notebook corporativo com alta durabilidade', 5200.0, 10, 'Notebooks', 'Lenovo', 'ThinkPad X1', 'Lenovo', '2023-06-15', 1, 0.0),
('Notebook Asus ROG', 'Notebook gamer de alta performance', 8900.0, 8, 'Notebooks', 'Asus', 'ROG Strix', 'Asus', '2023-07-20', 1, 0.0),

-- Smartphones
('Samsung Galaxy S23', 'Smartphone premium com câmera de 200MP', 3800.0, 25, 'Smartphones', 'Samsung', 'Galaxy S23', 'Samsung Electronics', '2023-03-01', 1, 0.0),
('iPhone 15 Pro', 'iPhone com chip A17 Pro e titânio', 7500.0, 12, 'Smartphones', 'Apple', 'iPhone 15 Pro', 'Apple Inc.', '2023-09-15', 1, 0.0),
('Xiaomi Redmi Note 13', 'Smartphone com excelente custo-benefício', 1500.0, 30, 'Smartphones', 'Xiaomi', 'Redmi Note 13', 'Xiaomi', '2023-08-10', 1, 0.0),

-- Periféricos
('Mouse Logitech MX Master 3', 'Mouse ergonômico sem fio', 450.0, 40, 'Periféricos', 'Logitech', 'MX Master 3', 'Logitech', '2023-01-15', 1, 0.0),
('Teclado Mecânico Keychron K2', 'Teclado mecânico wireless', 650.0, 20, 'Periféricos', 'Keychron', 'K2', 'Keychron', '2023-02-20', 1, 0.0),
('Webcam Logitech C920', 'Webcam Full HD 1080p', 380.0, 35, 'Periféricos', 'Logitech', 'C920', 'Logitech', '2023-04-05', 1, 0.0),

-- Monitores
('Monitor LG UltraWide 29''', 'Monitor ultrawide 29 polegadas', 1800.0, 18, 'Monitores', 'LG', '29WP500', 'LG Electronics', '2023-05-25', 1, 0.0),
('Monitor Samsung Odyssey G5', 'Monitor gamer curvo 27'' 144Hz', 2200.0, 12, 'Monitores', 'Samsung', 'Odyssey G5', 'Samsung Electronics', '2023-06-30', 1, 0.0),

-- Fones de Ouvido
('Fone Sony WH-1000XM5', 'Fone com cancelamento de ruído premium', 1900.0, 22, 'Áudio', 'Sony', 'WH-1000XM5', 'Sony Corporation', '2023-03-12', 1, 0.0),
('AirPods Pro 2', 'Fones true wireless da Apple', 2100.0, 28, 'Áudio', 'Apple', 'AirPods Pro 2', 'Apple Inc.', '2023-09-20', 1, 0.0),

-- Tablets
('iPad Air M2', 'Tablet Apple com chip M2', 5500.0, 10, 'Tablets', 'Apple', 'iPad Air', 'Apple Inc.', '2023-10-05', 1, 0.0),
('Samsung Galaxy Tab S9', 'Tablet Android premium', 3200.0, 15, 'Tablets', 'Samsung', 'Galaxy Tab S9', 'Samsung Electronics', '2023-08-15', 1, 0.0);