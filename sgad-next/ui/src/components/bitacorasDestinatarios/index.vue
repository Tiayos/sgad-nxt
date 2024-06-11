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
          selectionMode="single"
          v-model:selection="eventoSelected"
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
            style="width: 10px"
            bodyStyle="text-align: center;"
          >
            <template #body="slotProps">
              <FBadge status="success" v-if="slotProps.data.estado.codigo === 5"
                >Recibido</FBadge
              >
            </template>
          </Column>

          <Column header="Archivo" style="width: 10px" bodyStyle="text-align: center;">
            <template #body="slotProps">
              <span v-if="slotProps.data.bitacora.doc_archivo">
                <a
                  :href="getDownloadUrl(slotProps.data.bitacora.doc_archivo)"
                  target="_blank"
                  >{{ slotProps.data.bitacora.nombre_archivo }}</a
                >
              </span>
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
      }"
      :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeAcciones,
        },
      ]"
    >
      <FModalSection title-hidden>
        <FLayout>
          <FLayoutAnnotatedSection>
            <template #title>
              <FText variant="bodyLg" as="h1" alignment="center" font-weight="bold"
                >Eventos</FText
              >
            </template>
            <FCard sectioned>
              <FFormLayout>
                <FResourceList
                  :resourceName="{ singular: 'evento', plural: 'eventos' }"
                  :items="eventosBitacorasAcciones"
                >
                  <template #item="{ item }">
                    <FResourceItem
                      :id="item.codigo"
                      :accessibilityLabel="
                        'Ver detalles para ' +
                        item.bitacora.nombres_remitente +
                        ' ' +
                        item.bitacora.apellidos_remitente
                      "
                    >
                      <template #media>
                        <FAvatar
                          customer
                          size="medium"
                          :name="
                            item.bitacora.nombres_remitente +
                            ' ' +
                            item.bitacora.apellidos_remitente
                          "
                        />
                      </template>

                      <h3>
                        <FText fontWeight="bold" as="span">
                          {{ item.bitacora.nombres_remitente }}
                          {{ item.bitacora.apellidos_remitente }}
                        </FText>
                      </h3>
                      <div><strong>No. de ref:</strong> {{ item.codigo }}</div>
                      <div><strong>Asunto:</strong> {{ item.bitacora.asunto }}</div>
                      <div>
                        <strong>Número de guía:</strong> {{ item.bitacora.numero_guia }}
                      </div>
                      <div>
                        <strong>Estado documento:</strong>
                        <FBadge
                          :status="item.estado.codigo === 5 ? 'success' : 'default'"
                        >
                          {{ item.estado.estado_descripcion }}
                        </FBadge>
                      </div>
                    </FResourceItem>
                  </template>
                </FResourceList>
              </FFormLayout>
            </FCard>
          </FLayoutAnnotatedSection>
        </FLayout>
      </FModalSection>
    </FModal>
  </FCard>
</template>
<script lang="ts" setup>
import { EventoBitacora } from "../../models/EventoBitacora.model";
import { MagnifyingGlassSolid } from "@ups-dev/freya-icons";

const {
  eventosBitacorasList,
  getAllEventosByBitCodigo,
} = useBitacorasDestinatariosComposable();
const eventoSelected = ref<EventoBitacora>({} as EventoBitacora);
const accionesModal = ref<boolean>(false);
const eventosBitacorasAcciones = ref<EventoBitacora[]>([]);
const selectedItems = ref([]);

const open = ref(false);
const handleToggle = () => (open.value = !open.value);
const transition = {
  duration: "var(--f-motion-duration-150)",
  timingFunction: "var(--f-motion-ease-in-out)",
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

const handleChangeAcciones = () => {
  accionesModal.value = !accionesModal.value;
};

const prepareAcciones = async (EventoParam: EventoBitacora) => {
  eventosBitacorasAcciones.value = await getAllEventosByBitCodigo(
    EventoParam.bitacora.codigo
  );
  console.log(eventosBitacorasAcciones.value, "<<<<");
  handleChangeAcciones();
};

const onSubmitAcciones = () => {};
</script>
<style lang="css"></style>
