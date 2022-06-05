/* 
 * Filename: Prompts.java
 * Purpose: Displays a file dialog box to select a file or a messagebox for a question with a yes / no answer
*/

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Prompts 
{
	public String selectFile(String startingDirectory) 
	{
		JFileChooser fileDialog = new JFileChooser();
		FileNameExtensionFilter extensions = new FileNameExtensionFilter("All Files (*.*)", "*.*");
		
		fileDialog.setFileFilter(extensions);
		
		if (fileDialog.showDialog(null, startingDirectory) == JFileChooser.APPROVE_OPTION) 
		{
			return fileDialog.getSelectedFile().getAbsolutePath();	
		}
		
		return null;
	}
	
	public boolean showMessageBoxChoice(JFrame mainWindow, String title, String contents) {
		int result = JOptionPane.showConfirmDialog(mainWindow, contents, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (result == JOptionPane.YES_OPTION)
			return true;
		
		return false;
	}
}