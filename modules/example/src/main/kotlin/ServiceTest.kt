import arrow.Kind
import higherkindness.mu.example.models.Models.Person
import higherkindness.mu.meta.rpc.protocol.Empty
import higherkindness.mu.service
@service
interface PersonServiceAvro<F, A> : Kind<F, A> {

  fun listPersons(empty: Empty): Kind<F, MutableList<Person>>

  fun getPerson(id: String): Kind<F, Person>

  fun getPersonLinks(id: String): Kind<F, List<Person>>

  fun createPerson(person: Person): Kind<F, Person>

  enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
  }
}
