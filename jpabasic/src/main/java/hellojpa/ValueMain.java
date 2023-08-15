package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        Integer a1 = new Integer(13);
        Integer b1 = a1;



        int a = 10;
        int b = a;
        // 항상 값을 복사.
        a = 20;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
