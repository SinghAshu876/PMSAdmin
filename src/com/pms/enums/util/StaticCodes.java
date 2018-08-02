package com.pms.enums.util;

import java.util.LinkedHashMap;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class StaticCodes {

	public static LinkedHashMap<Character, String> codeMap = new LinkedHashMap<Character, String>();
	static {
		codeMap.put('1', "A");
		codeMap.put('2', "B");
		codeMap.put('3', "C");
		codeMap.put('4', "D");
		codeMap.put('5', "E");
		codeMap.put('6', "F");
		codeMap.put('7', "G");
		codeMap.put('8', "H");
		codeMap.put('9', "I");
		codeMap.put('0', "J");
	}

	public static Integer[] yearArray = new Integer[16];
	static {
		yearArray[0] = 2015;
		yearArray[1] = 2016;
		yearArray[2] = 2017;
		yearArray[3] = 2018;
		yearArray[4] = 2019;
		yearArray[5] = 2020;
		yearArray[6] = 2021;
		yearArray[7] = 2022;
		yearArray[8] = 2023;
		yearArray[9] = 2024;
		yearArray[10] = 2025;
		yearArray[11] = 2026;
		yearArray[12] = 2027;
		yearArray[13] = 2028;
		yearArray[14] = 2029;
		yearArray[15] = 2030;

	}

}
