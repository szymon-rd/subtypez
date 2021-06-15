# Subtypez
A small macro library for defining custom 'subtypes' for Scala literals. It definitely wasn't tested enough to be introduced in a production environment. Example of usage:

In core module:
```scala
  type UpperCaseString <: String
  implicit val UpperCaseStringDef = Definition[String, UpperCaseString](_.forall(_.isUpper))

  type FirstHuman <: String
  implicit val FirstHumanDef = OneOf[String, FirstHuman]("adam", "Eve")
```

In dependent module:
```scala
  val s: UpperCaseString = "DSa" // compile error
  val s2: UpperCaseString = "DSA" // everything is ok
  val h: FirstHuman = "adam" // it's ok
  val h2: FirstHuman = "stefan" // compile error
```

Other example:
```scala
  val email: Email = "test@email.com"
  val note_email: Email = "not_emailg" // does not compile
```
