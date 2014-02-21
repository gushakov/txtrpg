grammar Cmds;

tokens {
	LOOK = 'look';
	DIR = 'dir';
	NORTH_MOVE = 'n';
	SOUTH_MOVE = 's';
	EAST_MOVE = 'e';
	WEST_MOVE = 'w';
	UP_MOVE = 'u';
	DOWN_MOVE = 'd';
}

@header {
	package com.github.txtrpg.antlr;
	import java.util.Map;
	import java.util.HashMap;
}

@lexer::header {
	package com.github.txtrpg.antlr;
}

@lexer::members {
	List<RecognitionException> exceptions = new ArrayList<RecognitionException>();

	public List<RecognitionException> getExceptions(){
		return exceptions;
	}

	public void reportError(RecognitionException e) {
		super.reportError(e);
		exceptions.add(e);
	}
}

@parser::members {
	String type = null;
	Map<String, Object> args = null;

	public String getType(){
		return type;
	}

	public Map<String, Object> getArgs(){
		return args;
	}

	List<RecognitionException> exceptions = new ArrayList<RecognitionException>();

	public List<RecognitionException> getExceptions(){
		return exceptions;
	}

	public void reportError(RecognitionException e) {
		super.reportError(e);
		exceptions.add(e);
	}

}

//rules

parse returns [String t]
@init {
	args = new HashMap<String, Object>();
}
@after {
	t = type;
}
	: end
	| SPACE? (look
	          | dir
	          | move) end
	;

look : LOOK {type = $LOOK.text;}
	    (SPACE WORD {args.put("target", $WORD.text);}
		(SPACE INDEX {args.put("index", $INDEX.text);})?)?;

dir : DIR {type = $DIR.text;};

move : (d=NORTH
     | d=SOUTH
     | d=EAST
     | d=WEST
     | d=UP_MOVE
     | d=DOWN_MOVE
     | d=LEFT
     | d=RIGHT) {type = "move"; args.put("dir", $d.text);}
     ;

//common

end :  SPACE? NEWLINE? EOF;				//end of line
WORD : ('a'..'z')+;						//single word
INDEX : '1'..'9';						//digit
SPACE : (' ' | '\t')+;					//space
NEWLINE : '\r'? '\n';					//end of line