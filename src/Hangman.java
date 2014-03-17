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
	private JLabel successLabel;
	private JLabel choiceLabel;
	private JLabel lbl;
	private JLabel labelTop1;
	private JLabel labelTop2;
	private JLabel labelTop3;
	
	private String guessThis = "";
	private String hideThis = "";
	private String[] letters;
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
	    String[] alpha = "abcdefghijklmnopqrstuvwxyz".split("");
	        for(int i=1;i<=26;i++)
	        {
	        	  btn=new JButton(String.valueOf(alpha[i]));
	              alphaPanel.add(btn);
	              btn.setText(alpha[i]);
	              btn.addActionListener(new AlphaButtonListener());
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
			labelTop1.setText(getWord());
		}
		
		public String getWord()
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
			letters = randWord.split("");
			for (int i = 0; i < letters.length; i++)
			{
				guessThis += letters[i] + " ";
			}
			for (int i = 0; i < letters.length - 1; i++)
			{
				hideThis += "_ ";
			}
			
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
	}
	
	private class AlphaButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			choiceLabel.setVisible(true);
			JButton choiceBtn = (JButton)e.getSource();
			choiceLabel.setText(choiceBtn.getText());
			
			if(guessThis.indexOf(choiceBtn.getText()) != -1)
			{
				String cue = choiceBtn.getText();
				
				while (Array.IndexOf(letters, cue) != -1);
				
			}
			

		}
	}
	
	public static void main (String[] args)
	{
		Hangman myFrame = new Hangman();
		myFrame.setVisible(true);
	}

	public int getCueCount(String guessThis2, String text) 
	{
		int counter = 0;
		for( int i = 0; i < guessThis2.length(); i++ ) 
		{
		    if(guessThis2.charAt(i) == 'text') 
		    {
		        counter++;
		    } 
		}
		return counter;
	}	
}
