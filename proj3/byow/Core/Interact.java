package byow.Core;

import afu.org.checkerframework.checker.oigj.qual.World;
import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.introcs.StdDraw;
import net.sf.saxon.event.IDFilter;

import java.awt.*;
import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;


public class Interact implements Serializable{
    public static final File CWD = new File(System.getProperty("user.dir"));

    public static final File SAVE=new File(CWD,"gameinfo");
    public static final TETile PLAYER=Tileset.AVATAR;

    public Position player=new Position(0,0);
    public Position[][] sight=new Position[9][9];
    public Position vic=new Position(0,0);
    public String seed="";
    public TETile[][] world=null;
    public Random rand=null;
    public long time=0;
    public long time_old=0;
    public long start_time;
    public long time_chase=0;
    public boolean isVic=false;
    private boolean input_UI1=false;
    private boolean input_UI2=false;
    public boolean isSightON=false;
    public boolean isShowRoute=false;
    public Enemy enemy;


    public LinkedList<Position> light_list;

    public TETile[][] create_newgame(){



        StdDraw.setPenColor(Color.WHITE);

        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        int x=500;
        int y=700;
        StdDraw.text(x, y, "请输入地图生成种子（正数）");
        KeyboardInputSource input=new KeyboardInputSource();
        StdDraw.show();
        reset();
        while(true){
            char key = input.getNextKey();
            if(seed.isEmpty()&&!input_UI1)
            {
                input_UI1=true;
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.filledRectangle(x,y-200,50,50);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(x, y-200, "按B键返回");
                StdDraw.show();
            }
            else if(!input_UI2&&!seed.isEmpty()){
                input_UI2=true;
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.filledRectangle(x,y-200,50,50);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.text(x, y-200, "按B键回退");
                StdDraw.show();
            }
            if(key=='S'&&!seed.isEmpty())
            {

                setInfo(Long.valueOf(seed));
                vic=setStartandGoal(this.world,this.rand);
                timeStart();
                save();

                return  world;


            }


            if(Character.isDigit(key))
            {
                input_UI1=false;
                input_UI2=false;
                this.seed+=key;
                StdDraw.clear(Color.BLACK);
                StdDraw.text(x, y, "请输入地图生成种子（正数）");
                StdDraw.text(x, y-100, this.seed);
                StdDraw.show();
                continue;
            }else{
                ;
            }
            if(key=='B')
            {
                if(!this.seed.isEmpty()){
                    input_UI1=false;
                    input_UI2=false;
                    this.seed=this.seed.substring(0,seed.length()-1);
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(x, y, "请输入地图生成种子（正数）");
                    StdDraw.text(x, y-100, this.seed);
                    StdDraw.show();
                    continue;
                }else{
                    input_UI1=false;
                    input_UI2=false;
                    StdDraw.clear(Color.BLACK);
                    UI.startingUI();
                    return world;
                }

            }




        }




    }

    public  void save(){

            //文件保存，保存玩家的坐标，地图的种子
        GameInfo info_new=null;
        if(seed.isEmpty())
            System.exit(0);
        this.time=new Date().getTime()-start_time+time_old;
        if(!SAVE.exists()){
            try{

                SAVE.createNewFile();
                GameInfo info=new GameInfo(player,Long.valueOf(seed),world,time,this.vic,this.isSightON,this.enemy);


                ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(SAVE));
                oo.writeObject(info);
                oo.flush();
                oo.close();

                return;
            }catch(IOException o){

            }


        }


        try
        {


            ObjectInputStream in=new ObjectInputStream(new FileInputStream(SAVE));
            this.time=new Date().getTime()-start_time+time_old;
            GameInfo info=(GameInfo)in.readObject();
           info_new=new GameInfo(info,this.player,Long.valueOf(this.seed),this.world,this.time,this.vic,this.isSightON,this.enemy);

            in.close();

        }catch(Exception o){
          o.getStackTrace();
        }
        try
        {

            ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(SAVE));





            oo.writeObject(info_new);
            oo.flush();
            oo.close();


        }catch(Exception o){
            o.getStackTrace();
        }





    }
    public void load(long seed){


        //种子不存在，新建一个世界
//        world=mazeDemo.generateWorld(seed);


        //种子存在，根据gameinfo保存的信息新建世界
        if(!SAVE.exists()){
            StdDraw.text(500, 400, "存档文件不存在，请返回新建游戏");
            StdDraw.show();
            return ;

        }
        else{


            try
            {

                ObjectInputStream in=new ObjectInputStream(new FileInputStream(SAVE));

                GameInfo info=(GameInfo)in.readObject();

                in.close();
                if(info.SeedIsExist(seed)){
                    reset();
                    this.world=info.getWorld(seed);
                    this.player=info.getPlayerPosition(seed);
                    this.seed=String.valueOf(seed);
                    this.vic=info.getVictory(seed);
                    this.time=0;
                    this.time_old=info.getTime(seed);
                    timeStart();
                    this.sight=getSight(this.player);
                    this.isSightON=info.getSightOn(seed);
                    this.light_list=getLight(this.world);
                    this.enemy=info.getEnemy(seed);
//                    putPlayer(info.getPlayerPosition(seed),world);
                }
                else {
                    reset();

                    setInfo(seed);

                    vic=setStartandGoal(this.world,this.rand);

                }





            }catch(Exception o){

                    System.out.println("S");
            }




        }


    }



    public static Position getStart(TETile[][] world){
        Position p=new Position(0,0);


        for(int x=0;x<mazeDemo.WIDTH;x++)
            for(int y=0;y<mazeDemo.HEIGHT;y++)
            {
                p=new Position(x,y);
                if(mazeDemo.isROAD(p,world))
               return p;
            }

       return p;


    }

    public static Position EnemygetStart(TETile[][] world){
        Position p=null;


        for(int x=mazeDemo.WIDTH-1;x>=0;x--)
            for(int y=mazeDemo.HEIGHT-1;y>=0;y--)
            {
                p=new Position(x,y);
                if(mazeDemo.isROAD(p,world)){
                    world[p.x][p.y]=new TETile('#', new Color(216, 128, 128), world[p.x][p.y].getBackgroundColor(), "enemy");
                    return p;
                }

            }

        return p;


    }


    public  void moveUp(){
        Position new_player=player.shift(0,1);
        if(victory(new_player))
        {
            UI.vic_UI(getTime());
            return ;
        }


        if(mazeDemo.isROAD(new_player,world)){
            TETile old_floor=null;
            TETile new_floor=null;
            old_floor=new TETile('·', this.world[player.x][player.y].getTextColor(),this.world[player.x][player.y].getBackgroundColor(),
                    "floor");
            if(!this.world[new_player.x][new_player.y].getBackgroundColor().equals(this.world[player.x][player.y].getBackgroundColor()))

            new_floor=new TETile('@', Color.white, this.world[new_player.x][new_player.y].getBackgroundColor(), "you");
            else
                new_floor=new TETile('@', Color.white, old_floor.getBackgroundColor(), "you");
            this.world[player.x][player.y]=old_floor;
            this.world[new_player.x][new_player.y]=new_floor;
            this.player=new_player;
            this.sight=sight_shift(0,1,this.sight);
            enemy.searchRoute(player,world);
            showRoute(enemy);
        }


    }
    public void moveDown(){

        Position new_player=player.shift(0,-1);

        if(mazeDemo.isROAD(new_player,world)){
            TETile old_floor=null;
            TETile new_floor=null;
            old_floor=new TETile('·', this.world[player.x][player.y].getTextColor(),this.world[player.x][player.y].getBackgroundColor(),
                    "floor");
            if(!this.world[new_player.x][new_player.y].getBackgroundColor().equals(this.world[player.x][player.y].getBackgroundColor()))

                new_floor=new TETile('@', Color.white, this.world[new_player.x][new_player.y].getBackgroundColor(), "you");
            else
                new_floor=new TETile('@', Color.white, old_floor.getBackgroundColor(), "you");
            this.world[player.x][player.y]=old_floor;
            this.world[new_player.x][new_player.y]=new_floor;
            this.player=new_player;
            this.sight=sight_shift(0,-1,this.sight);
            enemy.searchRoute(player,world);
            showRoute(enemy);

        }
    }
    public  void moveLeft(){
        Position new_player=player.shift(-1,0);

        if(mazeDemo.isROAD(new_player,world)){
            TETile old_floor=null;
            TETile new_floor=null;
           old_floor=new TETile('·', this.world[player.x][player.y].getTextColor(),this.world[player.x][player.y].getBackgroundColor(),
                    "floor");

            if(!this.world[new_player.x][new_player.y].getBackgroundColor().equals(this.world[player.x][player.y].getBackgroundColor()))

                new_floor=new TETile('@', Color.white, this.world[new_player.x][new_player.y].getBackgroundColor(), "you");
            else
                new_floor=new TETile('@', Color.white, old_floor.getBackgroundColor(), "you");
            this.world[player.x][player.y]=old_floor;
            this.world[new_player.x][new_player.y]=new_floor;
            this.player=new_player;
            this.sight=sight_shift(-1,0,this.sight);
            enemy.searchRoute(player,world);
            showRoute(enemy);
        }

    }
    public  void moveRight(){
        Position new_player=player.shift(1,0);

        if(mazeDemo.isROAD(new_player,world)){
            TETile old_floor=null;
            TETile new_floor=null;
            old_floor=new TETile('·', this.world[player.x][player.y].getTextColor(),this.world[player.x][player.y].getBackgroundColor(),
                    "floor");
            if(!this.world[new_player.x][new_player.y].getBackgroundColor().equals(this.world[player.x][player.y].getBackgroundColor()))

                new_floor=new TETile('@', Color.white, this.world[new_player.x][new_player.y].getBackgroundColor(), "you");
            else
                new_floor=new TETile('@', Color.white, old_floor.getBackgroundColor(), "you");



            this.world[player.x][player.y]=old_floor;
            this.world[new_player.x][new_player.y]=new_floor;
            this.player=new_player;
            this.sight=sight_shift(1,0,this.sight);
            enemy.searchRoute(player,world);
            showRoute(enemy);
        }

    }

    public  void setPlayerPostion(Position pos){

        player=pos;


    }



    public void setInfo(long seed){

    TETile[][] world=mazeDemo.generateWorld(Long.valueOf(seed));
    Position start=getStart(world);
    this.player=start;
    this.seed=String.valueOf(seed);
    world[start.x][start.y]=PLAYER;
    this.world=world;
    this.rand=new Random(Long.valueOf(seed));
    this.time=0;
    this.isSightON=false;
    timeStart();
    this.sight=getSight(this.player);
    this.light_list=getLight(this.world);
    allLight_on();
    Position enmey_pos=EnemygetStart(world);
    enemy=initial_Enemy(enmey_pos,world);
}

    public void setSightON(){
        if(isSightON)
            isSightON=false;
        else
            isSightON=true;

    }

    private LinkedList<Position> getLight(TETile[][] world){
        LinkedList<Position> list=new LinkedList<>();

        for(int x=0;x<mazeDemo.WIDTH;x++)
            for(int y=0;y<mazeDemo.HEIGHT;y++)
            {
                Position p=new Position(x,y);
                if(mazeDemo.isLIGHT(p,world))
                    list.add(p);
            }
        return list;


    }

    public TETile[][] getWorld(){
        return world;
    }
    public String getSeed(){
        return seed;
    }
    public void putPlayer(Position player,TETile[][] world){
        world[player.x][player.y]=PLAYER;
        this.world=world;
    }

    private  Position setStartandGoal(TETile[][] world, Random rand){

        Position p=null;

        p=new Position(mazeDemo.STARTX+rand.nextInt(mazeDemo.MAP_WIDTH),mazeDemo.STARTY+mazeDemo.MAP_HEIGHT-1);
        Position p_down=p.shift(0,-1);
        while(!mazeDemo.isROAD(p_down,world)){
            p=new Position(mazeDemo.STARTX+rand.nextInt(mazeDemo.MAP_WIDTH),mazeDemo.STARTY+mazeDemo.MAP_HEIGHT-1);
            p_down=p.shift(0,-1);
        }
        world[p.x][p.y]=Tileset.SAND;

        return p;
    }

    public boolean victory(Position player){



        if(player.x== vic.x&&player.y==vic.y)
        {

//            GameInfo info_new=null;
//            try
//            {
//
//
//                ObjectInputStream in=new ObjectInputStream(new FileInputStream(SAVE));
//
//                GameInfo info=(GameInfo)in.readObject();
//                info.remove(Long.valueOf(this.seed));
//                info_new=info;
//                reset();
//                in.close();
//
//            }catch(Exception o){
//                o.getStackTrace();
//            }
//
//            try{
//                ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(SAVE));
//                oo.writeObject(info_new);
//                oo.flush();
//                oo.close();
//
//            }catch(IOException o){
//
//            }
//
//


            isVic=true;
            return true;

//            StdDraw.text(x, y-100, time);

        }else{
            return false;
        }



    }


    public void timeStart(){
        this.start_time=new Date().getTime();

    }

    public  long getTime(){
        this.time=new Date().getTime()-start_time+time_old;
        return this.time;
    }
    public void reset(){
        this.seed="";
        this.world=null;
        this.time=0;
        this.time_old=0;
        this.start_time=0;
        this.input_UI1=false;
        this.input_UI2=false;
       player=new Position(0,0);
        vic=new Position(0,0);

        rand=null;

        isVic=false;
        this.sight=null;
        this.isSightON=false;

    }



    public Position[][] getSight(Position new_player){
        Position[][] sight=new Position[9][9];
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                sight[i][j]=null;

        Position start=new_player.shift(0,4);
        int level=0;
        getSight_helper(start,level,sight);

        this.sight=sight;
        return sight;


    }
    private void getSight_helper(Position start,int level,Position[][] sight){
    if(level<5){
        int x=4;
        int y=8-level;

        for(int i=0;i<level;i++){
            sight[x-(i+1)][y]=start.shift(-(i+1),0);

        }
        sight[x][y]=start;
        for(int i=0;i<level;i++){
            sight[x+(i+1)][y]=start.shift((i+1),0);

        }
        getSight_helper(start.shift(0,-1),level+1,sight);
        if(level==4)
            return;
        x=4;
        y=level;
        start=start.shift(0,-((4-level)*2));
        for(int i=0;i<level;i++){
            sight[x-(i+1)][y]=start.shift(-(i+1),0);

        }
        sight[x][y]=start;
        for(int i=0;i<level;i++){
            sight[x+(i+1)][y]=start.shift((i+1),0);

        }

    }else{
        return;
    }


    }


    public TETile[][] updateWorld(TETile[][] world,Position[][] sight){
        TETile[][] new_world=new TETile[mazeDemo.WIDTH][mazeDemo.HEIGHT];
        for(int x=0;x<mazeDemo.WIDTH;x++){
            for(int y=0;y<mazeDemo.HEIGHT;y++)
            {
                new_world[x][y]=Tileset.NOTHING;
            }


        }


        for(int i=0;i<sight.length;i++) {
            for (int j = 0; j < sight[0].length; j++) {
                if (sight[i][j] != null) {
                    new_world[sight[i][j].x][sight[i][j].y] = world[sight[i][j].x][sight[i][j].y];
                }
            }
        }

        return new_world;

    }

    public Position[][] sight_shift(int x,int y,Position[][] sight){

        for(int i=0;i<sight.length;i++){
            for(int j=0;j<sight[0].length;j++){
                if(sight[i][j]!=null)
                {
                    Position p=sight[i][j].shift(x,y);
                    if(p.y<0)
                        p.y=0;
                    if(p.y>=mazeDemo.HEIGHT)
                        p.y=mazeDemo.HEIGHT-1;
                    if(p.x<0)
                        p.x=0;
                    if(p.x>=mazeDemo.WIDTH)
                        p.x=mazeDemo.WIDTH-1;
                    sight[i][j]=p;
                }


            }
        }
        return sight;

    }

    public void turnOnLight(){


        Position player_a_light=getAdjecentLight(player);

        if(player_a_light!=null)
        {
            if(!world[player_a_light.x][player_a_light.y].getBackgroundColor().equals(Color.BLACK)){
                lighting_off(player_a_light,this.world);
            }
            else{

                lighting_on(player_a_light,this.world);
            }
        }




    }

    public void lighting_on(Position light,TETile[][] world){
        int i=1;
        int level=3;
        boolean up_over=false;
        boolean down_over=false;
        int r=255;
        int g=255;
        int b=0;
        Position start=light.shift(-4,-4);
        int[] range=getRange(light,world);

        int range_x_left=range[0];
        int range_x_right=range[1];
        int range_y_up=range[2];
        int range_y_down=range[3];

        TETile[][] light_tile=new TETile[9][9];
        changeRange(range,light,level);
        for(int x=0;x<7;x++)
            for(int y=0;y<7;y++)
            {
                if(isInRange(x,y,range_x_left,range_x_right,range_y_up,range_y_down,light))
                {

                    changeColor(r,g,b,light.shift(x-3,y-3),level);
                }


            }
        level--;
        changeRange(range,light,level);
        range_x_left=range[0];
        range_x_right=range[1];
        range_y_up =range[2];
        range_y_down=range[3];



        for(int x=0;x<7;x++)
            for(int y=0;y<7;y++)
            {

                    if(isInRange(x,y,range_x_left,range_x_right,range_y_up,range_y_down,light))
                    {

                        changeColor(r,g,b,light.shift(x-3,y-3),level);
                    }



            }
        level--;
        changeRange(range,light,level);
        range_x_left=range[0];
        range_x_right=range[1];
        range_y_up =range[2];
        range_y_down=range[3];



        for(int x=0;x<7;x++)
            for(int y=0;y<7;y++)
            {

                if(isInRange(x,y,range_x_left,range_x_right,range_y_up,range_y_down,light))
                {

                    changeColor(r,g,b,light.shift(x-3,y-3),level);
                }



            }

    }

    public  int[] getRange(Position light,TETile[][] world){
        Position pos=null;
        int range_x_left=light.x-1;
        int range_x_right=light.x+1;
        int range_y_up=light.y+1;
        int range_y_down=light.y-1;

        while(true){
            pos=new Position(range_x_left,light.y);

            if((!mazeDemo.isROAD(pos,world)
                    ||(mazeDemo.isROAD(pos,world)&&!mazeDemo.isROAD(pos.shift(0,1),world)
                    &&!mazeDemo.isROAD(pos.shift(0,-1),world)))&&!world[pos.x][pos.y].description().equals("you"))
            {
                break;
            }
            else
            {
                range_x_left--;
            }

        }
        while(true){
            pos=new Position(range_x_right,light.y);
            if(!world[pos.x][pos.y].description().equals("you")&&(!mazeDemo.isROAD(pos,world)
                    ||(mazeDemo.isROAD(pos,world)
                    &&!mazeDemo.isROAD(pos.shift(0,1),world)) &&!mazeDemo.isROAD(pos.shift(0,-1),world)))

            {
                break;
            }
            else
            {
                range_x_right++;
            }

        }
        while(true){
            pos=new Position(light.x,range_y_up);
            if(!world[pos.x][pos.y].description().equals("you")&&(!mazeDemo.isROAD(pos,world)
                    ||(mazeDemo.isROAD(pos,world)
                    &&!mazeDemo.isROAD(pos.shift(1,0),world)

                    &&!mazeDemo.isROAD(pos.shift(-1,0),world))))
            {
                break;
            }
            else
            {
                range_y_up++;
            }

        }
        while(true){
            pos=new Position(light.x,range_y_down);
            if(!world[pos.x][pos.y].description().equals("you")&&(!mazeDemo.isROAD(pos,world)
                    ||(mazeDemo.isROAD(pos,world)
                    &&!mazeDemo.isROAD(pos.shift(1,0),world)

                    &&!mazeDemo.isROAD(pos.shift(-1,0),world))))
            {
                break;
            }
            else
            {
                range_y_down--;
            }

        }

        int[] range={range_x_left,range_x_right,range_y_up,range_y_down};

        return range;

    }

    public int[] changeRange(int[] range ,Position light,int level){


        if(Math.abs(range[0]-light.x)<=level+1)
        {
            ;
        }
        else
            range[0]=range[0]+1;

        if(Math.abs(range[1]-light.x)<=level+1)
        {
            ;
        }
        else
            range[1]=range[1]-1;

        if(Math.abs(range[2]-light.y)<=level+1)
        {
            ;
        }
        else
            range[2]=range[2]-1;
        if(Math.abs(range[3]-light.y)<=level+1)
        {
            ;
        }
        else
            range[3]=range[3]+1;

        return range;


    }


    public boolean isInRange(int x,int y,int range_x_left,int range_x_right,int range_y_up,int range_y_down,Position light){
//               if(mazeDemo.isROAD(new Position(x,y),this.world))
//               {
//                   if(x-3+light.x == range_x_left ||
//                           x-3+light.x == range_x_right ||
//                           y-3+light.y==range_y_up
//                           ||y-3+light.y==range_y_down)
//                   {
//                       return true;
//                   }
//
//               }

        if(x-3+light.x > range_x_left &&
                x-3+light.x < range_x_right &&
                y-3+light.y<range_y_up
                &&y-3+light.y>range_y_down)
            return true;
                else
                    return false;
    }

    public int getMin(int [] array){
        int i=0;
        int min=array[0];
        while(i<array.length){
            if(array[i]<=min)
                min=array[i];

            i++;
        }
        return min;

    }

    public int getMax(int [] array){
        int i=0;
        int max=array[0];
        while(i<array.length){
            if(array[i]>=max)
               max=array[i];

            i++;
        }
        return max;

    }

    public  void  allLight_on(){
        int i=0;
        while(i<light_list.size()){
            lighting_on(light_list.get(i),world);
            i++;
        }
    }

    public void lighting_off(Position light,TETile[][] world){
        int[] range=getRange(light,world);

        int range_x_left=range[0];
        int range_x_right=range[1];
        int range_y_up=range[2];
        int range_y_down=range[3];

        for(int x=0;x<7;x++)
            for(int y=0;y<7;y++)
            {
                if(isInRange(x,y,range_x_left,range_x_right,range_y_up,range_y_down,light))
                {

                    changeColor_off(0,0,0,light.shift(x-3,y-3));
                }


            }




    }

    public Position getAdjecentLight(Position player){

        Position p_left=player.shift(-1,0);
        Position p_right=player.shift(1,0);
        Position p_down=player.shift(0,-1);
        Position p_up=player.shift(0,1);





        if(this.world[p_left.x][p_left.y].description().equals("light"))
            return p_left;
        if(this.world[p_right.x][p_right.y].description().equals("light"))
            return p_right;
        if(this.world[p_down.x][p_down.y].description().equals("light"))
            return p_down;
        if(this.world[p_up.x][p_up.y].description().equals("light"))
            return p_up;
        return null;

    }


    public void changeColor(int r,int g,int b,Position pos,int i){
        if (world[pos.x][pos.y].description().equals("floor")){

        }
        TETile tile=null;
        if(world[pos.x][pos.y].description().equals("light")){
            tile=new TETile('●', new Color(255, 255, 0), new Color(255, 225, 125,195), "light");
            world[pos.x][pos.y]=tile;
            return;
        }
        if(pos.y==player.y&&pos.x== player.x)
            tile=new TETile('@', new Color(255, 255, 255), new Color(r, g-(3-i)*20, b+(3-i)*50,220-i*60), "you");
        else
        tile=new TETile('·', new Color(255, 255, 255), new Color(r, g-(3-i)*20, b+(3-i)*50,220-i*60), "floor");
        world[pos.x][pos.y]=tile;



    }
    public void changeColor_off(int r,int g,int b,Position pos){
        if (world[pos.x][pos.y].description().equals("floor")){

        }
        TETile tile=null;
        if(world[pos.x][pos.y].description().equals("light")){
            tile=new TETile('●', new Color(255, 255, 255), new Color(0, 0, 0,255), "light");
            world[pos.x][pos.y]=tile;
            return;
        }
        if(pos.y==player.y&&pos.x== player.x)
            tile=new TETile('@', new Color(255, 255, 255), new Color(r, g, b,255), "you");
        else
        tile=new TETile('·', new Color(255, 255, 255), new Color(r, g, b,255), "floor");
        world[pos.x][pos.y]=tile;



    }

    public Enemy initial_Enemy(Position enemy_pos,TETile[][] world){
        LinkedList<Position> route=new LinkedList<>();
        Enemy enemy=new Enemy(enemy_pos,route);
        enemy.searchRoute(this.player,world);
        time_chase=time;

        return enemy;

    }

    public void chase(Enemy enemy, TERenderer ter){
        LinkedList<Position> route=enemy.getRoute();


        int i=0;

            Position pos=route.get(i);
            Date new_date=new Date();
            if(time-time_chase>=1000)
            {
                time_chase=time;
                enemy.moveTo(pos,world);
                if(isSightON)
                ter.renderFrame(getWorld());
                else{
                    ter.renderFrame(updateWorld(getWorld(),sight));
                }
                i++;
                route.remove(0);
            }else{
                return;
            }

//            world[pos.x][pos.y]=new TETile('·', new Color(255, 0, 0), Color.black,
//                    "floor");





    }

    public Enemy getEnemy(){
        return enemy;
    }

    public void showRoute(Enemy enemy){

        if(isShowRoute){

            LinkedList<Position> route=enemy.getRoute();
            int i=0;
            while(i<route.size()){
                Position pos=route.get(i);
                world[pos.x][pos.y]=new TETile('·', new Color(255, 0, 0), world[pos.x][pos.y].getBackgroundColor(),
                        "floor");
                i++;
            }
        }else{

            LinkedList<Position> route=enemy.getRoute();
            int i=0;
            while(i<route.size()){
                Position pos=route.get(i);
                world[pos.x][pos.y]=new TETile('·', new Color(128, 192, 128),  world[pos.x][pos.y].getBackgroundColor(),
                        "floor");
                i++;
            }

        }
    }

    public void setShowRoute(){
        if(isShowRoute)
            isShowRoute=false;
        else
            isShowRoute=true;
    }

//    public boolean canLight(Position pos,Position light){
//
//    }
}
