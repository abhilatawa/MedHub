# Use an official Node.js runtime as a parent image
FROM node:latest

# Set the working directory to /app
WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code to the container
COPY . .

EXPOSE 5173

# Build the TypeScript application
RUN npm run build

# Specify the command to run on container startup
CMD ["npm", "start"]
#CMD [ "npm", "run", "dev" ]

# Dockerfile -> Dockerimage -> Docker Container