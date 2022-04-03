import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		mainForm.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));

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
		optionsPanel.setBounds(570, 125, 125, 195);
		playPanel.setBounds(570, 320, 125, 40);
		IPpanel.setBounds(0, 0, 125, 450);
		convPanel.setBounds(125, 0, 435, 360);
		newIPPanel.setBounds(570, 10, 125, 105);
		
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

		IPscrollPane.setBounds(10, 30, 105, 420);
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
	        	if (writeMessagePane.getText().equals(" Write Something")) {
	        		writeMessagePane.setText("");
	        		writeMessagePane.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {
				if (writeMessagePane.getText().equals("")) {
					writeMessagePane.setForeground(Color.GRAY);
					writeMessagePane.setText(" Write Something");
				}
			}
	    });
	}

	private void initLists() {
		list.setFont(new Font("Arial", Font.PLAIN, 12));
		list.setSelectedIndex(-1);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		IPscrollPane.setViewportView(list);

		list.setModel(listModel);
	}

	private void initButtons() {
		voiceMessageButton.setBounds(511, 0, 58, 40);
		uploadFileButton.setBounds(445, 45, 124, 40);
		sendButton.setBounds(445, 0, 58, 40);
		playButton.setBounds(0, 0, 125, 40);
		addIpButton.setBounds(0, 75, 125, 30);

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
		newNameField.setHorizontalAlignment(SwingConstants.CENTER);
		newNameField.setFont(new Font("Arial", Font.PLAIN, 12));
		newNameField.setForeground(Color.GRAY);
		newNameField.setBounds(0, 0, 125, 30);
		newNameField.setText("Enter new chat name");
		newIPPanel.add(newNameField);
		newNameField.setColumns(0);
		
		newIPField = new JTextField();
		newIPField.setFont(new Font("Arial", Font.PLAIN, 12));
		newIPField.setHorizontalAlignment(SwingConstants.CENTER);
		newIPField.setForeground(Color.GRAY);
		newIPField.setText("Enter new chat IP");
		newIPField.setBounds(0, 35, 125, 30);
		newIPPanel.add(newIPField);
		newIPField.setColumns(10);
		
		newNameField.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	if (newNameField.getText().equals("Enter new chat name")) {
	        		newNameField.setText("");
	        		newNameField.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {
				if (newNameField.getText().equals("")) {
					newNameField.setForeground(Color.GRAY);
					newNameField.setText("Enter new chat name");
				}
			}
	    });
		
		newIPField.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){
	        	if (newIPField.getText().equals("Enter new chat IP")) {
	        		newIPField.setText("");
	        		newIPField.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {
				if (newIPField.getText().equals("")) {
					newIPField.setForeground(Color.GRAY);
					newIPField.setText("Enter new chat IP");
				}
			}
	    });
	}
	
	private void initLabels() {
		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		optionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		optionsLabel.setBounds(0, 0, 115, 20);
		optionsPanel.add(optionsLabel);
		
		IPlabel.setFont(new Font("Arial", Font.BOLD, 21));
		IPlabel.setHorizontalAlignment(SwingConstants.CENTER);
		IPlabel.setBounds(10, 11, 105, 19);
		ImageIcon IPicon = new ImageIcon(this.getClass().getResource("/ip-address.png"));
		IPlabel.setIcon(IPicon);
		IPpanel.add(IPlabel);
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
		if (e.getSource() == sendButton) {
			if ((writeMessagePane.getText().equals("write Something") && writeMessagePane.getText().equals("")) == false) {
				appendToPane(messagePane, writeMessagePane.getText());
				writeMessagePane.setText("");
			}
		}
		if (e.getSource() == addIpButton) {
			if ((newIPField.getText().equals(("Enter new chat IP")) && newIPField.getText().equals((""))) == false) {
				if ((newNameField.getText().equals(("Enter new chat name")) && newNameField.getText().equals((""))) == false) {
					addIP(newIPField.getText(), newNameField.getText());
					newNameField.setText("");
					newIPField.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter name!", "Name field is empty", JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Please enter IP!", "IP field is empty", JOptionPane.ERROR_MESSAGE);
			}
		}	
	}
	
	public static void appendToPane(JTextPane pane, String txt) {
		StyledDocument doc = pane.getStyledDocument();
		Style style = pane.addStyle(txt, null);
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style,Color.GRAY);

		try { 
			doc.insertString(doc.getLength(),"\n " + txt + " ", style); 
		}
        catch (BadLocationException e){}
	}
	
	public void addIP(String IP, String Name) {
		if (IPs.containsKey(IP) == false) {
			IPs.put(IP, Name);
			names.add(Name);
			IPvalues.add(IP);
			listModel.addElement(Name);
		}
		else {
			JOptionPane.showMessageDialog(null, "IP already exists", "Could not add IP", JOptionPane.ERROR_MESSAGE);
		}
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
	
	private JTextField newNameField = new JTextField();
	private JTextField newIPField = new JTextField();
	
	private JList list = new JList();
	
	private JComboBox Theme = new JComboBox();
	private JRadioButton enableEncryption = new JRadioButton("Encryption");
	private JSeparator separator = new JSeparator();
	
	private JLabel optionsLabel = new JLabel("OPTIONS");
	private JLabel IPlabel = new JLabel("IP List");
	
	private Map<String, String> IPs = new HashMap<>();
	private ArrayList<String> IPvalues = new ArrayList<>();
	private ArrayList<String> names = new ArrayList<>();
	
	private DefaultListModel listModel = new DefaultListModel();
}