import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;


public class GUI implements ActionListener{	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.mainForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 
	public GUI() {
		initializeForm();
	}

	 
	private void initializeForm() {
		mainForm = new JFrame();

		mainForm.setTitle("Simple P2P");

		mainForm.getContentPane().setLayout(null);
		mainForm.setBounds(100, 100, 720, 500);
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
		initSeparators();
		initRadioButons();
		initComboBoxes();
	}

	private void initJPanels() {
		textFieldPanel.setBackground(Color.LIGHT_GRAY);
		playPanel.setBackground(Color.LIGHT_GRAY);
		IPpanel.setBackground(Color.LIGHT_GRAY);
		convPanel.setBackground(Color.LIGHT_GRAY);
		newIPPanel.setBackground(Color.LIGHT_GRAY);
		optionsPanel.setBackground(Color.LIGHT_GRAY);
		
		IPpanel.setForeground(Color.BLACK);
		
		textFieldPanel.setBounds(125, 365, 569, 85);
		optionsPanel.setBounds(570, 90, 125, 230);
		playPanel.setBounds(570, 320, 125, 40);
		IPpanel.setBounds(0, 0, 125, 450);
		convPanel.setBounds(125, 0, 435, 360);
		newIPPanel.setBounds(570, 10, 125, 80);
		
		textFieldPanel.setBorder(null);
		textFieldPanel.setLayout(null);
		playPanel.setBorder(null);
		playPanel.setLayout(null);
		IPpanel.setBorder(null);
		IPpanel.setLayout(null);
		convPanel.setBorder(null);
		convPanel.setLayout(null);
		newIPPanel.setBorder(null);
		newIPPanel.setLayout(null);
		optionsPanel.setBorder(null);
		optionsPanel.setLayout(null);
		
		mainForm.getContentPane().add(textFieldPanel);
		mainForm.getContentPane().add(playPanel);
		mainForm.getContentPane().add(IPpanel);
		mainForm.getContentPane().add(convPanel);
		mainForm.getContentPane().add(newIPPanel);
		mainForm.getContentPane().add(optionsPanel);

		IPscrollPane.setBounds(10, 10, 105, 440);
		IPpanel.add(IPscrollPane);

		messagescrollPane.setBounds(0, 10, 435, 350);
		convPanel.add(messagescrollPane);

		textscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textscrollPane.setBounds(0, 0, 435, 85);
		textFieldPanel.add(textscrollPane);
	}

	private void initTextBoxes() {
		messagePane.setFont(new Font("Arial", Font.PLAIN, 14));
		writeMessagePane.setFont(new Font("Arial", Font.PLAIN, 12));

		messagescrollPane.setViewportView(messagePane);
		textscrollPane.setViewportView(writeMessagePane);

		writeMessagePane.setForeground(Color.GRAY);
		messagePane.setEditable(false);

		writeMessagePane.setText(" Write Something");
		messagePane.setText("Conversation-1");

		writeMessagePane.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	if(writeMessagePane.getText().equals(" Write Something")) {
	        		writeMessagePane.setText("");
	        		writeMessagePane.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {
				if(writeMessagePane.getText().equals("")) {
					writeMessagePane.setForeground(Color.GRAY);
					writeMessagePane.setText(" Write Something");
				}
			}
	    });
	}

	private void initLists() {
		list.setFont(new Font("Arial", Font.PLAIN, 12));
		list.setSelectedIndex(0);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		IPscrollPane.setViewportView(list);

		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.4", "192.168.1.5", "192.168.1.6", "192.168.1.7", "192.168.1.8", "192.168.1.9", "192.168.1.10", "192.168.1.11", "192.168.1.12", "192.168.1.13", "192.168.1.14", "192.168.1.15", "192.168.1.16", "192.168.1.17", "192.168.1.18", "192.168.1.19", "192.168.1.20", "192.168.1.21", "192.168.1.22", "192.168.1.23", "192.168.1.24", "192.168.1.25", "192.168.1.26", "192.168.1.27", "192.168.1.28", "192.168.1.29", "192.168.1.30", "192.168.1.31", "192.168.1.32", "192.168.1.33", "192.168.1.34", "192.168.1.35", "192.168.1.36", "192.168.1.37", "192.168.1.38", "192.168.1.39", "192.168.1.40", "192.168.1.41", "192.168.1.42", "192.168.1.43", "192.168.1.44", "192.168.1.45", "192.168.1.46", "192.168.1.47", "192.168.1.48", "192.168.1.49", "192.168.1.50"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
	}

	private void initButtons() {
		voiceMessageButton.setBounds(511, 0, 58, 40);
		uploadFileButton.setBounds(445, 45, 124, 40);
		sendButton.setBounds(445, 0, 58, 40);
		playButton.setBounds(0, 0, 125, 40);
		addIpButton.setBounds(0, 35, 125, 30);

		voiceMessageButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		uploadFileButton.setFont(new Font("Arial", Font.BOLD, 12));
		sendButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		playButton.setFont(new Font("Arial", Font.BOLD, 25));
		addIpButton.setFont(new Font("Arial", Font.BOLD, 25));

		ImageIcon micIcon = new ImageIcon(this.getClass().getResource("/microphone.png"));
		ImageIcon sendIcon = new ImageIcon(this.getClass().getResource("/send.png"));

		sendButton.setHorizontalAlignment(SwingConstants.TRAILING);
		sendButton.setFocusPainted(false);
		sendButton.setIcon(sendIcon);

		textFieldPanel.add(voiceMessageButton);
		textFieldPanel.add(uploadFileButton);
		textFieldPanel.add(sendButton);
		playPanel.add(playButton);
		newIPPanel.add(addIpButton);

		voiceMessageButton.addActionListener(this);
		uploadFileButton.addActionListener(this);
		sendButton.addActionListener(this);
		playButton.setFocusPainted(false);
		addIpButton.addActionListener(this);

		playButton.addActionListener(this);
		addIpButton.setFocusPainted(false);
		voiceMessageButton.setIcon(micIcon);
		voiceMessageButton.setFocusPainted(false);
		uploadFileButton.setFocusPainted(false);
	}

	private void initTextFields() {
		newIPTextfield.setHorizontalAlignment(SwingConstants.CENTER);
		newIPTextfield.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newIPTextfield.setForeground(Color.GRAY);
		newIPTextfield.setBounds(0, 0, 125, 30);
		newIPTextfield.setText("Enter a new IP");
		newIPPanel.add(newIPTextfield);
		newIPTextfield.setColumns(0);
		
		newIPTextfield.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	if(newIPTextfield.getText().equals("Enter a new IP")) {
	        		newIPTextfield.setText("");
	        	}
	        	newIPTextfield.setForeground(Color.BLACK);
	        }

			@Override
			public void focusLost(FocusEvent e) {
				if(newIPTextfield.getText().equals("")) {
					newIPTextfield.setForeground(Color.GRAY);
					newIPTextfield.setText("Enter a new IP");
				}
			}
	    });
	}
	
	private void initLabels() {
		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		optionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		optionsLabel.setBounds(0, 0, 115, 20);
		optionsPanel.add(optionsLabel);
	}
		
	private void initSeparators() {	
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(0, 20, 124, 2);
		optionsPanel.add(separator);
	}
		
	private void initRadioButons() {
		enableEncryption.setHorizontalAlignment(SwingConstants.CENTER);
		enableEncryption.setFont(new Font("Arial", Font.PLAIN, 18));
		enableEncryption.setBackground(Color.LIGHT_GRAY);
		enableEncryption.setBounds(0, 30, 125, 23);
		enableEncryption.setFocusPainted(false);
		enableEncryption.setSelected(true);
		optionsPanel.add(enableEncryption);
	}

	private void initComboBoxes() {
		Theme.setModel(new DefaultComboBoxModel(new String[] {"Light Theme", "Dark Theme"}));
		Theme.setBounds(6, 60, 115, 20);
		Theme.setSelectedIndex(0);
		optionsPanel.add(Theme);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton) {
			if((!writeMessagePane.getText().equals(" Wwrite Something")) && (!writeMessagePane.getText().equals(""))) {
				appendToPane(messagePane, writeMessagePane.getText());
				writeMessagePane.setText("");
			}
		}
	
		
	}
	
	public static void appendToPane(JTextPane pane, String txt) {
		StyledDocument doc = pane.getStyledDocument();
		Style style = pane.addStyle(txt, null);
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style,Color.GRAY);
		try { doc.insertString(doc.getLength(),"\n " + txt + " ", style); }
        catch (BadLocationException e){}
	}
	
	
	// -= Private GUI Elements =-
	private JFrame mainForm = new JFrame();
	
	private JButton voiceMessageButton = new JButton("");
	private JButton uploadFileButton = new JButton("UPLOAD FILE...");
	private JButton sendButton = new JButton("");
	private JButton playButton = new JButton("PLAY");
	private JButton addIpButton = new JButton("ADD IP");
	
	private JPanel textFieldPanel = new JPanel();
	private JPanel playPanel = new JPanel();
	private JPanel IPpanel = new JPanel();
	private JPanel convPanel = new JPanel();
	private JPanel newIPPanel = new JPanel();
	private JPanel optionsPanel = new JPanel();
	
	private JScrollPane IPscrollPane = new JScrollPane();
	private JScrollPane messagescrollPane = new JScrollPane();
	private JScrollPane textscrollPane = new JScrollPane();
	
	private JTextPane messagePane = new JTextPane();
	private JTextPane writeMessagePane = new JTextPane();
	
	private JList list = new JList();
	private JTextField newIPTextfield = new JTextField();
	private JComboBox Theme = new JComboBox();
	private JRadioButton enableEncryption = new JRadioButton("Encryption");
	private JSeparator separator = new JSeparator();
	private JLabel optionsLabel = new JLabel("OPTIONS");
}
