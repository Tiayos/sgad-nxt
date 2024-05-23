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
  editBitacora,
  getBitacoraByNumSumilla,
  filtersSumillaBitacora,
  bitacora,
} = useSumillaComposable();

const codigoBitacora = ref<Number>(0);
const deleteModal = ref<boolean>(false);

const changeDeleteModal = () => {
  deleteModal.value = !deleteModal.value;
};

const confirmDelete = async () => {
  await deleteBitacora(codigoBitacora.value);
  await findBitacoras();
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

const uint8ArrayToFile = (byteArray: number[], fileName: string): File => {
  const uint8Array = new Uint8Array(byteArray);
  const blob = new Blob([uint8Array], { type: "application/pdf" });
  return new File([blob], fileName, { type: "application/pdf" });
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
