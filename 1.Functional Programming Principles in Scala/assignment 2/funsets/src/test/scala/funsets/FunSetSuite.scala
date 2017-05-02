package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains common elements of each set") {
    new TestSets {
      val set1: Set = (a:Int) => a > 1   && a <= 10
      val set2: Set = (a:Int) => a >= 10 && a <= 20
      val s = intersect(set1, set2)
      assert(contains(set1, 10), "Set1 contains 10")
      assert(contains(set2, 10), "Set2 contains 10")
      assert(contains(s, 10), "Intersection 10")
      assert(!contains(s, 9), "Intersection 9")
    }
  }

  test("diff contains difference of sets") {
    new TestSets {
      val set1: Set = (a:Int) => a > 1 && a <= 10
      val set2: Set = (a:Int) => a > 5 && a <= 10
      val s = diff(set1, set2)
      assert(contains(set1, 2), "Set1 contains 2")
      assert(!contains(set2, 2), "Set2 does not contain 2")
      assert(contains(s, 2), "Diff 2")
    }
  }

  test("filter contains elements that satisfy a set and a predicate") {
    new TestSets {
      val set1: Set = (a:Int) => a > 1   && a <= 10
      val set2: Set = (a:Int) => a >= 10 && a <= 20
      val s = filter(set1, set2)
      assert(contains(set1, 10), "Set1 contains 10")
      assert(contains(set2, 10), "Set2 contains 10")
      assert(contains(s, 10), "Filter 10")

      assert(contains(set1, 5), "Set1 contains 5")
      assert(!contains(set2, 5), "Set2 does not contain 5")
      assert(!contains(s, 5), "Filter 5")
    }
  }

  test("forall satisfies condition for all integers between -1000 and 1000"){
    new TestSets {
      val set1: Set = (a:Int) => a >= -bound
      val set2: Set = (a:Int) => a <= bound
      assert(forall(set1, set2))
    }
  }

  test("exists satisfies condition for at least one integer between -1000 and 1000"){
    new TestSets {
      val set1: Set = (a:Int) => a > 1 && a <= 10
      val set2: Set = (a:Int) => a >= 10 && a <= 20
      assert(exists(set1, set2))
    }
  }

  test("map") {
    new TestSets {
      val set1: Set = (a:Int) => a >= 1 && a < 5
      val set2: Set = map(set1, _*3)
      assert(contains(set2, 12))
      assert(!contains(set2, 15))
      assert(!contains(set2, 14))
    }
  }
}
