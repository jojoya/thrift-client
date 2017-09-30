package com.test;

import java.util.Random;

public class RandomString2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Integer[] query = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
		Random random = new Random();
		int i = random.nextInt(query.length);
		String.valueOf(query[i]);
		 System.out.println(query[i]);
	}

}
