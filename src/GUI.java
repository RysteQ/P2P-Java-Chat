import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.swing.text.*;


public class GUI implements ActionListener {	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI window = new GUI();
					window.mainForm.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	// Initialize GUI
	public GUI() 
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
		
		mainForm.getContentPane().setBackground(lightBG);
		mainForm.setBackground(lightBG);

		ImageIcon P2Picon = new ImageIcon(this.getClass().getResource("/P2P.png"));
		mainForm.setIconImage(P2Picon.getImage());
		mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Initialize all JPanels
	private void initJPanels() 
	{
		textFieldPanel.setBackground(lightBG);
		IPpanel.setBackground(lightBG);
		convPanel.setBackground(lightBG);
		controlsPanel.setBackground(lightBG);
		
		IPpanel.setForeground(Color.BLACK);
		
		textFieldPanel.setBounds(125, 365, 440, 85);
		IPpanel.setBounds(0, 0, 125, 450);
		convPanel.setBounds(125, 0, 435, 360);
		controlsPanel.setBounds(570, 10, 160, 440);
		
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
					writeMessagePane.setForeground(lightTextColor);
				}
			}
	    });
		
		// Send the writeMessagePane text to the host or clients
		writeMessagePane.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					String sendMessage;
					
					if (hostOrClient.equals("HOST")) 
					{
						if (usernameTextField.getText().equals("Enter username"))
							sendMessage = "Host - " + writeMessagePane.getText().trim();
						else
							sendMessage = usernameTextField.getText() + " - " + writeMessagePane.getText().trim();
						
						hostConnection.broadcastMessage(sendMessage);
						messagePane.setText(messagePane.getText() + sendMessage + "\n");
						writeMessagePane.setText("");
					} else if (hostOrClient.equals("CLIENT")) 
					{
						if (clientConnection.isConnected())
						{
							try 
							{
								if (usernameTextField.getText().equals("Enter username"))
									sendMessage = "User - " + writeMessagePane.getText().trim();
								else
									sendMessage = usernameTextField.getText() + " - " + writeMessagePane.getText().trim();
								
								clientConnection.sendMessage(sendMessage);
								writeMessagePane.setText("");
							} catch (IOException sendMessageException) 
							{
								sendMessageException.printStackTrace();
							}
						}
					} else 
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

	// Initialize lists
	private void initLists() 
	{
		IPList.setFont(new Font("Arial", Font.PLAIN, 12));
		IPList.setSelectedIndex(-1);
		IPList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		IPList.setBackground(Color.WHITE);
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
		hostButton.setBounds(0, 110, 160, 30);
		
		voiceMessageButton.setBackground(Color.WHITE);
		uploadFileButton.setBackground(Color.WHITE);
		playButton.setBackground(Color.WHITE);
		connectButton.setBackground(Color.WHITE);
		hostButton.setBackground(Color.WHITE);

		voiceMessageButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		uploadFileButton.setFont(new Font("Arial", Font.BOLD, 20));
		playButton.setFont(new Font("Arial", Font.BOLD, 25));
		connectButton.setFont(new Font("Arial", Font.BOLD, 25));
		hostButton.setFont(new Font("Arial", Font.BOLD, 25));

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
		voiceMessageButton.setIcon(micIconDark);
		voiceMessageButton.setFocusPainted(false);
		uploadFileButton.setFocusPainted(false);
		
		voiceMessageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uploadFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		connectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		hostButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
		
		
		usernameTextField.setBackground(Color.WHITE);
		IPTextField.setBackground(Color.WHITE);
		portTextField.setBackground(Color.WHITE);
		
		// Change the text based on the use of the TextBox
		usernameTextField.addFocusListener(new FocusListener() 
		{
	        @Override
	        public void focusGained(FocusEvent e){
	        	if (usernameTextField.getText().equals("Enter username"))
	        	{
	        		usernameTextField.setText("");
	        		usernameTextField.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {
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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
					}
				}
			}
	    });
		
		IPTextField.addFocusListener(new FocusListener() 
		{
	        @Override
	        public void focusGained(FocusEvent e){
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
	
	// Initialize Labels
	private void initLabels() 
	{
		IPlabel.setFont(new Font("Arial", Font.BOLD, 21));
		portLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		
		IPlabel.setHorizontalAlignment(SwingConstants.CENTER);
		portLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		IPlabel.setBounds(10, 11, 105, 19);
		portLabel.setBounds(0, 260, 85, 20);
		
		
		IPlabel.setIcon(IPiconDark);
		
		IPpanel.add(IPlabel);
		controlsPanel.add(portLabel);
	}
	
	// Initialize RadioButtons
	private void initRadioButons() 
	{
		enableEncryption.setFont(new Font("Arial", Font.PLAIN, 18));
		enableEncryption.setBackground(lightBG);
		enableEncryption.setBounds(0, 190, 140, 23);
		enableEncryption.setFocusPainted(false);
		enableEncryption.setSelected(true);
		controlsPanel.add(enableEncryption);
	}

	// Initialize ComboBoxes
	private void initComboBoxes() 
	{
		Theme.setModel(new DefaultComboBoxModel(new String[] {"Light Theme", "Dark Theme"}));
		Theme.setFont(new Font("Arial", Font.PLAIN, 18));
		Theme.setBounds(0, 150, 160, 25);
		Theme.setSelectedIndex(0);
		Theme.setBackground(Color.WHITE);
		Theme.addActionListener(this);
		controlsPanel.add(Theme);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		// create a new thread to start the client connection
		if (e.getSource() == connectButton) 
		{
			if (hostOrClient.equals("HOST") == false) 
			{
				if (usernameTextField.getText().length() != 0 && usernameTextField.getText().equals("Enter username") == false) 
				{
					if (Miscelenious.validIP(IPTextField.getText())) 
					{
						try 
						{
							if (Miscelenious.convertStringToInt(portTextField.getText()) >= 1000) 
							{
								clientConnection = new Networking.client(usernameTextField.getText(), IPTextField.getText(), Miscelenious.convertStringToInt(portTextField.getText()));
								
								if (clientConnection.isConnected()) 
								{
									hostButton.enable(false);
									usernameTextField.enable(false);
									
									clientConnection.setMessageTextbox(messagePane);
									clientConnection.setUsernameListModel(IPList, IPListModel);
									clientConnection.start();
									
									JOptionPane.showMessageDialog(null, "Connected to " + IPTextField.getText(), "Information", JOptionPane.INFORMATION_MESSAGE);	
								
									hostOrClient = "CLIENT";
								} else 
								{
									JOptionPane.showMessageDialog(null, "Couldn't connect to " + IPTextField.getText(), "Error", JOptionPane.INFORMATION_MESSAGE);
								}	
							}
						} catch (InterruptedException connectionException) 
						{
							JOptionPane.showMessageDialog(null, "There was an error while connecting to the host", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "Please enter a IP !", "Error", JOptionPane.ERROR_MESSAGE);
					}	
				} else 
				{
					JOptionPane.showMessageDialog(null, "Please enter a username", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else 
			{
				JOptionPane.showMessageDialog(null, "You cannot connect to someone due to you being a host", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// initialize host connection
		if (e.getSource() == hostButton)
		{
			int port = new Random().nextInt(60000) + 1000;
			
			hostConnection = new Networking.host(port);
			hostConnection.addUsernameListbox(IPList, IPListModel);
			hostConnection.start();
			
			usernameTextField.enable(false);
			
			// create a new thread to broadcast the receiving messages and update the port label
			new Thread(new Runnable() 
			{
				public void run() 
				{
					while (true) {
						hostConnection.broadcastMessage(" ");
						portLabel.setText("Port: " + String.valueOf(port + hostConnection.getPortOffset()));
						
						String receivedMessage = hostConnection.receiveMessages();
						
						if (receivedMessage != null)
						{	
							messagePane.setText(messagePane.getText() + receivedMessage + "\n");
							hostConnection.broadcastMessage(receivedMessage);
						}
						
						try 
						{
							Thread.sleep(100);
						} catch (InterruptedException threadSleepError) 
						{
							threadSleepError.printStackTrace();
						}
					}
				}
			}).start();;
			
			hostOrClient = "HOST";
			
			JOptionPane.showMessageDialog(null, "Now listening at port " + port, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource() == Theme) {
			if(Theme.getSelectedIndex() == 0) {
				System.out.println("CHANGE BACKGROUND TO LIGHT");
				changeBackground(lightBG);
			}
			if(Theme.getSelectedIndex() == 1){
				System.out.println("CHANGE BACKGROUND TO DARK");
				changeBackground(darkerBG);
			}
		}
	}
	
	public void changeBackground(Color color) {
		background = color;
		
		mainForm.getContentPane().setBackground(background);
		textFieldPanel.setBackground(background);
		IPpanel.setBackground(background);
		convPanel.setBackground(background);
		enableEncryption.setBackground(background);
		controlsPanel.setBackground(background);
		
		Theme.setBackground(background);
		
		if(background.equals(darkerBG)) {
			IPlabel.setForeground(lightTextColor);
			enableEncryption.setForeground(lightTextColor);
			voiceMessageButton.setForeground(lightTextColor);
			uploadFileButton.setForeground(lightTextColor);
			playButton.setForeground(lightTextColor);
			connectButton.setForeground(lightTextColor);
			hostButton.setForeground(lightTextColor);
			
			IPList.setForeground(lightTextColor);
			IPList.setBackground(darkBG);
			
			voiceMessageButton.setBackground(darkBG);
			uploadFileButton.setBackground(darkBG);
			playButton.setBackground(darkBG);
			connectButton.setBackground(darkBG);
			hostButton.setBackground(darkBG);
			
			Theme.setBackground(darkBG);
			Theme.setForeground(lightTextColor);
			
			usernameTextField.setBackground(darkBG);
			IPTextField.setBackground(darkBG);
			portTextField.setBackground(darkBG);
			
			messagePane.setBackground(darkBG);
			messagePane.setForeground(lightTextColor);
			writeMessagePane.setBackground(darkBG);
			if (writeMessagePane.getText().equals(" Write Something")) 
			{
				writeMessagePane.setForeground(Color.GRAY);	
			}
			else {
				writeMessagePane.setForeground(lightTextColor);
			}
			
			
			
			voiceMessageButton.setIcon(micIconLight);
			IPlabel.setIcon(IPiconLight);
		}
		else {
			IPlabel.setForeground(Color.BLACK);
			enableEncryption.setForeground(Color.BLACK);
			voiceMessageButton.setForeground(Color.BLACK);
			uploadFileButton.setForeground(Color.BLACK);
			playButton.setForeground(Color.BLACK);
			connectButton.setForeground(Color.BLACK);
			hostButton.setForeground(Color.BLACK);
			IPList.setForeground(Color.BLACK);
			IPList.setBackground(Color.WHITE);
			voiceMessageButton.setBackground(Color.WHITE);
			uploadFileButton.setBackground(Color.WHITE);
			playButton.setBackground(Color.WHITE);
			connectButton.setBackground(Color.WHITE);
			hostButton.setBackground(Color.WHITE);
			Theme.setBackground(Color.WHITE);
			Theme.setForeground(Color.BLACK);
			usernameTextField.setBackground(Color.WHITE);
			IPTextField.setBackground(Color.WHITE);
			portTextField.setBackground(Color.WHITE);
			
			messagePane.setBackground(Color.WHITE);
			messagePane.setForeground(Color.BLACK);
			writeMessagePane.setBackground(Color.WHITE);
			if (writeMessagePane.getText().equals(" Write Something")) 
			{
				writeMessagePane.setForeground(Color.GRAY);	
			}
			else {
				writeMessagePane.setForeground(Color.BLACK);
			}
			
			
			
			
			
			voiceMessageButton.setIcon(micIconDark);
			IPlabel.setIcon(IPiconDark);
		}
		
	}
	
	public static void appendToPane(JTextPane pane, String txt) 
	{
		StyledDocument doc = pane.getStyledDocument();
		Style style = pane.addStyle(txt, null);
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style,Color.GRAY);

		try 
		{ 
			doc.insertString(doc.getLength(),"\n " + txt + " ", style); 
		}
        catch (BadLocationException e){}
	}
	
	// -= Private GUI Elements =-
	private JFrame mainForm = new JFrame();
	
	private JButton voiceMessageButton = new JButton("");
	private JButton uploadFileButton = new JButton("File");
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
	
	private DefaultListModel IPListModel = new DefaultListModel();
	private JList IPList = new JList(IPListModel);
	
	private JComboBox Theme = new JComboBox();
	private JRadioButton enableEncryption = new JRadioButton(" Encryption");
	
	private JLabel IPlabel = new JLabel("IP List");
	private JLabel portLabel = new JLabel("");
	
	private Map<String, String> IPs = new HashMap<>();
	private ArrayList<String> IPvalues = new ArrayList<>();
	private ArrayList<String> names = new ArrayList<>();
	
	private DefaultListModel listModel = new DefaultListModel();
	
	private Color lightBG = new Color(0xDDDDDD);
	private Color lightTextColor = new Color(0xbebebe);
	private Color darkerBG = new Color(0x252526);
	private Color darkBG = new Color(0x3e3e42);
	private Color background = lightBG;
	
	private ImageIcon micIconDark = new ImageIcon(this.getClass().getResource("/microphone-dark.png"));
	private ImageIcon micIconLight = new ImageIcon(this.getClass().getResource("/microphone-light.png"));
	private ImageIcon IPiconDark = new ImageIcon(this.getClass().getResource("/ip-address-dark.png"));
	private ImageIcon IPiconLight = new ImageIcon(this.getClass().getResource("/ip-address-light.png"));;
	
	// -= Private variables =-
	private Networking.client clientConnection;
	private Networking.host hostConnection;
	
	private String hostOrClient = "NONE";
	
	// -= Constants =-
	private final int controlPanelWidth = 160;
}
