FROM node:22-alpine
WORKDIR /usr/src/app
COPY package*.json /usr/src/app
RUN npm install
COPY . .
EXPOSE 3000
CMD ["node", "app.js"]