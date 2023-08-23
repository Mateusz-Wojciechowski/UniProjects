public class Pascal {
    public static int [] NextPascalLine(int [] previousRow){
        int [] nextRow = new int[previousRow.length+1];
        nextRow[0] = 1;
        nextRow[nextRow.length-1] = 1;

        for(int i=1; i<nextRow.length-1; i++){
            nextRow[i] = previousRow[i-1] + previousRow[i];
        }

        return nextRow;
    }

    public static void printRow(int [] row){
        for(int i=0; i<row.length; i++){
            System.out.println(row[i]);
        }
    }

}
