package gitlet;

import java.util.LinkedList;

public class MergeCommit extends Commit{

    private String given_branch;
    private static final long serialVersionUID =  -7687123592477700924L;

    public void setGiven_branch(String s){
        this.given_branch=s;
    }
    MergeCommit(String msg, LinkedList<String> p){
        super(msg,p);
    }
    public void printInfo(){
        System.out.println("===");
        System.out.println("commit "+getHash());
        System.out.println("Date: "+getTimeStamp());
        System.out.println(message());

        System.out.println("");

    }



}
