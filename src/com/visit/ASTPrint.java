package com.visit;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import com.treeNode.Assign;
import com.treeNode.AssignExtn;
import com.treeNode.Attrib;
import com.treeNode.ClauseElse;
import com.treeNode.Declaration;
import com.treeNode.Expr;
import com.treeNode.ExprExtn;
import com.treeNode.Factor;
import com.treeNode.IfStmt;
import com.treeNode.Node;
import com.treeNode.NodeHelper;
import com.treeNode.Operator;
import com.treeNode.treeGen;
import com.treeNode.SimpleExpn;
import com.treeNode.SimpleExpnExtn;
import com.treeNode.Stmt;
import com.treeNode.StmtSeq;
import com.treeNode.TempNode;
import com.treeNode.Term;
import com.treeNode.TermExtn;
import com.treeNode.Type;
import com.treeNode.WriteInt;
import com.scanner.Token;
import com.tool.NodeInfo;

public class ASTPrint implements Visit{
	private StringBuffer pBuffer;
	
	
	public ASTPrint()
	{
		pBuffer = new StringBuffer();
		pBuffer.append("digraph parseTree { \n" + " ordering=out; ");
	}
	
	@Override
	public void visit(Node node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}
	
	public StringBuffer generateAST(treeGen parsetree)
	{
		visit(parsetree);
		pBuffer.append("\n" + "}");
		
		return pBuffer;
	}

	@Override
	public void visit(Assign node) {
		printCommonNodes(node);
	}

	@Override
	public void visit(AssignExtn node) {
		
		printCommonNodes(node);
	}

	@Override
	public void visit(Attrib node) {		
		//printCommonNodes(node);
		
	}

	@Override
	public void visit(Declaration node) {
		String program = "decl list";
		if (node.getChildNodes().size() > 0)
		{
			if(node instanceof Declaration && node.getASTParentNode() instanceof Declaration)
			{
				//node.setCount(father.getCount());
			}
			else
			{
				NodeInfo.buildASTview(node.getASTParentNode().getCount(), node.getCount(), pBuffer, program, "white");
			}
		}
		
		List<Node> childrenNodes = node.getASTChildNodes();
		
		for (Node anode : childrenNodes) {
			if(anode!=null)
				anode.accept(this);
		}
	}

	@Override
	public void visit(ClauseElse node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(Expr node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(ExprExtn node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(Factor node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(IfStmt node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(Operator node) {		
		
		printCommonNodes(node);		
	
	}

	@Override
	public void visit(treeGen pNode) {
		
		String program = "program";
		String color = "/x11/lightgrey";
		if (pNode.hasError()) {
			color = "/pastel13/1";
		}
		NodeInfo.buildASTview(0, pNode.getCount(), pBuffer, program, color);
		
		List<Node> childrenNodes = pNode.getASTChildNodes();
		
		for (Node node : childrenNodes) {
			if(node!=null)
				node.accept(this);
		}
		
	}

	@Override
	public void visit(SimpleExpn node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(SimpleExpnExtn node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(Stmt node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(StmtSeq node) {
		String program = "stmt list";
		if (node.getChildNodes().size() > 0)
		{
			
			if(node instanceof StmtSeq && node.getASTParentNode() instanceof StmtSeq)
			{
				//node.setCount(father.getCount());
			}
			else
			{
				NodeInfo.buildASTview(node.getASTParentNode().getCount(), node.getCount(), pBuffer, program, "white");
			}
		}
		
		List<Node> childrenNodes = node.getASTChildNodes();
		
		for (Node anode : childrenNodes) {
			if(anode!=null)
				anode.accept(this);
		}
		
	}

	@Override
	public void visit(Term node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(TermExtn node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(Type node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}

	@Override
	public void visit(WriteInt node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}
	
	

	@Override
	public void visit(TempNode node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
	}
	
	private void printCommonNodes(Node node)
	{
		String label = NodeHelper.Label.replace("#", "" + node.getCount());

		if (node.hasError()) {
			label = label.replace("$", "/pastel13/1");

		} else {

			label = label.replace("$", node.getColor());

		}
		
		if(node.getASTParentNode() instanceof Declaration){
			
			Attrib temp1 = node.getAttributes().get("type");
			
			if(temp1!=null && !node.hasError())
			{
				label = label.replace("@", "decl:"+NodeInfo.getTokenValue(node)+":"+temp1.getValue().getValue());
				
			}
			else
			{
				label = label.replace("@", "decl:"+NodeInfo.getTokenValue(node));
			}				
			
		}
		else {
			Attrib temp1 = node.getAttributes().get("type");
			//Token temp2=node.getLastChildType();
			Token tokentype=node.getTokenValue().getWord();
			if (temp1 != null && !node.hasError()) {
				
				if(tokentype!=null && tokentype!=Token.COMPARE && tokentype!=Token.ASGN && tokentype!=Token.ADDITIVE && tokentype!=Token.MULTIPLICATIVE)
					label = label.replace("@", NodeInfo.getTokenValue(node) + ":" +temp1.getValue().getValue());
				else
					label = label.replace("@", NodeInfo.getTokenValue(node));

			} else {
				label = label.replace("@", NodeInfo.getTokenValue(node));
			}
		}
		//label = label.replace("@", getTokenValue(node));
		Token currentToekn=node.getTokenValue().getWord() ;
		if (node.getTokenValue().getWord() != Token.INT && currentToekn != Token.BOOL && currentToekn != Token.LP
				&& currentToekn != Token.RP) {
			pBuffer.append(label);
			pBuffer.append("\n");

			pBuffer.append("n" + node.getASTParentNode().getCount() + " -> " + "n" + node.getCount());

			pBuffer.append("\n");
		}
		
		List<Node> childrenNodes = node.getASTChildNodes();		
		for (Node anode : childrenNodes) {
			if(anode!=null)
				anode.accept(this);
		}
	}	
	
	public StringBuffer getASTPrintText()
	{
		return pBuffer;
	}	
}
