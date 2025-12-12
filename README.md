# E-Commerce de Eletrônicos - JavaFX (Desatualizado)

Sistema de e-commerce desenvolvido em Java com interface gráfica JavaFX para venda de produtos eletrônicos.

## 📋 Estrutura do Projeto

```
ecommerce-javafx/
├── src/
│   ├── MainApp.java              # Classe principal da aplicação
│   ├── module-info.java          # Configuração do módulo JavaFX
│   ├── models/                   # Classes de modelo (backend)
│   │   ├── Client.java           # Modelo de cliente
│   │   ├── Product.java          # Modelo de produto
│   │   ├── Order.java            # Modelo de pedido
│   │   ├── OrderItem.java        # Modelo de item do pedido
│   │   ├── ValidateUtils.java    # Utilitários de validação
│   │   └── Main.java             # Classe Main original (não usada no JavaFX)
│   ├── views/                    # Telas da aplicação
│   │   ├── LoginView.java        # Tela de login
│   │   ├── RegisterView.java    # Tela de cadastro
│   │   ├── MainView.java         # Tela principal com catálogo
│   │   └── CartView.java         # Tela do carrinho de compras
│   ├── controllers/              # Controladores
│   │   ├── LoginController.java
│   │   └── RegisterController.java
│   └── utils/                    # Utilitários
│       ├── DataStore.java        # Gerenciamento de dados
│       └── Cart.java             # Gerenciamento do carrinho
└── README.md
```

## 🚀 Funcionalidades

### 1. **Autenticação de Usuários**
- Login com e-mail e senha
- Cadastro de novos clientes com validações:
  - Nome (apenas letras e espaços)
  - E-mail (formato válido)
  - Senha (mínimo 8 caracteres, letras, números e símbolos)
  - Telefone (10-15 dígitos)
  - Data de nascimento

### 2. **Catálogo de Produtos**
- Listagem de produtos eletrônicos
- Filtro por categoria (Notebooks, Smartphones, Periféricos, Monitores, Áudio, Tablets)
- Informações detalhadas: nome, descrição, preço, marca, modelo, estoque
- Adicionar produtos ao carrinho com quantidade personalizada

### 3. **Carrinho de Compras**
- Visualização de itens adicionados
- Cálculo automático de subtotais e total
- Remoção de itens
- Limpar carrinho completo
- Finalização de pedido

### 4. **Gestão de Pedidos**
- Confirmação de pedidos
- Atualização automática de estoque
- Histórico de pedidos por cliente

## 🛠️ Tecnologias Utilizadas

- **Java 11+**
- **JavaFX** (interface gráfica)
- **Padrão MVC** (Model-View-Controller)
- **Singleton Pattern** (DataStore e Cart)

## 📦 Produtos Pré-cadastrados

O sistema já vem com 15 produtos eletrônicos cadastrados:

### Notebooks
- Dell XPS 13 - R$ 4.500,00
- Lenovo ThinkPad X1 - R$ 5.200,00
- Asus ROG Strix - R$ 8.900,00

### Smartphones
- Samsung Galaxy S23 - R$ 3.800,00
- iPhone 15 Pro - R$ 7.500,00
- Xiaomi Redmi Note 13 - R$ 1.500,00

### Periféricos
- Mouse Logitech MX Master 3 - R$ 450,00
- Teclado Mecânico Keychron K2 - R$ 650,00
- Webcam Logitech C920 - R$ 380,00

### Monitores
- LG UltraWide 29'' - R$ 1.800,00
- Samsung Odyssey G5 - R$ 2.200,00

### Áudio
- Fone Sony WH-1000XM5 - R$ 1.900,00
- AirPods Pro 2 - R$ 2.100,00

### Tablets
- iPad Air M2 - R$ 5.500,00
- Samsung Galaxy Tab S9 - R$ 3.200,00

## 🔧 Como Executar no IntelliJ IDEA

### Pré-requisitos
- Java JDK 11 ou superior
- IntelliJ IDEA
- JavaFX SDK configurado

### Passos para Configuração

1. **Abrir o Projeto no IntelliJ**
   - File → Open → Selecione a pasta `ecommerce-javafx`

2. **Configurar JavaFX SDK**
   - File → Project Structure → Libraries
   - Clique em "+" → Java
   - Selecione a pasta `lib` do JavaFX SDK
   - Apply e OK

3. **Configurar VM Options**
   - Run → Edit Configurations
   - Adicione em "VM options":
   ```
   --module-path "CAMINHO_DO_JAVAFX_SDK/lib" --add-modules javafx.controls,javafx.fxml
   ```
   - Substitua `CAMINHO_DO_JAVAFX_SDK` pelo caminho real do seu JavaFX SDK

4. **Configurar Main Class**
   - Main class: `MainApp`

5. **Executar**
   - Clique em Run ou pressione Shift+F10

### Alternativa: Executar via Linha de Comando

```bash
# Compilar
javac --module-path "CAMINHO_DO_JAVAFX_SDK/lib" --add-modules javafx.controls -d out src/**/*.java

# Executar
java --module-path "CAMINHO_DO_JAVAFX_SDK/lib" --add-modules javafx.controls -cp out MainApp
```

## 👤 Usuário de Teste

Para testar o sistema, você pode criar uma nova conta ou usar os seguintes dados de exemplo:

**Criar nova conta:**
- Nome: João Silva
- Data de Nascimento: 01/01/1990
- E-mail: joao@teste.com
- Senha: Teste@123
- Telefone: (11) 98765-4321

## 🎨 Interface

### Tela de Login
- Design moderno e limpo
- Validação de campos
- Opção de criar nova conta

### Tela de Cadastro
- Formulário completo com validações em tempo real
- Mensagens de erro claras
- Requisitos de senha visíveis

### Tela Principal
- Header com informações do usuário e carrinho
- Sidebar com filtros por categoria
- Grid de produtos com cards estilizados
- Botão de adicionar ao carrinho

### Tela do Carrinho
- Tabela com itens do carrinho
- Cálculo de total em tempo real
- Opções de remover itens e limpar carrinho
- Botão de finalizar pedido

## 📝 Validações Implementadas

### Cliente
- **Nome**: Apenas letras e espaços, mínimo 2 caracteres
- **E-mail**: Formato válido (exemplo@dominio.com)
- **Senha**: Mínimo 8 caracteres, contendo letras, números e símbolos (@$!%*?&)
- **Telefone**: 10 a 15 dígitos numéricos
- **Data de Nascimento**: Formato DD/MM/AAAA

### Produto
- **Nome**: Não pode ser vazio
- **Preço**: Deve ser maior que zero
- **Estoque**: Não pode ser negativo

### Pedido
- **Quantidade**: Deve ser maior que zero
- **Estoque**: Verifica disponibilidade antes de confirmar

## 🔄 Fluxo da Aplicação

1. **Login/Cadastro**
   - Usuário faz login ou cria nova conta
   - Validações são aplicadas

2. **Navegação no Catálogo**
   - Usuário visualiza produtos
   - Pode filtrar por categoria
   - Adiciona produtos ao carrinho

3. **Carrinho**
   - Usuário revisa itens
   - Pode remover ou ajustar quantidades
   - Finaliza o pedido

4. **Confirmação**
   - Pedido é confirmado
   - Estoque é atualizado
   - Carrinho é limpo
   - Usuário retorna ao catálogo

## 🎯 Próximas Melhorias Sugeridas

- [ ] Adicionar imagens aos produtos
- [ ] Implementar busca por nome de produto
- [ ] Adicionar sistema de avaliações
- [ ] Implementar histórico de pedidos do cliente
- [ ] Adicionar diferentes métodos de pagamento
- [ ] Implementar sistema de cupons de desconto
- [ ] Adicionar painel administrativo para gerenciar produtos
- [ ] Implementar persistência de dados (banco de dados)
- [ ] Adicionar sistema de favoritos
- [ ] Implementar notificações de estoque baixo

## 📄 Licença

Projeto desenvolvido para fins educacionais.

## 👨‍💻 Autor

Desenvolvido como projeto de e-commerce JavaFX integrado com backend Java.
