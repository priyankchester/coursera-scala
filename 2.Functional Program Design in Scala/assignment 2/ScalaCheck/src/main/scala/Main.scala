import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

object Main extends App {

  def checkReverse(s: String): Boolean = s == s.reverse

  val palindromeGen: Gen[String] = for {
    base <- arbitrary[String]
    middle <- Gen.option(arbitrary[Char])
  } yield base + middle.getOrElse("") + base.reverse

}
