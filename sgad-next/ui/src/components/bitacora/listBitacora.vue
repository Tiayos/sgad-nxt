<template>
  <FCardSection>
    <FVerticalStack gap="8">
      <FDivider></FDivider>

      <FText id="buscarSumilla" as="h1" variant="headingMd" font-weight="semibold"
        >BITÁCORAS</FText
      >
      <DataTable
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
            <FHorizontalStack gap="2" align="space-between">
              <br />

              <FTextField
                type="text"
                id="filterSumillaBitacora"
                v-model="filtersSumillaBitacora['global'].value"
                placeholder="N° Sumilla"
                :disabled="true"
              >
              </FTextField>
            </FHorizontalStack>
          </div>
        </template>

        <Column field="codigo" header="No. de ref" style="width: 5px"></Column>
        <Column
          field="sumilla.numero_sumilla"
          header="Número Sumilla"
          style="width: 5px"
        ></Column>

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
            {{ $t("app.sgadNuxt.sumilla.eliminar") }}
          </FText>
        </FVerticalStack>
      </FModalSection>
    </FModal>
  </FCardSection>
</template>
<script setup lang="ts">
import { Bitacora } from "../../models/Bitacora.model";

const {
  bitacorasList,
  findBitacoras,
  deleteBitacora,
  filtersSumillaBitacora,
} = useSumillaComposable();

const codigoBitacora = ref<Number>(0);
const deleteModal = ref<boolean>(false);

const changeDeleteModal = () => {
  deleteModal.value = !deleteModal.value;
};

const handleChangeDeleteModal = (bitacorParam: Bitacora) => {
  deleteModal.value = !deleteModal.value;
  codigoBitacora.value = bitacorParam.codigo;
};

const confirmDelete = async () => {
  await deleteBitacora(codigoBitacora.value);
  await findBitacoras();
  changeDeleteModal();
};
</script>
<style lang="css">
.p-autocomplete {
  height: 35px;
}

.p-calendar {
  height: 33px;
}
</style>
