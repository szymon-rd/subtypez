package subtype.core

import subtype.core.Macros.SubsetCondition

import scala.reflect.ClassTag

object RuntimeConversions {
  def unsafeConvert[T: ClassTag, R](t: T)(implicit condition: SubsetCondition[T, R]): R = {
    if(condition.condition(t)) {
      t.asInstanceOf[R]
    } else {
      val tName = implicitly[ClassTag[T]].runtimeClass.getSimpleName
      throw new IllegalArgumentException(s"Cannot convert ${t} to $tName")
    }
  }
}
