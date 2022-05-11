/* 
 * Filename: Constants.java
 * Input: None
 * Output: None
 * Purpose: To store some values used in the files Networking.java and GUI.java
*/

public class Constants 
{
	public final String CONNECT_INSTRUCTION = "CONNECT";
	public final String DISCONNECT_INSTRUCTION = "DISCONNECT";
	public final String START_USERNAME_CHANGE_INSTRUCTION = "START_CHANGE_USERNAME_LIST";
	public final String START_FILE_TRANSFER_INSTRUCTION = "START_FILE_TRANSFER";
	public final String ASK_FILE_TRANSFER_APPROVAL_INSTRUCTION = "SEND_FILE";
	public final String ASK_FILE_TRANSFER_APPROVAL_ACCEPTED_INSTRUCTION = "SEND_FILE_ACCEPTED";
	public final String ASK_FILE_TRANSFER_APPROVAL_DECLINED_INSTRUCTION = "SEND_FILE_DECLINED";
	public final String END_USERNAME_CHANGE_INSTRUCTION = "END_CHANGE_USERNAME_LIST";
	public final String KICK_INSTRUCTION = "KICK";
	public final String CLOSING_HOST = "CLOSING";
	public final String DEFAULTS_FILE_SAVE_LOCATION = "C:\\Users\\{USER}\\Downloads\\";
	public final int FILE_TRANSFER_PORT = 65000;
}