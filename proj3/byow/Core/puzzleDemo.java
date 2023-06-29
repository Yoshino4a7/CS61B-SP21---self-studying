package byow.Core;

public class puzzleDemo {

    private Position pos;
    private boolean IsWall;
    private boolean IsFloor;


    public static void initial(int[][] world){
        for(int x=0;x<world.length;x++){

            for(int y=0;y<world[x].length;y++)
                world[x][y]=1;


        }




    }


//    puzzleDemo(Position p,boolean IsWall,boolean IsFloor)
//    {
//        this.pos=p;
//        this.IsWall=IsWall;
//        this.IsFloor=IsFloor;
//    }

    public void serachAdjacentWall(){



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

        puzzleDemo.




    }


}
