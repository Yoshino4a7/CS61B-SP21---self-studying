package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Date;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = new Date().getTime();
    private static final Random RANDOM = new Random(SEED);



    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.SAND;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            default: return Tileset.NOTHING;
        }
    }//随机选择不同样式的像素点

    private static TETile[] getSingleline(int length,int line,TETile t){
        int nothing=(length-line);
        int tile_exist=length+(line-1)*2;


        TETile[] tile=new TETile[tile_exist+nothing+nothing];
        for(int i=0;i<nothing;i++){
            tile[i]=Tileset.NOTHING;
        }
        for(int i=nothing;i<tile_exist+nothing;i++){
            tile[i]=t;
        }
        for(int i=tile_exist+nothing;i<tile.length;i++){
            tile[i]=Tileset.NOTHING;
        }
        return tile;
    }
    public static TETile[][] getHex(int size,TETile t){
        int width=size+(size-1)*2;
        int height=(size-1)*2+2;
        TETile[][] tile=new TETile[height][width];
        int up_half=size-1;



        for(int i=0;i<height/2;i++){
            TETile[] singleline=getSingleline(size,i+1,t);
            System.arraycopy(singleline,0,tile[i],0,singleline.length);

        }
        //画上半部分

        for(int i=height/2;i>0;i--){
            TETile[] singleline=getSingleline(size,i,t);
            System.arraycopy(singleline,0,tile[height+1-i-1],0,singleline.length);

        }
        //画下半部分
        return tile;

    }


    public static void drawHex(TETile[][] tile,TETile[][] world,int x,int y){
        int a=y;
        int b=x;
           for(int i=0;i<tile.length;i++)
           {
              for(int j=0;j<tile[i].length;j++)
              {
                  int x_f=j+b;
                  int y_f=a+i;
                  if(x_f>=50)
                      x_f=49;
                  if(y_f>=50)
                      y_f=49;
                  world[x_f][y_f]=tile[i][j];

              }


           }


    }

    public static void printHex(TETile[][] tile){
        for(int i=0;i<tile.length;i++)
        {
            for(int j=0;j<tile[i].length;j++)
               System.out.print(tile[i][j].description()+" ");
            System.out.println("");
        }


    }

    public static void clearArea(TETile[][] world){

        for(int i=0;i<WIDTH;i++)
        {
            for(int j=0;j<HEIGHT;j++)
          world[i][j]=Tileset.NOTHING;

        }
    }

    public static void randomWorld(TETile[][] world){

        for(int i=0;i<30;i++){
            int size=RANDOM.nextInt(6);
            if(size<=0)
                size=2;
            TETile[][] hex=getHex(size,randomTile());
            drawHex(hex,world,RANDOM.nextInt(49),RANDOM.nextInt(49));

        }



    }





    //画单个六边形
    //1.画一行中的空格  空格数=六边形最短边的长度n-行号（1开始）
    //2.画一行中的符号  符号数=六边形最短边的长度n+(行号-1)+2
    //3.循环执行1. 2.步 n次  画出六边形上半部分
    //4.令计数器初始值为最短边长度n，计数器自减执行3.  画出下半部分


    public static void printHex(int size){

        int blank=size-1;
        int real_tile=size;

        printHex_helper(size,blank,real_tile);


    }
    public static void printHex_helper(int size,int blank,int real_tile){
        for(int i=0;i<blank;i++)
        System.out.print(" ");
        for(int i=0;i<real_tile;i++)
            System.out.print("*");
        //画当前行
        System.out.println("");
        if(blank>0)//当画到没有空格的行时，不再进行递归，这样可以从中间开始画中间的镜像行
        {

            printHex_helper(size,blank-1,real_tile+2);
        }

        //由于进行递归画下一行，因此递归退栈时，会先从中间开始画中间的镜像行，向外拓展
//        （从外向里，又从里向外）

        for(int i=0;i<blank;i++)
            System.out.print(" ");
        for(int i=0;i<real_tile;i++)
            System.out.print("*");
        System.out.println("");
        //画当前行的镜像行
    }


    //画单个六边形(递归法)
    //1.画当前行，利用传过来的nothing数和tile数画出当前行
    //2.使nothing-1,tile数-2，递归helper函数，画下一行，直到nothing<0
    //3.画当前行的镜像行



    public static class Position{

        public int x;
        public int y;

        Position(int x,int y){
            this.x=x;
            this.y=y;
        }
        public Position shift(int dx,int dy){

            return new Position(this.x+dx,this.y+dy);
            //遇到不需要渲染的位置时，使渲染位置移动，只渲染需要显示的部分
        }

    }

    public static void darwline(TETile[][] world,Position p,TETile t,int length){


        for(int i=0;i<length;i++)
        {
            world[p.x+i][p.y]=t;

        }
    }

    public static void darwHex_helper(TETile[][] world,Position p,int blank,int real_tile,TETile t,int size){
        Position startP=p.shift(blank,0);
        //使渲染坐标移动到需要渲染的位置
        darwline(world,startP,t,real_tile);
        //画当前行

        //当画到没有空格的行时，不再进行递归，这样可以从中间开始画中间的镜像行
        if(blank>0)
        {
            Position nextP=p.shift(0,-1);
            darwHex_helper(world,nextP,blank-1,real_tile+2,t,size);
        }

        //由于进行递归画下一行，因此递归退栈时，会先从中间开始画中间的镜像行，向外拓展
//        （从外向里，又从里向外）
        Position startReflectP=p.shift(blank,-2*blank-1);//将渲染坐标移动到镜像行的镜像位置
        darwline(world,startReflectP,t,real_tile);
        //画当前行的镜像行
    }

    public static void addHexgon(TETile[][] world,Position p,int size){
        if(size<2) return;

        TETile t=randomTile();
        t=changeColor(t,RANDOM);
        darwHex_helper(world,p,size-1,size,t,size);

    }
    public static void addCollomHexgon(TETile[][] world,Position p,int size,int column){
        if(size<2) return;
        TETile t=randomTile();

        for(int i=0;i<column;i++){
            Position pos=p.shift(0,-i*size*2);
            addHexgon(world,pos,size);
        }




    }


    public static TETile changeColor(TETile t,Random RANDOM){


     return  TETile.colorVariant(t,50,50,50,RANDOM);


    }


    public static void darwWorld(TETile[][] world)
    {
        clearArea(world);
        Position p=new Position(19,49);
        addHexgon(world,p,4);


    }
    public static void darwTexWorld(TETile[][] world){
        Position p=new Position(5,35);
        Position next_p=p;
        int size=4;

        clearArea(world);
        for(int i=0;i<3;i++)
        {
            Position pos=p.shift((size*2-1)*i,size*i);
            addCollomHexgon(world,pos,size,3+i);
            next_p=pos;
        }
        int size_r=size-1;
        for(int i=0;i<2;i++)
        {
            Position pos=next_p.shift((size*2-1)*(i+1),-(size*(i+1)));
            addCollomHexgon(world,pos,size,5-i-1);
        }

    }



    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        //获取渲染引擎对象并初始化
        TETile[][] Hex = new TETile[WIDTH][HEIGHT];

        darwTexWorld(Hex);
//        darwWorld(Hex);
        ter.renderFrame(Hex);



    }





}
