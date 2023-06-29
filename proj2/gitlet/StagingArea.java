package gitlet;

import java.io.File;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.*;

public class StagingArea {
    public static final File ADDAREA = join(Repository.HELPERADD_DIR, "add_area");
    public static final File REMOVEAREA = join(Repository.HELPERADD_DIR, "remove_area");
    public static final File BLOBS_MAX = join(Repository.HELPERADD_DIR, "blobs_max");
    public static final File ADDSTATUS = join(Repository.HELPERADD_DIR, "add_status");
    public static final File MODSTATUS = join(Repository.HELPERADD_DIR, "modify_status");
    public static final File REMOVESTATUS = join(Repository.HELPERADD_DIR, "remove_status");
    public static final File ADDLIST = join(Repository.HELPERADD_DIR, "addInfo");
    public static final File MODLIST = join(Repository.HELPERADD_DIR, "modInfo");
    public static final File REMOVELIST = join(Repository.HELPERADD_DIR, "removeInfo");

    private  static HashMap<String,String>blobs=new HashMap<>();
    private  static HashMap<String,String>remove_blobs=new HashMap<>();
    private  static LinkedList<String>link_add=new LinkedList<>();
    private  static LinkedList<String>link_mod=new LinkedList<>();
    private  static LinkedList<String>link_remove=new LinkedList<>();
    private static int size=0;



    public static void init_Staging(){
        try{
            BLOBS_MAX.createNewFile();
            ADDAREA.createNewFile();
            ADDLIST.createNewFile();
            MODLIST.createNewFile();
            REMOVELIST.createNewFile();
            REMOVEAREA.createNewFile();
            writeObject(BLOBS_MAX,size);
            writeObject(ADDAREA,blobs);
            writeObject(ADDLIST,link_add);
            writeObject(MODLIST,link_mod);
            writeObject(REMOVELIST,link_remove);
            writeObject(REMOVEAREA,remove_blobs);
        }catch (IOException o){

        }
    }

    public static void add(String name,String hashcode){
        if(!ADDAREA.exists())
        {
            try{
                ADDAREA.createNewFile();
            }catch (IOException o){

            }
        }
        blobs=readObject(ADDAREA,HashMap.class);
        int size_cur=readObject(BLOBS_MAX,Integer.class);


        if(checkFileWithCWCommit(name,hashcode))

        {
            if(checkFileIsinTheStagingArea(name))
            removeFromStagingArea(name);
            return;
        }


        if(blobs.containsKey(name)){
            if(checkFileIsinTheStagingArea(name)){

                return;
            }else{

                add_create(name,hashcode,size_cur,false);
                return;
            }

        }
        else {

            add_create(name,hashcode,size_cur,false);
        }

    }

    private static void add_create(String filename,String hashname,int size_cur,boolean hasModify){
        File addfile_new=new File(Repository.CWD,filename);
        File addfilenew =new File(Repository.BLOBS_DIR,hashname);




        blobs.put(filename,hashname);
        size_cur++;
        writeObject(BLOBS_MAX,size_cur);
        writeObject(ADDAREA,blobs);
        try{

            addfilenew.createNewFile();
        }catch (IOException o){

        }
        byte[] s=readContents(addfile_new);
        writeContents(addfilenew,s);
        save(hasModify,filename);



    }

    public static void save_remove(String filename,boolean hasCommited){

        if(hasCommited){
            link_remove=readObject(REMOVELIST,LinkedList.class);
            link_remove.addFirst(filename);

            writeContents(REMOVESTATUS,getNameList(link_remove));
            writeObject(REMOVELIST,link_remove);
        }
        else{
            link_remove=readObject(REMOVELIST,LinkedList.class);
            link_remove.addFirst(filename);
            writeContents(REMOVESTATUS,getNameList(link_remove));
            writeObject(REMOVELIST,link_remove);
        }




    }

    public static void save(boolean hasModify,String filename){
        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);
        if(hasModify)
        {
            link_mod.addFirst(filename);
            if(link_add.contains(filename))
            {
                link_add.remove(filename);
            }
            writeContents(MODSTATUS,getNameList(link_mod));
            writeContents(ADDSTATUS,getNameList(link_add));
            writeObject(MODLIST,link_mod);
            writeObject(ADDLIST,link_add);
        }
        else{

            link_add.addFirst(filename);
            writeContents(ADDSTATUS,getNameList(link_add));
            writeObject(ADDLIST,link_add);

        }

    }


    public static void clearStatus(){
        writeObject(MODLIST,link_mod);
        writeObject(ADDLIST,link_add);
        writeObject(REMOVELIST,link_remove);
        writeContents(ADDSTATUS,"");
        writeContents(MODSTATUS,"");
        writeContents(REMOVESTATUS,"");
    }
    private static String getNameList(LinkedList<String> L){
        int i=0;
        String s="";
        while(i<L.size()){
            String a=L.get(i);
            if(a==null){
                s="";
            }else{
                s=s+a+"\n";
            }

            i++;
        }
        return s;
    }

    public static HashMap<String,String> getBlobs(){
        HashMap<String,String> b=readObject(ADDAREA,HashMap.class);
        return b;
    }

    public static boolean hasToCommit(){
        String s=readContentsAsString(ADDSTATUS);
        String s2=readContentsAsString(MODSTATUS);
        String s3=readContentsAsString(REMOVESTATUS);


        return (!s.isEmpty())||(!s2.isEmpty()||(!s3.isEmpty()));
    }
    public static void checkRemove(){
        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);
        link_remove=readObject(REMOVELIST,LinkedList.class);

        int i=0;
        while(i<link_add.size()){
           
            if(link_remove.contains(link_add.get(i))){
                link_add.remove(i);
            }
            i++;
        }
        writeContents(ADDSTATUS,getNameList(link_add));


        i=0;
        while(i<link_mod.size()){
            if(link_remove.contains(link_mod.get(i))){
                link_mod.remove(i);
            }
            i++;
        }
        writeContents(MODSTATUS,getNameList(link_mod));

    }

    public static void remove(String filename){



        blobs=readObject(ADDAREA,HashMap.class);
        remove_blobs=readObject(REMOVEAREA,HashMap.class);
        link_remove=readObject(REMOVELIST,LinkedList.class);
        link_add=readObject(ADDLIST,LinkedList.class);
        Commit head=ComTreeControler.getHead();


        if(!head.isTracked(filename,blobs.get(filename)))

            Repository.exit("No reason to remove the file.");

        if(link_remove.contains(filename)){
            System.out.println("this file has already removed and staging into the remove area");
            return ;
        }
        if(link_add.contains(filename)){
            link_add.remove(filename);
            writeObject(ADDLIST,link_add);
            writeContents(ADDSTATUS,getNameList(link_add));
            return ;
        }


        if(!blobs.get(filename).equals("NULL")){

            blobs.put(filename,"NULL");

            save_remove(filename,false);
            File f=new File(Repository.CWD,filename);
            f.delete();

            writeObject(ADDAREA,blobs);
        }
        else {
            if(head.isRemoved(filename)){
                remove_blobs.put(filename,blobs.get(filename));

                writeObject(REMOVEAREA,remove_blobs);

                save_remove(filename,true);
                return ;
            }
            System.out.println("this file did not bt added into the staging area or" +
                    "tracked with the head commit");
        }

    }

    public static void clearArea(){
        HashMap<String,String> b=new HashMap<>();
       writeObject(ADDAREA,b);



    }
    public static void checkmodify(){

        List<String> L= Utils.plainFilenamesIn(Repository.CWD);


        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);
        link_remove=readObject(REMOVELIST,LinkedList.class);
        blobs=readObject(ADDAREA,HashMap.class);
        Commit head=ComTreeControler.getHead();

        int i=0;
        while(i<L.size()){
            String s=L.get(i);
            File f=new File(Repository.CWD,s);
            if(!f.isFile())
            {

                i++;
                continue;
            }
            String file_hash=sha1(readContentsAsString(f));

            boolean isModify=!blobs.containsValue(file_hash);


            //Tracked in the current commit, changed in the working directory, but not staged
            if(head.isTracked(s,blobs.get(s))&&!link_add.contains(s)&&isModify){
                link_mod.addFirst(s);
                i++;
                continue;

            }
            //Staged for addition, but with different contents than in the working directory
            if(blobs.containsKey(s)&&!blobs.get(s).equals(file_hash)){
                link_mod.addFirst(s);
                i++;
                continue;
            }






            i++;
        }
        writeObject(ADDLIST,link_add);
        writeObject(MODLIST,link_mod);
        writeObject(REMOVELIST,link_remove);




    }

    public static void checkmodifyremove(){
        List<String> L= Utils.plainFilenamesIn(Repository.CWD);


        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);
        link_remove=readObject(REMOVELIST,LinkedList.class);


        int i=0;
        while(i<link_add.size()){
            String s=link_add.get(i);
            File f=new File(Repository.CWD,s);
            if(!f.isFile())
            {

                i++;
                continue;
            }
            //Staged for addition, but deleted in the working directory
            if(link_add.contains(s)&&!L.contains(s)){
                link_mod.addFirst(s);
                i++;
                continue;
            }


            i++;
        }


        writeObject(ADDLIST,link_add);
        writeObject(MODLIST,link_mod);
        writeObject(REMOVELIST,link_remove);



    }

    public static void checkmodifyTrackedbutDelete(){

        List<String> L= Utils.plainFilenamesIn(Repository.CWD);

        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);
        link_remove=readObject(REMOVELIST,LinkedList.class);
        Commit head=ComTreeControler.getHead();
        LinkedList<String> trackedFileName=head.getTrackedName();
        int i=0;
        while(i<trackedFileName.size()){
            String s=trackedFileName.get(i);
            File f=new File(Repository.CWD,s);
            if(!f.isFile())
            {

                i++;
                continue;
            }
            //Not staged for removal, but tracked in the current commit and deleted from the working directory.
            if(!link_remove.contains(s)&&!f.exists())
            {
                link_mod.addFirst(s);
                i++;
                continue;
            }


            i++;
        }


        writeObject(MODLIST,link_mod);
        writeContents(MODSTATUS,getNameList(link_mod));



    }



    public static LinkedList<String> findUntrackedFile(){

        List<String> L=Utils.plainFilenamesIn(Repository.CWD);
        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);
        link_remove=readObject(REMOVELIST,LinkedList.class);
        int i=0;
        LinkedList<String> L1=new LinkedList<>();
        while(i<L.size()) {
            L1.add(L.get(i));
            i++;
        }
        i=0;
        Iterator<String> ite=L1.iterator();

        while(ite.hasNext()){
            String s= ite.next();
            File f=new File(Repository.CWD,s);
            if(!f.isFile())
            {
                i++;
                ite.remove();
                continue;

            }
            boolean b=ComTreeControler.findtracked(s);


            if(link_add.contains(s)||link_mod.contains(s)){
                ite.remove();
                continue;

            }
            else{
                if(b){
                    ite.remove();
                    continue;
                }else if(link_remove.contains(s)){


                    continue;

                }


            }


        }
        return L1;

    }

    private static boolean checkFileIsinTheStagingArea(String filename){
        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);

        if(link_add.contains(filename)||link_mod.contains(filename))
            return true;
        else
            return false;


    }


    private static boolean checkFileWithCWCommit(String filename,String hashcode){
        Commit c=ComTreeControler.getHead();
        return c.isTracked(filename,hashcode);


    }
    private static void removeFromStagingArea(String filename){
        link_add=readObject(ADDLIST,LinkedList.class);
        if(link_add.contains(filename))
            link_add.remove(filename);
        writeObject(ADDLIST,link_add);
    }

}
