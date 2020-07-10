package encryptdecrypt;
import java.io.*;
import java.util.Scanner;

public class EncryptDecrypt {
    public static void main(String[] args) {
        String type = "enc", input = "";
        String algorithm = "shift";
        int key = 0;
        boolean dataPresent = false, inPresent = false, outPresent = false;
        String inPath = "", outPath = "";
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equalsIgnoreCase("-mode")) {
                type = args[i + 1];
            } else if (args[i].equalsIgnoreCase("-key")) {
                key = Integer.parseInt(args[i + 1]);
            } else if (args[i].equalsIgnoreCase("-data")) {
                input = args[i + 1];
                dataPresent = true;
            } else if (args[i].equalsIgnoreCase("-in")) {
                inPath = args[i + 1];
                inPresent = true;
            } else if (args[i].equalsIgnoreCase("-out")) {
                outPath = args[i + 1];
                outPresent = true;
            } else if (args[i].equalsIgnoreCase("-alg")) {
                algorithm = args[i + 1];
            }
        }
        contextStrategy context = new contextStrategy();
        if (algorithm.equalsIgnoreCase("unicode")) {
            context.setMethod(new unicodeEncryptStrategy(), type);
        } else {
            context.setMethod(new CeaserCipherStrategy(), type);
        }
        if (dataPresent && inPresent) { //if both data and input file present,give more preference to input file
            if (!outPresent) {
                String output = context.encryptContext(input, key);
                System.out.println(output);
            } else {
                File file = new File(outPath);
                try (FileWriter writer = new FileWriter(file)) {
                    String output = context.encryptContext(input, key);
                    writer.write(output);
                } catch (IOException e) {
                    System.out.printf("An error occurs %s", e.getMessage());
                }
            }
        } else if (!dataPresent && inPresent) { //if data not present at command line
            File file = new File(inPath);
            try (Scanner scanner = new Scanner(file)) { //read data from file
                String input2 = scanner.nextLine();
                if (!outPresent) { //if output file not present
                    String output = context.encryptContext(input2, key);
                    System.out.println(output);
                } else { //if output file present, create file and write to it
                    file = new File(outPath);
                    try (FileWriter writer = new FileWriter(file)) {
                        String output = context.encryptContext(input2, key);
                        writer.write(output);
                    } catch (IOException e) {
                        System.out.printf("An error occurs %s", e.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("ERROR!input File not found!");
            }
        } //end of else if block
        else {
            if (!outPresent) {
                String output = context.encryptContext(input, key);
                System.out.println(output);
            } else {
                File file = new File(outPath);
                try (FileWriter writer = new FileWriter(file)) {
                    String output = context.encryptContext(input, key);
                    writer.write(output);
                } catch (IOException e) {
                    System.out.printf("An error occurs %s", e.getMessage());
                }
            }
        }
    }
}

    class contextStrategy {
        private encryptStrategy method;
        String ecydec;

        public void setMethod(encryptStrategy method, String ecydec) {
            this.method = method;
            this.ecydec = ecydec;
        }

        public String encryptContext(String input, int key) {
            switch (ecydec) {
                case "enc":
                    return this.method.encrypt(input, key);
                case "dec":
                    return this.method.decrypt(input , key);
                default:
                    return "";
            }
        }
    }

    interface encryptStrategy {
        String encrypt(String input, int key);
        String decrypt(String input, int key);
    }

    class CeaserCipherStrategy implements encryptStrategy {

        @Override
        public String encrypt(String input, int key) {
            StringBuilder sb = new StringBuilder(input);
            String reference = "abcdefghijklmnopqrstuvwxyz";
            String reference1 = reference.substring(key) + reference.substring(0, key);

            int i;
            for (i = 0; i < sb.length(); i++) {
                char currChar = sb.charAt(i);
                char newChar;
                if (Character.isUpperCase(currChar)) {
                    int idx = reference.indexOf(currChar);
                    if (idx != -1) {
                        newChar = Character.toUpperCase(reference1.charAt(idx));
                        sb.setCharAt(i, newChar);
                    }
                } else if (Character.isLowerCase(currChar)) {
                    int idx = reference.indexOf(currChar);
                    if (idx != -1) {
                        newChar = reference1.charAt(idx);
                        sb.setCharAt(i, newChar);
                    }
                }
            }
            return sb.toString();
        }

        @Override
        public String decrypt(String input, int key) {
            return encrypt(input,26 - key);
        }
    }

    class unicodeEncryptStrategy implements encryptStrategy {
        @Override
        public String encrypt(String input, int key) {
            char[] inputs = input.toCharArray();
            for (int i = 0; i < inputs.length; i++) {
                int val = inputs[i] + key;
                char replacement = (char) val;
                inputs[i] = replacement;
            }
            return String.valueOf(inputs);
        }

        @Override
        public String decrypt(String input, int key) {
            return encrypt(input , -1 * key);
        }
    }


