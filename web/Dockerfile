FROM node:23 AS step01
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine AS step02
COPY --from=step01 /app/dist /usr/share/nginx/html
EXPOSE 80