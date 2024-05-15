<script setup lang="ts">
const currentYear = new Date().getFullYear()
</script>

<template>
    <footer>
        <div class="layout-footer">
            <span class="font-medium ml-2">{{ $t('app.footer.title', { year: currentYear }) }}</span>
        </div>
        <div class="layout-footer-img">
            <img alt="Logo" src="~/assets/images/ups-logo-gris.png" height="25" class="mr-2">
        </div>
</footer>
</template>

<style scoped>
.layout-footer-img {
    transition: margin-left 0.2s;
    display: flex;
    align-items: center;
    justify-content: center;
    padding-top: 1rem;
}
</style>
