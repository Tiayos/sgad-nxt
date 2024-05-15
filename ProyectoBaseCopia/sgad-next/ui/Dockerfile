FROM node:18.15.0-alpine3.17

ADD .output /app/

ENV NITRO_PORT 8080

ENV NUXT_PUBLIC_SCJ_CASOS_FORMULARIO=https://mobsvc.ups.edu.ec/scj/api/v1/casosFormularios
ENV NUXT_PUBLIC_SCJ_DERIVACIONES=https://mobsvc.ups.edu.ec/scj/api/v1/derivaciones
ENV NUXT_PUBLIC_SCJ_CAMPOS_FORMULARIO=https://mobsvc.ups.edu.ec/scj/api/v1/formulario
ENV NUXT_PUBLIC_SCJ_PATROCINIOS=https://mobsvc.ups.edu.ec/scj/api/v1/patrocinios
ENV NUXT_PUBLIC_SCJ_PERSONA=https://mobsvc.ups.edu.ec/scj/api/v1/persona
ENV NUXT_PUBLIC_SCJ_AUTH=https://mobsvc.ups.edu.ec/scj/auth/getToken
ENV NUXT_PUBLIC_SCJ_ADMIN_USERS=https://mobsvc.ups.edu.ec/scj/usersManagment
ENV NUXT_PUBLIC_SCJ_REPORT=https://mobsvc.ups.edu.ec/scj/api/v1/report
ENV NUXT_PUBLIC_SCJ_USUARIOS=https://mobsvc.ups.edu.ec/scj/api/v1/usuariosAsignados

ENV PORT=443
ENV KEYCLOAK_ISSUER=https://auth.ups.edu.ec/realms/dev
ENV KEYCLOAK_ID=scj
ENV KEYCLOAK_SECRET=Cb168HAsVkKMkBfSEe9YvO4hjT20gkku
ENV AUTH_ORIGIN=https://scjfront.ups.edu.ec/
ENV AUTH_SECRET=12345
ENV NEXTAUTH_URL=https://scjfront.ups.edu.ec/

EXPOSE 8080

CMD ["node", "/app/server/index.mjs"]