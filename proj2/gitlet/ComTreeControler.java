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
    public static final File CWBRANCH = join(Repository.HELPER_DIR, "c_branchname");
    private static LinkedList<String> branch_name=new LinkedList<String>();
    private static HashMap<String,String>commit_name=new HashMap<>();
    private static  LinkedList<Commit> commit_link=new LinkedList<>();

    public  static  TreeMap<String,Commit> commit_tree=new TreeMap<>();
    private static Commit master;
    private static Commit head;
    private static Commit current_branch;



    public static void init(){
        Commit initial_com=new Commit("initial commit",new LinkedList<String>());
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
            LinkedList<String> parents=new LinkedList<String>();
            parents.addLast(head.getHash());
            Commit commit=createCommit(msg,parents,cbranch);
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
           List<String> parents=head.getParent();
            if(!parents.isEmpty())
            head=getCommitwithId(parents.get(0));
            else
                head=null;

        }

    }
    public static void commit_log_parent_MASTER(){

        Commit c=readObject(MASTER,Commit.class);

        System.out.println("  ");
        System.out.println("----------------MASTER Commit-----------------");

        while(c!=null){
            c.printInfo();
//            c=c.getParent();
        }

    }



    private static Commit createCommit(String msg,LinkedList<String> parent,String branch){

        StagingArea.clearRemoval();

        HashMap<String,String>  blobs=StagingArea.getBlobs();

        HashMap<String,String> remove=StagingArea.getRemoval();

        Commit commit=new Commit(msg,parent);
        commit.timeSet();
        commit.setBlobs(blobs);
        commit.calcHash();
        commit.setBranch(branch);



        return commit;
    }


    private static Commit createMergeCommit(String msg,LinkedList<String> parent_list,
                                            String branch,HashMap<String,String> blobs,String other){

        MergeCommit commit=new MergeCommit(msg,parent_list);
        commit.timeSet();
        commit.setBlobs(blobs);
        commit.calcHash();
        commit.setBranch(branch);
        commit.setGiven_branch(other);
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
            CWBRANCH.createNewFile();
            StagingArea.ADDSTATUS.createNewFile();
            StagingArea.MODSTATUS.createNewFile();
            StagingArea.REMOVESTATUS.createNewFile();
            COMLINK.createNewFile();
            COMMAX.createNewFile();
            int i=1;

            head=com_in;

            Utils.writeObject(com,com_in);
            Utils.writeObject(MASTER,master);
            Utils.writeObject(HEAD,head);



            String s=com_in.getHash();

            commit_name.put("Commit0",s);
            commit_tree.put(s,com_in);
            commit_link.addFirst(com_in);

            current_branch=head;
            writeObject(BRANCH,branch_name);
            writeObject(CURRENTBRANCH,current_branch);

            writeContents(CWBRANCH,"master");
            writeObject(COMTREE,commit_tree);
            writeObject(COMNAME,commit_name);
            writeObject(COMLINK,commit_link);
            branch("master");
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
                i++;
            }

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
        if(head==null)
            return;
       head.writeblobs(f);



    }

    public static void checkoutFile(String commitid,File f){
        Commit c=getCommitwithId(commitid);
        if(c==null)
            return;
        c.writeblobs(f);

    }

    public static void checkoutBranch(String branch){
        File branch_file=new File(BRANCH_DIR,branch);


        head=getHead();

        if(!branch_file.exists()){

            Repository.exit("No such branch exists.");
            return;
        }
        if(head.findAllUntracked())
        {
            Repository.exit("There is an untracked file in the way; delete it, or add and commit it first.");
        }


            branch_name=readObject(BRANCH,LinkedList.class);
            current_branch=readObject(CURRENTBRANCH,Commit.class);



            if(getCurrentBranch().equals(branch))
            {
                Repository.exit("No need to checkout the current branch.");
                return;
            }

            savebranch();
            writeContents(CWBRANCH,branch);


            branch_name.remove(branch);
            branch_name.remove("*"+current_branch.getBranch());
            branch_name.addFirst(current_branch.getBranch());
            branch_name.addFirst("*"+branch);
            writeObject(BRANCH,branch_name);




        if(branch_file.exists()){

                current_branch=readObject(branch_file,Commit.class);
                head=current_branch;
                if(head.getBlobs()==null)
                {
                    writeObject(StagingArea.ADDAREA,new HashMap<String,String>());
                }
                else{
                    writeObject(StagingArea.ADDAREA,head.getBlobs());
                }



                Repository.deleteAllfile();
                current_branch.writeAllblobs();

                writeObject(HEAD,head);
                writeObject(CURRENTBRANCH,current_branch);




        }else{
            Repository.exit("No such branch exists.");
        }



    }

    public static Commit getCommitwithId(String id){
        commit_tree=readObject(COMTREE,TreeMap.class);
        String key="";
        List<String> L=Utils.plainFilenamesIn(Repository.COMMIT_DIR);
        int i=0;
        while(i<L.size()){

            String s=L.get(i);
            String s2="";
            if(s.length()>6){

                s2=s.substring(0,id.length());
            }

            if(s2.equals(id)){
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
        commit_tree=readObject(COMTREE,TreeMap.class);
        branch_name=readObject(BRANCH,LinkedList.class);
        current_branch=readObject(CURRENTBRANCH,Commit.class);
        head=readObject(HEAD,Commit.class);
        if(branch_name.contains("*"+name)){
            Repository.exit("A branch with that name already exists.");
        }
        if(!branch_name.contains(name)){
            if(name.equals("master"))
                branch_name.addLast("*"+name);
            else{
                branch_name.addLast(name);
            }
            head.addBranch(name);
            current_branch.addBranch(name);
            writeObject(HEAD,head);
            File newcommit=new File(Repository.COMMIT_DIR,head.getHash());
            commit_tree.put(head.getHash(),head);
            writeObject(newcommit,head);
            writeObject(CURRENTBRANCH,current_branch);
            writeObject(BRANCH,branch_name);
        }
        else{
            Repository.exit("A branch with that name already exists.");
        }

        File f=new File(BRANCH_DIR,name);
        try{
            f.createNewFile();
            writeObject(f,current_branch);
        }catch (IOException o){

        }

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

        if(!f.exists()){
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
        head=ComTreeControler.getHead();
        Commit c=getCommitwithId(commitid);

        if(head.findAllUntracked())
            Repository.exit("There is an untracked file in the way; delete it, or add and commit it first.");

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
        File branch=new File(BRANCH_DIR,otherbranch);
        if(!branch.exists())
            Repository.exit("A branch with that name does not exist.");
        Commit other_branch=readObject(c,Commit.class);
        Commit split=findSplit(otherbranch);
        branch_name=readObject(BRANCH,LinkedList.class);

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

         HashMap<String ,String> blobs=head.getBlobs();
        LinkedList<String> conflict_list=new LinkedList<>();

       boolean IsConflict=selectFilesintoBlobs(blobs,otherbranch,head,other_branch,split,conflict_list);




        wirteMergeBlobs(blobs,conflict_list);

        if(IsConflict)
        {
            System.out.println("Encountered a merge conflict");
            return;
        }




        String cbranch=head.getBranch();
        LinkedList<String> parent_list=new LinkedList<String>();
        parent_list.addLast(head.getHash());
        parent_list.addLast(other_branch.getHash());
        Commit merge_commit=
                createMergeCommit
                        ("Merged " + otherbranch + " into " + cbranch + ".",parent_list,cbranch,blobs,otherbranch);

        mergeCommit(merge_commit,otherbranch);


        }
        //find what is in other branch but not in the head




    private static Commit findSplit(String otherbranch){
        head=readObject(HEAD,Commit.class);
        File c=new File(BRANCH_DIR,otherbranch);

        Commit other_branch=readObject(c,Commit.class);
        Commit start=head;
        Commit branchc=other_branch;
//        int start_i;
//
//        int[]select= mergeCommitChange(head,other_branch);
//
//        int cmp= select[1]- select[2];
//
//        if(cmp>0){
//            int i=cmp;
//
//           start=head;
//           branchc=other_branch;
//
//            while(i>0){
//
//
//          start=getCommitwithId(start.getParent().get(select[0]));
//                i--;
//            }
//
//
//        }
//        else if(cmp<0){
//            int i=-cmp;
//          start=other_branch;
//         branchc=head;
//            while(i>0){
//                start=getCommitwithId(start.getParent().get(select[0]));
//
//                i--;
//            }
//
//        }
//
//
//            while(!branchc.getHash().equals(start.getHash())){
//                start=getCommitwithId(start.getParent().get(select[0]));
//                branchc=getCommitwithId(branchc.getParent().get(select[0]));
//
//            }

        if(start instanceof MergeCommit){
            findSplit_help(start,otherbranch);


        }
        else{
            while(start!=null){
                start.printbranch();
                if(start.isSplit(otherbranch))
                    return start;
                else
                {
                    if(!start.getParent().isEmpty())
                        start=getCommitwithId(start.getParent().get(0));
                    else
                        start=null;
                }


            }
            return null;
        }



        if(branchc instanceof MergeCommit){
           return findSplit_help(branchc,otherbranch);
        }
        else{
            while(branchc!=null){

                if(branchc.isSplit(otherbranch))
                    return head;
                else{
                    if(!branchc.getParent().isEmpty())
                        branchc=getCommitwithId(branchc.getParent().get(0));
                    else
                        head=null;
                }



            }
            return null;
        }




    }

    private static void conflict
            (String head_hash,String head_name,String branch_hash,String otherbranch,HashMap<String,String> blobs){
        String s="";
        if(head_hash==null||branch_hash==null){
            String d="";
            String d2="";
            File head_file;
            File branch_file;
            String b="<<<<<<< HEAD\n";
            if(head_hash!=null)
            {
                head_file=new File(Repository.BLOBS_DIR,head_hash);
                d=readContentsAsString(head_file);
            }

            if(branch_hash!=null)
            {
                branch_file=new File(Repository.BLOBS_DIR,branch_hash);
                d2=readContentsAsString(branch_file);
            }

            String b2="\n=======\n";

            String e=">>>>>>>";

            s=b+d+b2+d2+e;

        }else
        {

            String b="<<<<<<< HEAD\n";
            File head_file=new File(Repository.BLOBS_DIR,head_hash);
            File branch_file=new File(Repository.BLOBS_DIR,branch_hash);
            String d=readContentsAsString(head_file);
            String b2="\n=======\n";
            String d2=readContentsAsString(branch_file);
            String e="\n>>>>>>>";
            s=b+d+b2+d2+e;

        }

        File cwd_file=new File(Repository.CWD,head_name);
        try{
            cwd_file.createNewFile();
            writeContents(cwd_file,s);
        }catch (IOException o){

        }


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

    private static boolean selectFilesintoBlobs
            (HashMap<String,String> blobs,String otherbranch,Commit head,Commit other_branch,
             Commit split,LinkedList<String> conflict_list){
        boolean IsConflict=false;
        HashMap<String,String> blobs_head=head.getBlobs();
        HashMap<String,String> blobs_split=split.getBlobs();
        HashMap<String,String> blobs_other_branch=other_branch.getBlobs();

        SortedSet<String> set_head=  new TreeSet<>(blobs_head.keySet());
        SortedSet<String> set_split=  new TreeSet<>(blobs_split.keySet());
        SortedSet<String> set_other_branch=  new TreeSet<>(blobs_other_branch.keySet());


        Iterator<String> ite_head=set_head.iterator();

        Iterator<String> ite_branch=set_other_branch.iterator();
        while(ite_head.hasNext()){

            String head_name=ite_head.next();
            String head_hash=blobs_head.get(head_name);
            String branch_hash=blobs_other_branch.get(head_name);
            String split_hash=blobs_split.get(head_name);
            boolean isExist= blobs_other_branch.get(head_name)!=null && blobs_head.get(head_name)!=null;

            if(split_hash!=null){
                if(isExist){


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

                            conflict_list.add(head_name);


                                conflict(head_hash,head_name,branch_hash,otherbranch,blobs);

                                IsConflict=true;
                                //conflicted

                            }
                            //checkout which has modified since split







                }
            }

            if(head_hash!=null){

                if(split_hash!=null){
                    if(branch_hash==null&&split_hash.equals(head_hash))
                    {

                        blobs.put(head_name,null);
                        //remove HEAD's File
                    }
                }

            }



            if(head_hash!=null&&split_hash==null&&branch_hash==null)
            {
                blobs.put(head_name,head_hash);
                //neither in other nor in split but in HEAD
            }


            if(head_hash!=null){

                if(split_hash!=null){
                    if(branch_hash==null&&!split_hash.equals(head_hash))
                    {

                        conflict_list.add(head_name);
                        conflict(head_hash,head_name,branch_hash,otherbranch,blobs);
                        IsConflict=true;
                        //conflicted
                    }
                }

            }

        }

        while(ite_branch.hasNext()){

            String head_name=ite_branch.next();
            String head_hash=blobs_head.get(head_name);
            String branch_hash=blobs_other_branch.get(head_name);
            String split_hash=blobs_split.get(head_name);
            boolean isExist= blobs_other_branch.get(head_name)!=null && blobs_head.get(head_name)!=null;

            if(set_head.contains(head_name))
                continue;

            if(head_hash==null){

                if(split_hash!=null){
                    if(branch_hash!=null&&split_hash.equals(branch_hash))
                    {

                        blobs.put(head_name,null);
                        //remove Branch's File
                    }
                }

            }



            if(head_hash==null&&split_hash==null&&branch_hash!=null)
            {
                blobs.put(head_name,branch_hash);
                //neither in head nor in split but in other
            }


            if(head_hash!=null){

                if(split_hash!=null){
                    if(branch_hash==null&&!split_hash.equals(head_hash))
                    {

                        conflict_list.add(head_name);
                        conflict(head_hash,head_name,split_hash,otherbranch,blobs);
                        IsConflict=true;
                        //conflicted
                    }
                }

            }

        }
        return IsConflict;
    }


    private static int[] mergeCommitChange(Commit head,Commit other){
        int[] select=new int[3];

        if(!(head instanceof MergeCommit)&& !(other instanceof  MergeCommit)){
            int branch_select=0;

            int a_size=head.getBranch_size();
            int b_size=other.getBranch_size();
            select[0]=branch_select;
            select[1]=a_size;
            select[2]=b_size;

        }else{

            if(head instanceof  MergeCommit){



                return select;

            }








            return select;

        }









        return select;
    }

    private static void deleteFile_withoutConflict(LinkedList<String> conflict_list){
        List<String> L= Utils.plainFilenamesIn(Repository.CWD);

        int i=0;
        while(i<L.size()){
            String s=L.get(i);
            if(conflict_list.contains(s)){
                i++;
                continue;
            }

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

 private static void wirteMergeBlobs(HashMap<String,String> blobs,LinkedList<String> conflict_list){
        deleteFile_withoutConflict(conflict_list);

         if(blobs==null)
         {

             return;
         }
         Set<String> blobs_key=blobs.keySet();
         Iterator<String> ite=blobs_key.iterator();

         while(ite.hasNext()){
             String s=ite.next();
             File f=new File(Repository.CWD,s);
            if(conflict_list.contains(s)){
                conflict_list.remove(s);
                continue;

            }


             String file_hash=blobs.get(s);

             if(file_hash==null)
                 continue;

             try{
                 f.createNewFile();
                 File f_blobs=new File(Repository.BLOBS_DIR,file_hash);
                 if(file_hash.equals("da39a3ee5e6b4b0d3255bfef95601890afd80709"))
                 {

                 }
                 else
                     writeContents(f,readContents(f_blobs));

             }catch (IOException o){

             }

         }
 }

 private static Commit findSplit_help(Commit head,String otherbranch){
     while(head!=null){

         if(head.isSplit(otherbranch))
             return head;
         else
             head=getCommitwithId(head.getParent().get(0));

     }
     while(head!=null){

         if(head.isSplit(otherbranch))
             return head;
         else
             head=getCommitwithId(head.getParent().get(1));

     }
     return null;
 }

 public static String getCurrentBranch(){
        String s=readContentsAsString(CWBRANCH);

        return s;
 }

}

