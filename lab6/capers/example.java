package capers;

import java.io.File;
import java.io.IOException;


public class example {
    public static void main(String[] args) {
        File f=new File("dummy.txt");
      try
      {
          f.createNewFile();
      }
      catch(IOException o){

      }


        f.exists();
        Utils.writeContents(f, "Hello World");
        File d = new File("dummy");
        d.mkdir();


    }
}
