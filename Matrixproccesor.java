package processor;
import java.util.Scanner;
public class Matrixproccesor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OperateMatrix obj = new OperateMatrix();
        int rowNumber1,columnNumber1,rowNumber2,columnNumber2;
        while(true) {
            switchPrint();
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the size of the first matrix: ");
                    rowNumber1 = scanner.nextInt();
                    columnNumber1 = scanner.nextInt();
                    System.out.println("Enter first matrix: ");
                    double[][] matrix1 = fill(rowNumber1, columnNumber1);
                    System.out.print("Enter the size of the second matrix: ");
                    rowNumber2 = scanner.nextInt();
                    columnNumber2 = scanner.nextInt();
                    System.out.println("Enter second matrix:");
                    double[][] matrix2 = fill(rowNumber2, columnNumber2);
                    double[][] addAns = obj.matrixAdd(matrix1, matrix2);
                    System.out.println("The addition result is:");
                    printMatrix(addAns);
                    break;
                case 2:
                    System.out.print("Enter matrix size: ");
                    rowNumber1 = scanner.nextInt();
                    columnNumber1 = scanner.nextInt();
                    System.out.println("Enter matrix: ");
                    matrix1 = fill(rowNumber1, columnNumber1);
                    System.out.print("Enter the constant to be multiplied with the matrix: ");
                    int constant = scanner.nextInt();
                    double[][] answer = obj.matrixMultiply(matrix1, constant);
                    System.out.println("The multiplication result is:");
                    printMatrix(answer);
                    break;
                case 3:
                    System.out.print("Enter the size of the first matrix: ");
                    rowNumber1 = scanner.nextInt();
                    columnNumber1 = scanner.nextInt();
                    System.out.println("Enter first matrix: ");
                    matrix1 = fill(rowNumber1, columnNumber1);
                    System.out.print("Enter the size of the second matrix: ");
                    rowNumber2 = scanner.nextInt();
                    columnNumber2 = scanner.nextInt();
                    System.out.println("Enter second matrix:");
                    matrix2 = fill(rowNumber2, columnNumber2);
                    double[][] answerMul = obj.multiply_two_matrices(matrix1, matrix2);
                    printMatrix(answerMul);
                    break;
                case 4:
                    System.out.print("\n1. Main diagonal\n");
                    System.out.println("2. Side diagonal");
                    System.out.println("3. Vertical diagonal");
                    System.out.println("4. Horizontal line");
                    System.out.print("Your choice: ");
                    int type = scanner.nextInt();
                    System.out.print("Enter matrix size: ");
                    rowNumber1 = scanner.nextInt();
                    columnNumber1 = scanner.nextInt();
                    System.out.println("Enter matrix: ");
                    matrix1 = fill(rowNumber1, columnNumber1);

                    switch(type){
                        case 1:
                            System.out.println("The result is:");
                            double[][] transposed = obj.transposeMatrix(matrix1,"main_diagonal");
                            for (int i = 0; i < matrix1[0].length; i++) {
                                for (int j = 0; j < matrix1.length; j++) {
                                    System.out.print(transposed[i][j] + " ");
                                }
                                System.out.print("\n");
                            }
                            break;
                        case 2:
                            System.out.println("The result is:");
                            transposed = obj.transposeMatrix(matrix1,"secondary_diagonal");
                            printMatrix(transposed);
                            break;
                        case 3:
                            System.out.println("The result is:");
                            transposed = obj.transposeMatrix(matrix1,"vertical");
                            printMatrix(transposed);
                            break;
                        case 4:
                            System.out.println("The result is:");
                            transposed = obj.transposeMatrix(matrix1,"horizontal");
                            printMatrix(transposed);
                            break;
                    }
                    break;
                case 5:
                    System.out.print("Enter matrix size: ");
                    rowNumber1 = scanner.nextInt();
                    columnNumber1 = scanner.nextInt();
                    System.out.println("Enter matrix: ");
                    matrix1 = fill(rowNumber1, columnNumber1);
                    double number = Determinant.determinantOfMatrix(matrix1,matrix1.length);
                    System.out.println(format(number));
                    break;
                case 6:
                    System.out.print("Enter matrix size: ");
                    rowNumber1 = scanner.nextInt();
                    columnNumber1 = scanner.nextInt();
                    System.out.println("Enter matrix: ");
                    matrix1 = fill(rowNumber1, columnNumber1);
                    System.out.println("The result is:");
                    number = Determinant.determinantOfMatrix(matrix1,matrix1.length);
                    if(number == 0){
                        System.out.println("Inverse cannot be found, determinant zero");
                    }
                    else{
                        double[][] ans = obj.matrixMultiply(Determinant.invert(matrix1),1 / number);
                        printMatrix(ans);
                    }
                    break;
                case 0:
                    System.exit(0);
            }
        }

    }

    public static double[][] fill(int row, int column) {
        double[][] matrix = new double[row][column];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = Double.parseDouble(scanner.next());
            }
        }
        return matrix;
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0 ; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Double.parseDouble(format(matrix[i][j]));
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static String format(double digit) {
        if (digit == (long) digit) {
            return String.format("%d",(long)digit);
        } else {
            return String.format("%s" , digit);
        }
    }

    public static void switchPrint() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix to a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("0. Exit");
    }

}

class OperateMatrix {

    //a function to add two matrices,returns the summed matrix
    double[][] matrixAdd(double[][] matrix1, double[][] matrix2) {
        double[][] matrixSum = new double[matrix1.length][matrix1[0].length]; //the sum matrix also has the same dimensions
        for (int i = 0; i < matrix1.length; i++) { //considering only matrix1 because both matrices have same dimensions
            for (int j = 0; j < matrix1[i].length; j++) { //the restraint condition,specifies column number
                matrixSum[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return matrixSum;
    }

    // a function to just multiply a single number with a matrix
    double[][] matrixMultiply(double[][] matrix, double constant) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = matrix[i][j] * constant;
            }
        }
        return matrix;
    }

    double[][] multiply_two_matrices(double[][] matrix1, double[][] matrix2) {
        if (matrix1[0].length != matrix2.length) {
            System.out.println("Error!");
        }
        double[][] multipliedAns = new double[matrix1.length][matrix2[0].length];
        int i, j, k;
        for (i = 0; i < matrix1.length; i++) {
            for (j = 0; j < matrix2[0].length; j++) {
                for (k = 0; k < matrix2.length; k++)
                    multipliedAns[i][j] += matrix1[i][k] * matrix2[k][j];
            }
        }
        return multipliedAns;
    }

    double[][] transposeMatrix(double[][] matrix, String type) {
        double[][] transposed = new double[matrix.length][matrix[0].length];
        switch (type) {

            case "main_diagonal":
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        transposed[i][j] = matrix[j][i];
                    }
                }
                return transposed;

            case "secondary_diagonal":
                for (int i = 0; i < (matrix.length - 1); i++) {
                    for (int j = 0; j < (matrix[0].length - 1) - i; j++) {
                        double tmp = matrix[i][j];
                        matrix[i][j] = matrix[(matrix[0].length - 1) - j][(matrix[0].length - 1) - i];
                        matrix[(matrix[0].length - 1) - j][(matrix[0].length - 1) - i] = tmp;
                    }
                }
                return matrix;

            case "vertical":
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length / 2; j++) {
                        double temp = matrix[i][j];
                        matrix[i][j] = matrix[i][matrix.length - 1 - j];
                        matrix[i][matrix.length - 1 - j] = temp;
                    }
                }
                return matrix;

            case "horizontal":
                for (int i = 0; i < matrix.length / 2; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        double temp = matrix[i][j];
                        matrix[i][j] = matrix[matrix.length - 1 - i][j];
                        matrix[matrix.length - 1 - i][j] = temp;
                    }
                }
                return matrix;

        }

      return null;
    }

}
class Determinant {

    // Dimension of input square matrix
    static final int N = 4;

    // Function to get cofactor of
    // mat[p][q] in temp[][]. n is
    // current dimension of mat[][]
    static void getCofactor(double[][] mat,
                            double[][] temp, int p, int q, int n) {
        int i = 0, j = 0;

        // Looping for each element of
        // the matrix
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Copying into temporary matrix
                // only those element which are
                // not in given row and column
                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];

                    // Row is filled, so increase
                    // row index and reset col
                    //index
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }

    }

    /* Recursive function for finding determinant
    of matrix. n is current dimension of mat[][]. */
    static double determinantOfMatrix(double[][] mat, int n) {
        double D = 0; // Initialize result

        // Base case : if matrix contains single
        // element
        if (n == 1)
            return mat[0][0];

        // To store cofactors
        double[][] temp = new double[N][N];

        // To store sign multiplier
        int sign = 1;

        // Iterate for each element of first row
        for (int f = 0; f < n; f++) {
            // Getting Cofactor of mat[0][f]
            getCofactor(mat, temp, 0, f, n);
            D += sign * mat[0][f]
                    * determinantOfMatrix(temp, n - 1);

            // terms are to be added with
            // alternate sign
            sign = -sign;
        }

        return D;
    }

    static double[][] invert(double[][] matrix) {
          OperateMatrix obj = new OperateMatrix();
          matrix = obj.transposeMatrix(matrix,"main_diagonal");
          double[][] cofactor = new double[matrix.length][matrix.length];
          double[][] temp = new double[N][N];
          for(int i = 0; i < matrix.length; i++){
              for(int j = 0;j < matrix.length; j++){
                  getCofactor(matrix,temp,i,j,matrix.length);
                  cofactor[i][j] = Math.pow(-1,((i+1)+(j+1))) * determinantOfMatrix(temp, matrix.length - 1);
              }
          }
          return cofactor;
    }
}






