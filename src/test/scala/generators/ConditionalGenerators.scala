package generators

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

class ConditionalGenerators extends Properties("conditionalGenerators") {
  val smallEvenInteger = Gen.choose(0, 200) suchThat (_ % 2 == 0)

  property("smallAndEven") = forAll(smallEvenInteger) { n =>
    n >= 0 && n <= 200 && n % 2 == 0
  }
}
