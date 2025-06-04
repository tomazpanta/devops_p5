# Estágio 1: Build da aplicação React
FROM node:18-alpine as builder
# Verifique a versão do Node.js que seu projeto React requer/usa.

WORKDIR /app

# Copie package.json e package-lock.json (ou yarn.lock)
# Copiar esses arquivos primeiro aproveita o cache do Docker se eles não mudarem.
COPY package.json ./
COPY package-lock.json ./
# Se usar yarn, copie yarn.lock e use comandos yarn

# Instale as dependências do projeto
RUN npm install
# Se usar yarn: RUN yarn install --frozen-lockfile

# Copie o restante do código da aplicação para o diretório de trabalho
# Certifique-se de ter um arquivo .dockerignore na pasta devops_p5/frontend/frontend/
COPY . .

# Execute o script de build para gerar os arquivos de produção (geralmente na pasta /app/build)
RUN npm run build

# Estágio 2: Servir a aplicação com Nginx
FROM nginx:stable-alpine

# Copie os arquivos estáticos do build do estágio 'builder'
# O create-react-app geralmente coloca os arquivos de build na pasta 'build'.
# Verifique se o seu projeto React gera os arquivos na pasta 'build' ou 'dist'.
COPY --from=builder /app/build /usr/share/nginx/html

# (Opcional) Se você tiver uma configuração personalizada para o Nginx (nginx.conf),
# copie-a para o diretório de configuração do Nginx.
# Exemplo: COPY nginx.conf /etc/nginx/conf.d/default.conf
# Se não, o Nginx usará sua configuração padrão, que geralmente funciona bem para React.

# Exponha a porta 80 (padrão do Nginx)
EXPOSE 80

# Comando para iniciar o Nginx em primeiro plano quando o container iniciar
CMD ["nginx", "-g", "daemon off;"]