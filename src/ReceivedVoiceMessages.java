import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReceivedVoiceMessages extends JFrame {

	private JPanel frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReceivedVoiceMessages frame = new ReceivedVoiceMessages();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public ReceivedVoiceMessages() {
		Initialize();
	}
	
	public void Initialize() {
		initFrame();
		initList();
		initElse();
	}
	
	public void initFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 200, 300);
		ImageIcon downArrow = new ImageIcon(this.getClass().getResource("/down-arrow.png"));
		this.setIconImage(downArrow.getImage());
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
		listPanel.setBounds(5, 0, 175, 261);
		listPanel.setBorder(null);
		
		frame.add(listPanel);
		listPanel.setLayout(null);
		
		scrollPaneofList.setBounds(0, 30, 170, 225);
		scrollPaneofList.setBorder(null);
		listPanel.add(scrollPaneofList);
		
		
		list.setBackground(Color.LIGHT_GRAY);
		list.setBorder(null);
		list.addMouseListener(mouseListener);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPaneofList.setViewportView(list);
	}
	
	public void initElse(){
		recivedMessagesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recivedMessagesLabel.setBackground(Color.LIGHT_GRAY);
		recivedMessagesLabel.setBounds(0, 5, 170, 20);
		recivedMessagesLabel.setFont(new Font("Arial", Font.BOLD, 13));
		listPanel.add(recivedMessagesLabel);
		
		underline.setForeground(Color.BLACK);
		underline.setBounds(0, 23, 175, 10);
		listPanel.add(underline);
	}
	
	//No idea what this does.... Found it on the internet
	MouseListener mouseListener = new MouseAdapter() {
	      public void mouseClicked(MouseEvent mouseEvent) {
	        JList theList = (JList) mouseEvent.getSource();
	        if (mouseEvent.getClickCount() == 2) {
	          int index = theList.locationToIndex(mouseEvent.getPoint());
	          if (index >= 0) {
	            Object o = theList.getModel().getElementAt(index);
	            System.out.println("Clicked on: " + o.toString());
	          }
	        }
	      }
	    };
	
	
	private JPanel listPanel = new JPanel();
	private JLabel recivedMessagesLabel = new JLabel("Received voice messages");
	private JSeparator underline = new JSeparator();
	private JScrollPane scrollPaneofList = new JScrollPane();
	private JList list = new JList();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int posX = (int) screenSize.getWidth()/2 + 400;
	private int posY = (int) screenSize.getHeight()/2 - 200;


	
}
