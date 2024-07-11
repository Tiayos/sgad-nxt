<script setup lang="ts">
import { ref } from "vue";
import type {Persona} from "~/models/Sumilla.model";
import {useSessionStorage} from "~/utils/useSessionStorage";
import {usePersonaService} from "~/composables/services/usePersonaService";
import Footer from "~/components/navigation/Footer.vue";
const { data, signOut } = useAuth();
const skipToContentRef = ref(null);
const searchActive = ref(false);
const searchValue = ref("");
const userMenuActive = ref(false);
const mobileNavigationActive = ref(false);

//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");

//*Services
const { getUsrLogin } = usePersonaService();

onMounted(async () => {
  userLogin.value = await getUsrLogin(data.value?.user?.email!);
});

const handleSearchResultsDismiss = () => {
  searchActive.value = false;
  searchValue.value = "";
};

const toggleMobileNavigationActive = () =>
    (mobileNavigationActive.value = !mobileNavigationActive.value);

const cerrarSesion = async () => {
  try {
    sessionStorage.clear();
    await signOut();
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
</script>
<template>
  <FAppProvider>
    <FFrame
        :logo="logo"
        :showMobileNavigation="mobileNavigationActive"
        @navigation-dismiss="toggleMobileNavigationActive"
        :skipToContentTarget="skipToContentRef"
    >
      <template #topBar>
        <FTopBar
            showNavigationToggle
            :searchResultsVisible="searchActive"
            @searchResultsDismiss="handleSearchResultsDismiss"
            @navigation-toggle="toggleMobileNavigationActive"

        >
          <template #userMenu>
            <FTopBarUserMenu
                :actions="userMenuActions"
                :name="data?.user?.name ?? ''"
                :detail="data?.user?.email ?? ''"
                :initials="getInitials(data?.user?.name ?? '')"
                v-model:open="userMenuActive"
            />
          </template>
        </FTopBar>
      </template>

      <!-- <template #navigation v-if="$route.path != '/bitacora'">
        <FNavigation :location="$route.path"> -->
      <!-- <FNavigationSection v-if="activeLinkAsesor" :items="itemAsesor" />
          <FNavigationSection v-if="activeLinkAdmin" :items="itemsRolAdmin" />
          <FNavigationSection v-if="activeLinkSec" :items="itemsSec" /> -->
      <!-- </FNavigation>
      </template> -->

      <FPage full-width>
        <LazyNuxtPage v-if="$route.path !== '/documentosUPS'"/>
      </FPage>
      <Footer></Footer>
    </FFrame>
  </FAppProvider>
</template>

<style>
</style>