<template>
<FCard sectioned>
<FFormLayout>

    <FLayoutSection>
        <FCard sectioned>
            <FText as="h5" variant="HeadingMd" :font-weight="'semibold'" style="text-align: center;">
                --CAMPUS--
            </FText>
            <FText as="h6" variant="headingMd" style="text-align: center;">
                {{$t('app.sgadNuxt.sumilla.title') }}
            </FText>
        </FCard>
      </FLayoutSection>

      <FLayoutSection>
        <FCard sectioned >
            <FHorizontalStack gap="4" align="center">
                    <FText as="h5" variant="bodyMd" :font-weight="'semibold'"  >
                        {{ $t('app.sgadNuxt.sumilla.fecha') }}
                    </FText>
                    <FText as="h5" variant="bodyMd" :font-weight="'regular'" >
                        --fecha--
                    </FText>

                    <FText as="h5" variant="bodyMd" :font-weight="'semibold'" >
                        {{ $t('app.sgadNuxt.sumilla.hora') }}
                    </FText>
                    <FText as="h5" variant="bodyMd" :font-weight="'regular'" >
                        --hora--
                    </FText>
            </FHorizontalStack>

        </FCard>
      </FLayoutSection>

    <!-- <FTextField label="Nombre" autoComplete="off"/>
    <FTextField type="email"
                label="Correo electrónico"
                autoCoTmplete="email"/> -->
</FFormLayout>
</FCard>
</template>
<script setup lang="ts">

</script>
<style lang="css"></style>