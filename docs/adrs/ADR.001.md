# ADR: Estruturação de Pacotes

**Data:** 01/01/2026

## Contexto

A estrutura de pastas do projeto segue uma organização modularizada e com foco no domínio para proporcionar uma
separação clara das responsabilidades, fácil navegação e entendimento rápido do projeto sob uma perspectiva
de negócio.

## Decisão

A estrutura básica foi estabelecida da seguinte forma:

```plaintext
📁core                          // Esta camada contem as regras de negócios e os casos de uso da nossa aplicação,
├── 📁feature-name              
│   ├── 📁app                   // Esta camada contém a implementação dos nossos casos de uso.
│   │   ├── 📁gateways          //A camada de interface adapters (Portas)
│   │   └── 📁usecase           // Interfaces de casos de uso, onde temos as assinaturas dos métodos que serão utilizados nas services.
│   │       └── 📁command       // Objetos com dados necessários para realizar alguma ação. Utilizado para tranferir dados entre controllers, useCases, services, events.
│   ├── 📁domain                // Classes de domínio.
│   │   ├── 📁enums
│   │   └── 📁vo                // Objetos de transferência de informações, normalmente customizadas para casos específicos.
│   ├── 📁exception             // Implementação de exceptions customizadas
│   └──📁sk                     // código compartilhado entre os demais pacotes; deve conter, preferencialmente, identificadores e valores de objetos. 
📁infra                         // código de configuração de recursos técnicos que não fazem parte da implementação de negócio em si, como por exemplo configuração de bibliotecas parceiras.
└──📁feature-name
    ├── 📁controller            // Controllers da feature.    
    │   └── 📁openapi           // Classes com anotações de documentação relacionadas ao swagger das nossas controllers.
    ├── 📁persistence           // Repositórios da feature e classes de entidade    
    ├── 📁gateways              // Classe de implementação dos useCase que comunica com os repositories 

```

## Consequências

Em determinados casos pode ser que alguns pacotes fiquem com um número grande de classes. Caso isso venha a acontecer é
prudente agrupar por contexto/domínio para facilitar o entendimento.
