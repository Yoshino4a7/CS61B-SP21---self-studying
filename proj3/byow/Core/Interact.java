package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Date;
import java.util.Random;


public class Interact implements Serializable{
    public static final File CWD = new File(System.getProperty("user.dir"));

    public static final File SAVE=new File(CWD,"gameinfo");
    public static final TETile PLAYER=Tileset.AVATAR;

    public Position player=new Position(0,0);
    public Position vic=new Position(0,0);
    public String seed="";
    public TETile[][] world=null;
    public Random rand=null;
    public long time=0;
    public long time_old=0;
    public long start_time;
    public boolean isVic=false;
    private boolean input_UI1=false;
    private boolean input_UI2=false;

    public TETile[][] create_newgame(){



        StdDraw.setPenColor(Color.WHITE);

        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        int x=500;
        int y=700;
        StdDraw.text(x, y, "请输入地图生成种子（正数）");
        KeyboardInputSource input=new KeyboardInputSource();
        StdDraw.show();

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
                GameInfo info=new GameInfo(player,Long.valueOf(seed),world,time,this.vic);


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
           info_new=new GameInfo(info,this.player,Long.valueOf(this.seed),this.world,this.time,this.vic);

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

        reset();
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
                    this.world=info.getWorld(seed);
                    this.player=info.getPlayerPosition(seed);
                    this.seed=String.valueOf(seed);
                    this.vic=info.getVictory(seed);
                    this.time=0;
                    this.time_old=info.getTime(seed);
                    timeStart();
//                    putPlayer(info.getPlayerPosition(seed),world);
                }
                else {
                    setInfo(seed);
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


    public  void moveUp(){
        Position new_player=player.shift(0,1);
        if(victory(new_player))
        {
            UI.vic_UI(this.time);
            return ;
        }


        if(mazeDemo.isROAD(new_player,world)){
            this.world[player.x][player.y]=Tileset.FLOOR;
            this.world[new_player.x][new_player.y]=PLAYER;
            this.player=new_player;


        }


    }
    public void moveDown(){

        Position new_player=player.shift(0,-1);

        if(mazeDemo.isROAD(new_player,world)){
            this.world[player.x][player.y]=Tileset.FLOOR;
            this.world[new_player.x][new_player.y]=PLAYER;
            this.player=new_player;


        }
    }
    public  void moveLeft(){
        Position new_player=player.shift(-1,0);

        if(mazeDemo.isROAD(new_player,world)){
            this.world[player.x][player.y]=Tileset.FLOOR;
            this.world[new_player.x][new_player.y]=PLAYER;
            this.player=new_player;


        }

    }
    public  void moveRight(){
        Position new_player=player.shift(1,0);

        if(mazeDemo.isROAD(new_player,world)){
            this.world[player.x][player.y]=Tileset.FLOOR;
            this.world[new_player.x][new_player.y]=PLAYER;
            this.player=new_player;


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
    timeStart();



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

            GameInfo info_new=null;
            try
            {


                ObjectInputStream in=new ObjectInputStream(new FileInputStream(SAVE));
                reset();
                GameInfo info=(GameInfo)in.readObject();
                info.remove(Long.valueOf(this.seed));
                info_new=info;
                in.close();

            }catch(Exception o){
                o.getStackTrace();
            }

            try{
                ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(SAVE));
                oo.writeObject(info_new);
                oo.flush();
                oo.close();

            }catch(IOException o){

            }




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

    }

}
