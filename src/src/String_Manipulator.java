public class String_Manipulator {
	public String getLastWord(String toSplit, String[] charactersToSplit) 
	{
		for (int i = 0; i < charactersToSplit.length; i++) 
		{
			toSplit = toSplit.split("[" + charactersToSplit[i] + "]")[toSplit.split("[" + charactersToSplit[i] + "]").length - 1];	
		}
		
		return toSplit;
	}
}