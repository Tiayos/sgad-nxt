
// export const useAuthService = () => {
//   const config = useRuntimeConfig();
//   const { data, signOut } = useAuth();
//   let isRefreshing = false;

//   const tokens = reactive({
//     accessToken: data?.value?.access_token,
//     refreshToken: data?.value?.refresh_token,
//     accessTokenExpiry: null as number | null, // Campo para la fecha de expiración del access token
//   });

//   // Método para refrescar el access token
//   const refreshAccessToken = async () => {
//     try {
//       const refreshToken = tokens.refreshToken;
//       if (!refreshToken) {
//         throw new Error("No refresh token available");
//       }

//       const tokenUrl = `${config.public.KEYCLOAK_ISSUER}/protocol/openid-connect/token`;
//       const resp = await $fetch(tokenUrl, {
//         method: "POST",
//         headers: {
//           "Content-Type": "application/x-www-form-urlencoded",
//         },
//         body: new URLSearchParams({
//           grant_type: "refresh_token",
//           client_id: config.public.KEYCLOAK_ID as string,
//           client_secret: config.public.KEYCLOAK_SECRET as string,
//           refresh_token: refreshToken,
//         }),
//       });

//       if (resp.access_token) {
//         tokens.accessToken = resp.access_token;
//         tokens.refreshToken = resp.refresh_token;
//         const tokenPayload = JSON.parse(atob(resp.access_token.split(".")[1]));
//         tokens.accessTokenExpiry = tokenPayload.exp * 1000;
//         console.log("Token refreshed successfully");
//       } else {
//         signOut();
//         throw new Error("Failed to refresh the token");
//       }
//     } catch (error) {
//       signOut();
//       console.error("Token refresh failed", error);
//       throw error;
//     }
//   };

//   // Verifica si el token ha expirado
//   const isTokenExpired = (tokenExpiry: number | null) => {
//     return tokenExpiry ? Date.now() > tokenExpiry : true;
//   };

//   // Subscribers para manejar las solicitudes mientras se refresca el token
//   let refreshSubscribers: Array<(newToken: string) => void> = [];

//   const onAccessTokenRefreshed = (newToken: string) => {
//     refreshSubscribers.forEach((callback) => callback(newToken));
//     refreshSubscribers = [];
//   };

//   const addRefreshSubscriber = (callback: (newToken: string) => void) => {
//     refreshSubscribers.push(callback);
//   };

//   // Obtiene los headers con el token de acceso válido
//   const getHeaders = async (): Promise<{ headers: Record<string, string> }> => {
//     if (!tokens.accessToken) {
//       throw new Error("No access token available");
//     }

//     // Refresca el token si está expirado o por expirar
//     if (isTokenExpired(tokens.accessTokenExpiry)) {
//       if (!isRefreshing) {
//         isRefreshing = true;
//         await refreshAccessToken();
//         isRefreshing = false;
//         onAccessTokenRefreshed(tokens.accessToken);
//       } else {
//         return new Promise((resolve) => {
//           addRefreshSubscriber((newToken: string) => {
//             resolve({
//               headers: {
//                 Authorization: `Bearer ${newToken}`,
//               },
//             });
//           });
//         });
//       }
//     }

//     return {
//       headers: {
//         Authorization: `Bearer ${tokens.accessToken}`,
//       },
//     };
//   };

//   return {
//     getHeaders,
//     refreshAccessToken,
//   };
// };


export const useAuthService = () => {
  const config = useRuntimeConfig();
  const { data, signOut } = useAuth();
  let isRefreshing = false;
  const tokenMarginTime = 60 * 1000; // Margen de 1 minuto antes de considerar que el token está por expirar

  const tokens = reactive({
    accessToken: data?.value?.access_token,
    refreshToken: data?.value?.refresh_token,
    accessTokenExpiry: null as number | null, // Expiración del token de acceso
  });

  // Método para refrescar el access token
  const refreshAccessToken = async () => {
    try {
      const refreshToken = tokens.refreshToken;
      if (!refreshToken) {
        throw new Error("No refresh token available");
      }

      const tokenUrl = `${config.public.KEYCLOAK_ISSUER}/protocol/openid-connect/token`;
      const resp = await $fetch(tokenUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({
          grant_type: "refresh_token",
          client_id: config.public.KEYCLOAK_ID as string,
          client_secret: config.public.KEYCLOAK_SECRET as string,
          refresh_token: refreshToken,
        }),
      });

      if (resp.access_token) {
        tokens.accessToken = resp.access_token;
        tokens.refreshToken = resp.refresh_token;
        const tokenPayload = JSON.parse(atob(resp.access_token.split(".")[1]));
        tokens.accessTokenExpiry = tokenPayload.exp * 1000;
        console.log("Token refreshed successfully");
      } else {
        signOut();
        throw new Error("Failed to refresh the token");
      }
    } catch (error) {
      signOut();
      console.error("Token refresh failed", error);
      throw error;
    }
  };

  // Verifica si el token ha expirado (con un margen de tiempo)
  const isTokenExpired = (tokenExpiry: number | null) => {
    return tokenExpiry ? Date.now() > (tokenExpiry - tokenMarginTime) : true;
  };

  // Subscribers para manejar las solicitudes mientras se refresca el token
  let refreshSubscribers: Array<(newToken: string) => void> = [];

  const onAccessTokenRefreshed = (newToken: string) => {
    refreshSubscribers.forEach((callback) => callback(newToken));
    refreshSubscribers = [];
  };

  const addRefreshSubscriber = (callback: (newToken: string) => void) => {
    refreshSubscribers.push(callback);
  };

  // Obtiene los headers con el token de acceso válido
  const getHeaders = async (): Promise<{ headers: Record<string, string> }> => {
    if (!tokens.accessToken) {
      throw new Error("No access token available");
    }

    // Solo refresca si el token ha expirado o está por expirar
    if (isTokenExpired(tokens.accessTokenExpiry)) {
      if (!isRefreshing) {
        isRefreshing = true;
        await refreshAccessToken();
        isRefreshing = false;
        onAccessTokenRefreshed(tokens.accessToken);
      } else {
        return new Promise((resolve) => {
          addRefreshSubscriber((newToken: string) => {
            resolve({
              headers: {
                Authorization: `Bearer ${newToken}`,
              },
            });
          });
        });
      }
    }

    return {
      headers: {
        Authorization: `Bearer ${tokens.accessToken}`,
      },
    };
  };

  return {
    getHeaders,
    refreshAccessToken,
  };
}