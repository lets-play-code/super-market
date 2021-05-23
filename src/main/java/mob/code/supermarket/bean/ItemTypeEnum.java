package mob.code.supermarket.bean;

import java.util.Arrays;

public enum ItemTypeEnum {
    PACKAGE("0"), WEIGHT("1");

    private final String value;

    ItemTypeEnum(String value) {
        this.value = value;
    }

    public static ItemTypeEnum of(String value) {
        return Arrays.stream(ItemTypeEnum.values())
                .filter(typeEnum -> typeEnum.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    public boolean isPackage() {
        return this == ItemTypeEnum.PACKAGE;
    }
}
