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
            </FModal>
          </FCard>
        </FLayoutSection>

        <BitacoraListBitacora></BitacoraListBitacora>
      </FFormLayout>

      <FFormLayout v-if="selected == 1">
        <FFormLayoutGroup>
          <FModalSection title-hidden>
            <FVerticalStack gap="2">
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

const toast = useToast();
const { handleSubmit } = useForm();

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

//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const createModal = ref<boolean>(false);
const deleteModal = ref<boolean>(false);
const codigoSumillaDelete = ref<Number>(0);
const numeroSumilla = ref<string>("");

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
  console.log(sumillaEncontrada.value + "<<<");
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
