package byow.Core;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Random;

public class puzzleDemo {



    private int border_x_left;
    private int border_x_right;
    private int border_y_up;
    private int border_y_down;
    private static final long SEED = 2873125;
    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;
    private static final Random RANDOM = new Random(SEED);

    private boolean[][] road_map=new boolean[WIDTH][HEIGHT];

    public  void initial(int[][] world){

        border_x_left=1;
        border_x_right=world.length-2;
        border_y_up=world[0].length-2;
        border_y_down=1;


        for(int x=0;x<world.length;x++){

            for(int y=0;y<world[x].length;y++)
            {
                Position p=new Position(x,y);
                road_map[p.x][p.y]=false;
                if(!IsIntheArea(p))
                    world[x][y]=1;
                else{
                    if(y%2==0)
                        world[x][y]=1;
                    if(x%2==0)
                        world[x][y]=1;
                //其中(x%2==0&&y%2==0)&&(IsIntheArea(p))条件满足的位置是不可破坏的墙


                }

            }

        }
    }
    public boolean IsIntheArea(Position p){

        if((p.x<border_x_left||p.x>border_x_right||p.y>border_y_up||p.y<border_y_down))
        return false;
        else
            return true;

    }
    public boolean wallCanDestroy(Position p){

        if((p.x%2==0&&p.y%2==0)&&(IsIntheArea(p)))
            return false;
        else
            return true;

    }



    public  int[][] generateWorld(int[][] world){

        Position start = new Position(1,1);
        generate_helper(world,start);

        return world;


    }

    public void generate_helper(int[][] world,Position p)
    {
        ArrayList<Position> L=serachAdjacentWall(p,world);
        road_map[p.x][p.y]=true;
        //1.选定当前的路
        //2.获得当前路四周一格的墙壁，并将墙壁加到列表中  serachWall()

        while(!L.isEmpty()){
            int random_i=RANDOM.nextInt(L.size());
            Position wall=L.get(random_i);

            //3.随机将列表中的一个墙出队，打通
            Position next_road=searchRoad(wall,world);
            //4.坐标移动到与当前路相隔出队墙的路 Position searchRoad()
            if(next_road!=null){
                L.remove(random_i);
                world[wall.x][wall.y]=0;
                generate_helper(world,next_road);
            }
            else{
                L.remove(random_i);
            }

            //5.递归

        }



    }



//    puzzleDemo(Position p,boolean IsWall,boolean IsFloor)
//    {
//        this.pos=p;
//        this.IsWall=IsWall;
//        this.IsFloor=IsFloor;
//    }

    public  ArrayList serachAdjacentWall(Position p,int[][] world){
        ArrayList L=new ArrayList<Position>();

        //检查该位置p的上一格，下一格，左一格，右一格是否存在可破坏的墙
        //其中(x%2==0&&y%2==0)&&(IsIntheArea(p))条件满足的位置是不可破坏的墙
        Position p_left=p.shift(-1,0);
        Position p_right=p.shift(1,0);
        Position p_down=p.shift(0,-1);
        Position p_up=p.shift(0,1);

        if(IsIntheArea(p_left)&&wallCanDestroy(p_left))
        L.add(p_left);
        if(IsIntheArea(p_right)&&wallCanDestroy(p_right))
            L.add(p_right);
        if(IsIntheArea(p_down)&&wallCanDestroy(p_down))
            L.add(p_down);
        if(IsIntheArea(p_up)&&wallCanDestroy(p_up))
            L.add(p_up);





            return L;
    }

    public Position searchRoad(Position wall,int[][] world){
        ArrayList<Position> L=new ArrayList<>();
        //寻找与这个墙壁的相邻一格的路（与已选择的路相隔这个墙壁）
        //随机选择一个没有被访问过的路，返回作为下一次递归的起点
        Position p_left=wall.shift(-1,0);
        Position p_right=wall.shift(1,0);
        Position p_down=wall.shift(0,-1);
        Position p_up=wall.shift(0,1);

            if(IsIntheArea(p_left)&&!road_map[p_left.x][p_left.y]&&!isWall(p_left,world)&&wallCanDestroy(wall))
                L.add(p_left);


            if (IsIntheArea(p_right) &&!road_map[p_right.x][p_right.y]&& !isWall(p_right, world)&&wallCanDestroy(wall))
                L.add(p_right);



        if(IsIntheArea(p_down)&&!road_map[p_down.x][p_down.y]&&!isWall(p_down,world)&&wallCanDestroy(wall))
            L.add(p_down);



            if (IsIntheArea(p_up) && !road_map[p_up.x][p_up.y] && !isWall(p_up, world)&&wallCanDestroy(wall))
                L.add(p_up);
        if(L.isEmpty())
        return null;
        else{
            int random_i=RANDOM.nextInt(L.size());
            return L.get(random_i);

        }

    }

    public static void printWorld(int[][] world){

        for(int x=0;x<world.length;x++){

            for(int y=0;y<world[x].length;y++)
                System.out.print(world[x][y]);
            System.out.println("");


        }


    }

    public boolean isWall(Position p,int[][] world){

        if(p==null)
            return false;


        if(world[p.x][p.y]==1)
            return true;
        else
            return false;

    }





    public class Position{
        private int x;
        private int y;

        Position(int x,int y){

            this.x=x;
            this.y=y;


        }

        public Position shift(int dx, int dy){
           Position p=new Position(x+dx,y+dy);
            return p;
        }



    }


    public static void main(String[] args) {
        int[][] world=new int[WIDTH][HEIGHT];
        puzzleDemo pz=new puzzleDemo();
        pz.initial(world);
        puzzleDemo.printWorld(world);
        System.out.println();
        pz.generateWorld(world);
        puzzleDemo.printWorld(world);

    }


}
