import advance.CCodeParser;
import advance.CodeParser;
import advance.codeComprators.*;
import advance.codeStructure.Method;
import advance.codeStructure.SourceCode;
import advance.fileParcers.FileUtil;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public class Main {

    private static final String instructions = "\n" +
            "[SET comp ] - to set code comparator \n" +
            "Available: \n" +
            "\t LCS - using Longest Common Subsequence algorithm\n" +
            "\t COST - using algorithm for determining Alignment Cost of 2 methods\n" +
            "\t GREEDY - using Greedy String Tiling algorithm\n" +
            "Example: \'SET comp GREEDY\'\n" +
            "\n" +
            "[SET path ] - to set path to file, which should be checked for plagiarism\n" +
            "\t File will be compared with another files in it's directory\n" +
            "Example: \'SET path C:\\test\\Program.cpp\' \n" +
            "\n" +
            "[START] - to run a source code check\n" +
            "\n" +
            "[HELP] - to get this instructions again\n" +
            "\n" +
            "[EXIT] - to exit the program\n" +
            "\n" +
            "---";

    private static Comparators comparator;
    private static File fileToCheck;
    private static File directoryToCheck;

    private static String getState() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .append("comparator: ").append(comparator.name()).append("\n")
                .append("file to check: ").append(fileToCheck.getAbsolutePath()).append("\n")
                .append("files to compare: \n");
        //noinspection ConstantConditions
        for (String fileName : directoryToCheck.list((dir, name) -> !name.equals(fileToCheck.getName()) && name.matches(".*\\.cpp"))) {
            sb.append("\t").append(fileName).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command = "";
        comparator = Comparators.GREEDY;

        System.out.println("\t ---------- WELCOME TO CODE PLAGIARISM SEARCHER ----------");
        System.out.println(instructions);
        while (true) {
            command = scanner.nextLine();

            if (command.toUpperCase().startsWith("SET")) {
                String[] params = command.split("\\s+");
                if (params[1].toLowerCase().equals("comp")) {
                    switch (params[2].toUpperCase()) {
                        case "LCS":
                            comparator = Comparators.LCS;
                            System.out.println("OK");
                            break;
                        case "COST":
                            comparator = Comparators.COST;
                            System.out.println("OK");
                            break;
                        case "GREEDY":
                            comparator = Comparators.GREEDY;
                            System.out.println("OK");
                            break;
                        default:
                            System.out.println("can't find comparator: " + params[2]);
                    }
                    continue;
                }

                if (params[1].equals("path")) {
                    fileToCheck = new File(params[2]);
                    if (!fileToCheck.exists()) {
                        System.out.println("Can't find that file. Please, try again");
                        continue;
                    }
                    if (!fileToCheck.isFile()) {
                        fileToCheck = null;
                        System.out.println("This path isn't file. Please, try again");
                        continue;
                    }
                    System.out.println("OK");
                    directoryToCheck = fileToCheck.getParentFile();
                    continue;
                }
            }

            if (command.toUpperCase().startsWith("HELP")) {
                System.out.println(instructions);
                continue;
            }

            if (command.toUpperCase().equals("EXIT")) {
                System.out.println("Thank you for using code plagiarism searcher\n Bye!");
                return;
            }

            if (command.toUpperCase().equals("START")) {
                if (fileToCheck == null) {
                    System.out.println("No file to check");
                    continue;
                }
                if (directoryToCheck == null) {
                    System.out.println("Can't find directory");
                    continue;
                }
                System.out.println(getState());
                String ans = "";
                while (!ans.equals("y") && !ans.equals("n")) {
                    System.out.println("Are you sure you want to start checking? [y/n]");
                    ans = scanner.nextLine();
                }
                if (ans.equals("n")) {
                    System.out.println("starting is delayed\n");
                    continue;
                }
                startCheck();
                continue;
            }

            System.out.println("Don't understand " + command);
        }


    }

    private static void startCheck() {
        System.out.println("\tstarting...\n");
        List<SourceCode> sourceCodes = new ArrayList<>();
        SourceCode pattern = null;
        CodeParser codeParser = new CCodeParser();
        //noinspection ConstantConditions
        for (File file : directoryToCheck.listFiles((dir, name) -> name.matches(".*\\.cpp"))) {
            SourceCode code = codeParser.parse(FileUtil.getText(file.getAbsolutePath(), "UTF8"));
            code.setFileName(file.getName());
            if (file.getName().equals(fileToCheck.getName())) {
                pattern = code;
            } else {
                sourceCodes.add(code);
            }
            System.out.println("\t--\t--\t--\t--\n\nFile " + file.getName() + " :");
            System.out.println(" " + code.getMethods().size() + " methods found");
            int tokensAll = 0;
            for (Method method : code.getMethods()) {
                System.out.format(" %15s \tlines %d - %d \t %d tokens found\n",
                        method.getName(),
                        method.getStartLine(),
                        method.getEndLine(),
                        method.getNumberOfTokens());
                tokensAll += method.getNumberOfTokens();
            }
            System.out.println("total length : " + tokensAll + "\n");
        }

        performChecking(pattern, sourceCodes);
    }

    private static void doSomething() {

    }

    private static void performChecking(SourceCode pattern, List<SourceCode> sourceCodes) {
        System.out.println("\n\n comparing....\n");
        CodeComparator codeComparator = null;
        switch (comparator) {
            case LCS:
                codeComparator = new LCSCodeComparator();
                break;
            case COST:
                codeComparator = new AlignmentCostCodeComparator();
                break;
            case GREEDY:
                codeComparator = new GreedyStringTilingCodeComparator();
                break;
        }

        for (SourceCode sourceCode : sourceCodes) {
            CodeSimilarity similarity = codeComparator.compare(pattern, sourceCode);
            if (!similarity.getPlagiatedMethods().isEmpty()) {
                System.out.println("Found plagiarism with " + sourceCode.getFileName());
                System.out.println(similarity);
            }
        }
    }


    public enum Comparators {
        LCS,
        COST,
        GREEDY
    }
}
