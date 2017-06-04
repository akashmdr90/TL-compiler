package com.tool;

public class Identifier {
	final static char EOFCH = (char) -1;

	public static boolean isWhiteSpace(char ch) {
		boolean ret = false;

		if (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\n' || ch == '\f'
				|| ch == EOFCH) {
			ret = true;
		}

		return ret;
	}

	public static boolean check_identifier_validity(String s) {

		boolean valid = false;
		if (s.length() > 1 && (!s.substring(1, s.length()).contains("_"))
				&& ((Character.isLetter(s.charAt(0)) || s.charAt(0) == '_'))
				&& (s.substring(1, s.length()).matches("[A-Za-z0-9]+"))) {
			valid = true;
		} else if (s.length() == 1
				&& ((Character.isLetter(s.charAt(0)) || s.charAt(0) == '_'))) {
			valid = true;
		}
		return valid;

	}

	public static boolean isStartOfIdentifier(char ch) {
		boolean ret = false;

		if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch == '_')) {
			ret = true;
		}
		return ret;
	}

	public static boolean isPartOfIdentifier(char ch) {
		boolean ret = false;

		if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
				|| Character.isDigit(ch)) {
			ret = true;
		}

		return ret;
	}

	public static boolean isStartOfKeyword(char ch) {
		boolean ret = false;

		if (ch >= 'a' && ch <= 'z') {
			ret = true;
		}

		return ret;
	}

	public static boolean isPartOfKeyword(char ch) {
		boolean ret = false;

		if (isStartOfKeyword(ch)) {
			ret = true;
		}

		return ret;
	}

}
