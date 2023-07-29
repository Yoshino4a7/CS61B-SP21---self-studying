package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    private static final int MAP_WIDTH = mazeDemo.MAP_WIDTH;
    private static final int MAP_HEIGHT = mazeDemo.MAP_HEIGHT;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

        Position player=new Position(0,0);
        initial();
        KeyboardInputSource input=new KeyboardInputSource();
        Interact interactor=new Interact();
        boolean Ingame=false;
        boolean isVic=false;
        TETile[][] world=null;
        TETile[][] new_world=null;
        Position[][] sight=null;
        UI ui_controller=new UI(interactor);
        Enemy enemy=null;
        while(true){
            if(Ingame){

                UI.timeUI(interactor);
                interactor.chase(enemy,ter);

                StdDraw.show();



            }
            char key=input.getNextKey();

            if(isVic){
                Ingame=false;
                switch(key){
                    case 'N':
                        if(!Ingame){
                            world =interactor.create_newgame();
                            ter.initialize(mazeDemo.WIDTH,mazeDemo.HEIGHT);
                            ter.renderFrame(world);
                            isVic=false;
                            Ingame=true;
                        }

                        break;
                    case 'L':
                        if(!Ingame){
                            interactor=UI.loadUI(interactor);

                            ter.initialize(mazeDemo.WIDTH,mazeDemo.HEIGHT);
                            ter.renderFrame(interactor.getWorld());
                            Ingame=true;
                            isVic=false;
                        }

                        break;

                    case 'B':
                        if(!Ingame){
                            initial();
                           UI.startingUI();
                           interactor.reset();
                            isVic=false;
                        }

                        break;


                }

                continue;
            }




            if(key!='Q'&&key!=' '){

                switch(key){
                    case 'N':
                        if(!Ingame){
                            world =interactor.create_newgame();
                            if(world==null)
                                continue;


                            ter.initialize(mazeDemo.WIDTH,mazeDemo.HEIGHT);

                            if(!interactor.isSightON)
                            {
                                sight=interactor.sight;
                                world=interactor.updateWorld(interactor.getWorld(),sight);
                            }
                            else{
                                world=interactor.getWorld();
                            }
                            enemy=interactor.getEnemy();
                            ter.renderFrame(world);
                            isVic=false;
                            Ingame=true;
                        }

                        break;
                    case 'L':
                        if(!Ingame){
                           interactor=UI.loadUI(interactor);
                            if(interactor.getWorld()==null)
                                continue;
                            ter.initialize(mazeDemo.WIDTH,mazeDemo.HEIGHT);
                            if(!interactor.isSightON)
                            {
                                sight=interactor.sight;
                                world=interactor.updateWorld(interactor.getWorld(),sight);

                            }
                            else{
                                world=interactor.getWorld();

                            }
                            ter.renderFrame(world);
                            Ingame=true;
                            isVic=false;
                        }
                        else {

                            interactor.setSightON();
                            if(interactor.isSightON){
                                world=interactor.getWorld();

                            }else{

                                    sight=interactor.sight;
                                    world=interactor.updateWorld(interactor.getWorld(),sight);

                            }
                            ter.renderFrame(world);

                        }
                        break;
                    case 'W':
                        if(Ingame){

                            interactor.moveUp();


                            if(interactor.isVic)
                            {
                                isVic=true;

                                Ingame=false;
                            }
                            else{
                                if(!interactor.isSightON)
                                {
                                    sight=interactor.sight;
                                    world=interactor.updateWorld(interactor.getWorld(),sight);
                                }
                                else{
                                    world=interactor.getWorld();
                                }
                                ter.renderFrame(world);
                            }




                        }

                        break;
                    case 'S':
                        if(Ingame){
                            interactor.moveDown();

                            if(interactor.isVic)
                            {
                                isVic=true;
                                UI.vic_UI(interactor.getTime());
                                Ingame=false;
                            }else{

                                if(!interactor.isSightON)
                                {
                                    sight=interactor.sight;
                                    world=interactor.updateWorld(interactor.getWorld(),sight);
                                }
                                else{
                                    world=interactor.getWorld();
                                }
                                ter.renderFrame(world);
                            }

                        }
                        break;
                    case 'A':
                        if(Ingame) {
                            interactor.moveLeft();


                            if(interactor.isVic)
                            {
                                isVic=true;
                                UI.vic_UI(interactor.getTime());
                                Ingame=false;
                            }
                          else{
                                if(!interactor.isSightON)
                                {
                                    sight=interactor.sight;
                                    world=interactor.updateWorld(interactor.getWorld(),sight);
                                }
                                else{
                                    world=interactor.getWorld();
                                }

                                ter.renderFrame(world);
                            }
                        }
                        break;
                    case 'D':
                        if(Ingame) {
                            interactor.moveRight();

                            if(interactor.isVic)
                            {
                                isVic=true;
                                UI.vic_UI(interactor.getTime());
                                Ingame=false;
                            }
                            else{
                                if(!interactor.isSightON)
                                {
                                    sight=interactor.sight;
                                    world=interactor.updateWorld(interactor.getWorld(),sight);
                                }
                                else{
                                    world=interactor.getWorld();
                                }

                                ter.renderFrame(world);
                            }
                        }
                        break;

                    case 'M':
                        if(Ingame) {
                            Ingame=false;

                            interactor.save();
                            interactor.reset();
                            initial();



                        }
                        break;
                    case 'I':
                        if(Ingame) {

                            interactor.turnOnLight();
                            ter.renderFrame(interactor.getWorld());

                        }
                        break;
                    case 'U':
                        if(Ingame) {
                            interactor.setShowRoute();
                            interactor.showRoute(enemy);
                            ter.renderFrame(interactor.getWorld());

                        }
                        break;






                }




            }
            else if(key=='Q')
            {
                interactor.save();

               System.exit(0);
            }



        }
    }


    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = null;
        Position player_p=new Position(0,0);



        Interact interactor=new Interact();
        int i=0;
        String seed="";
        boolean newgame=false;
        while(i<input.length()){
            char c=input.toUpperCase().charAt(i);
           if(Character.isDigit(c)&&newgame)
           {
               seed+=c;
               i++;
               continue;
           }

            switch(c){
                case 'N':
                   newgame=true;

                    break;
                case 'S':
                    interactor.save();
                    if(!seed.isEmpty())
                    {
                        finalWorldFrame=mazeDemo.generateWorld(Long.valueOf(seed));
                    }

                    break;
                case 'L':
                    break;
                case 'W':
                    interactor.moveUp();
                    break;

                case 'A':
                    interactor.moveLeft();
                    break;
                case 'D':
                    interactor.moveRight();
                    break;

            }




            i++;
        }





        return finalWorldFrame;
    }

    public static void initial(){

        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.BLACK);

        UI.startingUI();

    }



    public static char  getInput(){



        return 's';


    }
}
/**
 * Method used for autograding and testing your code. The input string will be a series
 * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
 * behave exactly as if the user typed these characters into the engine using
 * interactWithKeyboard.
 *
 * Recall that strings ending in ":q" should cause the game to quite save. For example,
 * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
 * 7 commands (n123sss) and then quit and save. If we then do
 * interactWithInputString("l"), we should be back in the exact same state.
 *
 * In other words, both of these calls:
 *   - interactWithInputString("n123sss:q")
 *   - interactWithInputString("lww")
 *
 * should yield the exact same world state as:
 *   - interactWithInputString("n123sssww")
 *
 * @param input the input string to feed to your program
 * @return the 2D TETile[][] representing the state of the world
 */