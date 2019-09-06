package properties

import org.scalacheck.Prop.{forAll, BooleanOperators}
import org.scalacheck.Properties

object ConditionalProperties extends Properties("conditionalProperties") {
  property("makeList") = forAll { n: Int =>
    (n >= 0 && n < 1000) ==> (List.fill(n)("").length == n)
  }
//  property("Trivial") = forAll { n: Int =>
//    (n == 0) ==> (n == 0)
//  }
}
