package gitlet;

import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.util.*;
import static gitlet.Utils.*;
import static gitlet.ComTreeControler.*;

public class Remote {

    public static HashMap<String,File> remote_list=new HashMap<>();
    public static final File  REMOTE=join(Repository.REMOTE_DIR, "remote");;

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
        writeObject(REMOTE,remote_list);



    }

    public static void rmremote(String remotename){


        remote_list=readObject(REMOTE,HashMap.class);

        if(!remote_list.containsKey(remotename))
            Repository.exit("A remote with that name does not exist.");

        remote_list.remove(remotename);

        writeObject(REMOTE,remote_list);


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

    public static void fetch (String remote,String branch){
        LinkedList<String> branch_name=readObject(BRANCH,LinkedList.class);
        File remote_branchname=join(getRemoteRepo(remote),"commit","helper","branch_list");
        LinkedList<String> remote_branchname_list=readObject(remote_branchname,LinkedList.class);

        if(!remote_branchname_list.contains(branch))
        Repository.exit("That remote does not have that branch.");


        String current_branch=remote+"/"+branch;
        String curbranch=readContentsAsString(CWBRANCH);
        if(!branch_name.contains(branch)){

            branch(current_branch);
            checkoutBranch(current_branch);
        }
        if(!curbranch.equals(current_branch))
        checkoutBranch(current_branch);

        Commit head=readObject(HEAD,Commit.class);
        Commit remote_branch_commit=getCommitwithId(readContentsAsString(CURRENTBRANCH));

        copyCommit(head,remote_branch_commit);
        copyBlobs(head,remote_branch_commit,remote);








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

    private static void copyCommit(Commit head,Commit remote){
        Stack<String> stack=new Stack<>();
        Commit c_remote=remote;

        while(!c_remote.getHash().equals(head.getHash())){
            stack.push(c_remote.getHash());
            File f=new File(Repository.COMMIT_DIR,c_remote.getHash());
            if(f.exists()){
                stack.pop();
                if(!c_remote.getParent().isEmpty())
                    c_remote=getCommitwithId(c_remote.getParent().get(0));
                continue;
            }else{
                try{
                    f.createNewFile();


                }catch (IOException o){

                }
            }

            if(!c_remote.getParent().isEmpty())
                c_remote=getCommitwithId(c_remote.getParent().get(0));


        }

        while(!stack.isEmpty()){
            String hash=stack.pop();
            Commit c=getCommitwithId(hash);
            c.resetParent(head.getHash());
            File f=new File(Repository.COMMIT_DIR,c.getHash());
            writeObject(f,c);
            head=c;
        }
        writeObject(HEAD,head);

    }

    private static void copyBlobs(Commit head,Commit remote,String remotename){
        List<String> file_list=plainFilenamesIn(Repository.BLOBS_DIR);
        Commit c_remote=remote;

        while(!c_remote.getParent().isEmpty()){
            Set<String> blobs_set=c_remote.getblobsSet();
            Iterator<String> ite=blobs_set.iterator();

            while(ite.hasNext()){
                HashMap<String,String> blob=c_remote.getBlobs();
                if(blob!=null)
                {
                    String hash=blob.get(ite.next());
                    if(!file_list.contains(hash))
                    {
                        File f=getRemoteBlobs(remotename,hash);
                        createBlobs(hash,readContents(f));
                    }

                }
            }

            if(!c_remote.getParent().isEmpty())
                c_remote=getCommitwithId(c_remote.getParent().get(0));

        }




    }
    private static void createBlobs(String hash,byte[] data){
        File f=new File(Repository.BLOBS_DIR,hash);
        try{
            f.createNewFile();
            writeContents(f,data);
        }catch (IOException o){

        }

    }
    private static File getRemoteBlobs(String remote,String hash){
            File remote_repo=getRemoteRepo(remote);
            return join(remote_repo,"blobs",hash);

    }
    private static File getRemoteRepo(String remote){
        remote_list=readObject(REMOTE,HashMap.class);

        if(remote_list.containsKey(remote))
            return remote_list.get(remote);

        return null;


    }

}
