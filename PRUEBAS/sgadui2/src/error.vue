<script setup>
const error = useError();

const handleError = () => clearError({ redirect: "/" });
</script>

<template>
  <Head>
    <Title>{{ error?.statusCode }}</Title>
  </Head>
  <!-- <NuxtLayout name="error">
    <h2>{{ error?.statusCode }}</h2>
    <br />
    <h3>{{ error?.statusMessage }}</h3>
    <br />
    <FButton @click="handleError">{{ $t("app.actions.go_home") }}</FButton>
  </NuxtLayout> -->
</template>

<style scoped>
h2 {
  font-size: 50px;
  display: inline-block;
  padding-right: 12px;
  animation: type 0.5s alternate infinite;
}

@keyframes type {
  from {
    box-shadow: inset -3px 0px 0px #888;
  }

  to {
    box-shadow: inset -3px 0px 0px transparent;
  }
}
</style>
