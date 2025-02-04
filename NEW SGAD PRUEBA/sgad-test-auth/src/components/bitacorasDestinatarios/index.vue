<template>
  <FCard sectioned>
    <FTabs :tabs="tabs" v-model:selected="selected" fitted>

      <FFormLayout v-if="selected==0">
      <FLayoutSection>
        <!-- tabla en bitácora para ver documentos físicos -->
        <DataTable
            :value="eventosBitacorasList"
            show-gridlines
            :stripedRows="true"
            tableStyle="min-width: 50rem"
            :paginator="true"
            :rows="10"
            v-model:filters="filtersSumillaBitacora"
        >

        <template #header>
          <div
              class="datatable-header-toolbar flex flex-wrap align-items-center justify-content-between gap-2"
          >
          
            <FHorizontalStack gap="4" align="space-between">
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

          <Column field="bitacora.sumilla.numero_sumilla" header="No. de sumilla" style="width: 5px" sortable></Column>
          <Column header="Remitente" body-style="width: 1px" >
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
              headerStyle="text-align: center"
          >
          </Column>

          <Column
              field="bitacora.numero_guia"
              header="Número de guia"
              style="width: 10px"
              bodyStyle="text-align: center;"
              headerStyle="text-align: center"
          >
          </Column>

          <Column
              field="estado.estado_descripcion"
              header="Estado documento"
              headerStyle="text-align: center"
              style="width: 10px"
              body-style="text-align: center"
          >
            <template #body="slotProps">
              <FBadge status="info" v-if="slotProps.data.estado.codigo === 5"
              >Recibido</FBadge>
              <FBadge status="success" v-if="slotProps.data.estado.codigo === 4"
              >Aprobado</FBadge
              >
              <FBadge status="critical" v-if="slotProps.data.estado.codigo === 3"
              >Se solicita documentación fisica</FBadge
              >
              <FBadge status="attention" v-if="slotProps.data.estado.codigo === 7"
              >Reasignado para trámite</FBadge
              >
              <FBadge status="new" v-if="slotProps.data.estado.codigo === 9"
              >Reasignado desde otra sede</FBadge
              >
            </template>
          </Column>

          <Column header="Acciones" style="width: 5px" headerStyle="text-align: center" bodyStyle="text-align:center">
            <template #body="slotProps">
              <FButton
                  size="medium"
                  :icon="MagnifyingGlassSolid"
                  @click="prepareAcciones(slotProps.data)"
              />
            </template>
          </Column>
          <Column style="width: 10px" v-if="appRoles.includes('recepcionist') ">
          <template #body="slotProps">
            <FButton
                size="medium"
                :icon="PencilSolid"
                @click="prepareEdit(slotProps.data.bitacora.sumilla, persistAction.edit)"
            >Editar</FButton>
          </template>
        </Column>
        </DataTable>
      </FLayoutSection>
      </FFormLayout>

      <FFormLayout v-if="selected==1">
      <FLayoutSection>
        <DataTable
          :value="bitacorasExternasList"
          :showGridlines="true"
          :stripedRows="true"
          tableStyle="min-width: 50rem"
          :paginator="true"
          :rows="10">
        <Column field="sumilla.numero_sumilla" header="No. de sumilla" style="width: 5px"></Column>
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
        <Column header="Estado" style="width: 5px" header-style="text-align: center;" bodyStyle="text-align: center;">
          <template #body="slotProps">
            <FBadge v-if="slotProps.data.estado == 1" status="critical">En trámite</FBadge>
            <FBadge v-if="slotProps.data.estado == 5" status="info">Documentación física</FBadge>
            <FBadge v-if="slotProps.data.estado == 6" status="success">Enviado la respuesta al remitente</FBadge>
        </template>
        </Column> 
        <Column header="Ver documentos" headerStyle="text-align:center;" style="width: 5px" bodyStyle="text-align:center" >
                <template #body="slotProps">
                  <FButton
                      size="medium"
                      :icon="CaretDownSolid "
                      @click="prepareVerDocumentos(slotProps.data)"
                  />
                </template>
        </Column>

        <Column header="Acciones" headerStyle="text-align:center;" style="width: 5px" bodyStyle="text-align:center" >
                <template #body="slotProps">
                  <FButton
                      size="medium"
                      :icon="MagnifyingGlassSolid"
                      @click="prepareAccionesDocumentosElectronicos(slotProps.data)"
                      :disabled="slotProps.data.estado == 5  || slotProps.data.estado == 6"
                  />
                </template>
        </Column>
      </DataTable>
      </FLayoutSection>
      </FFormLayout>
    </FTabs>

    <!-- Documentos fisicos -->

    <FModal
        large
        v-model="accionesModal"
        title=""
        title-hidden
        :primaryAction="{
        content: 'Confirmar cambios',
        onAction: onSubmitAcciones,
        disabled: desabilitarGuardarCambios,
      }"
        :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeAcciones,
        },
      ]"
    >
      <FModalSection>
        <FButton
            @click="handleToggle"
            :ariaExpanded="open"
            ariaControls="basic-collapsible"
            :icon="open ? PlusSolid : MinusSolid"
            outline
        >
          <FText as="h6" variant="headingSm">
            {{ open ? "Eventos" : "Eventos" }}
          </FText>
        </FButton>
      </FModalSection>

      <FCollapsible
          id="basic-collapsible"
          :open="open"
          :transition="transition"
          expand-on-print
      >
        <FModalSection>
          <FVerticalStack gap="4">
            <FResourceList
                :resourceName="{ singular: 'evento', plural: 'eventos' }"
                :items="eventosBitacorasAcciones"
            >
              <template #item="{ item }">
                <FResourceItem id="item.codigo">
                  <template #media>
                    <FAvatar customer size="medium" />
                  </template>

                  <FVerticalStack gap="1">
                    <FHorizontalStack gap="2">
                      <FText fontWeight="bold" as="span">
                        {{ item.per_codigo_responsable.per_nombres }}
                      </FText>
                      <FText fontWeight="bold" as="span">
                        {{ item.per_codigo_responsable.per_apellidos }}
                      </FText>
                    </FHorizontalStack>

                    <FHorizontalStack gap="28">
                      <FText fontWeight="semibold" as="span"> No. de ref: </FText>
                      <FText fontWeight="semibold" as="span">
                        {{ item.bitacora.sumilla.numero_sumilla }}
                      </FText>
                      <FText
                          fontWeight="semibold"
                          as="span"
                          alignment="start"
                          style="font-style: italic"
                          class="texto-superior-derecha"
                          variant="bodySm"
                      >
                        {{ item.fecha }}
                      </FText>
                    </FHorizontalStack>

                    <FHorizontalStack gap="32">
                      <FText fontWeight="semibold" as="span"> Asunto: </FText>
                      <FText fontWeight="semibold" as="span">
                        {{ item.bitacora.asunto }}
                      </FText>
                    </FHorizontalStack>

                    <FHorizontalStack gap="12">
                      <FText fontWeight="semibold" as="span"> Estado documento: </FText>
                      <FBadge :status="item.estado.codigo === 5 ? 'success' : 'default'">
                        {{ item.estado.estado_descripcion }}
                      </FBadge>
                    </FHorizontalStack>
                  </FVerticalStack>
                </FResourceItem>
              </template>
            </FResourceList>
          </FVerticalStack>
        </FModalSection>
      </FCollapsible>

      <FModalSection v-if="documentosBitacoraList.length > 0 && eventoSelected.estado.codigo != 9">
        <div >
          <FVerticalStack gap="4">
            <FText as="h6" variant="bodyMd" font-weight="semibold">Documentos recibidos:</FText>
          </FVerticalStack>
          <ul>
            <li
                v-for="documento in documentosBitacoraList"
                :key="documento.doc_nombre_archivo"
                style="margin-top: 1rem"
            >
              <a
                  :href="
                  createDownloadLink(documento.doc_archivo, documento.doc_nombre_archivo)
                "
                  :download="documento.doc_nombre_archivo"
              >
                {{ documento.doc_nombre_archivo }}
              </a>
            </li>
          </ul>
        </div>
      </FModalSection>


                <FVerticalStack gap="4" v-if="eventoSelected.estado.codigo == 9">
                  <FText id="estadoLbl" as="h6" variant="bodyMd" fontWeight="semibold" alignment="justify" style="margin-left: 20px; margin-top: 25px;">
                    Adjuntar Documentos:
                  </FText>
                  <FileUpload
                      ref="fileUpload"
                      name="file"
                      accept=".pdf, .jpg, .jpeg, .png"
                      multiple
                      class="f"
                      :maxFileSize="10485760"
                      :chooseLabel="'Seleccionar archivos'"
                      :onSelect="handleFileSelect"
                      
                  />
                  <div v-if="documentosBitacoraList.length > 0">
                    <h3 style="margin-left: 20px; margin-top: 25px;">Documentos guardados:</h3>
                    <ul>
                      <li
                          v-for="(documento, index) in documentosBitacoraList"
                          :key="documento.doc_nombre_archivo"
                      >
                        <a
                            :href="
                            createDownloadLink(
                              documento.doc_archivo,
                              documento.doc_nombre_archivo
                            )
                          "
                            :download="documento.doc_nombre_archivo"
                        >
                          {{ documento.doc_nombre_archivo }}
                        </a>
                        <FButton
                            plain
                            destructive
                            size="micro"
                            :icon="TrashCanSolid"
                            @click="deleteFile(index)"
                            style="margin-left: 2rem; margin-top: 1rem; align-items: end"
                            
                        >Eliminar</FButton
                        >
                        <FDivider :border-width="'4'" />
                      </li>
                    </ul>
                  </div>
                </FVerticalStack>

                <FVerticalStack gap="4">
                  <FTooltip
                      activator-wrapper="div"
                      content="Sección para visualizar los documentos que lleguen a recepción que sean respuesta al mismo trámite."
                      placement="right"
                      width="wide">
                      <template #activator="{props}" >
                        <FHorizontalStack gap="1" v-bind="props">
                          <FText  id="estadoLbl" as="h6" variant="bodyMd" fontWeight="semibold" style="margin-left: 20px; margin-top: 25px;">
                            Documentos de respuesta al trámite (si aplica):
                          </FText>
                          <FHorizontalStack align="center" block-align="center" style="margin-top: 20px;">
                            <FIcon :source="CircleQuestionSolid" color="primary"/>
                          </FHorizontalStack>
                        </FHorizontalStack>
                      </template>
                    </FTooltip>
                  <FBox>
                    <div v-if="documentosRespuestaBitacoraList.length > 0">
                      <ul>
                        <li
                            v-for="(documento, index) in documentosBitacoraList"
                            :key="documento.doc_nombre_archivo"
                        >
                          <a
                              :href="createDownloadLink(documento.doc_archivo,documento.doc_nombre_archivo)"
                              :download="documento.doc_nombre_archivo">
                            {{ documento.doc_nombre_archivo }}
                          </a>
                          <FButton
                              plain
                              destructive
                              size="micro"
                              :icon="TrashCanSolid"
                              @click="deleteFile(index)"
                              style="margin-left: 2rem; margin-top: 1rem; align-items: end"
                              
                          >Eliminar</FButton
                          >
                          <FDivider :border-width="'4'" />
                        </li>
                      </ul>
                    </div>
                  <FText id="estadoLbl" as="h6" variant="bodyMd" color="subdued" fontWeight="semibold" style="margin-left: 50px; margin-top: 25px;" v-if="documentosRespuestaBitacoraList.length == 0" >
                      No se han adjuntado documentos de respuesta al trámite.
                  </FText>
                  </FBox>
                  <FDivider :border-width="'4'" />
                    
                </FVerticalStack>
              
      <FCardSection v-show="!desabilitarGuardarCambios">
        <FCardSubsection>
          <FFormLayout>
            <FFormLayoutGroup>
              <FVerticalStack gap="4">
                <FText id="estadoLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                  Estado:
                </FText>

                <Dropdown
                    v-model="estadoObj"
                    :options="estadosList"
                    optionLabel="estado_descripcion"
                    optionValue="codigo"
                    placeholder="Seleccione"
                    :disabled="desabilitarGuardarCambios"
                    :style="[estadoObjError != null ? { 'border-color': '#FF6767' } : {}]"
                />

                <FText
                    v-if="estadoObj == 7"
                    id="personaLbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                >
                  Destinatario:
                </FText>
                <FBox
                    v-if="estadoObj == 7"
                    background="bg"
                    padding="0"
                    borderWidth="1"
                    borderColor="border"
                    :style="[personaObjError != null ? { 'border-color': '#FF6767' } : {}]"
                >
                  <AutoComplete
                      v-model="personaObj"
                      optionLabel="nombreCompleto"
                      :suggestions="filteredItems"
                      @Complete="searchItem"
                      class="full-width-autocomplete"
                  />
                </FBox>
              </FVerticalStack>
            </FFormLayoutGroup>
          </FFormLayout>
        </FCardSubsection>
      </FCardSection>
    </FModal>

<!-- Documentos electronicos -->
    <FModal
        v-model="accionesDocumentosElectronicosModal"
        title=""
        title-hidden
        :primaryAction="{
        content: 'Confirmar',
        onAction: onSubmitRespuesta,
        disabled: (selectedTramite==0) || (selectedTramite ==2 && filesRespuesta.length == 0) ,
      }"
        :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeDocumentos,
        },
      ]"
    >
      <FCardSection>
        <FVerticalStack gap="4">
          <FText id="tramiteLbl" as="h6" variant="bodyMd" color="subdued" fontWeight="regular">
            Contestación del trámite:
          </FText>

          <Dropdown
                v-model="selectedTramite"
                :options="tramiteAccionList"
                optionLabel="label"
                optionValue="value"
                placeholder="Seleccione"
          />

          <FText id="tramiteLbl" as="h6" variant="bodyMd" color="success" fontWeight="semibold" v-if="selectedTramite == 1">
            Se enviará una notificacion al remitente informando la entrega de la documentación física
          </FText>

          <FVerticalStack gap="4" v-if="selectedTramite == 2">
            <FileUpload
              ref="fileUpload"
              name="file"
              accept=".pdf, .jpg, .jpeg, .png"
              multiple
              :maxFileSize="10485760"
              :auto="false"
              customUpload
              :onSelect="handleFileSelectRespuesta"
            />
          </FVerticalStack>

        </FVerticalStack>
      </FCardSection>
    </FModal>

<!-- modal para ver documentos -->

    <FModal
        v-model="modalVerDocumentos"
        title=""
        title-hidden
        :secondaryActions="[
        {
          content: 'Cerrar',
          onAction: handleChangeVerDocumentos,
        },
      ]"
    >
    <FCardSection>
      <FText id="docRecibidosLbl" as="h2" variant="bodyLg" font-weight="regular" color="subdued">Documentos recibidos:</FText>
  <FVerticalStack gap="4">
    <div v-if="documentosExternosRecibidos.length > 0">
      <ul>
        <li
          v-for="(documento, index) in documentosExternosRecibidos"
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

    <FVerticalStack gap="1" >
    <FText id="docRecibidosLbl" as="h2" variant="bodyLg" font-weight="regular" color="subdued">Documentos enviados:</FText>

    <div v-if="documentosExternosRespuesta.length > 0">
      <ul>
        <li
          v-for="(documento, index) in documentosExternosRespuesta"
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
  </FVerticalStack>
  
</FCardSection>
    </FModal>

<!-- modal para editar bitácoras-->

<FModal
        v-model="createModal"
        title=""
        title-hidden
        large
        :primaryAction="{
        content: 'Guardar Cambios',
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
            >
              {{ fechaSumillaView }}
            </FText>
            <FText
                as="h5"
                variant="bodyMd"
                :font-weight="'regular'"
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
                id="numHojas"
                :error="numHojasError"
            />
          </FHorizontalStack>
        </FVerticalStack>
      </FModalSection>

      <FModalSection>
        <FVerticalStack gap="4" align="center">
          <FText id="remitenteNombreLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Nombres remitente:
          </FText>
          <FTextField
              id="remitenteNombre"
              v-model="nombres_remitente"
              :error="nombres_remitenteError"
          />
          <FText id="remitenteApellidoLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Apellidos remitente:
          </FText>
          <FTextField
              id="remitenteApellido"
              v-model="apellidos_remitente"
              :error="apellidos_remitenteError"
          />
          
          <FHorizontalStack gap="8">
            <FText as="h6" variant="bodyLg" fontWeight="semibold"  >
            Es un mensajero externo?:
            </FText>
            <ToggleButton v-model="checked" onLabel="Si" offLabel="No" />
          </FHorizontalStack>

          <FText id="mensajeroLbl" as="h6" variant="bodyMd" fontWeight="semibold" >
            {{ checked==true ? 'Mensajero externo' : 'Mensajero interno' }}
          </FText>

          <FTextField
              id="mensajeroExterno"
              v-model="mensajeroExterno"
              :error="mensajeroExternoError"
              v-if="checked==true"
          />

          <FBox
              background="bg"
              padding="0"
              borderWidth="1"
              borderColor="border"
              style="border-radius: 5px"
              :style="[mensajeroError != null ? { 'border-color': '#FF6767' } : {}]"
              v-if="checked==false"
          >
            <AutoComplete
                v-model="mensajero"
                optionLabel="nombreCompleto"
                :suggestions="filteredItems"
                @Complete="searchItem"
                class="full-width-autocomplete"
            />
          </FBox>

          <FText id="numeroGuiaLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Número de guia:
          </FText>
          <FTextField
              id="numeroGuia"
              v-model="bitacora.numero_guia"
          />

          <FText id="observacionesLbl" as="h6" variant="bodyMd" fontWeight="semibold">
            Observaciones:
          </FText>
          <FTextField
              id="observaciones"
              v-model="bitacora.observaciones"
          />

          <FDivider border-width="5" border-color="border-inverse"/>
           <FVerticalStack gap="4">

              <!-- <FHorizontalStack gap="8">
                <FText as="h6" variant="bodyLg" fontWeight="bold"  >
                  Pertenece el documento a otra sede?:
                </FText>
                <ToggleButton v-model="checkedReasignacion" onLabel="Si" offLabel="No" />
              </FHorizontalStack> -->

                <FText
                    id="destinatarioLbl"
                    as="h6"
                    variant="bodyMd"
                    fontWeight="semibold"
                >
                  Destinatario:
                </FText>
                <FBox
                    background="bg"
                    padding="0"
                    borderWidth="1"
                    borderColor="border"
                    style="border-radius: 5px"
                    :style="[destinatarioError != null ? { 'border-color': '#FF6767' } : {}]"
                >
                  <AutoComplete
                      v-model="destinatario"
                      optionLabel="nombreCompleto"
                      :suggestions="filteredItems"
                      class="full-width-autocomplete"
                      @Complete="searchItem"
                  />
                </FBox>

                <FText id="asuntolbl" as="h6" variant="bodyMd" fontWeight="semibold">
                  Asunto:
                </FText>
                <FTextField
                    id="asunto"
                    v-model="asunto"
                    :error="asuntoError"
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
                    v-model="lugar_destino"
                    :error="lugar_destinoError"
                />

                <FVerticalStack gap="4">
                  <FileUpload
                      ref="fileUpload"
                      name="file"
                      accept=".pdf, .jpg, .jpeg, .png"
                      multiple
                      class="f"
                      :maxFileSize="10485760"
                      :chooseLabel="'Seleccionar archivos'"
                      :onSelect="handleFileSelect"
                  />
                  <div v-if="documentosBitacoraList.length > 0">
                    <h3>Documentos guardados:</h3>
                    <ul>
                      <li
                          v-for="(documento, index) in documentosBitacoraList"
                          :key="documento.doc_nombre_archivo"
                      >
                        <a
                            :href="
                            createDownloadLink(
                              documento.doc_archivo,
                              documento.doc_nombre_archivo
                            )
                          "
                            :download="documento.doc_nombre_archivo"
                        >
                          {{ documento.doc_nombre_archivo }}
                        </a>
                        <FButton
                            plain
                            destructive
                            size="micro"
                            :icon="TrashCanSolid"
                            @click="deleteFile(index)"
                            style="margin-left: 2rem; margin-top: 1rem; align-items: end"
                        >Eliminar</FButton
                        >
                        <FDivider :border-width="'4'" />
                      </li>
                    </ul>
                  </div>
                </FVerticalStack>
              </FVerticalStack>

          <FDivider border-width="5" border-color="border-inverse" />
            <FHorizontalStack gap="12">
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
              {{ sumilla?.hora_sumilla }}
              </FText>
            </FHorizontalStack>

            <FVerticalStack gap="4">
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

                <FTextField
                    id="horaEntrega"
                    type="time"
                    v-model="bitacora.hora_entrega"
                />


              <FText
                  id="personaEntregaLbl"
                  as="h6"
                  variant="bodyMd"
                  fontWeight="semibold"
              >
                Persona que entrega:
              </FText>

              <AutoComplete
                  class="full-width-autocomplete"
                  v-model="bitacora.usr_emisor"
                  optionLabel="nombreCompleto"
                  :suggestions="filteredItems"
                  @Complete="searchItem"
              />

              <FText id="personaRecibeLbl" as="h6" variant="bodyMd" fontWeight="semibold">
                Persona que recibe:
              </FText>
              <AutoComplete
                  class="full-width-autocomplete"
                  v-model="bitacora.usr_receptor"
                  optionLabel="nombreCompleto"
                  :suggestions="filteredItems"
                  @Complete="searchItem"
              />
              <FModalSection >
                <FVerticalStack gap="4">
                  <FDivider border-width="5" border-color="border-inverse"/>
                  <FText id="personaRecibeLbl" as="h6" variant="bodyLg" fontWeight="semibold">
                    Respuesta al trámite:
                  </FText>

                  <FVerticalStack gap="4">
                  <FileUpload
                      ref="fileUpload"
                      name="file"
                      accept=".pdf, .jpg, .jpeg, .png"
                      multiple
                      class="f"
                      :maxFileSize="10485760"
                      :chooseLabel="'Seleccionar archivos'"
                      :onSelect="handleFileSelectDocumentosRespuesta"
                  />
                  <div v-if="docBitacoraListRespuesta.length > 0">
                    <h3>Documentos de respuesta al trámite:</h3>
                    <ul>
                      <li
                          v-for="(documento, index) in docBitacoraListRespuesta"
                          :key="documento.doc_nombre_archivo">

                        <a :href="createDownloadLink(documento.doc_archivo,documento.doc_nombre_archivo)"
                            :download="documento.doc_nombre_archivo">
                          {{ documento.doc_nombre_archivo }}
                        </a>
                        <FButton
                            plain
                            destructive
                            size="micro"
                            :icon="TrashCanSolid"
                            @click="deleteFileDocsRespuesta(index)"
                            style="margin-left: 2rem; margin-top: 1rem; align-items: end">
                          Eliminar
                        </FButton>
                        <FDivider :border-width="'4'" />
                      </li>
                    </ul>
                  </div>
                </FVerticalStack>
                </FVerticalStack>

              </FModalSection>
            </FVerticalStack>
        </FVerticalStack>
      </FModalSection>
    </FModal>

    <FToast v-model="mostrarMsgError" :content=mensajeToast error :duration="5000" />
    <FToast v-model="mostrarMsgCorrecto" :content=mensajeToast  :duration="5000" />
  </FCard>
</template>
<script lang="ts" setup>
import {
  MagnifyingGlassSolid,
  TrashCanSolid,
  PlusSolid,
  MinusSolid,
  HouseSolid,
  LayerGroupSolid,
  PencilSolid,
  UserSolid,
  CaretDownSolid,
  CircleQuestionSolid
} from "@ups-dev/freya-icons";
import AutoComplete from "primevue/autocomplete";
import {useBitacorasDestinatariosComposable} from "~/composables/bitacoras/useBitacorasDestinatariosComposable";
import type {Estado} from "~/models/Estado.model";
import type {Persona} from "~/models/Sumilla.model";
import type {EventoBitacora} from "~/models/EventoBitacora.model";
import { useField, useForm} from 'vee-validate';
import {required, toTypedSchema} from "@vee-validate/rules";
import * as yup from 'yup';
import type { BitacoraExternos } from "~/models/BitacoraExternos.model";
import type { DocumentosExternos } from '../../models/DocumentosExternos.model';
import { useSumillaComposable } from "~/composables/sumillas/useSumillaComposable";
import type { DocumentoBitacora } from "~/models/DocumentoBitacora.model";
import type { Bitacora } from '../../models/Bitacora.model';

const {
  eventosBitacorasList,
  userLogin,
  documentosBitacoraList,
  getAllEventosByBitCodigo,
  getAllEstados,
  saveEventoBitacora,
  sendEmail,
  getDocumentosByBitCodigo,
  bitacorasExternasList,
  saveDocumentoExterno,
  getDocumentoExternoByBidCodigoRecibidos,
  getDocumentoExternoByBidCodigoRespuesta,
  editBitacoraElectronica,
  sendEmailSolDocumentacionFisica,
  sendEmailRespuestaElectronicaRemitente,
  enviarMailDocumentacionFisicaReasignada,
  findBitacorasDestinatarios
} = useBitacorasDestinatariosComposable();

const {getPersonasByFilterName, 
  deleteDocumentosByBitCodigo, 
  saveDocumentoBitacora, 
  getDocsRespuestaTramiteByBitCodigo,
  nombres_remitente,
  nombres_remitenteError,
  resetnombres_remitente,
  apellidos_remitente,
  apellidos_remitenteError,
  resetapellidos_remitente,
  lugar_destino,
  lugar_destinoError,
  resetlugar_destino,
  destinatario,
  destinatarioError,
  resetdestinatario,
  mensajero,
  mensajeroError,
  resetmensajero,
  asunto,
  asuntoError,
  resetasunto,
  checked,
  mensajeroExterno,
  mensajeroExternoError,
  resetMensajeroExterno,
  checkedReasignacion,
  sede,
  sumilla,
  numHojas,
  numHojasError,
  resetNumHojas,
  bitacora,
  fechaEntrega,
  docBitacoraListRespuesta,
  prepareEdit,
  persistAction,
  createModal,
  appRoles,
  editSumilla,
  findSumillas,
  editBitacora,
  findBitacoras,
  handleSubmit : handleEditSubmit,
  filtersSumillaBitacora,
} = useSumillaComposable();

const eventoSelected = ref<EventoBitacora>({} as EventoBitacora);
const accionesModal = ref<boolean>(false);
const eventosBitacorasAcciones = ref<EventoBitacora[]>([]);
const estadosList = ref<Estado[]>([]);
const desabilitarGuardarCambios = ref<boolean>();
const mostrarMsgCorrecto = ref<boolean>(false);
const mostrarMsgError = ref<boolean>(false);
const mensajeToast = ref<string>('');
const selected = ref(0);
const accionesDocumentosElectronicosModal = ref<boolean>(false);
const documentosExternosRecibidos = ref<DocumentosExternos[]>([]);
const documentosExternosRespuesta = ref<DocumentosExternos[]>([]);
const modalVerDocumentos = ref<boolean>(false);
const filesRespuesta = ref<File[]>([]); // Definir files como un ref que es un arreglo de File
const bitacoraExternaSelected = ref<BitacoraExternos>({} as BitacoraExternos);
const documentoExternoObj = ref<DocumentosExternos>({} as DocumentosExternos);
const files = ref<File[]>([]); // Definir files como un ref que es un arreglo de File
const documentObj = ref<DocumentoBitacora>({} as DocumentoBitacora);
const documentosRespuestaBitacoraList = ref<DocumentoBitacora[]>([]);
const changeEditModal = ref<boolean>(false);
const fechaSumillaView = ref();

  const { data } = useAuth();

const tramiteAccionList = ref(
    [   { label: 'RESPUESTA FÍSICA', value: 1 },
        { label: 'RESPUESTA ELECTRÓNICA', value: 2 },
    ]);
const selectedTramite = ref<number>(0);

interface TabDescriptor {
  id: string;
  content: string;
}

const handleFileSelectRespuesta = (event: any) => {
  filesRespuesta.value = event.files;
};

const deleteFile = async (index: any) => {
  const documento: DocumentoBitacora = documentosBitacoraList.value[index];
  try {
    await deleteDocumentosByBitCodigo(documento.bitacora.codigo); // Asegúrate de que tu API soporte esto
    documentosBitacoraList.value.splice(index, 1);
  } catch (error) {
    console.error("Error eliminando el archivo:", error);
  }
};

const tabs: TabDescriptor[] = [
  {
    id: "crear-sumilla",
    content: "Documentos Físicos",
  },
  {
    id: "buscar-sumilla",
    content: "Documentos electrónicos",
  },
];

const handleFileSelect = (event: any) => {
  files.value = event.files;
};

const handleChangeDocumentos = ()=>{
  accionesDocumentosElectronicosModal.value = !accionesDocumentosElectronicosModal.value;
}

const prepareAccionesDocumentosElectronicos = (bitacoraExterna:BitacoraExternos) => {
  selectedTramite.value = 0;
  filesRespuesta.value = [];
  bitacoraExternaSelected.value = bitacoraExterna;
  handleChangeDocumentos();
}

const handleChangeVerDocumentos = () =>{
  modalVerDocumentos.value = !modalVerDocumentos.value;
}

const prepareVerDocumentos = async (bitacoraExterna:BitacoraExternos) =>{
  filesRespuesta.value = [];
  documentosExternosRecibidos.value = await getDocumentoExternoByBidCodigoRecibidos(bitacoraExterna.codigo);
  documentosExternosRespuesta.value = await getDocumentoExternoByBidCodigoRespuesta(bitacoraExterna.codigo)
  handleChangeVerDocumentos();
}

const { handleSubmit, resetForm } = useForm({
  validationSchema: yup.object({
    estadoObj: yup.number().required(),
    personaObj: yup
        .object()
        .nullable()
        .when('estadoObj', {
          is: (val:number) => val === 7,
          then: (schema:any) => schema.required('Persona es requerida cuando estado es 7'),
          otherwise: (schema:any) => schema.nullable(),
        }),
  }),
});

const {
  value: estadoObj,
  errorMessage: estadoObjError,
  resetField: resetEstadoObj,
} = useField<number>("estadoObj", {
  // required: true,
});

const {
  value: personaObj,
  errorMessage: personaObjError,
  resetField: resetPersonaObj,
} = useField<Persona>("personaObj", {
  // required: isRequired.value,
});

watch(
    () => estadoObj.value,
    () => {
      if (estadoObj.value != 7) {
        resetPersonaObj();
      }
    }
);

const filteredItems = ref<Persona[]>([]);
const receptorPersonaList = ref<Persona[]>([]);

const searchItem = async(event: any) => {
  const query = event.query.toLowerCase();
  receptorPersonaList.value = await getPersonasByFilterName(query);
  filteredItems.value = receptorPersonaList.value.filter(
      (item:any) =>
          item.per_nombres.toLowerCase().includes(query) ||
          item.per_apellidos.toLowerCase().includes(query)
  );
};


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

const handleChangeAcciones = () => {
  resetEstadoObj();
  resetPersonaObj();
  accionesModal.value = !accionesModal.value;
};


const handleChangeCreateModal = () => {
  createModal.value = !createModal.value;
};


const completeObjectBitacora = () =>{
  bitacora.value.nombres_remitente = nombres_remitente.value;
  bitacora.value.apellidos_remitente = apellidos_remitente.value;
  bitacora.value.mensajero = mensajero.value;
  bitacora.value.destinatario = destinatario.value;
  bitacora.value.asunto = asunto.value;
  bitacora.value.lugar_destino = lugar_destino.value;
  bitacora.value.mensajero_externo = mensajeroExterno.value;
  bitacora.value.documento_reasignado = checkedReasignacion.value;
}

const saveDocumentosRespuesta = async () => {
  if (filesRespuesta.value.length > 0) {
    try {
      // Procesar cada archivo
      for (const file of filesRespuesta.value) {
        const reader = new FileReader();
        reader.onload = async (e) => {
          try {
            const result = e.target!.result as ArrayBuffer;
            const byteArray = new Uint8Array(result);

            // Convertir Uint8Array a byte[]
            const byteArrayJava = Array.from(byteArray);
            documentObj.value.bitacora = {} as Bitacora;
            documentObj.value.codigo = 0;
            documentObj.value.doc_archivo = byteArrayJava;
            documentObj.value.doc_nombre_archivo = file.name;
            documentObj.value.bitacora.codigo = bitacora.value.codigo;
            documentObj.value.adicionado = bitacora.value.adicionado;
            documentObj.value.estado_tramite = 'R'; // * C: creado - R: Respuesta al trámite 

            await saveDocumentoBitacora(documentObj.value);
          } catch (error) {
            console.error("Error processing file:", error);
            // Puedes manejar el error aquí si es necesario
          }
        };
        reader.onerror = (error) => {
          console.error("Error reading file:", error);
        };
        reader.readAsArrayBuffer(file);
      }
    } catch (error) {
      console.error("Error processing files:", error);
      // Puedes agregar más lógica de manejo de errores aquí si es necesario
    }
  }
};

const onSubmited = handleEditSubmit(async (values:any) => {
    sumilla.value.numero_hojas = Number(numHojas.value);
    await editSumilla(sumilla.value, sumilla.value.codigo!);
    completeObjectBitacora();

    await editBitacora(bitacora.value, bitacora.value.codigo);
    if (files.value.length > 0) {
      await saveDocumentos();
    }
    if(filesRespuesta.value.length > 0){
      await saveDocumentosRespuesta();
    }

    await findBitacorasDestinatarios();

    createModal.value = !createModal.value;
    resetNumHojas();
    mostrarMsgCorrecto.value = true;
    mensajeToast.value = "La sumilla se editó correctamente";
});


const handleFileSelectDocumentosRespuesta = (event: any) => {
  filesRespuesta.value = event.files;
};

const deleteFileDocsRespuesta = async (index: any) => {
  const documento: DocumentoBitacora = docBitacoraListRespuesta.value[index];
  try {
    await deleteDocumentosByBitCodigo(documento.bitacora.codigo); 
    docBitacoraListRespuesta.value.splice(index, 1);
  } catch (error) {
    console.error("Error eliminando el archivo:", error);
  }
};

//prepare documentos físicos
const prepareAcciones = async (eventoParam: EventoBitacora) => {
  documentosBitacoraList.value = await getDocumentosByBitCodigo(eventoParam.bitacora.codigo);
  documentosRespuestaBitacoraList.value = await getDocsRespuestaTramiteByBitCodigo(eventoParam.bitacora.codigo);
  desabilitarGuardarCambios.value = false;
  eventoSelected.value = eventoParam;
  eventosBitacorasAcciones.value = await getAllEventosByBitCodigo(
      eventoParam.bitacora.codigo
  );
  estadosList.value = await getAllEstados();

  const lastEvent: EventoBitacora =
      eventosBitacorasAcciones.value[eventosBitacorasAcciones.value.length - 1];
  estadoObj.value = lastEvent.estado.codigo;
  switch (lastEvent.estado.codigo) {
    case 3:
      desabilitarGuardarCambios.value = true;
      handleChangeAcciones();
      break;
    case 7:
      desabilitarGuardarCambios.value =
          lastEvent.per_codigo_reasignado.codigo != userLogin.value.codigo ? true : false;
      handleChangeAcciones();
      break;
    default:
      handleChangeAcciones();
  }
};

const onSubmitRespuesta = async() =>{
  switch (selectedTramite.value) {
    case 1:   //RESPUESTA FISICO
      bitacoraExternaSelected.value.estado = 5; // estado => se solicita físico
      await editBitacoraElectronica(bitacoraExternaSelected.value);
      await sendEmailSolDocumentacionFisica(bitacoraExternaSelected.value);  
      handleChangeDocumentos(); 
      break;
    case 2:   //RESPUESTA ELECTRÓNICA
      bitacoraExternaSelected.value.estado = 6; // estado => se dió una respuesta al remitente
      await editBitacoraElectronica(bitacoraExternaSelected.value);   
      await saveDocumentos();
      await sendEmailRespuestaElectronicaRemitente(bitacoraExternaSelected.value);
      handleChangeDocumentos(); 
      break;
  }
}

const saveDocumentos = async () => {
  if (filesRespuesta.value.length > 0) {
    try {
      // Procesar cada archivo
      for (const file of filesRespuesta.value) {
        const reader = new FileReader();
        reader.onload = async (e) => {
          try {
            const result = e.target!.result as ArrayBuffer;
            const byteArray = new Uint8Array(result);

            // Convertir Uint8Array a byte[]
            const byteArrayJava = Array.from(byteArray);
            // documentoExternoObj.value = await getDocumentoExternoByBidCodigo(bitacoraExternaSelected.value.codigo);
            documentoExternoObj.value.documentos_externos = bitacoraExternaSelected.value;
            documentoExternoObj.value.doe_archivo = byteArrayJava;
            documentoExternoObj.value.doe_nombre_archivo = file.name;
            documentoExternoObj.value.estado_documento_electronico = 'R';
            documentoExternoObj.value.adicionado = data.value?.user?.email!;
            await saveDocumentoExterno(documentoExternoObj.value);
            // await saveDocumentoBitacora(documentObj.value);
          } catch (error) {
            console.error("Error processing file:", error);
            // Puedes manejar el error aquí si es necesario
          }
        };
        reader.onerror = (error) => {
          console.error("Error reading file:", error);
        };
        reader.readAsArrayBuffer(file);
      }
    } catch (error) {
      console.error("Error processing files:", error);
      // Puedes agregar más lógica de manejo de errores aquí si es necesario
    }
  }
};

const onSubmitAcciones = handleSubmit(async (values:any) => {
  if (personaObj.value == null && estadoObj.value === 7) {
    mostrarMsgError.value = true;
    mensajeToast.value = 'Seleccione un destinatario';
  } else {
    switch (estadoObj.value) {
      case 4: // APROBADO
        eventoSelected.value.estado.codigo = 4;
        eventoSelected.value.codigo = 0;
        eventoSelected.value.per_codigo_responsable.codigo = userLogin.value.codigo;
        await saveEventoBitacora(eventoSelected.value);
        mostrarMsgCorrecto.value = true;
        mensajeToast.value = 'Se aprobó el documento';
        handleChangeAcciones();
        break;
      case 3: // NO APROBADO - SOLICITAR DOCUMENTACIÓN FÍSICA
        eventoSelected.value.estado.codigo = 3;
        eventoSelected.value.codigo = 0;
        eventoSelected.value.per_codigo_responsable.codigo = userLogin.value.codigo;
        await saveEventoBitacora(eventoSelected.value);
        enviarEmail(eventoSelected.value);
        handleChangeAcciones();
        mostrarMsgCorrecto.value = true;
        mensajeToast.value = 'Se solicitó documentación física';
        break;
      case 7:
        if (files.value.length > 0) {
         await saveDocumentosReasignados();
        }
        eventoSelected.value.per_codigo_reasignado = {} as Persona;
        eventoSelected.value.estado.codigo = 7;
        eventoSelected.value.codigo = 0;
        eventoSelected.value.per_codigo_responsable.codigo = userLogin.value.codigo;
        eventoSelected.value.per_codigo_reasignado.codigo = personaObj.value.codigo;
        await saveEventoBitacora(eventoSelected.value);
        mostrarMsgCorrecto.value = true;
        mensajeToast.value = 'Se reasignó el documento correctamente';
        await enviarMailDocumentacionFisicaReasignada(eventoSelected.value);
        handleChangeAcciones();
        break;
    }
  }
});

const saveDocumentosReasignados = async () => {
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
            documentObj.value.bitacora = {} as Bitacora;
            documentObj.value.codigo = 0;
            documentObj.value.doc_archivo = byteArrayJava;
            documentObj.value.doc_nombre_archivo = file.name;
            documentObj.value.bitacora.codigo = eventoSelected.value.bitacora.codigo;
            documentObj.value.adicionado = eventoSelected.value.bitacora.adicionado;
            documentoExternoObj.value.estado_documento_electronico = 'C';
            
            await saveDocumentoBitacora(documentObj.value);
          } catch (error) {
            console.error("Error processing file:", error);
            // Puedes manejar el error aquí si es necesario
          }
        };
        reader.onerror = (error) => {
          console.error("Error reading file:", error);
        };
        reader.readAsArrayBuffer(file);
      }
    } catch (error) {
      console.error("Error processing files:", error);
      // Puedes agregar más lógica de manejo de errores aquí si es necesario
    }
  }
};

const enviarEmail = async (evento: EventoBitacora) => {
  await sendEmail(evento);
};

const open = ref(false);
const handleToggle = () => (open.value = !open.value);
const transition = {
  duration: "var(--f-motion-duration-150)",
  timingFunction: "var(--f-motion-ease-in-out)",
};
</script>
<style lang="css">

.texto-superior-derecha {
  position: absolute;
  top: 0;
  right: 0;
  margin: 10px; /* margen opcional para separarlo un poco de los bordes */
}

.f .p-fileupload-buttonbar {
  display: none;
}

.p-fileupload .p-fileupload-buttonbar {
  display: none;
}

.hidden {
  list-style-type: none;
  display: none;
}
</style>
