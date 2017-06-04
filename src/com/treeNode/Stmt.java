package com.treeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.visit.Visit;

public class Stmt extends TempNode {

	private Assign mParseAssignment;
	private IfStmt mParseIfStatement;
	private WhileStmt mParseWhileStatement;
	private WriteInt mParseWriteExpression;
	private int mCount;
	Map<String, Attrib> mAttributes = new HashMap<String, Attrib>();
	private boolean mError;
	private String mColor;
	private List<Node> mNodes = null;
	private Node mFather;
	private Token mLastToken;

	public Stmt(Assign pParseAssignment, IfStmt pParseIfStatement, WhileStmt pParseWhileStatement,
			WriteInt pParseWriteExpression) {
		mParseAssignment = pParseAssignment;
		mParseIfStatement = pParseIfStatement;
		mParseWhileStatement = pParseWhileStatement;
		mParseWriteExpression = pParseWriteExpression;

	}

	public Assign getParseAssignment() {
		return mParseAssignment;
	}

	public IfStmt getParseIfStatement() {
		return mParseIfStatement;
	}

	public WhileStmt getParseWhileStatement() {
		return mParseWhileStatement;
	}

	public WriteInt getParseWriteExpression() {
		return mParseWriteExpression;
	}

	@Override
	public Node buildAST(Node father) {
		mFather = father;
		return this;
	}

	@Override
	public TokenInfo getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Node> getChildNodes() {
		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			if (mParseAssignment != null) {
				nodes.add(mParseAssignment.buildAST(this));
			} else if (mParseIfStatement != null) {
				nodes.add(mParseIfStatement.buildAST(this));
			} else if (mParseWhileStatement != null) {
				nodes.add(mParseWhileStatement.buildAST(this));
			} else if (mParseWriteExpression != null) {
				nodes.add(mParseWriteExpression.buildAST(this));
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
		// TODO Auto-generated method stub
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
