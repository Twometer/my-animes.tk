FROM node:14 AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build

FROM nginx:latest
WORKDIR /app
COPY --from=build /app/dist /usr/share/nginx/html