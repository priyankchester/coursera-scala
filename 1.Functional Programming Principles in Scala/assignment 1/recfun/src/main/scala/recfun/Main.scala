package recfun

object Main {
  def main(args: Array[String]) {

    // Test Exercise 1
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }


    // Test Exercise 2
    val inputs = List("",
      "(if (zero? x) max (/ 1 x))",
      ":-)",
      "())(",
      "I told him (that it’s not (yet) done). (But he wasn’t listening)"
    )
    inputs.foreach { input =>
      println(s"Balance chars: $input => ${balance(input.toCharArray.toList)}")
    }

    // Test Exercise 3
    println(countChange(4, List(1, 2)))
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
    (c, r) match {
      case (0, _) => 1
      case (_, _) if c==r => 1
      case (_, _) => pascal(c-1, r-1) + pascal(c, r-1)
    }
  }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {

      def balanceHelper(chars: List[Char], count: Int): Boolean = {
        chars match {
          case head :: tail => {
            head match {
              case '(' => balanceHelper(tail, count+1)
              case ')' => if(count==0) false else balanceHelper(tail, count-1)
              case _ => balanceHelper(tail, count)
            }
          }
          case Nil => count == 0
        }
      }

    balanceHelper(chars, 0)
  }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      money match {
        case 0 => 1
        case x if x < 0 => 0
        case _ => coins match {
          case head :: tail => {
            countChange(money-head, coins) + countChange(money, tail)
          }
          case Nil => 0
        }
      }
  }
}
