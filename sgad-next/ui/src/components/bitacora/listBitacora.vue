<template>
  <FCardSection>
    <FVerticalStack gap="8">
      <FDivider></FDivider>

      <FText id="buscarSumilla" as="h1" variant="headingMd" font-weight="semibold"
        >BITÁCORAS</FText
      >
      <DataTable
        v-model:selection="bitacoraSelected"
        selectionMode="single"
        v-model:filters="filtersSumillaBitacora"
        :value="bitacorasList"
        :showGridlines="true"
        :stripedRows="true"
        tableStyle="min-width: 50rem"
        :paginator="true"
        :rows="10"
      >
        <template #header>
          <div
            class="datatable-header-toolbar flex flex-wrap align-items-center justify-content-between gap-2"
          >
            <FHorizontalStack gap="4" align="space-between">
              <FHorizontalStack gap="2">
                <FButton
                  @click="prepareCreate"
                  size="medium"
                  :icon="PlusSolid"
                  PlusSolid
                  primary
                  >Crear</FButton
                >
                <FButton
                  @click="prepareTransferencia"
                  size="medium"
                  :icon="InboxSolid"
                  :disabled="bitacorasList.length == 0"
                  secondary
                  >Transferencia Documental</FButton
                >
                <FButton
                  size="medium"
                  secondary
                  :icon="MessageDotsRegular"
                  :disabled="bitacoraSelected.codigo == null"
                  @click="prepareEnviarDocumento(bitacoraSelected.sumilla)"
                  >Enviar Documento
                </FButton>
              </FHorizontalStack>

              <FHorizontalStack gap="4">
                <FTextField
                  type="text"
                  id="filterSumilla"
                  v-model="filtersSumillaBitacora['global'].value"
                  placeholder="N° Sumilla"
                >
                </FTextField>
              </FHorizontalStack>
            </FHorizontalStack>
          </div>
        </template>

        <Column field="codigo" header="No. de ref" style="width: 5px"></Column>
        <Column
          field="sumilla.numero_sumilla"
          header="Número Sumilla"
          style="width: 5px"
        ></Column>

        <Column header="Nombre del responsable" style="width: 5px">
          <template #body="slotProps">
            {{
              slotProps.data.sumilla.responsable.per_nombres +
              "  " +
              slotProps.data.sumilla.responsable.per_apellidos
            }}
          </template>
        </Column>

        <Column
          bodyStyle="text-align:center"
          header="Estado Transferencia documental"
          style="width: 5px"
        >
          <template #body="slotProps">
            <FBadge v-if="slotProps.data.estado_transferencia === 'N'" status="critical"
              >EDICION</FBadge
            >
            <FBadge v-if="slotProps.data.estado_transferencia == 'S'" status="success"
              >ENVIADO</FBadge
            >
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

        <Column header="Archivo" style="width: 10px" bodyStyle="text-align: center;">
          <template #body="slotProps">
            <span v-if="slotProps.data.doc_archivo">
              <a :href="getDownloadUrl(slotProps.data.doc_archivo)" target="_blank">{{
                slotProps.data.nombre_archivo
              }}</a>
            </span>
          </template>
        </Column>

        <Column :exportable="false" style="min-width: 8rem">
          <template #body="slotProps">
            <FileUpload
              style="
                display: inline-block;
                padding: 10px 10px;
                background-color: #3b82f6;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 14px;
                cursor: pointer;
                text-align: center;
                text-decoration: none;
                transition: background-color 0.3s ease;
                width: max-content;
              "
              mode="basic"
              name="demo[]"
              url="./upload.php"
              accept=".pdf"
              chooseLabel="  Subir Documento"
              :maxFileSize="10000000"
              @select="onUpload($event, slotProps.data)"
              @before-upload="hol(slotProps.data)"
            />
          </template>
        </Column>

        <Column
          header="Estado documento"
          style="width: 5px"
          bodyStyle="text-align:center"
        >
          <template #body="slotProps">
            <FButton
              size="medium"
              :icon="MagnifyingGlassSolid"
              @click="prepareEstadoDocumentoModal(slotProps.data)"
            />
          </template>
        </Column>

        <Column style="width: 10px">
          <template #body="slotProps">
            <FButton
              size="medium"
              :icon="PencilSolid"
              @click="prepareEdit(slotProps.data.sumilla)"
              >Editar</FButton
            >
          </template>
        </Column>
        <Column style="width: 10px">
          <template #body="slotProps">
            <FButton
              size="medium"
              secondary
              :icon="TrashCanSolid"
              @click="handleChangeDeleteModal(slotProps.data.sumilla)"
              >Eliminar</FButton
            >
          </template>
        </Column>
      </DataTable>
    </FVerticalStack>

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
            {{ $t("app.sgadNuxt.sumilla.eliminar") }}:
            <br />
            <span style="font-weight: 700">{{ sumilla.numero_sumilla }}</span>
          </FText>
        </FVerticalStack>
      </FModalSection>
    </FModal>

    <!-- CREAR MODAL-->

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
      <FModalSection title-hidden style="text-align: center">
        <FVerticalStack gap="4">
          <Image src="/logo.png" alt="Image" width="250" />
          <FText
            as="h5"
            variant="headingMd"
            :font-weight="'semibold'"
            style="text-align: center"
          >
            {{ sede.dee_descripcion }}
          </FText>
          <FText as="h6" variant="headingMd" style="text-align: center">
            {{ $t("app.sgadNuxt.sumilla.title") }}
          </FText>
        </FVerticalStack>
      </FModalSection>

      <FModalSection>
        <FVerticalStack gap="4" align="center">
          <FHorizontalStack gap="4" align="center">
            <FText as="h5" variant="bodyMd" :font-weight="'semibold'">
              {{ $t("app.sgadNuxt.sumilla.fecha") }}
            </FText>
            <FText
              as="h5"
              variant="bodyMd"
              :font-weight="'regular'"
              v-if="action == persistAction.create"
            >
              {{ fechaSumillaView }}
            </FText>
            <FText
              as="h5"
              variant="bodyMd"
              :font-weight="'regular'"
              v-if="action == persistAction.edit"
            >
              {{ sumilla.fecha_sumilla }}
            </FText>

            <FText as="h5" variant="bodyMd" :font-weight="'semibold'">
              {{ $t("app.sgadNuxt.sumilla.hora") }}
            </FText>
            <FText as="h5" variant="bodyMd" :font-weight="'regular'">
              {{ sumilla.hora_sumilla }}
            </FText>
          </FHorizontalStack>

          <FHorizontalStack gap="4" align="center">
            <FText as="h5" variant="bodyMd" :font-weight="'semibold'">
              {{ $t("app.sgadNuxt.sumilla.nombreResponsable") }}
            </FText>
            <FText as="h5" variant="bodyMd" :font-weight="'semibold'">
              {{ sumilla.responsable.per_nombres }}
              {{ sumilla.responsable.per_apellidos }}
            </FText>
          </FHorizontalStack>
          <FHorizontalStack gap="4" align="center">
            <FText as="h5" variant="bodyMd" :font-weight="'semibold'">
              {{ $t("app.sgadNuxt.sumilla.numHojas") }}
            </FText>
            <FTextField
              type="number"
              v-model="numHojas"
              id="numeroIdentificacion"
              :error="numHojasError"
            />
          </FHorizontalStack>
        </FVerticalStack>
      </FModalSection>

      <FModalSection>
        <FVerticalStack gap="4" align="center">
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
            class="full-width-autocomplete"
          />
          <span v-if="v$.mensajero.$error" style="color: #c5280c"
            >* El campo mensajero es requerido</span
          >

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
                  v-model="bitacora.destinatario"
                  optionLabel="nombreCompleto"
                  :suggestions="filteredItems"
                  class="full-width-autocomplete"
                  @Complete="searchItem"
                  :style="[v$.destinatario.$error ? { 'border-color': 'red' } : {}]"
                />

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
                  Destino UPS:
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
            <FHorizontalStack gap="8" align="center">
              <FText
                id="fechaRecepcionlbl"
                as="h6"
                variant="bodyMd"
                fontWeight="semibold"
              >
                Fecha de recepción:
              </FText>
              <FText id="fechaRecepcion" as="h6" variant="bodyMd" fontWeight="regular">
                {{
                  sumilla?.fecha_sumilla
                    ? new Date(sumilla.fecha_sumilla).toLocaleDateString()
                    : ""
                }}
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
                    @update:="changeHour"
                  >
                  </Calendar>
                </div>
              </FHorizontalStack>
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
                class="full-width-autocomplete"
                v-model="bitacora.usr_emisor"
                optionLabel="nombreCompleto"
                :suggestions="filteredItems"
                @Complete="searchItem"
              />

              <FText id="personaRecibeLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                Persona que recibe:
              </FText>
              <AutoComplete
                @mouseover="
                  bitacora.usr_receptor != null
                    ? (mostrarUsrReceptor = true)
                    : (mostrarUsrReceptor = false)
                "
                @mouseleave="mostrarUsrReceptor = false"
                class="full-width-autocomplete"
                v-model="bitacora.usr_receptor"
                optionLabel="nombreCompleto"
                :suggestions="filteredItems"
                @Complete="searchItem"
              />
            </FVerticalStack>
          </FCard>
        </FVerticalStack>
      </FModalSection>
    </FModal>

    <!-- Enviar documento -->
    <FModal
      v-model="envioDocumentoDestinatarioModal"
      title=""
      title-hidden
      large
      :primaryAction="{
        content: 'Enviar Documento',
        onAction: onSubmitEnviarDocumento,
      }"
      :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeEnvioDocumento,
        },
      ]"
    >
      <FCard sectioned>
        <FVerticalStack gap="4">
          <FText
            id="envioDocumentoTitulo"
            as="h6"
            variant="bodyLg"
            fontWeight="semibold"
            alignment="center"
          >
            Está seguro que quiere enviar el documento con el numero de sumilla:
            <span style="font-weight: 700">{{ bitacora.sumilla.numero_sumilla }} </span>
            al destinatario
            <br />
            <span style="font-weight: 700"
              >{{ bitacora.destinatario.per_nombres }}
              {{ bitacora.destinatario.per_apellidos }} </span
            >:
          </FText>
        </FVerticalStack>
      </FCard>
    </FModal>

    <!-- TRANSFERENCIA DOCUMENTAL -->

    <FModal
      v-model="transferenciaModal"
      title=""
      title-hidden
      small
      :primaryAction="{
        content: 'Enviar Transferencia',
        onAction: onSubmitTransferencia,
        disabled: bitacorasListTransferenciaDocumental.length == 0,
      }"
      :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeTransferencia,
        },
      ]"
    >
      <FCard sectioned>
        <FVerticalStack gap="4">
          <FText
            id="transferenciaTituloLbl"
            as="h6"
            variant="headingLg"
            fontWeight="semibold"
          >
            Transferencia Documental:
          </FText>
          <FDivider />
        </FVerticalStack>
        <FCardSection>
          <FVerticalStack gap="4">
            <FText id="FechaInicioLbl" as="h6" variant="bodyMd" fontWeight="semibold">
              Fecha Inicio:
            </FText>
            <FTextField id="FechaInicioTxt" type="date" v-model="fechaInicial" />

            <FText id="FechaFinLbl" as="h6" variant="bodyMd" fontWeight="semibold">
              Fecha Fin:
            </FText>
            <FTextField
              id="FechaFinTxt"
              type="date"
              v-model="fechaFinal"
              :disabled="fechaInicial == ''"
            />

            <FText id="FechaFinLbl" as="h6" variant="bodyMd" fontWeight="semibold">
              Usuario gestión documental:
            </FText>
            <Dropdown
              v-model="userTransferenciaDocumental"
              :options="usersGestionDocumentalList"
              optionLabel="nombreCompleto"
              optionValue="codigo"
              placeholder="Seleccione"
              :filter="true"
              :disabled="fechaFinal == ''"
            />

            <FText
              id="mensajeTransferencia"
              as="h6"
              variant="bodyMd"
              fontWeight="semibold"
              color="critical"
              v-if="
                bitacorasListTransferenciaDocumental.length == 0 &&
                mensajeTransferencia != ''
              "
            >
              {{ mensajeTransferencia }}
            </FText>

            <DataTable
              v-if="bitacorasListTransferenciaDocumental.length != 0"
              :value="bitacorasListTransferenciaDocumental"
              :showGridlines="true"
              :stripedRows="true"
              tableStyle="min-width: 50rem"
              :paginator="true"
              :rows="10"
            >
              <Column field="codigo" header="No. de ref" style="width: 5px"></Column>
              <Column
                field="sumilla.numero_sumilla"
                header="Número Sumilla"
                style="width: 5px"
              ></Column>
              <Column header="Estado Transferencia" style="width: 5px">
                <template #body="slotProps">
                  <FBadge
                    v-if="slotProps.data.estado_transferencia === 'N'"
                    status="critical"
                    >EDICION</FBadge
                  >
                  <FBadge
                    v-if="slotProps.data.estado_transferencia == 'S'"
                    status="success"
                    >ENVIADO</FBadge
                  >
                </template>
              </Column>
            </DataTable>
          </FVerticalStack>
        </FCardSection>
      </FCard>
    </FModal>

    <!-- VER ESTADO DOCUMENTO -->
    <FModal v-model="estadoDocumentoModal" title="" title-hidden>
      <FCard sectioned>
        <FHorizontalStack gap="12" align="center">
          <FText
            id="estadoTitleLbl"
            as="h6"
            variant="headingLg"
            fontWeight="semibold"
            alignment="center"
          >
            Estado del documento:
          </FText>

          <FBadge status="critical">
            <FText
              id="estadoTitleLbl"
              as="h6"
              variant="headingSm"
              fontWeight="bold"
              alignment="center"
            >
              {{ eventoVigente.estado.estado_descripcion }}
            </FText>
          </FBadge>
        </FHorizontalStack>
      </FCard>
    </FModal>
  </FCardSection>
</template>
<script setup lang="ts">
import { Persona, Sumilla } from "models/Sumilla.model";
import { Bitacora } from "../../models/Bitacora.model";
import { useToast } from "primevue/usetoast";
import Calendar from "primevue/calendar";
import AutoComplete from "primevue/autocomplete";

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

const {
  sumillaList,
  usersGestionDocumentalList,
  bitacorasListTransferenciaDocumental,
  bitacorasList,
  sumilla,
  bitacora,
  sumillaEncontrada,
  data,
  //*Service
  saveSumilla,
  editSumilla,
  findSumillas,
  deleteSumilla,
  saveBitacora,
  findBitacoras,
  editBitacora,
  getEventoBitacoraService,
  eventoVigente,
  editEstadoEnvioBitacora,
  deleteBitacora,
  getUsrLogin,
  deleteBitacoraByNumSumilla,
  saveTransferencia,
  v$,
  receptorPersonaList,
  getSumillaByNumeroSumilla,
  getBitacoraByNumSumilla,
  getSedeByEmail,
  filtersSumillaBitacora,
  sede,
  findBitacorasByFechaTransferencia,
  mensajeTransferencia,
  persistAction,
  action,
  numHojas,
  fechaEntrega,
  resetNumHojas,
  numHojasError,
  prepareEdit,
  createModal,
} = useSumillaComposable();

const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const fechaSumillaView = ref();
const transferenciaModal = ref<boolean>(false);
const fechaInicial = ref<string>("");
const fechaFinal = ref<string>("");
const userTransferenciaDocumental = ref<number>();
const toast = useToast();
const { handleSubmit } = useForm();
const mostrarMensajero = ref<boolean>(false);
const filteredItems = ref<Persona[]>([]);
const mostrarDestinatario = ref<boolean>(false);
const mostrarEmisor = ref<boolean>(false);
const mostrarUsrReceptor = ref<boolean>(false);
const codigoSumillaDelete = ref<Number>(0);
const bitacoraSelected = ref<Bitacora>({} as Bitacora);

const prepareCreate = async () => {
  sede.value = await getSedeByEmail(data.value?.user?.email!);
  userLogin.value = await getUsrLogin(data.value?.user?.email!);
  action.value = persistAction.create;
  bitacora.value = {} as Bitacora;
  sumilla.value = {} as Sumilla;
  resetNumHojas();
  v$.value.$reset();
  sumilla.value.responsable = userLogin.value;
  sumilla.value.fecha_sumilla = new Date();
  sumilla.value.hora_sumilla = new Date().getHours() + ":" + new Date().getMinutes();
  bitacora.value.doc_archivo = null;
  fechaSumillaView.value = sumilla.value.fecha_sumilla.toLocaleDateString();
  handleChangeCreateModal();
};

const prepareTransferencia = async () => {
  userLogin.value = await getUsrLogin(data.value?.user?.email!);
  bitacorasListTransferenciaDocumental.value = [];
  fechaInicial.value = "";
  fechaFinal.value = "";
  userTransferenciaDocumental.value = 0;
  mensajeTransferencia.value = "";
  handleChangeTransferencia();
};

const onSubmited = handleSubmit(async (values) => {
  v$.value.$validate;
  if (!v$.value.$error) {
    if (action.value == persistAction.create) {
      sumilla.value.numero_hojas = parseInt(numHojas.value);
      sumilla.value.fecha_sumilla = new Date();
      sumilla.value.hora_sumilla = new Date().getHours() + ":" + new Date().getMinutes();
      sumilla.value = await saveSumilla(sumilla.value, data.value?.user?.email!);
    } else if (action.value == persistAction.edit) {
      sumilla.value.numero_hojas = Number(numHojas.value);
      await editSumilla(sumilla.value, sumilla.value.codigo!);
    }

    await findSumillas();

    if (action.value == persistAction.edit) {
      await editBitacora(bitacora.value, bitacora.value.codigo);
    } else {
      bitacora.value.receptor_documento = sumilla.value?.responsable!;
      bitacora.value.fecha_recepcion = sumilla.value?.fecha_sumilla!;
      bitacora.value.hora_recepcion = sumilla.value?.hora_sumilla!;
      bitacora.value.sumilla = sumilla.value;
      bitacora.value.estado_transferencia = "N";
      bitacora.value.adicionado = data.value?.user?.email!;
      await saveBitacora(bitacora.value);
    }

    await findBitacoras();
    createModal.value = !createModal.value;
    resetNumHojas();

    toast.add({
      severity: "success",
      summary: "Sumilla",
      detail: `Se ha guardado la sumilla correctamente`,
      life: 5000,
    });
  }
});

watch(
  () => fechaFinal.value,
  (newValue, oldValue) => {
    if (fechaFinal.value != "") {
      findBitacorasByFechaTransferencia(fechaInicial.value, fechaFinal.value);
    }
  }
);

const handleChangeDeleteModal = async (sum: Sumilla) => {
  deleteModal.value = !deleteModal.value;
  codigoSumillaDelete.value = sum.codigo!;
  sumilla.value = sum;
};

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

const onSubmitTransferencia = async () => {
  await saveTransferencia(
    fechaInicial.value,
    fechaFinal.value,
    userTransferenciaDocumental.value!,
    userLogin.value.codigo
  );
  handleChangeTransferencia();
  toast.add({
    severity: "success",
    summary: "Transferencia",
    detail: `Se ha realizado la transferencia correctamente`,
    life: 3000,
  });
  await findBitacoras();
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

const handleChangeTransferencia = () => {
  transferenciaModal.value = !transferenciaModal.value;
};

const handleChangeCreateModal = () => {
  createModal.value = !createModal.value;
};

const codigoBitacora = ref<Number>(0);
const deleteModal = ref<boolean>(false);

const changeDeleteModal = () => {
  deleteModal.value = !deleteModal.value;
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

const uploadedDocuments = ref<Bitacora[]>([]);

const hol = async (bitacoraParam: Bitacora) => {
  const existingDocument = uploadedDocuments.value.find(
    (document) => document.doc_archivo === bitacoraParam.doc_archivo
  );

  if (existingDocument) {
    const index = uploadedDocuments.value.indexOf(existingDocument);
    uploadedDocuments.value.splice(index, 1); // Eliminar documento existente del arreglo
  }
};

const onUpload = async ({ files }: any, bitacoraParam: Bitacora) => {
  // bitacora.value = await getBitacoraByNumSumilla(sumilla.numero_sumilla);
  bitacora.value = bitacoraParam;
  const file = files[0];
  const fileContent = await readFileAsByteArray(file);
  bitacora.value.doc_archivo = Array.from(fileContent);
  bitacora.value.nombre_archivo = files[0].name;
  console.log(bitacora.value.nombre_archivo);
  console.log(files[0]);
  const existingDocument = uploadedDocuments.value.find(
    (document) => document.doc_archivo === bitacora.value.doc_archivo
  );

  if (existingDocument) {
    const index = uploadedDocuments.value.indexOf(existingDocument);
    uploadedDocuments.value.splice(index, 1); // Eliminar documento existente del arreglo
    console.log(bitacora.value, "bitacora splice");
  }
  uploadedDocuments.value.push(bitacora.value!);
  await editBitacora(bitacora.value, bitacora.value.codigo);
  await findBitacoras();
};

const readFileAsByteArray = (file: File): Promise<Uint8Array> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => {
      const arrayBuffer = reader.result as ArrayBuffer;
      const byteArray = new Uint8Array(arrayBuffer);
      resolve(byteArray);
    };
    reader.onerror = (error) => reject(error);
    reader.readAsArrayBuffer(file);
  });
};

const getDownloadUrl = (byteArray: number[] | string) => {
  let uint8Array;

  if (typeof byteArray === "string") {
    // Convierte la cadena base64 a un Uint8Array
    const binaryString = window.atob(byteArray);
    const len = binaryString.length;
    uint8Array = new Uint8Array(len);
    for (let i = 0; i < len; i++) {
      uint8Array[i] = binaryString.charCodeAt(i);
    }
  } else {
    // Asume que ya es un Uint8Array
    uint8Array = new Uint8Array(byteArray);
  }

  const blob = new Blob([uint8Array], { type: "application/pdf" });
  return URL.createObjectURL(blob);
};

const prepareEnviarDocumento = async (sumillaDocumento: Sumilla) => {
  bitacora.value = await getBitacoraByNumSumilla(sumillaDocumento.numero_sumilla);

  if (bitacora.value.doc_archivo != null) {
    handleChangeEnvioDocumento();
  } else {
    toast.add({
      severity: "error",
      summary: "Documento",
      detail: `No está subido el documento digital`,
      life: 3000,
    });
  }
};

const uint8ArrayToFile = (byteArray: number[], fileName: string): File => {
  const uint8Array = new Uint8Array(byteArray);
  const blob = new Blob([uint8Array], { type: "application/pdf" });
  return new File([blob], fileName, { type: "application/pdf" });
};

const activePopover = ref<string | null>(null);
const togglePopoverActive = async (
  popover: string,
  active: boolean,
  bitacoraParam: Bitacora
) => {
  activePopover.value = active ? popover : null;
};

const estadoDocumentoModal = ref<boolean>(false);

const handleChangeEstadoDocumental = () => {
  estadoDocumentoModal.value = !estadoDocumentoModal.value;
};

const prepareEstadoDocumentoModal = async (bitacoraParam: Bitacora) => {
  eventoVigente.value = await getEventoBitacoraService(bitacoraParam.codigo);
  console.log(eventoVigente.value);
  handleChangeEstadoDocumental();
};
</script>

<style lang="css">
.p-autocomplete {
  height: 35px;
}

.p-calendar {
  height: 33px;
}

.p-datatable .p-paginator-top {
  border-width: 0 0 1px 0;
  border-radius: 0;
}
.p-datatable .p-paginator-bottom {
  border-width: 0 0 1px 0;
  border-radius: 0;
}
.p-datatable .p-datatable-header {
  background: #f8f9fa;
  color: #343a40;
  border: 1px solid #dee2e6;
  border-width: 1px 0 1px 0;
  padding: 1rem 1rem;
  font-weight: 700;
}
.p-datatable .p-datatable-footer {
  background: #f8f9fa;
  color: #343a40;
  border: 1px solid #dee2e6;
  border-width: 0 0 1px 0;
  padding: 1rem 1rem;
  font-weight: 700;
}
.p-datatable .p-datatable-thead > tr > th {
  text-align: left;
  padding: 1rem 1rem;
  border: 1px solid #dee2e6;
  border-width: 0 0 1px 0;
  font-weight: 700;
  color: #343a40;
  background: #f8f9fa;
  transition: box-shadow 0.2s;
}
.p-datatable .p-datatable-tfoot > tr > td {
  text-align: left;
  padding: 1rem 1rem;
  border: 1px solid #dee2e6;
  border-width: 0 0 1px 0;
  font-weight: 700;
  color: #343a40;
  background: #f8f9fa;
}
.p-datatable .p-sortable-column .p-sortable-column-icon {
  color: #343a40;
  margin-left: 0.5rem;
}
.p-datatable .p-sortable-column .p-sortable-column-badge {
  border-radius: 50%;
  height: 1.143rem;
  min-width: 1.143rem;
  line-height: 1.143rem;
  color: #4338ca;
  background: #eef2ff;
  margin-left: 0.5rem;
}
.p-datatable .p-sortable-column:not(.p-highlight):hover {
  background: #e9ecef;
  color: #343a40;
}
.p-datatable .p-sortable-column:not(.p-highlight):hover .p-sortable-column-icon {
  color: #343a40;
}
.p-datatable .p-sortable-column.p-highlight {
  background: #eef2ff;
  color: #4338ca;
}
.p-datatable .p-sortable-column.p-highlight .p-sortable-column-icon {
  color: #4338ca;
}
.p-datatable .p-sortable-column.p-highlight:hover {
  background: #eef2ff;
  color: #4338ca;
}
.p-datatable .p-sortable-column.p-highlight:hover .p-sortable-column-icon {
  color: #4338ca;
}
.p-datatable .p-sortable-column:focus {
  box-shadow: inset 0 0 0 0.15rem #c7d2fe;
  outline: 0 none;
}
.p-datatable .p-datatable-tbody > tr {
  background: #ffffff;
  color: #495057;
  transition: box-shadow 0.2s;
}
.p-datatable .p-datatable-tbody > tr > td {
  text-align: left;
  border: 1px solid #dee2e6;
  border-width: 0 0 1px 0;
  padding: 1rem 1rem;
}
.p-datatable .p-datatable-tbody > tr > td .p-row-toggler,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-init,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-save,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-cancel {
  width: 2rem;
  height: 2rem;
  color: #6c757d;
  border: 0 none;
  background: transparent;
  border-radius: 50%;
  transition: background-color 0.2s, color 0.2s, box-shadow 0.2s;
}
.p-datatable .p-datatable-tbody > tr > td .p-row-toggler:enabled:hover,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-init:enabled:hover,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-save:enabled:hover,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-cancel:enabled:hover {
  color: #343a40;
  border-color: transparent;
  background: #e9ecef;
}
.p-datatable .p-datatable-tbody > tr > td .p-row-toggler:focus,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-init:focus,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-save:focus,
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-cancel:focus {
  outline: 0 none;
  outline-offset: 0;
  box-shadow: 0 0 0 0.2rem #c7d2fe;
}
.p-datatable .p-datatable-tbody > tr > td .p-row-editor-save {
  margin-right: 0.5rem;
}
.p-datatable .p-datatable-tbody > tr > td > .p-column-title {
  font-weight: 700;
}
.p-datatable .p-datatable-tbody > tr:focus {
  outline: 0.15rem solid #c7d2fe;
  outline-offset: -0.15rem;
}
.p-datatable .p-datatable-tbody > tr.p-highlight {
  background: #eef2ff;
  color: #4338ca;
}
.p-datatable .p-datatable-tbody > tr.p-datatable-dragpoint-top > td {
  box-shadow: inset 0 2px 0 0 #eef2ff;
}
.p-datatable .p-datatable-tbody > tr.p-datatable-dragpoint-bottom > td {
  box-shadow: inset 0 -2px 0 0 #eef2ff;
}
.p-datatable.p-datatable-hoverable-rows .p-datatable-tbody > tr:not(.p-highlight):hover {
  background: #e9ecef;
  color: #495057;
}
.p-datatable .p-column-resizer-helper {
  background: #6366f1;
}
.p-datatable .p-datatable-scrollable-header,
.p-datatable .p-datatable-scrollable-footer {
  background: #f8f9fa;
}
.p-datatable.p-datatable-scrollable
  > .p-datatable-wrapper
  > .p-datatable-table
  > .p-datatable-thead,
.p-datatable.p-datatable-scrollable
  > .p-datatable-wrapper
  > .p-datatable-table
  > .p-datatable-tfoot,
.p-datatable.p-datatable-scrollable
  > .p-datatable-wrapper
  > .p-virtualscroller
  > .p-datatable-table
  > .p-datatable-thead,
.p-datatable.p-datatable-scrollable
  > .p-datatable-wrapper
  > .p-virtualscroller
  > .p-datatable-table
  > .p-datatable-tfoot {
  background-color: #f8f9fa;
}
.p-datatable .p-datatable-loading-icon {
  font-size: 2rem;
}
.p-datatable.p-datatable-gridlines .p-datatable-header {
  border-width: 1px 1px 0 1px;
}
.p-datatable.p-datatable-gridlines .p-datatable-footer {
  border-width: 0 1px 1px 1px;
}
.p-datatable.p-datatable-gridlines .p-paginator-top {
  border-width: 0 1px 0 1px;
}
.p-datatable.p-datatable-gridlines .p-paginator-bottom {
  border-width: 0 1px 1px 1px;
}
.p-datatable.p-datatable-gridlines .p-datatable-thead > tr > th {
  border-width: 1px 1px 1px 1px;
}
.p-datatable.p-datatable-gridlines .p-datatable-tbody > tr > td {
  border-width: 1px;
}
.p-datatable.p-datatable-gridlines .p-datatable-tfoot > tr > td {
  border-width: 1px;
}
.p-datatable.p-datatable-gridlines.p-datatable-scrollable
  .p-datatable-thead
  > tr
  > th
  + th {
  border-left-width: 0;
}
.p-datatable.p-datatable-gridlines.p-datatable-scrollable
  .p-datatable-tbody
  > tr
  > td
  + td {
  border-left-width: 0;
}
.p-datatable.p-datatable-gridlines.p-datatable-scrollable
  .p-datatable-tbody
  > tr
  + tr
  > td,
.p-datatable.p-datatable-gridlines.p-datatable-scrollable
  .p-datatable-tbody
  > tr:first-child
  > td {
  border-top-width: 0;
}
.p-datatable.p-datatable-gridlines.p-datatable-scrollable
  .p-datatable-tfoot
  > tr
  > td
  + td {
  border-left-width: 0;
}
.p-datatable.p-datatable-striped .p-datatable-tbody > tr:nth-child(even) {
  background: #fcfcfc;
}
.p-datatable.p-datatable-striped .p-datatable-tbody > tr:nth-child(even).p-highlight {
  background: #eef2ff;
  color: #4338ca;
}
.p-datatable.p-datatable-striped
  .p-datatable-tbody
  > tr:nth-child(even).p-highlight
  .p-row-toggler {
  color: #4338ca;
}
.p-datatable.p-datatable-striped
  .p-datatable-tbody
  > tr:nth-child(even).p-highlight
  .p-row-toggler:hover {
  color: #4338ca;
}
.p-datatable.p-datatable-sm .p-datatable-header {
  padding: 0.5rem 0.5rem;
}
.p-datatable.p-datatable-sm .p-datatable-thead > tr > th {
  padding: 0.5rem 0.5rem;
}
.p-datatable.p-datatable-sm .p-datatable-tbody > tr > td {
  padding: 0.5rem 0.5rem;
}
.p-datatable.p-datatable-sm .p-datatable-tfoot > tr > td {
  padding: 0.5rem 0.5rem;
}
.p-datatable.p-datatable-sm .p-datatable-footer {
  padding: 0.5rem 0.5rem;
}
.p-datatable.p-datatable-lg .p-datatable-header {
  padding: 1.25rem 1.25rem;
}
.p-datatable.p-datatable-lg .p-datatable-thead > tr > th {
  padding: 1.25rem 1.25rem;
}
.p-datatable.p-datatable-lg .p-datatable-tbody > tr > td {
  padding: 1.25rem 1.25rem;
}
.p-datatable.p-datatable-lg .p-datatable-tfoot > tr > td {
  padding: 1.25rem 1.25rem;
}
.p-datatable.p-datatable-lg .p-datatable-footer {
  padding: 1.25rem 1.25rem;
}
</style>
