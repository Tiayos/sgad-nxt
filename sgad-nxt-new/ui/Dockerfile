FROM node:18.15.0-alpine3.17

# Establecer el directorio de trabajo
WORKDIR /app

ADD .output /app/

# Copiar package.json y package-lock.json para instalar las dependencias
COPY package*.json ./

# Instalar dependencias
#RUN npm install

# Copiar el resto de los archivos de la aplicación
#COPY . .

# Construir la aplicación
#RUN npm run build

# Establecer variables de entorno
ENV NITRO_PORT 8080
ENV NUXT_PUBLIC_SGAD_SUMILLA=https://sgadsvc.ups.edu.ec/sumilla
ENV NUXT_PUBLIC_SGAD_BITACORA=https://sgadsvc.ups.edu.ec/bitacora
ENV NUXT_PUBLIC_SGAD_PERSONA=https://sgadsvc.ups.edu.ec/persona
ENV NUXT_PUBLIC_SGAD_TRANSFERENCIA=https://sgadsvc.ups.edu.ec/transferencia
ENV NUXT_PUBLIC_SGAD_EVENTO=https://sgadsvc.ups.edu.ec/eventoBitacora
ENV NUXT_PUBLIC_SGAD_EMAIL=https://sgadsvc.ups.edu.ec/email
# ENV PORT=443
# ENV KEYCLOAK_ISSUER=https://auth.ups.edu.ec/realms/dev
# ENV KEYCLOAK_ID=sgad-next-prod
# ENV KEYCLOAK_SECRET=BmsLZvE460UpKaQ3ZVQiIftrgxHOT5yC
# ENV AUTH_ORIGIN=https://sgadui.ups.edu.ec/
# ENV AUTH_SECRET=12345
# ENV NEXTAUTH_URL=https://sgadui.ups.edu.ec/

# Exponer el puerto 8080
EXPOSE 8080

# Comando para iniciar la aplicación
CMD ["node", "/app/server/index.mjs"]