package com.buts.research;


import org.apache.commons.lang.RandomStringUtils;




public class Test {
	public static void main(String[] args) {
		String orig = "1111 hahwhwhw */*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/ Date:/Wed,/Mar/18,/2020/Start:/1:02/PM End:/1:02/PM";
		String[] split = orig.split("\\s+");
		for (String s:split) {
			System.out.println(s);
			System.out.println(split.length);
		}
    }

	
}