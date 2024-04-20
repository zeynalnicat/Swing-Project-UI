public class Test {
    public static void main(String[] args) {
        int NBR_CLIENT = 3;
        int i = 0;
        while (i < NBR_CLIENT) {
            Thread t = new Thread(new UserInterface());
            t.start();
            i++;
        }
    }
}
