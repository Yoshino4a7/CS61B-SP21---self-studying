package byow.Core;

import java.awt.*;
import java.util.Queue;

public class puzzleDemo {



    private int border_x_left;
    private int border_x_right;
    private int border_y_up;
    private int border_y_down;


    public  void initial(int[][] world){
        for(int x=0;x<world.length;x++){

            for(int y=0;y<world[x].length;y++)
                world[x][y]=1;

        }
        border_x_left=0;
        border_x_right=world.length;
        border_y_up=0;
        border_y_down=world[0].length;

    }

    public void generateWorld(int[][] world){






    }

    public void generate_helper(int[][] world,Position p)
    {
        List l=serachAdjacentWall(p);





    }



//    puzzleDemo(Position p,boolean IsWall,boolean IsFloor)
//    {
//        this.pos=p;
//        this.IsWall=IsWall;
//        this.IsFloor=IsFloor;
//    }

    public List serachAdjacentWall(Position p){


            return L;
    }


    public static void printWorld(int[][] world){

        for(int x=0;x<world.length;x++){

            for(int y=0;y<world[x].length;y++)
                System.out.print(world[x][y]);
            System.out.println("");


        }


    }







    public class Position{
        private int x;
        private int y;

        Position(int x,int y){

            this.x=x;
            this.y=y;


        }

        public void shift(int dx,int dy){
            this.x=this.x+dx;
            this.y=this.y+dy;
        }



    }


    public static void main(String[] args) {
        int[][] world=new int[9][9];
        puzzleDemo pz=new puzzleDemo();
        pz.initial(world);
        puzzleDemo.printWorld(world);




    }


}
