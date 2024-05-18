<template>
  <FCardSection>
    <FVerticalStack gap="8">
      <FDivider></FDivider>

      <FText id="buscarSumilla" as="h1" variant="headingMd" font-weight="semibold"
        >BITÁCORAS</FText
      >
      <DataTable
        :value="bitacorasList"
        :showGridlines="true"
        :stripedRows="true"
        tableStyle="min-width: 50rem"
        :paginator="true"
        :rows="10"
      >
        <!-- <template #header>
          <div
            class="datatable-header-toolbar flex flex-wrap align-items-center justify-content-between gap-2"
          >
            <FHorizontalStack gap="2">
              <FButton
                @click="prepareCreate"
                size="medium"
                :icon="PlusSolid"
                PlusSolid
                primary
                >Crear</FButton
              >
            </FHorizontalStack>
          </div>
        </template> -->
        <Column field="codigo" header="No. de ref" style="width: 5px"></Column>
        <Column header="Receptor" style="width: 5px">
          <template #body="slotProps">
            {{ slotProps.data.receptor_documento.per_nombres }}
            {{ slotProps.data.receptor_documento.per_apellidos }}
          </template>
        </Column>
        <Column header="Remitente" style="width: 5px">
          <template #body="slotProps">
            {{ slotProps.data.nombres_remitente }}
            {{ slotProps.data.apellidos_remitente }}
          </template>
        </Column>
        <Column header="Destinatario" style="width: 5px">
          <template #body="slotProps">
            {{
              slotProps.data.destinatario.per_nombres +
              " " +
              slotProps.data.destinatario.per_apellidos
            }}
          </template>
        </Column>
        <Column
          field="asunto"
          header="Asunto"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>
        <Column
          field="lugar_destino"
          header="Lugar de destino"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>
        <Column
          field="fecha_recepcion"
          header="Fecha recepción"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>
        <Column
          field="hora_recepcion"
          header="Hora recepción"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>

        <Column header="Mensajero" style="width: 10px" bodyStyle="text-align: center;">
          <template #body="slotProps">
            {{
              slotProps.data.mensajero
                ? slotProps.data.mensajero.per_nombres +
                  " " +
                  slotProps.data.mensajero.per_apellidos
                : ""
            }}
          </template>
        </Column>

        <Column
          field="numero_guia"
          header="Número de guia"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>

        <Column
          field="registro_sgad"
          header="Registro en el SGAD"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>

        <Column
          field="numero_tramite"
          header="Número de trámite"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>

        <Column
          field="observaciones"
          header="Observaciones"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>

        <Column header="Persona que entrega" style="width: 5px">
          <template #body="slotProps">
            {{
              slotProps.data.usr_emisor
                ? slotProps.data.usr_emisor.per_nombres +
                  slotProps.data.usr_emisor.per_apellidos
                : ""
            }}
          </template>
        </Column>

        <Column header="Persona que recibe" style="width: 5px">
          <template #body="slotProps">
            {{
              slotProps.data.usr_receptor
                ? slotProps.data.usr_receptor.per_nombres +
                  slotProps.data.usr_receptor.per_apellidos
                : ""
            }}
          </template>
        </Column>

        <Column
          field="fecha_entrega"
          header="Fecha entrega"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>

        <Column
          field="hora_entrega"
          header="Hora entrega"
          style="width: 10px"
          bodyStyle="text-align: center;"
        >
        </Column>

        <Column style="width: 10px">
          <template #body="slotProps">
            <FButton size="slim" :icon="PencilSolid" @click="prepareEdit(slotProps.data)"
              >Editar</FButton
            >
          </template>
        </Column>
        <Column style="width: 10px">
          <template #body="slotProps">
            <FButton
              size="slim"
              secondary
              :icon="TrashCanSolid"
              @click="handleChangeDeleteModal(slotProps.data)"
            >
              Eliminar</FButton
            >
          </template>
        </Column>
      </DataTable>
    </FVerticalStack>

    <FModal
      v-model="createModal"
      title=""
      title-hidden
      large
      :primaryAction="{
        content: 'Guardar Sumilla',
        onAction: onSubmited,
      }"
      :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeCreateModal,
        },
      ]"
    >
      <FModalSection title-hidden v-show="action == persistAction.create">
        <FVerticalStack gap="4">
          <FText id="buscarSumilla" as="h1" variant="bodyLg">Buscar Sumilla</FText>
          <FTextField type="number" v-model="codigoSumilla" id="codigoSumilla" />
          <FButton @click="findSumilla" size="medium" :icon="MagnifyingGlassSolid" primary
            >Buscar sumilla
          </FButton>
        </FVerticalStack>
      </FModalSection>

      <FModalSection v-if="sumilla?.codigo != null || bitacora.sumilla?.codigo != null">
        <FVerticalStack gap="4" align="center">
          <FText id="receptorlbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Receptor del documento:
          </FText>
          <FText id="receptor" as="h6" variant="bodyMd" font-weight="regular">
            {{ sumilla?.responsable.per_nombres }}
            {{ sumilla?.responsable.per_apellidos }}
          </FText>

          <FText id="remitenteNombreLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Nombres remitente:
          </FText>
          <FTextField
            id="remitenteNombre"
            v-model="bitacora.nombres_remitente"
            :error="v$?.nombres_remitente.$error"
            :label="v$?.nombres_remitente.$error ? 'Este campo es requerido' : ''"
          />
          <FText id="remitenteApellidoLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Apellidos remitente:
          </FText>
          <FTextField
            id="remitenteApellido"
            v-model="bitacora.apellidos_remitente"
            :error="v$?.apellidos_remitente.$error"
            :label="v$?.apellidos_remitente.$error ? 'Este campo es requerido' : ''"
          />

          <FHorizontalStack gap="4">
            <FText id="mensajeroLbl" as="h6" variant="bodyMd" fontWeight="semibold">
              Mensajero:
            </FText>
            <AutoComplete
              @mouseover="
                bitacora.mensajero != null
                  ? (mostrarMensajero = true)
                  : (mostrarMensajero = false)
              "
              @mouseleave="mostrarMensajero = false"
              v-model="bitacora.mensajero"
              optionLabel="nombreCompleto"
              :suggestions="filteredItems"
              @Complete="searchItem"
            />
            <span v-if="v$.mensajero.$error" style="color: #c5280c"
              >* El campo mensajero es requerido</span
            >
            <div v-if="mostrarMensajero">
              <FText as="p" variant="bodySm" font-weight="semibold">
                {{ bitacora.mensajero.per_nombres }}
                {{ bitacora.mensajero.per_apellidos }}
              </FText>
            </div>
          </FHorizontalStack>

          <FText id="numeroGuiaLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Número de guia:
          </FText>
          <FTextField id="numeroGuia" v-model="bitacora.numero_guia" />

          <FText id="observacionesLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Observaciones:
          </FText>
          <FTextField id="observaciones" v-model="bitacora.observaciones" />

          <FCard sectioned style="box-shadow: 3px 3px 10px 0px rgba(0, 0, 0, 0.3)">
            <FCardSection>
              <FVerticalStack gap="4">
                <FHorizontalStack gap="4">
                  <FText
                    id="destinatarioLbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                  >
                    Destinatario:
                  </FText>
                  <AutoComplete
                    @mouseover="
                      bitacora.destinatario != null
                        ? (mostrarDestinatario = true)
                        : (mostrarDestinatario = false)
                    "
                    @mouseleave="mostrarDestinatario = false"
                    style="width: 300px"
                    v-model="bitacora.destinatario"
                    optionLabel="nombreCompleto"
                    :suggestions="filteredItems"
                    @Complete="searchItem"
                    :style="[v$.destinatario.$error ? { 'border-color': 'red' } : {}]"
                  />
                  <span v-if="v$.destinatario.$error" style="color: #c5280c"
                    >* El campo destinatario es requerido</span
                  >
                  <div v-if="mostrarDestinatario">
                    <FText as="p" variant="bodySm" font-weight="semibold">
                      {{ bitacora.destinatario.per_nombres }}
                      {{ bitacora.destinatario.per_apellidos }}
                    </FText>
                  </div>
                </FHorizontalStack>
                <FText id="asuntolbl" as="h6" variant="bodyMd" fontWeight="semibold">
                  Asunto:
                </FText>
                <FTextField
                  id="asunto"
                  v-model="bitacora.asunto"
                  :error="v$?.asunto.$error"
                  :label="v$?.asunto.$error ? 'Este campo es requerido' : ''"
                />
                <FText
                  id="lugarDestinolbl"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
                >
                  Lugar de destino:
                </FText>
                <FTextField
                  id="lugarDestino"
                  v-model="bitacora.lugar_destino"
                  :error="v$?.lugar_destino.$error"
                  :label="v$?.lugar_destino.$error ? 'Este campo es requerido' : ''"
                />
              </FVerticalStack>
            </FCardSection>
          </FCard>

          <FCard sectioned style="box-shadow: 3px 3px 10px 0px rgba(0, 0, 0, 0.3)">
            <FHorizontalStack gap="32">
              <FText
                id="fechaRecepcionlbl"
                as="h6"
                variant="bodyMd"
                fontWeight="semibold"
              >
                Fecha de recepción:
              </FText>
              <FText id="fechaRecepcion" as="h6" variant="bodyMd" fontWeight="regular">
                {{ sumilla?.fecha_sumilla }}
              </FText>

              <FText id="horaRecepcionLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                Hora de recepción:
              </FText>
              <FText id="horaRecepcion" as="h6" variant="bodyMd" fontWeight="regular">
                {{ sumilla?.hora_sumilla }}
              </FText>
            </FHorizontalStack>
          </FCard>

          <FCard sectioned style="box-shadow: 3px 3px 10px 0px rgba(0, 0, 0, 0.3)">
            <FVerticalStack gap="4">
              <FHorizontalStack gap="16">
                <FText
                  id="fechaRecepcionlbl"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
                >
                  Fecha de entrega:
                </FText>
                <FTextField id="fechaEntrega" type="date" v-model="fechaEntrega" />

                <FText
                  id="horaEntregaLbl"
                  for="calendar-timeonly"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
                >
                  Hora de entrega:
                </FText>

                <div class="flex-auto">
                  <Calendar
                    v-model="bitacora.hora_entrega"
                    showIcon
                    iconDisplay="input"
                    timeOnly
                    @update:model-value="changeHour"
                  >
                  </Calendar>
                </div>
              </FHorizontalStack>

              <FHorizontalStack gap="4">
                <FText
                  id="personaEntregaLbl"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
                >
                  Persona que entrega:
                </FText>
                <AutoComplete
                  @mouseover="
                    bitacora.usr_emisor != null
                      ? (mostrarEmisor = true)
                      : (mostrarEmisor = false)
                  "
                  @mouseleave="mostrarEmisor = false"
                  style="width: 300px"
                  v-model="bitacora.usr_emisor"
                  optionLabel="nombreCompleto"
                  :suggestions="filteredItems"
                  @Complete="searchItem"
                />
                <div v-if="mostrarEmisor">
                  <FText as="p" variant="bodySm" font-weight="semibold">
                    {{ bitacora.usr_emisor.per_nombres }}
                    {{ bitacora.usr_emisor.per_apellidos }}
                  </FText>
                </div>
              </FHorizontalStack>

              <FHorizontalStack gap="4">
                <FText
                  id="personaRecibeLbl"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
                >
                  Persona que recibe:
                </FText>
                <AutoComplete
                  @mouseover="
                    bitacora.usr_receptor != null
                      ? (mostrarUsrReceptor = true)
                      : (mostrarUsrReceptor = false)
                  "
                  @mouseleave="mostrarUsrReceptor = false"
                  style="width: 300px"
                  v-model="bitacora.usr_receptor"
                  optionLabel="nombreCompleto"
                  :suggestions="filteredItems"
                  @Complete="searchItem"
                />
                <div v-if="mostrarUsrReceptor">
                  <FText as="p" variant="bodySm" font-weight="semibold">
                    {{ bitacora.usr_receptor.per_nombres }}
                    {{ bitacora.usr_receptor.per_apellidos }}
                  </FText>
                </div>
              </FHorizontalStack>
            </FVerticalStack>
          </FCard>
        </FVerticalStack>
      </FModalSection>
    </FModal>

    <FModal
      v-model="deleteModal"
      title=""
      title-hidden
      :primaryAction="{
        content: 'Eliminar',
        onAction: confirmDelete,
      }"
      :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: changeDeleteModal,
        },
      ]"
    >
      <FModalSection title-hidden style="text-align: center">
        <FVerticalStack gap="4">
          <FText
            as="h5"
            variant="headingMd"
            :font-weight="'semibold'"
            style="text-align: center"
          >
            {{ $t("app.sgadNuxt.sumilla.eliminar") }}
          </FText>
        </FVerticalStack>
      </FModalSection>
    </FModal>
  </FCardSection>
</template>
<script setup lang="ts">
import AutoComplete from "primevue/autocomplete";
import Calendar from "primevue/calendar";

import {
  PlusSolid,
  PencilSolid,
  TrashCanSolid,
  MagnifyingGlassSolid,
} from "@ups-dev/freya-icons";
import { Bitacora } from "../../models/Bitacora.model";
import { Persona, Sumilla } from "../../models/Sumilla.model";
import { useToast } from "primevue/usetoast";

const { handleSubmit } = useForm();
const filteredItems = ref<Persona[]>([]);

const toast = useToast();
const {
  bitacorasList,
  bitacora,
  receptorPersonaList,
  sumilla,
  getSumillaByNumeroSumilla,
  getUsers,
  saveBitacora,
  findBitacoras,
  editBitacora,
  deleteBitacora,
  v$,
  ErrorMessage,
} = useBitacoraComposable();

const createModal = ref<boolean>(false);
const codigoSumilla = ref<string>("");
const mostrarDestinatario = ref<boolean>(false);
const mostrarMensajero = ref<boolean>(false);
const mostrarEmisor = ref<boolean>(false);
const mostrarUsrReceptor = ref<boolean>(false);
const fechaEntrega = ref<string>("");
const codigoBitacora = ref<Number>(0);
const deleteModal = ref<boolean>(false);

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

// watch(
//   () => bitacora.value.registro_sgad,
//   (newValue, oldValue) => {
//     console.log("ESTA ENTRANDO DESDE EL INICIO");
//     if (bitacora.value.registro_sgad === "N") {
//       bitacora.value.numero_tramite = "";
//     }
//   }
// );

const searchItem = (event: any) => {
  console.log(event);
  const query = event.query.toLowerCase();
  filteredItems.value = receptorPersonaList.value.filter(
    (item) =>
      item.per_nombres.toLowerCase().includes(query) ||
      item.per_apellidos.toLowerCase().includes(query)
  );
};

enum persistAction {
  create,
  edit,
  view,
}
const action = ref();

const findSumilla = async () => {
  // sumilla.value = await getSumillaById(parseInt(codigoSumilla.value));
  receptorPersonaList.value = await getUsers();
  if (sumilla.value) {
    bitacora.value.receptor_documento = sumilla.value?.responsable!;
    bitacora.value.fecha_recepcion = sumilla.value?.fecha_sumilla!;
    bitacora.value.hora_recepcion = sumilla.value?.hora_sumilla!;
    bitacora.value.sumilla = sumilla.value!;
  }
};

const prepareCreate = () => {
  action.value = persistAction.create;
  handleChangeCreateModal();
};

const handleChangeCreateModal = () => {
  createModal.value = !createModal.value;
  bitacora.value = {} as Bitacora;
  sumilla.value = {} as Sumilla;
  codigoSumilla.value = "";
  v$.value.$reset();
};

const changeDeleteModal = () => {
  deleteModal.value = !deleteModal.value;
};

const prepareEdit = async (bitacoraParam: Bitacora) => {
  createModal.value = !createModal.value;
  action.value = persistAction.edit;
  receptorPersonaList.value = await getUsers();
  bitacora.value = { ...bitacoraParam };
  sumilla.value = { ...bitacoraParam.sumilla };
  bitacora.value.mensajero.nombreCompleto =
    bitacora.value.mensajero.per_nombres + " " + bitacora.value.mensajero.per_apellidos;
  bitacora.value.destinatario.nombreCompleto =
    bitacora.value.destinatario.per_nombres +
    " " +
    bitacora.value.destinatario.per_apellidos;
  bitacora.value.sumilla = sumilla.value;
  if (bitacora.value.fecha_entrega != null) {
    fechaEntrega.value = bitacora.value.fecha_entrega.toString();
  }
  console.log(bitacora.value.hora_entrega, "HORA ENTREGA PREPARE EDIT");
};

const handleChangeDeleteModal = (bitacorParam: Bitacora) => {
  deleteModal.value = !deleteModal.value;
  codigoBitacora.value = bitacorParam.codigo;
};

const confirmDelete = async () => {
  await deleteBitacora(codigoBitacora.value);
  await findBitacoras();
  toast.add({
    severity: "success",
    summary: "Bitácora",
    detail: `Se ha eliminado la bitácora correctamente`,
    life: 5000,
  });
  changeDeleteModal();
};

const changeHour = () => {
  const fecha = new Date(bitacora.value.hora_entrega);
  const hora = fecha.getHours();
  const minutos = fecha.getMinutes();
  bitacora.value.hora_entrega = `${hora}:${minutos}`;
  console.log(bitacora.value.hora_entrega, "hora_entrega");
};

const onSubmited = handleSubmit(async (values) => {
  v$.value.$validate();
  if (!v$.value.$error) {
    if (action.value == persistAction.edit) {
      await editBitacora(bitacora.value, bitacora.value.codigo);
    } else {
      await saveBitacora(bitacora.value);
    }

    await findBitacoras();
    toast.add({
      severity: "success",
      summary: "Sumilla",
      detail: `Se ha guardado la sumilla correctamente`,
      life: 5000,
    });
    handleChangeCreateModal();
    console.log("Formulario válido, enviando datos...");
  } else {
    // Manejo de errores
    console.error("El formulario no es válido. Por favor, corrige los errores.");
    console.log(v$.value.$errors);
    console.log(v$.value.$error);
  }
});

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
</script>
<style lang="css">
.p-autocomplete {
  height: 35px;
}

.p-calendar {
  height: 33px;
}
</style>
