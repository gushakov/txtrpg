grammar Command;

@header{
    package com.github.txtrpg.antlr4;
}

@parser::header {
    import com.github.txtrpg.core.Action;
}

// grammar tree

@parser::members {

 private String param1;
 private String param2;
 private String param3;
 private String param4;

private Action action;

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
    param1 = null;
    param2 = null;
    param3 = null;
    param4 = null;
 }

}

command : move
    | look
    ;

move : NORTH
 | EAST
 | SOUTH
 | WEST
 | UP
 | DOWN
 ;

look : 'look' WORD? { param1 = $WORD.text; };

// tokens

NORTH : 'n';
EAST : 'e';
SOUTH: 's';
WEST : 'w';
UP : 'u';
DOWN : 'd';

WORD : ('a'..'z')+;

// ignore white space

WS : [ \t\r\n] -> skip;

