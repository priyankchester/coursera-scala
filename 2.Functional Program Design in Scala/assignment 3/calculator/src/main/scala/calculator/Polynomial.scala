package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal {
      val a1 = a()
      val b1 = b()
      val c1 = c()
      b1*b1 - 4*a1*c1
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal {
      val deltaVal = Math.sqrt(delta())
      val denom = 2*a()
      val b1 = b()
      val sol1 = (-b1 + deltaVal) / denom
      val sol2 = (-b1 - deltaVal) / denom
      Set(sol1, sol2)
    }
  }
}
