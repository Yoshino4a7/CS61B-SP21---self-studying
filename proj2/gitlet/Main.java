package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
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
                }else
                Repository.add(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                if(args.length>2||args.length<=1)
                {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }else
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
                }else
                Repository.remove(args[1]);
                break;
            case "global-log":
                if(args.length>1||args.length<=1)
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

                        Repository.checkout(args[1],args[3]);
                    }
                    else{
                        Repository.checkoutbranch(args[1]);
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
