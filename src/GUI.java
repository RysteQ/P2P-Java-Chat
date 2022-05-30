import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class GUI implements ActionListener 
{
	public GUI(JFrame mainForm) 
	{
		this.mainForm = mainForm;
	}
	
	// Initialize GUI
	public void initGUI() 
	{
		initializeForm();
		initJPanels();
		initTextBoxes();
		initLists();
		initButtons();
		initTextFields();
		initLabels();
		initRadioButons();
		initComboBoxes();
		
		initButtonActions();
		initTextboxActions();
		initTextFieldActions();
	}
	
	public void displayGUI() 
	{
		mainForm.setVisible(true);
	}

	// Initialize main form
	private void initializeForm() 
	{
		mainForm = new JFrame();
		mainForm.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));

		mainForm.setTitle("Simple P2P");

		mainForm.getContentPane().setLayout(null);
		mainForm.setBounds(100, 100, 760, 500);
		mainForm.setLocationRelativeTo(null);
		mainForm.setResizable(false);
		
		mainForm.getContentPane().setBackground(Color.WHITE);
		mainForm.setBackground(Color.WHITE);

		ImageIcon P2Picon = new ImageIcon(this.getClass().getResource("/P2P.png"));
		mainForm.setIconImage(P2Picon.getImage());
		
		mainForm.addWindowListener(new WindowAdapter() 
		{
		    @Override
		    public void windowClosing(WindowEvent event) 
		    {
		        if (hostOrClient.equals("HOST"))
		        {
		        	Constants consts = new Constants();
		        	
		        	for (int i = 0; i < consts.MAXIMUM_CLIENTS; i++) 
		        	{
		        		hostConnection.close(i);
		        	}
		        } 
		        else 
		        {
		        	if (clientConnection.isConnected()) 
		        	{
			        	try 
			        	{
							clientConnection.leave();
						} 
			        	catch (IOException leaveError) 
			        	{
							leaveError.printStackTrace();
						}
		        	}
		        }
		    }
		});
	}

	// Initialize all JPanels
	private void initJPanels() 
	{
		textFieldPanel.setBackground(Color.WHITE);
		IPpanel.setBackground(Color.WHITE);
		convPanel.setBackground(Color.WHITE);
		controlsPanel.setBackground(Color.WHITE);
		
		IPpanel.setForeground(Color.BLACK);
		
		textFieldPanel.setBounds(125, 365, 440, 85);
		IPpanel.setBounds(0, 0, 125, 450);
		convPanel.setBounds(125, 0, 435, 360);
		controlsPanel.setBounds(570, 10, controlPanelWidth, 500);
		
		textFieldPanel.setLayout(null);
		IPpanel.setLayout(null);
		convPanel.setLayout(null);
		controlsPanel.setLayout(null);
		
		mainForm.getContentPane().add(textFieldPanel);
		mainForm.getContentPane().add(IPpanel);
		mainForm.getContentPane().add(convPanel);
		mainForm.getContentPane().add(controlsPanel);

		IPscrollPane.setBounds(10, 30, 105, 420);
		IPpanel.add(IPscrollPane);
		
		messagescrollPane.setBounds(0, 10, 435, 350);
		convPanel.add(messagescrollPane);

		textscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textscrollPane.setBounds(0, 0, 435, 85);
		textFieldPanel.add(textscrollPane);
	}

	// Initialize RichTextBoxes
	private void initTextBoxes() 
	{
		messagePane.setFont(new Font("Arial", Font.PLAIN, 14));
		writeMessagePane.setFont(new Font("Arial", Font.PLAIN, 12));

		messagescrollPane.setViewportView(messagePane);
		textscrollPane.setViewportView(writeMessagePane);

		writeMessagePane.setForeground(Color.GRAY);
		messagePane.setEditable(false);

		writeMessagePane.setText(" Write Something");
	}

	// Initialize lists
	private void initLists() 
	{
		IPList.setFont(new Font("Arial", Font.PLAIN, 12));
		IPList.setSelectedIndex(-1);

		IPList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		IPscrollPane.setViewportView(IPList);

		IPList.setModel(listModel);
	}

	// Initialize buttons
	private void initButtons() 
	{
		voiceMessageButton.setBounds(0, 400, 75, 40);
		uploadFileButton.setBounds(85, 400, 75, 40);
		playButton.setBounds(0, 355, controlPanelWidth, 40);
		connectButton.setBounds(0, 75, controlPanelWidth, 30);
		hostButton.setBounds(0, 110, controlPanelWidth, 30);
		
		voiceMessageButton.setBackground(Color.WHITE);
		uploadFileButton.setBackground(Color.WHITE);
		playButton.setBackground(Color.WHITE);
		connectButton.setBackground(Color.WHITE);
		hostButton.setBackground(Color.WHITE);

		voiceMessageButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		uploadFileButton.setFont(new Font("Arial", Font.BOLD, 12));
		playButton.setFont(new Font("Arial", Font.BOLD, 25));
		connectButton.setFont(new Font("Arial", Font.BOLD, 25));
		hostButton.setFont(new Font("Arial", Font.BOLD, 25));

		ImageIcon micIcon = new ImageIcon(this.getClass().getResource("/microphone.png"));

		controlsPanel.add(voiceMessageButton);
		controlsPanel.add(uploadFileButton);
		controlsPanel.add(playButton);
		controlsPanel.add(connectButton);
		controlsPanel.add(hostButton);

		voiceMessageButton.addActionListener(this);
		uploadFileButton.addActionListener(this);
		playButton.setFocusPainted(false);
		connectButton.addActionListener(this);
		hostButton.addActionListener(this);

		playButton.addActionListener(this);
		connectButton.setFocusPainted(false);
		voiceMessageButton.setIcon(micIcon);
		voiceMessageButton.setFocusPainted(false);
		uploadFileButton.setFocusPainted(false);
	}

	// Initialize TextBoxes
	private void initTextFields() 
	{
		usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		usernameTextField.setForeground(Color.GRAY);
		usernameTextField.setBounds(0, 0, controlPanelWidth, 30);
		usernameTextField.setText("Enter username");
		controlsPanel.add(usernameTextField);
		usernameTextField.setColumns(0);
		
		usernameTextField.setBackground(Color.WHITE);
		IPTextField.setBackground(Color.WHITE);
		portTextField.setBackground(Color.WHITE);
		
		IPTextField = new JTextField();
		IPTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		IPTextField.setHorizontalAlignment(SwingConstants.CENTER);
		IPTextField.setForeground(Color.GRAY);
		IPTextField.setText("IP to connect to");
		IPTextField.setBounds(0, 35, 120, 30);
		controlsPanel.add(IPTextField);
		IPTextField.setColumns(0);
		
		portTextField = new JTextField();
		portTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		portTextField.setHorizontalAlignment(SwingConstants.CENTER);
		portTextField.setForeground(Color.GRAY);
		portTextField.setText("Port");
		portTextField.setBounds(120, 35, 40, 30);
		controlsPanel.add(portTextField);
		portTextField.setColumns(0);
	}
	
	// Initialize Labels
	private void initLabels() 
	{
		IPlabel.setFont(new Font("Arial", Font.BOLD, 21));
		portLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		
		IPlabel.setHorizontalAlignment(SwingConstants.CENTER);
		portLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		IPlabel.setBounds(10, 11, 105, 19);
		portLabel.setBounds(0, 260, 85, 20);
		
		ImageIcon IPicon = new ImageIcon(this.getClass().getResource("/ip-address.png"));
		IPlabel.setIcon(IPicon);
		
		IPpanel.add(IPlabel);
		controlsPanel.add(portLabel);
	}
	
	// Initialize RadioButtons
	private void initRadioButons() 
	{
		enableEncryption.setHorizontalAlignment(SwingConstants.CENTER);
		enableEncryption.setFont(new Font("Arial", Font.PLAIN, 18));
		enableEncryption.setBackground(Color.WHITE);
		enableEncryption.setBounds(15, 190, 125, 23);
		enableEncryption.setFocusPainted(false);
		enableEncryption.setSelected(true);
		controlsPanel.add(enableEncryption);
	}

	// Initialize ComboBoxes
	private void initComboBoxes() 
	{
		Theme.setModel(new DefaultComboBoxModel<String>(new String[] {"Light Theme", "Dark Theme"}));
		Theme.setFont(new Font("Arial", Font.PLAIN, 18));
		Theme.setBounds(0, 145, controlPanelWidth, 25);
		Theme.setSelectedIndex(0);
		
		Theme.setBackground(Color.WHITE);
		Theme.addActionListener(this);
		
		controlsPanel.add(Theme);
	}
	
	private void initButtonActions() 
	{
		uploadFileButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (IPList.getSelectedIndex() != -1) 
				{
					Prompts prompt = new Prompts();
					Constants constants = new Constants();
					
					try 
					{
						String fileLocation = prompt.selectFile("Select");
						String[] instructionParameters = 
						{
								IPList.getSelectedValue().toString(),
								usernameTextField.getText(),
								fileLocation
						};
						
						clientConnection.sendInstruction(constants.COMMAND_START + constants.ASK_FILE_TRANSFER_APPROVAL_INSTRUCTION, instructionParameters);
						
						// I have no clue on why I have to wait but if I don't it will throw a bullshit error
						try { Thread.sleep(1000); } catch (Exception e1) { }
						
						clientConnection.sendFile(fileLocation, IPTextField.getText(), constants.FILE_TRANSFER_PORT);
					} 
					catch (IOException conversionError) 
					{
						conversionError.printStackTrace();
					}
				}
			}
		});
	}
	
	private void initTextboxActions() 
	{
		// Change the text based on the writeMessagePane TextBox usage
		writeMessagePane.addFocusListener(new FocusListener() 
		{
	        @Override
	        public void focusGained(FocusEvent e) 
	        {
	        	if (writeMessagePane.getText().equals(" Write Something")) 
	        	{
	        		writeMessagePane.setForeground(Color.BLACK);
	        		writeMessagePane.setText("");
	        		
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) 
			{
				if (writeMessagePane.getText().equals("")) 
				{
					writeMessagePane.setText(" Write Something");
					writeMessagePane.setForeground(Color.GRAY);
				}
			}
	    });
		
		// Send the writeMessagePane text to the host or clients
		// SPAGHETT
		writeMessagePane.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					String sendMessage;
					
					if (hostOrClient.equals("HOST")) 
					{
						if (usernameTextField.getText().equals("Enter username")) 
						{
							sendMessage = "Host - " + writeMessagePane.getText().trim();	
						}
						else 
						{
							sendMessage = usernameTextField.getText() + " - " + writeMessagePane.getText().trim();	
						}
						
						hostConnection.broadcastMessage(sendMessage);
						messagePane.setText(messagePane.getText() + sendMessage + "\n");
						writeMessagePane.setText(null);
					}
					else if (hostOrClient.equals("CLIENT")) 
					{
						if (clientConnection.isConnected())
						{
							try 
							{
								if (usernameTextField.getText().equals("Enter username")) 
								{
									sendMessage = "User - " + writeMessagePane.getText().trim();	
								}
								else 
								{
									sendMessage = usernameTextField.getText() + " - " + writeMessagePane.getText().trim();	
								}
								
								clientConnection.sendMessage(sendMessage);
								writeMessagePane.setText(null);
							} 
							catch (IOException sendMessageException)
							{
								sendMessageException.printStackTrace();
							}
						}
					} 
					else 
					{
						JOptionPane.showMessageDialog(null, "You are not connected / hosting to anyone", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }
		});
	}
	
	private void initTextFieldActions() {
		// Change the text based on the use of the TextBox
		usernameTextField.addFocusListener(new FocusListener() 
		{
	        @Override
	        public void focusGained(FocusEvent e)
	        {
	        	if (usernameTextField.getText().equals("Enter username"))
	        	{
	        		usernameTextField.setText("");
	        		usernameTextField.setForeground(Color.BLACK);
	        	}
	        }

	        public void focusLost(FocusEvent e) { }
	        
	        /*
	         * I will work on this one day, but as of now it will be left out
	         * 
			@Override
			public void focusLost(FocusEvent e) 
			{
				if (usernameTextField.getText().equals("")) 
				{
					usernameTextField.setForeground(Color.GRAY);
					usernameTextField.setText("Enter username");
					
					if (clientConnection.isConnected()) 
					{
						try 
						{
							clientConnection.sendMessage("RENAME");
						} 
						catch (IOException e1) 
						{
							e1.printStackTrace();
						}	
					}
				}
			} */
	    });
		
		IPTextField.addFocusListener(new FocusListener() 
		{
	        @Override
	        public void focusGained(FocusEvent e)
	        {
	        	if (IPTextField.getText().equals("IP to connect to")) 
	        	{
	        		IPTextField.setText("");
	        		IPTextField.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) 
			{
				if (IPTextField.getText().equals("")) 
				{
					IPTextField.setForeground(Color.GRAY);
					IPTextField.setText("Enter new chat IP");
				}
			}
	    });
		
		portTextField.addFocusListener(new FocusListener() 
		{
	        @Override
	        public void focusGained(FocusEvent e) 
	        {
	        	if (portTextField.getText().equals("Port")) 
	        	{
	        		portTextField.setText("");
	        		portTextField.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) 
			{
				if (portTextField.getText().equals("")) 
				{
					portTextField.setForeground(Color.GRAY);
					portTextField.setText("Port");
				}
			}
	    });
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		// create a new thread to start the client connection
		if (e.getSource() == connectButton) 
		{
			if (usernameTextField.getText().equals("Enter username") == false && Verifiers.validIP(IPTextField.getText()) && Verifiers.validPort(Integer.parseInt(portTextField.getText()))) 
			{
				try 
				{
					clientConnection = new Networking.client(IPTextField.getText(), Integer.parseInt(portTextField.getText()));
					
					// if the client is not connected then inform the user and exit
					if (clientConnection.isConnected() == false)
					{
						JOptionPane.showMessageDialog(null, "Couldn't connect to " + IPTextField.getText(), "Error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
						
					try 
					{ 
						clientConnection.sendMessage(usernameTextField.getText());
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
			
					hostButton.removeActionListener(this);

					clientConnection.setUsernameListModel(IPList);
					hostOrClient = "CLIENT";
						
					JOptionPane.showMessageDialog(null, "Connected to " + IPTextField.getText(), "Information", JOptionPane.INFORMATION_MESSAGE);	

					new Thread(new Runnable() 
					{
						public void run() 
						{
							Constants constants = new Constants();
							Prompts prompt = new Prompts();
							String receivedMessage;
										
							while (clientConnection.isConnected()) 
							{
								try 
								{
									// send the "ping message" and receive the response
									clientConnection.sendMessage(" ");
									receivedMessage = clientConnection.receiveMessage();

									if (receivedMessage.isBlank() == true)
										continue;
										
									// check if the response is a command
									if (receivedMessage.charAt(0) == constants.COMMAND_START) 
									{
										receivedMessage = receivedMessage.substring(1, receivedMessage.length());
										String command = receivedMessage.split("[|]")[0];
											
										if (receivedMessage.equals(constants.KICK_INSTRUCTION)) 
										{
											clientConnection.leave();	
										} 
										else if (receivedMessage.split("[|]")[0].equals(constants.START_USERNAME_CHANGE_INSTRUCTION)) 
										{
											JOptionPane.showMessageDialog(null, "New user connected", "Information", JOptionPane.INFORMATION_MESSAGE);
											
											IPListModel.clear();
											
											for (int index = 1; index < receivedMessage.split("[|]").length; index++) 
											{
												IPListModel.addElement(receivedMessage.split("[|]")[index]);
											}
											
											IPList.setModel(IPListModel);
										} 
										else if (command.equals(constants.ASK_FILE_TRANSFER_APPROVAL_INSTRUCTION)) 
										{
											if (prompt.showMessageBoxChoice(mainForm, "Question", "Do you want to receive a file from the user " + receivedMessage.split("[|]")[1] + " ?")) 
											{
												String_Manipulator stringManipulator = new String_Manipulator();
															
												String[] charactersToSplit = { "|", "\\\\" };
												String saveLocation = constants.DEFAULTS_FILE_SAVE_LOCATION.replace("{USER}", System.getProperty("user.name"));
									
												String[] parameters = 
												{ 
													clientConnection.getLocalAddress(), 
													receivedMessage.split("[|]")[1] 
												};
												
												clientConnection.sendInstruction(constants.COMMAND_START + constants.ASK_FILE_TRANSFER_APPROVAL_ACCEPTED_INSTRUCTION, parameters);
															
												// again, I have no clue on why I have to wait
												try { Thread.sleep(1000); } catch (Exception e1) { }
															
												clientConnection.receiveFile(saveLocation + stringManipulator.getLastWord(receivedMessage, charactersToSplit), constants.FILE_TRANSFER_PORT);
											}
										} 
										else if(command.equals(constants.ASK_GAME)) 
										{
											if (prompt.showMessageBoxChoice(mainForm, "Question", "Do you want to play a game with the user " + receivedMessage.split("[|]")[3] + " ?")) 
											{
												// over here there is probably a conflict at the host end so my solution to fix this on time is to just flood the host with
												// the same message until it gets verified, it will probably be removed later on although I make no promises
												while (clientConnection.receiveMessage().equals(constants.COMMAND_START + constants.GAME_VERIFIED) == false) 
												{
													clientConnection.sendMessage(constants.COMMAND_START + constants.GAME_ACCEPTED + "|" + receivedMessage.split("[|]")[3] + "|" + usernameTextField.getText());	
												}

												String IPToConnectTo;
												
												if (receivedMessage.split("[|]")[3].equals("Host"))
												{
													IPToConnectTo = IPTextField.getText();
												} 
												else 
												{
													IPToConnectTo = receivedMessage.split("[|]")[1];
												}
												
												try { Thread.sleep(100); } catch (InterruptedException e) { }
												
												tictactoe game = new tictactoe(true, false, IPToConnectTo, constants.GAME_PORT_START);
												game.start();
											} 
											else 
											{
												clientConnection.sendMessage(constants.COMMAND_START + constants.GAME_DECLINED);
											}
										}	
									} 
									else
									{
										// if the response is not a command then add the message to the message history textbox
										messagePane.setText(messagePane.getText() + receivedMessage + "\n");
									}
										
									try 
									{
										Thread.sleep(10);	
									} 
									catch (InterruptedException error) 
									{
										error.printStackTrace();
									}
								}
								catch (IOException receivedMessageException) 
								{
									try 
									{
										clientConnection.leave();
										IPListModel.clear();
									} 
									catch (IOException leaveError) 
									{
										leaveError.printStackTrace();
									}
								} 
							}
						}
					}).start();
				}
				catch (InterruptedException connectionException) 
				{
					JOptionPane.showMessageDialog(null, "There was an error while connecting to the host", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} 
			else
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid username / IP / port", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// initialize host connection
		if (e.getSource() == hostButton)
		{
			int port = new Random().nextInt(60000) + 1000;
			
			hostConnection = new Networking.host(port);
			
			connectButton.removeActionListener(this);
			
			new Thread(new Runnable() 
			{
				public void run() 
				{
					Constants constants = new Constants();
					
					String[] usernameList = hostConnection.getUsarnames();
					String usernames = "";
					
					while (true) 
					{
						hostConnection.bind(true);

						// update / broadcast the username list
						IPListModel.clear();
						
						for (int i = 0; i < constants.MAXIMUM_CLIENTS; i++) 
						{
							if (hostConnection.isSlotAvailable(i) == false) 
							{
								IPListModel.addElement(usernameList[i]);
								usernames += usernameList[i] + "|";	
							}
						}
						
						usernames = usernames.substring(0, usernames.length() - 1);
						
						hostConnection.broadcastMessage(constants.COMMAND_START + constants.START_USERNAME_CHANGE_INSTRUCTION + "|" + usernames);
						IPList.setModel(IPListModel);
						
						usernames = "";
					}
				}
			}).start();
			
			// create a new thread to broadcast the receiving messages and update the port label
			new Thread(new Runnable() 
			{
				public void run()
				{
					String filename = "";
					boolean sendFile = false;
					
					while (true) 
					{
						hostConnection.broadcastMessage(" ");
						portLabel.setText("Port: " + String.valueOf(port + hostConnection.getPortOffset()));
						
						Constants constants = new Constants();
						String_Manipulator stringManipulator = new String_Manipulator();
						
						String receivedMessage = hostConnection.receiveMessages();
						
						if (receivedMessage == null)
							continue;
						
						if (receivedMessage.charAt(0) == constants.COMMAND_START) 
						{
							receivedMessage = receivedMessage.substring(1);
							
							if (receivedMessage.split("[|]")[0].equals(constants.ASK_FILE_TRANSFER_APPROVAL_INSTRUCTION)) 
							{
								try 
								{
									String[] charactersToSplit = { "|", "\\\\" };
									filename = stringManipulator.getLastWord(receivedMessage, charactersToSplit);
									String saveLocation = constants.DEFAULTS_FILE_SAVE_LOCATION.replace("{USER}", System.getProperty("user.name")) + stringManipulator.getLastWord(receivedMessage, charactersToSplit);
										
									hostConnection.receiveFile(saveLocation, receivedMessage.split("[|]")[2], constants.FILE_TRANSFER_PORT);
									hostConnection.individualMessage(receivedMessage.split("[|]")[1], constants.COMMAND_START + constants.ASK_FILE_TRANSFER_APPROVAL_INSTRUCTION + "|" + receivedMessage.split("[|]")[2] + "|" + filename);
									
									sendFile = true;
								} 
								catch (IOException e)
								{
									e.printStackTrace();
								}
							} 
							else if (receivedMessage.split("[|]")[0].equals(constants.ASK_FILE_TRANSFER_APPROVAL_ACCEPTED_INSTRUCTION) && sendFile) 
							{
								try 
								{
									// I never code in java so the code is in the same shape as the Greek economy
									try { Thread.sleep(2000); } catch (Exception e1) { }
									
									String saveLocation = constants.DEFAULTS_FILE_SAVE_LOCATION.replace("{USER}", System.getProperty("user.name")) + filename;
									String IP = receivedMessage.split("[|]")[1];
									
									hostConnection.sendFile(saveLocation, IP, constants.FILE_TRANSFER_PORT);
										
									File toDelete = new File(saveLocation);
									toDelete.delete();
										
									sendFile = false;
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
								}
							} 
							else if (receivedMessage.split("[|]")[0].equals(constants.ASK_FILE_TRANSFER_APPROVAL_DECLINED_INSTRUCTION)) 
							{
								File toDelete = new File(constants.DEFAULTS_FILE_SAVE_LOCATION.replace("{USER}", System.getProperty("user.name")) + filename);
								toDelete.delete();
									
								sendFile = false;
							} 
							else if (receivedMessage.split("[|]")[0].equals(constants.ASK_GAME)) 
							{
								String toSend = receivedMessage.split("[|]")[1];
								String IP = receivedMessage.split("[|]")[2];
								String username = receivedMessage.split("[|]")[3];

								try 
								{
									hostConnection.individualMessage(toSend, 
										constants.COMMAND_START 
										+ constants.ASK_GAME
										+ "|"
										+ IP
										+ "|"
										+ constants.GAME_PORT_START
										+ "|"
										+ username
									);
								} 
								catch (IOException individualErrorMessage) 
								{
									individualErrorMessage.printStackTrace();
								}
							} 
							else if (receivedMessage.split("[|]")[0].equals(constants.GAME_ACCEPTED) || receivedMessage.split("[|]")[0].equals(constants.GAME_DECLINED)) 
							{
								String response = receivedMessage.split("[|]")[0];
								String username = receivedMessage.split("[|]")[1];
								String userThatVerified = receivedMessage.split("[|]")[2];
								
								try 
								{
									hostConnection.individualMessage(username, constants.COMMAND_START + response);
									hostConnection.individualMessage(userThatVerified, constants.COMMAND_START + constants.GAME_VERIFIED);
								} 
								catch (IOException individualMessageError) 
								{
									individualMessageError.printStackTrace();
								}
							}
						} 
						else 
						{
							messagePane.setText(messagePane.getText() + receivedMessage + "\n");
							hostConnection.broadcastMessage(receivedMessage);	
						}
						
						
						try { Thread.sleep(100); } catch (InterruptedException threadSleepError) { threadSleepError.printStackTrace(); }
					}
				}
			}).start();;
			
			hostOrClient = "HOST";
			
			JOptionPane.showMessageDialog(null, "Now listening at port " + port, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		

		if(e.getSource() == Theme) 
		{
			if(Theme.getSelectedIndex() == 0)
				changeBackground(Color.WHITE, Color.BLACK);
			
			if(Theme.getSelectedIndex() == 1)
				changeBackground(darkerBG, lightTextColor);
		}
		
		if(e.getSource() == playButton) 
		{
			if (IPList.getSelectedIndex() == -1) 
			{
				tictactoe singleGame = new tictactoe(false, false, "NULL", 0);
				singleGame.start();
			}
			else 
			{	
				if (hostOrClient.equals("CLIENT")) 
				{	
					new Thread(new Runnable() 
					{
						public void run()
						{
							Constants consts = new Constants();
							String opponentResponse = null;
							
							String[] arguments = 
							{
								IPList.getSelectedValue(),
								clientConnection.getLocalAddress(),
								usernameTextField.getText()
							};
							
							try 
							{
								clientConnection.sendInstruction(consts.COMMAND_START + consts.ASK_GAME, arguments);

								do 
								{
									opponentResponse = clientConnection.receiveMessage();
									opponentResponse = opponentResponse.substring(1);
								} 
								while ((opponentResponse.split("[|]")[0].equals(consts.GAME_ACCEPTED) || opponentResponse.split("[|]")[0].equals(consts.GAME_DECLINED)) == false);
								
								if (opponentResponse.split("[|]")[0].equals(consts.GAME_ACCEPTED))
								{
									tictactoe game = new tictactoe(true, true, "NULL", consts.GAME_PORT_START);
									game.start();
								} 
								else 
								{
									JOptionPane.showMessageDialog(null, "Opponent declined", "Error", JOptionPane.ERROR_MESSAGE);	
								}
							} 
							catch (IOException error) 
							{
								error.printStackTrace();
							}
						}
					}).start();
				} else if (hostOrClient.equals("HOST")) 
				{
					new Thread(new Runnable() 
					{
						public void run()
						{
							Constants consts = new Constants();
							String opponentResponse = null;
							String opponentIP = "";
							
							try 
							{
								hostConnection.individualMessage(IPList.getSelectedValue(), 
									consts.COMMAND_START 
									+ consts.ASK_GAME 
									+ "|" 
									+ "NULL"
									+ "|" 
									+ consts.GAME_PORT_START 
									+ "|"
									+ "Host"
								);

								do 
								{
									while (opponentResponse == null) 
									{
										opponentResponse = hostConnection.receiveMessages();
									}
									
									hostConnection.individualMessage(IPList.getSelectedValue(), 
										consts.COMMAND_START
										+ consts.GAME_VERIFIED
									);
									
									opponentResponse = opponentResponse.substring(1);
								} 
								while ((opponentResponse.split("[|]")[0].equals(consts.GAME_ACCEPTED) || opponentResponse.split("[|]")[0].equals(consts.GAME_DECLINED)) == false);
							} 
							catch (IOException error) 
							{
								error.printStackTrace();
							}
							
							if (opponentResponse.split("[|]")[0].equals(consts.GAME_ACCEPTED))
							{
								opponentIP = hostConnection.getUserIP(IPList.getSelectedValue());

								tictactoe game = new tictactoe(true, true, opponentIP, consts.GAME_PORT_START);
								game.start();
							} 
							else 
							{
								JOptionPane.showMessageDialog(null, "Opponent declined", "Error", JOptionPane.ERROR_MESSAGE);	
							}
						}
					}).start();
				}
			}
		}
	}

	public void changeBackground(Color backgroundColour, Color foregroundColour) {
		mainForm.getContentPane().setBackground(backgroundColour);
		textFieldPanel.setBackground(backgroundColour);
		IPpanel.setBackground(backgroundColour);
		convPanel.setBackground(backgroundColour);
		enableEncryption.setBackground(backgroundColour);
		controlsPanel.setBackground(backgroundColour);
		
		Theme.setBackground(backgroundColour);

		IPlabel.setForeground(foregroundColour);
		enableEncryption.setForeground(foregroundColour);
			
		voiceMessageButton.setForeground(foregroundColour);
		uploadFileButton.setForeground(foregroundColour);
		playButton.setForeground(foregroundColour);
		connectButton.setForeground(foregroundColour);
		hostButton.setForeground(foregroundColour);

		IPList.setForeground(foregroundColour);
		IPList.setBackground(backgroundColour);

		voiceMessageButton.setBackground(backgroundColour);
		uploadFileButton.setBackground(backgroundColour);
		playButton.setBackground(backgroundColour);
		connectButton.setBackground(backgroundColour);
		hostButton.setBackground(backgroundColour);

		Theme.setBackground(backgroundColour);
		Theme.setForeground(foregroundColour);

		usernameTextField.setBackground(backgroundColour);
		IPTextField.setBackground(backgroundColour);
		portTextField.setBackground(backgroundColour);

		messagePane.setBackground(backgroundColour);
		messagePane.setForeground(foregroundColour);
		writeMessagePane.setBackground(backgroundColour);
			
		if (writeMessagePane.getText().equals(" Write Something")) 
		{
			writeMessagePane.setForeground(Color.GRAY);		
		}
		else 
		{
			writeMessagePane.setForeground(foregroundColour);
		}
	}
	
	// -= Private GUI Elements =-
	private JFrame mainForm = new JFrame();
	
	private JButton voiceMessageButton = new JButton("");
	private JButton uploadFileButton = new JButton("Upload");
	private JButton playButton = new JButton("Play");
	private JButton connectButton = new JButton("Connect");
	private JButton hostButton = new JButton("Host");
	
	private JPanel textFieldPanel = new JPanel();
	private JPanel IPpanel = new JPanel();
	private JPanel convPanel = new JPanel();
	private JPanel controlsPanel = new JPanel();
	
	private JScrollPane IPscrollPane = new JScrollPane();
	private JScrollPane messagescrollPane = new JScrollPane();
	private JScrollPane textscrollPane = new JScrollPane();
	
	private JTextPane messagePane = new JTextPane();
	private JTextPane writeMessagePane = new JTextPane();
	
	private JTextField usernameTextField = new JTextField();
	private JTextField IPTextField = new JTextField();
	private JTextField portTextField = new JTextField();
	
	private DefaultListModel<String> IPListModel = new DefaultListModel<String>();
	private JList<String> IPList = new JList<String>(IPListModel);
	
	private JComboBox<String> Theme = new JComboBox<String>();
	private JRadioButton enableEncryption = new JRadioButton("Encryption");
	
	private JLabel IPlabel = new JLabel("IP List");
	private JLabel portLabel = new JLabel("");

	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	
	private Color lightTextColor = new Color(0xbebebe);
	private Color darkerBG = new Color(0x252526);
	
	// -= Networking =-
	private Networking.client clientConnection;
	private Networking.host hostConnection;
	
	private String hostOrClient = "NONE";
	
	// -= Constants =-
	private final int controlPanelWidth = 160;
}
