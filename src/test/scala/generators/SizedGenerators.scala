package generators

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.{forAll, BooleanOperators}
import org.scalacheck.{Gen, Properties}

object SizedGenerators extends Properties("sizedGenerators") {
  def matrix[T](g: Gen[T]): Gen[Seq[Seq[T]]] = Gen.sized { size =>
    val side = scala.math.sqrt(size).asInstanceOf[Int]
    Gen.listOfN(side, Gen.listOfN(side, g))
  }

  property("equallySized") = forAll(matrix(arbitrary[Int])) { matrix =>
    (matrix.size > 0) ==> (matrix.size == matrix.head.size)
  }
}
