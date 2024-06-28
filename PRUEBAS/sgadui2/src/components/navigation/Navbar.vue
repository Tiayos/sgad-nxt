<script setup lang="ts">
const emit = defineEmits(['topbar-menu-toggle', 'menu-toggle'])

const onMenuToggle = (event: any) => {
    emit('menu-toggle', event)
}
</script>

<template>
    <nav class="layout-topbar navbar">
        <button class="p-link layout-menu-button layout-topbar-button" @click="onMenuToggle">
            <i class="pi pi-bars"></i>
        </button>
        <NuxtLink to="/" class="layout-topbar-logo">
            <img alt="Logo" src="~/assets/images/logo_ups.png">
            <span class="title">{{ $t('app.navbar.title') }}</span>
        </NuxtLink>

        <button v-styleclass="{
            selector: '@next',
            enterClass: 'hidden',
            enterActiveClass: 'scalein',
            leaveToClass: 'hidden',
            leaveActiveClass: 'fadeout',
            hideOnOutsideClick: true
        }" class="p-link layout-topbar-menu-button layout-topbar-button">
            <i class="pi pi-ellipsis-v"></i>
        </button>
        <ul class="layout-topbar-menu hidden lg:flex origin-top">
            <li>
                <span class="username">Fulano Detal (example@ups.edu.ec)</span>
                <button class="p-link layout-topbar-button">
                    <i class="pi pi-sign-out"></i>
                </button>
            </li>
        </ul>
    </nav>
</template>

<style scoped>
img {
    margin-left: 0.5rem;
}

.navbar {
    background-color: #205081;
}

.title {
    font-size: 1.1rem;
    color: white;
    /* font-weight: 600; */
}

.username {
    color: white;
    font-size: 1rem;
    margin-right: 0.5rem;
    /* text-transform: uppercase; */
}

.pi-bars {
    color: #a0a0a0;
}

.pi-sign-out {
    color: #a0a0a0;
}
</style>