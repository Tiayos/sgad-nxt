# Usar la última imagen de Node.js 18.x en Alpine
FROM node:18-alpine

# Establecer el directorio de trabajo
WORKDIR /app

# Añadir el directorio de salida desde el host al contenedor
ADD .output /app/

# Establecer variables de entorno
ENV NITRO_PORT=8080

# Desarrollo

ENV PORT=443
ENV KEYCLOAK_ISSUER=https://auth.ups.edu.ec/realms/dev
ENV KEYCLOAK_ID=sgadui-dev
ENV KEYCLOAK_SECRET=yeQrR946XDMUZ5QYAo74mWkAHTFcgrW1
ENV AUTH_ORIGIN=https://sgadui.ups.edu.ec/
ENV AUTH_SECRET=12345
ENV NEXTAUTH_URL=https://sgadui.ups.edu.ec/

#Produccion
#ENV PORT=443
#ENV KEYCLOAK_ISSUER=https://auth.ups.edu.ec/realms/dev
#ENV KEYCLOAK_ID=sgad-produccion
#ENV KEYCLOAK_SECRET=IoYr2A0YPLSQ0U11GAvMJ2MAxCSsK7Yx
#ENV AUTH_ORIGIN=https://sgad.ups.edu.ec
#ENV AUTH_SECRET=12345
#ENV NEXTAUTH_URL=https://sgad.ups.edu.ec

# Exponer el puerto necesario
EXPOSE 8080

# Iniciar la aplicación
CMD ["node", "/app/server/index.mjs"]