import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class Networking 
{
	public static class host extends Thread 
	{
		host (int portNumber) 
		{
			this.portNumber = portNumber;
			initAvailableSlots();
		}
		
		// TODO After this project is done (10/06) I will refactor this whole project even more
		public void bind(boolean getUsername)
		{
			short availableSlot;
			
			try 
			{
				availableSlot = availableSlot();
				
				if (availableSlot != -1)
				{
					// save the user socket
					serverSocket = new ServerSocket(portNumber + portOffset);
					clientSocket[availableSlot] = serverSocket.accept();

					// get the input / output stream
					inputStream[availableSlot] = new BufferedReader(new InputStreamReader(clientSocket[availableSlot].getInputStream()));
					outputStream[availableSlot] = new PrintWriter(clientSocket[availableSlot].getOutputStream(), true);
					
					if (getUsername)
						usernames[availableSlot] = inputStream[availableSlot].readLine();
						
					portOffset++;
					
					changeAvailableSlotStatus(availableSlot, false, clientSocket[availableSlot]);
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		public void broadcastMessage(String message) 
		{
			for (int i = 0; i < maximumClients; i++)
				if (availableSlots[i] == false)
					outputStream[i].println(message);
		}
		
		public void individualMessage(String username, String message) throws IOException 
		{
			int index = 0;
			
			while (usernames[index].equals(username) == false && index != maximumClients)
				index++;
			
			if (index != maximumClients) 
			{
				outputStream[index].println(message);	
			}
			else 
			{
				throw new IOException("User not found");
			}
		}
		
		public int getPortOffset() 
		{
			return portOffset;
		}

		public String receiveMessages() 
		{
			String receivedMessage;
			int index = 0;
			
			try 
			{
				for (index = 0; index < maximumClients; index++) 
				{
					if (availableSlots[index] == false) 
					{
						receivedMessage = inputStream[index].readLine();
							
						if (receivedMessage.isBlank() == false)
							return receivedMessage;	
					}
				}
			} catch (IOException e) 
			{
				changeAvailableSlotStatus(index, false, clientSocket[index]);
			}

			return null;
		}
		
		public void sendFile(String fileLocation, String IP, int port) throws IOException 
		{
			// Create a new socket because for some reason I cannot use the existing one
			Socket fileSocket = new Socket(IP, port);
					
			// create the input / output streams
			InputStream file = new FileInputStream(new File(fileLocation));
			OutputStream outputStream = fileSocket.getOutputStream();
			        
			// create a buffer and a counter
			byte[] buffer = new byte[4096];
			int bytes;
			        
			// send the file over the socket
			while ((bytes = file.read(buffer)) > 0) 
			{
				outputStream.write(buffer, 0, bytes);	
			}
		        
			// close all of the streams
			outputStream.close();
			file.close();
			fileSocket.close();
		}

		public void receiveFile(String saveLocation, String clientUsername, int port) throws IOException 
		{
			for (int clientIndex = 0; clientIndex < usernames.length; clientIndex++) 
			{
				if (usernames[clientIndex].equals(clientUsername)) 
				{
					// connect to the sender
					ServerSocket fileTransferSocket = new ServerSocket(port);
					Socket fileTransferClient = fileTransferSocket.accept();
					
					// make sure the file exists
					File transferedFile = new File(saveLocation);
					
					if (transferedFile.exists() == false)
						transferedFile.createNewFile();
					
					// create the input / output stream
			        InputStream input = fileTransferClient.getInputStream();;
			        OutputStream fileOutput = new FileOutputStream(saveLocation);

			        // receive and save the file
			        byte[] buffer = new byte[4096];
			        int bytes;
			        
			        while ((bytes = input.read(buffer)) > 0)
			        	fileOutput.write(buffer, 0, bytes);

			        // close all of the streams
			        fileOutput.close();
			        input.close();
			        fileTransferSocket.close();
			        fileTransferClient.close();
					
					return;
				}
			}
			
			throw new IOException("User not found");
		}

		public void kickClient(int index) 
		{
			try 
			{
				changeAvailableSlotStatus(index, true, clientSocket[index]);
				clientSocket[index].close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		public void close(int index) 
		{
			try 
			{
				Constants constants = new Constants();
				
				for (int i = 0; i < maximumClients; i++) 
				{
					outputStream[i].println(constants.COMMAND_START + constants.CLOSING_HOST);
					clientSocket[i].close();
				}
				
				serverSocket.close();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		public String getUserIP(String username) 
		{
			for (int i = 0; i < usernames.length; i++)
				if (usernames[i].equals(username))
					return clientSocket[i].getLocalAddress().toString();
			
			return "NULL";
		}
		
		public String getIP() 
		{
			return serverSocket.getLocalSocketAddress().toString();
		}
		
		public String[] getUsarnames() 
		{
			return usernames;
		}
		
		private short availableSlot() 
		{
			for (short i = 0; i < availableSlots.length; i++)
				if (availableSlots[i])
					return i;
			
			return -1;
		}
		
		public boolean isSlotAvailable(int slot) 
		{
			return availableSlots[slot];
		}
		
		private void changeAvailableSlotStatus(int index, boolean newState, Socket clientSocket) 
		{
			if (newState == false)
				IPList[index] = clientSocket.getInetAddress().toString();
			
			availableSlots[index] = newState;
		}
		
		private void initAvailableSlots()
		{
			for (int i = 0; i < maximumClients; i++)
				availableSlots[i] = true;
		}
		
		// -= Private constants =-
		private final short maximumClients = 253;
		
		// -= Private variables =-
		private ServerSocket serverSocket;
		private Socket[] clientSocket = new Socket[maximumClients];
		private PrintWriter[] outputStream = new PrintWriter[maximumClients];
		private BufferedReader[] inputStream = new BufferedReader[maximumClients];
		private boolean[] availableSlots = new boolean[maximumClients];
		private String[] IPList = new String[maximumClients];
		private String[] usernames = new String[maximumClients];
		private int portNumber;
		private int portOffset = 0;
	}

	
	public static class client
	{
		client(String addressToConnectTo, int portNumberToConnectTo) throws InterruptedException 
		{
			connected = connect(addressToConnectTo, portNumberToConnectTo);
		}
		
		private boolean connect(String addressToConnectTo, int portNumberToConnectTo) throws InterruptedException 
		{
			try 
			{
				// connect to the specified host
				clientSocket = new Socket(addressToConnectTo, portNumberToConnectTo);
				
				// get the input and output streams
				inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				outputStream = new PrintWriter(clientSocket.getOutputStream(), true);

				return true;
			} 
			catch (IOException e)
			{ 
				e.printStackTrace();
			}
			
			return false;
		}
		
		public String getLocalAddress() 
		{
			InetAddress IP;
			
			try 
			{
				IP = InetAddress.getLocalHost();
				return IP.toString().split("[////]")[1];
			} 
			catch (UnknownHostException getIPError) 
			{
				getIPError.printStackTrace();
				return null;
			}
		}
		
		public void sendMessage(String message) throws IOException 
		{
			if (connected) 
			{
				outputStream.println(message);
				return;
			}	
			
			throw new IOException("There is not an active connection");
		}
		
		public void sendInstruction(String instruction, String[] parameters) throws IOException 
		{
			if (connected) 
			{
				String completeInstruction = instruction;
				
				for (int i = 0; i < parameters.length; i++)
					completeInstruction += instructionparameterSeperator + parameters[i];
				
				outputStream.println(completeInstruction);
			} 
			else 
			{
				throw new IOException("There is not an active connection");	
			}
		}
		
		public String receiveMessage() throws IOException 
		{
			if (connected)
				return inputStream.readLine();

			throw new IOException("There is not an active connection");
		}

		public void receiveFile(String saveLocation, int port) throws IOException 
		{
			// create a new socket to receive the file from
			ServerSocket fileTransferSocket = new ServerSocket(port);
			Socket fileTransferClient = fileTransferSocket.accept();
			
			// make sure the file exists
			File transferedFile = new File(saveLocation);
			
			if (transferedFile.exists() == false)
				transferedFile.createNewFile();
			
			// create the input / output streams
	        InputStream input = fileTransferClient.getInputStream();;
	        OutputStream fileOutput = new FileOutputStream(saveLocation);

	        // receive the file
	        byte[] buffer = new byte[4096];
	        int bytes;

	        // save the data
	        while ((bytes = input.read(buffer)) > 0) 
	        {
	        	fileOutput.write(buffer, 0, bytes);	
	        }

	        // close the streams
	        fileOutput.close();
	        input.close();
	        fileTransferSocket.close();
	        fileTransferClient.close();
		}
		
		public void sendFile(String fileLocation, String address, int port) throws IOException 
		{
			// create the socket to send the file from
			Socket fileSocket = new Socket(address, port);
			
			// create the input / output streams
	        InputStream file = new FileInputStream(new File(fileLocation));
	        OutputStream outputStream = fileSocket.getOutputStream();
	        
	        // send the file
	        byte[] buffer = new byte[4096];
	        int bytes;
	        
	        while ((bytes = file.read(buffer)) > 0) 
	        {
	        	outputStream.write(buffer, 0, bytes);	
	        }

	        // close the streams
	        outputStream.close();
	        file.close();
	        fileSocket.close();
		}
		
		public void leave() throws IOException 
		{
			if (connected) 
			{
				try 
				{
					Constants constants = new Constants();
					
					outputStream.println(constants.COMMAND_START + constants.DISCONNECT_INSTRUCTION);
					clientSocket.close();
					connected = false;	
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}	
			}
			
			throw new IOException("There is not an active connection");
		}
		
		public boolean isConnected() 
		{
			return connected;
		}
		
		public void setUsernameListModel(JList<String> usernameList)
		{
			this.usernameList = usernameList;
		}

		private BufferedReader inputStream;
		private PrintWriter outputStream;
		private Socket clientSocket;
		
		JList<String> usernameList;
		
		private boolean connected = false;
		private final char instructionparameterSeperator = '|';
	}
}