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
}
git remote add origin git@github.com:szymon-rd/subtypez.git
git branch -M master
git push -u origin master
