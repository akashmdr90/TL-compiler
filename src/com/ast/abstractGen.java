package com.ast;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.treeNode.*;
import com.treeExtension.TokenInfo;
import com.parse.Parsertool;
import com.scanner.Token;
import com.tool.NodeInfo;
import com.visit.ASTChecker;
import com.visit.ASTPrint;

public class abstractGen {

	Parsertool mParser;
	private treeGen mbuildAST=null;
	HashMap<String, Token> typeMap = new HashMap<String, Token>();
	public abstractGen(Parsertool pParser) 
	{
		mParser = pParser;
	}

	public String produceParseTree() 
	{	
		StringBuffer buffer;
		treeGen buildAST = mParser.startParse();	
		Counter instance = Counter.getInstance();
		instance.getCount();	
		generateASTTree(buildAST, null, 0, instance);	
		ASTChecker aststaticcheckvisitor=new ASTChecker();
		aststaticcheckvisitor.checkStaticErrors(buildAST);
		ASTPrint astvisitor=new ASTPrint();
		buffer=astvisitor.generateAST(buildAST);
		setbuildAST(buildAST);
		return buffer.toString();
	}	

	

	public void generateASTTree(Node pNode, Node pParent, int fCounter, Counter pCounter) 
	{
		
	   if (pNode == null)
			return;		
		
	   boolean isError = false;
	   String color = "white";
	   pNode.setPasrserParent(pParent);
	   if (pNode.getTokenValue() != null) {
		TokenInfo parenttokeninfo = pParent.getTokenValue();
		Token currenttoken = pNode.getTokenValue().getWord();

		if (parenttokeninfo != null && parenttokeninfo.getWord() == Token.ident&& (currenttoken == Token.INT || currenttoken == Token.BOOL)) 
		{				
			typeMap.put(parenttokeninfo.getWordValue(),currenttoken);
			} 
			else if (Token.getINTOP().contains(currenttoken)) 
			{
				Attrib attribute = new Attrib();
				attribute.setName("type");
				attribute.setValue(Token.INT);
				pNode.setAttribute(attribute.getName(), attribute);

				if (Token.getArithOP().contains(currenttoken)) {

					Attrib attrtypr = pParent.getAttributes().get("type");
					Attrib attropr = pParent.getAttributes().get("op");

					if (attropr != null) {
						if (attrtypr != null && attrtypr.getValue() == Token.BOOL && attrtypr.getValue() != Token.INT
								&& attropr.getValue() != Token.COMPARE) {
							System.err.println("TYPE_ERROR " + currenttoken);
							isError = true;
							pNode.setError(true);
						}
					} else {
						if (attrtypr != null && attrtypr.getValue() == Token.BOOL && attrtypr.getValue() != Token.INT) {
							System.err.println("TYPE_ERROR " + currenttoken);
							isError = true;
							pNode.setError(true);
						}
					}

				} 

				pParent.setAttribute(attribute.getName(), attribute);
			}
			else if (currenttoken == Token.IF || currenttoken == Token.WHILE)
			{
				Attrib attribute = new Attrib();	
				
				pNode.setAttribute(attribute.getName(), attribute);
				
			} 
			else if (currenttoken == Token.THEN) 
			{

			}
			
			else if (currenttoken == Token.num) 
			{
				TokenInfo word = pNode.getTokenValue();
				long value = Long.parseLong(word.getWordValue().trim());
				Attrib temp = pParent.getAttributes().get("type"); 
				Attrib temp1 = pParent.getAttributes().get("op");
				if(temp1 != null && temp1.getValue()==Token.COMPARE)
				{
					Attrib attribute = new Attrib();
					attribute.setName("type");
					attribute.setValue(Token.INT);
					pNode.setAttribute(attribute.getName(), attribute);
					pParent.setLastChildType(Token.INT);
				}
				else if (temp != null && temp.getValue()==Token.BOOL &&temp.getValue() != Token.INT) {
					System.err.println("TYPE_ERROR " + Token.num);
					isError = true;
					Attrib attribute = new Attrib();
					attribute.setName("type");
					attribute.setValue(Token.INT);
					pNode.setAttribute(attribute.getName(), attribute);
					
				} else if (value > 2147483647 || value < 0) {
					System.err.println("DATA_BOUNDARY_ERROR " + Token.num);
					isError = true;
					pNode.setError(true);
				} else {
					Attrib attribute = new Attrib();
					attribute.setName("type");
					attribute.setValue(Token.INT);
					pNode.setAttribute(attribute.getName(), attribute);
					
					if(temp!=null && temp.getValue() == Token.COMPARE)
					{
						Attrib parentattribute = new Attrib();
						parentattribute.setName("type");
						parentattribute.setValue(Token.BOOL);
						pParent.setAttribute(attribute.getName(), attribute);
					}
						
					else
						pParent.setAttribute(attribute.getName(), attribute);
				}
			}			
			
			
			else if (currenttoken == Token.ident) {							
				

				if (!(pParent instanceof Declaration)) {

					if (typeMap.get(pNode.getTokenValue().getWordValue()) == null) {
						System.err.println("IDENT_NOT_DECLARED" + currenttoken.getValue());
						isError = true;
						pNode.setError(true);
					} else {
						Attrib attributetype = new Attrib();
						attributetype.setName("type");
						attributetype.setValue(typeMap.get(pNode.getTokenValue().getWordValue()));
						pNode.setAttribute(attributetype.getName(), attributetype);
					}
				}				
				
				Token currtokentype=typeMap.get(pNode.getTokenValue().getWordValue());				
				Attrib parenttype = pParent.getAttributes().get("type");		
				Attrib temp = pParent.getAttributes().get("type"); 
				Attrib temp1 = pParent.getAttributes().get("op"); 
								
				if(temp1 != null && temp1.getValue()==Token.COMPARE)
				{
					System.out.println();
					Attrib attribute = new Attrib();
					attribute.setName("type");
					attribute.setValue(Token.INT);
					pNode.setAttribute(attribute.getName(), attribute);
					pParent.setLastChildType(Token.INT);
				}
				else if (currtokentype != null && parenttype != null && currtokentype.getValue() != parenttype.getValue().getValue()) {
					System.err.println("TYPE_ERROR " + pNode.getTokenValue().getWordValue());
					pParent.setError(true);
					isError = true;				
					Attrib attribute = new Attrib();
					attribute.setName("type");
					attribute.setValue(currtokentype);					
					pNode.setAttribute(attribute.getName(), attribute);	
					pNode.getTokenValue().getWord();
				} 
				else if (currtokentype != null && pParent.getLastChildType()!=null && pParent.getLastChildType()!=currtokentype) {
					if(pParent.getLastChildType()!=null && currtokentype.getValue() != pParent.getLastChildType().getValue())
					{	System.err.println("TYPE_ERROR " + pNode.getTokenValue().getWordValue());
						pParent.setError(true);						
						isError = true;
						Attrib attribute = new Attrib();
						attribute.setName("type");	
						attribute.setValue(currtokentype);
						pNode.setAttribute(attribute.getName(), attribute);
						pNode.getTokenValue().getWord();						
					}
				}
				else if (currtokentype == null) {
					for (Node tempNode : pNode.getChildNodes()) {
						if (tempNode.getTokenValue() != null) {
							Token typeToken = tempNode.getTokenValue().getWord();
							
							if (typeToken == Token.BOOL) {								Attrib attribute = new Attrib();
								attribute.setName("type");
								attribute.setValue(Token.BOOL);
								pNode.setAttribute(attribute.getName(), attribute);
								
							} else if (typeToken == Token.INT) {								Attrib attribute = new Attrib();
								attribute.setName("type");
								attribute.setValue(Token.INT);
								pNode.setAttribute(attribute.getName(), attribute);
								
							} else {
								pNode.setError(true);
								isError = true;
								System.err.println("ERROR " + "declaration of ID "
										+ pNode.getTokenValue().getWordValue() + " expected");
							}
						}
					}
					if (pNode.getChildNodes().size() < 1)
					{						
						pNode.setError(true);
						isError = true;
						System.err.println("ERROR " + "declaration of ID " + pNode.getTokenValue().getWordValue()
								+ " expected");
					}

				} else 
				{
					Attrib attribute = new Attrib();
					attribute.setName("type");
									
					if(typeMap.get(pNode.getTokenValue().getWordValue())==null)
					{
						System.err.println("IDENT_NOT_DECLARED" +currenttoken.getValue());
						pNode.setError(true);
						isError = true;
					}
					else
					{
						attribute.setValue(typeMap.get(pNode.getTokenValue().getWordValue()));
						pNode.setAttribute(attribute.getName(), attribute);
					}
				}
				NodeInfo.setAttributes(pParent,pNode);
				
			} else if (currenttoken == Token.boollit) {
				Attrib temp = pParent.getAttributes().get("type");
				if (temp != null && temp.getValue() != Token.BOOL) {
					System.err.println("TYPE_ERROR " + Token.boollit + " " + pNode.getTokenValue().getLineNumber() + " " + temp.getValue());
					Attrib attribute = new Attrib();
					attribute.setName("type");
					attribute.setValue(Token.BOOL);
					pNode.setAttribute(attribute.getName(), attribute);					
					isError = true;
				} else
				{
					Attrib attribute = new Attrib();
					attribute.setName("type");
					attribute.setValue(Token.BOOL);
					pNode.setAttribute(attribute.getName(), attribute);
					pParent.setAttribute(attribute.getName(), attribute);					
				}
			} else if (currenttoken == Token.READINT) {
				Attrib temp = pParent.getAttributes().get("type");
				
				Attrib attribute = new Attrib();
				attribute.setName("type");
				attribute.setValue(Token.INT);				
				
				pNode.setAttribute(attribute.getName(), attribute);
				
				if (temp != null && temp.getValue() != Token.INT) {
					System.err.println("TYPE_ERROR " + Token.READINT);
					isError = true;
				}
				
			} else if (currenttoken == Token.WRITEINT) {

				Attrib attribute = new Attrib();
				attribute.setName("type");
				attribute.setValue(Token.INT);
				pNode.setAttribute(attribute.getName(), attribute);
				pParent.setAttribute(attribute.getName(), attribute);
			}

			Token printToken = pNode.getTokenValue().getWord();
			String label = NodeHelper.Label.replace("#", "" + pNode.getCount());
			if (isError) {
				label = label.replace("$", "/pastel13/1");
				pParent.setError(isError);
				pParent.setColor("/pastel13/1");
				pNode.setColor(color);
			} else {

				if (printToken == Token.num) {
					color = "white";
				} else if (printToken.getValue() ==Token.COMPARE.getValue()  ) {
									
					Attrib opattribute = new Attrib();
					opattribute.setName("op");
					opattribute.setValue(Token.COMPARE);
					pNode.setAttribute("op",opattribute);					
					
				}
				label = label.replace("$", color);
				pNode.setColor(color);
			}			
			
		}
		
		if (pNode instanceof Declaration) 
		{			
			if (pNode.getChildNodes().size() > 0)
			{
				if(pNode instanceof Declaration && pParent instanceof Declaration)
				{
					pNode.setCount(pParent.getCount());
					
				}				
			}
		}

		if (pNode instanceof StmtSeq) {
			if (pNode.getChildNodes().size() > 0)
			{				
				if(pNode instanceof StmtSeq && pParent instanceof StmtSeq)
				{
					pNode.setCount(pParent.getCount());
				}			
			}
		}

		
		List<Node> childNodes = pNode.getChildNodes();		
		int fatherCount = fCounter;
		Map<String, Attrib> attributes = new HashMap<String, Attrib>();
		
		for (Node node : childNodes) {
			if (pNode != null) {
				
				
					if(pNode.getLastChildType()!=null)
					{
					if (!(pNode instanceof Declaration) && (pNode instanceof treeGen)
							&& !(pNode instanceof StmtSeq))
						node.setLastChildType(pNode.getLastChildType());
					}
				
				attributes.putAll(pNode.getAttributes());
				if (pNode.getTokenValue() != null && pNode.getTokenValue().getWord().getValue() == Token.COMPARE.getValue() && Token.COMPARE.getValue()=="=" ) {
				}

				if (node instanceof Declaration || node instanceof StmtSeq) {
					attributes.remove("type");
					NodeInfo.setAttributes(node, attributes);
					node.setCount(pCounter.getCount());
				} else if (node != null && node.getTokenValue() == null) {
					NodeInfo.setAttributes(node, attributes);
					node.setCount(pNode.getCount());
				} else if (node != null && node.getTokenValue() != null) {
					NodeInfo.setAttributes(node, attributes);
					node.setCount(pCounter.getCount());
				}
			} else
				node.setCount(1);
			try {
				generateASTTree(node, pNode, fatherCount, pCounter);
			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			Attrib childatr = node.getAttributes().get("type");			
			if(childatr!=null)
			{
				pNode.setLastChildType(childatr.getValue());					
			}
			
			if(pNode.getLastChildType()==null && node.getLastChildType()!=null)
			{
				pNode.setLastChildType(node.getLastChildType());
			}
						
			if(node.hasError())
			{
				pNode.setError(true);
			}
			
			if(NodeInfo.isValidASTNode(node))
			{
				Node astparent=NodeInfo.getValidASTParentNode(pNode);
				
				if(astparent!=null)
					node.setASTParentNode(astparent);
				astparent.addASTChildNode(node);
			}
		}
	}

	public treeGen getbuildAST() {
		return mbuildAST;
	}

	private void setbuildAST(treeGen mbuildAST) {
		this.mbuildAST = mbuildAST;
	}	
}
