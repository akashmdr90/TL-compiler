package com.treeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.visit.Visit;

public class SimpleExpnExtn extends TempNode{

	private TokenInfo mToken;
	private SimpleExpn mParseTerm;
	private int mCount;
	Map<String,Attrib> mAttributes = new HashMap<String,Attrib>();
	private boolean mError;
	private String mColor;
	private Node mNode =null;
	private List<Node> mNodes = null;
	private Node mFather;
	private Token mLastToken;

	public SimpleExpnExtn(TokenInfo pWord, SimpleExpn pParseTerm) {
		mToken = pWord;
		mParseTerm = pParseTerm;
		
		
	}
	
	public SimpleExpn getParseTerm() {
		return mParseTerm;
	}
	
	public TokenInfo getToken() {
		return mToken;
	}
	
		
	@Override
	public Node buildAST(Node father) {

		if (mToken != null && mNode == null) {

			Operator node = new Operator(mToken);
			this.mFather = node;
			node.setChilds(getChildNodes());
			mNode = node.buildAST(father);
		}

		return mNode;
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
			if (mParseTerm != null) {
				nodes.add(mParseTerm.buildAST(this.mFather));
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
		mLastToken=token;
		
	}

	@Override
	public void accept(Visit visitor) {
		visitor.visit(this);
		
	}

}
