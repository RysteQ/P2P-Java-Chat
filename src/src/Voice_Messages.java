import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;

public class Voice_Messages 
{
	public static class RecordVoiceMessage implements ActionListener
	{
		public void init(Color backgroundColour)
		{
			initFrame(backgroundColour);
			initButtons(backgroundColour);
		}
		
		public void displayGUI() 
		{
			form.setVisible(true);
		}
		
		public boolean isOpen() 
		{
			return isFormOpen;
		}
		
		public boolean recorded() 
		{
			return recorded;
		}
		
		private void initFrame(Color backgroundColour) 
		{
			form = new JFrame();
			
			form.setTitle("Record Voice Message");
			form.setIconImage(recordIcon.getImage());
			
			form.setBackground(backgroundColour);
			form.setLayout(null);
			
			form.setBounds(100, 100, 350, 145);
			form.setResizable(false);
			form.setLocationRelativeTo(null);
			
			form.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
			form.getContentPane().setLayout(null);
			
			form.addWindowListener(new WindowAdapter() 
			{
			    @Override
			    public void windowClosing(WindowEvent event) 
			    {
			        closeApplication();
			    }
			});
		
			isFormOpen = true;
		}
		
		private void initButtons(Color backgroundColour) 
		{
			startRecordingButton.setBackground(backgroundColour);
			startRecordingButton.setFont(new Font("Arial", Font.BOLD, 18));
			startRecordingButton.setBounds(5, 5, 325, 45);
			startRecordingButton.setFocusPainted(false);
			startRecordingButton.addActionListener(this);
			
			stopRecordingButton.setBackground(backgroundColour);
			stopRecordingButton.setFont(new Font("Arial", Font.BOLD, 18));
			stopRecordingButton.setBounds(5, 55, 325, 45);
			stopRecordingButton.setFocusPainted(false);
			stopRecordingButton.addActionListener(this);
			
			form.add(startRecordingButton);
			form.add(stopRecordingButton);
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			Constants consts = new Constants();
			
			if (e.getSource() == startRecordingButton) 
			{
				startRecording(consts.DEFAULT_VOICE_MESSAGE_SAVE_LOCATION, consts.VOICE_MESSAGE_FILENAME, consts.VOICE_MESSAGE_SAMPLE_RATE, consts.VOICE_MESSAGE_SAMPLE_BITS, consts.VOICE_MESSAGE_CHANNELS);
			}
			else if (e.getSource() == stopRecordingButton) 
			{
				stopRecording();
			}
		}
		
		private void startRecording(String saveLocation, String filename, int sampleRate, int sampleBits, int channels)
		{
			recorder = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, sampleBits, channels, 4, sampleRate, false);
			inputInfo = new DataLine.Info(TargetDataLine.class, recorder);
			
			if (AudioSystem.isLineSupported(inputInfo) == false) 
			{
				JOptionPane.showMessageDialog(null, "Audio system does not support the data info", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			new Thread(new Runnable() 
			{
				public void run()
				{
					try 
					{
						inputLine = (TargetDataLine) AudioSystem.getLine(inputInfo);
						inputLine.open();
						inputLine.start();
					}
					catch (LineUnavailableException lineUnabailableError) 
					{
						JOptionPane.showMessageDialog(null, "Input line in unavailable", "Error", JOptionPane.ERROR);
						lineUnabailableError.printStackTrace();
						
						return;
					}
						
					AudioInputStream audioInput = new AudioInputStream(inputLine);
					File recordedMessage = new File(saveLocation + "\\" + filename);
					recording = true;
					recorded = false;
						
					try 
					{
						AudioSystem.write(audioInput, AudioFileFormat.Type.WAVE, recordedMessage);
					} 
					catch (IOException recordSaveError) 
					{
						JOptionPane.showMessageDialog(null, "Error saving voice message", "Error", JOptionPane.ERROR_MESSAGE);
						recordSaveError.printStackTrace();
						
						recording = false;
						
						return;
					}
				}
			}).start();
		}
		
		private void stopRecording() 
		{
			if (recording) 
			{
				inputLine.stop();
				inputLine.close();
				
				recording = false;
				recorded = true;
				
				closeApplication();
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "You are not currently recording a voice message", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		public void closeApplication() 
		{
			if (recording) 
			{
				inputLine.stop();
				inputLine.close();
			
				Constants consts = new Constants();
				File fileToDelete = new File(consts.DEFAULT_VOICE_MESSAGE_SAVE_LOCATION + "\\" + consts.VOICE_MESSAGE_FILENAME);
				
				fileToDelete.delete();
			}
			
			isFormOpen = false;
		}
		
		// PRIVATE VARIABLES
		private JButton startRecordingButton = new JButton("Record");
		private JButton stopRecordingButton = new JButton("Stop");

		private ImageIcon recordIcon = new ImageIcon(getClass().getResource("/resources/recordIcon.png"));
		private JFrame form;
		
		AudioFormat recorder;
		DataLine.Info inputInfo;
		TargetDataLine inputLine;
		
		private boolean recording = false;
		private boolean recorded = false;
		private boolean isFormOpen = true;
	}
	
	
	public static class ReceivedVoiceMessages implements ActionListener
	{
		public void init(Color themeColour) 
		{
			initFrame(themeColour);
			initButtons(themeColour);
		}
		
		public void displayGUI() 
		{
			form.setVisible(true);
		}
		
		private void initFrame(Color backgroundColour) 
		{
			form = new JFrame();
			
			form.setTitle("Play Voice Message");
			form.setIconImage(recordIcon.getImage());
			
			form.setBackground(backgroundColour);
			form.setLayout(null);
			
			form.setBounds(100, 100, 350, 145);
			form.setResizable(false);
			form.setLocationRelativeTo(null);
			
			form.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
			form.getContentPane().setLayout(null);
			
			form.addWindowListener(new WindowAdapter() 
			{
			    @Override
			    public void windowClosing(WindowEvent event) 
			    {
			        closeApplication();
			    }
			});
		}
		
		private void initButtons(Color backgroundColour) 
		{
			playReceivedVoiceMessage.setBackground(backgroundColour);
			playReceivedVoiceMessage.setFont(new Font("Arial", Font.BOLD, 18));
			playReceivedVoiceMessage.setBounds(5, 5, 325, 45);
			playReceivedVoiceMessage.setFocusPainted(false);
			playReceivedVoiceMessage.addActionListener(this);
			
			stopPlayingReceivedVoiceMessage.setBackground(backgroundColour);
			stopPlayingReceivedVoiceMessage.setFont(new Font("Arial", Font.BOLD, 18));
			stopPlayingReceivedVoiceMessage.setBounds(5, 55, 325, 45);
			stopPlayingReceivedVoiceMessage.setFocusPainted(false);
			stopPlayingReceivedVoiceMessage.addActionListener(this);
			
			form.add(playReceivedVoiceMessage);
			form.add(stopPlayingReceivedVoiceMessage);
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			Constants consts = new Constants();
			
			if (e.getSource() == playReceivedVoiceMessage) 
			{
				String filepath = consts.DEFAULT_VOICE_MESSAGE_SAVE_LOCATION + "\\" + consts.RECEIVED_VOICE_MESSAGE_FILENAME;
				File fileToCheck = new File(filepath);
				
				if (fileToCheck.exists()) 
				{
					playReceivedVoiceMessage(filepath);
				} 
				else 
				{
					JOptionPane.showMessageDialog(null, "You do not have a voice message yet", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else if (e.getSource() == stopPlayingReceivedVoiceMessage) 
			{
				stopPlayingReceivedVoiceMessage();
			}
		}
		
		private void playReceivedVoiceMessage(String fileLocation)
		{
			if (playing)
				return;
			
			new Thread(new Runnable() 
			{
				public void run()
				{	
					File voiceMessage = new File(fileLocation);
					playing = true;
					
					try 
					{
						audioController = AudioSystem.getClip();
						
						audioController.open(AudioSystem.getAudioInputStream(voiceMessage));
						audioController.start();
					} 
					catch (Exception voiceMessagePlayError) 
					{
						voiceMessagePlayError.printStackTrace();
						playing = false;
					}
				}
			}).start();
		}
		
		private void stopPlayingReceivedVoiceMessage() 
		{
			if (playing) 
			{
				audioController.stop();
				playing = false;	
			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "There is no voice message playing right now", "Error", JOptionPane.ERROR_MESSAGE);	
			}
		}
		
		private void closeApplication() 
		{
			if (playing) 
			{
				audioController.stop();
				playing = false;
			}
		}
		
		private JButton playReceivedVoiceMessage = new JButton("Play");
		private JButton stopPlayingReceivedVoiceMessage = new JButton("Stop");
		
		private ImageIcon recordIcon = new ImageIcon(getClass().getResource("/resources/recordIcon.png"));
		private JFrame form;
	
		private Clip audioController;
		private boolean playing = false;
	}
}