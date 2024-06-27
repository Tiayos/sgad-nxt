<script setup lang="ts">
import { useI18n } from 'vue-i18n'

import { MenuItem } from '@/models/menu-item.model'

const emit = defineEmits(['menuitem-click'])

const { t } = useI18n()

const onMenuItemClick = (item: MenuItem) => {
    emit('menuitem-click', item)
}

const menu: MenuItem[] = [
    {
        label: t('app.menu.title'),
        items: [
            { label: t('app.menu.label.ficha'), icon: 'pi pi-fw pi-users', to: { name: 'fichaAmpliada' } },
        ]
    },
]

</script>

<template>
    <div class="layout-menu-container">
        <LazyNavigationMenuItem :items="menu" class="layout-menu" :root="true" @menuitem-click="onMenuItemClick" />
    </div>
</template>

<style scoped></style>
