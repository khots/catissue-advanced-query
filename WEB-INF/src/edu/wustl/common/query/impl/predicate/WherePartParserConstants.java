/* Generated By:JavaCC: Do not edit this line. WherePartParserConstants.java */
package edu.wustl.common.query.impl.predicate;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface WherePartParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int DOLLAR = 5;
  /** RegularExpression Id. */
  int COMMA = 6;
  /** RegularExpression Id. */
  int OPENING_PARENTHESIS = 7;
  /** RegularExpression Id. */
  int CLOSING_PARENTHESIS = 8;
  /** RegularExpression Id. */
  int PREFIX_UNARY_OPERATOR = 9;
  /** RegularExpression Id. */
  int PREFIX_BINARY_OPERATOR = 10;
  /** RegularExpression Id. */
  int INFIX_OPERATOR = 11;
  /** RegularExpression Id. */
  int LOGICAL_OPERATOR = 12;
  /** RegularExpression Id. */
  int CONDITION_ATTRIBUTE = 13;
  /** RegularExpression Id. */
  int DIGITS = 14;
  /** RegularExpression Id. */
  int NUMBER = 15;
  /** RegularExpression Id. */
  int STRING = 16;
  /** RegularExpression Id. */
  int CONSTANT = 17;
  /** RegularExpression Id. */
  int RHS = 18;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"$\"",
    "\",\"",
    "\"(\"",
    "\")\"",
    "<PREFIX_UNARY_OPERATOR>",
    "\"contains(string(\"",
    "<INFIX_OPERATOR>",
    "<LOGICAL_OPERATOR>",
    "<CONDITION_ATTRIBUTE>",
    "<DIGITS>",
    "<NUMBER>",
    "<STRING>",
    "<CONSTANT>",
    "<RHS>",
  };

}