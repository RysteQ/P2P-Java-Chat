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

	 
	public GUI() 
	{
		initializeForm();
	}

	 
	private void initializeForm() 
	{
		mainForm = new JFrame();
		mainForm.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));

		mainForm.setTitle("Simple P2P");

		mainForm.getContentPane().setLayout(null);
		mainForm.setBounds(100, 100, 760, 500);
		mainForm.setLocationRelativeTo(null);
		mainForm.setResizable(false);
		
		mainForm.getContentPane().setBackground(Color.LIGHT_GRAY);
		mainForm.setBackground(Color.LIGHT_GRAY);

		ImageIcon P2Picon = new ImageIcon(this.getClass().getResource("/P2P.png"));
		mainForm.setIconImage(P2Picon.getImage());
		mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// initialize components
		initJPanels();
		initTextBoxes();
		initLists();
		initButtons();
		initTextFields();
		initLabels();
		initRadioButons();
		initComboBoxes();
	}

	private void initJPanels() 
	{
		textFieldPanel.setBackground(Color.LIGHT_GRAY);
		IPpanel.setBackground(Color.LIGHT_GRAY);
		convPanel.setBackground(Color.LIGHT_GRAY);
		controlsPanel.setBackground(Color.LIGHT_GRAY);
		
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

	private void initTextBoxes() 
	{
		messagePane.setFont(new Font("Arial", Font.PLAIN, 14));
		writeMessagePane.setFont(new Font("Arial", Font.PLAIN, 12));

		messagescrollPane.setViewportView(messagePane);
		textscrollPane.setViewportView(writeMessagePane);

		writeMessagePane.setForeground(Color.GRAY);
		messagePane.setEditable(false);

		writeMessagePane.setText("Write Something");

		writeMessagePane.addFocusListener(new FocusListener() 
		{
	        @Override
	        public void focusGained(FocusEvent e) 
	        {
	        	if (writeMessagePane.getText().equals("Write Something")) 
	        	{
	        		writeMessagePane.setText("");
	        		writeMessagePane.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) 
			{
				if (writeMessagePane.getText().equals("")) 
				{
					writeMessagePane.setForeground(Color.GRAY);
					writeMessagePane.setText("Write Something");
				}
			}
	    });
		
		writeMessagePane.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					if (hostOrClient.equals("HOST")) 
					{
						String sendMessage;
						
						if (usernameTextField.getText().equals("Enter username"))
							sendMessage = "Host - " + writeMessagePane.getText().trim();
						else
							sendMessage = usernameTextField.getText() + " - " + writeMessagePane.getText().trim();
						
						hostConnection.broadcastMessage(sendMessage);
						messagePane.setText(messagePane.getText() + sendMessage + "\n");
						writeMessagePane.setText(null);
					} else if (hostOrClient.equals("CLIENT")) 
					{
						if (clientConnection.isConnected())
						{
							try 
							{
								String sendMessage;
								
								if (usernameTextField.getText().equals("Enter username"))
									sendMessage = "User - " + writeMessagePane.getText().trim();
								else
									sendMessage = usernameTextField.getText() + " - " + writeMessagePane.getText().trim();
								
								clientConnection.sendMessage(sendMessage);
								writeMessagePane.setText(null);
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

	private void initLists() 
	{
		list.setFont(new Font("Arial", Font.PLAIN, 12));
		list.setSelectedIndex(-1);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		IPscrollPane.setViewportView(list);

		list.setModel(listModel);
	}

	private void initButtons() 
	{
		voiceMessageButton.setBounds(0, 400, 75, 40);
		uploadFileButton.setBounds(85, 400, 75, 40);
		playButton.setBounds(0, 355, controlPanelWidth, 40);
		connectButton.setBounds(0, 75, controlPanelWidth, 30);
		hostButton.setBounds(0, 110, controlPanelWidth, 30);

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
		
		usernameTextField.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	if (usernameTextField.getText().equals("Enter username")) {
	        		usernameTextField.setText("");
	        		usernameTextField.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {
				if (usernameTextField.getText().equals("")) {
					usernameTextField.setForeground(Color.GRAY);
					usernameTextField.setText("Enter username");
				}
			}
	    });
		
		IPTextField.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	if (IPTextField.getText().equals("IP to connect to")) {
	        		IPTextField.setText("");
	        		IPTextField.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {
				if (IPTextField.getText().equals("")) {
					IPTextField.setForeground(Color.GRAY);
					IPTextField.setText("Enter new chat IP");
				}
			}
	    });
		
		portTextField.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	if (portTextField.getText().equals("Port")) {
	        		portTextField.setText("");
	        		portTextField.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {
				if (portTextField.getText().equals("")) {
					portTextField.setForeground(Color.GRAY);
					portTextField.setText("Port");
				}
			}
	    });
	}
	
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
		
	private void initRadioButons() 
	{
		enableEncryption.setHorizontalAlignment(SwingConstants.CENTER);
		enableEncryption.setFont(new Font("Arial", Font.PLAIN, 18));
		enableEncryption.setBackground(Color.LIGHT_GRAY);
		enableEncryption.setBounds(15, 190, 125, 23);
		enableEncryption.setFocusPainted(false);
		enableEncryption.setSelected(true);
		controlsPanel.add(enableEncryption);
	}

	private void initComboBoxes() 
	{
		Theme.setModel(new DefaultComboBoxModel(new String[] {"Light Theme", "Dark Theme"}));
		Theme.setFont(new Font("Arial", Font.PLAIN, 18));
		Theme.setBounds(0, 145, controlPanelWidth, 40);
		Theme.setSelectedIndex(0);
		controlsPanel.add(Theme);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if (e.getSource() == connectButton) 
		{
			if (hostOrClient.equals("HOST") == false) 
			{
				if (Miscelenious.validIP(IPTextField.getText())) 
				{
					try 
					{
						if (Miscelenious.convertStringToInt(portTextField.getText()) >= 1000) 
						{
							clientConnection = new Networking.client(IPTextField.getText(), Miscelenious.convertStringToInt(portTextField.getText()));
							
							if (clientConnection.isConnected()) 
							{
								clientConnection.setMessageTextbox(messagePane);
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
				JOptionPane.showMessageDialog(null, "You cannot connect to someone due to you being a host", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} 
		
		if (e.getSource() == hostButton) 
		{
			int port = new Random().nextInt(60000) + 1000;
			
			hostConnection = new Networking.host(port);
			hostConnection.start();
			
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
							Thread.sleep(10);
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
	
	public void addIP(String IP, String Name) 
	{
		if (IPs.containsKey(IP) == false) 
		{
			IPs.put(IP, Name);
			names.add(Name);
			IPvalues.add(IP);
			IPListModel.addElement("fs");
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "IP already exists", "Could not add IP", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// -= Private GUI Elements =-
	private JFrame mainForm = new JFrame();
	
	private JButton voiceMessageButton = new JButton("");
	private JButton uploadFileButton = new JButton("Upload file");
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
	private JList list = new JList(IPListModel);
	
	private JComboBox Theme = new JComboBox();
	private JRadioButton enableEncryption = new JRadioButton("Encryption");
	
	private JLabel IPlabel = new JLabel("IP List");
	private JLabel portLabel = new JLabel("");
	
	private Map<String, String> IPs = new HashMap<>();
	private ArrayList<String> IPvalues = new ArrayList<>();
	private ArrayList<String> names = new ArrayList<>();
	
	private DefaultListModel listModel = new DefaultListModel();
	
	// -= Private variables =-
	Networking.client clientConnection;
	Networking.host hostConnection;
	
	private String hostOrClient = "NONE";
	
	// -= Constants =-
	private final int controlPanelWidth = 160;
}