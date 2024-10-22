<template>
  <FPage
         title="Sistema de Gestión documental"
         subtitle= "Registra la bitácora que se enviará al usuario de la UPS"
         :pagination="{ hasPrevious: true, hasNext: true}">

    <FTabs :tabs="tabs" v-model:selected="selected" fitted>
      <FCard sectioned title="" v-if="selected==0">
        <FVerticalStack gap="4">
          <FFormLayout>
          <FFormLayoutGroup>
            <FTextField
                id="nombresRemitenteTxt"
                v-model="nombreRemitente"
                rules="required"
                label="Nombres remitente:"
                :error="nombreRemitenteError"
                required-indicator
            />
          <FTextField
              id="apellidosRemitenteTxt"
              v-model="apellidoRemitente"
              :error="apellidoRemitenteError"
              label="Apellidos remitente:"
              required-indicator
          />
        </FFormLayoutGroup>
      </FFormLayout>
            
      <FVerticalStack gap="4">

          <FTextField
              id="nombreOrganizacionTxt"
              v-model="nombreOrganizacion"
              :error="nombreOrganizacionError"
              required-indicator
              label="Nombre de la organización:"
          />

          <FTextField
              id="asuntoTxt"
              v-model="asunto"
              :error="asuntoError"
              required-indicator
              label="Asunto del trámite:"
          />
          <FTextField
              id="correoRemitenteTxt"
              v-model="correoRemitente"
              :error="correoRemitenteError"
              required-indicator
              label="Correo del remitente:"
          />

          <FText id="sedeLbl" as="h6" variant="bodyMd">Sede:</FText>
          <Dropdown
                  v-model="sede"
                  :options="sedeList"
                  optionLabel="label"
                  optionValue="value"
                  placeholder="Seleccione"
                  :style="[sedeError != null ? { 'border-color': '#FF6767' } : {}]"
              />

          <FTextField
              id="nombreDestinatarioTxt"
              v-model="nombreDestinatario"
              rules="required"
              :error="nombreDestinatarioError"
              required-indicator
              label="Nombres completos del destinatario:"
          />

          <FText id="subirDocumentoLbl" as="h6" variant="bodyMd" font-weight="semibold">Subir Documentos:</FText>
          <FVerticalStack gap="4">
          </FVerticalStack>
          <FileUpload
              ref="fileUpload"
              name="file"
              accept=".pdf"
              multiple
              :maxFileSize="10485760"
              class="custom-file-upload"
              :chooseLabel="'Seleccionar archivos'"
              :onSelect="handleFileSelect"
              :key="uploadKey"
          >
          </FileUpload>
          <RecaptchaV2
              @widget-id="handleWidgetId"
              @error-callback="handleErrorCalback"
              @expired-callback="handleExpiredCallback"
              @load-callback="handleLoadCallback"
          />
        </FVerticalStack>
      </FVerticalStack>
      </FCard>
      <FPageActions v-if="selected==0" :primaryAction="{content: 'Enviar documento',
        icon: PlusSolid,
        disabled: captchaValue==false,
        onAction:() => onSubmit(),}" 
        :secondaryActions="[{content: 'Limpiar', onAction: () => limpiar(),
        }]">
      </FPageActions>
      
      <FCard sectioned title="" v-if="selected==1">
        <FVerticalStack gap="4">
          <FText id="numeroSumillaLbl" as="dd" color="subdued" font-weight="medium" variant="bodyLg">Número de sumilla:</FText>
          <FTextField
              id="numeroSumillaBuscarTxt"
              v-model="numeroSumilla"
              rules="required"
          />
          <FText id="numCodigoLbl" as="dd" color="subdued" font-weight="medium" variant="bodyLg">Código de documento:</FText>
          <FTextField
              id="codigoDocumentoTxt"
              v-model="codigoAleatorioDocumento"
              rules="required"
              placeholder="Código enviado a su correo"
          />

<FCardSection>

  <FVerticalStack gap="1" v-if="documentosEncontradosList.length>0" >
                <FText id="docRecibidosLbl" as="h2" variant="headingMd" font-weight="bold" color="subdued">Documentos respuesta:</FText>
                <div>
                  <ul>
                    <li
                      v-for="(documento, index) in documentosEncontradosList"
                      :key="documento.doe_nombre_archivo"      
                      >
                      <a
                        :href="createDownloadLink(documento.doe_archivo, documento.doe_nombre_archivo)"
                        :download="documento.doe_nombre_archivo"
                      >
                        <i class="pi pi-file" style="margin-right: 8px;"></i>
                        {{ documento.doe_nombre_archivo }}
                      </a>
                    </li>
                  </ul>
                </div>
                <FDivider :border-width="'4'" />
              </FVerticalStack>

              <FVerticalStack gap="4">
                <FText id="devueltoDestinatarioLbl"  as="dd" variant="bodyLg" color="success" font-weight="semibold" v-if="bitacoraElectronica.estado==5" >
                  <i class="pi pi-check-circle"></i> El destinatario ha enviado la documentación física.
                </FText>
                <FText id="devueltoDestinatarioLbl" as="dd" variant="bodyMd" v-if="bitacoraElectronica.estado==3" >
                  El colaborador de recepción le ha devuelto el documento por la siguiente razón:
                </FText>
                <FText id="devueltoDestinatarioLbl" as="dd" variant="bodyMd" color="success" font-weight="regular" v-if="bitacoraElectronica.estado==3" >
                  {{ bitacoraElectronica.observacion }}
                </FText>
              </FVerticalStack>

</FCardSection>

          <FPageActions v-if="selected==1"
            :primaryAction="{content: 'Buscar documento',
            icon: MagnifyingGlassSolid,
            disabled: numeroSumilla =='' && codigoAleatorioDocumento =='',
            onAction:() => findDocumentosElectronicos(),}">
          </FPageActions>
    
        </FVerticalStack>
        
      </FCard>

    </FTabs>

    <FModal
        v-model="submitModal"
        title=""
        title-hidden
        large
        :primaryAction="{
        content: 'Enviar documento',
        onAction: handleChangeSubmitModal,
      }"
        :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeSubmitModal,
        },
      ]"
    >
    </FModal>
  </FPage>
  <FToast v-model="mostrarMsgError" :content=mensajeToast error :duration="5000" />
  <FToast v-model="mostrarMsgCorrecto" :content=mensajeToast  :duration="5000" />
</template>
<script lang="ts" setup>
import {
  FileSolid,
  PlusSolid, TrashCanSolid, MagnifyingGlassSolid,
  CheckSolid
} from "@ups-dev/freya-icons";
import {useBitacoraExternaComposable} from "~/composables/documentosExternos/bitacoraExternaComposable";
import { RecaptchaV2 } from "vue3-recaptcha-v2";
import type {Persona, Sumilla} from "~/models/Sumilla.model";
import type {BitacoraExternos} from "~/models/BitacoraExternos.model";
import type { Bitacora } from "~/models/Bitacora.model";
import type { DocumentosExternos } from "~/models/DocumentosExternos.model";

const {
  bitacoraExterna,
  sumilla,
  handleSubmit,
  nombreRemitente,
  nombreRemitenteError,
  resetNombreRemitente,
  apellidoRemitente,
  apellidoRemitenteError,
  resetApellidoRemitente,
  correoRemitente,
  correoRemitenteError,
  resetCorreoRemitente,
  asunto,
  asuntoError,
  resetAsunto,
  nombreOrganizacion,
  nombreOrganizacionError,
  resetNombreOrganizacion,
  nombreDestinatario,
  nombreDestinatarioError,
  resetNombreDestinatario,
  sede,
  sedeError,
  resetSede,
  sedeList,
  captchaValue,
  files,
  documentObj,
  resetFiles,
  ///--------------------Service
  getUsrLogin,
  saveBitacoraExterna,
  sendEmailUsuarioExterno,
  saveSumillaExterna,
  saveDocumentoExterno,
  getDocumentoElectronicoByCodigoAleatorio,
  getBitacorasElectronicasBySumilla
} = useBitacoraExternaComposable()
const submitModal = ref<boolean>(false);
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const mostrarMsgError = ref<boolean>(false);
const mostrarMsgCorrecto = ref<boolean>(false);
const mensajeToast = ref<string>('');
const uploadKey = ref<number>(0);
const selected = ref(0);
const numeroSumilla = ref<string>('');
const codigoAleatorioDocumento = ref<string>('');
const documentosEncontradosList = ref<DocumentosExternos[]>([]);
const bitacoraElectronica = ref<BitacoraExternos>({} as BitacoraExternos);

const findDocumentosElectronicos = async () =>{
  documentosEncontradosList.value = await getDocumentoElectronicoByCodigoAleatorio(numeroSumilla.value.trim(), codigoAleatorioDocumento.value.trim());
  if(documentosEncontradosList.value.length == 0){
    bitacoraElectronica.value = await getBitacorasElectronicasBySumilla(numeroSumilla.value.trim(), codigoAleatorioDocumento.value.trim());
    if(bitacoraElectronica.value.estado == 1 || bitacoraElectronica.value.estado == 2 || bitacoraElectronica.value.estado == 4 ){
      mostrarMsgError.value = true;
      mensajeToast.value = 'Su documento sigue en trámite';
    }
  }
}

const limpiar = () =>{
  bitacoraExterna.value = {} as BitacoraExternos;
  documentObj.value = {} as DocumentosExternos;
  resetNombreRemitente();
  resetApellidoRemitente();
  resetNombreOrganizacion();
  resetAsunto();
  resetCorreoRemitente();
  resetNombreDestinatario();
  resetSede();
  uploadKey.value +=1;
  resetFiles();
}

interface TabDescriptor {
  id: string;
  content: string;
}

const tabs: TabDescriptor[] = [
  {
    id: "crear-sumilla",
    content: "Enviar Documentos",
  },
  {
    id: "buscar-sumilla",
    content: "Consultar documentos",
  },
];

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

const handleFileSelect = (event: any) => {
  files.value = event.files;
};

const handleWidgetId = (widgetId: number) => {
};
const handleErrorCalback = () => {
  console.log("Error callback");
};
const handleExpiredCallback = () => {
  console.log("Expired callback");
  captchaValue.value = false;
};
const handleLoadCallback = (response: any) => {
  captchaValue.value = response.length>0 ? true : false;
};

const handleChangeSubmitModal = () =>{
  submitModal.value = !submitModal.value;
}

const onSubmit = handleSubmit( async(values:any) => {
  try {
    // sumilla.value.codigo = 0;
    sumilla.value.responsable = {} as Persona;
    sumilla.value.numero_hojas = files.value.length;
    sumilla.value.fecha_sumilla = new Date();
    sumilla.value.hora_sumilla = new Date().getHours() + ":" + new Date().getMinutes();
    sumilla.value.sum_sede = sede.value

    switch (sede.value) {
      case 2:
      const user2 = ref<Persona>({} as Persona);
      user2.value = await getUsrLogin('larias@ups.edu.ec')
      sumilla.value.responsable = user2.value
        break;
      case 3:
      const user3 = ref<Persona>({} as Persona);
      user3.value = await getUsrLogin('amacias@ups.edu.ec');
      sumilla.value.responsable = user3.value
        break;
      case 4:
      const user4 = ref<Persona>({} as Persona);
      user4.value = await getUsrLogin('megas@ups.edu.ec');
      sumilla.value.responsable = user4.value
        break;
    }

    const sumillaAux:Sumilla = await saveSumillaExterna(sumilla.value);

    bitacoraExterna.value.nombres_remitente = nombreRemitente.value;
    bitacoraExterna.value.apellidos_remitente = apellidoRemitente.value;
    bitacoraExterna.value.asunto = asunto.value;
    bitacoraExterna.value.correo_remitente = correoRemitente.value;
    bitacoraExterna.value.adicionado = bitacoraExterna.value.correo_remitente;
    bitacoraExterna.value.sumilla = sumillaAux;
    bitacoraExterna.value.nombre_completo_destinatario = nombreDestinatario.value;
    bitacoraExterna.value.estado = 4;
    bitacoraExterna.value.nombre_organizacion = nombreOrganizacion.value
    bitacoraExterna.value.codigo_consulta = crearCodigoAleatorio();

    bitacoraExterna.value = await saveBitacoraExterna(bitacoraExterna.value);
    await saveDocumentos();

    //* Send Email
    await sendEmailUsuarioExterno(bitacoraExterna.value, sumillaAux.numero_sumilla)
    mostrarMsgCorrecto.value = true;
    mensajeToast.value = 'Se envió correctamente el documento';

    limpiar();

  }catch (e) {
    console.log(e);
  }

});

const crearCodigoAleatorio = ()  =>{
  const generatedNumbers = new Set<number>();
    const min = 10 ** 4; 
    const max = 10 ** 8 - 1; 

  let randomNum: number;

  do {
    randomNum = Math.floor(Math.random() * (max - min + 1)) + min;
  } while (generatedNumbers.has(randomNum));
  
  return randomNum.toString();
}


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
            // documentObj.value.codigo = 0;
            documentObj.value.doe_archivo = byteArrayJava;
            documentObj.value.doe_nombre_archivo = file.name;
            documentObj.value.documentos_externos = bitacoraExterna.value;
            documentObj.value.adicionado = bitacoraExterna.value.adicionado;
            documentObj.value.estado_documento_electronico = 'E';

            await saveDocumentoExterno(documentObj.value);
          } catch (error) {
            console.error("Error al guardar el documento:", error);
          }
        };
        reader.onerror = (error) => {
          console.error("Error reading file:", error);
        };
        reader.readAsArrayBuffer(file);
      }
    } catch (error) {
      console.error("Error processing files:", error);
    }
  }
};

</script>
<style lang="css">
.p-fileupload .p-fileupload-buttonbar {
  display: none;
}

.custom-file-upload .p-button {
  background-color: blue !important;
  border-color: blue !important;
  font-size: 16px !important; /* Ajusta el tamaño según tus necesidades */
}

.custom-file-upload .p-fileupload-choose {
  background-color: blue !important;
  border-color: blue !important;
  font-size: 16px !important; /* Ajusta el tamaño según tus necesidades */
}

.custom-file-upload .p-fileupload-choose .p-button-label {
  color: white !important; /* Cambia el color del texto si es necesario */
}
</style>
