public class Miscelenious {
	public static boolean validIP(String IPToTest) {
		String[] tokens = IPToTest.split("[.]", 4);
		
		for (String token : tokens) {
			try {
				if (Integer.parseInt(token) > 255 || Integer.parseInt(token) < 0 || token.contains(".") == true)
					return false;
			} catch (Exception e) {
				System.out.println(e);
				return false;
			}
		}
		
		return true;
	}
	
	public static int convertStringToInt(String toConvert) {
		return Integer.parseInt(toConvert);
	}
}