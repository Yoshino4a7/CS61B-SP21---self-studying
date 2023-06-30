package gitlet;

// TODO: any imports you need here

import java.io.*;

import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    //make the Commit Serializable to save in the file
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private static final long serialVersionUID =  -1912789513991243467L;
    private String message;
    private String timeStamp;
    private int hash;
    private Commit parent;
    private HashMap<String,String> blobs;
    private String hashcode;
    private String branch;
    private int branch_size=0;


    /* TODO: fill in the rest of this class. */

    public Commit(){
        message="";
        timeStamp="";
        branch="master";
        parent=null;
        calcHash();
    }

    public Commit(String msg,Commit p){
        message=msg;
        if(p==null)
        timeStamp="Wed Dec 31 16:00:00 1969 -0800";
        parent=p;
        if(p!=null)
            branch_size=p.getBranch_size();
        sizeUp();
        branch="master";

        //In order to store the info with file,you should make the parent to String type
    }


    public String getTimeStamp(){
        return this.timeStamp;
    }
    public String message(){
        return this.message;
    }
    public Commit getParent(){
        return parent;
    }
    public void printInfo(){
        System.out.println("===");
        System.out.println("commit "+hashcode);
        System.out.println("Date: "+timeStamp);
        System.out.println(message);
        if(this instanceof MergeCommit)
            System.out.println("Merged development into "+branch+".");
        System.out.println("");

    }
    public void timeSet(){
        Date date=new Date();

        Formatter form=new Formatter();
        timeStamp= form.format("%ta %tb %td %tT %tY %tz",date,date,date,date,date,date).toString();

    }
    public void setBlobs(HashMap<String,String> s){
        blobs=s;
    }
   //transfer this object to the bytes array for using sha1 funciton
    public void calcHash(){
        ByteArrayOutputStream bo=new ByteArrayOutputStream();
        ObjectOutputStream oo=null;
        try{
            oo=new ObjectOutputStream(bo);//make the content of oo flush into bo
            oo.writeObject(this);
            oo.flush();//make the content of oo flush into bo
        }catch (IOException o){

        }
        hashcode=Utils.sha1((Object) bo.toByteArray());
    }
    public String getHash(){

        return hashcode;
    }

    public boolean isRemoved(String filename){

        if(blobs==null)
        {

            return false;
        }


        
        return blobs.get(filename).equals("NULL");
    }
    public HashMap<String,String> getBlobs(){
        return blobs;
    }

    public String getBranch(){
        return branch;
    }
    public void setBranch(String branch1){
        branch=branch1;

    }

    public boolean findtracked(String filename){
        if(blobs==null)
            return false;

        return blobs.containsKey(filename);



    }
    public Set<String> getblobsSet(){
        if(blobs==null)
            return null;
        Set<String> blobs_set=blobs.keySet();




        return blobs_set;



    }

    public String getBlobs(String filename){

        if(blobs==null)
            return null;
		if(blobs.get(filename)==null)
			return null;
		

        return blobs.get(filename);
    }

    public boolean isTracked(String filename,String hashcode){


        if(blobs==null)
            return false;
        if(blobs.containsKey(filename))

        return blobs.get(filename).equals(hashcode);

        else
            return false;


    }
    public LinkedList getTrackedName(){
        LinkedList<String> L=new LinkedList<>();
        Set<String> s=blobs.keySet();
         Iterator<String> ite=  s.iterator();

        while(ite.hasNext()){
           L.addFirst(ite.next());

        }



        return L;



    }

    private void sizeUp(){
        branch_size++;
    }
    public int getBranch_size(){
        return branch_size;
    }


    public void writeblobs(File f){

        if(blobs==null)
        {
           Repository.exit("File does not exist in that commit.");
            return;
        }

        if(blobs.containsKey(f.getName()))
        {

            try{
                f.createNewFile();
                File f_blobs=new File(Repository.BLOBS_DIR,blobs.get(f.getName()));
                writeContents(f,readContents(f_blobs));

            }catch (IOException o){

            }

        }else {
            Repository.exit("File does not exist in that commit.");

        }


    }

    public void writeAllblobs(){



        if(blobs==null)
        {

            return;
        }
        Set<String> blobs_key=blobs.keySet();
        Iterator<String> ite=blobs_key.iterator();

        while(ite.hasNext()){
            String s=ite.next();
            File f=new File(Repository.CWD,s);


            if(!findUntracked(s))

            {
                String file_hash=blobs.get(s);

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

            }else
                Repository.exit("`There is an untracked file in the way; delete it, or add and commit it first.`");



        }


    }

    public void printBlobsSet(){
        Set<String> blobs_key=blobs.keySet();
        Iterator<String> ite=blobs_key.iterator();

        while(ite.hasNext()) {
            System.out.println(ite.next());
        }

    }

    public void printBlobsValue(){
        Set<String> blobs_key=blobs.keySet();
        Iterator<String> ite=blobs_key.iterator();

        while(ite.hasNext()) {
            System.out.println(blobs.get(ite.next()));
        }

    }

    public boolean findUntracked(String filename){

        if(blobs==null)
            return true;

        return !blobs.containsKey(filename);

    }

    public boolean findAllUntracked(){
        List<String> L=plainFilenamesIn(Repository.CWD);
        Iterator<String> ite =L.iterator();


        while(ite.hasNext()){
            if(blobs==null)
                return true;
            String s=ite.next();
            if(findUntracked(s))
                return true;





        }

        return false;

    }


}
