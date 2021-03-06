package com.treeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.visit.Visit;

public class Factor extends TempNode {

	private TokenInfo mTokenStart;
	private SimpleExpn mParseExpression;
	private TokenInfo mTokenEnd;
	private int mCount;
	Map<String, Attrib> mAttributes = new HashMap<String, Attrib>();
	private boolean mError;
	private String mColor;
	private List<Node> mNodes = null;
	private Node mFather = null;
	private Token mLastToken;

	public Factor(TokenInfo pToken, SimpleExpn pParseExpression, TokenInfo pToken2) {
		mTokenStart = pToken;
		mParseExpression = pParseExpression;
		mTokenEnd = pToken2;

	}

	public TokenInfo getTokenStart() {
		return mTokenStart;
	}

	public SimpleExpn getParseExpression() {
		return mParseExpression;
	}

	public TokenInfo getTokenEnd() {
		return mTokenEnd;
	}

	public TokenInfo getTokenValue() {
		if (mTokenStart != null && mTokenStart.getWord() == Token.LP)
			return null;
		else
			return mTokenStart;
	}

	public Node buildAST(Node father) {
		mFather = father;
		return this;
	}

	@Override
	public List<Node> getChildNodes() {

		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			if (mParseExpression != null) {
				nodes.add(new Operator(mTokenStart));
				nodes.add(mParseExpression.buildAST(this));
				nodes.add(new Operator(mTokenEnd));
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
		
		return mFather;
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
