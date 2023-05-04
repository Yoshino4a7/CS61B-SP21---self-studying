package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean[] oneColmove (int col,int row,Tile t,boolean Merge){
        int value;
        int final_row=row;
        boolean   is_Merge=false;
        boolean   is_Changed=false;
        boolean[] output=new boolean[2];
        for(int j=row;j<board.size();j++) {
            if(j>=3)
                break;
            else
            {

                Tile up_t=board.tile(col,j+1);
                if(up_t==null||(up_t!=null&&adjacentValueCompare(t,up_t)))
                {
                    if(adjacentValueCompare(t,up_t))
                    {
                        if(Merge)
                            break;
                        score+=up_t.value()+t.value();
                        is_Merge=true;
                        output[0]=is_Merge;
                    }



                    if(j<3)
                        final_row++;
                    else
                    {

                        final_row=board.size()-1;

                    }




                }
                else

                    break;

            }



    }
        board.move(col,final_row,t);
    if(row!=final_row)
    {
        is_Changed=true;
        output[1]=is_Changed;
    }




return output;
    }



    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        boolean is_Merge=false;
boolean[] tilt_change=new boolean[2];
        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        switch(side)
        {
            case NORTH:
                board.setViewingPerspective(Side.NORTH);
                break;
            case EAST:
                board.setViewingPerspective(Side.EAST);
                break;
            case SOUTH:
                board.setViewingPerspective(Side.SOUTH);
                break;
            case WEST:
                board.setViewingPerspective(Side.WEST);
                break;
        }

if((atLeastOneMoveExists(this.board))&&!maxTileExists(this.board))
{

    //i表示列


    for(int i=0;i<board.size();i++)
    {
        is_Merge=false;
        for(int j=board.size()-1;j>=0;j--)
        {
            if(board.tile(i,j)!=null)
            {
                Tile t=board.tile(i,j);
                tilt_change= oneColmove (i,j,t,tilt_change[0]);

if(tilt_change[1])
                changed=true;


            }



        }
    }

}
        board.setViewingPerspective(Side.NORTH);


        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        //b表示2048的面板，这个面板抽象成一个二维数组，b.size()相当于获得这个二维数组的第一维长度4（4*4矩阵）
        //二维数组相当于存了四个数组指针，每一个指针又指向一个有四个元素的数组
        for(int i=0;i<b.size();i++)


        {
            for(int j=0;j<b.size();j++)
            {
                if(b.tile(i,j)==null)
                return true;


            }
        }
        return false;


    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for(int i=0;i<b.size();i++)


        {
            for(int j=0;j<b.size();j++)
            {
                if(b.tile(i,j)==null)
                    continue;
                //若面板的某一格没有Tile对象，则需要忽略，不然会引起报错（不是程序逻辑问题，而是异常）
                if(b.tile(i,j).value()==MAX_PIECE)
                    return true;


            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean adjacentValueCompare(Tile tile1,Tile tile2){
    if(tile1!=null&&tile2!=null)
    {
        if(tile1.value()== tile2.value())
            return true;
        else
            return false;
    }
    return false;
    }

    public static boolean adjacentCompare(Board b ,int i ,int j ,int up ,int down,int left,int right){
        if(b.tile(i,j)!=null) {
            if(adjacentValueCompare(b.tile(i,j),b.tile(i,up))&&up!=0)
                return true;
            if(adjacentValueCompare(b.tile(i,j),b.tile(i,down))&&down!=b.size()-1)
                return true;
            if(adjacentValueCompare(b.tile(i,j),b.tile(left,j))&&left!=0)
                return true;
            if(adjacentValueCompare(b.tile(i,j),b.tile(right,j))&&right!=b.size()-1)
                return true;

        }
        return false;
    }
    public static int[] computeAdjacent(Board b,int i ,int j ){
       int [] output=new int [4];
       int up,down,left,right;


        up=j-1;
        if(up<0)
            up=0;
        down=j+1;
        if(down>=b.size())
            down=b.size()-1;

        left=i-1;
        if(left<0)
            left=0;
        right=i+1;
        if(right>=b.size())
            right=b.size()-1;
        output[0]=up;
        output[1]=down;
        output[2]=left;
        output[3]=right;
        return output;
    }

    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        boolean existSameValue=false;

        int [] adjacent;

        for(int i=0;i<b.size();i++)

//i是列，是col
        {
            for(int j=0;j<b.size();j++) {
//j是行,是row

                adjacent=computeAdjacent(b,i,j);
                if(adjacentCompare(b , i ,j ,adjacent[0] ,adjacent[1],adjacent[2],adjacent[3]))
                    existSameValue=true;
            }
        }
        if((existSameValue)||maxTileExists(b)||(emptySpaceExists(b)))
            return true;
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
