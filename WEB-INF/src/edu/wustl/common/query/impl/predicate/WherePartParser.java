/* Generated By:JavaCC: Do not edit this line. WherePartParser.java */
package edu.wustl.common.query.impl.predicate;

import java.io.*;


public class WherePartParser implements WherePartParserConstants {

        private PredicateGenerator predicateGenerator;

        public WherePartParser(String wherePart, PredicateGenerator predicateGenerator)
        {
                this(new StringReader(wherePart));
                this.predicateGenerator = predicateGenerator;
        }


        public static void main(String[] args) throws ParseException, FileNotFoundException
        {
                InputStream in = new FileInputStream("WherePartChitra.txt");

                WherePartParser parser = new WherePartParser(in);
                parser.parse();

        }

  final public void parse() throws ParseException, ParseException {
    trace_call("parse");
    try {
        Token t = null;
      ConditionTree();
      jj_consume_token(0);
                System.out.println("Condition Tree Parsed Successfully!");
    } finally {
      trace_return("parse");
    }
  }

  final private void ConditionTree() throws ParseException {
    trace_call("ConditionTree");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PREFIX_UNARY_OPERATOR:
      case PREFIX_BINARY_OPERATOR:
      case CONDITION_ATTRIBUTE:
        ConditionsOnOneEntity();
        break;
      case OPENING_PARENTHESIS:
        ParenthesizedConditionsOnOneEntity();
        label_1:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case LOGICAL_OPERATOR:
            ;
            break;
          default:
            jj_la1[0] = jj_gen;
            break label_1;
          }
          jj_consume_token(LOGICAL_OPERATOR);
          jj_consume_token(OPENING_PARENTHESIS);
          ConditionsOnChild();
          jj_consume_token(CLOSING_PARENTHESIS);
        }
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("ConditionTree");
    }
  }

  final private void ParenthesizedConditionsOnOneEntity() throws ParseException {
    trace_call("ParenthesizedConditionsOnOneEntity");
    try {
      jj_consume_token(OPENING_PARENTHESIS);
      ConditionsOnOneEntity();
      jj_consume_token(CLOSING_PARENTHESIS);
    } finally {
      trace_return("ParenthesizedConditionsOnOneEntity");
    }
  }

  final private void ConditionsOnOneEntity() throws ParseException {
    trace_call("ConditionsOnOneEntity");
    try {
      AtomicCondition();
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LOGICAL_OPERATOR:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_2;
        }
        jj_consume_token(LOGICAL_OPERATOR);
        AtomicCondition();
      }
    } finally {
      trace_return("ConditionsOnOneEntity");
    }
  }

  final private void AtomicCondition() throws ParseException {
    trace_call("AtomicCondition");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PREFIX_UNARY_OPERATOR:
        PrefixUnaryCondition();
        break;
      case PREFIX_BINARY_OPERATOR:
        PrefixBinaryCondition();
        break;
      case CONDITION_ATTRIBUTE:
        InfixCondition();
        break;
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("AtomicCondition");
    }
  }

  final private void PrefixUnaryCondition() throws ParseException {
    trace_call("PrefixUnaryCondition");
    try {
        Token conditionAttribute = null;
        Token operator = null;
      operator = jj_consume_token(PREFIX_UNARY_OPERATOR);
      conditionAttribute = jj_consume_token(CONDITION_ATTRIBUTE);
      jj_consume_token(CLOSING_PARENTHESIS);
                        int separator = conditionAttribute.image.indexOf("/");
                        String forVariable = conditionAttribute.image.substring(0, separator);
                        String attribute = conditionAttribute.image.substring(separator+1);
                        AbstractPredicate predicate = new PrefixUnaryPredicate(attribute, operator.image);
                        predicateGenerator.addPredicate(forVariable, predicate);
    } finally {
      trace_return("PrefixUnaryCondition");
    }
  }

  final private void PrefixBinaryCondition() throws ParseException {
    trace_call("PrefixBinaryCondition");
    try {
        Token conditionAttribute = null;
        Token operator = null;
        String rhs = null;
      operator = jj_consume_token(PREFIX_BINARY_OPERATOR);
      conditionAttribute = jj_consume_token(CONDITION_ATTRIBUTE);
      jj_consume_token(CLOSING_PARENTHESIS);
      jj_consume_token(COMMA);
      rhs = RHS();
      jj_consume_token(CLOSING_PARENTHESIS);
                int separator = conditionAttribute.image.indexOf("/");
                String forVariable = conditionAttribute.image.substring(0, separator);
                String attribute = conditionAttribute.image.substring(separator+1);
                AbstractPredicate predicate = new PrefixBinaryPredicate(attribute, operator.image, rhs);
                predicateGenerator.addPredicate(forVariable, predicate);
    } finally {
      trace_return("PrefixBinaryCondition");
    }
  }

  final private void InfixCondition() throws ParseException {
    trace_call("InfixCondition");
    try {
        Token conditionAttribute = null;
        Token operator = null;
        String rhs = null;
      conditionAttribute = jj_consume_token(CONDITION_ATTRIBUTE);
      operator = jj_consume_token(INFIX_OPERATOR);
      rhs = RHS();
                int separator = conditionAttribute.image.indexOf("/");
                String forVariable = conditionAttribute.image.substring(0, separator);
                String attribute = conditionAttribute.image.substring(separator+1);
                AbstractPredicate predicate = new InfixPredicate(attribute, operator.image, rhs);
                predicateGenerator.addPredicate(forVariable, predicate);
    } finally {
      trace_return("InfixCondition");
    }
  }

  final private void ConditionsOnChild() throws ParseException {
    trace_call("ConditionsOnChild");
    try {
      ConditionTree();
    } finally {
      trace_return("ConditionsOnChild");
    }
  }

  final private String RHS() throws ParseException {
    trace_call("RHS");
    try {
        Token rhsToken = null;
        String rhs = null;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case FUNCTION_CALL:
        rhsToken = jj_consume_token(FUNCTION_CALL);
                {if (true) return rhsToken.image;}
        break;
      case CONSTANT:
        rhsToken = jj_consume_token(CONSTANT);
                {if (true) return rhsToken.image;}
        break;
      case CONDITION_ATTRIBUTE:
        rhsToken = jj_consume_token(CONDITION_ATTRIBUTE);
                {if (true) return rhsToken.image;}
        break;
      case OPENING_PARENTHESIS:
        rhs = CSV();
                {if (true) return rhs;}
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("RHS");
    }
  }

  final private String CSV() throws ParseException {
    trace_call("CSV");
    try {
        StringBuilder rhs = new StringBuilder();
        Token constant1 = null;
        Token constant2 = null;
      jj_consume_token(OPENING_PARENTHESIS);
                rhs.append('(');
      constant1 = jj_consume_token(CONSTANT);
                rhs.append(constant1.image);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_3;
        }
        jj_consume_token(COMMA);
        constant2 = jj_consume_token(CONSTANT);
      }
                if(constant2 != null)
                {
                        rhs.append(',').append(constant2.image);
                }
      jj_consume_token(CLOSING_PARENTHESIS);
                rhs.append(')');
                {if (true) return rhs.toString();}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("CSV");
    }
  }

  /** Generated Token Manager. */
  public WherePartParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[6];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1000,0x2680,0x1000,0x2600,0x62080,0x40,};
   }

  /** Constructor with InputStream. */
  public WherePartParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public WherePartParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new WherePartParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public WherePartParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new WherePartParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public WherePartParser(WherePartParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(WherePartParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      trace_token(token, "");
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
      trace_token(token, " (in getNextToken)");
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List jj_expentries = new java.util.ArrayList();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[19];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 6; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 19; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  private int trace_indent = 0;
  private boolean trace_enabled = true;

/** Enable tracing. */
  final public void enable_tracing() {
    trace_enabled = true;
  }

/** Disable tracing. */
  final public void disable_tracing() {
    trace_enabled = false;
  }

  private void trace_call(String s) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Call:   " + s);
    }
    trace_indent = trace_indent + 2;
  }

  private void trace_return(String s) {
    trace_indent = trace_indent - 2;
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Return: " + s);
    }
  }

  private void trace_token(Token t, String where) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Consumed token: <" + tokenImage[t.kind]);
      if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
        System.out.print(": \"" + t.image + "\"");
      }
      System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
    }
  }

  private void trace_scan(Token t1, int t2) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Visited token: <" + tokenImage[t1.kind]);
      if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
        System.out.print(": \"" + t1.image + "\"");
      }
      System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
    }
  }

}
