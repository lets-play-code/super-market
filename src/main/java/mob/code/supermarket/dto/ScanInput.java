package mob.code.supermarket.dto;

public class ScanInput {
    public static final String SEPARATOR = "-";
    private final int count;
    private final String barcode;
    private final boolean hasCount;

    public ScanInput(String input) {
        if (input.contains(SEPARATOR)) {
            String[] parts = input.split(SEPARATOR);
            hasCount = true;
            barcode = parts[0];
            count = Integer.parseInt(parts[1]);
        } else {
            hasCount = false;
            barcode = input;
            count = 1;
        }
    }

    public String getBarcode() {
        return barcode;
    }

    public int getCount() {
        return count;
    }

    public boolean hasCount() {
        return hasCount;
    }
}
