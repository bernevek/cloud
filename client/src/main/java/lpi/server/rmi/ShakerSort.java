package lpi.server.rmi;

import java.nio.charset.StandardCharsets;

public class ShakerSort implements Task<int[]> {
    private static final long serialVersionUID = 227L;

    private final FileInfo fileInfo;

    public ShakerSort() {
        fileInfo = null;
    }

    public ShakerSort(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public int[] execute() {
        String fileContent = new String(fileInfo.getFileContent(), StandardCharsets.UTF_8).trim();
        String[] proxyArray = (fileContent.equals("")) ? new String[]{} : fileContent.split(" ");
        int[] numbers;
        if (proxyArray.length == 0) {
            numbers = new int[]{};
        } else {
            numbers = new int[proxyArray.length];
            for (int i = 0; i < proxyArray.length; i++) {
                numbers[i] = Integer.parseInt(proxyArray[i]);
            }
        }
        sort(numbers);
        return numbers;
    }

    private void sort(int[] numbers) {
        int size = numbers.length;
        int lastSwap = 0;
        int temp = 0;
        for (int i = 0; i < size / 2; i++) {
            int currentSwap = 0;
            for (int j = lastSwap + 1; j < numbers.length - i; j++) {
                if (numbers[j - 1] > numbers[j]) {
                    temp = numbers[j - 1];
                    numbers[j - 1] = numbers[j];
                    numbers[j] = temp;
                    currentSwap = j;
                }
            }
            lastSwap = currentSwap;

            for (int j = lastSwap - 1; j >= i + 1; j--) {
                if (numbers[j - 1] > numbers[j]) {
                    temp = numbers[j - 1];
                    numbers[j - 1] = numbers[j];
                    numbers[j] = temp;
                    currentSwap = j;
                }
            }
            lastSwap = currentSwap;
        }
    }
}
