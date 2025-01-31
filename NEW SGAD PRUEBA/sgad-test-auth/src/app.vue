<script setup lang="ts">
// import { ref } from "vue";
import type {Persona} from "~/models/Sumilla.model";
import {useSessionStorage} from "~/utils/useSessionStorage";
import {usePersonaService} from "~/composables/services/usePersonaService";
// import Footer from "~/components/navigation/Footer.vue";
import { useArchivosStore } from "./composables/store/useArchivosStore";

import {
  HouseSolid,
  EyeRegular,
  InboxSolid,
  BarsSolid,
  BugSolid,
  MessageDotsRegular,
  MessageDotsSolid,
  BracketsCurlySolid,
  BoxArchiveSolid
} from "@ups-dev/freya-icons";

const { data, signOut  } = useAuth();
const skipToContentRef = ref(null);
const searchActive = ref(false);
const searchValue = ref("");
const userMenuActive = ref(false);
const mobileNavigationActive = ref(true);
const router = useRouter(); //* redirecciones
const store = useArchivosStore();
const showSumilla = ref<boolean>(false);

const validarRuta = ref<string>(router.currentRoute.value.fullPath);

//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");

//*Services
const { getUsrLogin } = usePersonaService();

onMounted(async () => {

if(store.appRoles.includes('recepcionist')){
  showSumilla.value = true;
}

  store.appRoles.forEach((e:string) => {
    if (e == 'recepcionist') {
    showSumilla.value = true;
  }
});

  if(!validarRuta.value.includes("documentosUPS")){
    userLogin.value = await getUsrLogin(data.value?.user?.email!);
  }
})   

const handleSearchResultsDismiss = () => {
  searchActive.value = false;
  searchValue.value = "";
};

const toggleMobileNavigationActive = () =>
    (mobileNavigationActive.value = !mobileNavigationActive.value);

// const cerrarSesion = async () => {
//   try {
//     sessionStorage.clear();
//     await signOut({ callbackUrl: "/" });
//   } catch (error) {}
// };

const cerrarSesion = async () => {
  try {
    sessionStorage.clear();
    await signOut({ redirect: true });
  } catch (error) {}
};

const userMenuActions = [
  {
    items: [
      {
        content: "Cerrar Sesión",
        onAction: () => {
          cerrarSesion();
        },
      },
    ],
  },
];

const logo = {
  width: 152,
  topBarSource: "/logo.png",
  url: "/",
  accessibilityLabel: "UPS Dev",
};

const getInitials = (name: string) => {
  const parts = name.split(" ");
  return [parts[0], parts[2]].map((p) => p.charAt(0)).join("");
};

const items: FNavigationItem[] = [
    {
      to: '/sumilla',
      label: 'Sumilla',
      icon: BoxArchiveSolid,
    }, {
      to: '/bitacora',
      label: 'Bitácora',
      icon: MessageDotsSolid,
    }];

    
const items2: FNavigationItem[] = [
    {
      to: '/bitacora',
      label: 'Bitácora',
      icon: MessageDotsSolid,
    }];

</script>
<template>
  <FAppProvider>
    <FFrame
        :logo="logo"
        :showMobileNavigation="mobileNavigationActive"
        @navigation-dismiss="toggleMobileNavigationActive"
        :skipToContentTarget="skipToContentRef"
    >
      <FButton @click="toggleMobileNavigationActive" 
      v-if="$route.path != '/documentosUPS'" 
      :icon="BarsSolid" 
      :style="[(mobileNavigationActive==true) ? '' : 'margin-left: -220px;']" 
      size="medium" 
      :pressed="mobileNavigationActive==true"
      >
      </FButton>
      <template #topBar>
        <FTopBar
            showNavigationToggle
            :searchResultsVisible="searchActive"
            @searchResultsDismiss="handleSearchResultsDismiss"
            @navigation-toggle="toggleMobileNavigationActive"
        >
                            <!-- Botón para alternar visibilidad del menú -->
          <template #userMenu>
            <FTopBarUserMenu
                :actions="userMenuActions"
                :name="data?.user?.name ?? ''"
                :detail="data?.user?.email ?? ''"
                :initials="($route.path !== '/documentosUPS' ? getInitials(data?.user?.name ?? '') : '') "
                v-model:open="userMenuActive"
            />
          </template>
        </FTopBar>
      </template>

      <template #navigation>
        <!-- Menú de navegación -->
        <div v-if="mobileNavigationActive" >
          <FNavigation :location="$route.path" v-if="$route.path != '/documentosUPS'" >
            <FNavigationSection  :items="showSumilla ? items : items2"/>
          </FNavigation>
        </div>
      </template>

      <FPage full-width  >
        <LazyNuxtPage />
      </FPage>
      <Footer></Footer>
    </FFrame>
  </FAppProvider>
</template>

<style>

/* Asegurarse de que solo el nombre del archivo se muestra y se ajusta a una sola columna */
.p-fileupload .p-fileupload-file {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border: 1px solid #dee2e6;
  border-radius: 6px;
  margin-bottom: 0.5rem;
}

.p-fileupload .p-fileupload-file .p-fileupload-file-name {
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.p-fileupload .p-fileupload-file .p-fileupload-file-size,
.p-fileupload .p-fileupload-file .p-progressbar,
.p-fileupload .p-fileupload-file .p-fileupload-file-status,
.p-fileupload .p-fileupload-file .p-fileupload-file-icon {
  display: none !important;
}

.p-fileupload .p-fileupload-file img {
  display: none !important;
}

/* Especifica la ocultación de elementos adicionales si siguen apareciendo */
.p-fileupload .p-fileupload-file .p-fileupload-file-status,
.p-fileupload .p-fileupload-file .p-fileupload-file-icon {
  display: none !important;
}

/* Ajusta el estilo del botón para que se vea como deseas */
.p-fileupload .p-fileupload-buttonbar {
  background: #f8f9fa;
  padding: 1.25rem;
  border: 1px solid #dee2e6;
  color: #343a40;
  border-bottom: 0;
  border-top-right-radius: 6px;
  border-top-left-radius: 6px;
  gap: 0.5rem;
}

.p-fileupload .p-fileupload-buttonbar .p-button.p-fileupload-choose.p-focus {
  outline: 0;
  box-shadow: 0 0 0 0.2rem #c7d2fe;
}

.p-fileupload .p-fileupload-content {
  background: #ffffff;
  padding: 2rem 1rem;
  border: 1px solid #dee2e6;
  color: #495057;
  border-bottom-right-radius: 6px;
  border-bottom-left-radius: 6px;
}

.p-fileupload .p-fileupload-row > div {
  padding: 1rem;
}
.p-fileupload .p-fileupload-upload,
.p-fileupload .p-fileupload-cancel {
  display: none !important;
}

/* Asegurarse de que solo el botón de selección de archivos se muestre */
.p-fileupload .p-fileupload-choose {
  display: block !important; /* Asegura que el botón de elegir archivos esté visible */
}

.menu-toggle {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.menu-toggle:hover {
  background-color: #0056b3;
}
</style>
