<script setup>
const { status,
  data,
  lastRefreshedAt,
  getCsrfToken,
  getProviders,
  getSession,
  signIn,
  signOut, } = useAuth()
  const csrftoken=await getCsrfToken()
 const sesion= await getSession()
const logout=async ()=>{
  signOut()
}
</script>
<template>
  <div>
    {{ data }}<br>
    {{ status }}<br>  
    {{ sesion }}<br>
    {{ csrftoken }}<br>
    {{ lastRefreshedAt }}<br>
    <button @click="logout">Cerrar sesion</button>
    <NuxtWelcome />
  </div>
</template>
