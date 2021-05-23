package mob.code.supermarket.legacy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mob.code.supermarket.bean.BarCodeException;
import org.springframework.util.StringUtils;

public class BarcodeReader {

    public List<StringBuilder> symbols;
    public List<String> barcodes;
    public List<String> counts;
    private static final int NB_CHARS_BY_LINE = 27;
    public static final String SA =
        " _ | ||_|   |  |   _  _||_  _  _| _|   |_|  |" + " _ |_  _| _ |_ |_| _   |  | _ |_||_| _ |_| _|";

    public static BarcodeReader barcodeFactory() {
        BarcodeReader barcode = new BarcodeReader();
        barcode.initSymbols();
        barcode.barcodes = new ArrayList<>();
        barcode.counts = new ArrayList<>();
        return barcode;
    }

    private void initSymbols() {
        this.symbols = Stream.generate(StringBuilder::new).limit(NB_CHARS_BY_LINE).collect(Collectors.toList());
    }

    private void storeTheNumber() {
        StringBuilder n = new StringBuilder();
        symbols.stream().filter(s -> s.length() != 0)
            .forEach(s -> n.append(String.valueOf(getSymbolFromStringRepresentation(s.toString()))));
        barcodes.add(n.toString());
    }

    public int getSymbolFromStringRepresentation(final String representation) {
        if (SA.indexOf(representation) == -1) {
            throw new BarCodeException("");
        }
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
        if (StringUtils.isEmpty(in)) {
            return Arrays.asList();
        }
        try {
            String[] entryLines = updateInputEntitys(in);
            for (int i = 0; i < entryLines.length; i++) {
                if (((i + 1) % 4) != 0) {
                    splitLineAndFillAccounts(entryLines[i]);
                } else {
                    storeTheNumber();
                    initSymbols();
                    storeTheCount(entryLines[i]);
                }
            }
        } catch (BarCodeException ex) {
            throw new BarCodeException(in);
        }
        return composeBarCode();
    }

    private List<String> composeBarCode() {
        List<String> objects = new ArrayList<>();
        for (int i = 0; i < barcodes.size(); i++) {
            if (StringUtils.isEmpty(counts.get(i))) {
                objects.add(barcodes.get(i));
                continue;
            }
            objects.add(barcodes.get(i) + "-" + counts.get(i));
        }
        return objects;
    }

    private void storeTheCount(String entryLine) {
        counts.add(entryLine);
    }

    private String[] updateInputEntitys(String in) {
        String[] finalEntryLines = new String[4];
        String[] entryLines = in.split("\n");
        if (entryLines.length == 3) {
            finalEntryLines[0] = entryLines[0];
            finalEntryLines[1] = entryLines[1];
            finalEntryLines[2] = entryLines[2];
            finalEntryLines[3] = "";
        } else {
            finalEntryLines = entryLines;
        }
        return finalEntryLines;
    }

    private void splitLineAndFillAccounts(String stringLine) {
        for (int i = 3, j = 0; i <= stringLine.length(); i += 3, j++) {
            String substring = stringLine.substring(i - 3, i);
            symbols.get(j).append(substring);
        }
    }
}
