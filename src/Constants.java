/* 
 * Filename: Constants.java
 * Input: None
 * Output: None
 * Purpose: To store some values used in the files Networking.java and GUI.java
*/

public class Constants
{
	public final char COMMAND_START = '@';
	
	public final String CONNECT_INSTRUCTION = "CONNECT";
	public final String DISCONNECT_INSTRUCTION = "DISCONNECT";
	public final String START_USERNAME_CHANGE_INSTRUCTION = "START_CHANGE_USERNAME_LIST";
	public final String START_FILE_TRANSFER_INSTRUCTION = "START_FILE_TRANSFER";
	public final String ASK_FILE_TRANSFER_APPROVAL_INSTRUCTION = "SEND_FILE";
	public final String ASK_FILE_TRANSFER_APPROVAL_ACCEPTED_INSTRUCTION = "SEND_FILE_ACCEPTED";
	public final String ASK_FILE_TRANSFER_APPROVAL_DECLINED_INSTRUCTION = "SEND_FILE_DECLINED";
	public final String KICK_INSTRUCTION = "KICK";
	public final String CLOSING_HOST = "CLOSING";
	public final String NEW_USER = "NEW_USER";
	public final String SEND_VOICE_MESSAGE_INSTRUCTION = "VOICE_MESSAGE";
	public final String ACCEPT_VOICE_MESSAGE = "ACCEPT_VOICE_MESSAGE";
	public final String REJECT_VOICE_MESSAGE = "REJECT_VOICE_MESSAGE";
	
	public final String ASK_GAME = "ASK_GAME_START";
	public final String GAME_ACCEPTED = "GAME_ACCEPTED";
	public final String GAME_DECLINED = "GAME_DECLINED";
	public final String SET_GAME_PORT = "SET_GAME_PORT";
	public final String GAME_VERIFIED = "GAME_VERIFIED";
	public final String ANNOUNCE_WINNER = "WINNER";
	public final String GAME_RESET_APPROVED = "RESET_APPROVED";
	public final String ASK_RESET_APPROVAL = "ASK_RESET";
	
	public final String DEFAULTS_FILE_SAVE_LOCATION = "C:\\Users\\{USER}\\Downloads\\".replace("{USER}", System.getProperty("user.name"));
	public final String DEFAULT_VOICE_MESSAGE_SAVE_LOCATION = "C:\\Users\\{USER}\\Documents\\".replace("{USER}", System.getProperty("user.name"));
	public final String VOICE_MESSAGE_FILENAME = "voice message.wav";
	public final String RECEIVED_VOICE_MESSAGE_FILENAME = "received message.wav";
	
	public final int VOICE_MESSAGE_SAMPLE_RATE = 44100;
	public final int VOICE_MESSAGE_SAMPLE_BITS = 16;
	public final int VOICE_MESSAGE_CHANNELS = 2;
	
	public final int FILE_TRANSFER_PORT = 65000;
	public final int GAME_PORT_START = 62000;
	public final int MAXIMUM_CLIENTS = 253;
	public final int VOICE_MESSAGE_TRANSFER_PORT = 64000;
}