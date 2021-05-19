package mob.code.supermarket.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScanInputTest {
    @Test
    public void should_have_count_1_if_barcode_only() {
        ScanInput scanInput = new ScanInput("1234567");
        assertThat(scanInput.getCount()).isEqualTo(1);
        assertThat(scanInput.getBarcode()).isEqualTo("1234567");
    }

    @Test
    public void should_have_int_count_if_has_2nd_part() {
        ScanInput scanInput = new ScanInput("1234567-12");
        assertThat(scanInput.getCount()).isEqualTo(12);
        assertThat(scanInput.getBarcode()).isEqualTo("1234567");
    }
}