// plugins/auth-fetch.ts
import { defineNuxtPlugin } from '#app';
import { useAuthService } from '~/composables/services/useAuthService';

export default defineNuxtPlugin((nuxtApp) => {
  const authService = useAuthService();

  // Sobrescribir $fetch para agregar el token a todas las solicitudes
  const originalFetch = nuxtApp.$fetch;

  nuxtApp.provide('fetch', async (request:any, opts = {}) => {
    // Obtiene los headers con el token de acceso
    const { headers } = await authService.getHeaders();

    // Fusiona los headers existentes con los nuevos headers
    opts.headers = {
      ...opts.headers,
      ...headers,
    };

    try {
      // Llama a la función original de $fetch con los nuevos headers
      return await originalFetch(request, opts);
    } catch (error) {
      console.error('Error en fetch con token:', error);
      throw error;
    }
  });
});