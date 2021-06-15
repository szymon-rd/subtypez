package subtype.core

import scala.reflect.macros.whitebox.Context
import scala.collection.mutable.{ListBuffer, Stack}
import scala.language.experimental.macros
import scala.reflect.ClassTag

object Macros {

  trait SubsetCondition[T, R] {
    def condition(t: T): Boolean
  }

  class SubsetSuperclass[T, R] extends (T => R) {
    def apply(i: T): R = throw new UnsupportedOperationException
  }

  class SubsetConverter[T, R](condition: SubsetCondition[T, R]) extends SubsetSuperclass[T, R] {
    override def apply(i: T): R = macro convertToSubset_impl[T, R]
    type SupersetType = T
    def condition(t: T): Boolean = condition.condition(t)
  }

  class SubsetConverterWithCondition[T, R](scondition: T => Boolean) extends SubsetConverter(new SubsetCondition[T, R] {
    def condition(t: T): Boolean = scondition(t)
  }) with SubsetCondition[T, R]


  def convertToSubset_impl[T: c.WeakTypeTag, R: c.WeakTypeTag](c: Context)(i: c.Expr[T]): c.Expr[R] = {
    import c.universe._
    implicit val cc: c.type = c
    val subsetTypeTag = implicitly[c.WeakTypeTag[R]]
    val supersetTypeTag = implicitly[c.WeakTypeTag[T]]
    val x = c.inferImplicitValue(weakTypeOf[SubsetCondition[T, R]])
    if (x.isEmpty) {
      c.abort(c.enclosingPosition, s"Cannot convert from ${supersetTypeTag.tpe.typeSymbol} to ${subsetTypeTag.tpe.typeSymbol}.")
    }
    val y = c.eval(c.Expr[SubsetCondition[T, R]](c.untypecheck(x)))
    val expr = i.tree match {
      case Literal(Constant(v: T)) =>
        if (y != null) {
          if (y.condition(v)) {
            reify(c.Expr[T](i.tree).splice.asInstanceOf[R])
          } else {
            c.abort(c.enclosingPosition, s"Value ${v} isn't of type ${subsetTypeTag.tpe.typeSymbol}")
          }
        } else {
          c.abort(c.enclosingPosition, s"Cannot convert from ${supersetTypeTag.tpe.typeSymbol} to ${subsetTypeTag.tpe.typeSymbol}.")
        }
      case _ =>
        c.abort(c.enclosingPosition,
          s"Cannot perform conversion from ${supersetTypeTag.tpe.typeSymbol} to ${subsetTypeTag.tpe.typeSymbol} for non-constant values. Use .checkedSubset or .assertSubset functions instead."
        )
    }
    expr
  }



}
