<template>
  <FCard sectioned>
    <FTabs :tabs="tabs" v-model:selected="selected" fitted>
      <FFormLayout v-if="selected == 0">
        <FLayoutSection>
          <FCard sectioned>
            <FVerticalStack gap="8">
              <FText id="buscarSumilla" as="h1" variant="headingMd" font-weight="semibold"
                >SUMILLAS</FText
              >

              <DataTable
                v-model:filters="filtersSumillaBitacora"
                :value="sumillaList"
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
                <Column field="codigo" header="Codigo Sumilla" style="width: 2px">
                </Column>
                <Column field="numero_sumilla" header="Número Sumilla" style="width: 5px">
                </Column>
                <Column
                  field="fecha_sumilla"
                  header="Fecha sumilla"
                  style="width: 5px"
                ></Column>
                <Column
                  field="hora_sumilla"
                  header="Hora sumilla"
                  style="width: 5px"
                ></Column>

                <Column header="Nombre del responsable" style="width: 5px">
                  <template #body="slotProps">
                    {{
                      slotProps.data.responsable.per_nombres +
                      "  " +
                      slotProps.data.responsable.per_apellidos
                    }}
                  </template>
                </Column>
                <Column header="Sede" style="width: 5px">
                  <template #body="slotProps">
                    {{ Sede[slotProps.data.sum_sede] }}
                  </template>
                </Column>
                <Column
                  field="numero_hojas"
                  header="No. Hojas recibidas"
                  style="width: 10px"
                  headerStyle="text-align: center;"
                  bodyStyle="text-align: center;"
                >
                </Column>

                <Column style="width: 10px">
                  <template #body="slotProps">
                    <FButton
                      size="slim"
                      :icon="PencilSolid"
                      @click="prepareEdit(slotProps.data)"
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
                      >Eliminar</FButton
                    >
                  </template>
                </Column>
              </DataTable>
            </FVerticalStack>

            <!-- ELIMINAR MODAL-->

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
                  <FText
                    id="remitenteNombreLbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                  >
                    Nombres remitente:
                  </FText>
                  <FTextField
                    id="remitenteNombre"
                    v-model="bitacora.nombres_remitente"
                    :error="v$?.nombres_remitente.$error"
                    :label="v$?.nombres_remitente.$error ? 'Este campo es requerido' : ''"
                  />
                  <FText
                    id="remitenteApellidoLbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                  >
                    Apellidos remitente:
                  </FText>
                  <FTextField
                    id="remitenteApellido"
                    v-model="bitacora.apellidos_remitente"
                    :error="v$?.apellidos_remitente.$error"
                    :label="
                      v$?.apellidos_remitente.$error ? 'Este campo es requerido' : ''
                    "
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

                  <FText
                    id="numeroGuiaLbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                  >
                    Número de guia:
                  </FText>
                  <FTextField id="numeroGuia" v-model="bitacora.numero_guia" />

                  <FText
                    id="observacionesLbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                  >
                    Observaciones:
                  </FText>
                  <FTextField id="observaciones" v-model="bitacora.observaciones" />

                  <FCard
                    sectioned
                    style="box-shadow: 3px 3px 10px 0px rgba(0, 0, 0, 0.3)"
                  >
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
                          :style="[
                            v$.destinatario.$error ? { 'border-color': 'red' } : {},
                          ]"
                        />

                        <FText
                          id="asuntolbl"
                          as="h6"
                          variant="bodyMd"
                          fontWeight="semibold"
                        >
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
                          :label="
                            v$?.lugar_destino.$error ? 'Este campo es requerido' : ''
                          "
                        />
                      </FVerticalStack>
                    </FCardSection>
                  </FCard>

                  <FCard
                    sectioned
                    style="box-shadow: 3px 3px 10px 0px rgba(0, 0, 0, 0.3)"
                  >
                    <FHorizontalStack gap="8" align="center">
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
                        {{
                          sumilla?.fecha_sumilla
                            ? new Date(sumilla.fecha_sumilla).toLocaleDateString()
                            : ""
                        }}
                      </FText>

                      <FText
                        id="horaRecepcionLbl"
                        as="h6"
                        variant="bodyMd"
                        fontWeight="semibold"
                      >
                        Hora de recepción:
                      </FText>
                      <FText
                        id="horaRecepcion"
                        as="h6"
                        variant="bodyMd"
                        fontWeight="regular"
                      >
                        {{ sumilla?.hora_sumilla }}
                      </FText>
                    </FHorizontalStack>
                  </FCard>

                  <FCard
                    sectioned
                    style="box-shadow: 3px 3px 10px 0px rgba(0, 0, 0, 0.3)"
                  >
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
                        <FTextField
                          id="fechaEntrega"
                          type="date"
                          v-model="fechaEntrega"
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

            <!-- MODAL TRANSFERENCIA DOCUMENTAL -->

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
                    <FText
                      id="FechaInicioLbl"
                      as="h6"
                      variant="bodyMd"
                      fontWeight="semibold"
                    >
                      Fecha Inicio:
                    </FText>
                    <FTextField id="FechaInicioTxt" type="date" v-model="fechaInicial" />

                    <FText
                      id="FechaFinLbl"
                      as="h6"
                      variant="bodyMd"
                      fontWeight="semibold"
                    >
                      Fecha Fin:
                    </FText>
                    <FTextField
                      id="FechaFinTxt"
                      type="date"
                      v-model="fechaFinal"
                      :disabled="fechaInicial == ''"
                    />

                    <FText
                      id="FechaFinLbl"
                      as="h6"
                      variant="bodyMd"
                      fontWeight="semibold"
                    >
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
                      <Column
                        field="codigo"
                        header="No. de ref"
                        style="width: 5px"
                      ></Column>
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
          </FCard>
        </FLayoutSection>

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
} from "@ups-dev/freya-icons";
import Image from "primevue/image";
import { Persona, Sumilla } from "../../models/Sumilla.model";
import { useToast } from "primevue/usetoast";
import Calendar from "primevue/calendar";
import AutoComplete from "primevue/autocomplete";
import { Bitacora } from "../../models/Bitacora.model";

const toast = useToast();
const { handleSubmit } = useForm();
const nom = ref("");
const mostrarDestinatario = ref<boolean>(false);
const mostrarMensajero = ref<boolean>(false);
const mostrarEmisor = ref<boolean>(false);
const mostrarUsrReceptor = ref<boolean>(false);
const fechaEntrega = ref<string>("");
const fechaSumillaView = ref();
const userTransferenciaDocumental = ref<number>();

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
} = useSumillaComposable();

//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const createModal = ref<boolean>(false);
const deleteModal = ref<boolean>(false);
const documentModal = ref<boolean>(false);
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

enum persistAction {
  create,
  edit,
  view,
}
const action = ref();

const handleChangeCreateModal = () => {
  createModal.value = !createModal.value;
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

const prepareEdit = async (sumillaParam: Sumilla) => {
  action.value = persistAction.edit;
  sumilla.value = { ...sumillaParam };
  console.log(sumilla.value.numero_hojas, "sumilla.value.numero_hojas");
  numHojas.value =
    sumilla.value.numero_hojas != null ? sumilla.value.numero_hojas.toString() : "";

  bitacora.value = await getBitacoraByNumSumilla(sumilla.value.numero_sumilla);
  fechaEntrega.value =
    bitacora.value.fecha_entrega != null ? bitacora.value.fecha_entrega.toString() : "";

  bitacora.value.mensajero.nombreCompleto = bitacora.value.mensajero.per_apellidos
    .concat(" ")
    .concat(bitacora.value.mensajero.per_nombres);
  bitacora.value.destinatario.nombreCompleto = bitacora.value.destinatario.per_apellidos
    .concat(" ")
    .concat(bitacora.value.destinatario.per_nombres);

  if (bitacora.value.usr_emisor != null) {
    bitacora.value.usr_emisor.nombreCompleto = bitacora.value.usr_emisor.per_apellidos
      .concat(" ")
      .concat(bitacora.value.usr_emisor.per_nombres);
  }

  if (bitacora.value.usr_receptor != null) {
    bitacora.value.usr_receptor.nombreCompleto = bitacora.value.usr_receptor.per_apellidos
      .concat(" ")
      .concat(bitacora.value.usr_receptor.per_nombres);
  }

  createModal.value = !createModal.value;
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

const handleChangeTransferencia = () => {
  transferenciaModal.value = !transferenciaModal.value;
};

const prepareTransferencia = () => {
  bitacorasListTransferenciaDocumental.value = [];
  fechaInicial.value = "";
  fechaFinal.value = "";
  userTransferenciaDocumental.value = 0;
  mensajeTransferencia.value = "";
  handleChangeTransferencia();
};

const onSubmitTransferencia = async () => {
  await saveTransferencia(
    fechaInicial.value,
    fechaFinal.value,
    userTransferenciaDocumental.value!
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
