package com.treeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.visit.Visit;

public class IfStmt extends TempNode {

	private TokenInfo mIfToken;
	private Expr mParseExpression;
	private TokenInfo mThenToken;
	private StmtSeq mParseStatementSequences;
	private ClauseElse mParseElseExpression;
	private TokenInfo mEndToken;
	private int mCount;
	Map<String,Attrib> mAttributes = new HashMap<String,Attrib>();
	private boolean mError;
	private String mColor;
	private Node mNode =null;
	private List<Node> mNodes = null;
	private Node father =null;
	private Token mLastToken;

	public IfStmt(TokenInfo pIfT, Expr pParseExpression, TokenInfo pThenT,
			StmtSeq pParseStatementSequences, ClauseElse pParseElseExpression, TokenInfo pEndT) {
		mIfToken = pIfT;
		mParseExpression = pParseExpression;
		mThenToken = pThenT;
		mParseStatementSequences = pParseStatementSequences;
		mParseElseExpression = pParseElseExpression;
		mEndToken = pEndT;

	}

	public TokenInfo getIfToken() {
		return mIfToken;
	}

	public Expr getParseExpression() {
		return mParseExpression;
	}

	public TokenInfo getThenToken() {
		return mThenToken;
	}

	public StmtSeq getParseStatementSequences() {
		return mParseStatementSequences;
	}

	public ClauseElse getParseElseExpression() {
		return mParseElseExpression;
	}

	public TokenInfo getEndToken() {
		return mEndToken;
	}

	
	@Override
	public TokenInfo getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node buildAST(Node father) {
		if (mNode == null) {
			Operator node = new Operator(mIfToken);
			this.father= node;
			node.setChilds(getChildNodes());
			mNode = node.buildAST(father);
			
		}

		return mNode;
	}

	
	@Override
	public List<Node> getChildNodes() {

		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			List<Node> nodesthen = new ArrayList<Node>();
			if (mParseExpression != null) {
				nodes.add(mParseExpression.buildAST(this.father));
			}
			Operator node = new Operator(mThenToken);
			node = (Operator)node.buildAST(this.father);
			
			if (mParseStatementSequences != null) {
				nodesthen.add(mParseStatementSequences.buildAST(node));
				node.setChilds(nodesthen);
				nodes.add(node);
			}

			if (mParseStatementSequences != null) {
				nodes.add(mParseElseExpression.buildAST(this.father));
			}
			//node.setChilds(nodesthen);

			//nodes.add(node);
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
		mAttributes.put(key,pAttribute);
		
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
		
		return null;
	}

	@Override
	public Token getLastChildType() {
		
		return mLastToken;
	}

	@Override
	public void setLastChildType(Token token) {
		mLastToken=token;
		
	}
	@Override
	public void accept(Visit visitor) {
		visitor.visit(this);
		
	}

}
