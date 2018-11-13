package compiler.mbl

import fastparse.all._

object MblParser {

  val whitespaceChars = " \n\t\r".toSet
  val languageChars = "()'".toSet
  val reservedChars = whitespaceChars ++ languageChars

  val whitepaceParser = P(CharPred(whitespaceChars.contains(_)).rep)
  val symbolParser : Parser[MblSymbol] = P(CharPred(!reservedChars.contains(_)).rep(1).!).map(MblSymbol)
  val literalParser : Parser[MblQuoted] = P("'" ~/ CharPred(!reservedChars.contains(_)).rep(1).!).map(MblQuoted)
  val listParser : Parser[MblList] = P("(" ~/ (whitepaceParser ~ expressionParser).rep(0) ~ whitepaceParser.? ~ ")").map(v => MblList(v.toList))
  val expressionParser : Parser[MblElement] = P(literalParser | symbolParser | listParser)

  def parse(program : String) /*: Either[Object, String]*/ =
    expressionParser.parse(program) match {
      case pf : Parsed.Failure =>
        Right(pf.msg)
      case ps : Parsed.Success[MblElement] =>
        Left(ps.value)
    }

}
