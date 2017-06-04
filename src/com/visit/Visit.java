package com.visit;
import com.treeNode.*;

public interface Visit {
	public void visit(Assign node);
	public void visit(AssignExtn node);
	public void visit(Attrib node);
	public void visit(Declaration node);
	public void visit(ClauseElse node);
	public void visit(Expr node);
	public void visit(ExprExtn node);
	public void visit(Factor node);
	public void visit(IfStmt node);
	public void visit(Operator node);	
	public void visit(treeGen node);
	public void visit(SimpleExpn node);
	public void visit(SimpleExpnExtn node);
	public void visit(Stmt node);
	public void visit(StmtSeq node);	
	public void visit(Term node);
	public void visit(TermExtn node);
	public void visit(Type node);
	public void visit(WriteInt node);
	public void visit(TempNode node);
	public void visit(Node node);

}
