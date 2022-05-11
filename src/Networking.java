import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

public class Networking {
	public static class host extends Thread {
		host (int portNumber) 
		{
			this.portNumber = portNumber;
			initAvailableSlots();
		}
		
		public void bind() throws InterruptedException 
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
					usernames[availableSlot] = inputStream[availableSlot].readLine();
						
					portOffset++;
					
					// do other miscelenious actions
					changeAvailableSlotStatus(availableSlot, false, clientSocket[availableSlot]);						
					broadcastUsernames();
					updateGUIUsernames();
				}
			} catch (IOException e) 
			{
				System.out.println("Error listening to incoming requests\n" + e);
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
				outputStream[index].println(message);
			else
				throw new IOException("User not found");
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
		
		public void sendFile(String fileLocation, String clientUsername, int port) throws IOException 
		{
			for (int clientIndex = 0; clientIndex < usernames.length; clientIndex++) 
			{
				if (usernames[clientIndex].equals(clientUsername)) 
				{
					// Create a new socket because for some reason I cannot use the existing one
					Socket fileSocket = new Socket(clientSocket[clientIndex].getInetAddress(), port);
					
					// create the input / output streams
			        InputStream file = new FileInputStream(new File(fileLocation));
			        OutputStream outputStream = fileSocket.getOutputStream();
			        
			        // create a buffer and a counter
			        byte[] buffer = new byte[4096];
			        int bytes;
			        
			        // send the file over the socket
			        while ((bytes = file.read(buffer)) > 0)
			        	outputStream.write(buffer, 0, bytes);
			        
			        // close all of the streams
			        outputStream.close();
			        file.close();
			        fileSocket.close();
					
					return;
				}	
			}
	        
	        throw new IOException("User not found");
		}

		public void receiveFile(String saveLocation, String clientUsername, int port) throws IOException 
		{
			for (int clientIndex = 0; clientIndex < usernames.length; clientIndex++) 
			{
				if (usernames[clientIndex].equals(clientUsername)) 
				{
					ServerSocket fileTransferSocket = new ServerSocket(port);
					Socket fileTransferClient = fileTransferSocket.accept();
					
					File transferedFile = new File(saveLocation);
					
					if (transferedFile.exists() == false)
						transferedFile.createNewFile();
					
			        InputStream input = fileTransferClient.getInputStream();;
			        OutputStream fileOutput = new FileOutputStream(saveLocation);

			        byte[] buffer = new byte[4096];
			        int bytes;
			        
			        while ((bytes = input.read(buffer)) > 0)
			        	fileOutput.write(buffer, 0, bytes);

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
			} catch (IOException e) 
			{
				System.out.println("Error closing sockets\n" + e);
			}
		}

		public void close(int index) 
		{
			try 
			{
				Constants constants = new Constants();
				
				for (int i = 0; i < maximumClients; i++) 
				{
					outputStream[i].println(constants.CLOSING_HOST);
					clientSocket[i].close();
				}
				
				serverSocket.close();
			} catch (IOException e) 
			{
				System.out.println("Error closing sockets\n" + e);
			}
		}
		
		public void setUsernameListModel(JList usernameList, DefaultListModel usernameListModel) 
		{
			this.usernameList = usernameList;
			this.usernameListModel = usernameListModel;
		}
		
		// THIS WILL BE REMOVED
		private void broadcastUsernames() 
		{
			Constants constants = new Constants();
			
			// broadcast the username list change to all users
			broadcastMessage(constants.START_USERNAME_CHANGE_INSTRUCTION);
			
			for (int i = 0; i < maximumClients; i++)
				if (availableSlots[i] == false)
					broadcastMessage("@" + usernames[i]);
			
			// broadcast the end of the username list change to all users
			broadcastMessage(constants.END_USERNAME_CHANGE_INSTRUCTION);
		}
		
		private void updateGUIUsernames() 
		{
			usernameListModel.clear();
			
			for (int i = 0; i < maximumClients; i++)
				if (availableSlots[i] == false)
					usernameListModel.addElement(usernames[i]);
			
			usernameList.setModel(usernameListModel);
		}
		
		private short availableSlot() 
		{
			for (short i = 0; i < maximumClients; i++) 
				if (availableSlots[i] == true)
					return i;
			
			return -1;
		}
		
		private void changeAvailableSlotStatus(int index, boolean newState, Socket clientSocket) 
		{
			if (newState == false)
				IPList[index] = clientSocket.getInetAddress().toString();
			else
				IPList[index] = "NULL";
			
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
		
		private DefaultListModel usernameListModel;
		private JList usernameList;
	}

	
	public static class client
	{
		client(String username, String addressToConnectTo, int portNumberToConnectTo) throws InterruptedException 
		{
			connected = connect(username, addressToConnectTo, portNumberToConnectTo);
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
			} else 
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
			ServerSocket fileTransferSocket = new ServerSocket(port);
			Socket fileTransferClient = fileTransferSocket.accept();
			
			File transferedFile = new File(saveLocation);
			
			if (transferedFile.exists() == false)
				transferedFile.createNewFile();
			
	        InputStream input = fileTransferClient.getInputStream();;
	        OutputStream fileOutput = new FileOutputStream(saveLocation);

	        byte[] buffer = new byte[4096];
	        int bytes;
	        
	        while ((bytes = input.read(buffer)) > 0)
	        	fileOutput.write(buffer, 0, bytes);

	        fileOutput.close();
	        input.close();
	        fileTransferSocket.close();
	        fileTransferClient.close();
		}
		
		public void sendFile(String fileLocation, String address, int port) throws IOException 
		{
			Socket fileSocket = new Socket(address, port);
			
	        InputStream file = new FileInputStream(new File(fileLocation));
	        OutputStream outputStream = fileSocket.getOutputStream();
	        
	        byte[] buffer = new byte[4096];
	        int bytes;
	        
	        while ((bytes = file.read(buffer)) > 0) {
	        	outputStream.write(buffer, 0, bytes);
	        }

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
					
					outputStream.println(constants.DISCONNECT_INSTRUCTION);
					clientSocket.close();
					connected = false;	
				} 
				catch (IOException e) 
				{
					System.out.println("Error closing connectiong\n" + e);
				}	
			}
			
			throw new IOException("There is not an active connection");
		}
		
		public boolean isConnected() 
		{
			return connected;
		}
		
		private boolean connect(String username, String addressToConnectTo, int portNumberToConnectTo) throws InterruptedException 
		{
			try 
			{
				// connect to the specified host
				clientSocket = new Socket(addressToConnectTo, portNumberToConnectTo);
				
				// get the input and output streams
				inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
				
				outputStream.println(username);

				connected = true;
				
				return true;
			} catch (IOException e) 
			{ 
				e.printStackTrace();
			}
			
			return false;
		}
		
		public void setMessageTextbox(JTextPane messageTextbox) 
		{
			this.messageTextbox = messageTextbox;
		}
		
		public void setUsernameListModel(JList usernameList, DefaultListModel usernameListModel) 
		{
			this.usernameListModel = usernameListModel;
			this.usernameList = usernameList;
		}

		private BufferedReader inputStream;
		private PrintWriter outputStream;
		private Socket clientSocket;
		
		private JTextPane messageTextbox;
		private DefaultListModel usernameListModel;
		JList usernameList;
		
		private boolean connected = false;
		private final char instructionparameterSeperator = '@';
	}
}