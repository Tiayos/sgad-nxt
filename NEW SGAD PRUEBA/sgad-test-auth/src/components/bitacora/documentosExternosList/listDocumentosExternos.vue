<template>
    <FText as="h5" variant="headingLg" color="subdued" font-weight="regular" >Documentación electrónica</FText>
    <DataTable
          :value="bitacorasExternasList"
          :showGridlines="true"
          :stripedRows="true"
          :filters="filtersSumillaElectronica"
          tableStyle="min-width: 50rem"
          :paginator="true"
          :rows="10"
          removable-sort
    >
    <template #header>
          <div
              class="datatable-header-toolbar flex flex-wrap align-items-center justify-content-between gap-2"
          >
          
            <FHorizontalStack gap="4" align="space-between">

              <FHorizontalStack gap="4">
                <FTextField
                    type="text"
                    id="filterSumillaElectronica"
                    v-model="filtersSumillaElectronica['global'].value"
                    placeholder="N° sumilla electrónica"
                >
                </FTextField>
              </FHorizontalStack>
            </FHorizontalStack>
          </div>
        </template>
    <Column field="sumilla.numero_sumilla" header="No. de sumilla" style="width: 5px" sortable ></Column>
    <Column header="Nombre del responsable" style="width: 5px">
        <template #body="slotProps">
          {{
            (slotProps.data.sumilla.responsable.per_nombres || '') + " " + (slotProps.data.sumilla.responsable.per_apellidos || '')
          }}
        </template>
    </Column>
    <Column header="Remitente" style="width: 5px">
        <template #body="slotProps">
          {{
            (slotProps.data.nombres_remitente || '') + " " + (slotProps.data.apellidos_remitente || '')
          }}
        </template>
    </Column>
    <Column field="nombre_completo_destinatario" header="Destinatario" style="width: 5px"></Column>
    <!-- <Column field="correo_remitente" header="Correo remitente" style="width: 5px"></Column> -->
    <Column 
    field="estado" 
    header="Estado"  
    style="width: 5px" 
    header-style="text-align: center;" 
    bodyStyle="text-align: center;"
  >
    <template #body="slotProps">
      <FBadge v-if="slotProps.data.estado == 1" status="success">enviado al destinatario</FBadge>
      <FBadge v-if="slotProps.data.estado == 2" status="critical">REASIGNADO</FBadge>
      <FBadge v-if="slotProps.data.estado == 3" status="warning">devuelto al remitente</FBadge>
      <FBadge v-if="slotProps.data.estado == 4" status="critical">en trámite</FBadge>
      <FBadge v-if="slotProps.data.estado == 5 || slotProps.data.estado == 6" status="success">tramitado</FBadge>
      <!-- <FBadge v-if="slotProps.data.estado == 6" status="success">EL DESTINATARIO DIÓ TRÁMITE</FBadge> -->
    </template>
</Column>
    <Column header="Acciones" style="width: 5px" bodyStyle="text-align:center" >
            <template #body="slotProps">
              <FButton
                  size="medium"
                  :icon="CaretDownSolid"
                  @click="acciones(slotProps.data)"
                  :disabled="slotProps.data.estado== 1 || slotProps.data.estado== 3  || slotProps.data.estado== 5  || slotProps.data.estado== 6 "
              />
            </template>
    </Column>
    <Column header="Ver" style="width: 5px" bodyStyle="text-align:center" headerStyle="text-align: center">
            <template #body="slotProps">
              <FButton
                  size="medium"
                  :icon="MagnifyingGlassSolid"
                  @click="prepareView(slotProps.data)"
              />
            </template>
    </Column>
    </DataTable>

<!-- Modal enviar destinatario o reasignar -->
    <FModal
        v-model="modalAcciones"
        title=""
        title-hidden
        sectioned
        :primaryAction="{
        content: lblButton,
        onAction: onSubmitEnviarDocumento,
        disabled: accion == 4
      }"
        :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeAcciones,
        },
      ]"
    >
        <FVerticalStack gap="4">
          <FText as="h1" variant="bodyMd" >Acción: </FText>
          <Dropdown
                v-model="accion"
                :options="accionesList"
                optionLabel="name"
                optionValue="value"
                placeholder="Seleccione"
                @change="limpiar"
          />

          <FVerticalStack gap="4" v-if="accion==1">
            <FText id="destinatarioLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                Destinatario:
            </FText>
            <FBox
              background="bg"
              padding="0"
              borderWidth="3"
              borderColor="border"
              style="border-radius: 5px"
              :style="[destinatarioError != null ? { 'border-color': '#FF6767' } : {}]"
          >
          <AutoComplete
                class="full-width-autocomplete"
                v-model="destinatario"
                optionLabel="nombreCompleto"
                :suggestions="filteredItems"
                @Complete="searchItem"
            />
          </FBox>
      
          </FVerticalStack>
          <FVerticalStack gap="4" v-if="accion==2">
            <FText id="sedeLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                Sede:
            </FText>
            <Dropdown
                v-model="sedeSelected"
                :options="sedeList"
                optionLabel="label"
                optionValue="value"
                placeholder="Seleccione"
                :style="[sedeSelectedError != null ? { 'border-color': '#FF6767' } : {}]"
                />
          </FVerticalStack>

          <FVerticalStack gap="4" v-if="accion==3">
            <FText id="destinatarioLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                Observación:
            </FText>
            <FTextField 
            :multiline="4"
            autoComplete="off"
            showCharacterCount
            :max-length="250"
            v-model="observacion"
            :error="observacionError"
            placeholder="Escriba el motivo"
            >
            </FTextField>
          </FVerticalStack>

        </FVerticalStack>
    </FModal>

    <FModal
        v-model="modalView"
        title=""
        title-hidden
        sectioned
        :secondaryActions="[
        {
          content: 'Cerrar',
          onAction: handleChangeView,
        },
      ]"
    >
    <FCardSection >
      <FVerticalStack gap="4" inline-align="start">

        <FText id="destinatarioLbl" as="h6" variant="bodyLg" color="subdued" fontWeight="regular">
            Documentos:
          </FText>     
          <div v-if="documentosExternosRecibidos.length > 0">
      <ul>
        <FVerticalStack gap="4">
        <li
          v-for="(documento, index) in documentosExternosRecibidos"
          :key="documento.doe_nombre_archivo"      
          >
            <a
            :href="createDownloadLink(documento.doe_archivo, documento.doe_nombre_archivo)"
            :download="documento.doe_nombre_archivo "
          >

            <i class="pi pi-file" style="margin-right: 8px;"></i>
            {{ documento.doe_nombre_archivo }}

          </a>
        </li>
      </FVerticalStack>

      </ul>
    </div>             

        <FText id="destinatarioLbl" as="h6" variant="bodyLg" color="subdued" fontWeight="regular">
            Número de sumilla: {{bitacoraExternaSelected.sumilla.numero_sumilla}}
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyMd" color="subdued" fontWeight="semibold" style="padding-left: 15px;">
            
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyLg" color="subdued" fontWeight="regular">
            Nombres del remitente: {{bitacoraExternaSelected.nombres_remitente}}
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyMd" color="subdued" fontWeight="semibold" style="padding-left: 15px;">
            <!-- {{bitacoraExternaSelected.nombres_remitente}} -->
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyLg" color="subdued" fontWeight="regular">
            Apellidos del remitente: {{bitacoraExternaSelected.apellidos_remitente}}
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyMd" color="subdued" fontWeight="semibold" style="padding-left: 15px;">
            <!-- {{bitacoraExternaSelected.apellidos_remitente}} -->
          </FText>     
          <FText id="destinatarioLbl" as="h6" variant="bodyLg" color="subdued" fontWeight="regular">
            Nombre de la organización: {{bitacoraExternaSelected.nombre_organizacion}}
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyMd" color="subdued" fontWeight="semibold" style="padding-left: 15px;">
            <!-- {{bitacoraExternaSelected.nombre_organizacion}} -->
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyLg" color="subdued" fontWeight="regular">
            Asunto: {{bitacoraExternaSelected.asunto}}
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyMd" color="subdued" fontWeight="semibold" style="padding-left: 15px;">
            <!-- {{bitacoraExternaSelected.asunto}} -->
          </FText>    
          <FText id="destinatarioLbl" as="h6" variant="bodyLg" color="subdued" fontWeight="regular">
            Correo remitente: {{bitacoraExternaSelected.correo_remitente}}
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyMd" color="subdued" fontWeight="semibold" style="padding-left: 15px;">
            <!-- {{bitacoraExternaSelected.correo_remitente}} -->
          </FText>      
          <FText id="destinatarioLbl" as="h6" variant="bodyLg" color="subdued" fontWeight="regular">
            Nombres completos del destinatario: {{bitacoraExternaSelected.nombre_completo_destinatario}}
          </FText>
          <FText id="destinatarioLbl" as="h6" variant="bodyMd"  color="subdued" fontWeight="semibold" style="padding-left: 15px;">
            <!-- {{bitacoraExternaSelected.nombre_completo_destinatario}} -->
          </FText>
           
      </FVerticalStack>
    </FCardSection>
        
    </FModal>

</template>

<script setup lang="ts">
import { useBitacoraExternaComposable } from "~/composables/documentosExternos/bitacoraExternaComposable";
import type { DocumentosExternos } from "~/models/DocumentosExternos.model";
import type { BitacoraExternos } from "~/models/BitacoraExternos.model";
import type { SedeProjection } from "~/models/projection/SedeProjection.model";
import type { Persona } from "~/models/Sumilla.model";
import { usePersonaService } from "~/composables/services/usePersonaService";
import { useBitDocElectronicosComposable } from '../../../composables/bitacoras/useBitDocElectronicosComposable';
import { FilterMatchMode } from "primevue/api";

import {
    MagnifyingGlassSolid,
    CaretDownSolid,
} from "@ups-dev/freya-icons";
import AutoComplete from "primevue/autocomplete";

const {getUsersByFilterName} = usePersonaService();

const bitacorasExternasList = ref<BitacoraExternos[]>([]);
const sede = ref<SedeProjection>({} as SedeProjection);
const modalAcciones = ref<boolean>(false);
const modalView = ref<boolean>(false);
const accionesList = ref([{ name: "ENVIAR DESTINATARIO", value: 1 }, { name: "REASIGNAR", value: 2 }, {name:"DEVOLVER AL REMITENTE", value:3}]);
const filteredItems = ref<Persona[]>([]);
const destinatarioPersonaList = ref<Persona[]>([]);
const lblButton = ref<string>("Confirmar");
const bitacoraExternaSelected = ref<BitacoraExternos>({} as BitacoraExternos);
const documentosExternosRecibidos = ref<DocumentosExternos[]>([]);

const filtersSumillaElectronica = ref({
        global: { value: "", matchMode: FilterMatchMode.CONTAINS },
    });

const {
    getAllBitacorasExternosBySede,
    data,
    getSedeByEmail,
    sedeList,
    getUsrLogin,
    editBitacoraExterna,
    getDocumentoExternoByBidCodigoRecibidos
} = useBitacoraExternaComposable();

const {
  accion,
  destinatario,
  destinatarioError,
  resetdestinatario,
  sedeSelected,
  sedeSelectedError,
  resetSedeSelected,
  observacion,
  observacionError,
  resetObservacion,
  handleSubmit,
  sendEmailReasignado,
  sendEmailDestinatario
} = useBitDocElectronicosComposable();

watch(() => accion.value, () => {
  switch (accion.value) {
    case 1:
      lblButton.value = 'Enviar documento'
      break;
    case 2:
    case 3:
      lblButton.value = 'Confirmar'
      break;
  }
})

onMounted(async() => {
    try {
        sede.value = await getSedeByEmail(data.value?.user?.email!);
        bitacorasExternasList.value = await getAllBitacorasExternosBySede(sede.value.dee_codigo);
    } catch (error) {
        throw (error);
    }
});

const limpiar = () =>{
  resetdestinatario();
  resetSedeSelected();
  resetObservacion();
}

const searchItem = async(event: any) => {
  const query = event.query.toLowerCase();
  destinatarioPersonaList.value = await getPersonasByFilterName(query);
  filteredItems.value = destinatarioPersonaList.value.filter(
      (item:any) =>
          item.per_nombres.toLowerCase().includes(query) ||
          item.per_apellidos.toLowerCase().includes(query)
  );
};

const getPersonasByFilterName = async(name:string = 'abe') =>{
       return destinatarioPersonaList.value = await getUsersByFilterName(name);
    }

const handleChangeAcciones = () =>{
    modalAcciones.value = !modalAcciones.value
}

const acciones = (bitacoraExt: BitacoraExternos) => {
  bitacoraExternaSelected.value = bitacoraExt;
  accion.value = bitacoraExt.estado;
    handleChangeAcciones();
}

const handleChangeView = () =>{
  modalView.value = !modalView.value
}

const prepareView = async (bitacoraExt: BitacoraExternos) => {
  bitacoraExternaSelected.value = bitacoraExt;
  documentosExternosRecibidos.value = await getDocumentoExternoByBidCodigoRecibidos(bitacoraExt.codigo);
  handleChangeView();
}

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

const onSubmitEnviarDocumento = handleSubmit(async (values:any) => {
  switch (values.accion) {
    case 1: // enviar destinatario
    try {
      bitacoraExternaSelected.value.estado = accion.value;
      bitacoraExternaSelected.value.destinatario = destinatario.value;
      bitacoraExternaSelected.value.responsable = await getUsrLogin(data.value?.user?.email!);
      await editBitacoraExterna(bitacoraExternaSelected.value, bitacoraExternaSelected.value.codigo, accion.value, sedeSelected.value ?? 0)
      bitacorasExternasList.value = await getAllBitacorasExternosBySede(sede.value.dee_codigo);
      await sendEmailDestinatario(bitacoraExternaSelected.value)
      handleChangeAcciones();
    } catch (error) {
      console.log(error);
    }
      break;
    case 2:
      try {
        bitacoraExternaSelected.value.estado = accion.value;
        bitacoraExternaSelected.value.responsable = await getUsrLogin(data.value?.user?.email!);
        await editBitacoraExterna(bitacoraExternaSelected.value, bitacoraExternaSelected.value.codigo, accion.value, sedeSelected.value);
        await sendEmailReasignado(bitacoraExternaSelected.value, sedeSelected.value);
        bitacorasExternasList.value = await getAllBitacorasExternosBySede(sede.value.dee_codigo);
        handleChangeAcciones();
      } catch (error) {
        console.log(error);
      }
      break;
    case 3:
      try {
        bitacoraExternaSelected.value.estado = accion.value;
        bitacoraExternaSelected.value.observacion = observacion.value
        bitacoraExternaSelected.value.responsable = await getUsrLogin(data.value?.user?.email!);
        await editBitacoraExterna(bitacoraExternaSelected.value, bitacoraExternaSelected.value.codigo, accion.value, sedeSelected.value ?? 0);
        bitacorasExternasList.value = await getAllBitacorasExternosBySede(sede.value.dee_codigo);
        handleChangeAcciones();
      } catch (error) {
        console.log(error);
      }
  }
});

</script>

<style lang="css">

</style>