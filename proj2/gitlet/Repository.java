package gitlet;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMIT_DIR = join(GITLET_DIR, "commit");
    public static final File BLOBS_DIR = join(GITLET_DIR, "blobs");
    public static final File HELPER_DIR = join(COMMIT_DIR, "helper");
    public static final File HELPERADD_DIR = join(BLOBS_DIR, "helper");
    public static final File REMOTE_DIR = join(GITLET_DIR, "remote");



    /* TODO: fill in the rest of this class. */


    public static void init(){

        if(GITLET_DIR.exists())
            exit("A Gitlet version-control system already exists in the current directory.");


               GITLET_DIR.mkdir();
               COMMIT_DIR.mkdir();
               BLOBS_DIR.mkdir();
               HELPER_DIR.mkdir();
               HELPERADD_DIR.mkdir();
               REMOTE_DIR.mkdir();

               StagingArea.init_Staging();
               ComTreeControler.init();
               Remote.init();






    }


    public static void add(String filename){

        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }

        File addfile_new=new File(CWD,filename);

        String new_name="";
        if(addfile_new.exists())
        new_name=sha1(readContents(addfile_new));



        StagingArea.add(filename,new_name);


    }
    public static void commit(String msg){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }
        if(msg.equals(""))
        {
            exit("Please enter a commit message.");
        }
        ComTreeControler.commit(msg);


    }
    public static void remove(String filename){
        if(!GITLET_DIR.exists()){
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        StagingArea.remove(filename);
    }
    public static void status(){
        if(!GITLET_DIR.exists()){
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }

        if(!StagingArea.ADDSTATUS.exists()){
            try{
                StagingArea.ADDSTATUS.createNewFile();
            }catch (IOException o){

            }
        }


        StagingArea.checkRemove();
        Commit head=ComTreeControler.getHead();
        LinkedList<String> branch_name=readObject(ComTreeControler.BRANCH,LinkedList.class);
        LinkedList<String> untrackfile=StagingArea.getUntrack(head);
        LinkedList<String> modify=StagingArea.getModify(head);
        String add_info=readContentsAsString(StagingArea.ADDSTATUS);
        String mod_info=StagingArea.getNameList(modify);
        String remove_info=readContentsAsString(StagingArea.REMOVESTATUS);
        String untrack_info=StagingArea.getNameList(untrackfile);

        System.out.println("=== Branches ===");
        Iterator<String> ite=branch_name.iterator();
        while(ite.hasNext()){
            System.out.println(ite.next());
        }
        System.out.println("");
        System.out.println("=== Staged Files ===");
        if(add_info!=null)
        System.out.print(add_info);
        System.out.println("");
        System.out.println("=== Removed Files ===");
        if(remove_info!=null)
        System.out.print(remove_info);
        System.out.println("");
        System.out.println("=== Modifications Not Staged For Commit ===");
        if(mod_info!=null)
            System.out.print(mod_info);
        System.out.println("");
        System.out.println("=== Untracked Files ===");
        if(mod_info!=null)
            System.out.print(untrack_info);
        System.out.println("");




    }
    public static void log(){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }
       ComTreeControler.commit_log_parent();

    }

    public static void globallog(){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }
        List<String> L= Utils.plainFilenamesIn(COMMIT_DIR);
        int i=0;
        while(i<L.size()){

            String s=L.get(i);
            if(s.equals("HEAD"))
            {
                i++;
                continue;
            }

            if(s.equals("MASTER"))
            {
                i++;
                continue;
            }

            if(s.equals("helper"))
            {
                i++;
                continue;
            }


            Commit c=ComTreeControler.load(s);
            c.printInfo();
            i++;

        }




    }

    public static void find(String msg){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }
        ComTreeControler.find(msg);



    }

    public static void checkout(String filename){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }

        File f=new File(CWD,filename);
        ComTreeControler.checkoutFile(f);


    }
    public static void checkout(String commitid,String filename){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }

        File f=new File(CWD,filename);
        ComTreeControler.checkoutFile(commitid,f);




    }

    public static void checkoutbranch(String branch){

        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }

        ComTreeControler.checkoutBranch(branch);




    }

    public static void branch(String name){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }
        ComTreeControler.branch(name);
    }
    public static void rmbranch(String name){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }
        ComTreeControler.rmbranch(name);
    }

    public static void reset(String commitid){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }

        ComTreeControler.reset(commitid);
    }

    public static void merge(String otherbranch){
        if(!GITLET_DIR.exists()){
            exit("Not in an initialized Gitlet directory.");
            return;
        }
        if(StagingArea.hasToCommit())
            exit("You have uncommitted changes.");

        ComTreeControler.merge(otherbranch);



    }

    public static void addremote(String otherremote,String remotepath){


    Remote.addremote(otherremote,remotepath);



    }
    public static void rmremote(String otherremote){


        Remote.rmremote(otherremote);



    }

    public static void push(String remote,String branch){

        Remote.push(remote,branch);

    }

    public static void fetch(String remote,String branch){


        Remote.fetch(remote,branch);


    }


    private static void printUntrackedFile(List<String> L){
         Iterator<String> i=  L.iterator();
        while(i.hasNext()){

            System.out.println(i.next());

        }


    }





    private static String hash_compute(String s){
        return sha1(s);
    }

    public static void deleteAllfile(){
        List<String> L= Utils.plainFilenamesIn(CWD);

        int i=0;
        while(i<L.size()){
            String s=L.get(i);
            File f=new File(Repository.CWD,s);
            if(!f.isFile())
            {
                i++;

                continue;

            }
            f.delete();
            i++;
        }


    }

    public static void exit(String msg){
        if(msg!=null){
            System.out.println(msg);
        }
        System.exit(0);
    }

}
