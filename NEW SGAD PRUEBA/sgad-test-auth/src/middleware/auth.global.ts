import jwtDecode from "jwt-decode";
import { useAuthService } from "~/composables/services/useAuthService";
import { useSumillaService } from "~/composables/services/useSumillaService";
import { useArchivosStore } from "~/composables/store/useArchivosStore";

export default defineNuxtRouteMiddleware(async(to, from) => {
    const config = useRuntimeConfig();
    const {data} = useAuth();
    const accessToken = data?.value?.access_token;
    const authStore = useArchivosStore();
    const {appRoles} = storeToRefs(authStore);

    if (accessToken) {
        try {
          const decodedToken: any = jwtDecode(accessToken);
            if(decodedToken.email.endsWith('@est.ups.edu.ec') && (to.path == '/sumilla' || to.path == '/bitacora') ){
                return abortNavigation();
            }

          appRoles.value = decodedToken?.resource_access?.['sgad-produccion']?.roles || [];
          if(appRoles.value.length == 1 && !decodedToken.email.endsWith('@ups.edu.ec')){ // es invitado
            if((to.path == '/sumilla' || to.path == '/bitacora' || to.path == '/') ){
              return abortNavigation();
          }
          }

          // si no tiene el rol de recepcionista no se le permite ingresar a /sumilla
          if (appRoles.value.includes("recepcionist")) {
          } else {
            if(to.path == '/sumilla')
                return abortNavigation();
          }
        } catch (error) {
          console.error("Error al decodificar el token:", error);
        }
      } else {
      }
    });

