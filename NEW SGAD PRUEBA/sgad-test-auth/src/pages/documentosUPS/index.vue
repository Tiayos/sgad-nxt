<template>
<SendDocuments></SendDocuments>
</template>
<script setup lang="ts">

import {definePageMeta} from "#imports";

// ? El definePageMeta es para que la pagina no tenga autorización 
definePageMeta({
  auth: false,
})

</script>
<style lang="css"></style>
