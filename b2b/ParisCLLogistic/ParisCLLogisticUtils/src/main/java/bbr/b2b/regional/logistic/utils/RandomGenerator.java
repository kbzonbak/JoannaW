package bbr.b2b.regional.logistic.utils;

import java.util.Random;

public class RandomGenerator {

	//private static final String CHAR_LIST_1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	//private static final int RANDOM_STRING_LENGTH = 6;
	     
	
	public static String generateRandomString(int stringLength){
		StringBuffer randStr = new StringBuffer();
	    for(int i=0; i< stringLength; i++){
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
	    }
	    return randStr.toString();
	}

	
	private static int getRandomNumber() {
		int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length()-1);
        return randomInt;
        
//        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
//        if (randomInt - 1 == -1) {
//            return randomInt;
//        } else {
//            return randomInt - 1;
//        }
    }

}
