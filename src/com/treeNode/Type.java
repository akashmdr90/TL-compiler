package com.treeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.visit.Visit;

public class Type extends TempNode {
	private TokenInfo type;
	private int mCount;
	Map<String, Attrib> mAttributes = new HashMap<String, Attrib>();
	private boolean mError;
	private String mColor;
	private Node mFather;
	private Token mLastToken;

	public Type(TokenInfo pType) {
		this.type = pType;
	}

	public TokenInfo getType() {
		return type;
	}

	@Override
	public TokenInfo getTokenValue() {
		
		return type;
	}

	@Override
	public Node buildAST(Node father) {
		this.mFather = father;
		return this;
	}

	@Override
	public List<Node> getChildNodes() {
		List<Node> nodes = new ArrayList<Node>();
		return nodes;
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
