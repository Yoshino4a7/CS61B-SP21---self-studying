package gitlet;

public class MergeCommit extends Commit{
    private Commit parent2;
    MergeCommit(String msg,Commit p,Commit p2){
        super(msg,p);
        parent2=p2;

    }



}
