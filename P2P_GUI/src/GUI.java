import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.w3c.dom.events.MouseEvent;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JMenu;
import java.awt.Panel;
import java.awt.CardLayout;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;

public class GUI implements ActionListener{

	private JFrame SimpleP2PApp;				
	private JButton sendButton;								//Initialize the components that will be used by the event handlers
	private JButton voiceMessageButton;
	private JButton uploadFileButton;
	private JButton playButton;
	private JButton addIpButton;
	private JTextField newIPTextfield;
	private JTextPane messagePane;
	private JTextPane writeMessagePane;

	
	// Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.SimpleP2PApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	//Initialize the GUI.
	 
	public GUI() {
		initialize();
		
	}

	
	//Initialize the contents of the frame.
	 
	private void initialize() {
		
		SimpleP2PApp = new JFrame();									          //The general Frame
		SimpleP2PApp.setResizable(false);
		SimpleP2PApp.setLocationRelativeTo(null);
		SimpleP2PApp.getContentPane().setBackground(Color.LIGHT_GRAY);
		SimpleP2PApp.setBackground(Color.LIGHT_GRAY);
		SimpleP2PApp.setTitle("Simple P2P");
		SimpleP2PApp.setBounds(100, 100, 720, 500);
		ImageIcon P2Picon = new ImageIcon(this.getClass().getResource("/P2P.png"));
		SimpleP2PApp.setIconImage(P2Picon.getImage());
		SimpleP2PApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SimpleP2PApp.getContentPane().setLayout(null);
		
		
		
		//--------------------------------------- All the panels ---------------------------------------
		
		
		JPanel textFieldPanel = new JPanel();								//The panel that has the textfield that the user writes + the send buttons(text and voice) + upload file
		textFieldPanel.setBackground(Color.LIGHT_GRAY);
		textFieldPanel.setBorder(null);
		textFieldPanel.setBounds(125, 365, 569, 85);
		SimpleP2PApp.getContentPane().add(textFieldPanel);
		textFieldPanel.setLayout(null);
		
		JPanel playPanel = new JPanel();									//Play Games Panel
		playPanel.setBackground(Color.LIGHT_GRAY);
		playPanel.setBorder(null);
		playPanel.setBounds(570, 320, 125, 40);
		SimpleP2PApp.getContentPane().add(playPanel);
		playPanel.setLayout(null);
		
		JPanel IPpanel = new JPanel();										//Existing IPs panel
		IPpanel.setBorder(null);
		IPpanel.setBackground(Color.LIGHT_GRAY);
		IPpanel.setForeground(Color.BLACK);
		IPpanel.setBounds(0, 0, 125, 450);
		SimpleP2PApp.getContentPane().add(IPpanel);
		IPpanel.setLayout(null);
		
		
		
		JPanel convPanel = new JPanel();									//The panel that the conversation is shown
		convPanel.setBackground(Color.LIGHT_GRAY);
		convPanel.setBorder(null);
		convPanel.setBounds(125, 0, 435, 360);
		SimpleP2PApp.getContentPane().add(convPanel);
		convPanel.setLayout(null);
		
		JPanel newIPPanel = new JPanel();									  //Add new IP panel
		newIPPanel.setBorder(null);
		newIPPanel.setBackground(Color.LIGHT_GRAY);
		newIPPanel.setBounds(570, 10, 125, 80);
		SimpleP2PApp.getContentPane().add(newIPPanel);
		newIPPanel.setLayout(null);
		
		JPanel optionsPanel = new JPanel();									  //Options panel
		optionsPanel.setBorder(null);
		optionsPanel.setBackground(Color.LIGHT_GRAY);
		optionsPanel.setBounds(570, 90, 125, 230);
		SimpleP2PApp.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		
		
		
		//--------------------------------------- All the Scroll Panes ---------------------------------------
		
		
		JScrollPane IPscrollPane = new JScrollPane();
		IPscrollPane.setBounds(10, 10, 105, 440);
		IPpanel.add(IPscrollPane);
		
		JScrollPane messagescrollPane = new JScrollPane();
		messagescrollPane.setBounds(0, 10, 435, 350);
		convPanel.add(messagescrollPane);
		
		JScrollPane textscrollPane = new JScrollPane();
		textscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textscrollPane.setBounds(0, 0, 435, 85);
		textFieldPanel.add(textscrollPane);
		
		
		
		//--------------------------------------- All the Text Panes ---------------------------------------
		
		
		messagePane = new JTextPane();
		messagescrollPane.setViewportView(messagePane);
		messagePane.setFont(new Font("Arial", Font.PLAIN, 14));
		messagePane.setText("Conversation-1");
		messagePane.setEditable(false);
		
		writeMessagePane = new JTextPane();
		writeMessagePane.setForeground(Color.GRAY);
		writeMessagePane.setFont(new Font("Arial", Font.PLAIN, 12));
		writeMessagePane.setText(" Write Something");
		textscrollPane.setViewportView(writeMessagePane);
		writeMessagePane.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){									//When you click on the text field the text disappears
	        	if(writeMessagePane.getText().equals(" Write Something")) {
	        		writeMessagePane.setText("");
	        		writeMessagePane.setForeground(Color.BLACK);
	        	}
	        }

			@Override
			public void focusLost(FocusEvent e) {									//When you click away of the text field the text reappears
				if(writeMessagePane.getText().equals("")) {
					writeMessagePane.setForeground(Color.GRAY);
					writeMessagePane.setText(" Write Something");
				}
			}
	    });
		
		
		
		//--------------------------------------- All the Lists ---------------------------------------
				
				
		JList list = new JList();
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
		list.setSelectedIndex(0);
		list.setFont(new Font("Arial", Font.PLAIN, 12));
		
		
		
		
		//--------------------------------------- All the buttons ---------------------------------------
		
		
		voiceMessageButton = new JButton("");
		voiceMessageButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		voiceMessageButton.setFocusPainted(false);
		voiceMessageButton.setBounds(511, 0, 58, 40);
		ImageIcon micIcon = new ImageIcon(this.getClass().getResource("/microphone.png"));
		voiceMessageButton.setIcon(micIcon);
		voiceMessageButton.addActionListener(this);
		textFieldPanel.add(voiceMessageButton);
		
		uploadFileButton = new JButton("UPLOAD FILE...");
		uploadFileButton.setFont(new Font("Arial", Font.BOLD, 12));
		uploadFileButton.setFocusPainted(false);
		uploadFileButton.setBounds(445, 45, 124, 40);
		uploadFileButton.addActionListener(this);
		textFieldPanel.add(uploadFileButton);
		
		sendButton = new JButton("");
		sendButton.setHorizontalAlignment(SwingConstants.TRAILING);
		sendButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sendButton.setFocusPainted(false);
		sendButton.setBounds(445, 0, 58, 40);
		ImageIcon sendIcon = new ImageIcon(this.getClass().getResource("/send.png"));
		sendButton.setIcon(sendIcon);
		sendButton.addActionListener(this);
		textFieldPanel.add(sendButton);
		
		playButton = new JButton("PLAY");									   //Play (tic-tac-toe) button 
		playButton.setBounds(0, 0, 125, 40);
		playPanel.add(playButton);
		playButton.setFont(new Font("Arial", Font.BOLD, 25));
		playButton.addActionListener(this);
		playButton.setFocusPainted(false);
		
		addIpButton = new JButton("ADD IP");
		addIpButton.setFont(new Font("Arial", Font.BOLD, 25));
		addIpButton.setFocusPainted(false);
		addIpButton.setBounds(0, 35, 125, 30);
		addIpButton.addActionListener(this);
		newIPPanel.add(addIpButton);
		
		
		
		//--------------------------------------- All the Text Fields ---------------------------------------
		
		
		newIPTextfield = new JTextField();										 //New IP textfield
		newIPTextfield.setBounds(0, 0, 125, 30);
		newIPPanel.add(newIPTextfield);
		newIPTextfield.setForeground(Color.GRAY);
		newIPTextfield.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newIPTextfield.setHorizontalAlignment(SwingConstants.CENTER);
		newIPTextfield.setText("Enter a new IP");
		newIPTextfield.setColumns(0);
		newIPTextfield.addFocusListener(new FocusListener(){
	        @Override
	        public void focusGained(FocusEvent e){									//When you click on the text field the text disappears
	        	if(newIPTextfield.getText().equals("Enter a new IP")) {
	        		newIPTextfield.setText("");
	        	}
	        	newIPTextfield.setForeground(Color.BLACK);
	        }

			@Override
			public void focusLost(FocusEvent e) {									//When you click away of the text field the text reappears
				if(newIPTextfield.getText().equals("")) {
					newIPTextfield.setForeground(Color.GRAY);
					newIPTextfield.setText("Enter a new IP");
				}
			}
	    });
		
		
		
		//--------------------------------------- All the Labels ---------------------------------------
		
		
		JLabel optionsLabel = new JLabel("OPTIONS");								//Options label
		optionsLabel.setBounds(0, 0, 115, 20);
		optionsPanel.add(optionsLabel);
		optionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		//--------------------------------------- All the Seperators ---------------------------------------
		
		
		JSeparator separator = new JSeparator();											//The line under the options
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(0, 20, 124, 2);
		optionsPanel.add(separator);
		
		
		
		//--------------------------------------- All the Radio Buttons ---------------------------------------
		
		
		JRadioButton enableEncryption = new JRadioButton("Encryption");									//Encryption enable-disable option
		enableEncryption.setSelected(true);
		enableEncryption.setHorizontalAlignment(SwingConstants.CENTER);
		enableEncryption.setFont(new Font("Arial", Font.PLAIN, 18));
		enableEncryption.setFocusPainted(false);
		enableEncryption.setBackground(Color.LIGHT_GRAY);
		enableEncryption.setBounds(0, 30, 125, 23);
		optionsPanel.add(enableEncryption);
		
		
		
		//--------------------------------------- All the Combo boxes ---------------------------------------
		
		
		JComboBox Theme = new JComboBox();																//Theme picker
		Theme.setModel(new DefaultComboBoxModel(new String[] {"Light Theme", "Dark Theme"}));
		Theme.setSelectedIndex(0);
		Theme.setBounds(6, 60, 115, 20);
		optionsPanel.add(Theme);
		
		
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton) {																			//Making the Send Button functional
			if((!writeMessagePane.getText().equals(" Wwrite Something")) && (!writeMessagePane.getText().equals(""))) {
				appendToPane(messagePane, writeMessagePane.getText());
				writeMessagePane.setText("");
			}
		}
	
		
	}
	
	public static void appendToPane(JTextPane pane, String txt) {						//Function to make the messages of the user a different color
		StyledDocument doc = pane.getStyledDocument();
		Style style = pane.addStyle(txt, null);
		StyleConstants.setForeground(style, Color.WHITE);
		StyleConstants.setBackground(style,Color.GRAY);
		try { doc.insertString(doc.getLength(),"\n " + txt + " ", style); }
        catch (BadLocationException e){}
		
		
		
	}
}
