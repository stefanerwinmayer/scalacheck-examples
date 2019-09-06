package properties

import org.scalacheck.Prop.{all, forAll, propBoolean}
import org.scalacheck.Properties

object LabelingProperties extends Properties("labelingProperties") {

  val complexProp = forAll { (m: Int, n: Int) =>
    val res = n * m
    ("result > #1" |: res >= m) &&
    (res >= n) :| "result > #2" &&
    (res < m + n) :| "result not sum"
  }

  val propMul = forAll { (n: Int, m: Int) =>
    val res = n * m
    ("evidence = " + res) |: all(
      "div1" |: m != 0 ==> (res / m == n),
      "div2" |: n != 0 ==> (res / n == m),
      "lt1" |: res > m,
      "lt2" |: res > n
    )
  }
}
