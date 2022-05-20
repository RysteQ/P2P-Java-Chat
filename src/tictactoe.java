import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;

public class tictactoe implements ActionListener{

	public static void main(String[] args) {		//Launch the game
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tictactoe window = new tictactoe();
					window.frmTicTacToe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public tictactoe() {		// Create the application.						
		initialize();
	}
 
	public void initialize() {		//Initialize the contents of the frame.
		
		frmTicTacToe = new JFrame();
		frmTicTacToe.setResizable(false);
		frmTicTacToe.setTitle("TIC-TAC-TOE");
		frmTicTacToe.setIconImage(ticTacToeIcon.getImage());
		frmTicTacToe.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmTicTacToe.setBounds(100, 100, 450, 300);
		frmTicTacToe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTicTacToe.getContentPane().setLayout(null);
		
		for(int i = 0; i < table.length; i++) {
			for(int j = 0; j < table.length; j++) {
				table[i][j] = "";
			}
		}
		
		initPanels();
		initSeperators();
		initPlaces();
		initLabels();
		initScoreboard();
	}
	
	private void initPanels() {

		playPanel.setBackground(Color.LIGHT_GRAY);
		playPanel.setBounds(0, 0, 300, 260);
		frmTicTacToe.getContentPane().add(playPanel);
		playPanel.setLayout(null);
		

		infoPanel.setBackground(Color.LIGHT_GRAY);
		infoPanel.setBounds(306, 0, 129, 260);
		frmTicTacToe.getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
	}
	
	private void initPlaces() {
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		topLeft.setBackground(Color.LIGHT_GRAY);
		topLeft.setBounds(1, 1, 98, 77);
		topLeft.setBorder(emptyBorder);
		topLeft.setContentAreaFilled(false);
		topLeft.addActionListener(this);
		topLeft.setFocusPainted(false);
		playPanel.add(topLeft);
		
		
		topMid.setBackground(Color.LIGHT_GRAY);
		topMid.setBounds(101, 1, 98, 77);
		topMid.setBorder(emptyBorder);
		topMid.setContentAreaFilled(false);
		topMid.addActionListener(this);
		topMid.setFocusPainted(false);
		playPanel.add(topMid);
		
		
		topRight.setBackground(Color.LIGHT_GRAY);
		topRight.setBounds(201, 1, 98, 77);
		topRight.setBorder(emptyBorder);
		topRight.setContentAreaFilled(false);
		topRight.addActionListener(this);
		topRight.setFocusPainted(false);
		playPanel.add(topRight);
		
		
		midLeft.setBackground(Color.LIGHT_GRAY);
		midLeft.setBounds(1, 82, 98, 77);
		midLeft.setBorder(emptyBorder);
		midLeft.setContentAreaFilled(false);
		midLeft.addActionListener(this);
		midLeft.setFocusPainted(false);
		playPanel.add(midLeft);
		
		
		midMid.setBackground(Color.LIGHT_GRAY);
		midMid.setBounds(101, 81, 98, 77);
		midMid.setBorder(emptyBorder);
		midMid.setContentAreaFilled(false);
		midMid.addActionListener(this);
		midMid.setFocusPainted(false);
		playPanel.add(midMid);
		
		
		midRight.setBackground(Color.LIGHT_GRAY);
		midRight.setBounds(201, 81, 98, 77);
		midRight.setBorder(emptyBorder);
		midRight.setContentAreaFilled(false);
		midRight.addActionListener(this);
		midRight.setFocusPainted(false);
		playPanel.add(midRight);
		
		
		botLeft.setBackground(Color.LIGHT_GRAY);
		botLeft.setBounds(1, 161, 98, 87);
		botLeft.setBorder(emptyBorder);
		botLeft.setContentAreaFilled(false);
		botLeft.addActionListener(this);
		botLeft.setFocusPainted(false);
		playPanel.add(botLeft);
		
		
		botMid.setBackground(Color.LIGHT_GRAY);
		botMid.setBounds(101, 161, 98, 87);
		botMid.setBorder(emptyBorder);
		botMid.setContentAreaFilled(false);
		botMid.addActionListener(this);
		botMid.setFocusPainted(false);
		playPanel.add(botMid);
		
		
		botRight.setBackground(Color.LIGHT_GRAY);
		botRight.setBounds(201, 161, 98, 87);
		botRight.setBorder(emptyBorder);
		botRight.setContentAreaFilled(false);
		botRight.addActionListener(this);
		botRight.setFocusPainted(false);
		playPanel.add(botRight);
		

		start.setFont(new Font("Arial", Font.BOLD, 15));   // NOT a place to click BUT its the button to start the game!!!
		start.setBounds(10, 230, 110, 25);
		start.setContentAreaFilled(false);
		start.addActionListener(this);
		start.setFocusPainted(false);
		infoPanel.add(start);
	}
	
	private void initSeperators() {
		separator_0.setOrientation(SwingConstants.VERTICAL);
		separator_0.setForeground(Color.BLACK);
		separator_0.setBounds(100, 10, 1, 240);
		playPanel.add(separator_0);
		
		
		separator_1.setForeground(Color.BLACK);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(200, 10, 1, 240);
		playPanel.add(separator_1);
		
		
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(10, 80, 280, 1);
		playPanel.add(separator_2);
		

		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(10, 160, 280, 1);
		playPanel.add(separator_3);
		
		separator_4.setForeground(Color.BLACK);
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBounds(303, 0, 1, 270);
		frmTicTacToe.getContentPane().add(separator_4);
		
		
		ticTacToeUnderline.setForeground(Color.BLACK);	//its NOT used as a seperator
		ticTacToeUnderline.setBounds(20, 25, 95, 2);
		infoPanel.add(ticTacToeUnderline);
	}
	
	private void initLabels() {
		ticTacToeLabel.setFont(new Font("Arial", Font.BOLD, 14));
		ticTacToeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ticTacToeLabel.setBounds(10, 11, 110, 15);
		infoPanel.add(ticTacToeLabel);
	}
	
	private void initScoreboard() {
		score.setBackground(Color.LIGHT_GRAY);
		score.setFont(new Font("Arial", Font.BOLD, 16));
		score.setHorizontalAlignment(SwingConstants.CENTER);
		score.setBounds(20, 49, 99, 14);
		infoPanel.add(score);
		
		
		wins.setFont(new Font("Arial", Font.BOLD, 14));
		wins.setBounds(10, 75, 46, 14);
		infoPanel.add(wins);
		
		
		draws.setFont(new Font("Arial", Font.BOLD, 14));
		draws.setBounds(10, 100, 71, 14);
		infoPanel.add(draws);
		
		
		defeats.setFont(new Font("Arial", Font.BOLD, 14));
		defeats.setBounds(10, 125, 71, 14);
		infoPanel.add(defeats);
		
		
		winsShow.setBackground(Color.LIGHT_GRAY);
		winsShow.setForeground(new Color(46, 139, 87));
		winsShow.setHorizontalAlignment(SwingConstants.TRAILING);
		winsShow.setFont(new Font("Arial", Font.BOLD, 17));
		winsShow.setBounds(66, 74, 38, 15);
		infoPanel.add(winsShow);
		
		
		drawsShow.setForeground(Color.DARK_GRAY);
		drawsShow.setHorizontalAlignment(SwingConstants.TRAILING);
		drawsShow.setBackground(Color.LIGHT_GRAY);
		drawsShow.setFont(new Font("Arial", Font.BOLD, 17));
		drawsShow.setBounds(66, 101, 38, 15);
		infoPanel.add(drawsShow);
		
		defeatsShow.setForeground(new Color(165, 42, 42));
		defeatsShow.setHorizontalAlignment(SwingConstants.CENTER);
		defeatsShow.setBackground(Color.LIGHT_GRAY);
		defeatsShow.setFont(new Font("Arial", Font.BOLD, 17));
		defeatsShow.setBounds(76, 125, 46, 15);
		infoPanel.add(defeatsShow);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == topLeft) {
			turn+=1;
			setIcon(topLeft, 0, 0);
			check();
		}
		if(e.getSource() == topMid) {
			turn+=1;
			setIcon(topMid, 0, 1);
			check();
		}
		if(e.getSource() == topRight) {
			turn+=1;
			setIcon(topRight, 0, 2);
			check();
		}
		if(e.getSource() == midLeft) {
			turn+=1;
			setIcon(midLeft, 1, 0);
			check();
		}
		if(e.getSource() == midMid) {
			turn+=1;
			setIcon(midMid, 1, 1);
			check();
		}
		if(e.getSource() == midRight) {
			turn+=1;
			setIcon(midRight, 1, 2);
			check();
		}
		if(e.getSource() == botLeft) {
			turn+=1;
			setIcon(botLeft, 2, 0);
			check();
		}
		if(e.getSource() == botMid) {
			turn+=1;
			setIcon(botMid, 2, 1);
			check();
		}
		if(e.getSource() == botRight) {
			turn+=1;
			setIcon(botRight, 2, 2);
			check();
		}
		if(e.getSource() == start) {
			reset();
		}
		
	}
	
	
	private void setIcon(JButton button, int x, int y) {
		button.removeActionListener(this);
		if(turn%2 == 0) {
			button.setIcon(xIcon);
			table[x][y] = "X";
		}
		else if(turn%2 == 1){
			button.setIcon(circleIcon);
			table[x][y] = "O";
		}
		System.out.println(turn);
	}
	
	public void reset() {
		
		topLeft.setIcon(null);
		topMid.setIcon(null);
		topRight.setIcon(null);
		midLeft.setIcon(null);
		midMid.setIcon(null);
		midRight.setIcon(null);
		botLeft.setIcon(null);
		botMid.setIcon(null);
		botRight.setIcon(null);
		
		topLeft.addActionListener(this);
		topMid.addActionListener(this);
		topRight.addActionListener(this);
		midLeft.addActionListener(this);
		midMid.addActionListener(this);
		midRight.addActionListener(this);
		botLeft.addActionListener(this);
		botMid.addActionListener(this);
		botRight.addActionListener(this);
		
		turn = 0;
		System.out.println("RESET");											//DELETE WHEN FINISHED
		System.out.println("--------------------------------");
	}
	
	private void check() {
		if((table[0][0].equals(table[0][1])) && (table[0][0].equals(table[0][2])) && !table[0][0].equals("")) {
			if(table[0][0].equals("O")) {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			reset();
		}
		if((table[1][0].equals(table[1][1])) && (table[1][0].equals(table[1][2])) && !table[1][0].equals("")) {
			if(table[1][0].equals("O")) {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			reset();
		}
		if((table[2][0].equals(table[2][1])) && (table[2][0].equals(table[2][2])) && !table[2][0].equals("")) {
			if(table[2][0].equals("O")) {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			reset();
		}
		if((table[0][0].equals(table[1][0])) && (table[0][0].equals(table[2][0])) && !table[0][0].equals("")) {
			if(table[0][0].equals("O")) {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if((table[0][1].equals(table[1][1])) && (table[0][1].equals(table[2][1])) && !table[0][1].equals("")) {
			if(table[0][1].equals("O")) {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			reset();
		}
		if((table[0][2].equals(table[1][2])) && (table[0][2].equals(table[2][2])) && !table[0][2].equals("")) {
			if(table[0][2].equals("O")) {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			reset();
		}
		if((table[0][0].equals(table[1][1])) && (table[0][0].equals(table[2][2])) && !table[0][0].equals("")) {
			if(table[0][0].equals("O")) {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			reset();
		}
		if((table[0][2].equals(table[1][1])) && (table[0][2].equals(table[2][0])) && !table[0][2].equals("")) {
			if(table[0][2].equals("O")) {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			reset();
		}
	}
	
	
	private JPanel playPanel = new JPanel();
	private JPanel infoPanel = new JPanel();
	
	private JSeparator separator_0 = new JSeparator();
	private JSeparator separator_1 = new JSeparator();
	private JSeparator separator_2 = new JSeparator();
	private JSeparator separator_3 = new JSeparator();
	private JSeparator separator_4 = new JSeparator();
	private JSeparator ticTacToeUnderline = new JSeparator();
	
	private JButton topLeft = new JButton("");
	private JButton topMid = new JButton("");
	private JButton topRight = new JButton("");
	private JButton midLeft = new JButton("");
	private JButton midMid = new JButton("");
	private JButton midRight = new JButton("");
	private JButton botLeft = new JButton("");
	private JButton botMid = new JButton("");
	private JButton botRight = new JButton("");
	private JButton start = new JButton("START");
	
	private JLabel ticTacToeLabel = new JLabel("TIC-TAC-TOE");
	private JLabel score = new JLabel("SCORE");
	private JLabel wins = new JLabel("WINS:");
	private JLabel draws = new JLabel("DRAWS:");
	private JLabel defeats = new JLabel("DEFEATS:");
	private JLabel winsShow = new JLabel("0");
	private JLabel drawsShow = new JLabel("0");
	private JLabel defeatsShow = new JLabel("0");
	
	private JFrame frmTicTacToe;
	private ImageIcon ticTacToeIcon = new ImageIcon(this.getClass().getResource("/tic-tac-toe.png"));
	private ImageIcon circleIcon = new ImageIcon(this.getClass().getResource("/circle.png"));
	private ImageIcon xIcon = new ImageIcon(this.getClass().getResource("/x.png"));
	
	private int turn = 0;
	private String[][] table = new String[3][3];
	
}
