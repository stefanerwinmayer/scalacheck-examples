package generators

import org.scalacheck.{Arbitrary, Properties}
import org.scalacheck.Gen
import org.scalacheck.Gen.oneOf
import org.scalacheck.Prop.forAll

class ArbitraryGenerator extends Properties("arbitraryGenerator") {
  val eventInteger = Arbitrary.arbitrary[Int] suchThat (_ % 2 == 0)

  val squares = for {
    xs <- Arbitrary.arbitrary[List[Int]]
  } yield xs.map(x => x * x)

  property("allIntegersShouldBeEvent") = forAll(eventInteger) { i =>
    i % 2 == 0
  }

  implicit lazy val arbBool: Arbitrary[Boolean] = Arbitrary(oneOf(true, false))

  abstract sealed class Tree[T] {
    def merge(t: Tree[T]) = Internal(List(this, t))

    def size: Int = this match {
      case Leaf(_)            => 1
      case Internal(children) => (children :\ 0)(_.size + _)
    }
  }

  case class Internal[T](children: Seq[Tree[T]]) extends Tree[T]

  case class Leaf[T](elem: T) extends Tree[T]

  implicit def arbTree[T](implicit a: Arbitrary[T]): Arbitrary[Tree[T]] =
    Arbitrary {
      val genLeaf = for (e <- Arbitrary.arbitrary[T]) yield Leaf(e)

      def genInternal(sz: Int): Gen[Tree[T]] =
        for {
          n <- Gen.choose(sz / 3, sz / 2)
          c <- Gen.listOfN(n, sizedTree(sz / 2))
        } yield Internal(c)

      def sizedTree(sz: Int) =
        if (sz <= 0) genLeaf
        else Gen.frequency((1, genLeaf), (3, genInternal(sz)))

      Gen.sized(sz => sizedTree(sz))
    }

  property("mergedTreeHasSameSizeAsSumOfItsTrees") = forAll {
    (t1: Tree[Int], t2: Tree[Int]) =>
      t1.size + t2.size == t1.merge(t2).size
  }
}
