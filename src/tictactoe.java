import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
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

public class TicTacToe implements ActionListener
{
	public TicTacToe(boolean singleOrMulti, boolean slaveOrMaster, String IP, int port)
	{
		this.singleOrMulti = singleOrMulti;
		this.slaveOrMaster = slaveOrMaster;
		this.IP = IP;
		this.port = port;
	}

	public void start() 
	{					
		initialize();
	}
 
	public void initialize() 
	{
		if (singleOrMulti)
		{
			if (slaveOrMaster)
			{
				serverConnection = new Networking.host(port);
				turnAllowed = true;

				serverConnection.bind(false);

				new Thread(new Runnable() 
				{
					public void run() 
					{
						Prompts prompt = new Prompts();
						Constants consts = new Constants();
						
						String receivedMessage;
						int x;
						int y;
						
						
						JButton[][] buttons = 
						{
							{ topLeft, topMid, topRight },
							{ midLeft, midMid, midRight },
							{ botLeft, botMid, botRight }
						};
						
						while (true) 
						{
							do 
							{
								receivedMessage = serverConnection.receiveMessages();	
							}
							while (receivedMessage == null);
							
							if (receivedMessage.equals(consts.ASK_RESET_APPROVAL)) 
							{
								if (prompt.showMessageBoxChoice(frmTicTacToe, "Question", "Do you want to reset the game ?")) 
								{
									reset();
								}
								
								serverConnection.broadcastMessage(consts.GAME_RESET_APPROVED);
								
								continue;
							} 
							else if (receivedMessage.equals(consts.GAME_RESET_APPROVED)) 
							{
								reset();
								
								continue;
							}
							
							x = Integer.parseInt(receivedMessage.split("[|]")[0]);
							y = Integer.parseInt(receivedMessage.split("[|]")[1]);
							
							buttons[x][y].setIcon(circleIcon);

							allowedMove[x][y] = false;
							turnAllowed = true;
							table[x][y] = "O";
							
							check();
						}
					}
				}).start();
			}
			else 
			{
				try 
				{
					Thread.sleep(100);
					
					clientConnection = new Networking.client(IP, port);
					
					new Thread(new Runnable() 
					{
						public void run() 
						{
							Constants consts = new Constants();
							Prompts prompt = new Prompts();
							
							String receivedMessage = null;
							String winner = null;
							int x;
							int y;
							
							JButton[][] buttons = 
							{
								{ topLeft, topMid, topRight },
								{ midLeft, midMid, midRight },
								{ botLeft, botMid, botRight }
							};
							
							while (true) 
							{
								try 
								{
									do 
									{
										receivedMessage = clientConnection.receiveMessage();	
									}
									while (receivedMessage == null);
								} 
								catch (IOException receivingMoveError) 
								{
									receivingMoveError.printStackTrace();
								}
								
								if (receivedMessage.split("[|]")[0].equals(consts.ANNOUNCE_WINNER)) 
								{
									winner = receivedMessage.split("[|]")[1];
									
									if (winner.equals("YOU")) 
									{
										JOptionPane.showMessageDialog(null, "YOU WINS !!!!", "Information", JOptionPane.INFORMATION_MESSAGE);	
										
										int currentWins = Integer.parseInt(winsShow.getText());
										
										winsShow.setText(Integer.toString(currentWins + 1));
									} 
									else if (winner.equals("ENEMY"))
									{
										JOptionPane.showMessageDialog(null, "ENEMY WINS !!!!", "Information", JOptionPane.INFORMATION_MESSAGE);	
									
										int currentLoses = Integer.parseInt(defeatsShow.getText());
										
										defeatsShow.setText(Integer.toString(currentLoses + 1));
									} 
									else if (winner.equals("DRAW"))
									{
										JOptionPane.showMessageDialog(null, "Draw !!!!", "Information", JOptionPane.INFORMATION_MESSAGE);	
										
										int currentDraws = Integer.parseInt(drawsShow.getText());
										
										drawsShow.setText(Integer.toString(currentDraws + 1));
									}
							
									reset();
									
									continue;
								} 
								else if (receivedMessage.equals(consts.ASK_RESET_APPROVAL))
								{
									if (prompt.showMessageBoxChoice(frmTicTacToe, "Question", "Do you want to reset the game ?")) 
									{
										try 
										{
											clientConnection.sendMessage(consts.GAME_RESET_APPROVED);
											reset();
										} catch (IOException sendErrorMessage) 
										{
											sendErrorMessage.printStackTrace();
										}
									}
								
									continue;
								} else if (receivedMessage.equals(consts.GAME_RESET_APPROVED))
								{
									reset();
								}
							
								// get the coordinates
								x = Integer.parseInt(receivedMessage.split("[|]")[0]);
								y = Integer.parseInt(receivedMessage.split("[|]")[1]);
								
								// change the icon of the corresponding button
								buttons[x][y].setIcon(xIcon);
								
								// update some values for the map etc
								allowedMove[x][y] = false;
								turnAllowed = true;
								table[x][y] = "O";
							}
						}
					}).start();
				} 
				catch (InterruptedException connectionError)
				{
					connectionError.printStackTrace();
				}
			}
		}
			
		frmTicTacToe = new JFrame();
		frmTicTacToe.setResizable(false);
		frmTicTacToe.setTitle("TIC-TAC-TOE");
		frmTicTacToe.setIconImage(ticTacToeIcon.getImage());
		frmTicTacToe.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmTicTacToe.setBounds(100, 100, 450, 300);
		frmTicTacToe.getContentPane().setLayout(null);
		frmTicTacToe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frmTicTacToe.addWindowListener(new WindowAdapter() 
		{
		    @Override
		    public void windowClosing(WindowEvent event) 
		    {
		        exitProcedure();
		    }
		});
		
		for(int i = 0; i < table.length; i++)
			for(int j = 0; j < table.length; j++)
				table[i][j] = "";
		
		initPanels();
		initSeperators();
		initPlaces();
		initLabels();
		initScoreboard();
		
		frmTicTacToe.setVisible(true);
	}

	private void initPanels() 
	{
		playPanel.setBackground(Color.LIGHT_GRAY);
		playPanel.setBounds(0, 0, 300, 260);
		frmTicTacToe.getContentPane().add(playPanel);
		playPanel.setLayout(null);	

		infoPanel.setBackground(Color.LIGHT_GRAY);
		infoPanel.setBounds(306, 0, 129, 260);
		frmTicTacToe.getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
	}
	
	private void initPlaces() 
	{
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
	
		start.setFont(new Font("Arial", Font.BOLD, 15));
		start.setBounds(10, 230, 110, 25);
		start.setContentAreaFilled(false);
		start.addActionListener(this);
		start.setFocusPainted(false);
		infoPanel.add(start);
	}
	
	private void initSeperators() 
	{
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
				
		ticTacToeUnderline.setForeground(Color.BLACK);
		ticTacToeUnderline.setBounds(20, 25, 95, 2);
		infoPanel.add(ticTacToeUnderline);
	}

	private void initLabels() 
	{
		ticTacToeLabel.setFont(new Font("Arial", Font.BOLD, 14));
		ticTacToeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ticTacToeLabel.setBounds(10, 11, 110, 15);
		infoPanel.add(ticTacToeLabel);
	}
	
	private void initScoreboard() 
	{
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
	public void actionPerformed(ActionEvent e) 
	{
		Constants consts = new Constants();
		
		if(e.getSource() == topLeft) setIcon(0, 0);
		if(e.getSource() == topMid) setIcon(0, 1);
		if(e.getSource() == topRight) setIcon(0, 2);
		if(e.getSource() == midLeft) setIcon(1, 0);
		if(e.getSource() == midMid) setIcon(1, 1);
		if(e.getSource() == midRight) setIcon(1, 2);
		if(e.getSource() == botLeft) setIcon(2, 0);
		if(e.getSource() == botMid) setIcon(2, 1);
		if(e.getSource() == botRight) setIcon(2, 2);
		
		if(e.getSource() == start) 
		{
			if (singleOrMulti) 
			{		
				if (slaveOrMaster == false)
				{
					try 
					{
						clientConnection.sendMessage(consts.ASK_RESET_APPROVAL);
					} 
					catch (IOException sendMessageError) 
					{
						sendMessageError.printStackTrace();
					}
				} else 
				{
					serverConnection.broadcastMessage(consts.ASK_RESET_APPROVAL);
				}
			} else 
			{
				reset();
			}
		}

		if (singleOrMulti == false) 
		{
			turn++;
			check();	
		}
	}
	
	
	private void setIcon(int x, int y) {
		// I am now proud of this "shortcut" but there was a problem with the new thread I created
		// for multiplayer so I couldn't care less if this is messy or not
		if (allowedMove[x][y] == false)
			return;
		
		if (singleOrMulti) 
		{
			if (turnAllowed == false)
				return;
			
			try 
			{
				if (slaveOrMaster) 
				{
					serverConnection.broadcastMessage
					(
						Integer.toString(x) 
						+ "|"  		
						+ Integer.toString(y) 
						+ "|" 
						+ "X"
					);
					
					buttonMap[x][y].setIcon(xIcon);
					table[x][y] = "X";
					
					check();
				} 
				else 
				{
					clientConnection.sendMessage
					(
						Integer.toString(x) 
						+ "|" 
						+ Integer.toString(y)
						+ "|" 
						+ "O"
					);
					
					buttonMap[x][y].setIcon(circleIcon);
					table[x][y] = "O";
				}
				
				turnAllowed = false;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		} 
		else 
		{
			if(turn % 2 == 0)
			{
				buttonMap[x][y].setIcon(xIcon);
				table[x][y] = "X";
			} 
			else if(turn % 2 == 1)
			{
				buttonMap[x][y].setIcon(circleIcon);
				table[x][y] = "O";
			}	
		}
	}
	
	public void reset() 
	{
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
		
		for (int x = 0; x < 3; x++) 
		{
			for (int y = 0; y < 3; y++) 
			{
				allowedMove[x][y] = true;
				table[x][y] = "";
			}
		}
		
		turn = 0;
	}
	
	private void check() 
	{
		Constants consts = new Constants();
		String winner = "NULL";
		
		// horizontal check
		for (int x = 0; x < 3; x++) 
		{
			if (table[x][0].equals(table[x][1]) && table[x][0].equals(table[x][2])) 
			{
				if (table[x][0].equals("X")) 
				{
					winner = "PLAYER 1";		
				} 
				else if (table[x][0].equals("O")) 
				{
					winner = "PLAYER 2";
				}	
			}
		}
		
		// vertical check
		for (int y = 0; y < 3; y++) 
		{
			if (table[0][y].equals(table[1][y]) && table[0][y].equals(table[2][y])) 
			{
				if (table[0][y].equals("X")) 
				{
					winner = "PLAYER 1";		
				} 
				else if (table[0][y].equals("O")) 
				{
					winner = "PLAYER 2";
				}	
			}
		}
		
		// diagonal check
		if ((table[0][0].equals(table[1][1]) && table[0][0].equals(table[2][2])) || table[0][2].equals(table[1][1]) && table[0][2].equals(table[2][0]))
		{
			if (table[1][1].equals("X"))
			{
				winner = "PLAYER 1";
			} 
			else if (table[1][1].equals("O")) 
			{
				winner = "PLAYER 2";
			}
		}
		
		if (winner.equals("NULL") == false) 
		{
			if (singleOrMulti) 
			{
				if (winner.equals("PLAYER 1")) 
				{
					int currentWins = Integer.parseInt(winsShow.getText());
					JOptionPane.showMessageDialog(null, "YOU WIN !!!!", "Information", JOptionPane.INFORMATION_MESSAGE);	
					
					serverConnection.broadcastMessage(consts.ANNOUNCE_WINNER + "|" + "ENEMY");
					winsShow.setText(Integer.toString(currentWins + 1));
				} 
				else 
				{										
					int currentLoses = Integer.parseInt(defeatsShow.getText());

					JOptionPane.showMessageDialog(null, "ENEMY WINS !!!!", "Information", JOptionPane.INFORMATION_MESSAGE);	
					
					serverConnection.broadcastMessage(consts.ANNOUNCE_WINNER + "|" + "YOU");
					defeatsShow.setText(Integer.toString(currentLoses + 1));
				}
			}
			
			reset();
		}
		
		
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++)
				if (allowedMove[x][y] == true)
					return;
		
		JOptionPane.showMessageDialog(null, "Out of moves", "Information", JOptionPane.INFORMATION_MESSAGE);
		
		if (singleOrMulti) 
		{
			int currentDraws = Integer.parseInt(drawsShow.getText());
		
			serverConnection.broadcastMessage(consts.ANNOUNCE_WINNER + "|" + "DRAW");	
			drawsShow.setText(Integer.toString(currentDraws + 1));
		}
		
		reset();
	}
	
	public void exitProcedure() 
	{
		if (singleOrMulti) 
		{
			if (slaveOrMaster) 
			{
				serverConnection.closeAll();
			} 
			else 
			{
				try 
				{
					clientConnection.leave();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		frmTicTacToe.dispose();
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
	
	private boolean[][] allowedMove = 
	{
		{ true, true, true },
		{ true, true, true },
		{ true, true, true }
	};
	
	private JButton[][] buttonMap = 
	{
		{ topLeft, topMid, topRight },
		{ midLeft, midMid, midRight },
		{ botLeft, botMid, botRight }
	};
	
	private boolean singleOrMulti = false;
	private boolean slaveOrMaster = false;
	private boolean turnAllowed = false;
	private String IP = "";
	private int port = 0;
	private Networking.client clientConnection = null;
	private Networking.host serverConnection = null;
}
