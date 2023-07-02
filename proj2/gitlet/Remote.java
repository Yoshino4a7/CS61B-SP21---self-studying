package gitlet;

import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.util.*;
import static gitlet.Utils.*;
import static gitlet.ComTreeControler.*;

public class Remote {

    public static Map<String,File> remote_list=new HashMap<>();
    public static final File  REMOTE=new File(Repository.REMOTE_DIR,"REMOTE");

    public static void init(){


        if(!REMOTE.exists()){

            try{
                REMOTE.createNewFile();
                writeObject(REMOTE,new HashMap<String,File>());
            }catch (IOException o){

            }

        }

    }


    public static void addremote(String remotename,String remotepath){
        File remote_gitlet=new File(remotepath);

        remote_list=readObject(REMOTE,HashMap.class);

        if(remote_list.containsKey(remotename))
            Repository.exit("A remote with that name already exists.");

        remote_list.put(remotename,remote_gitlet);
        writeObject(REMOTE,remote_gitlet);



    }

    public static void rmremote(String remotename){


        remote_list=readObject(REMOTE,HashMap.class);

        if(!remote_list.containsKey(remotename))
            Repository.exit("A remote with that name does not exist.");

        remote_list.remove(remotename);




    }

    public static void push (String remote,String branch){
        remote_list=readObject(REMOTE,HashMap.class);
        File remote_repo=remote_list.get(remote);
        if(remote_repo==null||!remote_repo.exists())
            Repository.exit("Remote directory not found.");

        File branchfile=join(remote_repo,"commit","helper","branch",branch);
        Commit remote_branch_commit=getCommitwithId(readContentsAsString(branchfile));
        Commit current_branch_commit=
                getCommitwithId(readContentsAsString(ComTreeControler.CURRENTBRANCH));

        if(checkIntheHistory(remote_branch_commit,current_branch_commit)){
            changeRemote(branchfile,branch,current_branch_commit);
        }else{

            Repository.exit("Please pull down remote changes before pushing.");
        }


    }


    private static void changeRemote(File remote_repo,String branch,Commit current_head){

        File remote_head=join(remote_repo,"commit","helper","HEAD");
        File remote_comtree=join(remote_repo,"commit","helper","commitTree");

        TreeMap<String, Commit> commit_tree = readObject(remote_comtree,TreeMap.class);
        Commit head=readObject(remote_head,Commit.class);
        current_head.resetParent(head.getHash());
        current_head.resetBranch(branch);
        commit_tree.put(current_head.getHash(),current_head);
        writeObject(remote_head,current_head);
        writeObject(remote_comtree,commit_tree);

    }

    private static boolean checkIntheHistory(Commit remote_commit,Commit current){

        String remote_hash=remote_commit.getHash();
        Commit c=current;
        while(!c.getParent().isEmpty()){

            if(c.getHash().equals(remote_hash))
            return true;
            c=getCommitwithId(c.getParent().get(0));

        }
        if(c.getHash().equals(remote_hash))
            return true;

        return false;

    }


}
