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
        //1.选定当前的路
        //2.获得当前路四周一格的墙壁，并将墙壁加到列表中  serachWall()
        //while(墙壁>0)
//        {
            //3.随机将列表中的一个墙出队，
            //4.坐标移动到与当前路相隔出队墙的路 Position searchRoad()
            //if(这个坐标存在)
            //{
//             5. generate_helper(int[][] world,Position p)
            //}


//        } 





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
