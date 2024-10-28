<template>
  <FCardSection>
    <FVerticalStack gap="8">
      <FText as="h5" variant="headingLg" color="subdued" font-weight="regular">Documentación física</FText>
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
                    :disabled="bitacoraSelected == null || disabledEnviarDocumento"
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
              (slotProps.data.sumilla.responsable.per_nombres || '') + " " + (slotProps.data.sumilla.responsable.per_apellidos || '')
            }}
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
              (slotProps.data.destinatario.per_nombres || '') + " " + (slotProps.data.destinatario.per_apellidos || '')
            }}
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

        <Column style="width: 10px">
          <template #body="slotProps">
            <FButton
                size="medium"
                :icon="PencilSolid"
                @click="prepareEdit(slotProps.data.sumilla, persistAction.view)"
            >Ver</FButton
            >
          </template>
        </Column>

        <Column style="width: 10px">
          <template #body="slotProps">
            <FButton
                size="medium"
                :icon="PencilSolid"
                @click="prepareEdit(slotProps.data.sumilla, persistAction.edit)"
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
                @click="handleChangeDeleteModal(slotProps.data)"
            >Eliminar</FButton
            >
          </template>
        </Column>
      </DataTable>

      <BitacoraDocumentosExternosListDocumentosExternos></BitacoraDocumentosExternosListDocumentosExternos>
      
    </FVerticalStack>

    <!-- DELETE MODAL-->

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
        disabled: action == persistAction.view,
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
                v-if="action == persistAction.edit || action == persistAction.view"
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
                id="numHojas"
                :error="numHojasError"
                :disabled="action == persistAction.view"
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
              v-model="nombres_remitente"
              :error="nombres_remitenteError"
              :disabled="action == persistAction.view"
          />
          <FText id="remitenteApellidoLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Apellidos remitente:
          </FText>
          <FTextField
              id="remitenteApellido"
              v-model="apellidos_remitente"
              :error="apellidos_remitenteError"
              :disabled="action == persistAction.view"
          />

          <FText id="mensajeroLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Mensajero:
          </FText>
          <FBox
              background="bg"
              padding="0"
              borderWidth="1"
              borderColor="border"
              style="border-radius: 5px"
              :style="[mensajeroError != null ? { 'border-color': '#FF6767' } : {}]"
          >
            <AutoComplete
                v-model="mensajero"
                optionLabel="nombreCompleto"
                :suggestions="filteredItems"
                @Complete="searchItem"
                class="full-width-autocomplete"
                :disabled="action == persistAction.view"
            />
          </FBox>

          <FText id="numeroGuiaLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Número de guia:
          </FText>
          <FTextField
              id="numeroGuia"
              v-model="bitacora.numero_guia"
              :disabled="action == persistAction.view"
          />

          <FText id="observacionesLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Observaciones:
          </FText>
          <FTextField
              id="observaciones"
              v-model="bitacora.observaciones"
              :disabled="action == persistAction.view"
          />

          <FDivider border-width="5" border-color="border-inverse"/>
           <FVerticalStack gap="4">
                <FText
                    id="destinatarioLbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                >
                  Destinatario:
                </FText>
                <FBox
                    background="bg"
                    padding="0"
                    borderWidth="1"
                    borderColor="border"
                    style="border-radius: 5px"
                    :style="[destinatarioError != null ? { 'border-color': '#FF6767' } : {}]"
                >
                  <AutoComplete
                      v-model="destinatario"
                      optionLabel="nombreCompleto"
                      :disabled="action == persistAction.view"
                      :suggestions="filteredItems"
                      class="full-width-autocomplete"
                      @Complete="searchItem"
                  />
                </FBox>

                <FText id="asuntolbl" as="h6" variant="bodyMd" fontWeight="semibold">
                  Asunto:
                </FText>
                <FTextField
                    id="asunto"
                    v-model="asunto"
                    :error="asuntoError"
                    :disabled="action == persistAction.view"
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
                    v-model="lugar_destino"
                    :error="lugar_destinoError"
                    :disabled="action == persistAction.view"
                />

                <FVerticalStack gap="4">
                  <FileUpload
                      ref="fileUpload"
                      name="file"
                      accept=".pdf"
                      multiple
                      class="f"
                      :maxFileSize="10485760"
                      :chooseLabel="'Seleccionar archivos'"
                      :onSelect="handleFileSelect"
                      :disabled="action == persistAction.view"
                  />
                  <div v-if="documentosBitacoraList.length > 0">
                    <h3>Documentos guardados:</h3>
                    <ul>
                      <li
                          v-for="(documento, index) in documentosBitacoraList"
                          :key="documento.doc_nombre_archivo"
                      >
                        <a
                            :href="
                            createDownloadLink(
                              documento.doc_archivo,
                              documento.doc_nombre_archivo
                            )
                          "
                            :download="documento.doc_nombre_archivo"
                        >
                          {{ documento.doc_nombre_archivo }}
                        </a>
                        <FButton
                            plain
                            destructive
                            size="micro"
                            :icon="TrashCanSolid"
                            @click="deleteFile(index)"
                            style="margin-left: 2rem; margin-top: 1rem; align-items: end"
                            :disabled="action == persistAction.view"
                        >Eliminar</FButton
                        >
                        <FDivider :border-width="'4'" />
                      </li>
                    </ul>
                  </div>
                </FVerticalStack>
              </FVerticalStack>

          <FDivider border-width="5" border-color="border-inverse" />
            <FHorizontalStack gap="12">
              <FText
                  id="fechaRecepcionlbl"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
              >
                Fecha de recepción:
              </FText>
              <FText
                  id="fechaRecepcion"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="regular"
              >
              {{ (action == persistAction.view) ? sumilla?.fecha_sumilla : ""  }}
              {{ (action == persistAction.create) ? new Date(sumilla?.fecha_sumilla).toLocaleDateString() : '' }}
              {{ sumilla?.hora_sumilla }}
              </FText>
            </FHorizontalStack>

            <FVerticalStack gap="4">
                <FText
                    id="fechaRecepcionlbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                >
                  Fecha de entrega:
                </FText>
                <FTextField
                    id="fechaEntrega"
                    type="date"
                    v-model="fechaEntrega"
                    :disabled="action == persistAction.view"
                />

                <FText
                    id="horaEntregaLbl"
                    for="calendar-timeonly"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                >
                  Hora de entrega:
                </FText>

                <FTextField
                    id="horaEntrega"
                    type="time"
                    v-model="bitacora.hora_entrega"
                    :disabled="action == persistAction.view"
                />


              <FText
                  id="personaEntregaLbl"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
              >
                Persona que entrega:
              </FText>

              <AutoComplete
                  class="full-width-autocomplete"
                  v-model="bitacora.usr_emisor"
                  optionLabel="nombreCompleto"
                  :suggestions="filteredItems"
                  @Complete="searchItem"
                  :disabled="action == persistAction.view"
              />

              <FText id="personaRecibeLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                Persona que recibe:
              </FText>
              <AutoComplete
                  class="full-width-autocomplete"
                  v-model="bitacora.usr_receptor"
                  optionLabel="nombreCompleto"
                  :suggestions="filteredItems"
                  @Complete="searchItem"
                  :disabled="action == persistAction.view"
              />
            </FVerticalStack>
        </FVerticalStack>
      </FModalSection>
    </FModal>

    <!-- Enviar documento modal -->
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
        large
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
  <FToast v-model="mostrarMsgError" :content=mensajeToast error :duration="5000" />
  <FToast v-model="mostrarMsgCorrecto" :content=mensajeToast  :duration="5000" />

</template>
<script setup lang="ts">
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
  FileSolid,
} from "@ups-dev/freya-icons";
import {useSumillaComposable} from "~/composables/sumillas/useSumillaComposable";
import type {Persona, Sumilla} from "~/models/Sumilla.model";
import type {Bitacora} from "~/models/Bitacora.model";
import type {Estado} from "~/models/Estado.model";
import type {EventoBitacora} from "~/models/EventoBitacora.model";
import type {DocumentoBitacora} from "~/models/DocumentoBitacora.model";

const {
  usersGestionDocumentalList,
  bitacorasListTransferenciaDocumental,
  bitacorasList,
  sumilla,
  bitacora,
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
  deleteDocumentosByBitCodigo,
  getDocumentosByBitCodigo,
  eventoVigente,
  getUsrLogin,
  deleteBitacoraByNumSumilla,
  saveTransferencia,
  // v$,
  receptorPersonaList,
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
  saveEventoBitacora,
  eventoBitacora,
  deleteEventoBitacora,
  documentObj,
  saveDocumentoBitacora,
  files,
  documentosBitacoraList,
  handleSubmit,
  nombres_remitente,
  nombres_remitenteError,
  resetnombres_remitente,
  apellidos_remitente,
  apellidos_remitenteError,
  resetapellidos_remitente,
  lugar_destino,
  lugar_destinoError,
  resetlugar_destino,
  destinatario,
  destinatarioError,
  resetdestinatario,
  mensajero,
  mensajeroError,
  resetmensajero,
  asunto,
  asuntoError,
  resetasunto,
  getPersonasByFilterName,
  sendEmailDocFisicaBitacora
} = useSumillaComposable();

const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const fechaSumillaView = ref();
const transferenciaModal = ref<boolean>(false);
const fechaInicial = ref<string>("");
const fechaFinal = ref<string>("");
const userTransferenciaDocumental = ref<number>();
const filteredItems = ref<Persona[]>([]);
const codigoSumillaDelete = ref<Number>(0);
const bitacoraSelected = ref<Bitacora>({} as Bitacora );
const disabledEnviarDocumento = ref<boolean>(true);
const estadoDocumentoModal = ref<boolean>(false);

const mostrarMsgCorrecto = ref<boolean>(false);
const mostrarMsgError = ref<boolean>(false);
const mensajeToast = ref<string>('');


const prepareCreate = async () => {
  sede.value = await getSedeByEmail(data.value?.user?.email!);
  userLogin.value = await getUsrLogin(data.value?.user?.email!);
  action.value = persistAction.create;
  bitacora.value = {} as Bitacora;
  sumilla.value = {} as Sumilla;
  files.value = [];
  documentosBitacoraList.value = [];
  resetNumHojas();
  resetnombres_remitente();
  resetapellidos_remitente();
  resetasunto();
  resetmensajero();
  resetlugar_destino()
  resetdestinatario();
  // v$.value.$reset();
  // resetForm();
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

const handleFileSelect = (event: any) => {
  files.value = event.files;
};

const completeObjectBitacora = () =>{
  bitacora.value.nombres_remitente = nombres_remitente.value;
  bitacora.value.apellidos_remitente = apellidos_remitente.value;
  bitacora.value.mensajero = mensajero.value;
  bitacora.value.destinatario = destinatario.value;
  bitacora.value.asunto = asunto.value;
  bitacora.value.lugar_destino = lugar_destino.value;
}

const onSubmited = handleSubmit(async (values:any) => {
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
      completeObjectBitacora();
      await editBitacora(bitacora.value, bitacora.value.codigo);
      if (files.value.length > 0) {
        saveDocumentos();
      }
    } else {
      completeObjectBitacora();
      bitacora.value.receptor_documento = sumilla.value?.responsable!;
      bitacora.value.fecha_recepcion = sumilla.value?.fecha_sumilla!;
      bitacora.value.hora_recepcion = sumilla.value?.hora_sumilla!;
      bitacora.value.sumilla = sumilla.value;
      bitacora.value.estado_transferencia = "N";
      bitacora.value.adicionado = data.value?.user?.email!;
      bitacora.value = await saveBitacora(bitacora.value);
      await saveDocumentos();
    }

    await findBitacoras();
    createModal.value = !createModal.value;
    resetNumHojas();
    mostrarMsgCorrecto.value = true;
    mensajeToast.value = "La sumilla se guardó correctamente";
});

const saveDocumentos = async () => {
  if (files.value.length > 0) {
    try {
      // Procesar cada archivo
      for (const file of files.value) {
        const reader = new FileReader();
        reader.onload = async (e) => {
          try {
            const result = e.target!.result as ArrayBuffer;
            const byteArray = new Uint8Array(result);

            // Convertir Uint8Array a byte[]
            const byteArrayJava = Array.from(byteArray);
            documentObj.value.bitacora = {} as Bitacora;
            documentObj.value.codigo = 0;
            documentObj.value.doc_archivo = byteArrayJava;
            documentObj.value.doc_nombre_archivo = file.name;
            documentObj.value.bitacora.codigo = bitacora.value.codigo;
            documentObj.value.adicionado = bitacora.value.adicionado;

            await saveDocumentoBitacora(documentObj.value);
          } catch (error) {
            console.error("Error processing file:", error);
            // Puedes manejar el error aquí si es necesario
          }
        };
        reader.onerror = (error) => {
          console.error("Error reading file:", error);
        };
        reader.readAsArrayBuffer(file);
      }
    } catch (error) {
      console.error("Error processing files:", error);
      // Puedes agregar más lógica de manejo de errores aquí si es necesario
    }
  }
};

watch(
    () => fechaFinal.value,
    () => {
      if (fechaFinal.value != "") {
        findBitacorasByFechaTransferencia(
            fechaInicial.value,
            fechaFinal.value,
            userLogin.value.codigo
        );
      }
    }
);
const handleChangeDeleteModal = async (bitacoraParam: Bitacora) => {
  eventoBitacora.value = await getEventoBitacoraService(bitacoraParam.codigo);
  if (
      eventoBitacora.value.estado.codigo != 2 ||
      bitacoraParam.estado_transferencia == "S"
  ) {
    mostrarMsgError.value = true;
    mensajeToast.value = "No se puede eliminar, el documento fue enviado al destinatario";
  } else {
    deleteModal.value = !deleteModal.value;
    codigoSumillaDelete.value = bitacoraParam.sumilla.codigo!;
    sumilla.value = bitacoraParam.sumilla;
    bitacora.value = bitacoraParam;
  }
};

const searchItem = async(event: any) => {
  const query = event.query.toLowerCase();
  receptorPersonaList.value = await getPersonasByFilterName(query);
  filteredItems.value = receptorPersonaList.value.filter(
      (item:any) =>
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
  mostrarMsgCorrecto.value = true;
  mensajeToast.value = "La transferencia se realizó correctamente";
  await findBitacoras();
};

const envioDocumentoDestinatarioModal = ref<boolean>(false);
const handleChangeEnvioDocumento = () => {
  envioDocumentoDestinatarioModal.value = !envioDocumentoDestinatarioModal.value;
};

const onSubmitEnviarDocumento = async () => {
  try {
    eventoBitacora.value.estado = {} as Estado;
    eventoBitacora.value.per_codigo_responsable = {} as Persona;

    eventoBitacora.value.fecha = new Date();
    eventoBitacora.value.vigencia = "S";
    eventoBitacora.value.bitacora = bitacora.value;
    eventoBitacora.value.estado.codigo = 5;
    eventoBitacora.value.adicionado = data.value?.user?.email!;
    eventoBitacora.value.per_codigo_responsable.codigo =
        bitacora.value.receptor_documento.codigo;
    await saveEventoBitacora(eventoBitacora.value);
    mostrarMsgCorrecto.value = true;
    mensajeToast.value = "El documento se envío correctamente";
    await sendEmailDocFisicaBitacora(eventoBitacora.value.bitacora);
    await findBitacoras();
    handleChangeEnvioDocumento();
  } catch (error) {
    console.log(error);
  }
  
};

const handleChangeTransferencia = () => {
  transferenciaModal.value = !transferenciaModal.value;
};

const handleChangeCreateModal = () => {
  createModal.value = !createModal.value;
};

const deleteModal = ref<boolean>(false);

const changeDeleteModal = () => {
  deleteModal.value = !deleteModal.value;
};

const confirmDelete = async () => {
  try {
    await deleteDocumentosByBitCodigo(bitacora.value.codigo);
    await deleteEventoBitacora(eventoBitacora.value.bitacora.codigo);
    await deleteBitacoraByNumSumilla(sumilla.value.codigo!);
    await deleteSumilla(sumilla.value.codigo!);
    await findBitacoras();
    mostrarMsgCorrecto.value = true;
    mensajeToast.value = "Se ha eliminado la Bitácora correctamente";
    changeDeleteModal();
  } catch (error) {}
};

const prepareEnviarDocumento = async (sumillaDocumento: Sumilla) => {
  bitacora.value = await getBitacoraByNumSumilla(sumillaDocumento.numero_sumilla);
  documentosBitacoraList.value = await getDocumentosByBitCodigo(bitacora.value.codigo);

  handleChangeEnvioDocumento();

  // if (documentosBitacoraList.value.length > 0) {
  //   handleChangeEnvioDocumento();
  // } else {
  //   mostrarMsgError.value = true;
  //   mensajeToast.value = 'No está subido el documento digital';
  // }
};

watch(
    () => bitacoraSelected.value,
    async () => {
      if(bitacoraSelected.value != null){
        const evento: EventoBitacora = await getEventoBitacoraService(
            bitacoraSelected.value.codigo
        );
        if (evento.estado.codigo != 2 && bitacoraSelected.value.destinatario != null) {
          disabledEnviarDocumento.value = true;
        } else {
          disabledEnviarDocumento.value = false;
        }
      }
    }
);

const handleChangeEstadoDocumental = () => {
  estadoDocumentoModal.value = !estadoDocumentoModal.value;
};

const prepareEstadoDocumentoModal = async (bitacoraParam: Bitacora) => {
  eventoVigente.value = await getEventoBitacoraService(bitacoraParam.codigo);
  handleChangeEstadoDocumental();
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

const deleteFile = async (index: any) => {
  const documento: DocumentoBitacora = documentosBitacoraList.value[index];
  try {
    await deleteDocumentosByBitCodigo(documento.bitacora.codigo); // Asegúrate de que tu API soporte esto
    documentosBitacoraList.value.splice(index, 1);
  } catch (error) {
    console.error("Error eliminando el archivo:", error);
  }
};
</script>

<style lang="css">

.calendar .p-datepicker-trigger .p-datepicker-icon {
  background-image: url('/calendar.png');
  background-size: contain;
  background-repeat: no-repeat;
  width: 1.5rem; /* Tamaño del icono */
  height: 1.5rem;
  margin-right: 0.5rem; /* Espacio entre el icono y el texto */
}

/* Ajuste de estilo para el calendario */
.calendar .p-datepicker {
  /* Estilos adicionales según sea necesario */
}
.p-autocomplete {
  height: 35px;
}

</style>
