import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class RecordNewMessage extends JFrame implements ActionListener {

	private JPanel frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordNewMessage frame = new RecordNewMessage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public RecordNewMessage() {
		Initialize();
	}
	
	public void Initialize() {
		initFrame();
		initList();
		initButtons();
		initElse();
	}
	
	public void initFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 200, 300);
		ImageIcon recordIcon = new ImageIcon(this.getClass().getResource("/recordIcon.png"));
		this.setIconImage(recordIcon.getImage());
		this.setResizable(false);
		this.setLocation(posX, posY);
		frame = new JPanel();
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frame);
		frame.setLayout(null);
		
	}
	
	public void initList() {
		listPanel.setBackground(Color.LIGHT_GRAY);
		listPanel.setBounds(5, 0, 175, 200);
		listPanel.setBorder(null);
		frame.add(listPanel);
		listPanel.setLayout(null);
		
		scrollPaneOfList.setBounds(0, 30, 170, 170);
		scrollPaneOfList.setBorder(null);
		listPanel.add(scrollPaneOfList);
		
		
		listOfRecords.setBackground(Color.LIGHT_GRAY);
		listOfRecords.setBorder(null);
		listOfRecords.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPaneOfList.setViewportView(listOfRecords);
	}
	
	public void initButtons() {
		recordButton.setBackground(Color.WHITE);
		recordButton.setFont(new Font("Arial", Font.BOLD, 18));
		recordButton.setBounds(5, 205, 175, 45);
		recordButton.setFocusPainted(false);
		recordButton.addActionListener(this);
		frame.add(recordButton);
	}
	
	public void initElse(){
		recordedMessagesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recordedMessagesLabel.setBackground(Color.LIGHT_GRAY);
		recordedMessagesLabel.setBounds(0, 5, 170, 20);
		recordedMessagesLabel.setFont(new Font("Arial", Font.BOLD, 13));
		listPanel.add(recordedMessagesLabel);
		
		
		Underline.setForeground(Color.BLACK);
		Underline.setBounds(0, 23, 175, 10);
		listPanel.add(Underline);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == recordButton) {
			System.out.println("Recording new message");
		}
	}
	
	
	
	private JButton recordButton = new JButton("NEW RECORD");
	private JPanel listPanel = new JPanel();
	private JLabel recordedMessagesLabel = new JLabel("Your recorded messages");
	private JSeparator Underline = new JSeparator();
	private JScrollPane scrollPaneOfList = new JScrollPane();
	private JList listOfRecords = new JList();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int posX = (int) screenSize.getWidth()/2 -600;
	private int posY = (int) screenSize.getHeight()/2 -200;
}
