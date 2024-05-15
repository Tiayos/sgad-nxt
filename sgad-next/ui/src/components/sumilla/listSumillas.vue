<template>
  <FCard sectioned>
    <FFormLayout>
      <FLayoutSection>
        <FCard sectioned>
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
                class="flex flex-wrap align-items-center justify-content-between gap-2"
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
    </FFormLayout>
  </FCard>
</template>
<script setup lang="ts">
import { PlusSolid, PencilSolid, TrashCanSolid } from "@ups-dev/freya-icons";
import Image from "primevue/image";
import { Persona, Sumilla } from "../../models/Sumilla.model";
import { useToast } from "primevue/usetoast";

const toast = useToast();
const { handleSubmit } = useForm();

//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const createModal = ref<boolean>(false);
const deleteModal = ref<boolean>(false);
const codigoSumillaDelete = ref<Number>(0);

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
  await saveSumilla(sumilla.value);
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

const {
  sumillaList,
  sumilla,
  //*Service
  saveSumilla,
  findSumillas,
  deleteSumilla,
} = useSumillaComposable();
</script>
<style lang="css"></style>
