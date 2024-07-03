FROM node:18.15.0-alpine3.17

ADD .output /app/

ENV NITRO_PORT 8080

#DESARROLLO

#ENV PORT=3000
#ENV KEYCLOAK_ISSUER=https://auth.ups.edu.ec/realms/dev
#ENV KEYCLOAK_ID=sgad-next-dev
#ENV KEYCLOAK_SECRET=evVRjt1nt1CUldvfGEDOeEvx5kMXRZtq
#ENV AUTH_ORIGIN=http://localhost:3000
#ENV AUTH_SECRET=12345
#ENV NEXTAUTH_URL=http://localhost:3000

#PRODUCCIÓN

ENV PORT=443
ENV KEYCLOAK_ISSUER=https://auth.ups.edu.ec/realms/dev
ENV KEYCLOAK_ID=sgad-prod
ENV KEYCLOAK_SECRET=yeQrR946XDMUZ5QYAo74mWkAHTFcgrW1
ENV AUTH_ORIGIN=https://sgadui.ups.edu.ec/
ENV AUTH_SECRET=12345
ENV NEXTAUTH_URL=https://sgadui.ups.edu.ec/

EXPOSE 8080

CMD ["node", "/app/server/index.mjs"]