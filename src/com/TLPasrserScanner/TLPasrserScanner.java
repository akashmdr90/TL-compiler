package com.TLPasrserScanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import com.treeNode.treeGen;
import com.ast.abstractGen;
import com.parse.Parsertool;
import com.tool.Writer;
import com.blockCode.Block;
import com.blockCode.BlockGenerator;
import com.Optanalysis.optUtil;
public class TLPasrserScanner {

	Map<String, Block> bMap = new HashMap<String, Block>();
	private abstractGen aGen;
	private BlockGenerator blkGen;
	private optUtil oTree;
        public void CreateAST(String flName, String flPath, String flNtext) 
		{
		Parsertool parser = null;
		try {parser = new Parsertool(flName);} catch (FileNotFoundException e1) {}
		aGen = new abstractGen(parser);
		String outputtree = aGen.produceParseTree();
		String ouputpath = "";
		try {ouputpath = Writer.writToFile(outputtree, flPath, flNtext, ".ast.dot");} catch (IOException e) {}
		System.out.println("The Output of the AST is located at path location " + ouputpath);
	}

	public void GenerateCfgMIPSCode(String flName, String flPath, String flNtext) {
		treeGen buildAST = aGen.getbuildAST();
		if (buildAST != null) {
			if (buildAST.hasError()) {
				System.out.println("Error is there in AST");
			} else {
				blkGen = new BlockGenerator(buildAST);
				blkGen.generateBlocks(buildAST);
				bMap = blkGen.getBlockMap();
				oTree = new optUtil(bMap);
				oTree.performOptimization();
				String outputcfg = oTree.getOptimizedCfg().toString();
				String outputmips = oTree.getOptimizedMips().toString();
				String ouputcfgpath = "";
				try {ouputcfgpath = Writer.writToFile(outputcfg, flPath, flNtext, ".3A.cfg.dot");} catch (IOException e) {}
				System.out.println("CFG Output file is located at path location " + ouputcfgpath);
				String ouputpathmips = "";
				try {ouputpathmips = Writer.writToFile(outputmips, flPath, flNtext, ".s");} catch (IOException e) {}
				System.out.println("MIPS Output file is located at path location " + ouputpathmips);
				System.out.println();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String flNtext = null;
		String fileextension = null;
		String flPath = null;
		String flName = null;
		String filebasename = null;
		boolean setFlag = true;		
		if (args.length < 1) {			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			setFlag = false;}
		if (setFlag == true)
			flName = args[0];
		File file = new File(flName);
		if (!file.exists())
			throw new RuntimeException("File No Found");
		filebasename = file.getName();
		int pos = filebasename.lastIndexOf(".");
		if (pos > 0) {
			flNtext = filebasename.substring(0, pos);
		}
		flPath = file.getAbsoluteFile().getParent();
		int i = flName.lastIndexOf('.');
		if (i > 0) {
			fileextension = flName.substring(i + 1);
		}
		if (!fileextension.contains("tl"))
		throw new RuntimeException("Wrong extension");
		TLPasrserScanner compiler = new TLPasrserScanner();
		compiler.CreateAST(flName, flPath, flNtext);
		compiler.GenerateCfgMIPSCode(flName, flPath, flNtext);
	}
}
