package converter;
import java.util.Scanner;

public class Converter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sourceRadix = scanner.next();
        String sourceNumber = "";
        if(scanner.hasNext()) {
            sourceNumber = scanner.next();
        }
        else{
            System.out.println("error!");
            System.exit(-1);
        }
        String destinationRadix = "";
        if(scanner.hasNext()) {
            destinationRadix = scanner.next();
        }
        else{
            System.out.println("error!");
            System.exit(-1);
        }


        int destinationRadix1 = 0;
        try{destinationRadix1 = (Integer.parseInt(destinationRadix));}
            catch(NumberFormatException e){
            System.out.println("error!");
            System.exit(-1);
        }
        int sourceRadix1 = 0;
        try{sourceRadix1 = Integer.parseInt(sourceRadix);}
        catch (NumberFormatException e){
            System.out.println("error!");
            System.exit(-1);
        }

        if(destinationRadix1 < 1 || destinationRadix1 > 36) {
           System.out.println("error!");
           System.exit(-1);
        }
        else if(sourceRadix1 < 1 || sourceRadix1 >36) {
           System.out.println("error!");
           System.exit(-1);
        }
        Convert object = new Convert();
        if(sourceNumber.contains(".")) {
            String integerPart = sourceNumber.substring(0 , sourceNumber.indexOf('.'));
            String fractionalPart = sourceNumber.substring(sourceNumber.indexOf('.'));
            int number = object.convRadix_to_decimal(integerPart, sourceRadix1);
            double fraction = object.anyBaseToDecimal(fractionalPart,sourceRadix1);
            //*******CONVERTING NOW FROM DECIMAL TO ANY BASE************//
            String number_in_Base = object.convert(number,destinationRadix1);
            String fraction_in_Base = object.decimalToAnyBase(fraction , destinationRadix1);
            if(! number_in_Base.equalsIgnoreCase("0"))  {
                System.out.println(number_in_Base + fraction_in_Base.substring(fraction_in_Base.indexOf('.')));
            }
            else{
                System.out.println(fraction_in_Base);
            }

        }
        else {
            int number = object.convRadix_to_decimal(sourceNumber, sourceRadix1);
            String answer = object.convert(number, destinationRadix1);
            System.out.println(answer);
        }
    }
}


class Convert {
    public String convert(int number, int radix) {
        if (number == 0) {
            return "0";
        }
        StringBuilder str = new StringBuilder();
        if(radix == 1) {
            while(number != 0) {
                str.append("1");
                number --;
            }
            return str.toString();
        }
        while (number != 0) {
            int newNumber = number % radix;
            if (radix > 10 && newNumber > 9) {
                char ch = Character.forDigit(newNumber , radix);
                str.append(ch);
            } else {
                str.append(newNumber);
            }
            number /= radix;
        }
        return str.reverse().toString();
    }

    int convRadix_to_decimal(String number, int radix) {
        if (radix == 10) {
            return Integer.parseInt(number);
        }
        int decimalEquivalent = 0;
        int i, j;
        int currNumber ;
        for (i = 0, j = number.length() - 1; i < number.length() && j >= 0; i++, j--) {
            if (Character.isAlphabetic(number.charAt(i))) {
               currNumber = Character.getNumericValue(number.charAt(i));
            }
            else {
                currNumber = Integer.parseInt(String.valueOf(number.charAt(i)));
            }
            decimalEquivalent += (currNumber * Math.pow(radix , j));
        }
       return decimalEquivalent;
    }

    String decimalToAnyBase (double number, int base) {
        StringBuilder str = new StringBuilder();
        str.append("0.");
        if(base == 1){
          String number1 = String.valueOf(number);
          int startingIndex = number1.indexOf('.');
          return number1.substring(0 , startingIndex + 6);//return 5 points after the dot
        }
        String actualNumber;
        int count = 5;
        while(count != 0) {
            actualNumber = String.valueOf(number * base);
            int a = Integer.parseInt(actualNumber.substring(0,actualNumber.indexOf('.')));
            number = Double.parseDouble("0" + actualNumber.substring(actualNumber.indexOf('.')));
            if(a > 9) {
                char ch = Character.forDigit(a,base);
                str.append(ch);
            }
            else{
                str.append(a);
            }
            count --;
        }
        return str.toString();
    }

    double anyBaseToDecimal(String number, int base) {
        double finalValue = 0;
        if(base == 10){
            return Double.parseDouble(number);
        }
        int j , i;
        for(i = 0 , j = 1; i < number.length() && j < number.length() ; i++) {
            double currnumber;
            if (Character.isAlphabetic(number.charAt(i))) {
                currnumber = Character.getNumericValue(number.charAt(i));
                finalValue += currnumber / Math.pow(base , j);
                j = j + 1;

            }
            else if(Character.isDigit(number.charAt(i))){
                currnumber = Integer.parseInt(String.valueOf(number.charAt(i)));
                finalValue += currnumber / Math.pow(base , j);
                j = j + 1;

            }
        }
        return finalValue;
    }


}

