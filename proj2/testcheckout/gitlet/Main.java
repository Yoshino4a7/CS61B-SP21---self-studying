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
                Repository.init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                Repository.add(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                Repository.commit(args[1]);
                break;
            case "status":
                Repository.status();
                break;
            case "log":
                Repository.log();
                break;
            case "rm":
                Repository.remove(args[1]);
                break;
            case "global-log":
                Repository.globallog();
                break;
            case "find":
                Repository.find(args[1]);
                break;
            case "branch":

                Repository.branch(args[1]);
                break;
            case "rm-branch":

                Repository.rmbranch(args[1]);
                break;

            case "reset":

                Repository.reset(args[1]);
                break;

            case "merge":

                Repository.merge(args[1]);
                break;

            case "checkout":
               if(args[1].equals("--"))
                Repository.checkout(args[2]);
                else if(args.length>=4){

                    Repository.checkout(args[1],args[3]);
                }
                else{
                   Repository.checkoutbranch(args[1]);
               }
                break;
            default:
                System.out.println("gitlet不支持该命令");
                break;
        }
    }
}
