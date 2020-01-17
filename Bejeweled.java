/**
* Bejeweled.java (Skeleton)
*
* This class represents a Bejeweled (TM)
* game, which allows player to make moves
* by swapping two pieces. Chains formed after
* valid moves disappears and the pieces on top
* fall to fill in the gap, and new random pieces
* fill in the empty slots.  Game ends after a
* certain number of moves or player chooses to 
* end the game.
*/

import java.awt.Color;

public class Bejeweled {

/* 
 * Constants
 */  
   final Color COLOUR_DELETE = Color.RED;
   final Color COLOUR_SELECT = Color.YELLOW;

   final int CHAIN_REQ = 3;	// minimum size required to form a chain
   final int NUMMOVE = 10;		// number of moves to be play in one game
   final int EMPTY = -1; 		// represents a slot on the game board where the piece has disappear  

   final int NUMPIECESTYLE;   // number of different piece style
   final int NUMROW;		  		// number of rows in the game board
   final int NUMCOL;	 	  		// number of columns in the game boar
   final int INUSE = 1;
   final int NOTINUSE = 0;
   boolean pass = false;
/* 
 * Global variables
 */   
   BejeweledGUI gui;	// the object referring to the GUI, use it when calling methods to update the GUI

   int board[][];		// the 2D array representing the current content of the game board

   boolean firstSelection = true;		// indicate if the current selection is the selection of the first piece
   int slot1Row, slot1Col;		// store the location of the first selection

   int score = 0;						// current score of the game
   int numMoveLeft = NUMMOVE;				// number of move left for the game
   int points = 0;
   int beat = 0;
/**************************
 * Constructor: Bejeweled
 **************************/
   public Bejeweled(BejeweledGUI gui) {
      this.gui = gui;
      NUMPIECESTYLE = gui.NUMPIECESTYLE;
      NUMROW = gui.NUMROW;
      NUMCOL = gui.NUMCOL;

      // this initializes the score, number of moves left and the score to beat on the board
      gui.setScore(score);
      gui.setMoveLeft(numMoveLeft);
      gui.setBeat(beat);
   
      // this initializes the board pieces
      initBoard();
   }

   // this is the method that intiialized pieces on the board as random pieces and sets the gui as well
   public void initBoard () {
   
   // this creates the array
      board = new int [NUMROW][NUMCOL];
   // this initializes the board with different pieces
   // it loops through the pieces going down the board then across the board
      for (int i = 0; i < NUMROW; i ++) {
         for (int j = 0; j < NUMCOL; j ++) {
            // this sets the pieces to a random piece
            board [i][j] = randPiece();
            // this sets the gui
            gui.setPiece(i, j, board[i][j]);
         }
      }
      // this automatically deletes any chain formed
      autoDelete();
      // this checks to see if there are any moves on the board. If not, it will reset the board with random pieces
      noMoves(NOTINUSE);
   }
	
// this is a random number generator that generates a number between 0 - 6
   public int randPiece () {
      // this is the number that the method will return
      int random;
      // this finds a random number between 0 and 6
      random = (int)(Math.random() * gui.NUMPIECESTYLE);
      return random;
   }

   // this checks to see if the swap of the pieces is valid. if it is, it will return true, otherwise it will return false
   public boolean valid (int row1, int row2, int col1, int col2) {
      // this finds the absolute value between rows if they are in the same column and columns if they are in the same row
      // as long as the absolute value is 1 between the pieces, it will return true
      if (Math.abs(row1 - row2) == 1 && col1 == col2) {
         return true;
      } 
      else if (Math.abs(col1 - col2) == 1 && row1 == row2) {
         return true;
      } 
      else {
         return false;
      }
   
   }
/*****************************************************
 * play
 * This method is called when a piece is clicked.  
 * Parameter "row" and "column" is the location of the 
 * piece that is clicked by the player
 *****************************************************/
   public void play (int row, int column) {
   
   // this is the first selection by the user 
      if (firstSelection) {
         System.out.println(board[row][column]);
      // this keeps the row of the selected piece in a variable
         slot1Row = row;
      // this keeps the column of the selected piece in a variable
         slot1Col = column;
      // this highlights the selected slot
         gui.highlightSlot(slot1Row, slot1Col, COLOUR_SELECT);
      // this changes first selection to false to know that the second selection will not be "the first selection"
         firstSelection = false;
      // this checks to see if the player clicked on the same piece
      } 
      
      /*
      EXTENSION !!!
      this checks to see if the same piece was selected
      if it was then the piece unhighlights
      */
      
      else if (slot1Row == row && slot1Col == column) {
      // if they did, the slot is unhighlighted
         gui.unhighlightSlot(slot1Row, slot1Col);
      // this makes first Selection true again and allows the player to make another first selection
         firstSelection = true;
        
      // this is the second selection by the user
      } 
      
      else {
      // this highlights the slot selected
         gui.highlightSlot(row, column, COLOUR_SELECT);
      // this changes firstSelection back to true to know that the next selection will be the first
         firstSelection = true;
      // this checks to make sure that the selected pieces are adjacent
         if (!(valid(slot1Row, row, slot1Col, column))) {
         // this shows the message that the move is invalid
            gui.showInvalidMoveMessage();
         // this unhighlights the slots that the player clicked on
            gui.unhighlightSlot(slot1Row, slot1Col);
            gui.unhighlightSlot(row, column);
         // this checks to make sure the pieces make a chain
         } 
         else {
         // this swaps the pieces to see if there were any chains formed
            swap (slot1Row, slot1Col, row, column);
         // this checks to see if the vertical or horizontal chains formed are < CHAIN_REQ
            if (hCheck(slot1Row, slot1Col) < CHAIN_REQ && vCheck(slot1Row, slot1Col) < CHAIN_REQ &&
            hCheck(row, column) < CHAIN_REQ && vCheck(row, column) < CHAIN_REQ) {
            // if they are, the pieces will unswap 
               unUpdateSwap(slot1Row, slot1Col, row, column);
            // this shows that the move is invalid
               gui.showInvalidMoveMessage();
            // this unhighlights the slots selected
               gui.unhighlightSlot(slot1Row, slot1Col);
               gui.unhighlightSlot(row, column);
            // this is entered if the invalid checks are passed
            // this counts the chains formed and highlights the chains
            } 
            else {
               numMoveLeft --;
            // this updates the number of moves left on the board
               gui.setMoveLeft (numMoveLeft);
            
            // this updates the GUI for the two pieces and swaps the pieces
               gui.setPiece(slot1Row, slot1Col, board[slot1Row][slot1Col]);
               gui.setPiece(row, column, board[row][column]);
            
            
               System.out.println("horizontal check " + hCheck(slot1Row, slot1Col));
               System.out.println("vertical check " + vCheck(slot1Row, slot1Col));
               System.out.println("horizontal check touch 2" + hCheck(row, column));
               System.out.println("vertical touch 2" + vCheck(row, column));
            
            // this sets points to 0, so that every time it counts the points, it will not add on
               points = 0;
            
            // this highlights the slots if there is a chain formed, and also counts the points
               deleteChain(slot1Row, slot1Col);
               deleteChain(row, column);
               
            // this shows the message that a chain was formed, as well as the number of points obtained from that 
            // swap
               gui.showChainSizeMessage(points);
            
            // this adds the number of points earned to the score     
               score += points;
            // this updates the score for the GUI
               gui.setScore (score);
            
               for (int i = 0; i < NUMCOL; i ++) {
                  moveDown(i);
               }
            // this unhighlights the slots selected
               gui.unhighlightSlot(slot1Row, slot1Col);
               gui.unhighlightSlot(row, column);
               autoDelete();
               // this checks to see if there are any possible moves left on the board. 
               noMoves(NOTINUSE);
            // this ends the game once numMoveLeft reaches 0
               if (numMoveLeft == 0) {
                  endGame();
               }
            }
         }
      }
   }

   /* 
   EXTENSION:
   this checks to see if the board has any more possible moves
   the parameter is used for hints. if it is INUSE, that is for the hints and it highlight the first possible hint
   if it is NOTINUSE, the method just searches for any possible chains
   */
   public void noMoves (int using) {
      // this checks to see if there are any moves available on the board
      // this initializes it as false, to say that there are currently no moves on the board
      boolean movesAvail = false;
      // these are the required variables for the check
      int tempSwapLEFT, tempSwapRIGHT, tempSwapUP, tempSwapDOWN;
      // this variable is to count how many possible matches there are 
      int areThere = 0;
      
      // this loops through the board to see if there are any matches
      while (!movesAvail) {
         // this loops through the rows
         for (int i = 0; i < NUMROW; i ++) {
            // this loops through the columns
            for (int j = 0; j < NUMCOL; j ++) {
               // this initializes the variables to check the pieces next to the current piece
               tempSwapDOWN = i + 1;
               tempSwapUP = i - 1;
               tempSwapLEFT = j - 1;
               tempSwapRIGHT = j + 1;
               
               // this checks the piece below the current piece being checked
               if (tempSwapDOWN < NUMROW) {
                  // this swaps the two pieces
                  swap (i, j, tempSwapDOWN, j);
                  // this checks to see if any chains are formed vertically or horizontally after the swap
                  if (hCheck(i, j) >= CHAIN_REQ || vCheck(i, j) >= CHAIN_REQ || 
                     hCheck(tempSwapDOWN, j) >= CHAIN_REQ || vCheck(tempSwapDOWN, j) >= CHAIN_REQ) {
                     // this is if the parameter sent in is INUSE, if it is, it will highlight the slot if there is a match
                     if (using == INUSE) {
                        gui.highlightSlot(i, j, COLOUR_SELECT);
                        gui.highlightSlot(tempSwapDOWN, j, COLOUR_SELECT);
                        // this will update the value of using to NOTINUSE, so that the method will not highlight anymore pieces
                        using = NOTINUSE;
                     }
                     // this adds one to areThere if there are/is a chain formed
                     areThere ++;
                  }
                  // this unupdates the swap
                  unUpdateSwap (i, j, tempSwapDOWN, j);
               }
               
               // this checks the piece above the current piece being checked
               if (tempSwapUP >= 0) {
                  // this swaps the two pieces
                  swap (i, j, tempSwapUP, j);
                  // this checks to see if there are any chains formed vertically or horizontally by the pieces
                  if (hCheck(i, j) >= CHAIN_REQ || vCheck(i, j) >= CHAIN_REQ || 
                     hCheck(tempSwapUP, j) >= CHAIN_REQ || vCheck(tempSwapUP, j) >= CHAIN_REQ) {
                     // this is if the parameter sent in is INUSE, if it is, it will highlight the slot if there is a match
                     if (using == INUSE) {
                        gui.highlightSlot(i, j, COLOUR_SELECT);
                        gui.highlightSlot(tempSwapUP, j, COLOUR_SELECT);
                        using = NOTINUSE;
                     }
                     // this adds one to areThere if there is/are a chain formed
                     areThere ++;
                  }
                  // this unupdates the swap
                  unUpdateSwap (i, j, tempSwapUP, j);
               }
               
               // this checks the piece to the left of the current piece being checked
               if (tempSwapLEFT >= 0) {
                  // this swaps the two pieces
                  swap (i, j, i, tempSwapLEFT);
                  // this checks to see if there are any chains formed vertically or horizontally by the pieces
                  if (hCheck(i, j) >= CHAIN_REQ || vCheck(i, j) >= CHAIN_REQ || 
                     hCheck(i, tempSwapLEFT) >= CHAIN_REQ || vCheck(i, tempSwapLEFT) >= CHAIN_REQ) {
                     // this is if the parameter sent in is INUSE, if it is, it will highlight the slot if there is a match
                     if (using == INUSE) {
                        gui.highlightSlot(i, j, COLOUR_SELECT);
                        gui.highlightSlot(i, tempSwapLEFT, COLOUR_SELECT);
                        using = NOTINUSE;
                     }
                     // this adds one to areThere if there are/is a chain formed
                     areThere ++;
                  }
                  // this unupdates the swap
                  unUpdateSwap (i, j, i, tempSwapLEFT);
               }
               
               // this checks the piece that is to the right of the current piece being checked
               if (tempSwapRIGHT < NUMCOL) {
                  // this swaps the two pieces 
                  swap (i, j, i, tempSwapRIGHT);
                  // this checks to see if there are any chains formed vertically or horizontally by the pieces
                  if (hCheck(i, j) >= CHAIN_REQ || vCheck(i, j) >= CHAIN_REQ || 
                     hCheck(i, tempSwapRIGHT) >= CHAIN_REQ || vCheck(i, tempSwapRIGHT) >= CHAIN_REQ) {
                     // this is if the parameter sent in is INUSE, if it is, it will highlight the slot if there is a match
                     if (using == INUSE) {
                        gui.highlightSlot(i, j, COLOUR_SELECT);
                        gui.highlightSlot(i, tempSwapRIGHT, COLOUR_SELECT);
                        using = NOTINUSE;
                     }
                     // this adds one to areThere if there is/are a chain formed
                     areThere ++;
                  }
                  // this unupdates the swap
                  unUpdateSwap (i, j, i, tempSwapRIGHT);
               }
               
            }
         }
         
         // this is to check if there were any matches on the board
         // if there were
         if (areThere > 0) {
            System.out.println("are there : " + areThere);
            // this sets movesAvail as true
            // this allows it to exit the loop and continue the game
            movesAvail = true;
            // this is if there are no moves on the board
         } 
         else {
            
            System.out.println("none");
            
            // this sets all the pieces on the board to -1
            gui.resetGameBoard();
            
            // this sets the board with new random pieces
            initBoard();
            
            // this shows the message that there are no matches and tells the played that the game board was reset
            gui.showResetBoardMessage();
         }
      }
      autoDelete();
   }
   
   /*
   EXTENSION
   this is the method to show a possible match on the board
   if the user clicks on the button, it will call the method noMoves and search for the first possible match
   */
   public void showHint() {
      noMoves(INUSE);
   }
   
   /*
   EXTENSION
   this is the method that automatically deletes chains
   */
   public void autoDelete () {
       // these are the variables required for the automatic chain deleter
      boolean chainCheck = false;
      int chainCounter = 0;
      
      // this loops through the entire board until there are no chains formed without manually selecting them
      while (!(chainCheck)) {
         // this resets the chain counter to 0 to restart the chain counting each time
         chainCounter = 0;
         // this loops through the board
         for (int i = 0; i < NUMROW; i ++) {
            for (int j = 0; j < NUMCOL; j ++) {
               points = 0;
               // this checks to see if a chain is formed
               if (hCheck(i, j) >= CHAIN_REQ || vCheck(i, j) >= CHAIN_REQ) {
                  // this highlights the chain
                  deleteChain(i, j);
                  
                  // this shows the points from each automatic chain delete
                  gui.showChainSizeMessage(points);
                  // this updates the score
                  score += points;
                  // this updates the score on the screen
                  gui.setScore(score);
                  // this will shift the pieces down if there are chains formed
                  for (int r = 0; r < NUMCOL; r ++) {
                     moveDown(r);
                  }
                  // this updates chain counter
                  chainCounter ++;
               }
            }
         }
         
         // if chainCounter is 0, then it will exit autoDelete because there are no more matches that are already there on the board
         if (chainCounter == 0) {
            chainCheck = true;
         }
      }
   }
// this swaps the pieces to see if they make a chain. if they do not, then it goes back to the 
// original position, if it does, then it is swapped permanently
   public void swap (int row1, int col1, int row2, int col2) {
      int piece = board[row2][col2];
      board[row2][col2] = board[row1][col1];
      board[row1][col1] = piece;
   }

// this unupdates the swap if the swap is invalid because it does not form a chain
   public void unUpdateSwap (int row1, int col1, int row2, int col2) {
   // this keeps one of the pieces in a temporary variable
      int piece1 = board[row2][col2];
   
   // this swaps the pieces
      board[row2][col2] = board[row1][col1];
      board[row1][col1] = piece1;
   }

// this checks if there are any vertical matchups. the rows correspond to this which is up and down the
// board. 
   public int vCheck (int row, int col) {
      // this sets a variable to keep the piece that it is checking
      int current = board[row][col];
      int checkTop = row - 1;
      int checkBottom = row + 1;
      // this sets the count to 1 because it takes into account the piece that is being checked but not being checked
      // in the method
      int count = 1;
   
   // this checks to make sure that the piece above is the same as the selected piece
      if (checkTop >= 0 && board[checkTop][col] == current) {
      // if it is the same, this continues to check the pieces above the selected piece and updates count if it 
      // is the same
         while (checkTop >= 0 && board[checkTop][col] == current) {
            count ++;
            checkTop --;
         }
      } 
   
   // this checks to make sure the piece directly below the selected piece is the same
      if (checkBottom < NUMROW && board[checkBottom][col] == current) {   
      // if it is the same, this continues to check the pieces below the selected piece and updates count
      // if it is the same
         while (checkBottom < NUMROW && board[checkBottom][col] == current) {
            count ++;
            checkBottom ++;
         }
      }
   
   // this returns the total for count
      return count;
   }

// this checks if there are any horizontal matchups. the columns correspond to this which is across
// the board. 
   public int hCheck (int row, int col) {
      // this sets a variable for the piece that it is checking to compare it to the other pieces
      int current = board[row][col];
      int checkLeft = col - 1;
      int checkRight = col + 1;
      // this initializes count to 1 because it takes into account the piece that it is checking but is not being checked
      // in the method
      int count = 1;
   
   // this checks the piece to the left of the selected piece to make sure it is the same as the selected piece
      if (checkLeft >= 0 && board[row][checkLeft] == current) {
      // this continues to check the pieces to the left of the piece selected and updates the count if the 
      // piece is the same
         while (checkLeft >= 0 && board[row][checkLeft] == current) {
            count ++;
            checkLeft --;
         }
      }
   
   // this checks the piece to the right of the selected piece to make sure it is the same as the selected piece
      if (checkRight < NUMCOL && board[row][checkRight] == current) {
      // this continues to check the pieces to the right of the piece selected and updates the count if the 
      // piece is the same
         while (checkRight < NUMCOL && board[row][checkRight] == current) {
            count ++;
            checkRight ++;
         }
      }
   
   // this returns the total for count
      return count;
   }

   // this is the delete chain method
   public void deleteChain (int row, int col) {
      // this sets that there is not a chain formed yet
      boolean delete = false;
      // this sets the piece being checked
      int current = board[row][col];
      // this sets variables for counting up and down to check for the chains
      int hTop = row - 1;
      int hBottom = row + 1;
      int hLeft = col - 1;
      int hRight = col + 1;
      // this sets that there is no vertical chain or horizontal chain
      boolean vertical = false;
      boolean horizontal = false;
      
      // this checks to make sure the pieces form a chain greater than 2 before highlighting anything
      if (hCheck(row, col) >= CHAIN_REQ) {
         // this sets the pieces to the column to the left or right
         hLeft = col - 1;
         hRight = col + 1;
         
         // if there is a chain formed, then it sets that there was a delete chain
         delete = true;
         // this checks to see if the piece to the left is the same as the selected piece
         if (hLeft >= 0 && board[row][hLeft] == current) {
            // this sets the variables to check up and down the horizontal pieces
            hTop = row - 1;
            hBottom = row + 1;
            
            // this keeps checking the pieces to the left of the selected piece
            while (hLeft >= 0 && board[row][hLeft] == current) {
               hTop = row - 1;
               hBottom = row + 1;
               // if there is a verical chain formed in the horizontal checks, vertical will be set to true
               if (vCheck(row, hLeft) >= CHAIN_REQ) {
                  vertical = true;
               } else {
                  vertical = false;
               }
               System.out.println("hLeft ver: " + vertical);
               // this deletes the current piece in the next column that is being checked
               gui.highlightSlot(row, hLeft, COLOUR_DELETE);
               // this sets the current piece checked to empty
               board[row][hLeft] = EMPTY;
               points ++;
               // this checks the pieces above and sets them to empty if they are part of the chain
               while (vertical && hTop >= 0 && board[hTop][hLeft] == current) {
                  // if the piece equals the current piece being checked, it will highlihgt and delete the piece
                  gui.highlightSlot(hTop, hLeft, COLOUR_DELETE);
                  board[hTop][hLeft] = EMPTY;
                  points ++;
                  // this continues the search upwards
                  hTop --;
               }
               while (vertical && hBottom < NUMROW && board[hBottom][hLeft] == current) {
                  // if the piece equals the current piece being checked, it will highlihgt and delete the piece
                  gui.highlightSlot(hBottom, hLeft, COLOUR_DELETE);
                  board[hBottom][hLeft] = EMPTY;
                  points ++;
                  // this continues the search downwards
                  hBottom ++;
               }
               // this continues the search to the left
               hLeft --;
            }
         }
         
         // this checks to see if the piece to the right is the same as the piece selected
         if (hRight < NUMCOL && board[row][hRight] == current) {
            hTop = row - 1;
            hBottom = row + 1;
            
            // this keeps checking the pieces to the right of the selected piece
            while (hRight < NUMCOL && board[row][hRight] == current) {
               hTop = row - 1;
               hBottom = row + 1;
               // this sets vertical to true if there is a vertical chian to the right
               if (vCheck(row, hRight) >= CHAIN_REQ) {
                  vertical = true;
               } else {
                  vertical = false;
               }
               System.out.println("hRight ver : " + vertical);
               // this deletes the piece being checked
               gui.highlightSlot(row, hRight, COLOUR_DELETE);
               board[row][hRight] = EMPTY;
               points ++;
               while (vertical && hTop >= 0 && board[hTop][hRight] == current) {
                  // if the piece equals the current piece being checked, it will highlight and delete the piece
                  gui.highlightSlot(hTop, hRight, COLOUR_DELETE);
                  board[hTop][hRight] = EMPTY;
                  // this updates the points
                  points ++;
                  hTop --;
               }
               while (vertical && hBottom < NUMROW && board[hBottom][hRight] == current) {
                  // if the piece equals the current piece being checked, it will highlight and delete the piece
                  gui.highlightSlot(hBottom, hRight, COLOUR_DELETE);
                  board[hBottom][hRight] = EMPTY;
                  // this updates the points
                  points ++;
                  hBottom ++;
               }
               // this continues the check to the right
               hRight ++;
            }
         }
         
      }  
      
      // this checks to see if there are any vertical chains formed
      if (vCheck(row, col) >= CHAIN_REQ) {
         hBottom = row + 1;
         hTop = row - 1;
         delete = true;
         // this checks to see if the piece to the left is the same as the selected piece
         if (hTop >= 0 && board[hTop][col] == current) {
            hLeft = col - 1;
            hRight = col + 1;
            
            // this keeps checking the pieces to the left of the selected piece
            while (hTop >= 0 && board[hTop][col] == current) {
               hLeft = col - 1;
               hRight = col + 1;
               
               // if the pieces on top have horizontal chains formed, then horizontal is set to true
               if (hCheck(hTop, col) >= CHAIN_REQ) {
                  horizontal = true;
               } else {
                  horizontal = false;
               }
               System.out.println("hTop hor : " + horizontal);
               // this deletes and highlights the piece being checked
               gui.highlightSlot(hTop, col, COLOUR_DELETE);
               board[hTop][col] = EMPTY;
               points ++;
               while (hLeft >= 0 && horizontal && board[hTop][hLeft] == current) {
                  // if the piece equals the current piece being checked, it will highlight and delete the piece
                  gui.highlightSlot(hTop, hLeft, COLOUR_DELETE);
                  board[hTop][hLeft] = EMPTY;
                  // this updates the points
                  points ++;
                  hLeft --;
               }
               while (hRight < NUMROW && horizontal && board[hTop][hRight] == current) {
                  // if the piece equals the current piece being checked, it will highlight and delete the piece
                  gui.highlightSlot(hTop, hRight, COLOUR_DELETE);
                  board[hTop][hRight] = EMPTY;
                  // this updates the points
                  points ++;
                  hRight ++;
               }
               // this continues to check upwards
               hTop --;
            }
         }
         
         // this checks to see if the piece to the right is the same as the piece selected
         if (hBottom < NUMCOL && board[hBottom][col] == current) {
            hLeft = col - 1;
            hRight = col + 1;
            
            // this keeps checking the pieces to the right of the selected piece
            while (hBottom < NUMCOL && board[hBottom][col] == current) {
               hRight = col + 1;
               hLeft = col - 1;
               // if there is a horizontal chain below the piece being checked, it will set horizontal to true
               if (hCheck(hBottom, col) >= CHAIN_REQ) {
                  horizontal = true;
               } else {
                  horizontal = false;
               }
               System.out.println("hBOT hor: " + horizontal);
               // this sets the pieces being checked to EMPTY and hightlights it
               gui.highlightSlot(hBottom, col, COLOUR_DELETE);
               board[hBottom][col] = EMPTY;
               points ++;
               while (hLeft >= 0 && horizontal && board[hBottom][hLeft] == current) {
                  // if the piece equals the current piece being checked, it will highlight and delete the piece
                  gui.highlightSlot(hBottom, hLeft, COLOUR_DELETE);
                  board[hBottom][hLeft] = EMPTY;
                  // this updates the points
                  points ++;
                  hLeft --;
               }
               while (hRight < NUMROW && horizontal && board[hBottom][hRight] == current) {
                  gui.highlightSlot(hBottom, hRight, COLOUR_DELETE);
                  // if the piece equals the current piece being checked, it will highlight and delete the piece
                  board[hBottom][hRight] = EMPTY;
                  // this updates the points
                  points ++;
                  hRight ++;
               }
               // this continues to check below
               hBottom ++;
            }
         }
      }
      // if there was any detection of a chain, it sets the piece being checked to EMPTY and highlights it to delete
      if (delete) {
         gui.highlightSlot(row, col, COLOUR_DELETE);
         board[row][col] = EMPTY;
         // this updates the points
         points ++;
      }
   }
// this moves the pieces down the board according to the number of slots it needs to move down
// if the piece is EMPTY, the counter will count how many empty slots are in the column
// if the piece minus count is less than 0, it will fill it with a new random piece
// if the piece minus count is greater than or equal to 0, it will move the piece that corresponds to the
// count down to that spot
   public void moveDown (int col) {
      int count = 0;
      int temp = NUMROW - 1;
      int down = 0;
      
      // this counts the number of EMPTY SLOTS there are in that certain column
      // it will keep going down the row until it reaches the end of the column
         while (down < NUMROW) {
            // if the piece is empty, it will count
            if (board[down][col] == EMPTY) {
               count ++;
            }
         // if it passes all of the EMPTY SLOTS, it keeps the last empty slot row number in a variable
            if (down > 0 && board[down][col] != EMPTY && board[down - 1][col] == EMPTY 
            || board[down][col] == EMPTY && down == NUMROW - 1) {
               if (board[down][col] == EMPTY) {
                  temp = NUMROW - 1;
               } else {
                  temp = down - 1;
               }
               // this moves the chain down according to the number of EMPTY slots there are
            // it uses the last EMPTY slot as the number of pieces it must replace
               for (int i = temp; i >= 0; i --) {
               // if the row number minus the number of counted pieces is greater than or equal to 0, it will replace
               // the slot with a piece that is count pieces above it
                  if (i - count >= 0) {
                     board[i][col] = board[i - count][col];
                  // this updates the graphics
                     gui.setPiece(i, col, board[i][col]);
                  // this unhighlights the slots
                     gui.unhighlightSlot(i, col);
                  // if the row number minus the number of counted pieces is less than 0, it will be replaced with a random 
                  // generated piece
                  } 
                  else {
                     board[i][col] = randPiece();
                  // this updates the graphics
                     gui.setPiece(i, col, board[i][col]);
                  // this unhighlights the slot
                     gui.unhighlightSlot(i, col);
                  }
               }
               // this sets count to 0 to keep checking the column
               count = 0;
            }
            // this continues to move count down
               down ++;
         }
   
   }
   
   /*
   EXTENSION
   if the user wants to change the number of moves this will check to see what to do
   */
   public void changeMoveNumber () {
      gui.showChangeMovesMessage(); 
      // if a number was input, it will restart the game
      if (gui.moves != null) {
         numMoveLeft = gui.newMoves;
         score = 0;
         gui.setScore(score);    
         gui.showRestartGameMessage();
         initBoard();
      }
      // this updates the moves on the board
      gui.setMoveLeft(numMoveLeft);
      // if they input 0, it will end the game
      if (numMoveLeft == 0) {
         endGame();
      }
   }
   
   /*
   EXTENSION
   this is if the user wants to change the number of points to beat
   */
   public void changeBeatScore () {
   // this shows the message if they want to chnage the score to beat
      gui.showChangeBeatMessage();
      // if the score to beat is not nothing, then it will update the score and number of pieces and the board
      if (gui.toBeat != null) {
         beat = gui.newBeat;
         numMoveLeft = NUMMOVE;
         gui.setMoveLeft(numMoveLeft);
         score = 0;
         gui.setScore(score);
         gui.showRestartGameMessage();
         initBoard();
      }
      // this updates the gui
      gui.setBeat(beat);
   }
/*****************************************************
 * endGame
 * This method is called when the player clicks on the
 * "End Game" button
 *****************************************************/
   public void endGame() {
   // if they beat the score, pass will be true
      if (score >= beat) {
         pass = true;
      }
      // if nothing was input to change the number of moves, the number of moves will remain NUMMOVE
      if (gui.moves == null) {
         System.out.println(NUMMOVE + " + " + numMoveLeft);
         // this outputs the message with score, number of moves and if they succeeded or not
         gui.showGameOverMessage(score, NUMMOVE - numMoveLeft, pass);
         // if something was input, this will take the number of moves input, and subtract the number of moves left
      } else if (gui.newMoves > 0 || gui.newMoves == 0) {
         System.out.println("end " + gui.newMoves);
         // this outputs the message
         gui.showGameOverMessage(score, gui.newMoves - numMoveLeft, pass);
      } 
      
      // if they want to restart the same, they select that they want to and it will reset the board to the beginning
      if (gui.showReplayGameMessage() == 1) {
         // it will show that the board reset
         gui.showRestartGameMessage();
         // this resets the score and number of moves
         score = 0;
         numMoveLeft = NUMMOVE;
         beat = 0;
         gui.setScore(score);
         gui.setMoveLeft(numMoveLeft);
         gui.setBeat(beat);
         initBoard();
      }
   }

}