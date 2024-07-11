<!-- NoAuthLayout.vue -->
<template>
  <div>
    <!-- Aquí defines la estructura específica del layout para páginas sin autenticación -->
    <slot />
  </div>
</template>

<script setup lang="ts">
import { definePageMeta } from "#imports";

definePageMeta({
  auth: false,
  layout: 'new-user-layout' // Nombre del layout personalizado para páginas sin autenticación
});
</script>

<style scoped>
/* Estilos específicos para este layout si es necesario */
</style>