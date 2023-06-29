package gitlet;

import java.io.File;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.*;

public class ComTreeControler {
    public static final File MASTER = join(Repository.COMMIT_DIR, "MASTER");
    public static final File HEAD = join(Repository.COMMIT_DIR, "HEAD");

    public static final File COMTREE = join(Repository.HELPER_DIR, "commitTree");
    public static final File COMNAME = join(Repository.HELPER_DIR, "commitName");
    public static final File COMLINK = join(Repository.HELPER_DIR, "commitLink");
    public static final File COMMAX = join(Repository.HELPER_DIR, "commitMax");
    public static final File BRANCH = join(Repository.HELPER_DIR, "branch_list");
    public static final File BRANCH_DIR = join(Repository.HELPER_DIR, "branch");
    public static final File CURRENTBRANCH = join(Repository.HELPER_DIR, "current_branch");
    private static LinkedList<String> branch_name=new LinkedList<String>();
    private static HashMap<String,String>commit_name=new HashMap<>();
    private static  LinkedList<Commit> commit_link=new LinkedList<>();

    public  static  TreeMap<String,Commit> commit_tree=new TreeMap<>();
    private static Commit master;
    private static Commit head;
    private static Commit current_branch;



    public static void init(){
        Commit initial_com=new Commit("initial commit",null);
        initial_com.calcHash();
        master=initial_com;
        head=initial_com;

        current_branch=master;

        File commit_initial = join(Repository.COMMIT_DIR, initial_com.getHash());

        init_create(commit_initial,initial_com);
    }

    public static void commit(String msg){
        if(msg==null)
            Repository.exit("Please enter a commit message.");


        if(StagingArea.hasToCommit())
        {
            head=readObject(HEAD,Commit.class);
            branch_name=readObject(BRANCH,LinkedList.class);
            current_branch=readObject(CURRENTBRANCH,Commit.class);
            commit_tree=readObject(COMTREE,TreeMap.class);


            String cbranch=findCurrentBranch(branch_name);
            Commit commit=createCommit(msg,head,cbranch);
            File newcommit=new File(Repository.COMMIT_DIR,commit.getHash());
            try{
                newcommit.createNewFile();
            }catch (IOException o){

            }

            commit_tree=readObject(COMTREE,TreeMap.class);
            commit_name=readObject(COMNAME,HashMap.class);
            int commitmax=readObject(COMMAX,Integer.class);
            commit_name.put("Commit"+commitmax,commit.getHash());
            commit_tree.put(commit.getHash(),commit);
            head=commit;
            current_branch=head;
            commitmax++;

            if(current_branch.getBranch().equals("master")){
                master=head;
                writeObject(MASTER,master);
            }


            writeObject(CURRENTBRANCH,current_branch);
            writeObject(HEAD,head);
            writeObject(newcommit,commit);
            writeObject(COMTREE,commit_tree);
            writeObject(COMNAME,commit_name);
            writeObject(COMMAX,commitmax);


            StagingArea.clearStatus();
        }else{
            Repository.exit("No changes added to the commit.");

        }

    }

    public static void mergeCommit(Commit commit,String otherbranch){

        head=readObject(HEAD,Commit.class);
        branch_name=readObject(BRANCH,LinkedList.class);

        current_branch=readObject(CURRENTBRANCH,Commit.class);
        File newcommit=new File(Repository.COMMIT_DIR,commit.getHash());
        try{
            newcommit.createNewFile();
        }catch (IOException o){

        }
        commit_tree=readObject(COMTREE,TreeMap.class);
        commit_name=readObject(COMNAME,HashMap.class);
        int commitmax=readObject(COMMAX,Integer.class);
        commit_name.put("Commit"+commitmax,commit.getHash());
        commit_tree.put(commit.getHash(),commit);
        head=commit;
        current_branch=head;
        if(current_branch.getBranch().equals("master")){
            master=head;
            writeObject(MASTER,master);
        }
        writeObject(CURRENTBRANCH,current_branch);
        writeObject(HEAD,head);
        writeObject(newcommit,commit);
        writeObject(COMTREE,commit_tree);
        writeObject(COMNAME,commit_name);
        writeObject(COMMAX,commitmax);
        writeObject(BRANCH,branch_name);

    }

    public static void commit_log(){

        commit_tree=readObject(COMTREE,TreeMap.class);
        commit_name=readObject(COMNAME,HashMap.class);
        int i=commit_tree.size()-1;
        System.out.println("  ");
        System.out.println("----------------Commit-----------------");

        while(i>=0){

            String hashname=commit_name.get("Commit"+i);

            Commit c= commit_tree.get(hashname);
            c.printInfo();
            i--;
        }




    }

    public static void commit_log_parent(){

        head=readObject(HEAD,Commit.class);


        while(head!=null){
            head.printInfo();
            head=head.getParent();
        }

    }
    public static void commit_log_parent_MASTER(){

        Commit c=readObject(MASTER,Commit.class);

        System.out.println("  ");
        System.out.println("----------------MASTER Commit-----------------");

        while(c!=null){
            c.printInfo();
            c=c.getParent();
        }

    }



    private static Commit createCommit(String msg,Commit parent,String branch){
        HashMap<String,String>  blobs=StagingArea.getBlobs();





        Commit commit=new Commit(msg,parent);
        commit.timeSet();
        commit.setBlobs(blobs);
        commit.calcHash();
        commit.setBranch(branch);
        String hashname=commit.getHash();


        return commit;
    }


    private static Commit createMergeCommit(String msg,Commit parent,Commit parent2,
                                            String branch,HashMap<String,String> blobs){

        MergeCommit commit=new MergeCommit(msg,parent,parent2);
        commit.timeSet();
        commit.setBlobs(blobs);
        commit.calcHash();
        commit.setBranch(branch);
        String hashname=commit.getHash();


        return commit;
    }





    public static Commit getHead(){
        Commit c=readObject(HEAD,Commit.class);
        return c;
    }


    public static void init_create(File com,Commit com_in){
        try{
            BRANCH_DIR.mkdir();
            COMNAME.createNewFile();
            COMTREE.createNewFile();
            com.createNewFile();
            BRANCH.createNewFile();
            MASTER.createNewFile();
            HEAD.createNewFile();
            CURRENTBRANCH.createNewFile();
            StagingArea.ADDSTATUS.createNewFile();
            StagingArea.MODSTATUS.createNewFile();
            StagingArea.REMOVESTATUS.createNewFile();
            COMLINK.createNewFile();
            COMMAX.createNewFile();
            int i=1;


            Utils.writeObject(com,com_in);
            Utils.writeObject(MASTER,master);
            Utils.writeObject(HEAD,head);


            String s=com_in.getHash();

            commit_name.put("Commit0",s);
            commit_tree.put(s,com_in);
            commit_link.addFirst(com_in);


            writeObject(BRANCH,branch_name);
            writeObject(CURRENTBRANCH,current_branch);
            branch("master");
            writeObject(COMTREE,commit_tree);
            writeObject(COMNAME,commit_name);
            writeObject(COMLINK,commit_link);
            writeObject(COMMAX,i);


        }catch (IOException o){
        }
    }
    public static Commit load(String id){
        commit_tree=readObject(COMTREE,TreeMap.class);

        Commit c=commit_tree.get(id);
        return c;
    }

    public static void find(String msg){
        commit_tree=readObject(COMTREE,TreeMap.class);
        Iterator<Map.Entry<String,Commit>> ite=commit_tree.entrySet().iterator();
        int i=0;
        // Through the Map.Entry class ,get the hashmap's iterator

        while(ite.hasNext()){
            Map.Entry<String,Commit> m=ite.next();
           Commit c=m.getValue();
            String s=c.message();
            if(s.equals(msg)){
                String s2=c.getHash();
                System.out.println(s2);

            }
            i++;
        }

        if(i==0){
            Repository.exit("Found no commit with that message.");
        }

    }
    public static boolean findtracked(String filename){
       Commit c=getHead();

        if(c.findtracked(filename))
        {
            return true;
        }else{
            return false;
        }


    }
    public static void checkoutFile(File f){
        head=ComTreeControler.getHead();

       head.writeblobs(f);



    }

    public static void checkoutFile(String commitid,File f){
        Commit c=getCommitwithId(commitid);

        c.writeblobs(f);

    }

    public static void checkoutBranch(String branch){

            branch_name=readObject(BRANCH,LinkedList.class);
            current_branch=readObject(CURRENTBRANCH,Commit.class);

            if(branch_name.contains("*"+branch))
            {
                Repository.exit("No need to checkout the current branch.");
                return;
            }

            if(!branch_name.contains(branch))
            {
                Repository.exit("No such branch exists.");
                return;
            }

            savebranch();



            branch_name.remove(branch);
            branch_name.remove("*"+current_branch.getBranch());
            branch_name.addFirst(current_branch.getBranch());
            branch_name.addFirst("*"+branch);
            writeObject(BRANCH,branch_name);


        if(branch_name.contains("*"+branch)){

            File branch_file=new File(BRANCH_DIR,branch);
            current_branch=readObject(branch_file,Commit.class);
            head=current_branch;
            current_branch.writeAllblobs();
            writeObject(HEAD,head);
            writeObject(CURRENTBRANCH,current_branch);


        }else{
            Repository.exit("No such branch exists.");
        }



    }

    private static Commit getCommitwithId(String id){
        commit_tree=readObject(COMTREE,TreeMap.class);
        String key="";
        List<String> L=Utils.plainFilenamesIn(Repository.COMMIT_DIR);
        int i=0;
        while(i<L.size()){

            String s=L.get(i);
            if(s.length()>6){
                s=s.substring(0,6);
            }
			String s2=id.substring(0,6);
            if(s2.equals(s)){
                key=L.get(i);
                break;
            }

            i++;
        }
        if(key.equals("")){
            System.out.println("No commit with that id exists.");
            return null;
        }


        return commit_tree.get(key);


    }


    public static void branch(String name){



        branch_name=readObject(BRANCH,LinkedList.class);
        current_branch=readObject(CURRENTBRANCH,Commit.class);
        head=readObject(HEAD,Commit.class);
        if(!name.equals("master"))
        {
            savebranch();
            branch_name.remove("*"+current_branch.getBranch());
            branch_name.addFirst(current_branch.getBranch());
            branch_name.addFirst("*"+name);

        }
        else{

            branch_name.addFirst("*"+name);
        }


        current_branch=head;

        File f=new File(BRANCH_DIR,name);
        try{
            f.createNewFile();
            writeObject(f,current_branch);
        }catch (IOException o){

        }

        writeObject(CURRENTBRANCH,current_branch);
        writeObject(BRANCH,branch_name);


    }

    private static void savebranch(){
        current_branch=readObject(CURRENTBRANCH,Commit.class);
        branch_name=readObject(BRANCH,LinkedList.class);
        String s="";
        Iterator<String> ite=branch_name.iterator();
        while(ite.hasNext()){
            String a=ite.next();



            if(a.charAt(0)=='*')
                s=a.substring(1);
        }
        File f=new File(BRANCH_DIR,s);

        writeObject(f,current_branch);


    }
    private static String findCurrentBranch(LinkedList<String> L){
        Iterator<String> ite=L.iterator();
        while(ite.hasNext())
        {
            String s=ite.next();
            if(s.charAt(0)=='*')
                return s.substring(1);
        }
        return "";
    }

    public static void rmbranch(String name){
        File f=new File(BRANCH_DIR,name);
        branch_name=readObject(BRANCH,LinkedList.class);
        head=readObject(HEAD,Commit.class);

        if(!branch_name.contains(name)){
            Repository.exit("A branch with that name does not exist.");
            return ;
        }

        if(head.getBranch().equals(name))
        {
            Repository.exit("Cannot remove the current branch.");
        }
        else{

            branch_name.remove(name);
            f.delete();
            writeObject(BRANCH,branch_name);
        }






    }

    public static void reset(String commitid){
        StagingArea.clearStatus();

        Commit c=getCommitwithId(commitid);
        if(c!=null)
        {
            Repository.deleteAllfile();
            c.writeAllblobs();
            head=c;
            current_branch=c;
            writeObject(HEAD,head);
            writeObject(CURRENTBRANCH,current_branch);
        }





    }


    public static void merge(String otherbranch){
        head=readObject(HEAD,Commit.class);
        File c=new File(BRANCH_DIR,otherbranch);
        Commit other_branch=readObject(c,Commit.class);
        Commit split=findSplit(otherbranch);
        branch_name=readObject(BRANCH,LinkedList.class);
        if(!branch_name.contains(other_branch))
            Repository.exit("A branch with that name does not exist.");
        if(branch_name.contains("*"+otherbranch))
            Repository.exit("Cannot merge a branch with itself.");
        if(findUntracked(head.getblobsSet(),head))
        {
            Repository.exit("There is an untracked file in the way; delete it, or add and commit it first.");
        }

        if(split.getHash().equals(other_branch.getHash())){

            System.out.println("Given branch is an ancestor of the current branch.");
            return ;
        }
        if(split.getHash().equals(head.getHash())){

            System.out.println("Current branch fast-forwarded.");
            head=other_branch;
            writeObject(HEAD,head);
            return ;
        }




        HashMap<String,String> blobs_head=head.getBlobs();
        HashMap<String,String> blobs_split=split.getBlobs();
        HashMap<String,String> blobs_other_branch=other_branch.getBlobs();

        SortedSet<String> set_head=  new TreeSet<>(blobs_head.keySet());
        SortedSet<String> set_split=  new TreeSet<>(blobs_split.keySet());
        SortedSet<String> set_other_branch=  new TreeSet<>(blobs_other_branch.keySet());
        HashMap<String ,String> blobs=head.getBlobs();

        Iterator<String> ite_head=set_head.iterator();
        Iterator<String> ite_other_branch=set_other_branch.iterator();
        Iterator<String> ite_split=set_split.iterator();

        while(ite_head.hasNext()){
            String head_name=ite_head.next();
            String head_hash=blobs_head.get(head_name);
            String branch_hash=blobs_other_branch.get(head_name);
            String split_hash=blobs_split.get(head_name);
            boolean isExist= blobs_other_branch.containsKey(head_name)&&
                    !blobs_other_branch.get(head_name).equals("NULL") && !blobs_head.get(head_name).equals("NULL");

            if(split_hash!=null){
                if(isExist){

                    if(blobs_split.containsKey(head_name)&&!blobs_split.get(head_name).equals("NULL")){

                        if(!head_hash.equals(split_hash)&&head_hash.equals(branch_hash))
                        {
                            //same way

                        }

                        if(split_hash.equals(branch_hash)&&!head_hash.equals(split_hash)){
                            blobs.put(head_name,head_hash);

                            //head modified

                        }
                        if(split_hash.equals(head_hash)&&!branch_hash.equals(split_hash)){

                            blobs.put(head_name,branch_hash);

                            //branch modified
                        }
                        if(!split_hash.equals(head_hash)&&
                                !split_hash.equals(branch_hash)&&!head_hash.equals(branch_hash))
                        {

                            conflict(head_hash,head_name,branch_hash,otherbranch);
                            System.out.println("Encountered a merge conflict");
                            return;
                            //conflicted

                        }
                        //checkout which has modified since split
                    }else{
                        conflict(head_hash,head_name,branch_hash,otherbranch);
                        System.out.println("Encountered a merge conflict");
                        return;
                        //conflicted
                    }



                }
            }



            if(branch_hash!=null){
                if(branch_hash.equals("NULL")&&split_hash!=null){

                    if (!head_hash.equals("NULL")&&split_hash.equals("NULL"))
                    {
                        blobs.put(head_name,head_hash);

                        //head add
                    }
                    else if (split_hash.equals(head_hash))
                    {
                        blobs.put(head_name,"NULL");

                        //remove head_name
                    }
                    else if(head_hash.equals("NULL")){
                        //same way do nothing

                    }

                }
            }
           if(head_hash!=null){
               if(head_hash.equals("NULL")&&split_hash!=null){

                   if (!branch_hash.equals("NULL")&&split_hash.equals("NULL"))
                   {
                       blobs.put(head_name,branch_hash);

                       //branch add
                   }
                   else if (split_hash.equals(branch_hash))
                   {
                       blobs.put(head_name,"NULL");

                       //remove branch_name
                   }
                   else if (branch_hash.equals("NULL")){
                       //same way do nothing

                   }


               }
           }




        }
       //deal with head
        while(ite_other_branch.hasNext()){
            String branch_name=ite_other_branch.next();
            String branch_hash=blobs_other_branch.get(branch_name);

            if(!blobs_head.containsKey(branch_name)){

               blobs.put(branch_name,branch_hash);

                    //checkout if it exist in the head
                }else{

                    continue;
                }

            }



        String cbranch=head.getBranch();
        Commit merge_commit=createMergeCommit("merge "+otherbranch,head,other_branch,cbranch,blobs);
        mergeCommit(merge_commit,otherbranch);


        }
        //find what is in other branch but not in the head




    private static Commit findSplit(String otherbranch){
        head=readObject(HEAD,Commit.class);
        File c=new File(BRANCH_DIR,otherbranch);

        Commit other_branch=readObject(c,Commit.class);
        Commit start=head;
        Commit branchc=other_branch;
        int start_i;

        int a_size=head.getBranch_size();
        int b_size=other_branch.getBranch_size();

        int cmp=a_size-b_size;

        if(cmp>0){
            int i=cmp;

           start=head;
           branchc=other_branch;

            while(i>0){
                start=start.getParent();
                i--;
            }


        }
        else if(cmp<0){
            int i=-cmp;
          start=other_branch;
         branchc=head;
            while(i>0){
                start=start.getParent();

                i--;
            }

        }


            while(!branchc.getHash().equals(start.getHash())){
                start=start.getParent();
                branchc=branchc.getParent();

            }
            return start;


    }

    private static void conflict(String head_hash,String head_name,String branch_hash,String otherbranch){

        String a="Here is some content not affected by the conflict\n";
        String b="<<<<<<< HEAD\n";
        File head_file=new File(Repository.BLOBS_DIR,head_hash);
        File branch_file=new File(Repository.BLOBS_DIR,branch_hash);
        String d=readContentsAsString(head_file);
        String b2="\n=======\n";
        String d2=readContentsAsString(branch_file);
        String e="\n>>>>>>> "+otherbranch+"\n";
        String s=a+b+d+b2+d2+e;
        File cwd_file=new File(Repository.CWD,head_name);
        writeContents(cwd_file,s);

    }

    private static boolean findUntracked(Set<String> set,Commit head){
            List<String> file_list=plainFilenamesIn(Repository.CWD);
            Iterator<String> ite=file_list.iterator();
            while(ite.hasNext())
            {
                if(!set.contains(ite.next()))
                    return true;


            }
            return false;

    }


}

