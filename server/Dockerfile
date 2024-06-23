FROM node:22-alpine
WORKDIR /usr/app
COPY ./server/package*.json /usr/app
RUN npm install
COPY . .
EXPOSE 3000
CMD ["node", "app.js"]