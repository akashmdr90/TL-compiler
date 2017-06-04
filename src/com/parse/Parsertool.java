package com.parse;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.treeNode.*;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.scanner.TokenSearch;
import com.tool.Logger;

public class Parsertool {

	TokenSearch mTokenManager;

	private boolean isError = false;

	private boolean isRecovered = true;

	public Parsertool(String pFileName) throws FileNotFoundException {
		mTokenManager = new TokenSearch(pFileName);
		mTokenManager.next();

	}

	public treeGen startParse() {
		TokenInfo tokenprogram = mTokenManager.token();
		tokenShoulBeAs(Token.PROGRAM);

		Declaration declaration = null;
		StmtSeq parseStatementSequences = null;

		if (isCurrentTokenAs(Token.VAR)) {

			declaration = parseDeclaration();

		}

		TokenInfo tokenbegin = mTokenManager.token();

		tokenShoulBeAs(Token.BEGIN);

		if (Token.getStatement().contains(mTokenManager.token().getWord())) {
			parseStatementSequences = parseStatementSequences();

		}

		TokenInfo tokenend = mTokenManager.token();
		tokenShoulBeAs(Token.END);

		return new treeGen(tokenprogram, declaration, tokenbegin, parseStatementSequences, tokenend);
	}
	private StmtSeq parseStatementSequences() {
		Stmt statement = parseStatement();
		TokenInfo token = mTokenManager.token();
		StmtSeq parseStatementSequences = new StmtSeq(null, null, null);
		tokenShoulBeAs(Token.SC);
		if (Token.getStatement().contains(mTokenManager.token().getWord())) {
			parseStatementSequences = parseStatementSequences();

		}
		return new StmtSeq(statement, token, parseStatementSequences);

	}

	
	private Stmt parseStatement() {
		Assign parseAssignment = null;
		IfStmt parseIfStatement = null;
		WhileStmt parseWhileStatement = null;
		WriteInt parseWriteExpression = null;
		if (isCurrentTokenAs(Token.ident)) {
			parseAssignment = parseAssignment();
		} else if (isCurrentTokenAs(Token.IF)) {
			parseIfStatement = parseIfStatement();
		} else if (isCurrentTokenAs(Token.WHILE)) {
			parseWhileStatement = parseWhileStatement();
		} else if (isCurrentTokenAs(Token.WRITEINT)) {
			parseWriteExpression = parseWriteExpression();
		}

		return new Stmt(parseAssignment, parseIfStatement, parseWhileStatement, parseWriteExpression);

	}
	

	private Assign parseAssignment() {
		TokenInfo ident = mTokenManager.token();
		tokenShoulBeAs(Token.ident);
		TokenInfo asgn = mTokenManager.token();
		tokenShoulBeAs(Token.ASGN);
		AssignExtn parseAssignment1 = parseAssignment1();
		return new Assign(ident, asgn, parseAssignment1);
	}

	private AssignExtn parseAssignment1() {
		TokenInfo word = mTokenManager.token();
		Expr parseExpression = null;
		if (haveExpectedToekn(Token.READINT)) {

		} else {
			parseExpression = parseExpression();
		}

		return new AssignExtn(word, parseExpression);
	}

	private WriteInt parseWriteExpression() {
		TokenInfo token = mTokenManager.token();
		tokenShoulBeAs(Token.WRITEINT);
		Expr parseExpression = parseExpression();

		return new WriteInt(token, parseExpression);

	}

	private ClauseElse parseElseExpression() {
		TokenInfo token = mTokenManager.token();
		if (haveExpectedToekn(Token.ELSE)) {
			StmtSeq parseStatementSequences = parseStatementSequences();

			return new ClauseElse(token, parseStatementSequences);
		} else
			return new ClauseElse(null, null);

	}

	private IfStmt parseIfStatement() {
		TokenInfo ifT = mTokenManager.token();
		tokenShoulBeAs(Token.IF);
		Expr parseExpression = parseExpression();
		TokenInfo thenT = mTokenManager.token();
		tokenShoulBeAs(Token.THEN);
		StmtSeq parseStatementSequences = parseStatementSequences();
		ClauseElse parseElseExpression = parseElseExpression();
		TokenInfo endT = mTokenManager.token();
		tokenShoulBeAs(Token.END);
		return new IfStmt(ifT, parseExpression, thenT, parseStatementSequences, parseElseExpression, endT);
	}

	private WhileStmt parseWhileStatement() {
		TokenInfo whileT = mTokenManager.token();
		tokenShoulBeAs(Token.WHILE);
		Expr parseExpression = parseExpression();
		TokenInfo dOT = mTokenManager.token();
		tokenShoulBeAs(Token.DO);
		StmtSeq parseStatementSequences = parseStatementSequences();
		TokenInfo endT = mTokenManager.token();
		tokenShoulBeAs(Token.END);
		return new WhileStmt(whileT, parseExpression, dOT, parseStatementSequences, endT);
	}

	private Expr parseExpression() {
		SimpleExpn parseSimpleExpression = parseSimpleExpression();
		ExprExtn expression1 = parseExpression1();
			return new Expr(parseSimpleExpression, expression1);

	}

	private ExprExtn parseExpression1() {
		TokenInfo word = mTokenManager.token();

		if (haveExpectedToekn(Token.COMPARE)) {
			SimpleExpn parseSimpleExpression = parseSimpleExpression();
			return new ExprExtn(word, parseSimpleExpression);
		}

		else
			return new ExprExtn(null, null);
	}

	private SimpleExpn parseSimpleExpression() {
		Term parseTerm = parseTerm();
		SimpleExpnExtn parseSimpleExpression1 = parseSimpleExpression1();
		return new SimpleExpn(parseTerm, parseSimpleExpression1);

	}

	private SimpleExpnExtn parseSimpleExpression1() {
		TokenInfo word = mTokenManager.token();
		if (haveExpectedToekn(Token.ADDITIVE)) {
			SimpleExpn parseTerm = parseSimpleExpression();
			return new SimpleExpnExtn(word, parseTerm);
		}
		else if (haveExpectedToekn(Token.MULTIPLICATIVE)) {
			SimpleExpn parseTerm = parseSimpleExpression();
			return new SimpleExpnExtn(word, parseTerm);
		}
		else
			return new SimpleExpnExtn(null, null);
	}

	private Term parseTerm() {
		Factor parseFactor = parseFactor();
		TermExtn parseTerm1 = parseTerm1();

		return new Term(parseFactor, parseTerm1);
	}
	private TermExtn parseTerm1() {
		TokenInfo token = mTokenManager.token();
		if (haveExpectedToekn(Token.MULTIPLICATIVE)) {
		   Factor parseFactor = parseFactor();

			return new TermExtn(token, parseFactor);
		}

		else
			return new TermExtn(null, null);

	}
	private Factor parseFactor() {
		TokenInfo token = mTokenManager.token();
		if (haveExpectedToekn(Token.ident) || haveExpectedToekn(Token.num) || haveExpectedToekn(Token.boollit)) {
			return new Factor(token, null, null);
		} else {
			tokenShoulBeAs(Token.LP);

			SimpleExpn parseExpression = parseSimpleExpression();
			TokenInfo token2 = mTokenManager.token();
			tokenShoulBeAs(Token.RP);

			return new Factor(token, parseExpression, token2);

		}

	}

	public Declaration parseDeclaration() {
		TokenInfo tokenVar = mTokenManager.token();
		tokenShoulBeAs(Token.VAR);
		TokenInfo tokenID = mTokenManager.token();
		tokenShoulBeAs(Token.ident);
		TokenInfo tokenAs = mTokenManager.token();
		tokenShoulBeAs(Token.AS);
		Type parseType = parseType();
		TokenInfo tokenSc = mTokenManager.token();
		tokenShoulBeAs(Token.SC);

		Declaration declaration = null;
		if (isCurrentTokenAs(Token.VAR)) {
			declaration = parseDeclaration();
		}

		return new Declaration(tokenVar, tokenID, tokenAs, parseType, tokenSc, declaration);
	}

	public Type parseType() {
		TokenInfo token = mTokenManager.token();
		if (haveExpectedToekn(Token.BOOL) || haveExpectedToekn(Token.INT)) {
			return new Type(token);
		} else {
			isError = true;
			return new Type(null);
		}
		
	}

	private void tokenShoulBeAs(Token pToken) {
		if (mTokenManager.token().getWord() == pToken) {
			isRecovered = true;
			mTokenManager.next();
		} else if (isRecovered) {
			isRecovered = false;

			Logger.reportParserError("PARSER ERROR %s found where %s sought in line no %d ",
					mTokenManager.token().getWord(), pToken, mTokenManager.token().getLineNumber());
		} else {
			while (!isCurrentTokenAs(pToken) && !isCurrentTokenAs(Token.EOF)) {
				mTokenManager.next();
			}
			if (isCurrentTokenAs(pToken)) {
				isRecovered = true;
				mTokenManager.next();
			}
		}
	}

	private boolean haveExpectedToekn(Token pToken) {
		if (isCurrentTokenAs(pToken)) {
			mTokenManager.next();
			return true;

		} else {
			return false;
		}
	}

	private boolean isCurrentTokenAs(Token pToken) {
		return pToken.getValue().equalsIgnoreCase(mTokenManager.token().getWord().getValue());
	}

}
