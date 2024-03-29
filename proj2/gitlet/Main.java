package gitlet;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length==0)
            Repository.exit("Please enter a command.");

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command

                if(args.length>2||args.length<1)

                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }
                else{

                    Repository.init();
                }


                break;
            case "add":
                // TODO: handle the `add [filename]` command
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else if(args[1].equals("-A")){
                    List<String> list=Utils.plainFilenamesIn(Repository.CWD);
                    Iterator<String> ite=list.iterator();
                    while(ite.hasNext()){
                        String s=ite.next();
                        File f =new File(Repository.CWD,s);
                        if(f.isFile())
                            Repository.add(s);

                    }



                }
                    else
                Repository.add(args[1]);
                break;

            case "add-remote":
                // TODO: handle the `add [filename]` command
                if(args.length>3||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                    Repository.addremote(args[1],args[2]);
                break;
            case "fetch":
                // TODO: handle the `add [filename]` command
                if(args.length>3||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                    Repository.fetch(args[1],args[2]);
                break;
            case "push":
                // TODO: handle the `add [filename]` command
                if(args.length>3||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                    Repository.push(args[1],args[2]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                if(args.length>2||args.length<1)
                {
                    Repository.exit("Incorrect operands.");

                }else if(args.length==1)
                Repository.exit("Please enter a commit message.");
                else
                Repository.commit(args[1]);
                break;
            case "status":

                if(args.length>2||args.length<1)

                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                Repository.status();
                break;
            case "log":

                if(args.length>2||args.length<1)

                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                Repository.log();
                break;
            case "rm":
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else if(args[1].equals("-A")){
                List<String> list=Utils.plainFilenamesIn(Repository.CWD);
                Iterator<String> ite=list.iterator();
                while(ite.hasNext()){
                    String s=ite.next();
                    File f =new File(Repository.CWD,s);
                    if(f.isFile())
                        Repository.remove(s);

                }
                }
                else
                Repository.remove(args[1]);
                break;
            case "global-log":
                if(args.length>2||args.length<1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                Repository.globallog();
                break;
            case "find":
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                Repository.find(args[1]);
                break;
            case "branch":
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                Repository.rmbranch(args[1]);
                break;

            case "rm-remote":
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                    Repository.rmremote(args[1]);
                break;

            case "reset":
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                Repository.reset(args[1]);
                break;

            case "merge":
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
                Repository.merge(args[1]);
                break;

            case "checkout":
                if(args.length>4||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }
                else{
                    if(args[1].equals("--"))
                        Repository.checkout(args[2]);
                    else if(args.length==4){
                        if(!args[2].equals("--"))
                        {
                            System.out.println("Incorrect operands.");
                            System.exit(0);
                        }
                        Repository.checkout(args[1],args[3]);
                    }
                    else if(args.length<3){

                        Repository.checkoutbranch(args[1]);

                    }else{
                        System.out.println("Incorrect operands.");
                        System.exit(0);
                    }
                }

                break;
            case "":
                System.out.println("Please enter a command.");
                System.exit(0);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
                break;
        }
    }
}
