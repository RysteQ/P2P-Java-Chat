import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextPane;


public class Networking {
	public static class host extends Thread {
		host (int portNumber) {
			this.portNumber = portNumber;
			initAvailableSlots();
		}
		
		public void bind() throws InterruptedException 
		{
			short availableSlot;
			
			while (true)
			{
				try 
				{
					availableSlot = availableSlot();
					
					if (availableSlot != -1) 
					{
						serverSocket = new ServerSocket(portNumber + portOffset);
						clientSocket[availableSlot] = serverSocket.accept();

						inputStream[availableSlot] = new BufferedReader(new InputStreamReader(clientSocket[availableSlot].getInputStream()));
						outputStream[availableSlot] = new PrintWriter(clientSocket[availableSlot].getOutputStream(), true);
						
						changeAvailableSlotStatus(availableSlot, false, clientSocket[availableSlot]);
						
						portOffset++;
					}
				} catch (IOException e) 
				{
					System.out.println("Error listening to incoming requests\n" + e);
				}
			}
		}

		public void broadcastMessage(String message) 
		{
			for (int i = 0; i < maximumClients; i++)
				if (availableSlots[i] == false)
					outputStream[i].println(message);
		}
		
		public int getPortOffset() 
		{
			return portOffset;
		}

		public String receiveMessages() 
		{
			String receivedMessage;
			
			try 
			{
				for (int i = 0; i < maximumClients; i++) 
				{
					if (availableSlots[i] == false) 
					{
						receivedMessage = inputStream[i].readLine();
							
						if (receivedMessage.isBlank() == false)
							return receivedMessage;	
					}
				}
			} catch (IOException e) 
			{
				System.out.println("Error receiveing message\n" + e);
			}

			return null;
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
			try {
				for (int i = 0; i < maximumClients; i++) 
				{
					outputStream[i].println("CLOSING");
					clientSocket[i].close();
				}
				
				serverSocket.close();
			} catch (IOException e) 
			{
				System.out.println("Error closing sockets\n" + e);
			}
		}
		
		public String[] getIPList() {
			return IPList;
		}

		private short availableSlot() 
		{
			for (short i = 0; i < maximumClients; i++) 
			{
				if (availableSlots[i] == true)
					return i;
			}
			
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
		
		@Override
		public void run() 
		{
			try 
			{
				bind();
			} catch (InterruptedException bindException) 
			{
				bindException.printStackTrace();
			}
		}
		
		// -= Private constants =-
		private final short maximumClients = 254;
		
		// -= Private variables =-
		private ServerSocket serverSocket;
		private Socket[] clientSocket = new Socket[maximumClients];
		private PrintWriter[] outputStream = new PrintWriter[maximumClients];
		private BufferedReader[] inputStream = new BufferedReader[maximumClients];
		private boolean[] availableSlots = new boolean[maximumClients];
		private String[] IPList = new String[maximumClients];
		private int portNumber;
		private int portOffset = 0;
	}

	
	public static class client extends Thread 
	{
		client(String addressToConnectTo, int portNumberToConnectTo) throws InterruptedException 
		{
			connected = connect(addressToConnectTo, portNumberToConnectTo);
		}
		
		public void sendMessage(String message) throws IOException 
		{
			if (connected)
			{
				outputStream.println(message);	
			} else 
			{
				throw new IOException("There is not an active connection");
			}
		}
		
		public String receiveMessage() throws IOException {
			if (connected) 
			{
				try 
				{
					return inputStream.readLine();	
				} catch (IOException e) 
				{
					System.out.println("Error receiveing message\n" + e);
					return null;
				}	
			} else 
			{
				throw new IOException("There is not an active connection");
			}
		}
		
		public void leave() throws IOException 
		{
			if (connected) 
			{
				try 
				{
					outputStream.println("Disconnected");
					clientSocket.close();
					connected = false;	
				} catch (IOException e) 
				{
					System.out.println("Error closing connectiong\n" + e);
				}	
			} else 
			{
				throw new IOException("There is not an active connection");
			}
		}
		
		public boolean isConnected() 
		{
			return connected;
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

				connected = true;
				
				return true;
			} catch (IOException e) 
			{
				// if there is an error while connecting to the specified host, display this message
				System.out.println("Error connecting to " + addressToConnectTo + ":" + portNumberToConnectTo);
				System.out.println(e);
			}
			
			return false;
		}
		
		public void setMessageTextbox(JTextPane messageTextbox) 
		{
			this.messageTextbox = messageTextbox;
		}
		
		@Override
		public void run() 
		{
			String receivedMessage;
			
			while (true) 
			{
				try 
				{
					sendMessage(" ");
					receivedMessage = receiveMessage();

					if (receivedMessage.isBlank() == false)
						messageTextbox.setText(messageTextbox.getText() + receivedMessage + "\n");
					
				Thread.sleep(10);
				
				} 
				catch (IOException receivedMessageException) 
				{
					receivedMessageException.printStackTrace();
				} 
				catch (InterruptedException threadSleepError) 
				{
					threadSleepError.printStackTrace();
				}	
			}
		}
		

		private BufferedReader inputStream;
		private PrintWriter outputStream;
		private Socket clientSocket;
		
		private JTextPane messageTextbox;
		
		private boolean connected = false;
	}
}