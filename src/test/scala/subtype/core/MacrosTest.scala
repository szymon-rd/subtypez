package subtype.core
import org.scalatest._
import org.scalatest.matchers.must.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import subtype.core.Macros._
import subtype.core.Definitions._


class MacrosTest extends AnyFlatSpec with Matchers {

  "Subtype" should "work with correct email for Email type" in {
    assertCompiles("""val x: Email = "email@test.com"""")
  }

  it should "thrown compile error with incorrect email for Email type" in {
    assertDoesNotCompile("""val x: Email = "not_email"""")
  }

  it should "convert types in runtime" in {
    val x: String = "email@test.com"
    val y: Email = RuntimeConversions.unsafeConvert[String, Email](x)
    assert(x == y)
  }

  it should "fail on converting types not matching condition in runtime" in {
    val x: String = "not_email"
    assertThrows[IllegalArgumentException] {
      val y: Email = RuntimeConversions.unsafeConvert[String, Email](x)
    }
  }
}

