/* 
 * Filename: Miscelenious.java
 * Purpose: This is mostly used to check if the user input is file back to GUI.java
*/


// THIS WILL BE REWORKED UPON


public class Miscelenious {
	public static boolean validIP(String IPToTest) 
	{
		String[] tokens = IPToTest.split("[.]", 4);
		
		for (String token : tokens)
		{
			try 
			{
				if (Integer.parseInt(token) > 255 || Integer.parseInt(token) < 0 || token.contains(".") == true)
					return false;
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static int convertStringToInt(String toConvert) 
	{
		return Integer.parseInt(toConvert);
	}
	
	public static boolean validPort(int portNumber) 
	{
		if (portNumber < 1000 || portNumber > 61000)
			return false;
					
		return true;
	}
	
	public String getLastWord(String toSplit, String[] charactersToSplit) 
	{
		for (int i = 0; i < charactersToSplit.length; i++)
			toSplit = toSplit.split(charactersToSplit[i])[toSplit.split(charactersToSplit[i]).length - 1];
		
		return toSplit;
	}
}