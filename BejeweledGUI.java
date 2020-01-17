/**
 * BejeweledGUI.java (Skeleton)
 * Provide the GUI for the Bejeweled game
 */
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.border.*;
import javax.swing.JTextField;
import java.awt.*;
import java.io.*;

public class BejeweledGUI {
	// the name of the configuration file
   private final String CONFIGFILE = "config.txt";
   private final Color BACKGROUNDCOLOUR = new Color(0, 0, 0);
   private final Color white = new Color (255, 255, 255);
	
   private JLabel[][] slots;
   private JFrame mainFrame;
   private ImageIcon[] pieceIcon;
   public JButton endGameButton;
   public JButton changeMovesButton;
   public JButton showHintButton;
   public JButton changeBeatButton;
   private JTextField score;
   private JTextField numMoveLeft;
	public JTextField beat;
   private String logoIcon;
   private String[] iconFile;
	      
/**
* Number of different piece styles
*/
   public final int NUMPIECESTYLE = 7;

/**
* Number of rows on the game board
*/
   public final int NUMROW = 8;

/**
* Number of colums on the game board
*/
   public final int NUMCOL = 8;

/**
* Constants defining the demensions of the different components
* on the GUI
*/    
   private final int PIECESIZE = 70;
   private final int PLAYPANEWIDTH = NUMCOL * PIECESIZE;
   private final int PLAYPANEHEIGHT = NUMROW * PIECESIZE;

   private final int INFOPANEWIDTH = 2 * PIECESIZE;
   private final int INFOPANEHEIGHT = PLAYPANEHEIGHT;

   private final int LOGOHEIGHT = 2 * PIECESIZE;
   private final int LOGOWIDTH = PLAYPANEWIDTH + INFOPANEWIDTH;

   private final int FRAMEWIDTH = (int)(LOGOWIDTH * 1.03);
   private final int FRAMEHEIGHT = (int)((LOGOHEIGHT + PLAYPANEHEIGHT) * 1.1);

// Constructor:  BejeweledGUI
// - intialize variables from config files
// - initialize the imageIcon array
// - initialize the slots array
// - create the main frame
   public BejeweledGUI () {
      initConfig();
      initImageIcon();
      initSlots();
      createMainFrame();
      
   }

   private void initConfig() {
    	// TO DO: 
   	// initialize the following variables with information read from the config file 
   	// - logoIcon
   	// - iconFile 
      try {
         BufferedReader in = new BufferedReader(new FileReader(CONFIGFILE));
      	
         logoIcon = in.readLine();
         iconFile = new String [NUMPIECESTYLE];
         for (int i = 0; i < NUMPIECESTYLE; i ++) {
            iconFile[i] = in.readLine();
         }
      	// this closes the file
         in.close();
      // this is the exception if the reader doesn't work
      } catch (IOException iox) {
      
         System.out.println("Error");
      } 
   }

// initImageIcon
// Initialize pieceIcon arrays with graphic files
   private void initImageIcon() {
      pieceIcon = new ImageIcon[NUMPIECESTYLE];
      for (int i = 0; i < NUMPIECESTYLE; i++) {
         pieceIcon[i] = new ImageIcon(iconFile[i]);
      }
   }

// initSlots
// initialize the array of JLabels
   private void initSlots() {
      slots = new JLabel[NUMROW][NUMCOL];
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots [i] [j] = new JLabel ();
         // slots[i][j].setFont(new Font("SansSerif", Font.BOLD, 18));
            slots[i][j].setPreferredSize(new Dimension(PIECESIZE, PIECESIZE));
            slots [i] [j].setHorizontalAlignment (SwingConstants.CENTER);      
         }
      }
   }

// createPlayPanel
   private JPanel createPlayPanel() {
      JPanel panel = new JPanel(); 
      panel.setPreferredSize(new Dimension(PLAYPANEWIDTH, PLAYPANEHEIGHT));
      panel.setBackground(BACKGROUNDCOLOUR);
      panel.setLayout(new GridLayout(NUMROW, NUMCOL));
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            panel.add(slots[i][j]);
         }
      }
      return panel;    
   }

// createInfoPanel
   private JPanel createInfoPanel() {
   
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(INFOPANEWIDTH, INFOPANEHEIGHT));
      panel.setBackground (BACKGROUNDCOLOUR);
      panel.setBorder (new LineBorder (Color.white)); 
   
      Font headingFont = new Font ("Serif", Font.PLAIN, 24);
      Font regularFont = new Font ("Serif", Font.BOLD, 16);
   
   // Create a panel for the scoreboard
      JPanel scorePanel = new JPanel();
      scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
      
      scorePanel.setBackground(BACKGROUNDCOLOUR);
   
     // Create the label to display "Score" heading
      JLabel scoreLabel = new JLabel ("     Score     ", JLabel.CENTER);
      scoreLabel.setFont(headingFont);
      scoreLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
      scoreLabel.setForeground(Color.white);
   
      score = new JTextField();
      score.setFont(regularFont);
      score.setText("0");
      score.setEditable(false);
      score.setHorizontalAlignment (JTextField.CENTER);
      score.setBackground(BACKGROUNDCOLOUR);
      score.setForeground(Color.white);
      
      scorePanel.add(scoreLabel);
      scorePanel.add(score);
   
      JPanel moveLeftPanel = new JPanel();
      moveLeftPanel.setLayout(new BoxLayout(moveLeftPanel, BoxLayout.Y_AXIS));
      moveLeftPanel.setBackground(BACKGROUNDCOLOUR);
   
   // Create the label to display "Moves Left" heading
      JLabel moveLeftLabel = new JLabel ("Moves Left", JLabel.CENTER);
      moveLeftLabel.setFont(headingFont);
      moveLeftLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
      moveLeftLabel.setForeground(Color.white);
   
      numMoveLeft = new JTextField();
      numMoveLeft.setFont(regularFont);
      numMoveLeft.setText("0");
      numMoveLeft.setEditable(false);
      numMoveLeft.setHorizontalAlignment (JTextField.CENTER);
      numMoveLeft.setBackground(BACKGROUNDCOLOUR);
      numMoveLeft.setForeground(Color.white);
      
      // Create a panel for the beat score
      JPanel beatPanel = new JPanel();
      beatPanel.setLayout(new BoxLayout(beatPanel, BoxLayout.Y_AXIS));
      beatPanel.setBackground(BACKGROUNDCOLOUR);
   
     // Create the label to display "Score To Beat" heading
      JLabel beatLabel = new JLabel ("Score To Beat", JLabel.CENTER);
      beatLabel.setFont(headingFont);
      beatLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
      beatLabel.setForeground(Color.white);
   
      beat = new JTextField();
      beat.setFont(regularFont);
      beat.setText("0");
      beat.setEditable(false);
      beat.setHorizontalAlignment (JTextField.CENTER);
      beat.setBackground(BACKGROUNDCOLOUR);
      beat.setForeground(Color.white);

      
      JLabel emptyLabel1 = new JLabel (" ", JLabel.CENTER);
      emptyLabel1.setFont(headingFont);
      emptyLabel1.setAlignmentX (Component.CENTER_ALIGNMENT); 
          
      JLabel emptyLabel2 = new JLabel (" ", JLabel.CENTER);
      emptyLabel2.setFont(headingFont);
      emptyLabel2.setAlignmentX (Component.CENTER_ALIGNMENT);
      
      JLabel emptyLabel3 = new JLabel (" ", JLabel.CENTER);
      emptyLabel3.setFont(headingFont);
      emptyLabel3.setAlignmentX (Component.CENTER_ALIGNMENT);
      
   
      moveLeftPanel.add(emptyLabel1);
      moveLeftPanel.add(moveLeftLabel);
      moveLeftPanel.add(numMoveLeft);
      moveLeftPanel.add(emptyLabel2);
      beatPanel.add(beatLabel);
      beatPanel.add(beat);
      beatPanel.add(emptyLabel3);
      
   	// this creates the buttons
      endGameButton = new JButton("End Game");
      changeMovesButton = new JButton("Change Move #");
      showHintButton = new JButton("Show Hint");
      changeBeatButton = new JButton("Change Beat Score");
   
      // this adds the panels to the board
      panel.add(scorePanel);
      panel.add(moveLeftPanel);
      panel.add(beatPanel);
      panel.add(endGameButton);
      panel.add(changeMovesButton);
      panel.add(showHintButton);
      panel.add(changeBeatButton);
      
      return panel;
   }

// createMainFrame
   private void createMainFrame() {
   
   // Create the main Frame
      mainFrame = new JFrame ("Bejeweled");
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel panel = (JPanel)mainFrame.getContentPane();
      panel.setLayout (new BoxLayout(panel,BoxLayout.Y_AXIS));
   
   // Create the panel for the logo
      JPanel logoPane = new JPanel();
      logoPane.setPreferredSize(new Dimension (LOGOWIDTH, LOGOHEIGHT));
      logoPane.setBackground (BACKGROUNDCOLOUR);
      
      JLabel logo = new JLabel();
      logo.setIcon(new ImageIcon(logoIcon));
      logoPane.add(logo);
   
   // Create the bottom Panel which contains the play panel and info Panel
      JPanel bottomPane = new JPanel();
      bottomPane.setLayout(new BoxLayout(bottomPane,BoxLayout.X_AXIS));
      bottomPane.setPreferredSize(new Dimension(PLAYPANEWIDTH + INFOPANEWIDTH, PLAYPANEHEIGHT));
      bottomPane.add(createPlayPanel());
      bottomPane.add(createInfoPanel());
   
   // Add the logo and bottom panel to the main frame
      panel.add(logoPane);
      panel.add(bottomPane);
   
      mainFrame.setContentPane(panel);
   //   mainFrame.setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
      mainFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
      mainFrame.setVisible(true);
   }

/**
* Returns the column number of where the given JLabel is on
* 
* @param  label the label whose column number to be requested
* @return the column number
*/
   public int getRow(JLabel label) {
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++) {
         for (int j = 0; j < NUMCOL && result == -1; j++) {
            if (slots[i][j] == label) {
               result = i;
            }
         }
      }
      return result;
   }

/**
* Returns the column number of where the given JLabel is on
* 
* @param  label the label whose column number to be requested
* @return the column number
*/
   public int getColumn(JLabel label) {
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++) {
         for (int j = 0; j < NUMCOL && result == -1; j++) {
            if (slots[i][j] == label) {
               result = j;
            }
         }
      }
      return result;
   }

//    public int getBeat(JLabel label) {
//       int newBeat;
//       newBeat = Integer.parseInt(beat.getText(label));
//       
//       return newBeat;
//    }
   
   public void addListener (BejeweledListener listener) {
   	// add listener for each slot on the game board
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots [i] [j].addMouseListener (listener);
         }
      }
      
   	// add listener for the button
      endGameButton.addMouseListener(listener);
      changeMovesButton.addMouseListener(listener);
      showHintButton.addMouseListener(listener);
      changeBeatButton.addMouseListener(listener);
   }

/**
* Display the specified player icon on the specified slot
* 
* @param row row of the slot
* @param col column of the slot
* @param piece index of the piece to be displayed
*/
   public void setPiece(int row, int col, int piece) {
      slots[row][col].setIcon(pieceIcon[piece]);
   }

/**
* Highlight the specified slot with the specified colour
* 
* @param row row of the slot
* @param col column of the slot
* @param colour colour used to highlight the slot
*/
   public void highlightSlot(int row, int col, Color colour) {
      slots[row][col].setBorder (new LineBorder (colour));   
   }

/**
* Unhighlight the specified slot to the default grid colour
* 
* @param row row of the slot
* @param col column of the slot
*/
   public void unhighlightSlot(int row, int col) {
      slots[row][col].setBorder (new LineBorder (BACKGROUNDCOLOUR));   
   }

/**
* Display the score on the corresponding textfield
* 
* @param point the score to be displayed
*/
   public void setScore(int point) {
      score.setText(point+"");
   }
	
   // EXTENSION 
   // this updates the score to beat
   public void setBeat (int toBeat) {
      beat.setText(toBeat+"");
   }
/**
* Display the number of moves left on the corresponding textfield
* 
* @param num number of moves left to be displayed
*/
   public void setMoveLeft(int num) {
      numMoveLeft.setText(num+"");
   }	
  
/**
* Reset the game board (clear all the pieces on the game board)
* 
*/
   public void resetGameBoard() {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots[i][j].setIcon(null);
         }
      }
   }

/**
* Display a pop up window displaying the message about invalid move
* 
*/
   public void showInvalidMoveMessage(){
      JOptionPane.showMessageDialog(null, " This move is invalid", "Invalid Move", JOptionPane.PLAIN_MESSAGE, null); 
   }

/**
* Display a pop up window specifying the size of the chain(s) that is (are) formed after the swap
* 
* @param chainSize the size of the chain(s) that is (are) formed
*/
   public void showChainSizeMessage(int chainSize){
      JOptionPane.showMessageDialog(null, "Chain(s) with total size of " + chainSize + " is (are) formed.", "Chain Formed!", JOptionPane.PLAIN_MESSAGE, null); 
   }

/**
* Display a pop up window specifying the game is over with the score and number of moves used
* 
* @param point the score earned in the game
* @param numMove the number of moves used in the game
*/
   public void showGameOverMessage(int point, int numMove, boolean beat){
   String passMessage;
      if (beat) {
         passMessage = "You passed!";
      } else {
         passMessage = "You did not pass!!";
      }
      JOptionPane.showMessageDialog(null, passMessage, "Success || !Success", JOptionPane.PLAIN_MESSAGE, null);
      JOptionPane.showMessageDialog(null, "Game Over!\nYour score is " + point + " in " + numMove + " moves.", "Game Over!", JOptionPane.PLAIN_MESSAGE, null); 
//       System.exit (0);
   }

   String moves;
   int newMoves;
	
   // EXTENSION
   // this is if the user wants to update the moves in their game
   public void showChangeMovesMessage() {
      moves = JOptionPane.showInputDialog(null,"Change Moves to:",
         "Change Number of Moves", JOptionPane.QUESTION_MESSAGE);
         // if something was input, it will update newMoves and update the logics of the game
      if (moves != null) {
         newMoves = Integer.parseInt(moves);
      }
      
   }
   
   String toBeat;
   int newBeat;
	
   // EXTENSION
   // this checks to see what they want to update their beat score to
   public void showChangeBeatMessage() {
      toBeat = JOptionPane.showInputDialog(null,"Change Beat Score to:",
         "Change Number of Points", JOptionPane.QUESTION_MESSAGE);
      // if something was entered, it will set a new value to newBeat and be updated in the logics
      if (toBeat != null) {
         newBeat = Integer.parseInt(toBeat);
      }
   }
   
   // EXTENSION
   // this checks to see if the user wants to replay the game   
   public int showReplayGameMessage() {
      int choice;
      
      choice = JOptionPane.showConfirmDialog(null, "Would you like to replay the game?", "Replay?", JOptionPane.YES_NO_OPTION);
      
      // if they select yes, it will return 1
      if (choice == JOptionPane.YES_OPTION) {
         return 1;
      } else {
         // otherwise, it wil exit the game
         System.exit(0);
         return 0;
      }
   }
   
   // EXTENSION
// this displays a message when there are no possible moves on the board
   public void showResetBoardMessage () {
      JOptionPane.showMessageDialog(null, "Board was reset!", "No Matches!", JOptionPane.PLAIN_MESSAGE, null);
   }
   
   //EXTENSION
   // this shows the message that the board was reset after they reset moves or beat score
   public void showRestartGameMessage() {
      JOptionPane.showMessageDialog(null, "Board was reset!", "Restarted!", JOptionPane.PLAIN_MESSAGE, null);
   }

   public static void main (String[] args) {
      BejeweledGUI gui = new BejeweledGUI ();
      Bejeweled game = new Bejeweled (gui);
      BejeweledListener listener = new BejeweledListener (game, gui);
   }

}