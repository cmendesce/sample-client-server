FROM node:22-alpine
ARG APP='./client'
RUN mkdir -p /home/node/app/node_modules && chown -R node:node /home/node/app
WORKDIR /home/node/app
COPY ${APP}/package*.json ./
USER node
RUN npm install
COPY --chown=node:node ${APP} .
EXPOSE 3000
CMD [ "node", "app.js" ]