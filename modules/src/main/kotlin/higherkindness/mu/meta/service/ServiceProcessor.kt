package higherkindness.mu.meta.service

import arrow.common.utils.knownError
import arrow.extensions.PolyTemplateGenerator
import arrow.meta.ast.Code
import arrow.meta.ast.Modifier
import arrow.meta.ast.Type
import arrow.meta.decoder.TypeDecoder
import arrow.meta.encoder.MetaApi
import arrow.meta.processor.MetaProcessor
import arrow.mtl.extensions.list.functorFilter.filter
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import higherkindness.mu.service
import javax.annotation.processing.Processor

@AutoService(Processor::class)
class ServiceProcessor : MetaProcessor<service>(service::class), MetaApi, PolyTemplateGenerator {

  private fun Type.fileSpecBuilder(): FileSpec.Builder {
    val fbuilder = FileSpec.builder(
      packageName.value,
      "${name.simpleName}Expanded"
    )
    return fbuilder.addType(copyInterface())
  }


  private fun Type.copyInterface(): TypeSpec {
    val ibuilder = TypeSpec.interfaceBuilder("${name.simpleName}Expanded")
    modifiers.forEach { ibuilder.addModifiers(it.lyrics()) }
    typeVariables.forEach { ibuilder.addTypeVariable(it.copy(bounds = listOf()).lyrics()) }
    properties.forEach { ibuilder.addProperty(it.lyrics()) }
    superInterfaces.forEach { ibuilder.addSuperinterface(it.lyrics()) }
    annotations.forEach { ibuilder.addAnnotation(it.lyrics()) }
    enumConstants.forEach { ibuilder.addEnumConstant(it.key, it.value.lyrics()) }
    types.forEach { ibuilder.addType(it.lyrics()) }
    declaredFunctions.forEach {
      ibuilder
        .addFunction(it.copy(
          modifiers = it.modifiers - Modifier.Override).lyrics()
        )
    }
    return ibuilder.build()
  }

  override fun transform(annotatedElement: AnnotatedElement): List<FileSpec.Builder> {
    return when (annotatedElement) {
      is AnnotatedElement.Interface -> {
        val type: Type = annotatedElement.type
        listOf(type.fileSpecBuilder())
      }
      is AnnotatedElement.Class ->
        knownError("""
            |$this is an invalid target for @service.
            |Generation of services is only supported for interfaces.
            """.trimMargin())
    }
  }
}

