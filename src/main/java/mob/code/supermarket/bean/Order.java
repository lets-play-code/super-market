package mob.code.supermarket.bean;

public class Order {
    private final String input;

    public Order(String input) {
        this.input = input;
    }

    public String barcode() {
        return splitBy()[0];
    }

    private String[] splitBy() {
        return this.input.split("-");
    }

    public int count() {
        String[] split = splitBy();
        if(split.length==1){
            return 1;
        }
        return Integer.parseInt(split[1]);
    }
}
