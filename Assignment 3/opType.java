// opType - just a pre-1.5 pattern for doing enumerations
//
// Kurt Schmidt
// 3/07
//
// EDITOR: tabstop=2, cols=80
//
// javac 1.6.0, on Linux version 2.6.18.6 (gcc version 3.4.6 (Gentoo
//		 3.4.6-r1, ssp-3.4.5-1.0, pie-8.7.9))
//

import java.io.*;

public final class opType {

	public static opType ADD = new opType( "Add" );
	public static opType SUB = new opType( "Sub" );
	public static opType MULT = new opType( "Mult" );
	public static opType DIV = new opType( "Div" );
	public static opType MOD = new opType( "Mod" );
	public static opType LPAR = new opType( "LParen" );
	public static opType RPAR = new opType( "RParen" );

	protected String name;

	private opType( String n )
	{
		name = n;
	}

	public String getName()	// for debugging, maybe
	{
		switch(name){
			case "Add":
				return "+";
			case "Sub":
				return "-";
			case "Mult":
				return "*";
			case "Div":
				return "/";
			case "Mod":
				return "%";
			case"LParen":
				return "(";
			case "RParen":
				return ")";
			default:
				return " ";
		}
	}

	public static void main( String argv[] )	// testing
	{
		opType a = opType.ADD;
		opType b = opType.ADD;
		opType c = opType.MULT;

		if( a != b )
			System.out.println( "a and b should be the same (ADD)" );

		if( a == c )
			System.out.println( "a and c should not be the same (ADD vs. MULT)" );

		if( b == c )
			System.out.println( "b and c should not be the same (ADD vs. MULT)" );

		System.out.println( "Done!" );
	}
}

