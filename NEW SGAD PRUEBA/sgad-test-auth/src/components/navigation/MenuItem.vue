<script setup lang="ts">
import { MenuItem } from '@/models/menu-item.model'

const activeIndex = ref<number | null>()

const emit = defineEmits(['menuitem-click'])

defineProps({
    items: {
        type: Array<MenuItem>,
        default: () => ([])
    },
    root: {
        type: Boolean,
        default: false
    }
})

const onMenuItemClick = (event: Event, item: MenuItem, index: number) => {
    if (item.disabled) {
        event.preventDefault()
        return
    }

    if (!item.to && !item.url)
        event.preventDefault()

    // execute command
    if (item.command)
        item.command({ originalEvent: event, item })


    activeIndex.value = index === activeIndex.value ? null : index

    emit('menuitem-click', item)
}

const visible = (item: MenuItem) => typeof item.visible === 'function' ? item.visible() : item.visible !== false

</script>

<template>
    <ul v-if="items">
        <template v-for="(item, i) of items">
            <li v-if="visible(item) && !item.separator" :key="item.label || i"
                :class="[{ 'layout-menuitem-category': root, 'active-menuitem': activeIndex === i && !item.to && !item.disabled }]"
                role="none">
                <template v-if="root">
                    <div class="layout-menuitem-root-text">
                        {{ item.label }}
                    </div>
                    <NavigationMenuItem :items="visible(item) && item.items"
                        @menuitem-click="$emit('menuitem-click', $event)" />
                </template>
                <template v-else>
                    <NuxtLink v-if="item.to" v-ripple :to="item.to"
                        :class="[item.className, 'p-ripple', { 'p-disabled': item.disabled }]" :style="item.style"
                        :target="item.target" exact role="menuitem" @click="onMenuItemClick($event, item, i)">
                        <i :class="item.icon"></i>
                        <span>{{ item.label }}</span>
                        <i v-if="item.items" class="pi pi-fw pi-angle-down menuitem-toggle-icon" />
                        <Badge v-if="item.badge" :value="item.badge" />
                    </NuxtLink>
                    <a v-if="!item.to" v-ripple :href="item.url || '#'" :style="item.style"
                        :class="[item.className, 'p-ripple', { 'p-disabled': item.disabled }]" :target="item.target"
                        role="menuitem" @click="onMenuItemClick($event, item, i)">
                        <i :class="item.icon"></i>
                        <span>{{ item.label }}</span>
                        <i v-if="item.items" class="pi pi-fw pi-angle-down menuitem-toggle-icon" />
                        <Badge v-if="item.badge" :value="item.badge" />
                    </a>
                    <transition name="layout-submenu-wrapper">
                        <NavigationMenuItem v-show="activeIndex === i" :items="visible(item) && item.items"
                            @menuitem-click="$emit('menuitem-click', $event)" />
                    </transition>
                </template>
            </li>
            <li v-if="visible(item) && item.separator" :key="`separator${i}`" class="p-menu-separator" :style="item.style"
                role="separator"></li>
        </template>
</ul>
</template>

<style scoped></style>
