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

public class ASTChecker implements Visit{
	
	private HashMap<String, String> typeMap = new HashMap<String,String>();
	private HashMap<String, Boolean> initmap = new HashMap<String,Boolean>();
	
	public ASTChecker()
	{
		typeMap = new HashMap<String,String>();
		
	}
	
	public void checkStaticErrors(treeGen parsetree)
	{
		visit(parsetree);
	}
	
	@Override
	public void visit(Node node) {
		// TODO Auto-generated method stub
		printCommonNodes(node);
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
		// TODO Auto-generated method stub	
		
		if(Token.getBinaryOperation().contains(node.getTokenValue().getWord()))
		{
			Stack<Node> mStack = new Stack<Node>();
			List<Node> childlist=node.getASTChildNodes();
			
			for(int i=0;i<childlist.size();i++)
			{
				mStack.push(childlist.get(i));
			}
			
			Node rightnode=null;
			Node leftnode=null;
			
			if(!mStack.isEmpty())
				rightnode=mStack.pop();
			if(!mStack.isEmpty())
				leftnode=mStack.pop();
			
			if(rightnode!=null && rightnode.getTokenValue()!=null && rightnode.getTokenValue().getWord()==Token.ident)
			{
				if(initmap.get(rightnode.getTokenValue().getWordValue())==null)
				{
					///initmap.put(leftnode.getTokenValue().getWordValue(), true);
					String msg=" ";
					msg=msg.format("[Semantic Error] at Line %d[Variable %s not initialized]",node.getTokenValue().getLineNumber(),rightnode.getTokenValue().getWordValue());
					System.out.println(msg);
					node.setError(true);
				}
			}
			
			if(leftnode!=null && leftnode.getTokenValue()!=null && leftnode.getTokenValue().getWord()==Token.ident)
			{
				if(initmap.get(leftnode.getTokenValue().getWordValue())==null)
				{
					String msg= " ";			
					msg=msg.format("[Semantic Error] at Line %d [Variable %s not initialized]",node.getTokenValue().getLineNumber(),leftnode.getTokenValue().getWordValue());
					System.out.println(msg);
					node.setError(true);
				}
			}
		}
		
		printCommonNodes(node);
		
		if(node.getTokenValue()!=null)
		{			
			if(node.getTokenValue().getWord()==Token.ASGN)
			{
				Stack<Node> mStack = new Stack<Node>();
				List<Node> childlist=node.getASTChildNodes();
				
				for(int i=0;i<childlist.size();i++)
				{
					mStack.push(childlist.get(i));
				}
				
				Node rightnode=mStack.pop();
				Node leftnode=mStack.pop();
				
				if(leftnode.getTokenValue()!=null && leftnode.getTokenValue().getWord()==Token.ident)
				{
					if(initmap.get(leftnode.getTokenValue().getWordValue())==null)
					{
						initmap.put(leftnode.getTokenValue().getWordValue(), true);
					}
				}
			}
		}
	}

	@Override
	public void visit(treeGen pNode) {
		
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
		List<Node> childrenNodes = node.getASTChildNodes();		
		for (Node anode : childrenNodes) {
			if(anode!=null)
				anode.accept(this);
		}
	}

}
