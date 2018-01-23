package com.gbm.mgb.utils.order;

import java.util.Random;

public class RandomCode {

	public static String getSixRandCode() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;

	}
	
	public static String getThreeRandomCode() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 3; i++) {
			result += random.nextInt(10);
		}
		return result;
	}
}
