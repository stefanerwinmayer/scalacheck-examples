package properties

import org.scalacheck.Prop.{forAll, all, atLeastOne}
import org.scalacheck.Properties

object CombiningProperties extends Properties("combiningProperties") {
  val p1 = forAll { n: Int =>
    n == n
  }
  val p2 = forAll { n: Int =>
    n != n
  }
  val p3 = p1 && p2
  val p4 = p1 || p2
  val p5 = p1 == p2
  val p6 = all(p1, p2) // same as p1 && p2
  val p7 = atLeastOne(p1, p2) // same as p1 || p2

  property("or") = p4
  property("atLeastOne") = p7
}
