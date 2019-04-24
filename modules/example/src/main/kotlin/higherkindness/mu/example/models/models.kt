package higherkindness.mu.example.models

import arrow.core.None
import arrow.core.Option
import arrow.core.Tuple7

typealias PersonAggregation = Tuple7<models.Person, models.Person, models.Person, models.Person, MutableList<models.Person>, MutableList<models.Person>, List<models.Person>>

object models {

  data class Person(
    val id: String,
    val name: PersonName,
    val gender: String,
    val location: Location,
    val email: String,
    val picture: Option<Picture>
  )

  data class PersonName(val title: String, val first: String, val last: String)
  data class Location(val street: String, val city: String, val state: String, val postCode: Int)
  data class Picture(val large: String, val medium: String, val thumbnail: String)
  data class PersonLink(val p1: Person, val p2: Person)

  data class DatabaseException(val maybeCause: Option<Throwable> = None) : RuntimeException(maybeCause.fold({ None.toString() }, { it.message }))

}