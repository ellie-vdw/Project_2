/**
* <h1>BoardPrinterWrapper</h1>
* <p>The BoardPrinterWrapper is a class that handles the
*     BoardPrinter class. It makes it easier to print the boards.</p>
* <p>
*  <p> <b> Update: </b> </p>
* <p>        20200910
*            This class no longer supports multiplexing of the boards,
*            in accordance to the specifications and requriements.
*            It also no longer is implemeted from BoardInterface class.
*            The file has been renamed to BoardPrinterWrapper.</p>
* <p> <b> Requried Files: </b> </p>
* <ul>
*  <li> Board.java </li>
* </ul>
*/
public class BoardPrinterWrapper{
  private int xOffset;
  private int yOffset;
  private int indexOffset;

  private char ship_marker;
  private char board_marker;
  private boolean indexed;

  private Board g;
  private Board ui;

  /**
  * This is the constructor, it creates a new board with offsets
  * @Pre Must have valid Board Object and characters and a indexed boolean
  * @Post Creates a board with new offsets for output
  * @param t_g - Board
  * @param t_ship_marker - char
  * @param t_board_marker - char
  * @param t_indexed - Boolean
  */
  public BoardPrinterWrapper(Board t_g, char t_ship_marker, char t_board_marker, boolean t_indexed){
    this.xOffset = 1;
    this.yOffset = 1;
    this.indexOffset = 1;
    this.ship_marker = t_ship_marker;
    this.board_marker = t_board_marker;
    this.indexed = t_indexed;

    this.g = t_g;
    this.ui = new Board(g.getXSize() + this.indexOffset + 2 * this.xOffset, g.getYSize() + 2 * this.indexOffset + this.yOffset, this.board_marker, g.getNumberOfShips(), g.getName());

  }

  /**
  * This is a private method. It updates the offset Board with the current board
  * @Pre Must have been constructed
  * @Post Updates offset board with new g board
  * @param none
  * @return void
  */
  private void addBoard(){
      int xInput = 0;
      int yInput = 0;

      char x_ui = '0';
      char y_ui = 'A';

      xInput = this.xOffset + (0 * (this.xOffset + g.getXSize())) + (0+1) * this.indexOffset;
      yInput = this.yOffset + (0 * (this.yOffset + g.getYSize())) + (0+1) * this.indexOffset;
      for(int i = -1; i < g.getYSize(); i++){
        for(int k = -1; k < g.getXSize(); k++){
          if(i == -1 && indexed){
          ui.addMarker(x_ui, k + xInput, i + yInput);
            x_ui ++;
          }
          else if(k == -1 && indexed){
           ui.addMarker(y_ui, k + xInput, i + yInput);
            y_ui++;
          }
        }
      }

      xInput = this.xOffset + (0 * (this.xOffset + g.getXSize())) + (0+1) * this.indexOffset;
      yInput = this.yOffset + (0 * (this.yOffset + g.getYSize())) + (0+1) * this.indexOffset;
      for(int i = 0; i < g.getYSize(); i++){
        for(int k = 0; k < g.getXSize(); k++){
            ui.addMarker(g.getMarker(k, i), xInput + k, yInput + i);
        }
      }
  }


  /**
  * This adds element to the board. It does not add elements to the given board
  * @Pre Must have character and valid address
  * @Post Alters the offset board, temp.
  * @param marker - char
  * @param x - int
  * @param y - int
  */
  public void addElement(char marker, int x, int y){
    this.ui.addMarker(marker, x, y);
  }

  /**
  * This retunrs a element of the board at a given address
  * @Pre Must have valid address
  * @Post Gets an element from the offset board
  * @param x - int
  * @param y - int
  * @return char
  */
  public char getElement(int x, int y){
    return this.ui.getMarker(x, y);
  }

  /**
  * This returns the offsetBoard
  * @Pre Must have been constructed
  * @Post Returns a copy of the board by a new Board Object
  * @param copy - Board
  * @return Board offsetBoard
  */
  public Board getCopyBoard(Board copy){
    copy = new Board(ui.getXSize(), ui.getYSize(), this.board_marker, copy.getNumberOfShips(), copy.getName());
    for(int i = 0; i < ui.getYSize(); i++){
      for(int k = 0; k < ui.getXSize(); k++){
        copy.addMarker(ui.getMarker(k , i), k, i);
      }
    }
    return copy;
  }

  /**
  * This uses the print function to print a normal board or attack board
  * @Pre Must have valid boolean character and constructed
  * @Post Prints the board, this is the output.
  * @param t_hidden - boolean
  * @return void
  */
  public void print(boolean t_hidden){
    this.addBoard();
    if(t_hidden){
      BoardPrinter.printAndRemove(this.ui.getCopyBoard(ui), this.ship_marker);
    }
    else{
      BoardPrinter.printBoard(this.ui.getCopyBoard(ui));
    }
  }

}
