package subtype.core

import subtype.core.Macros.SubsetConverterWithCondition

object Definitions {
  object Definition {
    def apply[T, R](condition: T => Boolean) = new SubsetConverterWithCondition[T, R](condition)
  }

  object OneOf {
    def apply[T, R](elems: String*) = Definition[T, R]((t: T) => elems.contains(t))
  }


  type UpperCaseString <: String
  implicit val UpperCaseStringDef = Definition[String, UpperCaseString](_.forall(_.isUpper))

  type FirstHuman <: String
  implicit val FirstHumanDef = OneOf[String, FirstHuman]("adam", "Eve")

  type EvenInt <: Int
  implicit val EvenIntCondition = Definition[Int, EvenInt](_ % 2 == 0)

  type LowerCaseString <: String
  implicit val LowerCaseStringDefinition = Definition[String, UpperCaseString](_.forall(_.isUpper))

  type Email <: String
  implicit val EmailDefinition = Definition[String, Email](EmailChecker.check)

  object EmailChecker {
    private val emailRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r
    def check(e: String): Boolean = e match{
      case null                                           => false
      case e if e.trim.isEmpty                            => false
      case e if emailRegex.findFirstMatchIn(e).isDefined  => true
      case _                                              => false
    }
  }
}
