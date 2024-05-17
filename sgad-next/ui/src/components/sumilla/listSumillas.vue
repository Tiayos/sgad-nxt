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
                </template>
                <Column field="codigo" header="No. sumilla" style="width: 5px"> </Column>
                <Column field="numero_sumilla" header="No. Trámite" style="width: 5px">
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

            <!-- ELIMINAR -->

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

            <!-- CREAR -->

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
                    --CAMPUS--
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
                      {{ sumilla.fecha_sumilla.toLocaleDateString() }}
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

                  <FHorizontalStack gap="4">
                    <FText
                      id="mensajeroLbl"
                      as="h6"
                      variant="bodyMd"
                      fontWeight="semibold"
                    >
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
                            :style="[
                              v$.destinatario.$error ? { 'border-color': 'red' } : {},
                            ]"
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
                          Lugar de destino:
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
                    <FHorizontalStack gap="32">
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
                        {{ sumilla?.fecha_sumilla }}
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
                      <FText
                        id="registroSgadLbl"
                        as="h6"
                        variant="bodyMd"
                        fontWeight="semibold"
                      >
                        Registro en el SGAD:
                      </FText>
                      <Dropdown
                        id="registroSgad"
                        v-model="bitacora.registro_sgad"
                        placeholder="Seleccione una opción"
                        :options="options"
                        optionLabel="label"
                        optionValue="value"
                        checkmark
                        :highlightOnSelect="false"
                        class="w-full md:w-14rem"
                        :style="[
                          v$.registro_sgad.$error ? { 'border-color': 'red' } : {},
                        ]"
                      />
                      <span v-if="v$.registro_sgad.$error" style="color: #c5280c"
                        >* El campo destinatario es requerido</span
                      >

                      <div v-if="bitacora.registro_sgad == 'S'">
                        <FText
                          id="numeroTramiteLbl"
                          as="h6"
                          variant="bodyMd"
                          fontWeight="semibold"
                        >
                          Número de trámite:
                        </FText>
                        <FTextField
                          id="numeroTramite"
                          v-model="bitacora.numero_tramite"
                          :error="v$?.numero_tramite.$error"
                          :label="
                            v$?.numero_tramite.$error ? 'Este campo es requerido' : ''
                          "
                        />
                      </div>
                    </FVerticalStack>
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
} from "@ups-dev/freya-icons";
import Image from "primevue/image";
import { Persona, Sumilla } from "../../models/Sumilla.model";
import { useToast } from "primevue/usetoast";
import Calendar from "primevue/calendar";
import AutoComplete from "primevue/autocomplete";

const toast = useToast();
const { handleSubmit } = useForm();

const mostrarDestinatario = ref<boolean>(false);
const mostrarMensajero = ref<boolean>(false);
const mostrarEmisor = ref<boolean>(false);
const mostrarUsrReceptor = ref<boolean>(false);
const fechaEntrega = ref<string>("");
const codigoBitacora = ref<Number>(0);

const {
  sumillaList,
  sumilla,
  sumillaEncontrada,
  data,
  //*Service
  saveSumilla,
  findSumillas,
  deleteSumilla,
  getSumillaByNumeroSumilla,
} = useSumillaComposable();

const {
  bitacorasList,
  bitacora,
  receptorPersonaList,
  getSumillaById,
  getUsers,
  saveBitacora,
  findBitacoras,
  editBitacora,
  deleteBitacora,
  v$,
  ErrorMessage,
} = useBitacoraComposable();

//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const createModal = ref<boolean>(false);
const deleteModal = ref<boolean>(false);
const codigoSumillaDelete = ref<Number>(0);
const numeroSumilla = ref<string>("");
const filteredItems = ref<Persona[]>([]);

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
  console.log(event);
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
  console.log(bitacora.value.hora_entrega, "hora_entrega");
};

const prepareCreate = () => {
  action.value = persistAction.create;
  handleChangeCreateModal();
  sumilla.value.responsable = userLogin.value;
  sumilla.value.fecha_sumilla = new Date();
  sumilla.value.hora_sumilla = new Date().getHours() + ":" + new Date().getMinutes();
};

const prepareEdit = async (sumillaParam: Sumilla) => {
  action.value = persistAction.edit;
  sumilla.value = { ...sumillaParam };
  createModal.value = !createModal.value;
};

const confirmDelete = async () => {
  await deleteSumilla(codigoSumillaDelete.value);
  await findSumillas();
  toast.add({
    severity: "success",
    summary: "Sumilla",
    detail: `Se ha eliminado la sumilla correctamente`,
    life: 5000,
  });
  changeDeleteModal();
};

const handleChangeDeleteModal = (sum: any) => {
  deleteModal.value = !deleteModal.value;
  codigoSumillaDelete.value = sum.codigo;
};

const changeDeleteModal = () => {
  deleteModal.value = !deleteModal.value;
};

const onSubmited = handleSubmit(async (values) => {
  sumilla.value.numero_hojas = parseInt(numHojas.value);
  sumilla.value.fecha_sumilla = new Date();
  sumilla.value.hora_sumilla = new Date().getHours() + ":" + new Date().getMinutes();
  await saveSumilla(sumilla.value, data.value?.user?.email!);
  await findSumillas();
  toast.add({
    severity: "success",
    summary: "Sumilla",
    detail: `Se ha guardado la sumilla correctamente`,
    life: 5000,
  });
  createModal.value = !createModal.value;
  resetNumHojas();
});

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
  selected.value = 0;
  await prepareEdit(sumillaEncontrada.value!);
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
</style>
