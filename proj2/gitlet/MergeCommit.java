package gitlet;

public class MergeCommit extends Commit{
    private Commit parent2;
    private String given_branch;
    private static final long serialVersionUID =  -7687123592477700924L;
    MergeCommit(String msg,Commit p,Commit p2){
        super(msg,p);
        parent2=p2;

    }
    public void setGiven_branch(String s){
        this.given_branch=s;
    }
    public void printInfo(){
        System.out.println("===");
        System.out.println("commit "+getHash());
        if(this instanceof MergeCommit)
            System.out.println("Merged "+this.given_branch+" into "+getBranch()+".");
        System.out.println("");

    }



}
