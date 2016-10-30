package com.khanintel.lesson53_TestArray;

/**
 * 一个用于 二维数组的运算的 小工具，比较健壮，可以接收非矩形的二维数组，甚至在行数、列数可以不匹配的情况下来计算矩阵的和、差、乘积等
 *
 * ------Created by Justin on 2016-10-30.
 */
public final class MatrixCalc {

    /**
     * 输入二维数组，获得二维数组的属性
     * @param matrix 任意二维数组
     * @return 二维数组的属性
     */
    private static MatrixAttribute getMatrixAttr(int[][] matrix) {
        int rowNumber;
        int columnNumber;

        rowNumber = matrix.length;

        boolean isRectangle = true;

        columnNumber = matrix[0].length;
        for (int i=0; i<matrix.length; i++) {

            int length = matrix[i].length;

            if (length != columnNumber) {
                isRectangle = false;
            }

            if (length > columnNumber ){
                columnNumber = length;
            }
        }

        return ( new MatrixAttribute(rowNumber,columnNumber,isRectangle) );

    }


    /**
     * 将一个小的 matrix1， 拷贝到大的 matrix2 里面去
     * @param matrix1，可以是 非矩形的 二维数组
     * @param matrix2，必须是 矩形的 二维数组，可以远比 matrix1 的尺寸要大
     */
    private static int[][] syncMatrix(int[][] matrix1, int[][] matrix2) {
        MatrixAttribute matrixAttribute_1 = getMatrixAttr(matrix1);
        MatrixAttribute matrixAttribute_2 = getMatrixAttr(matrix2);

        if ( matrixAttribute_2.getRowNumber() < matrixAttribute_1.getRowNumber() ||
                matrixAttribute_2.getColumnNumber() < matrixAttribute_2.getColumnNumber() ) {
            try {
                throw new Exception("The Destination matrix size is smaller than the original matrix!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i=0; i<matrix1.length; i++) {
            for (int j=0; j<matrix1[i].length; j++) {
                matrix2[i][j] = matrix1[i][j];
            }
        }

        return matrix2;

    }

    /**
     * 对于不是矩形的二维数组，把它放在一个大的、正好合适的矩形数组里面，并且不会修改原有的二维数组
     * @param matrix 一个任意形状的（可以是非矩形的）二维数组
     * @return 一个大的、正好合适的矩形数组
     */
    private static int[][] matrixReFormat(int[][] matrix) {

        MatrixAttribute matrixAttribute = getMatrixAttr(matrix);

        int rowNumber = matrixAttribute.getRowNumber();
        int columnNumber = matrixAttribute.getColumnNumber();
        boolean isRectangle = matrixAttribute.isRectangle();

        int[][] newMatrix;

        if (!isRectangle) {
            newMatrix = new int[rowNumber][columnNumber];
            newMatrix = syncMatrix(matrix, newMatrix);
        } else {
            newMatrix = matrix;
        }
        return newMatrix;
    }

    /**
     * 可以接收非矩形的二维数组，返回一个负数矩阵（同样的形状，可以是矩形或非矩形）
     * ------注意，没有修改原有输入的值（包括从非矩形到矩形）
     * @param matrix1 一个任意形状的（可以是非矩形的）二维数组
     * @return 一个负数矩阵（同样的形状，可以是矩形或非矩形）
     */
    public static int[][] matrixNegative(int[][] matrix1) {

        int[][] newMatrix = new int[matrix1.length][];

        for (int i=0; i<matrix1.length; i++) {

            newMatrix[i] = new int[ (matrix1[i].length) ];

            for (int j=0; j<matrix1[i].length; j++) {
                newMatrix[i][j] = 0 - matrix1[i][j];
            }
        }

        return newMatrix;
    }


    /**
     * 接收2个大小不同的、可以非矩形的矩阵，返回一个大的矩阵，包含两个矩阵之和
     * ------注意，没有修改原有的两个输入的值（包括从非矩形到矩形）
     * @param matrix1 一个任意形状的（可以是非矩形的）二维数组
     * @param matrix2 一个任意形状的（可以是非矩形的）二维数组，可以与 matrix1 的形状不一样
     * @return 一个包含两个矩阵之和的大的矩阵
     */
    public static int[][] matrixAdd(int[][] matrix1, int[][] matrix2) {

        MatrixAttribute matrixAttribute_1 = getMatrixAttr(matrix1);
        MatrixAttribute matrixAttribute_2 = getMatrixAttr(matrix2);

        int[][] newMatrix1;
        int[][] newMatrix2;
        int newMatrixRowNO;
        int newMatrixColumnNO;

        /**
         * 两个相加的矩阵，必须是标准矩形，行数、列数一致
         */
        if (! (matrixAttribute_1.equals(matrixAttribute_2) && matrixAttribute_1.isRectangle() && matrixAttribute_2.isRectangle()) ) {
            newMatrixRowNO =
                    (matrixAttribute_1.getRowNumber() > matrixAttribute_2.getRowNumber()) ?
                            matrixAttribute_1.getRowNumber() : matrixAttribute_2.getRowNumber();
            newMatrixColumnNO =
                    (matrixAttribute_1.getColumnNumber() > matrixAttribute_2.getColumnNumber()) ?
                            matrixAttribute_1.getColumnNumber() : matrixAttribute_2.getColumnNumber();

            newMatrix1 = new int[newMatrixRowNO][newMatrixColumnNO];
            newMatrix2 = new int[newMatrixRowNO][newMatrixColumnNO];

            newMatrix1 = syncMatrix(matrix1, newMatrix1);
            newMatrix2 = syncMatrix(matrix2, newMatrix2);

        } else {
            newMatrix1 = matrix1;
            newMatrix2 = matrix2;
            newMatrixRowNO = newMatrix1.length;
            newMatrixColumnNO = newMatrix1[0].length;
        }

        int[][] summaryMatrix = new int[newMatrixRowNO][newMatrixColumnNO];

        for (int i=0; i<newMatrixRowNO; i++) {
            for (int j=0; j<newMatrixColumnNO; j++) {
                summaryMatrix[i][j] = newMatrix1[i][j] + newMatrix2[i][j];
            }
        }
        return summaryMatrix;
    }

    /**
     * 矩阵相减，没什么可说的
     * ------注意，没有修改原有的两个输入的值（包括从非矩形到矩形）
     * @param matrix1 一个任意形状的（可以是非矩形的）二维数组
     * @param matrix2 一个任意形状的（可以是非矩形的）二维数组，可以与 matrix1 的形状不一样
     * @return 一个包含两个矩阵之差的大的矩阵
     */
    public static int[][] matrixMinus(int[][] matrix1, int[][] matrix2) {

        int[][] newMatrix2 = matrixNegative(matrix2);

        return matrixAdd(matrix1, newMatrix2);

    }

    /**
     * 矩阵的翻转
     * ------注意，没有修改原有的输入的值（包括从非矩形到矩形）
     * @param matrix1 一个任意形状的（可以是非矩形的）二维数组
     * @return 翻转后的矩阵，是一个标准矩形的矩阵
     */
    public static int[][] matrixFlip(int[][] matrix1) {
        MatrixAttribute matrixAttribute = getMatrixAttr(matrix1);
        int[][] newMatrix = new int[matrixAttribute.getColumnNumber()][matrixAttribute.getRowNumber()];

        for (int i=0; i<matrix1.length; i++) {
            for (int j=0; j<matrix1[i].length; j++) {
                newMatrix[j][i] = matrix1[i][j];
            }
        }
        return newMatrix;
    }

    /**
     * 获得一个新的矩阵，包含两个矩阵的乘积
     * ------注意，没有修改原有的两个输入的值（包括从非矩形到矩形）
     * @param matrix1 一个任意形状的（可以是非矩形的）二维数组
     * @param matrix2 一个任意形状的（可以是非矩形的）二维数组，可以与 matrix1 的形状不一样
     * @return 一个包含两个矩阵之乘积的矩阵
     */
    public static int[][] matrixMultiplication(int[][] matrix1, int[][] matrix2) {
        MatrixAttribute matrixAttribute_1 = getMatrixAttr(matrix1);
        MatrixAttribute matrixAttribute_2 = getMatrixAttr(matrix2);

        int[][] newMatrix1;
        int[][] newMatrix2;
        int newMatrixNO;

        int[][] preProcessMatrix1 = matrixReFormat(matrix1);
        int[][] preProcessMatrix2 = matrixReFormat(matrix2);


        /**
         * 两个相乘的矩阵，必须是矩形矩阵，且 matrix1的行数 等于 matrix2的列数
         */

        if (matrixAttribute_1.getColumnNumber() != matrixAttribute_2.getRowNumber()) {
            newMatrixNO =
                    (matrixAttribute_1.getColumnNumber() > matrixAttribute_2.getRowNumber())?
                            matrixAttribute_1.getColumnNumber():matrixAttribute_2.getRowNumber();

            newMatrix1 = new int[matrixAttribute_1.getRowNumber()][newMatrixNO];
            newMatrix2 = new int[newMatrixNO][matrixAttribute_2.getColumnNumber()];

            newMatrix1 = syncMatrix(matrix1, newMatrix1);
            newMatrix2 = syncMatrix(matrix2, newMatrix2);

        } else {
            newMatrix1 = preProcessMatrix1;
            newMatrix2 = preProcessMatrix2;
            newMatrixNO = matrixAttribute_1.getColumnNumber();
        }

        int[][] multiplicationMatrix = new int[matrixAttribute_1.getRowNumber()][matrixAttribute_2.getColumnNumber()];

        for (int i=0; i<matrixAttribute_1.getRowNumber(); i++) {
            for (int j=0; j<matrixAttribute_2.getColumnNumber(); j++) {
                int sum = 0;
                for (int k=0; k < newMatrixNO; k++) {
                    sum += newMatrix1[i][k] * newMatrix2[k][j];
                }
                multiplicationMatrix[i][j] = sum;
            }
        }
        return multiplicationMatrix;
    }

    /**
     * 将矩阵输出到Console处
     * @param matrix1 任意形状的矩阵
     */
    public static void printResult(int[][] matrix1) {
        for (int i=0; i<matrix1.length; i++) {
            for (int j=0; j<matrix1[i].length; j++) {
                System.out.print(matrix1[i][j] + "\t");
            }
            System.out.println();
        }
    }


    /**
     * 多达 100 行的测试代码
     * @param args 没啥用的参数，啊哈哈
     */
    public static void main(String[] args) {

        int[][] matrix1 = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        } ;

        int[][] matrix2 = {
                {9,8,7},
                {6,5,4},
                {3,2,1}
        } ;

        int[][] matrix101 = {
                {1,},
                {2,3},
                {4,5,6}
        } ;

        int[][] matrix102 = {
                {9,8,7},
                {6,5},
                {4}
        } ;

        int[][] matrix201 = {
                {1,2},
                {3}
        };

        int[][] matrix202 = {
                {1,3,5,7,9},
                {2,4,6,8},
                {0},
                {-1,-5,-9}
        };

        /**
         * 测试二维数组的打印
         */
        System.out.println("########################################################");
        System.out.println("测试 二维数组打印 功能");
        printResult(matrix1);
        System.out.println();
        printResult(matrix101);
        System.out.println();
        printResult(matrix202);

        /**
         * 测试 ReFormat 功能
         */
        System.out.println("########################################################");
        System.out.println("测试 ReFormat 功能");
        printResult(matrixReFormat(matrix101));
        System.out.println();
        printResult(matrixReFormat(matrix102));

        /**
         * 测试 Negative 功能
         */
        System.out.println("########################################################");
        System.out.println("测试 Negative 功能");
        printResult(matrixNegative(matrix1));
        System.out.println();
        printResult(matrixNegative(matrix101));

        /**
         * 测试 Add 功能
         */
        System.out.println("########################################################");
        System.out.println("测试 Add 功能");
        printResult(matrixAdd(matrix1, matrix2));
        System.out.println();
        printResult(matrixAdd(matrix101, matrix102));
        System.out.println();
        printResult(matrixAdd(matrix1, matrix101));
        System.out.println();
        printResult(matrixAdd(matrix201, matrix202));

        /**
         * 测试 Minus 功能
         */
        System.out.println("########################################################");
        System.out.println("测试 Minus 功能");
        printResult(matrixMinus(matrix1, matrix2));
        System.out.println();
        printResult(matrixMinus(matrix101, matrix102));
        System.out.println();
        printResult(matrixMinus(matrix1, matrix101));
        System.out.println();
        printResult(matrixMinus(matrix201, matrix202));

        /**
         * 测试 Flip 功能
         */
        System.out.println("########################################################");
        System.out.println("测试 Flip 功能");
        printResult(matrixFlip(matrix1));
        System.out.println();
        printResult(matrixFlip(matrix101));

        /**
         * 测试 Multiplication 功能
         */
        System.out.println("########################################################");
        System.out.println("测试 Multiplication 功能");
        printResult(matrixMultiplication(matrix1, matrix2));
        System.out.println();
        printResult(matrixMultiplication(matrix101, matrix102));
        System.out.println();
        printResult(matrixMultiplication(matrix1, matrix101));
        System.out.println();
        printResult(matrixMultiplication(matrix201, matrix202));

    }

}


class MatrixAttribute {
    private int rowNumber;
    private int columnNumber;
    private boolean isRectangle;

    public MatrixAttribute() {
    }

    public MatrixAttribute(int rowNumber, int columnNumber, boolean isRectangle) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.isRectangle = isRectangle;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public boolean isRectangle() {
        return isRectangle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MatrixAttribute) {
            MatrixAttribute matrixObj = (MatrixAttribute)obj;
            return ((rowNumber == matrixObj.rowNumber) && (columnNumber == matrixObj.columnNumber) && (isRectangle == matrixObj.isRectangle));
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "rowNumber:" + rowNumber + "\n" +
                "columnNumber:" + columnNumber + "\n" +
                "isRectangle:" + isRectangle;
    }

}
