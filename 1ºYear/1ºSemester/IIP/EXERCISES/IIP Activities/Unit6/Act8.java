package Unit6;

public class Act8{
    public static void main(String [] args){
        int i=1;
        while(i*i<10) {
            int j=i;
            while(j*j<100) {
                System.out.print(i + j);
                System.out.print(" ");
                j *= 2;
            }
            i++; System.out.print("\n");
        }
        System.out.print("\n*****");
    }
}