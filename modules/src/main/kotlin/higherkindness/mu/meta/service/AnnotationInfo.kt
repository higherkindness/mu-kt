package higherkindness.mu.meta.service

import higherkindness.mu.service

val serviceAnnotationKClass = service::class
val serviceAnnotationClass = service::class.java
val serviceAnnotationName = "@" + serviceAnnotationClass.simpleName