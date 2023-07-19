package byow.Core;

import byow.TileEngine.TETile;

import java.io.*;

public class fileTest {
    public static final File CWD = new File(System.getProperty("user.dir"));



    public static GameInfo tranGameInfo(File save){

        GameInfo info=null;

        try
        {
            FileInputStream fi=new FileInputStream(save);
            ObjectInputStream in=new ObjectInputStream(fi);

            info=(GameInfo)in.readObject();
            in.close();
            return info;
        }catch(Exception o){

        }



        return info;


    }

    public static void saveGameInfo(File save,GameInfo info){



        try
        {
            FileOutputStream fo=new FileOutputStream(save);
            ObjectOutputStream oo=new ObjectOutputStream(fo);
            oo.writeObject(info);
            oo.flush();
            oo.close();
        }catch(Exception o){

        }






    }

    public static void main(String[] args) {

//        fileTest.saveGameInfo(SAVE,info);

        try
        {
            Dog dog=new Dog("SS",20);
            File SAVE=new File("D:\\java_program\\java_demo2\\proj3\\byow","info");
            ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(SAVE));
            oo.writeObject(dog);
            oo.flush();
            oo.close();
        }catch(Exception o){
            System.out.println("W");
        }

        try
        {
            File SAVE=new File("D:\\java_program\\java_demo2\\proj3\\byow","info");
            ObjectInputStream in=new ObjectInputStream(new FileInputStream(SAVE));

            Dog dog2=(Dog) in.readObject();

            dog2.buck();
            in.close();

        }catch(IOException o){
        System.out.println("S");
        }catch (ClassNotFoundException n){

        }


    }
}
