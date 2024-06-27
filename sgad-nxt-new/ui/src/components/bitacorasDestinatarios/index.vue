<template>
  <FCard sectioned>
    <FFormLayout>
      <FLayoutSection>
        <DataTable
          :value="eventosBitacorasList"
          :showGridlines="true"
          :stripedRows="true"
          tableStyle="min-width: 50rem"
          :paginator="true"
          :rows="10"
        >
          <Column field="codigo" header="No. de ref" style="width: 5px"></Column>
          <Column header="Remitente" style="width: 5px">
            <template #body="slotProps">
              {{ slotProps.data.bitacora.nombres_remitente }}
              {{ slotProps.data.bitacora.apellidos_remitente }}
            </template>
          </Column>

          <Column
            field="bitacora.asunto"
            header="Asunto"
            style="width: 10px"
            bodyStyle="text-align: center;"
          >
          </Column>

          <Column
            field="bitacora.numero_guia"
            header="Número de guia"
            style="width: 10px"
            bodyStyle="text-align: center;"
          >
          </Column>

          <Column
            field="estado.estado_descripcion"
            header="Estado documento"
            headerStyle="text-align: center"
            style="width: 10px"
            body-style="text-align: center"
          >
            <template #body="slotProps">
              <FBadge status="info" v-if="slotProps.data.estado.codigo === 5"
                >Recibido</FBadge
              >
              <FBadge status="success" v-if="slotProps.data.estado.codigo === 4"
                >Aprobado</FBadge
              >
              <FBadge status="critical" v-if="slotProps.data.estado.codigo === 3"
                >Se solicita documentación fisica</FBadge
              >
              <FBadge status="attention" v-if="slotProps.data.estado.codigo === 7"
                >Reasignado para trámite</FBadge
              >
            </template>
          </Column>

          <Column header="Acciones" style="width: 5px" bodyStyle="text-align:center">
            <template #body="slotProps">
              <FButton
                size="medium"
                :icon="MagnifyingGlassSolid"
                @click="prepareAcciones(slotProps.data)"
              />
            </template>
          </Column>
        </DataTable>
      </FLayoutSection>
    </FFormLayout>

    <!-- MODAL ACCIONES -->

    <FModal
      large
      v-model="accionesModal"
      title=""
      title-hidden
      :primaryAction="{
        content: 'Confirmar cambios',
        onAction: onSubmitAcciones,
        disabled: desabilitarGuardarCambios,
      }"
      :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeAcciones,
        },
      ]"
    >
      <FModalSection>
        <FButton
          @click="handleToggle"
          :ariaExpanded="open"
          ariaControls="basic-collapsible"
          :icon="open ? PlusSolid : MinusSolid"
          outline
        >
          <FText as="h6" variant="headingSm">
            {{ open ? "Eventos" : "Eventos" }}
          </FText>
        </FButton>
      </FModalSection>

      <FCollapsible
        id="basic-collapsible"
        :open="open"
        :transition="transition"
        expand-on-print
      >
        <FModalSection>
          <FVerticalStack gap="4">
            <FResourceList
              :resourceName="{ singular: 'evento', plural: 'eventos' }"
              :items="eventosBitacorasAcciones"
            >
              <template #item="{ item }">
                <FResourceItem id="item.codigo">
                  <template #media>
                    <FAvatar customer size="medium" />
                  </template>

                  <FVerticalStack gap="1">
                    <FHorizontalStack gap="2">
                      <FText fontWeight="bold" as="span">
                        {{ item.per_codigo_responsable.per_nombres }}
                      </FText>
                      <FText fontWeight="bold" as="span">
                        {{ item.per_codigo_responsable.per_apellidos }}
                      </FText>
                    </FHorizontalStack>

                    <FHorizontalStack gap="28">
                      <FText fontWeight="semibold" as="span"> No. de ref: </FText>
                      <FText fontWeight="semibold" as="span">
                        {{ item.bitacora.sumilla.numero_sumilla }}
                      </FText>
                      <FText
                        fontWeight="semibold"
                        as="span"
                        alignment="start"
                        style="font-style: italic"
                        class="texto-superior-derecha"
                        variant="bodySm"
                      >
                        {{ item.fecha }}
                      </FText>
                    </FHorizontalStack>

                    <FHorizontalStack gap="32">
                      <FText fontWeight="semibold" as="span"> Asunto: </FText>
                      <FText fontWeight="semibold" as="span">
                        {{ item.bitacora.asunto }}
                      </FText>
                    </FHorizontalStack>

                    <FHorizontalStack gap="12">
                      <FText fontWeight="semibold" as="span"> Estado documento: </FText>
                      <FBadge :status="item.estado.codigo === 5 ? 'success' : 'default'">
                        {{ item.estado.estado_descripcion }}
                      </FBadge>
                    </FHorizontalStack>
                  </FVerticalStack>
                </FResourceItem>
              </template>
            </FResourceList>
          </FVerticalStack>
        </FModalSection>
      </FCollapsible>

      <FModalSection>
        <div v-if="documentosBitacoraList.length > 0">
          <FVerticalStack gap="4">
            <FText as="h6" variant="bodyMd" font-weight="semibold">Documentos:</FText>
          </FVerticalStack>
          <ul>
            <li
              v-for="documento in documentosBitacoraList"
              :key="documento.doc_nombre_archivo"
              style="margin-top: 1rem"
            >
              <a
                :href="
                  createDownloadLink(documento.doc_archivo, documento.doc_nombre_archivo)
                "
                :download="documento.doc_nombre_archivo"
              >
                {{ documento.doc_nombre_archivo }}
              </a>
            </li>
          </ul>
        </div>
      </FModalSection>

      <FCardSection>
        <FCardSubsection>
          <FFormLayout>
            <FFormLayoutGroup>
              <FVerticalStack gap="4">
                <FText id="estadoLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                  Estado:
                </FText>

                <Dropdown
                  v-model="estadoObj"
                  :options="estadosList"
                  optionLabel="estado_descripcion"
                  optionValue="codigo"
                  placeholder="Seleccione"
                  :disabled="desabilitarGuardarCambios"
                  :style="[estadoObjError != null ? { 'border-color': '#FF6767' } : {}]"
                />

                <FText
                  v-if="estadoObj == 7"
                  id="personaLbl"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
                >
                  Destinatario:
                </FText>
                <FBox
                  v-if="estadoObj == 7"
                  background="bg"
                  padding="0"
                  borderWidth="1"
                  borderColor="border"
                  :style="[personaObjError != null ? { 'border-color': '#FF6767' } : {}]"
                >
                  <AutoComplete
                    v-model="personaObj"
                    optionLabel="nombreCompleto"
                    :suggestions="filteredItems"
                    @Complete="searchItem"
                    class="full-width-autocomplete"
                  />
                </FBox>
              </FVerticalStack>
            </FFormLayoutGroup>
          </FFormLayout>
        </FCardSubsection>
      </FCardSection>
    </FModal>
  </FCard>
</template>
<script lang="ts" setup>
import { EventoBitacora } from "../../models/EventoBitacora.model";
import {
  MagnifyingGlassSolid,
  TrashCanSolid,
  PlusSolid,
  MinusSolid,
} from "@ups-dev/freya-icons";
import { Estado } from "../../models/Estado.model";
import AutoComplete from "primevue/autocomplete";
import { useToast } from "primevue/usetoast";
import { Persona } from "../../models/Sumilla.model";

const {
  eventosBitacorasList,
  userLogin,
  documentosBitacoraList,
  getAllEventosByBitCodigo,
  getAllEstados,
  getUsers,
  saveEventoBitacora,
  sendEmail,
  getDocumentosByBitCodigo,
} = useBitacorasDestinatariosComposable();
const eventoSelected = ref<EventoBitacora>({} as EventoBitacora);
const accionesModal = ref<boolean>(false);
const eventosBitacorasAcciones = ref<EventoBitacora[]>([]);
const estadosList = ref<Estado[]>([]);
const { handleSubmit } = useForm();
const isRequired = computed(() => estadoObj.value == 7);
const toast = useToast();
const desabilitarGuardarCambios = ref<boolean>();

const {
  value: estadoObj,
  errorMessage: estadoObjError,
  resetField: resetEstadoObj,
} = useField<number>("estadoObj", {
  required: true,
});

const {
  value: personaObj,
  errorMessage: personaObjError,
  resetField: resetPersonaObj,
} = useField<Persona>("personaObj", {
  required: isRequired.value,
});

watch(
  () => estadoObj.value,
  (newValue, oldValue) => {
    if (estadoObj.value != 7) {
      resetPersonaObj();
    }
  }
);

const filteredItems = ref<Persona[]>([]);
const receptorPersonaList = ref<Persona[]>([]);

const searchItem = (event: any) => {
  const query = event.query.toLowerCase();
  filteredItems.value = receptorPersonaList.value.filter(
    (item) =>
      item.per_nombres.toLowerCase().includes(query) ||
      item.per_apellidos.toLowerCase().includes(query)
  );
};

const createDownloadLink = (doc_archivo: any, doc_nombre_archivo: any) => {
  const byteCharacters = atob(doc_archivo);
  const byteNumbers = new Array(byteCharacters.length);
  for (let i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
  }
  const byteArray = new Uint8Array(byteNumbers);
  const blob = new Blob([byteArray], { type: "application/pdf" });
  const url = URL.createObjectURL(blob);

  return url;
};

const handleChangeAcciones = () => {
  resetEstadoObj();
  resetPersonaObj();
  accionesModal.value = !accionesModal.value;
};

const prepareAcciones = async (eventoParam: EventoBitacora) => {
  documentosBitacoraList.value = await getDocumentosByBitCodigo(
    eventoParam.bitacora.codigo
  );
  desabilitarGuardarCambios.value = false;
  eventoSelected.value = eventoParam;
  eventosBitacorasAcciones.value = await getAllEventosByBitCodigo(
    eventoParam.bitacora.codigo
  );
  estadosList.value = await getAllEstados();
  receptorPersonaList.value = await getUsers();

  const lastEvent: EventoBitacora =
    eventosBitacorasAcciones.value[eventosBitacorasAcciones.value.length - 1];
  estadoObj.value = lastEvent.estado.codigo;
  switch (lastEvent.estado.codigo) {
    case 3:
      desabilitarGuardarCambios.value = true;
      handleChangeAcciones();
      break;
    case 7:
      desabilitarGuardarCambios.value =
        lastEvent.per_codigo_reasignado.codigo != userLogin.value.codigo ? true : false;
      handleChangeAcciones();
      break;
    default:
      handleChangeAcciones();
  }
};

const onSubmitAcciones = handleSubmit(async (values) => {
  if (personaObj.value == null && estadoObj.value === 7) {
    toast.add({
      severity: "error",
      summary: "Destinatario",
      detail: `Seleccione un destinatario`,
      life: 3000,
    });
  } else {
    switch (estadoObj.value) {
      case 4: // APROBADO
        eventoSelected.value.estado.codigo = 4;
        eventoSelected.value.codigo = 0;
        eventoSelected.value.per_codigo_responsable.codigo = userLogin.value.codigo;
        await saveEventoBitacora(eventoSelected.value);
        handleChangeAcciones();
        break;
      case 3: // NO APROBADO - SOLICITAR DOCUMENTACIÓN FÍSICA
        eventoSelected.value.estado.codigo = 3;
        eventoSelected.value.codigo = 0;
        eventoSelected.value.per_codigo_responsable.codigo = userLogin.value.codigo;
        await saveEventoBitacora(eventoSelected.value);
        enviarEmail(eventoSelected.value);
        handleChangeAcciones();
        break;
      case 7:
        eventoSelected.value.per_codigo_reasignado = {} as Persona;
        eventoSelected.value.estado.codigo = 7;
        eventoSelected.value.codigo = 0;
        eventoSelected.value.per_codigo_responsable.codigo = userLogin.value.codigo;
        eventoSelected.value.per_codigo_reasignado.codigo = personaObj.value.codigo;
        await saveEventoBitacora(eventoSelected.value);
        handleChangeAcciones();
        break;
    }
  }
});

const enviarEmail = async (evento: EventoBitacora) => {
  await sendEmail(evento);
};

const open = ref(false);
const handleToggle = () => (open.value = !open.value);
const transition = {
  duration: "var(--f-motion-duration-150)",
  timingFunction: "var(--f-motion-ease-in-out)",
};
</script>
<style lang="css">
.p-autocomplete {
  height: 35px;
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

.texto-superior-derecha {
  position: absolute;
  top: 0;
  right: 0;
  margin: 10px; /* margen opcional para separarlo un poco de los bordes */
}
</style>
