package com.treeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.visit.Visit;

public class ClauseElse extends TempNode {

	private TokenInfo mToken;
	private StmtSeq mParseStatementSequences;
	private int mCount;
	Map<String, Attrib> mAttributes = new HashMap<String, Attrib>();
	private boolean mError;
	private String mColor;
	private Node mNode = null;
	private List<Node> mNodes = null;
	private Node mFather = null;
	private Token mLastToken;

	public ClauseElse(TokenInfo pToken, StmtSeq pParseStatementSequences) {
		mToken = pToken;
		mParseStatementSequences = pParseStatementSequences;

	}

	public StmtSeq getParseStatementSequences() {
		return mParseStatementSequences;
	}

	public TokenInfo getToken() {
		return mToken;
	}

	@Override
	public TokenInfo getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node buildAST(Node father) {
		if (mNode == null) {
			Operator node = new Operator(mToken);
			this.mFather = node;
			node.setChilds(getChildNodes());
			mNode = node.buildAST(father);
		}
		return mNode;
	}

	@Override
	public List<Node> getChildNodes() {
		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();

			if (mParseStatementSequences != null) {
				nodes.add(mParseStatementSequences.buildAST(this.mFather));
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node getFather() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getLastChildType() {
		// TODO Auto-generated method stub
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
