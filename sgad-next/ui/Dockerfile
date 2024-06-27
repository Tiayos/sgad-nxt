FROM node:18.15.0-alpine3.17

ADD .output /app/

ENV NITRO_PORT 8080

ENV NUXT_PUBLIC_SGAD_SUMILLA=https://sgadsvc.ups.edu.ec/sgad/api/v1/sumilla
ENV NUXT_PUBLIC_SGAD_BITACORA=https://sgadsvc.ups.edu.ec/sgad/api/v1/bitacora
ENV NUXT_PUBLIC_SGAD_PERSONA=https://sgadsvc.ups.edu.ec/sgad/api/v1/persona
ENV NUXT_PUBLIC_SGAD_TRANSFERENCIA=https://sgadsvc.ups.edu.ec/sgad/api/v1/transferencia
ENV NUXT_PUBLIC_SGAD_EVENTO=https://sgadsvc.ups.edu.ec/sgad/api/v1/eventoBitacora
ENV NUXT_PUBLIC_SGAD_EMAIL=https://sgadsvc.ups.edu.ec/sgad/api/v1/email

#ENV PORT=443
#ENV KEYCLOAK_ISSUER=https://auth.ups.edu.ec/realms/dev
#ENV KEYCLOAK_ID=sgad-next-prod
#ENV KEYCLOAK_SECRET=BmsLZvE460UpKaQ3ZVQiIftrgxHOT5yC
#ENV AUTH_ORIGIN=https://sgadui.ups.edu.ec/
#ENV AUTH_SECRET=12345
#ENV NEXTAUTH_URL=https://sgadui.ups.edu.ec/

EXPOSE 8080

CMD ["node", "/app/server/index.mjs"]