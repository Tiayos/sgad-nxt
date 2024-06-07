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
import { Persona, Sumilla } from "../../models/Sumilla.model";
import { useToast } from "primevue/usetoast";

const toast = useToast();
const fechaEntrega = ref<string>("");

const {
  sumilla,
  bitacora,
  sumillaEncontrada,
  //*Service
  findSumillas,
  deleteSumilla,
  findBitacoras,
  editBitacora,
  deleteBitacoraByNumSumilla,
  receptorPersonaList,
  getSumillaByNumeroSumilla,
  getBitacoraByNumSumilla,
  filtersSumillaBitacora,
  findBitacorasByFechaTransferencia,
  prepareEdit,
} = useSumillaComposable();

//*Session storage
const deleteModal = ref<boolean>(false);
const codigoSumillaDelete = ref<Number>(0);
const numeroSumilla = ref<string>("");
const filteredItems = ref<Persona[]>([]);

const selected = ref(0);

const options = [
  {
    label: "SI",
    value: "S",
  },
  {
    label: "NO",
    value: "N",
  },
];
const {
  value: numHojas,
  errorMessage: numHojasError,
  resetField: resetNumHojas,
} = useField<string>("numHojas", {
  required: true,
});

// enum persistAction {
//   create,
//   edit,
//   view,
// }
// const action = ref();

// const handleChangeCreateModal = () => {
//   createModal.value = !createModal.value;
// };

const searchItem = (event: any) => {
  const query = event.query.toLowerCase();
  filteredItems.value = receptorPersonaList.value.filter(
    (item) =>
      item.per_nombres.toLowerCase().includes(query) ||
      item.per_apellidos.toLowerCase().includes(query)
  );
};

const changeHour = () => {
  const fecha = new Date(bitacora.value.hora_entrega);
  const hora = fecha.getHours();
  const minutos = fecha.getMinutes();
  bitacora.value.hora_entrega = `${hora}:${minutos}`;
};

const confirmDelete = async () => {
  await deleteBitacoraByNumSumilla(sumilla.value.codigo!);
  await deleteSumilla(sumilla.value.codigo!);
  await findSumillas();
  await findBitacoras();
  toast.add({
    severity: "success",
    summary: "Sumilla",
    detail: `Se ha eliminado la sumilla correctamente`,
    life: 5000,
  });
  changeDeleteModal();
};

const handleChangeDeleteModal = async (sum: Sumilla) => {
  deleteModal.value = !deleteModal.value;
  codigoSumillaDelete.value = sum.codigo!;
  sumilla.value = sum;
};

const changeDeleteModal = () => {
  deleteModal.value = !deleteModal.value;
};

// const onSubmited = handleSubmit(async (values) => {
//   v$.value.$validate;
//   if (!v$.value.$error) {
//     if (action.value == persistAction.create) {
//       sumilla.value.numero_hojas = parseInt(numHojas.value);
//       sumilla.value.fecha_sumilla = new Date();
//       sumilla.value.hora_sumilla = new Date().getHours() + ":" + new Date().getMinutes();
//       sumilla.value = await saveSumilla(sumilla.value, data.value?.user?.email!);
//     } else if (action.value == persistAction.edit) {
//       sumilla.value.numero_hojas = Number(numHojas.value);
//       await editSumilla(sumilla.value, sumilla.value.codigo!);
//     }

//     await findSumillas();

//     if (action.value == persistAction.edit) {
//       await editBitacora(bitacora.value, bitacora.value.codigo);
//     } else {
//       bitacora.value.receptor_documento = sumilla.value?.responsable!;
//       bitacora.value.fecha_recepcion = sumilla.value?.fecha_sumilla!;
//       bitacora.value.hora_recepcion = sumilla.value?.hora_sumilla!;
//       bitacora.value.sumilla = sumilla.value;
//       bitacora.value.estado_transferencia = "N";
//       bitacora.value.adicionado = data.value?.user?.email!;
//       await saveBitacora(bitacora.value);
//     }

//     await findBitacoras();
//     createModal.value = !createModal.value;
//     resetNumHojas();

//     toast.add({
//       severity: "success",
//       summary: "Sumilla",
//       detail: `Se ha guardado la sumilla correctamente`,
//       life: 5000,
//     });
//   }
// });

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
    await prepareEdit(sumillaEncontrada.value!);
    filtersSumillaBitacora.value.global.value = sumillaEncontrada.value.numero_sumilla;
  }
};

watch(
  () => fechaEntrega.value,
  (newValue, oldValue) => {
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

const transferenciaModal = ref<boolean>(false);
const fechaInicial = ref<string>("");
const fechaFinal = ref<string>("");

watch(
  () => fechaFinal.value,
  (newValue, oldValue) => {
    if (fechaFinal.value != "") {
      findBitacorasByFechaTransferencia(fechaInicial.value, fechaFinal.value);
    }
  }
);

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

const prepareEnviarDocumento = async (sumillaDocumento: Sumilla) => {
  bitacora.value = await getBitacoraByNumSumilla(sumillaDocumento.numero_sumilla);

  if (bitacora.value.doc_archivo != null) {
    if (bitacora.value.estado_transferencia != "N") {
      toast.add({
        severity: "info",
        summary: "Documento",
        detail: `El documento se encuentra enviado`,
        life: 3000,
      });
    } else {
      handleChangeEnvioDocumento();
    }
  } else {
    toast.add({
      severity: "error",
      summary: "Documento",
      detail: `No está subido el documento digital`,
      life: 3000,
    });
  }
};

const envioDocumentoDestinatarioModal = ref<boolean>(false);

const handleChangeEnvioDocumento = () => {
  envioDocumentoDestinatarioModal.value = !envioDocumentoDestinatarioModal.value;
};

const onSubmitEnviarDocumento = async () => {
  // bitacora.value.estado_envio_destinatario = "E";
  await editBitacora(bitacora.value, bitacora.value.codigo); // E- ENVIADO N- NO ENVIADO A- APROBADO R-RECHAZADO
  toast.add({
    severity: "success",
    summary: "Documento",
    detail: `El documento se envío correctamente `,
    life: 3000,
  });
  await findBitacoras();
  handleChangeEnvioDocumento();
};
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
