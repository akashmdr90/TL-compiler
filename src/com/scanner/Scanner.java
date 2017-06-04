package com.scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import com.treeExtension.TokenInfo;
import com.tool.Checker;
import com.tool.Identifier;
import com.tool.Reader;

public class Scanner {

	private char currentChar;
	private int lineNumber;
	private boolean isError;
	private Hashtable<String, Token> keyWordsTable;
	private Reader inputFileReader;
	private String fileName;
	final char EOFCH = (char) -1;
	int flag = 0;
	private List<String> errorList;
	public Scanner(String fileName) throws FileNotFoundException {

		this.fileName = fileName;
		this.inputFileReader = new Reader(fileName);

		keyWordsTable = new Hashtable<String, Token>();
		errorList = new ArrayList<String>();
		keyWordsTable = KeyWords.getKeyWords();

		this.isError = false;
		this.currentChar = ' ';
	}

	public boolean hasError() {
		return isError;
	}

	private void readNextCharacter() {
		currentChar = inputFileReader.getNextChar();
		lineNumber = inputFileReader.getLineNumber();
	}

	@SuppressWarnings("static-access")
	private void updateErrorList(String message, Object... args) {
		String strmsg = "[SCANNER ERROR]: " + message; 
		String msg = "";

		msg = msg.format(strmsg, args);

		errorList.add(msg);
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public TokenInfo getNextToken() {

		if (flag == 0)
			readNextCharacter();
		StringBuffer buffer;
		while (Identifier.isWhiteSpace(currentChar)) {
			if (currentChar == EOFCH)
				break;
			readNextCharacter();
		}

		switch (currentChar) {

		case '+':
			flag = 0;
			return new TokenInfo(Token.ADDITIVE, "+", lineNumber);

		case '-':
			flag = 0;
			return new TokenInfo(Token.ADDITIVE, "-", lineNumber);

		case '*':
			flag = 0;
			return new TokenInfo(Token.MULTIPLICATIVE, "*", lineNumber);
		case '(':
			flag = 0;
			return new TokenInfo(Token.LP, lineNumber);

		case ')':
			flag = 0;
			return new TokenInfo(Token.RP, lineNumber);

		case '=':
			flag = 0;
			return new TokenInfo(Token.COMPARE, "=", lineNumber);
		case ';':
			flag = 0;
			return new TokenInfo(Token.SC, lineNumber);
		case ':':
			flag = 0;
			readNextCharacter();

			while (Identifier.isWhiteSpace(currentChar)) {
				if (currentChar == EOFCH)
					break;
				readNextCharacter();
			}

			if (currentChar == '=') {
				return new TokenInfo(Token.ASGN, lineNumber);
			} else {
				isError = true;
				updateErrorList("[%s line:%d] '%s' does not does not support after \":\" character",fileName, lineNumber, "" + currentChar);

				return getNextToken();
			}

		case '>':
			flag = 0;
			readNextCharacter();

			while (Identifier.isWhiteSpace(currentChar)) {
				if (currentChar == EOFCH)
					break;
				readNextCharacter();
			}

			if (currentChar != '=') {
				flag = 1;
				return new TokenInfo(Token.COMPARE, ">", lineNumber);

			} else if (currentChar == '=') {
				return new TokenInfo(Token.COMPARE, ">=", lineNumber);
			}
		case '<':
			flag = 0;
			readNextCharacter();

			while (Identifier.isWhiteSpace(currentChar)) {
				if (currentChar == EOFCH)
					break;
				readNextCharacter();
			}

			if (currentChar != '=') {
				flag = 1;
				return new TokenInfo(Token.COMPARE, "<", lineNumber);

			} else if (currentChar == '=') {
				return new TokenInfo(Token.COMPARE, "<=", lineNumber);
			}

		case '!':
			flag = 0;
			readNextCharacter();

			while (Identifier.isWhiteSpace(currentChar)) {
				if (currentChar == EOFCH)
					break;
				readNextCharacter();
			}

			if (currentChar == '=') {
				return new TokenInfo(Token.COMPARE, "!=", lineNumber);
			} else {
				isError = true;
				updateErrorList("[%s line:%d] %s does not support after ! Character",fileName, lineNumber, "" + currentChar);

				return getNextToken();
			}
		case '%':
			flag = 0;
			readNextCharacter();
			while (currentChar != '\n') {
				readNextCharacter();
			}
			return getNextToken();

		case EOFCH:
			return new TokenInfo(Token.EOF, lineNumber);

		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			flag = 1;
			buffer = new StringBuffer();
			boolean beginzerochek = false;
			boolean leadingzeroerror = false;

			if (currentChar == '0')
				beginzerochek = true;

			while (Character.isDigit(currentChar)) {
				buffer.append(currentChar);
				readNextCharacter();

				if (beginzerochek == true && Character.isDigit(currentChar)) {
					leadingzeroerror = true;
				}
			}
			if (leadingzeroerror == true) {
				isError = true;
				updateErrorList(
						"[%s line:%d] Does not support leading zero for int",fileName, lineNumber);

				return getNextToken();
			} else {
				if (Checker.isValidIntRange(buffer)) {
					return new TokenInfo(Token.num, buffer.toString(),
							lineNumber);
				} else {
					isError = true;
					updateErrorList("[%s line:%d] Interger value out of range: %s",fileName, lineNumber, buffer.toString());

					return getNextToken();
				}
			}

		default:

			flag = 1;
			buffer = new StringBuffer();

			if (Identifier.isStartOfIdentifier(currentChar)
					|| Identifier.isStartOfKeyword(currentChar)) {
				do {
					buffer.append(currentChar);
					readNextCharacter();

				} while (Identifier.isPartOfIdentifier(currentChar)
						|| Identifier.isPartOfKeyword(currentChar));

				String identifier = buffer.toString();
				if (identifier.equalsIgnoreCase("true")
						|| identifier.equalsIgnoreCase("false")) {
					return new TokenInfo(Token.boollit, identifier, lineNumber);
				} else if (identifier.equalsIgnoreCase("div")
						|| identifier.equalsIgnoreCase("mod")) {
					return new TokenInfo(Token.MULTIPLICATIVE, identifier,
							lineNumber);
				} else if (keyWordsTable.containsKey(identifier)) {
					return new TokenInfo(keyWordsTable.get(identifier),
							lineNumber);
				} else if (Identifier
						.check_identifier_validity(identifier)) {
					return new TokenInfo(Token.ident, identifier, lineNumber);
				} else {
					isError = true;
					updateErrorList("[%s line:%d] Invalid keyword %s",fileName, lineNumber, identifier);

					return getNextToken();
				}

			} else {
				isError = true;
				updateErrorList("[%s line:%d] Unidentified input token: '%c'", fileName, lineNumber,currentChar);
				readNextCharacter();

				return getNextToken();
			}

		}
	}
}
