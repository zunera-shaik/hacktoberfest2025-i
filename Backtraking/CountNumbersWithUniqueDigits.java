public class CountNumbersWithUniqueDigits {
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0) return 1; // only 0
        if (n == 1) return 10; // 0-9

        int count = 10; // for n = 1
        int uniqueDigits = 9; // first digit (1-9)
        int availableDigits = 9; // remaining digits (0-9 except first)
        
        for (int i = 2; i <= n && i <= 10; i++) {
            uniqueDigits *= availableDigits;
            count += uniqueDigits;
            availableDigits--;
        }
        
        return count;
    }

    // simple test
    public static void main(String[] args) {
        CountNumbersWithUniqueDigits solver = new CountNumbersWithUniqueDigits();
        System.out.println(solver.countNumbersWithUniqueDigits(2)); // 91
        System.out.println(solver.countNumbersWithUniqueDigits(0)); // 1
        System.out.println(solver.countNumbersWithUniqueDigits(3)); // 739
    }
}
