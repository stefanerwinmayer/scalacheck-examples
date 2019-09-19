package generators

import scala.util.Random

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

class GeneratingContainers extends Properties("generatingContainers") {
  val genIntList = Gen.containerOf[List, Int](Gen.oneOf(1, 3, 5))
  val genStringLazyList = Gen.containerOf[Stream, String](Gen.alphaStr)
  val genBoolArray = Gen.containerOf[Array, Boolean](true)
  val zeroOrMoreDigits = Gen.someOf(1 to 9)
  val oneOrMoreDigits = Gen.atLeastOne(1 to 9)
  val fiveDice: Gen[Seq[Int]] = Gen.pick(5, 1 to 6)
  val threeLetters: Gen[Seq[Char]] = Gen.pick(3, 'A' to 'Z')
  val threeLettersPermuted = threeLetters.map(Random.shuffle(_))

  property("listContainsOneThreeFiveOnly") = forAll(genIntList) { ns =>
    ns == ns.filter { List(1, 3, 5).contains }
  }

  property("arrayContainsTrueFalseOnly") = forAll(genBoolArray) { bs =>
    bs.sameElements {
      bs.filter { Set(true, false).contains }
    }
  }

  property("seqContainsNumbersBetweenOneToNineOnly") =
    forAll(zeroOrMoreDigits) { ns =>
      ns == ns.filter { (1 to 9).contains }
    }

  property("seqIsNeverEmpty") = forAll(oneOrMoreDigits) { ns =>
    ns.nonEmpty
  }

  property("seqIsAlwaysFiveNumbers") = forAll(fiveDice) { dice =>
    dice.length == 5
  }

  property("seqContainsUpperCaseLettersOnly") = forAll(threeLetters) { c =>
    c == c.filter { ('A' to 'Z').contains }
  }
}
