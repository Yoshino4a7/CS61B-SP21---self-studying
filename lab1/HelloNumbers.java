public class HelloNumbers {
    public static void main(String[] args) {
        int x = 10;
        int total = 0;
        while (x <= 20) {
            System.out.print(total + " ");
            total = total + x;
            x = x + 1;
        }
	}
} 
