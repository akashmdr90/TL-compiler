package com.registerallocate;

public class RegisterCounting {
	static int count = 0;
	int prevCount=0;
	private static RegisterCounting Instance;

	private RegisterCounting() {
		count = 0;

	}

	public static RegisterCounting getInstance() {
		if (Instance == null) {
			Instance = new RegisterCounting();
		}
		return Instance;
	}

	public int getCount() {

		int temp = count;
        prevCount = count;
		count++;

		return temp;

	}
	
	
	public int getPrevCount() {
		return prevCount;
	}

}
