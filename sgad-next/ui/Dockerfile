FROM node:18.15.0-alpine3.17

ADD .output /app/

ENV NITRO_PORT 8080

ENV NUXT_PUBLIC_SGAD_SUMILLA=https://sgad-svc.ups.edu.ec/sumilla
ENV NUXT_PUBLIC_SGAD_BITACORA=https://sgad-svc.ups.edu.ec/bitacora
ENV NUXT_PUBLIC_SGAD_PERSONA=https://sgad-svc.ups.edu.ec/persona
ENV NUXT_PUBLIC_SGAD_TRANSFERENCIA=https://sgad-svc.ups.edu.ec/transferencia
ENV NUXT_PUBLIC_SGAD_EVENTO=https://sgad-svc.ups.edu.ec/eventoBitacora
ENV NUXT_PUBLIC_SGAD_EMAIL=https://sgad-svc.ups.edu.ec/email

ENV PORT=443
ENV KEYCLOAK_ISSUER=https://auth.ups.edu.ec/realms/dev
ENV KEYCLOAK_ID=sgad-next-prod
ENV KEYCLOAK_SECRET=BmsLZvE460UpKaQ3ZVQiIftrgxHOT5yC
ENV AUTH_ORIGIN=https://sgad-ui.ups.edu.ec/
ENV AUTH_SECRET=12345
ENV NEXTAUTH_URL=https://sgad-ui.ups.edu.ec/

EXPOSE 8080

CMD ["node", "/app/server/index.mjs"]