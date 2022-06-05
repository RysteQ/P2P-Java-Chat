import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				try 
				{
					JFrame mainForm = new JFrame();
					
					GUI window = new GUI(mainForm);
					
					window.initGUI();
					window.displayGUI();
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}	
			}
		});
	}
}