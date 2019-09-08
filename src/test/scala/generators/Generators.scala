package generators

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object Generators extends Properties("generators") {
  val myGen = for {
    n <- Gen.choose(10, 20)
    m <- Gen.choose(2 * n, 500)
  } yield (n, m)

  val vowel = Gen.oneOf('A', 'E', 'I', 'O', 'U')

  val vowel_ = Gen.frequency(
    (3, 'A'),
    (4, 'E'),
    (2, 'I'),
    (3, 'O'),
    (1, 'U'),
    (1, 'Y')
  )

  property("atLeastDouble") = forAll(myGen) {
    case (n, m) => (n * 2) <= m
  }

  property("vowelEvenDistribution") = forAll(vowel) { v =>
    Set('A', 'E', 'I', 'O', 'U').contains(v)
  }

  property("vowelUnevenDistribution") = forAll(vowel_) { v =>
    Set('A', 'E', 'I', 'O', 'U', 'Y').contains(v)
  }
}
