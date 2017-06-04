package com.scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.treeExtension.TokenInfo;
import com.tool.Writer;

public class TokenSearch {

	private Scanner mScanner;
	private TokenInfo previousToken;
	private TokenInfo token;
	private List<TokenInfo> tokenlist = new ArrayList<TokenInfo>();
	private List<TokenInfo> newTokenlist = new ArrayList<TokenInfo>();
	private int tokenindex;
	public TokenSearch(String fileName) throws FileNotFoundException {
		mScanner = new Scanner(fileName);

		while (true) {
			TokenInfo tokeninfo = mScanner.getNextToken();

			if (tokeninfo.getWord() == Token.EOF) {
				 tokenlist.add(tokeninfo);
				break;
			}
			else {
				tokenlist.add(tokeninfo);
			}
		}

		String tempfilepath = System.getProperty("user.dir") + "/";

		try { 
			Writer.WriteTokenFile(tempfilepath, fileName, tokenlist);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		System.out.println("Scanner Output file is located at path: " + tempfilepath+fileName+".tok");
		if (mScanner.hasError()) { 
			List<String> errors = new ArrayList<String>();
			errors = mScanner.getErrorList();

			for (int i = 0; i < errors.size(); i++)
				System.out.println(errors.get(i));

			System.out.println("Please fix the SCANNER ERROR(s) to continue");

			System.exit(0);
		}

		tokenindex = 0;

	}

	public TokenInfo previousToken() {
		return previousToken;
	}

	public boolean hasError() {
		return mScanner.hasError();
	}

	public TokenInfo token() {
		return token;
	}

	public void next() {
		previousToken = token;
	
		token = tokenlist.get(tokenindex);
		tokenindex++;
	}
}
