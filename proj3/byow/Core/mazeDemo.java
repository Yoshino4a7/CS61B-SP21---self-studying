package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class mazeDemo {
    public static final int WIDTH = 70;
    public static final int HEIGHT = 50;

    public static final int MAP_WIDTH = 61;
    public static final int MAP_HEIGHT = 45;

    public static final int STARTX = 3;
    public static final int STARTY= 3;

    private static final int border_x_left=1+STARTX;
    private static final int border_x_right=MAP_WIDTH-2+STARTX ;
    private static final int  border_y_up=MAP_HEIGHT-2+STARTY;
    private static final int  border_y_down=1+STARTY;

    private static final TETile WALL = Tileset.TREE;
    private static final TETile ROAD = Tileset.FLOOR;
    private static final TETile NOTHING=Tileset.NOTHING;



    public static void initial(TETile[][] world,boolean[][] road_map,Position start,long seed){
        clearArea(world);
        Random rand=new Random(seed);

        for(int x= start.x;x<start.x+MAP_WIDTH;x++){

            for(int y=start.y;y<start.y+MAP_HEIGHT;y++)
            {
                Position p=new Position(x- start.x,y-start.y);//相对坐标
                Position p_a=new Position(x,y);//绝对坐标
                road_map[p.x][p.y]=false;
                //road_map是相对坐标，world是绝对坐标，得到的wall和road应当是绝对坐标
                //相对坐标x=绝对坐标x-STARTX;
                //相对坐标y=绝对坐标y-STARTY;
                if(!IsIntheArea(p_a,start))
                    world[x][y]=WALL;
                else{
                    if((y-STARTY)%2==0||(x-STARTX)%2==0)
                        world[x][y]=WALL;
                   else
                        world[x][y]=ROAD;
                    //其中(x%2==0&&y%2==0)&&(IsIntheArea(p))条件满足的位置是不可破坏的墙


                }

            }

        }


        generateWorld_block(world,road_map,start,rand);
        generateWorld(world,road_map,start,rand);


        generate_helper_DFS(world);
//        world[S[0].x+1][S[0].y]=WALL;
//        world[S[0].x-1][S[0].y]=WALL;
//        world[S[1].x+1][S[1].y]=WALL;
//        world[S[1].x-1][S[1].y]=WALL;
        clearRoad(world,road_map);

//        generate_nothing_block(world,start);
    }

    public static TETile[][] generateWorld_block(TETile[][] world,boolean[][] road_map,Position S,Random rand){

        Position start = new Position(1+STARTX,1+STARTY);
        generate_helper_block(world,road_map,start,rand);


        return world;


    }
    public static void generate_helper_DFS(TETile[][] world){

        Position S=new Position(STARTX,STARTY);
        int i=0;
        while(i<50){
            for(int x=0;x<WIDTH;x++)
                for(int y=0;y<HEIGHT;y++){
                    if(isROAD(new Position(x,y),world)){
                        S=new Position(x,y);
                        Position p_left=S.shift(-1,0);
                        Position p_right=S.shift(1,0);
                        Position p_up=S.shift(0,1);
                        Position p_down=S.shift(0,-1);
                        ArrayList<Position> wall_list=new ArrayList<>();

                        if(isWall(p_left,world))
                            wall_list.add(p_left);
                        if(isWall(p_right,world))
                            wall_list.add(p_right);
                        if(isWall(p_up,world))
                            wall_list.add(p_up);
                        if(isWall(p_down,world))
                            wall_list.add(p_down);


                        if(wall_list.size()>=3)
                        {

                            world[x][y]=WALL;
                        }

                    }

                }
            i++;
        }
        ArrayList<Position> wall_add_list=new ArrayList<>();
        for(int x=0;x<WIDTH;x++)
            for(int y=0;y<HEIGHT;y++){
                if(isWall(new Position(x,y),world)){
                    S=new Position(x,y);
                    Position p_left=S.shift(-1,0);
                    Position p_right=S.shift(1,0);
                    Position p_up=S.shift(0,1);
                    Position p_down=S.shift(0,-1);
                    ArrayList<Position> wall_list=new ArrayList<>();

                    if(isROAD(p_left,world))
                        wall_list.add(p_left);
                    if(isROAD(p_right,world))
                        wall_list.add(p_right);
                    if(isROAD(p_up,world))
                        wall_list.add(p_up);
                    if(isROAD(p_down,world))
                        wall_list.add(p_down);


                    if(wall_list.size()==0)
                    {

                        world[x][y]=Tileset.NOTHING;
                    }
                   wall_list=new ArrayList<>();
                    if(isWall(p_left,world))
                        wall_list.add(p_left);
                    if(isWall(p_right,world))
                        wall_list.add(p_right);
                    if(isWall(p_up,world))
                        wall_list.add(p_up);
                    if(isWall(p_down,world))
                        wall_list.add(p_down);


                    if(world[x][y].equals(Tileset.NOTHING))
                    {
                        if(!IsIntheArea(new Position(x,y),new Position(STARTX,STARTY))&&wall_list.size()>=2)
                        wall_add_list.add(new Position(x,y));
                        else if(wall_list.size()>=4)
                            wall_add_list.add(new Position(x,y));

                    }



                }

            }
        for(int x=0;x<WIDTH;x++)
            for(int y=0;y<HEIGHT;y++){
                S=new Position(x,y);
                if(isNOTHING(S,world)&&IsIntheArea(S,new Position(STARTX,STARTY))){
                    Position p_left=S.shift(-1,0);
                    Position p_right=S.shift(1,0);
                    Position p_up=S.shift(0,1);
                    Position p_down=S.shift(0,-1);
                    ArrayList<Position> wall_list=new ArrayList<>();
                    if(isWall(p_left,world))
                        wall_list.add(p_left);
                    if(isWall(p_right,world))
                        wall_list.add(p_right);
                    if(isWall(p_up,world))
                        wall_list.add(p_up);
                    if(isWall(p_down,world))
                        wall_list.add(p_down);

                    if(wall_list.size()>=3)
                    {

                        world[x][y]=Tileset.MOUNTAIN;
                    }

                }
            }

        for(int x=0;x<WIDTH;x++)
            for(int y=0;y<HEIGHT;y++){
                S=new Position(x,y);
                Position p_left=S.shift(-1,0);
                if(p_left.x<0)
                    p_left=S.shift(0,0);
                Position p_right=S.shift(1,0);
                if(p_right.x>=WIDTH)
                    p_right=S.shift(0,0);
                Position p_up=S.shift(0,1);
                if(p_up.y>=HEIGHT)
                    p_up=S.shift(0,0);
                Position p_down=S.shift(0,-1);
                if(p_down.y<0)
                    p_down=S.shift(0,0);
                if(isNOTHING(S,world)){

                    ArrayList<Position> wall_list=new ArrayList<>();
                    if(isNOTHING(p_left,world))
                        wall_list.add(p_left);
                    if(isNOTHING(p_right,world))
                        wall_list.add(p_right);
                    if(isNOTHING(p_up,world))
                        wall_list.add(p_up);
                    if(isNOTHING(p_down,world))
                        wall_list.add(p_down);

                    if(wall_list.size()>=3)
                    {
                        if(!IsIntheArea(S,new Position(STARTX,STARTY)))
                            world[x][y]=Tileset.NOTHING;
                        else{
                            world[x][y]=Tileset.NOTHING;
                        }
                    }

                }
            }

    }

    public static void generate_helper_block(TETile[][] world,boolean[][] road_map,Position start,Random rand){

            int num=5*rand.nextInt(4);
            if(num==0)
                num=5;
            int i=0;
            while(i<num){
                Position block_p=getRandomPosition(world,start,rand);//获得随机方块的左上角坐标
                int width=rand.nextInt(6);//获取随机的宽度
                int height=rand.nextInt(6);//获取随机的高度

                if(width<=2)
                    width=3;
                if(height<=2)
                    height=3;
                if(width%2==0)
                    width=width+1;
                if(height%2==0)
                    height=height+1;

                if(block_p!=null){
                    if(block_correct(block_p,width,height,world,rand))//判断生成的方块是否处在合法范围
                    {
                        int x_r=rand.nextInt(width-1);
                        int y_r=rand.nextInt(height-1);


                                for(int x= block_p.x;x<block_p.x+width;x++)
                                    for(int y=block_p.y;y<block_p.y+height;y++)
                                {


                                    road_map[x-STARTX][y-STARTY]=true;
                                    if(x==x_r+block_p.x&&y==y_r+block_p.y)
                                        world[x][y]=Tileset.LIGHT;
                                    else
                                         world[x][y]=ROAD;
                                }


                        i++;
                    }
                    else
                        continue;
                }

                else
                    continue;


            }






    }

    public static Position getRandomPosition(TETile[][] world,Position start,Random rand){
        int x=rand.nextInt(MAP_WIDTH);
        int y=rand.nextInt(MAP_HEIGHT);



        Position p=new Position(x,y);

            if(IsIntheArea(p,start))
                return p;
           else
            return null;



    }

    public static boolean block_correct(Position start,int width,int height,TETile[][] world,Random rand){
        if(start.x+width<border_x_right&&start.x>border_x_left&&start.y-height>border_y_down&&start.y<border_y_up) {
            Position border_start = start.shift(-1, height);
            for (int x = border_start.x; x < border_start.x + width + 2; x++) {

                if (world[x][border_start.y].equals(ROAD)||world[x][border_start.y].equals(NOTHING))
                    return false;
                else{
                    continue;
                }

            }
            for (int y = border_start.y; y > border_start.y -height - 2; y--) {

                if (world[border_start.x][y].equals(ROAD)||world[border_start.x][y].equals(NOTHING))
                    return false;
                else{
                    continue;
                }
            }
            border_start = start.shift(width, height);
            for (int y = border_start.y; y > border_start.y -height - 2; y--) {
//                world[border_start.x][y]=Tileset.SAND;
                if (world[border_start.x][y].equals(ROAD)||world[border_start.x][y].equals(NOTHING))
                    return false;
                else{
                    continue;
                }
            }
            border_start = start.shift(-1, -1);
            for (int x = border_start.x; x < border_start.x + width + 2; x++) {
//                world[x][border_start.y]=Tileset.GRASS;
                if (world[x][border_start.y].equals(ROAD)||world[x][border_start.y].equals(NOTHING))
                    return false;
                else{
                    continue;
                }
            }
            int x_road=STARTX;
            int y_road=STARTY;
            Position p=new Position(x_road,y_road);

            while(!IsIntheArea(p,start)){


                border_start = start.shift(-1, -1);
                int ran_clear=rand.nextInt(4);
                switch(ran_clear){
                    case 0:
                        x_road=border_start.x+rand.nextInt(width-1)+1;
                        y_road=border_start.y;

                        Position p_up=new Position(x_road,y_road+1);
                        Position p_down=new Position(x_road,y_road-1);

                        if(isWall(p_up,world)||isWall(p_down,world))
                            continue;

                        break;
                    case 1:
                        x_road=border_start.x;
                        y_road=border_start.y+rand.nextInt(height-1)+1;

                        Position p_left=new Position(x_road-1,y_road);
                        Position p_right=new Position(x_road+1,y_road-1);
                        if(isWall(p_left,world)||isWall(p_right,world))
                            continue;
                        break;


                    case 2:
                        border_start = start.shift(width, -1);
                        x_road=border_start.x;
                        y_road=border_start.y+rand.nextInt(height-1)+1;


                        p_left=new Position(x_road-1,y_road);
                        p_right=new Position(x_road+1,y_road-1);
                        if(isWall(p_left,world)||isWall(p_right,world))
                            continue;
                        break;
                    case 3:
                        border_start = start.shift(-1, height);
                        x_road=border_start.x+rand.nextInt(width-1)+1;
                        y_road=border_start.y;


                         p_up=new Position(x_road,y_road+1);
                         p_down=new Position(x_road,y_road-1);
                        if(isWall(p_up,world)||isWall(p_down,world))
                            continue;
                        break;


                }

                p=new Position(x_road,y_road);
            }


            world[x_road][y_road]=ROAD;


            return true;
        }
        else
            return false;
    }

    public static TETile[][] generateWorld(TETile[][] world,boolean[][] road_map,Position S,Random rand){

        Position start = new Position(1+STARTX,1+STARTY);
        generate_helper(world,start,road_map,S,rand);

        return world;


    }

    public static void generate_helper(TETile[][] world, Position p,boolean[][] road_map,Position start,Random rand)
    {
        Position a_p=getInternalPosition(p,start);
        ArrayList<Position> L=serachAdjacentWall(p,world,start);
        road_map[p.x-STARTX][p.y-STARTY]=true;
        //1.选定当前的路
        //2.获得当前路四周一格的墙壁，并将墙壁加到列表中  serachWall()

        while(!L.isEmpty()){
            int random_i=rand.nextInt(L.size());
           Position wall=L.get(random_i);

            //3.随机将列表中的一个墙出队，打通
           Position next_road=searchRoad(wall,world,road_map,start,rand);
            //4.坐标移动到与当前路相隔出队墙的路 Position searchRoad()
            if(next_road!=null){
                L.remove(random_i);
                world[wall.x][wall.y]=Tileset.FLOOR;
                generate_helper(world,next_road,road_map,start,rand);
            }
            else{
                L.remove(random_i);
            }

            //5.递归

        }



    }



    public static void clearRoad(TETile[][] world ,boolean[][] road_map){
        for(int x=0;x<MAP_WIDTH;x++)
            for(int y=0;y<MAP_HEIGHT;y++){

                if(road_map[x][y]){

                    ;

                }else {
                    Position p=new Position(x+STARTX,y+STARTY);
                    Position p_left=p.shift(-1,0);
                    Position p_right=p.shift(1,0);
                    Position p_down=p.shift(0,-1);
                    Position p_up=p.shift(0,1);
                    ArrayList<Position> L=new ArrayList<>();
                    if(isWall(p_left,world))
                        L.add(p_left);
                    if(isWall(p_right,world))
                        L.add(p_right);
                    if(isWall(p_down,world))
                        L.add(p_down);
                    if(isWall(p_up,world))
                        L.add(p_up);

//                   if(L!=null){
//                       for(int i=0;i<L.size();i++)
//                       {
//                            serachAdjacentRoad(world,L.get(i),road_map);
//
//
//                       }
//                   }
                    if(L.size()==4){
                        road_map[x][y]=true;
                        world[x+STARTX][y+STARTY]=WALL;

//                      for(int i=0;i<L.size();i++){
//                          Position wall=L.get(i);
//                          p_left=wall.shift(-1,0);
//                          p_right=wall.shift(1,0);
//                          p_down=wall.shift(0,-1);
//                          p_up=wall.shift(0,1);
//
//
//
//                          if(!isROAD(p_left,world)&&
//                                  !isROAD(p_right,world)&&!isROAD(p_down,world)&&!isROAD(p_up,world))
//                            world[wall.x][wall.y]=NOTHING;
//
//
//                      }
//                      p_left=p.shift(-1,0);
//                        p_right=p.shift(1,0);
//                        p_down=p.shift(0,-1);
//                        p_up=p.shift(0,1);
//                        if(isWall(p_left,world)&&
//                                isWall(p_right,world)&&isWall(p_down,world)&&isWall(p_up,world))
//                        world[x+STARTX][y+STARTY]=WALL;
                    }








                }



            }




    }




    public static void serachAdjacentRoad(TETile[][] world,Position wall,boolean[][] road_map){

        Position p_left=wall.shift(-1,0);
        Position p_right=wall.shift(1,0);
        Position p_down=wall.shift(0,-1);
        Position p_up=wall.shift(0,1);

        if(IsIntheArea(p_left,new Position(STARTX,STARTY)))
        if(!road_map[p_left.x-STARTX][p_left.y-STARTY]){

            road_map[p_left.x-STARTX][p_left.y-STARTY]=true;
            world[p_left.x][p_left.y]=WALL;
        }

        if(IsIntheArea(p_right,new Position(STARTX,STARTY)))
        if(!road_map[p_right.x-STARTX][p_right.y-STARTY]){

            road_map[p_right.x-STARTX][p_right.y-STARTY]=true;
            world[p_right.x][p_right.y]=WALL;
        }

        if(IsIntheArea(p_down,new Position(STARTX,STARTY)))
        if(!road_map[p_down.x-STARTX][p_down.y-STARTY]){

            road_map[p_down.x-STARTX][p_down.y-STARTY]=true;
            world[p_down.x][p_down.y]=WALL;
        }

        if(IsIntheArea(p_up,new Position(STARTX,STARTY)))
        if(!road_map[p_up.x-STARTX][p_up.y-STARTY]){

            road_map[p_up.x-STARTX][p_up.y-STARTY]=true;
            world[p_up.x][p_up.y]=WALL;
        }



    }



    public static boolean IsIntheArea(Position p,Position start){
//        Position a_p=getInternalPosition(p,start);
        if((p.x<border_x_left||p.x>border_x_right||p.y>border_y_up||p.y<border_y_down))
            return false;
        else
            return true;

    }
    public static void clearArea(TETile[][] world){

        for(int x= 0;x<WIDTH;x++)
        {
            for(int y=0;y<HEIGHT;y++)
                world[x][y]=Tileset.NOTHING;

        }
    }

    public static Position getInternalPosition(Position p,Position start){
        Position a_p=new Position(p.x-STARTX,p.y-STARTY);
        return a_p;
    }

    public static boolean wallCanDestroy(Position p,Position start){
        Position a_p=new Position(p.x-start.x,p.y-start.y);
        if((a_p.x%2==0&&a_p.y%2==0))
            return false;
        else
            return true;

    }

    public static ArrayList serachAdjacentWall(Position p, TETile[][] world,Position start){
        ArrayList L=new ArrayList<Position>();

        //检查该位置p的上一格，下一格，左一格，右一格是否存在可破坏的墙
        //其中(x%2==0&&y%2==0)&&(IsIntheArea(p))条件满足的位置是不可破坏的墙
       Position p_left=p.shift(-1,0);
        Position p_right=p.shift(1,0);
        Position p_down=p.shift(0,-1);
       Position p_up=p.shift(0,1);

        if(IsIntheArea(p_left,start)&&wallCanDestroy(p_left,start))
            L.add(p_left);
        if(IsIntheArea(p_right,start)&&wallCanDestroy(p_right,start))
            L.add(p_right);
        if(IsIntheArea(p_down,start)&&wallCanDestroy(p_down,start))
            L.add(p_down);
        if(IsIntheArea(p_up,start)&&wallCanDestroy(p_up,start))
            L.add(p_up);





        return L;
    }

    public static Position searchRoad(Position wall, TETile[][] world,boolean[][] road_map,Position start,Random rand){
        ArrayList<Position> L=new ArrayList<>();
        Position p_r=getInternalPosition(wall,start);
        //寻找与这个墙壁的相邻一格的路（与已选择的路相隔这个墙壁）
        //随机选择一个没有被访问过的路，返回作为下一次递归的起点
       Position p_left=wall.shift(-1,0);
       Position p_right=wall.shift(1,0);
       Position p_down=wall.shift(0,-1);
       Position p_up=wall.shift(0,1);

        if(IsIntheArea(p_left,start)&&!road_map[p_left.x-STARTX][p_left.y-STARTY]&&!isWall(p_left,world)&&wallCanDestroy(wall,start))
            L.add(p_left);


        if (IsIntheArea(p_right,start) &&!road_map[p_right.x-STARTX][p_right.y-STARTY]&& !isWall(p_right, world)&&wallCanDestroy(wall,start))
            L.add(p_right);



        if(IsIntheArea(p_down,start)&&!road_map[p_down.x-STARTX][p_down.y-STARTY]&&!isWall(p_down,world)&&wallCanDestroy(wall,start))
            L.add(p_down);



        if (IsIntheArea(p_up,start) && !road_map[p_up.x-STARTX][p_up.y-STARTY] && !isWall(p_up, world)&&wallCanDestroy(wall,start))
            L.add(p_up);
        if(L.isEmpty())
            return null;
        else{
            int random_i=rand.nextInt(L.size());
            return L.get(random_i);

        }

    }

    public static boolean isWall(Position p, TETile[][] world){

        if(p==null)
            return false;


        if(world[p.x][p.y].equals(WALL))
            return true;
        else
            return false;

    }

    public static boolean isNOTHING(Position p, TETile[][] world){

        if(p==null)
            return false;


        if(world[p.x][p.y].equals(Tileset.NOTHING))
            return true;
        else
            return false;

    }

    public static boolean isROAD(Position p, TETile[][] world){

        if(p==null)
            return false;


        if(world[p.x][p.y].description().equals("floor"))
            return true;
        else
            return false;

    }

    public static boolean isLIGHT(Position p, TETile[][] world){

        if(p==null)
            return false;


        if(world[p.x][p.y].description().equals("light"))
            return true;
        else
            return false;

    }

    public static boolean isENEMY(Position p, TETile[][] world){

        if(p==null)
            return false;


        if(world[p.x][p.y].description().equals("enemy"))
            return true;
        else
            return false;

    }

    public static boolean isSAND(Position p, TETile[][] world){

        if(p==null)
            return false;


        if(world[p.x][p.y].description().equals("sand"))
            return true;
        else
            return false;

    }




    public static TETile[][] generateWorld(long seed){


        TETile[][] world = new TETile[WIDTH][HEIGHT];
        boolean[][] road_map=new boolean[MAP_WIDTH][MAP_HEIGHT];
        Position start=new Position(STARTX,STARTY);
        initial(world,road_map,start,seed);


        return world;
    }




    public static void main(String[] args) {



    }

}
