package calculator

sealed abstract class Expr
final case class Literal(v: Double) extends Expr
final case class Ref(name: String) extends Expr
final case class Plus(a: Expr, b: Expr) extends Expr
final case class Minus(a: Expr, b: Expr) extends Expr
final case class Times(a: Expr, b: Expr) extends Expr
final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues( namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions map { case (variable, exprSig) =>
      (variable, Signal(eval(exprSig(), namedExpressions, Some(variable))))
    }
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]], baseOpt: Option[String]): Double = {
    val NAN = Signal(Literal(Double.NaN))

    expr match {
      case Literal(v) => v
      case Ref(name)  =>
        if(baseOpt.isDefined && name.equals(baseOpt.get))
          Double.NaN
        else
          eval(getReferenceExpr(name, references), references, baseOpt)
      case Plus   (a, b) =>  eval(a, references, baseOpt) + eval(b, references, baseOpt)
      case Minus  (a, b) =>  eval(a, references, baseOpt) - eval(b, references, baseOpt)
      case Times  (a, b) =>  eval(a, references, baseOpt) * eval(b, references, baseOpt)
      case Divide (a, b) =>
        if(eval(b, references, baseOpt) == 0)
          Double.NaN
        else
          eval(a, references, baseOpt) / eval(b, references, baseOpt)
    }
  }

  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String, references: Map[String, Signal[Expr]]): Expr = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
  }
}
