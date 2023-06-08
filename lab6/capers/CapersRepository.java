package capers;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

import static capers.Utils.*;

/** A repository for Capers 
 * @author TODO
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD,".capers");
    static final  File STORY_FILE = new File(CAPERS_FOLDER,"story");
    // TODO Hint: look at the `join` function in Utils
    private static int sum=0;

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        // TODO
        CAPERS_FOLDER.mkdir();
        Dog.DOG_FOLDER.mkdir();
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO
        if (!STORY_FILE.exists()) {
            try {
                STORY_FILE.createNewFile();

            }catch (IOException o) {

            }
        }
        String old=readContentsAsString(STORY_FILE);
        String s_final=old+text+"\n";
        writeContents(STORY_FILE,s_final);
        String.format("%s",s_final);
        System.out.println(s_final);

}


    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog dog=new Dog(name,breed,age);
        dog.saveDog();

    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
        File f=new File(Dog.DOG_FOLDER,name);
        if(f.exists()){
            Dog d = Dog.fromFile(name);
            d.haveBirthday();
            writeObject(f,d);
        }
       return ;
    }
}
