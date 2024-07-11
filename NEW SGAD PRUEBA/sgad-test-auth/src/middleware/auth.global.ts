import {redirect} from "next/navigation";

export default defineNuxtRouteMiddleware(async(to, from) => {
    const { data } = useAuth();

    // if (to.path === '/documentosUPS') {
    //     // Verifica si el usuario está autenticado, si no lo está, permite el acceso
    //         return;
    //
    // }

})

