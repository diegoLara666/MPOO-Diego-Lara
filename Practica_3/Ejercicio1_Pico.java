class Result {

    /*
     * Complete the 'longestPeak' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY array as parameter.
     */

    public static int longestPeak(int[] array) {
        if (array == null || array.length < 3) {
            return 0;
        }

        int maxLength = 0;
        int i = 1;

        while (i < array.length - 1) {
            boolean esCima = (array[i] - array[i - 1] > 1) && (array[i] > array[i + 1]);

            if (!esCima) {
                i++;
                continue;
            }

            int left = i - 1;
            while (left > 0 && (array[left] - array[left - 1] > 1)) {
                left--;
            }

            int right = i + 1;
            while (right < array.length - 1 && (array[right] > array[right + 1])) {
                right++;
            }

            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }

            i = right;
        }

        return maxLength;
    }

}
