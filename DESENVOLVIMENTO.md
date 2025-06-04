# Documento de Desenvolvimento do Projeto: Gerenciador de Itens Dockerizado

Este documento detalha o processo de planejamento, desenvolvimento e os desafios encontrados na criação do projeto "Gerenciador de Itens Dockerizado".

## 1. Planejamento do Projeto

* **Objetivo Inicial:** Desenvolver uma aplicação full stack dockerizada com Frontend, Backend e Banco de Dados, comunicando-se via Docker Compose.
* **Abordagem devido ao Prazo:** Dado o prazo curto e a natureza individual do projeto, optei por um ciclo de desenvolvimento intensivo e focado. O objetivo foi não apenas cumprir os requisitos da arquitetura dockerizada, mas também solidificar conhecimentos em desenvolvimento FullStack com um sistema simples, porém 100% funcional, aplicando os aprendizados de forma prática.

## 2. Ciclo de Desenvolvimento e Metodologia

* **Ciclo/Fases:** O projeto foi desenvolvido em um ciclo intensivo ao longo dos dias disponíveis, com foco em metas específicas para cada etapa:
    * **Fase 1 (Configuração e Backend):** Estruturação inicial do projeto (Git, organização de pastas), desenvolvimento da API base do backend utilizando Java com Spring Boot (incluindo a entidade `Item`, repositório e controller iniciais), criação do `Dockerfile` do backend e configuração do serviço MySQL no `docker-compose.yml`.
    * **Fase 2 (Frontend e Integração):** Desenvolvimento da interface do usuário com React (criação dos componentes de formulário para adição de itens e de lista para exibição), implementação da lógica de comunicação com a API do backend, criação do `Dockerfile` para o frontend e a finalização e depuração do `docker-compose.yml` para orquestrar todos os serviços. Esta fase incluiu uma depuração intensiva das variáveis de ambiente, conexões de rede entre containers e a persistência de dados do banco.
    * **Fase 3 (Testes, Documentação e Finalização):** Realização de testes funcionais da aplicação completa, incluindo a interação entre frontend e backend via API. Elaboração da documentação principal (`README.md`) e deste documento de desenvolvimento (`DESENVOLVIMENTO.md`). Revisão final do projeto e organização do repositório para entrega.
* **Metodologia Ágil Utilizada:** Mesmo sendo um projeto individual, foi utilizada uma abordagem inspirada no Kanban pessoal, com um checklist de tarefas específicas organizadas em "A Fazer", "Em Andamento" e "Feito", para manter o foco e o progresso visível ao longo do ciclo de desenvolvimento.

## 3. Ferramentas Utilizadas

* **IDE:** IntelliJ IDEA.
* **Controle de Versão:** Git e GitHub (Repositório: [https://github.com/tomazpanta/devops_p5](https://github.com/tomazpanta/devops_p5)).
* **Containerização e Orquestração:** Docker e Docker Compose.
* **Tecnologias da Aplicação:**
    * Backend: Java com Spring Boot e Gradle.
    * Frontend: React (JavaScript).
    * Banco de Dados: MySQL.
* **Teste de API:** Insomnia, para testar as requisições e respostas da API backend.
* **Sistema Operacional:** macOS.

## 4. Desafios Enfrentados e Soluções Adotadas

* **Desafio 1: Configuração inicial do `docker-compose.yml` e comunicação entre containers.**
    * **Problema:** Dificuldade inicial em fazer os containers (backend, frontend, db) se comunicarem corretamente dentro da rede Docker, resultando em erros como "network undefined" ou falhas de conexão.
    * **Solução:** Definição cuidadosa de uma rede personalizada (`devops_network`) no `docker-compose.yml` e atribuição dessa rede a todos os serviços. Utilização correta do `depends_on` para gerenciar a ordem de inicialização dos serviços e implementação de `healthcheck` para o serviço do banco de dados, garantindo que o backend só tentasse iniciar após o MySQL estar efetivamente saudável e pronto para aceitar conexões. A referência aos serviços pelos seus nomes definidos no Docker Compose (ex: `db` como hostname para o MySQL) foi crucial para a comunicação interna na rede Docker.

* **Desafio 2: Problemas de conexão do Backend com o MySQL (Dialeto, Usuário/Senha e Variáveis de Ambiente).**
    * **Problema:** Diversos erros durante a inicialização do Spring Boot ao tentar conectar com o MySQL, incluindo `ClassNotFoundException` para o dialeto do MySQL, erros de `Access denied for user 'root'`, e, mais sutilmente, `Access denied` para um nome de usuário que incluía comentários ou caracteres inesperados.
    * **Solução:** Foi um processo iterativo de depuração:
        1.  Correção da propriedade `spring.jpa.properties.hibernate.dialect` no `application.properties`, removendo comentários da mesma linha do valor.
        2.  Limpeza e verificação minuciosa do arquivo `.env` para remover quaisquer comentários ou espaços extras nos valores das variáveis, garantindo que os valores de `ENV_MYSQL_USER`, `ENV_MYSQL_PASSWORD`, etc., estivessem puros.
        3.  Ajuste do `application.properties` para remover os valores default de `spring.datasource.username` e `spring.datasource.password`, forçando a aplicação a depender exclusivamente das variáveis de ambiente injetadas pelo `docker-compose.yml`.
        4.  Limpeza completa dos volumes do Docker (`docker-compose down -v`) e reconstrução das imagens (`docker-compose up --build`) a cada tentativa para garantir que o MySQL inicializasse com as credenciais corretas e que o backend as recebesse de forma limpa e sem interferência de estados anteriores ou caches.

* **Desafio 3: Estrutura de Pastas do Projeto Java Backend não reconhecida pelo Spring Boot.**
    * **Problema:** O Spring Boot não estava detectando a interface `ItemRepository`, resultando na mensagem `Found 0 JPA repository interfaces` nos logs, o que impedia a interação com o banco de dados.
    * **Solução:** Reorganização das pastas `controller`, `model`, `repository`, e `service` para que ficassem localizadas dentro da estrutura de pacotes correta do Java (`src/main/java/br/com/tomazpanta/backend/`), permitindo que o mecanismo de component scan do Spring Boot (habilitado por `@SpringBootApplication`) pudesse encontrá-las e registrá-las adequadamente.

* **Desafio 4: Conflito ao tentar enviar alterações para o GitHub (Push Rejected).**
    * **Problema:** Uma tentativa de `push` de commits locais para o `main` branch foi rejeitada pelo GitHub porque o `README.md` havia sido atualizado diretamente na interface web do GitHub, tornando o repositório local desatualizado em relação ao remoto.
    * **Solução:** Utilização da funcionalidade "Merge" oferecida pelo IntelliJ IDEA. Este processo baixou as alterações remotas (`git pull`), integrou-as com as modificações locais e, após o merge bem-sucedido, o `push` pôde ser realizado novamente, sincronizando os dois repositórios.

## 5. Extras Desejáveis Implementados

* **Uso de variáveis de ambiente com `.env`**: Sim, o arquivo `.env` foi utilizado para gerenciar as credenciais do banco de dados (usuário, senha, nome do banco, senha do root), e o `docker-compose.yml` foi configurado para ler e utilizar essas variáveis, promovendo uma configuração mais segura e flexível.
* **Scripts de build e inicialização automatizados**: Sim, os `Dockerfiles` para o backend e frontend contêm todos os passos necessários para construir as respectivas imagens e os comandos (`CMD`/`ENTRYPOINT`) para a inicialização dos containers. Adicionalmente, os arquivos `build.gradle` (para o backend) e `package.json` (para o frontend) contêm scripts que automatizam tarefas de build e desenvolvimento.