   import javax.swing.*;
   import java.awt.event.*;
   import java.awt.Component;
	
   public class BejeweledListener implements MouseListener
   {
      private BejeweledGUI gui;
      private Bejeweled game;
      public BejeweledListener (Bejeweled game, BejeweledGUI gui) {
         this.game = game;
         this.gui = gui;
         gui.addListener (this);
      }
      
      public void mouseClicked (MouseEvent event) {
        Component objectClicked = event.getComponent();
         if (objectClicked instanceof JLabel) {
            JLabel label = (JLabel) objectClicked;	
            int row = gui.getRow(label);
            int column = gui.getColumn (label);
            game.play(row, column); 
         } else if (objectClicked instanceof JButton) {
            // this checks the individual buttons that were clicked
            // this checks for number of moves
            if(gui.changeMovesButton == event.getSource()){
               game.changeMoveNumber();
            // this checks the hints button
            } else if (gui.showHintButton == event.getSource()) {
               game.showHint();
            // this checks the beat score button
            } else if (gui.changeBeatButton == event.getSource()) {
               game.changeBeatScore();
            // this checks the end game button
            } else if (gui.endGameButton == event.getSource()){
               game.endGame(); 
            }
         }
         
      
      }
   
      public void mousePressed (MouseEvent event) {
      }
   
      public void mouseReleased (MouseEvent event) {
      }
   
   
      public void mouseEntered (MouseEvent event) {
      }
   
      public void mouseExited (MouseEvent event) {
      }
   }