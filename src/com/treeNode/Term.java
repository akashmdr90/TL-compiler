package com.treeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeExtension.TokenInfo;
import com.scanner.Token;
import com.visit.Visit;

public class Term extends TempNode {

	private Factor mParseFactor;
	private TermExtn mParseTerm1;
	private int mCount;
	Map<String, Attrib> mAttributes = new HashMap<String, Attrib>();
	private boolean mError;
	private String mColor;
	private List<Node> mNodes = null;
	private Node mFather;
	private Token mLastToken;

	public Term(Factor pParseFactor, TermExtn pParseTerm1) {
		mParseFactor = pParseFactor;
		mParseTerm1 = pParseTerm1;

	}

	public Factor getParseFactor() {
		return mParseFactor;
	}

	public TermExtn getParseTerm1() {
		return mParseTerm1;
	}

	@Override
	public TokenInfo getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node buildAST(Node father) {
		mFather = father;
		return this;
	}

	@Override
	public List<Node> getChildNodes() {
		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			List<Node> nodeOP = new ArrayList<Node>();
			if (mParseFactor != null) {

				nodes.add(mParseFactor.buildAST(this));
			}

			if (mParseTerm1 != null) {
				Operator buildAST = (Operator) mParseTerm1.buildAST(this);
				List<Node> childNodes = mParseTerm1.getChildNodes();
				nodes.addAll(childNodes);
				if (buildAST != null) {
					buildAST.setChilds(nodes);
					nodeOP.add(buildAST);
				} else {
					mNodes = nodes;
					return mNodes;
				}
			}
			mNodes = nodeOP;
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
