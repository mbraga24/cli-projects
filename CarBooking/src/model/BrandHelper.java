package model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BrandHelper {

    private BrandHelper() {}

    public static List<String> returnBrandOptions() {
        return Arrays.stream(Brand.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
