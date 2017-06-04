package com.tool;

public class Checker {
	public static boolean isValidIntRange(StringBuffer strbuff) {
		boolean ret = false;

		long val;

		val = Long.parseLong(strbuff.toString());

		if (val >= 0 && val <= 2147483647)
			ret = true;

		return ret;
	}

}
