export function useSessionStorage<T>(key: string): useSessionStorageContract<T>;
export function useSessionStorage<T>(key: string, initialValue: T): useSessionStorageContract<T>;

export function useSessionStorage<T>(key: string, initialValue?: T) {
    const data = ref<T | undefined>(initialValue);

    const clearStorage = () => {
        console.log('entra al remove');
        sessionStorage.removeItem(key);
    }

    // Obtener el valor almacenado en sessionStorage si existe
    onBeforeMount(() => {
        if (process.client) {
            const storedValue = sessionStorage.getItem(key);
            if (storedValue !== null)
                data.value = JSON.parse(storedValue);
            else if (initialValue)
                sessionStorage.setItem(key, JSON.stringify(initialValue));
        }
    });

    // Guardar el valor en sessionStorage cuando cambie
    watch(data, (newValue) => {
        if (newValue)
            sessionStorage.setItem(key, JSON.stringify(newValue));
    });

    return {
        data,
        clearStorage
    };
}

export interface useSessionStorageContract<T> {
    data: Ref<T>
    clearStorage: () => void
}