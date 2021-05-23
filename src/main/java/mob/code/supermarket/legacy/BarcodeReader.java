package mob.code.supermarket.legacy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BarcodeReader {

    List<StringBuilder> symbols;
    public List<String> barcodes = new ArrayList<>();
    private static final int NB_CHARS_BY_LINE = 27;
    public static final String SA =
            " _ | ||_|   |  |   _  _||_  _  _| _|   |_|  | _ |_  _| _ |_ |_| _   |  | _ |_||_| _ |_| _|";

    public static BarcodeReader barcodeFactory() {
        BarcodeReader barcode = new BarcodeReader();
        barcode.initSymbols();
        return barcode;
    }

    private void initSymbols() {
        this.symbols = Stream.generate(StringBuilder::new)
                .limit(NB_CHARS_BY_LINE)
                .collect(Collectors.toList());
    }

    private void storeTheNumber() {
        StringBuilder n = new StringBuilder();
        symbols.stream().filter(s -> s.length() != 0).forEach(s ->
                n.append(getSymbolFromStringRepresentation(s.toString()))
        );
        barcodes.add(n.toString());
    }

    public int getSymbolFromStringRepresentation(final String representation) {
        return SA.indexOf(representation) / 9;
    }


    public void getAccountNumbers() {
        // Read from file
        // read the line by 4-tuple
        // Every 4 lines get the account number
        // Store  the number in a List
        // Then re-init the accountsymbols list
        // Print the account list
    }

    public List<String> getBarcode(String in) {
        // Read from string
        // read the line by 4-tuple
        String[] entryLines = in.split("\n");
        for (int i = 0; i < entryLines.length; i++) {
            if (((i + 1) % 4) != 0) {
                splitLineAndFillAccounts(entryLines[i]);
            } else {
                storeTheNumber();
                initSymbols();
            }
        }
        barcodes.forEach(System.out::println);
        return barcodes;
    }

    private void splitLineAndFillAccounts(String stringLine) {
        for (int i = 3, j = 0; i <= stringLine.length(); i += 3, j++) {
            symbols.get(j).append(stringLine.substring(i - 3, i));
        }
    }
}
