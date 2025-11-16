# Resumo do Projeto E-Commerce JavaFX

## Visão Geral

Este projeto implementa um sistema completo de e-commerce de eletrônicos com interface gráfica JavaFX, integrado ao backend Java fornecido. O sistema permite cadastro de usuários, navegação em catálogo de produtos, gerenciamento de carrinho de compras e finalização de pedidos.

## Arquitetura do Sistema

O projeto segue o padrão **MVC (Model-View-Controller)** com a seguinte estrutura:

### **Models (Backend)**
Contém as classes de negócio fornecidas originalmente:
- `Client.java` - Gerencia dados e validações de clientes
- `Product.java` - Gerencia produtos com estoque e preços
- `Order.java` - Gerencia pedidos de compra
- `OrderItem.java` - Representa itens individuais do pedido
- `ValidateUtils.java` - Utilitários de validação (email, senha, telefone, nome)

### **Views (Frontend JavaFX)**
Implementa as interfaces gráficas:
- `LoginView.java` - Tela de autenticação
- `RegisterView.java` - Tela de cadastro de novos usuários
- `MainView.java` - Tela principal com catálogo de produtos
- `CartView.java` - Tela do carrinho de compras

### **Controllers**
Gerencia a lógica entre views e models:
- `LoginController.java` - Controla autenticação de usuários
- `RegisterController.java` - Controla cadastro de novos clientes

### **Utils (Utilitários)**
Classes auxiliares para gerenciamento de dados:
- `DataStore.java` - Singleton que gerencia dados da aplicação (clientes, produtos, pedidos)
- `Cart.java` - Singleton que gerencia o carrinho de compras

## Funcionalidades Implementadas

### 1. Sistema de Autenticação
- Login com validação de e-mail e senha
- Cadastro de novos usuários com validações completas
- Sessão de usuário mantida durante navegação

### 2. Catálogo de Produtos
- Exibição de 15 produtos eletrônicos pré-cadastrados
- Filtro por categoria (Notebooks, Smartphones, Periféricos, Monitores, Áudio, Tablets)
- Cards de produtos com informações detalhadas
- Seleção de quantidade antes de adicionar ao carrinho

### 3. Carrinho de Compras
- Visualização de itens em formato de tabela
- Cálculo automático de subtotais e total
- Remoção individual de itens
- Opção de limpar carrinho completo
- Validação de estoque antes de finalizar

### 4. Gestão de Pedidos
- Confirmação de pedidos com atualização de estoque
- Histórico de pedidos por cliente
- Validações de quantidade e disponibilidade

## Validações Implementadas

O sistema implementa validações rigorosas usando a classe `ValidateUtils`:

- **Nome**: Apenas letras e espaços, mínimo 2 caracteres
- **E-mail**: Formato válido com regex
- **Senha**: Mínimo 8 caracteres, contendo letras, números e símbolos especiais
- **Telefone**: 10 a 15 dígitos numéricos
- **Produto**: Nome não vazio, preço positivo, estoque não negativo

## Design e Interface

O frontend foi desenvolvido com foco em:

- **Usabilidade**: Interface intuitiva e fácil de navegar
- **Design Moderno**: Uso de cores harmoniosas e elementos visuais limpos
- **Responsividade**: Layout adaptado para diferentes resoluções
- **Feedback Visual**: Mensagens claras de sucesso e erro
- **Consistência**: Padrão visual mantido em todas as telas

### Paleta de Cores
- **Primária**: #3498db (azul)
- **Sucesso**: #27ae60 (verde)
- **Erro**: #e74c3c (vermelho)
- **Neutro**: #2c3e50 (cinza escuro)
- **Background**: #f5f5f5 (cinza claro)

## Fluxo de Navegação

```
Login → Cadastro (opcional) → Catálogo Principal → Carrinho → Confirmação → Catálogo
  ↑                                    ↓                                      ↓
  └────────────────────────────────── Logout ←──────────────────────────────┘
```

## Dados Pré-cadastrados

O sistema já vem com 15 produtos eletrônicos distribuídos em 6 categorias:

| Categoria | Quantidade | Faixa de Preço |
|-----------|------------|----------------|
| Notebooks | 3 | R$ 4.500 - R$ 8.900 |
| Smartphones | 3 | R$ 1.500 - R$ 7.500 |
| Periféricos | 3 | R$ 380 - R$ 650 |
| Monitores | 2 | R$ 1.800 - R$ 2.200 |
| Áudio | 2 | R$ 1.900 - R$ 2.100 |
| Tablets | 2 | R$ 3.200 - R$ 5.500 |

## Tecnologias Utilizadas

- **Linguagem**: Java 11+
- **Interface Gráfica**: JavaFX
- **Padrões de Projeto**: Singleton, MVC
- **Estrutura**: Modular com packages organizados

## Arquivos de Configuração

- `module-info.java` - Configuração do módulo JavaFX
- `compile.sh` - Script de compilação
- `README.md` - Documentação completa
- `CONFIGURACAO_INTELLIJ.md` - Guia de configuração do IntelliJ

## Pontos Fortes do Projeto

1. **Integração Completa**: Frontend totalmente integrado com o backend fornecido
2. **Validações Robustas**: Uso das validações do backend em toda a aplicação
3. **Código Organizado**: Estrutura modular e bem documentada
4. **Interface Profissional**: Design moderno e intuitivo
5. **Facilidade de Uso**: Fluxo de navegação claro e direto
6. **Extensibilidade**: Fácil adicionar novos produtos, categorias e funcionalidades

## Possíveis Melhorias Futuras

1. **Persistência de Dados**: Integração com banco de dados (MySQL, PostgreSQL)
2. **Imagens de Produtos**: Adicionar fotos reais dos produtos
3. **Sistema de Busca**: Implementar busca por nome e características
4. **Painel Admin**: Interface para gerenciar produtos e pedidos
5. **Histórico de Pedidos**: Visualização de compras anteriores
6. **Sistema de Avaliações**: Permitir clientes avaliarem produtos
7. **Cupons de Desconto**: Sistema de promoções e descontos
8. **Múltiplos Endereços**: Gerenciamento de endereços de entrega
9. **Métodos de Pagamento**: Integração com gateways de pagamento
10. **Notificações**: Sistema de alertas e notificações

## Considerações Técnicas

### Gerenciamento de Estado
O sistema utiliza dois Singletons principais:
- `DataStore`: Gerencia todos os dados da aplicação
- `Cart`: Gerencia o estado do carrinho de compras

### Navegação entre Telas
A navegação é feita através da substituição de cenas no Stage principal:
```java
MainApp.getPrimaryStage().setScene(newView.getScene());
```

### Atualização de Estoque
O estoque é atualizado automaticamente ao confirmar um pedido através do método `confirmOrder()` da classe `Order`.

## Conclusão

Este projeto demonstra uma implementação completa de um sistema de e-commerce com interface gráfica JavaFX, integrando perfeitamente com o backend Java fornecido. O sistema está pronto para uso e pode ser facilmente estendido com novas funcionalidades.

---

**Desenvolvido como projeto de integração Frontend JavaFX + Backend Java**
