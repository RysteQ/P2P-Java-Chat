/* 
 * Filename: Miscelenious.java
 * Purpose: This is mostly used to check if the user input is file back to GUI.java
*/


public class Verifiers 
{
	public static boolean validIP(String IPToTest) 
	{
		String[] tokens = IPToTest.split("[.]", 4);
		
		for (String token : tokens)
		{
			try 
			{
				if (Integer.parseInt(token) > 255 || Integer.parseInt(token) < 0 || token.contains(".") == true)
					return false;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static boolean validPort(int portNumber) 
	{
		if (portNumber < 1000 || portNumber > 61000)
			return false;
					
		return true;
	}
}