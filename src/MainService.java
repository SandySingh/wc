import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MainService {

    private String fileName = "";

    public void processPipedInput(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        if (args.length == 0) {
            executePipeAll(input);
        } else if (args.length == 1) {
            executePipeSelected(input, args[0]);
        } else {
            System.out.println("Usage: wc [option] [filename]");
        }
    }

    private void executePipeAll(String input) {
        long lines = input.split("\n").length;
        long words = input.split("\\s+").length;
        long bytes = input.getBytes().length;
        printAll(lines, words, bytes);
    }

    private void executePipeSelected(String input, String selection) {
        long ans;
        try {
            switch (selection) {
                case "-c" -> ans = input.getBytes().length;
                case "-l" -> ans = input.split("\n").length;
                case "-w" -> ans = input.split("\\s+").length;
                case "-m" -> ans = input.length();
                default -> throw new Exception();
            }
            System.out.println(ans + " " + fileName);
        } catch (Exception e) {
            System.out.println("Usage: wc [option] [filename]");
        }
    }

    public void processArgsInput(String[] args) {
        if (args.length == 1) {
            fileName = args[0];
            executeFileAll(Paths.get(args[0]));
        } else if (args.length == 2) {
            fileName = args[1];
            executeFileSelected(Paths.get(args[1]), args[0]);
        } else {
            System.out.println("Usage: wc [option] [filename]");
        }
    }

    private void executeFileAll(Path filePath) {
        try {
            long lines = Files.readAllLines(filePath).size();
            long words = Files.readString(filePath).split("\\s+").length;
            long bytes = Files.readAllBytes(filePath).length;
            printAll(lines, words, bytes);
        } catch (IOException e) {
            System.out.println("Usage: wc [option] [filename]");
            throw new RuntimeException(e);
        }
    }

    private void executeFileSelected(Path filePath, String selection) {
        long ans;
        try {
            switch (selection) {
                case "-l" -> ans = Files.readAllLines(filePath).size();
                case "-c" -> ans = Files.readAllBytes(filePath).length;
                case "-w" -> ans = Files.readString(filePath).split("\\s+").length;
                case "-m" -> ans = Files.readString(filePath).length();
                default -> throw new Exception();
            }
            System.out.println(ans + " " + fileName);
        } catch (Exception e) {
            System.out.println("Usage: wc [option] [filename]");
        }
    }

    private void printAll(long lines, long words, long bytes) {
        System.out.println(lines + " " + words + " " + bytes + " " + fileName);
    }
}
