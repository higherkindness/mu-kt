package higherkindness.mu.meta

import arrow.common.utils.knownError
import arrow.meta.ast.Modifier
import arrow.meta.ast.Type
import arrow.meta.encoder.MetaApi
import arrow.meta.processor.MetaProcessor
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import higherkindness.mu.service
import javax.annotation.processing.Processor

@AutoService(Processor::class)
class ServiceProcessor : MetaProcessor<service>(service::class), MetaApi {

  private fun Type.fileSpecBuilder(): FileSpec.Builder {
    val builder = FileSpec.builder(
      packageName.value,
      name.simpleName
    )
    this.allFunctions.forEach {
      builder.addFunction(it
        .removeConstrains(keepModifiers = setOf(Modifier.Open)).lyrics())
    }
    return builder
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

