<template>
  <FCard sectioned>
    <FFormLayout>
      <FLayoutSection>
        <DataTable
          :value="bitacorasList"
          :showGridlines="true"
          :stripedRows="true"
          tableStyle="min-width: 50rem"
          :paginator="true"
          :rows="10"
        >
          <Column field="codigo" header="No. de ref" style="width: 5px"></Column>

          <Column header="Remitente" style="width: 5px">
            <template #body="slotProps">
              {{ slotProps.data.nombres_remitente }}
              {{ slotProps.data.apellidos_remitente }}
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
            field="numero_guia"
            header="Número de guia"
            style="width: 10px"
            bodyStyle="text-align: center;"
          >
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
          <Column header="Archivo" style="width: 10px" bodyStyle="text-align: center;">
            <template #body="slotProps">
              <span v-if="slotProps.data.doc_archivo">
                <a :href="getDownloadUrl(slotProps.data.doc_archivo)" target="_blank">{{
                  slotProps.data.nombre_archivo
                }}</a>
              </span>
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
        </DataTable>
      </FLayoutSection>
    </FFormLayout>
  </FCard>
</template>
<script lang="ts" setup>
const { bitacorasList } = useBitacorasDestinatariosComposable();

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
</script>
<style lang="css"></style>
