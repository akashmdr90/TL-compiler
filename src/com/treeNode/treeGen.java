package com.treeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.visit.Visit;

public class treeGen extends TempNode {
	Declaration declarations = null;
	StmtSeq statementSequence = null;
	TokenInfo program;
	TokenInfo begin;
	TokenInfo end;
	private int mCount;
	Map<String, Attrib> mAttributes = new HashMap<String, Attrib>();
	private boolean mError;
	private String mColor;
	private List<Node> mNodes = null;
	private Token mLastToken;

	public treeGen(TokenInfo pTokenP, Declaration pDeclaration, TokenInfo pTokenB,
			StmtSeq pParseStatementSequences, TokenInfo pTokenE) {
		this.program = pTokenP;
		this.declarations = pDeclaration;
		this.begin = pTokenB;
		this.statementSequence = pParseStatementSequences;
		this.end = pTokenE;
	}

	public TokenInfo getBegin() {
		return begin;
	}

	public TokenInfo getProgram() {
		return program;
	}

	public Declaration getDeclarations() {
		return declarations;
	}

	public StmtSeq getStatementSequence() {
		return statementSequence;
	}

	public TokenInfo getEnd() {
		return end;
	}

	@Override
	public TokenInfo getTokenValue() {
		
		return null;
	}

	@Override
	public Node buildAST(Node father) {

		return this;
	}

	@Override
	public List<Node> getChildNodes() {

		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			if (declarations != null) {
				nodes.add(declarations.buildAST(this));
			}
			if (statementSequence != null) {
				nodes.add(statementSequence.buildAST(this));
			}
			mNodes = nodes;
		}
		return mNodes;
	}

	@Override
	public int getCount() {

		return mCount;
	}

	@Override
	public void setCount(int pCount) {
		mCount = pCount;

	}

	@Override
	public Map<String, Attrib> getAttributes() {
		
		return mAttributes;
	}

	@Override
	public void setAttribute(String key, Attrib pAttribute) {
		mAttributes.put(key, pAttribute);

	}

	@Override
	public String getColor() {
		return mColor;
	}

	@Override
	public void setColor(String pColor) {
		mColor = pColor;

	}

	@Override
	public void setError(boolean pError) {
		mError = pError;

	}

	@Override
	public boolean hasError() {
		return mError;
	}

	@Override
	public boolean isDeclaration() {
		
		return false;
	}

	@Override
	public Node getFather() {
		
		return null;
	}

	@Override
	public Token getLastChildType() {
		
		return mLastToken;
	}

	@Override
	public void setLastChildType(Token token) {
		mLastToken = token;

	}

	@Override
	public void accept(Visit visitor) {
		visitor.visit(this);

	}
}
