import jwtDecode from "jwt-decode";
import { useAuthService } from "~/composables/services/useAuthService";
import { useSumillaService } from "~/composables/services/useSumillaService";
import { useArchivosStore } from "~/composables/store/useArchivosStore";

export default defineNuxtRouteMiddleware(async(to, from) => {
    const config = useRuntimeConfig();
    const {data} = useAuth();

    const accessToken = data?.value?.access_token;

    if (accessToken) {
        try {
          const decodedToken: any = jwtDecode(accessToken);

            if(decodedToken.email.endsWith('@est.ups.edu.ec') && (to.path == '/sumilla' || to.path == '/bitacora') ){
                return abortNavigation();
            }

          const appRoles = decodedToken?.resource_access?.['sgad-next-dev']?.roles || [];
          if (appRoles.includes("recepcionist")) {
            console.log("El usuario tiene el rol recepcionist");
          } else {
            console.log("El usuario no tiene el rol recepcionist");
            if(to.path == '/sumilla')
                return abortNavigation();
          }
        } catch (error) {
          console.error("Error al decodificar el token:", error);
        }
      } else {
        console.log("No hay access token disponible");
      }
    });

