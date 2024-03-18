import java.io.*;
import java.util.Arrays;

public class Main {

    private final static MainService mainService = new MainService();

    public static void main(String[] args) throws Exception {
        if (isPipedInput()) {
            mainService.processPipedInput(args);
        } else {
            mainService.processArgsInput(args);
        }
    }

    private static boolean isPipedInput() {
        if (System.console() != null) {
            return true;
        }

        try {
            return System.in.available() > 0;
        } catch (IOException e) {
            return false;
        }
    }
}