# Guia de Configuração do IntelliJ IDEA

Este documento fornece instruções detalhadas para configurar e executar o projeto E-Commerce JavaFX no IntelliJ IDEA.

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java JDK 11 ou superior** (recomendado: JDK 17 ou 21)
- **IntelliJ IDEA** (Community ou Ultimate Edition)
- **JavaFX SDK** (versão compatível com seu JDK)

## Download do JavaFX SDK

Se você ainda não tem o JavaFX SDK instalado:

1. Acesse: https://gluonhq.com/products/javafx/
2. Baixe a versão correspondente ao seu sistema operacional
3. Extraia o arquivo em um local de fácil acesso (ex: `C:\javafx-sdk` no Windows ou `/opt/javafx-sdk` no Linux/Mac)

## Passo 1: Abrir o Projeto

1. Abra o IntelliJ IDEA
2. Clique em **File → Open**
3. Navegue até a pasta `ecommerce-javafx`
4. Clique em **OK**

## Passo 2: Configurar o JDK do Projeto

1. Vá em **File → Project Structure** (ou pressione `Ctrl+Alt+Shift+S`)
2. Em **Project**, selecione:
   - **SDK**: Escolha o JDK 11 ou superior
   - **Language level**: Selecione a versão correspondente ao JDK
3. Clique em **Apply**

## Passo 3: Marcar a Pasta src como Source Root

1. No painel lateral esquerdo (Project), localize a pasta `src`
2. Clique com o botão direito em `src`
3. Selecione **Mark Directory as → Sources Root**

## Passo 4: Adicionar JavaFX como Biblioteca

### Opção A: Adicionar Biblioteca Global (Recomendado)

1. Vá em **File → Project Structure → Libraries**
2. Clique no ícone **+** (Add)
3. Selecione **Java**
4. Navegue até a pasta `lib` dentro do JavaFX SDK que você baixou
5. Selecione a pasta `lib` e clique em **OK**
6. Nomeie a biblioteca como "JavaFX" e clique em **OK**
7. Clique em **Apply** e **OK**

### Opção B: Adicionar via Maven (Alternativa)

Se preferir usar Maven, adicione as dependências no `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21.0.1</version>
    </dependency>
</dependencies>
```

## Passo 5: Configurar Run Configuration

1. Clique em **Run → Edit Configurations**
2. Clique no ícone **+** (Add New Configuration)
3. Selecione **Application**
4. Configure os seguintes campos:

   - **Name**: `E-Commerce JavaFX`
   - **Main class**: `MainApp` (clique no botão `...` e selecione a classe)
   - **VM options**: Adicione a seguinte linha (substitua o caminho pelo seu):
   
   **Windows:**
   ```
   --module-path "C:\caminho\para\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
   ```
   
   **Linux/Mac:**
   ```
   --module-path "/caminho/para/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
   ```

5. Clique em **Apply** e **OK**
## Passo 6: Conectar banco de dados

1. Cerifique-se que o `database_schema.sql` esteja em seu gerenciador MySQL.
2. Caso a conexão tenha uma porta, senha ou nome diferente, altere no arquivo `DataBaseConnection` na pasta `'ecommerce-javafx/src/utils/DataBaseConnection'`
## Passo 7: Executar o Projeto

1. Certifique-se de que a configuração `E-Commerce JavaFX` está selecionada na barra de ferramentas
2. Clique no botão **Run** (ícone de play verde) ou pressione `Shift+F10`
3. A aplicação deve iniciar e exibir a tela de login

## Solução de Problemas Comuns

### Erro: "Error: JavaFX runtime components are missing"

**Solução**: Verifique se as VM options estão configuradas corretamente com o caminho correto para o JavaFX SDK.

### Erro: "Module javafx.controls not found"

**Solução**: 
1. Verifique se o JavaFX SDK está instalado corretamente
2. Confirme que o caminho nas VM options está correto
3. Certifique-se de que a biblioteca JavaFX foi adicionada ao projeto

### Erro de Compilação: "package models does not exist"

**Solução**:
1. Certifique-se de que a pasta `src` está marcada como **Sources Root**
2. Faça um **Build → Rebuild Project**

### Erro: "Cannot find symbol" em classes de validação

**Solução**: Certifique-se de que todos os arquivos em `src/models` têm a declaração `package models;` no início.

## Estrutura de Pastas no IntelliJ

Após a configuração correta, você deve ver:

```
ecommerce-javafx
├── .idea (gerado pelo IntelliJ)
├── out (arquivos compilados)
├── src (marcado como Sources Root)
│   ├── MainApp.java
│   ├── module-info.java
│   ├── controllers
│   ├── models
│   ├── utils
│   └── views
├── compile.sh
├── CONFIGURACAO_INTELLIJ.md
└── README.md
```

## Testando a Aplicação

### Criar uma Nova Conta

1. Na tela de login, clique em **Criar Conta**
2. Preencha os dados:
   - Nome: João Silva
   - Data de Nascimento: 01/01/1990
   - E-mail: joao@teste.com
   - Senha: Teste@123
   - Telefone: (11) 98765-4321
3. Clique em **Cadastrar**

### Fazer Login

1. Use o e-mail e senha cadastrados
2. Clique em **Entrar**

### Navegar no Catálogo

1. Visualize os produtos disponíveis
2. Use o filtro de categoria no lado esquerdo
3. Adicione produtos ao carrinho

### Finalizar Compra

1. Clique no botão **Carrinho**
2. Revise os itens
3. Clique em **Finalizar Pedido**

## Dicas Adicionais

- **Atalhos úteis no IntelliJ**:
  - `Ctrl+Shift+F10`: Executar a classe atual
  - `Ctrl+F9`: Compilar o projeto
  - `Alt+1`: Abrir/fechar painel do projeto
  - `Ctrl+N`: Buscar classe

- **Debug**: Para debugar, use o botão de debug (ícone de inseto) em vez do botão Run

- **Hot Reload**: O IntelliJ não suporta hot reload para JavaFX, então você precisará reiniciar a aplicação após fazer alterações

## Suporte

Se você encontrar problemas não listados aqui:

1. Verifique se todas as dependências estão instaladas corretamente
2. Certifique-se de que o JDK e JavaFX SDK são compatíveis
3. Tente fazer um **File → Invalidate Caches / Restart**
4. Reconstrua o projeto com **Build → Rebuild Project**

---

**Desenvolvido para IntelliJ IDEA**
