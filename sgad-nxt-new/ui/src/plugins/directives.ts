export default defineNuxtPlugin(({ vueApp: app }) => {
    app.directive('upper', {
        updated(el: HTMLInputElement) {
            el.value = el.value.toUpperCase()
        },
    })
})