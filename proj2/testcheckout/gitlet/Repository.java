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


    /* TODO: fill in the rest of this class. */


    public static void init(){
        GitletException ge=new GitletException("已经在当前目录建立gitlet仓库");
        if(GITLET_DIR.exists())
            ge.printStackTrace();
        else{
            GITLET_DIR.mkdir();
            COMMIT_DIR.mkdir();
            BLOBS_DIR.mkdir();
            HELPER_DIR.mkdir();
            HELPERADD_DIR.mkdir();
            StagingArea.init_Staging();
            ComTreeControler.init();
            System.out.print("该目录创建gitlet仓库完成");


        }
    }


    public static void add(String filename){

        File addfile_new=new File(CWD,filename);
        String new_name=sha1(readContentsAsString(addfile_new));

        StagingArea.add(filename,new_name);


    }
    public static void commit(String msg){

        ComTreeControler.commit(msg);


    }
    public static void remove(String filename){
        StagingArea.remove(filename);
    }
    public static void status(){
        if(!StagingArea.ADDSTATUS.exists()){
            try{
                StagingArea.ADDSTATUS.createNewFile();
            }catch (IOException o){

            }
        }


        StagingArea.checkRemove();
        LinkedList<String> branch_name=readObject(ComTreeControler.BRANCH,LinkedList.class);
        String add_info=readContentsAsString(StagingArea.ADDSTATUS);

        String mod_info=readContentsAsString(StagingArea.MODSTATUS);
        String remove_info=readContentsAsString(StagingArea.REMOVESTATUS);
        System.out.println("----------------branch-----------------");
        Iterator<String> ite=branch_name.iterator();
        while(ite.hasNext()){
            System.out.println(ite.next());
        }
        System.out.println("----------------Staging Files-----------------");
        if(add_info!=null)
        System.out.println(add_info);
        System.out.println("----------------Removed Files-----------------");
        if(remove_info!=null)
        System.out.println(remove_info);
        System.out.println("------------Modification Not Staged For Commit-----------------");
        if(mod_info!=null)
            System.out.println(mod_info);
        System.out.println("------------Untracked Files-----------------");
        LinkedList<String> L=StagingArea.findUntrackedFile();
        if(L!=null)
        printUntrackedFile(L);




    }
    public static void log(){
       ComTreeControler.commit_log_parent();
        ComTreeControler.commit_log_parent_MASTER();
    }

    public static void globallog(){
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

        ComTreeControler.find(msg);



    }

    public static void checkout(String filename){


        File f=new File(CWD,filename);
        ComTreeControler.checkoutFile(f);


    }
    public static void checkout(String commitid,String filename){


        File f=new File(CWD,filename);
        ComTreeControler.checkoutFile(commitid,f);




    }

    public static void checkoutbranch(String branch){

        System.out.println("切换分支");

        ComTreeControler.checkoutBranch(branch);




    }

    public static void branch(String name){
        ComTreeControler.branch(name);
    }
    public static void rmbranch(String name){
        ComTreeControler.rmbranch(name);
    }

    public static void reset(String commitid){
        deleteAllfile();
        ComTreeControler.reset(commitid);
    }

    public static void merge(String otherbranch){

        ComTreeControler.merge(otherbranch);



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

    private static void deleteAllfile(){
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



}
