# Gerenciador de Itens Dockerizado

## Objetivo do Projeto

O objetivo desta aplicação é permitir ao usuário criar itens com suas respectivas descrições, adicioná-los a um sistema e, em seguida, listar todos os itens cadastrados na mesma página.

## Arquitetura da Solução

Este projeto consiste em uma aplicação full stack containerizada com Docker e orquestrada com Docker Compose, seguindo a arquitetura de três componentes principais:

* **Frontend:** Aplicação web desenvolvida com a biblioteca React, responsável pela interface do usuário.
* **Backend:** Aplicativo web construído com Java e Spring Boot, fornecendo uma API REST para o gerenciamento dos itens.
* **Banco de Dados:** MySQL, utilizado para persistir os dados dos itens.

Os três componentes rodam em containers Docker separados e se comunicam através de uma rede Docker personalizada, gerenciada pelo Docker Compose.

## Pré-requisitos

Para executar este projeto localmente, você precisará ter instalados:

* Docker: [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)
* Docker Compose: [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)

## Como Executar

Siga os passos abaixo para configurar e rodar a aplicação:

1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/tomazpanta/devops_p5](https://github.com/tomazpanta/devops_p5)
    cd devops_p5
    ```

2.  **Configure as Variáveis de Ambiente:**
    Este projeto utiliza um arquivo `.env` para gerenciar as credenciais do banco de dados e outras configurações sensíveis.
    * Crie um arquivo chamado `.env` na raiz do projeto (`devops_p5/.env`).
    * Adicione as seguintes variáveis a ele, substituindo pelos seus valores desejados:
        ```env
        # devops_p5/.env
        ENV_MYSQL_ROOT_PASSWORD=rootsecretpassword_do_env
        ENV_MYSQL_DATABASE=meubanco_db_do_env
        ENV_MYSQL_USER=userdevops_do_env
        ENV_MYSQL_PASSWORD=passdevops_do_env
        ```
    * **Nota:** O arquivo `.env` não deve ser versionado no Git. Recomenda-se criar um arquivo `env.example` com as chaves das variáveis como template.

3.  **Suba os Containers com Docker Compose:**
    No terminal, na raiz do projeto (`devops_p5/`), execute o comando:
    ```bash
    docker-compose up --build
    ```
    * Para rodar os containers em background (modo detached), utilize:
        ```bash
        docker-compose up --build -d
        ```

4.  **Acesse a Aplicação:**
    Após os containers iniciarem com sucesso:
    * **Frontend:** Abra seu navegador e acesse `http://localhost:3000`
    * **Backend API (Exemplo):** Os endpoints da API estarão disponíveis em `http://localhost:8080/api/items` (ex: para listar todos os itens).

## Como Parar a Aplicação

Para parar todos os containers da aplicação, execute no terminal (na raiz do projeto):
```bash
docker-compose down
```

## Estrutura de Pastas

```
/devops_p5
├── .env                       # Arquivo de variáveis de ambiente para o Docker Compose (NÃO versionar)
├── .gitignore                 # Arquivo .gitignore global para o projeto
├── .idea/                     # Pasta de configuração do IntelliJ IDEA (geralmente no .gitignore)
├── backend/
│   └── backend/               # Módulo do Backend (Spring Boot com Gradle)
│       ├── .gradle/
│       ├── gradle/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/
│       │   │   │   └── br/com/tomazpanta/backend/  # Pacote base da aplicação backend
│       │   │   │       ├── BackendApplication.java # Classe principal Spring Boot
│       │   │   │       ├── controller/             # Controladores REST (ex: ItemController.java)
│       │   │   │       ├── model/                  # Entidades JPA (ex: Item.java)
│       │   │   │       ├── repository/             # Repositórios Spring Data JPA (ex: ItemRepository.java)
│       │   │   │       └── service/                # Camada de serviço (ex: ItemService.java)
│       │   │   └── resources/
│       │   │       └── application.properties    # Configurações do backend
│       │   └── test/                             # Testes do backend
│       │       └── java/
│       │           └── br/com/tomazpanta/backend/
│       │               └── BackendApplicationTests.java
│       ├── .dockerignore          # Ignora arquivos no build da imagem Docker do backend
│       ├── .gitattributes
│       ├── .gitignore             # Ignora arquivos do Git específicos do backend
│       ├── build.gradle           # Arquivo de build do Gradle
│       ├── Dockerfile             # Dockerfile para construir a imagem Docker do backend
│       ├── gradlew                # Script do Gradle wrapper (Linux/Mac)
│       ├── gradlew.bat            # Script do Gradle wrapper (Windows)
│       ├── HELP.md
│       └── settings.gradle
├── frontend/
│   └── frontend/              # Módulo do Frontend (React)
│       ├── node_modules/      # Dependências do Node.js (geralmente no .gitignore)
│       ├── public/            # Arquivos estáticos públicos do React
│       ├── src/               # Código fonte da aplicação React
│       │   ├── components/    # Componentes React
│       │   │   ├── ItemForm.js
│       │   │   └── ItemList.js
│       │   ├── App.css
│       │   ├── App.js         # Componente principal React
│       │   ├── App.test.js
│       │   ├── index.css
│       │   ├── index.js       # Ponto de entrada do React
│       │   └── ... (outros arquivos do Create React App)
│       ├── .dockerignore          # Ignora arquivos no build da imagem Docker do frontend
│       ├── .gitignore             # Ignora arquivos do Git específicos do frontend
│       ├── Dockerfile             # Dockerfile para construir a imagem Docker do frontend
│       ├── frontend.iml           # Arquivo de módulo do IntelliJ (opcional no Git)
│       ├── package.json
│       ├── package-lock.json
│       └── README.md              # README específico do módulo frontend
├── docker-compose.yml         # Arquivo de orquestração dos containers Docker
├── README.md                  # Este arquivo de documentação principal
└── DESENVOLVIMENTO.md         # Documento detalhando o processo de desenvolvimento
