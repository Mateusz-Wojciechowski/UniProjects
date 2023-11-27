public class Main {
    public static void main(String[] args) {
        int [] previousRow = {1, 3, 3, 1};
        int [] row = Pascal.NextPascalLine(previousRow);
        Pascal.printRow(row);
    }
}
