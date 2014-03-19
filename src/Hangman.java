import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman extends JFrame
{
	private static final int APP_WIDTH = 500;
	private static final int APP_HEIGHT = 500;
	private JLabel choiceLabel;
	private JLabel labelTop1;
	private JLabel labelTop2;
	private JLabel labelTop3;
	
	private char[] guessThis = null;
	private char[] hideThis = null;
	private char[] letters;
	private JButton button;
	private JButton btn;
	private JPanel alphaPanel;
	private JPanel topPanel;
	
	public Hangman()
	{
		super("Clicking");
		setSize(APP_WIDTH, APP_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(3, 3));
		
		button = new JButton("New Word");
		button.addActionListener(new MyButtonListener());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 5));
		topPanel.setBackground(Color.PINK);
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(1, 3));
		middlePanel.setBackground(Color.YELLOW);
		
		//successLabel = new JLabel();
		//successLabel.setVisible(false);
		//topPanel.add(successLabel);
		
		labelTop1 = new JLabel("Top Left");
		labelTop1.setForeground(Color.RED);
		topPanel.add(labelTop1);
		
		//JLabel labelTop2 = new JLabel("Top Center");
		//labelTop2.setForeground(Color.WHITE);
		//topPanel.add(labelTop2);
		
		labelTop3 = new JLabel("Top Right");
		labelTop3.setForeground(Color.YELLOW);
		topPanel.add(labelTop3);
		
		topPanel.add(button);
		
		choiceLabel = new JLabel();
		choiceLabel.setVisible(false);
		middlePanel.add(choiceLabel);
		
		int rows = 4;
		int cols = 7; 
		int hgap = 2;
		int vgap = 2;
	    alphaPanel = new JPanel(new GridLayout(rows, cols, hgap, vgap));
	    char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	        for(int i=0;i < alpha.length;i++)
	        {
	        	  btn=new JButton(String.valueOf(alpha[i]));
	              alphaPanel.add(btn);
	              btn.addActionListener(new MyButtonListener());
	        }   
		
		add(topPanel);
		add(middlePanel);
		add(alphaPanel);
		pack();
	}
	
	private class MyButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			labelTop1.setText(getWord().toString());
		}
		
		public char[] getWord()
		{
			Random rand = new Random(System.currentTimeMillis());
			String randWord = "";
			List<String> words = new ArrayList<String>();
			Scanner fileIn = getFileReader("input/dictionary.txt");
			while(fileIn.hasNext())
			{
				String line = fileIn.nextLine();
				String[] wordsLine = line.split(", ");
			    for(String word : wordsLine) 
			    {
			    	words.add(word);
			    }
		    }
			randWord = words.get(rand.nextInt(words.size()));
			letters = randWord.toCharArray();
			hideThis = getHidden(letters);
			
			System.out.println(randWord);
			button.setVisible(false);
			return hideThis;
		}
		
		public Scanner getFileReader(String fileName)
		{
			File file = new File(fileName);

			try
			{
				return new Scanner(file);
			}
			
			catch (FileNotFoundException e)
			{
				System.out.println(e.getMessage());
				System.exit(-1);
				return null;
			}
		}
		
		private char[] getHidden(char[]word)
		{
			char[] hideVersion = new char[word.length];
			for (int i = 0; i < word.length; i++)
			{
				if (Character.isLetter(word[i]))
				{
					hideVersion[i] = '_';
				}
			}
			return hideVersion;
		}
	}
	
	private class AlphaButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			choiceLabel.setVisible(true);
			JButton choiceBtn = (JButton)e.getSource();
			choiceLabel.setText(choiceBtn.getText());
			
			String cue = choiceBtn.getText();
			char c = cue.charAt(0); 
			char[] wordUpdate = replace(guessThis, c);
			
			labelTop1.setText(wordUpdate.toString());
		}
		
		public char[] replace(char[] word, char replace)
		{
			//the letter comes in
			//check to see if the letter matches the first letter in the word
			//if so, replace the char at that index point in the hideThis
			//if not, leave it alone (no else statement needed)
			//return the new hideThis array, not the word array.
			
			for (int i = 0; i < word.length; i++)
			{
				if (replace == word[i])
				{
					hideThis[i] = replace;
				}
			}
			
			return hideThis;
		}
		
	}
	
	public static void main (String[] args)
	{
		Hangman myFrame = new Hangman();
		myFrame.setVisible(true);
	}
}

