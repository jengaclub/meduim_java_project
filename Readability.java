package readability;
import java.util.Scanner;
import java.io.*;


public class Readability {
    public static void main(String[] args) {
        File file = new File(args[0]);
        int letterC,wordC,sentenceC;
        String inputString;
        try (Scanner scanner = new Scanner(file)) {
            System.out.println("The text is:");
            inputString = scanner.nextLine();
            System.out.println(inputString);
            System.out.print("\n");
            Readability obj = new Readability();
            String syllableString =  inputString.replaceAll("[.,?!]","");
            String[] words = syllableString.split("\\s+");
            int[] a;
            a = obj.SyllableCounter(words);
            int syllables = a[0];
            int polySyllables = a[1];
            letterC = obj.letterCount(inputString);
            wordC = obj.wordCount(inputString);
            sentenceC = obj.sentenceCount(inputString);

            System.out.println("Words: "+wordC);
            System.out.println("Sentences: "+sentenceC);
            System.out.println("Characters: "+letterC);
            System.out.println("Syllables: "+syllables);
            System.out.println("Polysyllables: "+polySyllables);

            System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
            String choice = "all";
            System.out.println(choice);
            System.out.print("\n");
            float ratioLW = (float) letterC / (float) wordC;//ratio of letter per word
            float ratioWS = (float) wordC / (float) sentenceC;//ratio of word per sentence
            float score = (float) (4.71 * ratioLW + (0.5 * ratioWS - 21.43));
            float score2 = (float)(0.39 * ((float)wordC /(float)sentenceC) + (11.8 * ((float)syllables / (float)wordC)) - 15.59);//fleshman
            float score3 = (float)(1.043 * Math.sqrt((double)polySyllables * ((double)30 / (double)sentenceC)) + 3.1291);//SMOG
            float L = (float)letterC * 100 / (float)wordC;
            float S = (float)sentenceC * 100 / (float)wordC;
            float score4 = (float) (0.0588 * L - 0.296 * S - 15.8);
            System.out.print("Automated Readability Index: " + score);
            int year = obj.print(score);
            System.out.print("\n");
            System.out.print("Flesch–Kincaid readability tests: " + score2);
            int year1 = obj.print(score2);
            System.out.print("\n");
            System.out.print("Simple Measure of Gobbledygook: " + score3);
            int year2 = obj.print(score3);
            System.out.print("\n");
            System.out.print("Coleman–Liau index: " + score4);
            int year3 = obj.print(score4);
            float average = (float)year+year1+year2+year3 /(float)4;
            System.out.print("\n");
            System.out.println("This text should be understood in average by" +average+" year olds.");
        }

        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
    }

    public int letterCount(String input){
        return input.replaceAll("\\s+","").length();
    }
    public int wordCount(String input){
        return input.split("\\s+").length;
    }
    public int sentenceCount(String input){
        String[] inputS = input.split("[?.!]");
        String last = inputS[inputS.length - 1];
        int sentenceNumber = inputS.length;
        String[] lastInput = last.split("[?.!]");
        if(lastInput.length > 1){
            return sentenceNumber+1;
        }
        return sentenceNumber;
    }
    public int[] SyllableCounter(String[] words){
        int[] array = {0,0};
        //declaring the array containing the count of syllables  and the polySyllables
        int i, j, count, polyCount = 0;
        boolean prevVowel;
        String word1;
        String word ;
        for (i = 0; i < words.length ; i++){ //this loop loops through ever word in the sentence passed
            word1 = words[i]; //word is the word at the particular instance of i;
            count = 0;
            prevVowel = false;
                if(word1.charAt(word1.length() - 1) == 'e'){
                    word = word1.substring(0,word1.length() - 1); //removes the letter e from the word if it appears last in the word
                }
                else{
                    word = word1;
                }
                for(j = 0; j < word.length(); j++) { //loop through each word character by character

                       if (isVowel(word.charAt(j))){
                           count++;
                           if (j >= 1) { //because we cannot access -1 index,exception occurs otherwise
                               prevVowel = isVowel(word.charAt(j - 1)); //prevVowel becomes true if the char behind the current character is a a vowel
                           }
                       }

                    if (j >=1  && prevVowel) { //if the index is not the first character,and the previous character is also a vowel
                        count--;
                        prevVowel = false;
                    }
                }//traversing through each character ends
                array[0] += count; //total number of syllables gets added word by word
                if (count > 2) {
                    polyCount++;
                }
        }//traversing through different word done
        array[1] = polyCount;
        return array;
    }
    public boolean isVowel(char word){
        if(word == 'a' || word == 'e' || word == 'i' || word == 'o' || word == 'u'|| word == 'y')
            return true;
        else if( word == 'A' || word == 'E' || word == 'I' || word == 'O' || word == 'U' || word == 'Y')
            return true;
        else
            return false;
    }
    public int print(float score) {
        int year = 0;
        if (score <= 1) {
            System.out.print(" (about 6 year olds).");
            year = 6;
        } else if (score > 1 && score <= 2) {
            System.out.print(" (about 7 year olds).");
            year = 7;
        } else if (score > 2 && score <= 3) {
            System.out.print(" (about 9 year olds).");
            year = 9;
        } else if (score > 3 && score <= 4) {
            System.out.print(" (about 10 year olds).");
            year = 10;
        } else if (score > 4 && score <= 5) {
            System.out.print(" (about 11 year olds).");
            year = 11;
        } else if (score > 5 && score <= 6) {
            System.out.print(" (about 12 year olds).");
            year = 12;
        } else if (score > 6 && score <= 7) {
            System.out.print(" (about 13 year olds).");
            year = 13;
        } else if (score > 7 && score <= 8) {
            System.out.print(" (about 14 year olds).");
            year = 14;
        } else if (score > 8 && score <= 9) {
            System.out.print(" (about 15 year olds).");
            year = 15;
        } else if (score > 9 && score <= 10) {
            System.out.print(" (about 16 year olds).");
            year = 16;
        } else if (score > 10 && score <= 11) {
            System.out.print(" (about 17 year olds).");
            year = 17;
        } else if (score > 11 && score <= 12) {
            System.out.print(" (about 18 year olds).");
            year = 18;
        } else if (score > 12 && score <= 13) {
            System.out.print(" (about 24 year olds).");
            year = 24;
        } else if (score > 13 && score <= 14) {
            System.out.println(" (about 24+ year olds).");
            year = 24;
        }
        else{
            System.out.println(" (about 24+ year olds).");
            year = 24;
        }

        return year;
    }



}
