<script setup lang="ts">
import { ref } from "vue";
import { useI18n } from "vue-i18n";
import { FSubNavigationItem } from "@ups-dev/freya-vue";
const { data, signOut } = useAuth();

const defaultState = reactive({
  emailFieldValue: "sbermeob@gmail.com",
  nameFieldValue: "UPS",
});

const skipToContentRef = ref(null);
const storeName = ref(defaultState.emailFieldValue);
const searchActive = ref(false);
const searchValue = ref("");
const userMenuActive = ref(false);
const mobileNavigationActive = ref(false);
const activeLinkAdmin = ref(false);
const activeLinkAsesor = ref(false);
const activeLinkSec = ref(false);

const handleSearchResultsDismiss = () => {
  searchActive.value = false;
  searchValue.value = "";
};

const toggleMobileNavigationActive = () =>
  (mobileNavigationActive.value = !mobileNavigationActive.value);

const cerrarSesion = async () => {
  try {
    signOut();
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
              name="correoEjemplo@ups.edu.ec"
              :detail="storeName"
              initials="CE"
              v-model:open="userMenuActive"
            />
          </template>
        </FTopBar>
      </template>

      <template #navigation>
        <FNavigation :location="$route.path">
          <!-- <FNavigationSection v-if="activeLinkAsesor" :items="itemAsesor" />
          <FNavigationSection v-if="activeLinkAdmin" :items="itemsRolAdmin" />
          <FNavigationSection v-if="activeLinkSec" :items="itemsSec" /> -->
        </FNavigation>
      </template>

      <FPage full-width>
        <LazyNuxtPage />
      </FPage>
    </FFrame>
    <Toast />
  </FAppProvider>
</template>

<style>
.layout-sidebar {
  background-color: #dddddd;
}
</style>
