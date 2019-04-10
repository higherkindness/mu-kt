package higherkindness.mu

import arrow.Kind
import higherkindness.mu.example.models.models
import higherkindness.mu.example.models.models.Person
import higherkindness.mu.meta.rpc.protocol.Empty

@service
interface PersonServiceAvro<F, A> : Kind<F, A> {

  fun listPersons(empty: Empty): Kind<F, MutableList<Person>>

  fun getPerson(id: String): Kind<F, Person>

  fun getPersonLinks(id: String): Kind<F, List<Person>>

  fun createPerson(person: models.Person): Kind<F, Person>

}
