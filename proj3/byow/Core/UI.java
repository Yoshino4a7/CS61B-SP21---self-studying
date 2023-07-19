package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class UI {
    private Interact interactor;


    UI(Interact interactor){

        this.interactor=interactor;

    }

    public static void vic_UI(long time){

        Engine.initial();
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.clear(Color.BLACK);

        int x=500;
        int y=700;
        StdDraw.text(x, y, "恭喜你，你走出了这片森林！！！！");
        StdDraw.text(x, y-100, "你所用的时间是:");


        StdDraw.text(x, y-200, timeFormat(time)+"!!!");
        if(time/1000<=60)
        {
            StdDraw.text(x, y-300, "可这会不会太快了一点呢？我想你可以走慢一点，去探索更多的东西");

        }
        StdDraw.text(x, y-400, "按B返回");

        StdDraw.show();
    }

    public static Interact loadUI(Interact interactor){
        boolean input_UI1=false;
         boolean input_UI2=false;
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        int x=500;
        int y=700;
        StdDraw.text(x, y, "请输入要加载的地图生成种子（正数）");
        KeyboardInputSource input=new KeyboardInputSource();
        StdDraw.show();
        String seed="";
        while(true){
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
            char key = input.getNextKey();

            if(key=='L'&&!seed.isEmpty())
            {


                interactor.load(Long.valueOf(seed));
                if(interactor.getWorld()==null)
                    continue;
                return interactor;

            }


            if(Character.isDigit(key))
            {
                input_UI1=false;
                input_UI2=false;
                seed+=key;
                StdDraw.clear(Color.BLACK);
                StdDraw.text(x, y, "请输入要加载的地图生成种子（正数）");
                StdDraw.text(x, y-100, seed);
                StdDraw.show();
                continue;
            }else{
                ;
            }
            if(key=='B')
            {
                input_UI1=false;
                input_UI2=false;
                if(!seed.isEmpty()){
                    seed=seed.substring(0,seed.length()-1);
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(x, y, "请输入要加载的地图生成种子（正数）");
                    StdDraw.text(x, y-100, seed);
                    StdDraw.show();
                    continue;
                }
                else{

                    StdDraw.clear(Color.BLACK);
                    startingUI();
                    return interactor;
                }

            }




        }

    }

    public static void startingUI(){

        int x=500;
        int y=700;
        StdDraw.text(x, y, "CS61B: The Game");
        StdDraw.text(x, y-300, "New Game(N)");
        StdDraw.text(x, y-400, "Load Game(L)");
        StdDraw.text(x, y-500, "Quit(Q)");
        StdDraw.show();




    }


    public static void timeUI(Interact interactor){

        int x=5;
        int y=49;
        String time_UI="";



        long time=interactor.getTime();

        time_UI=timeFormat(time);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(5,49,3,1);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(x, y, time_UI);
        StdDraw.show();


    }

    public static String timeFormat(long time){
        String h="";
        String m="";
        String s="";
        String result="";
        long time_s_sum=time/1000;
        long h_num=time_s_sum/3600;
        long m_num=time_s_sum/60%60;
        long s_num=time_s_sum-h_num*3600-m_num*60;
        if(h_num<10){
            h="0"+h_num;
        }else{
            h=h_num+"";
        }

        if(m_num<10){
            m="0"+m_num;
        }else{
            m=m_num+"";
        }
        if(s_num<10){
            s="0"+s_num;
        }else{
            s=s_num+"";
        }


        result=h+":"+m+":"+s;





        return result;

    }




}
