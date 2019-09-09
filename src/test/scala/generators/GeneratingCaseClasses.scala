package generators

sealed abstract class Tree
case class Node(left: Tree, right: Tree, v: Int) extends Tree
case object Leaf extends Tree

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen.const
import org.scalacheck.Gen.oneOf
import org.scalacheck.Gen.lzy
import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

object GeneratingCaseClasses extends Properties("generatingCaseClasses") {

  val genLeaf = const(Leaf)
  val genNode = for {
    v <- arbitrary[Int]
    left <- genTree
    right <- genTree
  } yield Node(left, right, v)

  def genTree: Gen[Tree] = oneOf(genLeaf, lzy(genNode))

  // Produces stack overflow at times
//  property("eitherNodeOrLeaf") = forAll(genTree) {
//    case Leaf          => true
//    case Node(_, _, _) => true
//  }
}
