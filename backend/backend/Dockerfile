# Estágio 1: Build da aplicação com Gradle
FROM eclipse-temurin:17-jdk-jammy as builder
# Certifique-se que esta versão do JDK é compatível com seu projeto.
# Alternativas: openjdk:17-jdk-slim

WORKDIR /app

# Copie os arquivos do Gradle Wrapper primeiro para aproveitar o cache do Docker
COPY gradlew .
COPY gradle ./gradle

# Copie os arquivos de definição do projeto
COPY build.gradle .


# Copie o código fonte da aplicação
# Certifique-se de ter um arquivo .dockerignore na pasta devops_p5/backend/backend/
# para evitar copiar arquivos desnecessários como .idea, build, .gradle
COPY src ./src

# Dê permissão de execução para o gradlew e construa a aplicação
# O --no-daemon é recomendado para ambientes CI/Docker.
# O -x test pula os testes para acelerar o build (remova se quiser rodar testes durante o build do Docker).
RUN chmod +x ./gradlew && ./gradlew build --no-daemon -x test

# Estágio 2: Criação da imagem final com o JRE (menor)
FROM eclipse-temurin:17-jre-jammy
# Use a JRE correspondente à JDK do estágio de build.
# Alternativas: openjdk:17-jre-slim

WORKDIR /app

# Defina um argumento para o nome do JAR (opcional, mas útil)
ARG JAR_FILE=build/libs/*.jar

# Copie o .jar construído do estágio 'builder'
# O Spring Boot geralmente cria um JAR executável. Verifique o nome/caminho exato em sua pasta build/libs/
COPY --from=builder /app/${JAR_FILE} app.jar

# Exponha a porta em que a aplicação Spring Boot vai rodar (padrão 8080)
EXPOSE 8080

# Comando para iniciar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]