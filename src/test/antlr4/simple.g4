grammar Simple;

@header{
    package com.github.txtrpg.antlr;
}

// grammar tree

move : NORTH
 | EAST
 | SOUTH
 | WEST
 | UP
 | DOWN
 ;


// tokens (in all caps)

// move

NORTH : 'n';
EAST : 'e';
SOUTH: 's';
WEST : 'w';
UP : 'u';
DOWN : 'd';

// ignore white space

WS : [ \t\r\n] -> skip;

