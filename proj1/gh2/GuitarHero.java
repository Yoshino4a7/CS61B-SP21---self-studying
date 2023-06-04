package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class GuitarHero {
    public static final double CONCERT_A = 440.0;
    public static void keyboard_print(){
        double halfWidth_w=0.02;
        double halfHeight_w=0.15;

        double halfWidth_b=0.012;
        double halfHeight_b=0.075;

        double x_w=0.04;
        double y_w=0.5;

        double x_b=0.06;
        double y_b=0.575;

        double x_factor_w=0.04;
        double x_factor_b=0.04;
        boolean loop=false;

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(x_w+0.56+x_factor_w*7,y_w,halfWidth_w,halfHeight_w);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(x_w+0.56-halfWidth_w+7*halfWidth_w*2,y_w-halfHeight_w,
                x_w+0.56+-halfWidth_w+7*halfWidth_w*2,y_w+halfHeight_w);

        StdDraw.line(x_w+0.56-halfWidth_w+7*halfWidth_w*2,y_w-halfHeight_w,
                x_w+0.56+-halfWidth_w+7*halfWidth_w*2,y_w+halfHeight_w);
        StdDraw.line(x_w+0.56+7*halfWidth_w*2+halfWidth_w,y_w-halfHeight_w,
                x_w+0.56+7*halfWidth_w*2+halfWidth_w,y_w+halfHeight_w);

        for (int j=0;j<7;j++){

            double x_line1=x_w+0.56-halfWidth_w+j*halfWidth_w*2;
            double x_line2=x_w+0.56-halfWidth_w+j*halfWidth_w*2;
            StdDraw.setPenColor(StdDraw.WHITE);


//
//
//
//
            StdDraw.filledRectangle(x_w+0.56+x_factor_w*j,y_w,halfWidth_w,halfHeight_w);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);
            StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);

        }

        for (int i=0;i<7;i++){


            double x_actually=x_b+0.56+x_factor_b*i;
            StdDraw.setPenColor(StdDraw.BLACK);
            switch(i)
            {
                case 0:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 2:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 3:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 5:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 6:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;

            }
//
//
//
//
        }



        for (int j=0;j<7;j++){

            double x_line1=x_w+0.28+-halfWidth_w+j*halfWidth_w*2;
            double x_line2=x_w+0.28+-halfWidth_w+j*halfWidth_w*2;
            StdDraw.setPenColor(StdDraw.WHITE);

            StdDraw.filledRectangle(x_w+0.28+x_factor_w*j,y_w,halfWidth_w,halfHeight_w);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);
            StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);

        }

        for (int i=0;i<7;i++){


            double x_actually=x_b+0.28+x_factor_b*i;
            StdDraw.setPenColor(StdDraw.BLACK);
            switch(i)
            {
                case 0:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 2:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 3:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 5:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 6:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;

            }






        }

        for (int j=0;j<7;j++){

            double x_line1=x_w-halfWidth_w+j*halfWidth_w*2;
            double x_line2=x_w-halfWidth_w+j*halfWidth_w*2;
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(x_w+x_factor_w*j,y_w,halfWidth_w,halfHeight_w);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);
            StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);

        }

        for (int i=0;i<7;i++){

            double x_actually=x_b+x_factor_b*i;

            StdDraw.setPenColor(StdDraw.BLACK);







            StdDraw.setPenColor(StdDraw.BLACK);




            switch(i)
            {
                case 0:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);

                    break;
                case 2:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 3:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 5:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;
                case 6:
                    StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                    break;



            }






        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(x_w-halfWidth_w,y_w-halfHeight_w,x_w+halfWidth_w*2*21+halfWidth_w,y_w-halfHeight_w);
        StdDraw.line(x_w-halfWidth_w,y_w+halfHeight_w,x_w+halfWidth_w*2*21+halfWidth_w,y_w+halfHeight_w);

    }

    public static void keyboard_paint(int key){
        double halfWidth_w=0.02;
        double halfHeight_w=0.15;

        double halfWidth_b=0.012;
        double halfHeight_b=0.075;

        double x_w=0.04;
        double y_w=0.5;

        double x_b=0.06;
        double y_b=0.575;

        double x_factor_w=0.04;
        double x_factor_b=0.04;
        boolean loop=false;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(x_w-halfWidth_w,y_w-halfHeight_w,x_w+halfWidth_w*2*21+halfWidth_w,y_w-halfHeight_w);
        StdDraw.line(x_w-halfWidth_w,y_w+halfHeight_w,x_w+halfWidth_w*2*21+halfWidth_w,y_w+halfHeight_w);

        switch(key/12){
            case 2:{
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(x_w+0.56+x_factor_w*7,y_w,halfWidth_w,halfHeight_w);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.line(x_w+0.56-halfWidth_w+7*halfWidth_w*2,y_w-halfHeight_w,
                        x_w+0.56+-halfWidth_w+7*halfWidth_w*2,y_w+halfHeight_w);

                StdDraw.line(x_w+0.56-halfWidth_w+7*halfWidth_w*2,y_w-halfHeight_w,
                        x_w+0.56+-halfWidth_w+7*halfWidth_w*2,y_w+halfHeight_w);
                StdDraw.line(x_w+0.56+7*halfWidth_w*2+halfWidth_w,y_w-halfHeight_w,
                        x_w+0.56+7*halfWidth_w*2+halfWidth_w,y_w+halfHeight_w);




                for (int j=0;j<7;j++){

                    double x_line1=x_w+0.56-halfWidth_w+j*halfWidth_w*2;
                    double x_line2=x_w+0.56-halfWidth_w+j*halfWidth_w*2;
                    StdDraw.setPenColor(StdDraw.WHITE);


//
//
//
//
                    StdDraw.filledRectangle(x_w+0.56+x_factor_w*j,y_w,halfWidth_w,halfHeight_w);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);
                    StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);

                }

                for (int i=0;i<7;i++){


                    double x_actually=x_b+0.56+x_factor_b*i;
                    StdDraw.setPenColor(StdDraw.BLACK);
                    switch(i)
                    {
                        case 0:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 2:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 3:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 5:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 6:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;

                    }
//
//
//
//
                }

                for (int i=6;i<7;i++){


                    double x_actually=x_b+0.28+x_factor_b*i;
                    StdDraw.setPenColor(StdDraw.BLACK);
                    switch(i)
                    {
                        case 0:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 2:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 3:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 5:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 6:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;

                    }






                }





                break;
            }
            case 1:{

                for (int j=0;j<7;j++){

                    double x_line1=x_w+0.28+-halfWidth_w+j*halfWidth_w*2;
                    double x_line2=x_w+0.28+-halfWidth_w+j*halfWidth_w*2;
                    StdDraw.setPenColor(StdDraw.WHITE);

                    StdDraw.filledRectangle(x_w+0.28+x_factor_w*j,y_w,halfWidth_w,halfHeight_w);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);
                    StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);

                }

                for (int i=0;i<7;i++){


                    double x_actually=x_b+0.28+x_factor_b*i;
                    StdDraw.setPenColor(StdDraw.BLACK);
                    switch(i)
                    {
                        case 0:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 2:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 3:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 5:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 6:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;

                    }






                }



                for (int i=6;i<7;i++){

                    double x_actually=x_b+x_factor_b*i;

                    StdDraw.setPenColor(StdDraw.BLACK);







                    StdDraw.setPenColor(StdDraw.BLACK);




                    switch(i)
                    {
                        case 0:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);

                            break;
                        case 2:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 3:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 5:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 6:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;



                    }






                }


                break;

            }
            case 0:
            {
                for (int j=0;j<7;j++){

                    double x_line1=x_w-halfWidth_w+j*halfWidth_w*2;
                    double x_line2=x_w-halfWidth_w+j*halfWidth_w*2;
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle(x_w+x_factor_w*j,y_w,halfWidth_w,halfHeight_w);

                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);
                    StdDraw.line(x_line1,y_w-halfHeight_w,x_line2,y_w+halfHeight_w);

                }

                for (int i=0;i<7;i++){

                    double x_actually=x_b+x_factor_b*i;

                    StdDraw.setPenColor(StdDraw.BLACK);







                    StdDraw.setPenColor(StdDraw.BLACK);




                    switch(i)
                    {
                        case 0:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);

                            break;
                        case 2:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 3:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 5:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;
                        case 6:
                            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
                            break;



                    }






                }


            }


        }









    }

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */

        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        StdDraw.setCanvasSize(1024, 1024);
        StdDraw.enableDoubleBuffering();
        keyboard_print();
        StdDraw.show();
//        StdDraw.setPenColor(255,215,0);
//        StdDraw.filledRectangle(0.25+0.1,0.4,0.05,0.2);
//        StdDraw.pause(100);
//        StdDraw.setPenColor(StdDraw.WHITE);
//        StdDraw.filledRectangle(0.25+0.1,0.4,0.05,0.2);

//        StdDraw.line(5.0, 5.0, 5.0, 50.0);





        GuitarString[] GuitarStringA = new GuitarString[37];
        for (int j = 0; j < 37; j++) {
            double CONCERT_C = CONCERT_A * Math.pow(2, (j - 24) / 12.0);
            GuitarString G = new GuitarString(CONCERT_C);
            GuitarStringA[j] = G;
        }


        while (true) {


            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int i =keyboard.indexOf(key);
                ;
                if(i!=-1)
                {  if(StdDraw.isKeyPressed(65)){
                    GuitarStringA[i].decayD();
                }

                else
                {

                    GuitarStringA[i].decayR();

                }

                    updateKeyboard(i,key);
                    StdDraw.show();
                    GuitarStringA[i].pluck();



                    StdDraw.enableDoubleBuffering();
                    keyboard_paint(i);
                    StdDraw.show();




                }





            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int j = 0; j < 37; j++) {
                sample += GuitarStringA[j].sample();
            }


            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int j = 0; j < 37; j++) {
                GuitarStringA[j].tic();
            }

        }

    }

    public static double ClickCover(int i,char c){
        double halfWidth_w=0.02;
        double halfHeight_w=0.15;

        double halfWidth_b=0.012;
        double halfHeight_b=0.075;

        double x_w=0.04;
        double y_w=0.5;

        double x_b=0.06;
        double y_b=0.575;

        double x_factor_w=0.04;
        double x_factor_b=0.04;
        boolean loop=false;
        String keyboard = "245789-=dfgjk;'";

        if(keyboard.indexOf(c)==-1)
        {


        int i_white=i%12;
            int i_white_loop=i/12;
        switch(i_white){
            case 0:
                i_white=0;
                break;
            case 2:
                i_white=1;
                break;
            case 3:
                i_white=2;
                break;
            case 5:
                i_white=3;
                break;
            case 7:
                i_white=4;
                break;
            case 8:
                i_white=5;
                break;
            case 10:
                i_white=6;
                break;

        }
//        double x_actually=x_w + x_factor_w * (i_white_loop+1) *7 * halfWidth_w *2*i_white;
            StdDraw.setPenColor(255,215,0);
            double x_actually=x_w+(i_white_loop)*7*halfWidth_w *2+i_white* halfWidth_w *2;
            StdDraw.filledRectangle(x_actually,y_w,halfWidth_w,halfHeight_w);
            StdDraw.show();
            StdDraw.pause(15);
            return x_actually;

        }
       return -1;
    }

    public static void ClickCover_w(double i){
        double halfWidth_w=0.02;
        double halfHeight_w=0.15;

        double halfWidth_b=0.012;
        double halfHeight_b=0.075;

        double x_w=0.04;
        double y_w=0.5;

        double x_b=0.06;
        double y_b=0.575;

        double x_factor_w=0.04;
        double x_factor_b=0.04;
        boolean loop=false;

if (i!=-1) {
    StdDraw.setPenColor(StdDraw.WHITE);
    double x_actually=i;
    StdDraw.filledRectangle(x_actually,y_w,halfWidth_w,halfHeight_w);

    StdDraw.show();
}


    }

    public static double blackCover(int i,char c){
        double halfWidth_w=0.02;
        double halfHeight_w=0.15;

        double halfWidth_b=0.012;
        double halfHeight_b=0.075;

        double x_w=0.04;
        double y_w=0.5;

        double x_b=0.06;
        double y_b=0.575;

        double x_factor_w=0.04;
        double x_factor_b=0.04;
        boolean loop=false;

        int i_black=i%12;
        int i_black_loop=i/12;

        String keyboard = "245789-=dfgjk;'";

        if((keyboard.indexOf(c)!=-1))
        {
            StdDraw.setPenColor(255,215,0);


            switch(i_black){
                case 1:
                    i_black=0;
                    break;
                case 4:
                    i_black=2;
                    break;
                case 6:
                    i_black=3;
                    break;
                case 9:
                    i_black=5;
                    break;
                case 11:
                    i_black=6;
                    break;

            }
//        double x_actually=x_w + x_factor_w * (i_white_loop+1) *7 * halfWidth_w *2*i_white;
            double x_actually=x_b+(i_black_loop)*7*halfWidth_w *2+i_black* halfWidth_w *2;

            StdDraw.filledRectangle(x_actually,y_b,halfWidth_b,halfHeight_b);
            StdDraw.show();
            StdDraw.pause(15);
            return x_actually;

        }
        return -1;




    }

    public static void blackCover_w(double i){
        double halfWidth_w=0.02;
        double halfHeight_w=0.15;

        double halfWidth_b=0.012;
        double halfHeight_b=0.075;

        double x_w=0.04;
        double y_w=0.5;

        double x_b=0.06;
        double y_b=0.575;

        double x_factor_w=0.04;
        double x_factor_b=0.04;
        boolean loop=false;


        if(i!=-1){
             StdDraw.setPenColor(StdDraw.BLACK);

            StdDraw.filledRectangle(i,y_b,halfWidth_b,halfHeight_b);
            StdDraw.show();
}



        }






    public static void updateKeyboard(int i,char c){

StdDraw.enableDoubleBuffering();


       ClickCover_w( ClickCover(i,c));
        StdDraw.show();

        blackCover_w( blackCover(i,c));
        StdDraw.show();



//        ClickCover_w(i,c);
//        blackCover_w(i,c);
     

//

//






    }
}

