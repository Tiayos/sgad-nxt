<template>
  <FCard sectioned>
    <FTabs :tabs="tabs" v-model:selected="selected" fitted>
      <FFormLayout v-if="selected == 0">
        <FLayoutSection> </FLayoutSection>

        <BitacoraListBitacora></BitacoraListBitacora>
      </FFormLayout>

      <FFormLayout v-if="selected == 1">
        <FFormLayoutGroup>
          <FModalSection title-hidden>
            <FVerticalStack gap="4">
              <FText id="buscarSumilla" as="h1" variant="bodyLg">Buscar Sumilla</FText>
              <FTextField type="text" v-model="numeroSumilla" id="codigoSumilla" />
              <FButton
                @click="findSumilla"
                size="medium"
                :icon="MagnifyingGlassSolid"
                primary
                >Buscar sumilla
              </FButton>
            </FVerticalStack>
          </FModalSection>
        </FFormLayoutGroup>
      </FFormLayout>
    </FTabs>
  </FCard>
</template>
<script setup lang="ts">
import {
  PlusSolid,
  PencilSolid,
  TrashCanSolid,
  MagnifyingGlassSolid,
  ArrowUpFromLineSolid,
  EllipsisSolid,
  InboxSolid,
  MessageDotsRegular,
} from "@ups-dev/freya-icons";
import Image from "primevue/image";
import type { Persona, Sumilla } from "../../models/Sumilla.model";
import { useToast } from "primevue/usetoast";

const toast = useToast();
const fechaEntrega = ref<string>("");

const {
  bitacora,
  sumillaEncontrada,
  //*Service
  getSumillaByNumeroSumilla,
  filtersSumillaBitacora,
  prepareEdit,
  action,
} = useSumillaComposable();

//*Session storage
const numeroSumilla = ref<string>("");
const selected = ref(0);

interface TabDescriptor {
  id: string;
  content: string;
}

const tabs: TabDescriptor[] = [
  {
    id: "crear-sumilla",
    content: "Crear Sumilla",
  },
  {
    id: "buscar-sumilla",
    content: "Buscar Sumilla",
  },
];

const findSumilla = async () => {
  sumillaEncontrada.value = await getSumillaByNumeroSumilla(numeroSumilla.value.trim());
  if (sumillaEncontrada.value) {
    selected.value = 0;
    await prepareEdit(sumillaEncontrada.value!, action.value);
    filtersSumillaBitacora.value.global.value = sumillaEncontrada.value.numero_sumilla;
  }
};

watch(
  () => fechaEntrega.value,
  () => {
    bitacora.value.fecha_entrega = toDate(fechaEntrega.value);
  }
);

const toDate = (date: string) => {
  const dateParts = date.split("-");
  const year = parseInt(dateParts[0]);
  const month = parseInt(dateParts[1]) - 1; // Month is zero-based
  const day = parseInt(dateParts[2]);
  return new Date(year, month, day);
};

// TRANSFERENCIA DOCUMENTAL

// const handleChangeTransferencia = () => {
//   transferenciaModal.value = !transferenciaModal.value;
// };

// const prepareTransferencia = async () => {
//   userLogin.value = await getUsrLogin(data.value?.user?.email!);
//   bitacorasListTransferenciaDocumental.value = [];
//   fechaInicial.value = "";
//   fechaFinal.value = "";
//   userTransferenciaDocumental.value = 0;
//   mensajeTransferencia.value = "";
//   handleChangeTransferencia();
// };

// const onSubmitTransferencia = async () => {
//   await saveTransferencia(
//     fechaInicial.value,
//     fechaFinal.value,
//     userTransferenciaDocumental.value!,
//     userLogin.value.codigo
//   );
//   handleChangeTransferencia();
//   toast.add({
//     severity: "success",
//     summary: "Transferencia",
//     detail: `Se ha realizado la transferencia correctamente`,
//     life: 3000,
//   });
//   await findBitacoras();
// };

// ENVIO DEL DOCUMENTO
</script>
<style lang="css">
.datatable-header-toolbar {
  background-color: #f2f2f2;
  border: 0.5px solid #c0c0c0;
  padding: 10px;
  border-radius: 4px;
}

.datatable-header-toolbar .FHorizontalStack {
  flex-grow: 1;
}

.datatable-header-toolbar .FButton {
  margin-right: 10px;
}

.datatable-header-toolbar .FButton:last-child {
  margin-right: 0;
}

.p-autocomplete {
  height: 35px;
  widows: auto;
}

.p-calendar {
  height: 33px;
}

.full-width-autocomplete .p-autocomplete {
  width: 100%;
}

.full-width-autocomplete .p-autocomplete-input {
  width: 100%;
  box-sizing: border-box;
}

/* Asegúrate de que el contenedor del AutoComplete ocupe el ancho completo */
.full-width-autocomplete {
  width: 100%;
}

/* *DROPDOWN */

.p-inputtext {
  width: 100% !important;
  padding: 0.5rem !important;
  box-sizing: border-box !important;
  color: #000 !important;
  background-color: #fff !important;
  border: 1px solid #ccc !important;
}
</style>
