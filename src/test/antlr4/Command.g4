grammar Command;


@header{
    package com.github.txtrpg.antlr4;
}

// grammar tree

@parser::members {

 private int variant = -1;
 private String param1;
 private String param2;
 private String param3;
 private String param4;

 public int getVariant(){
    return variant;
 }

 public String getParam1() {
    return param1;
 }

 public String getParam2() {
    return param2;
 }

 public String getParam3() {
    return param3;
 }

 public String getParam4() {
    return param4;
 }

 public void resetParams() {
    variant = -1;
    param1 = null;
    param2 = null;
    param3 = null;
    param4 = null;
 }

}

command : move EOF
    | look EOF
    | quit EOF
    ;

move : 'n'
    | 'e'
    | 's'
    | 'w'
    | 'u'
    | 'd'
 ;

look : 'look' { variant = 1; }
    | 'look' WORD { param1 = $WORD.text; variant = 2; }
    | 'look' WORD NUMBER { param1 = $WORD.text; param2 = $NUMBER.text; variant = 3; }
    ;

quit : 'quit' | 'exit';

// tokens
WORD : ('a'..'z')+;
NUMBER : ('0'..'9')+;

// ignore white space
WS : [ \t\r\n] -> skip;

