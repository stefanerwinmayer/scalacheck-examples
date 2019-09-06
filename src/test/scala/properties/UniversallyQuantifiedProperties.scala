package properties

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Prop, Properties}

object UniversallyQuantifiedProperties
    extends Properties("universallyQuantifiedProperties") {

  property("reverseList") = forAll { l: List[String] =>
    l.reverse.reverse == l
  }
  property("concatString") = forAll { (s1: String, s2: String) =>
    (s1 + s2).endsWith(s2)
  }

  val smallInteger = Gen.choose(0, 100)
  property("smallInteger") = Prop.forAll(smallInteger) { n =>
    n >= 0 && n <= 100
  }
}
