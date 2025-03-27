package com.carbooking.utils;

import com.carbooking.domain.model.Brand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BrandHelper {

    /*
    🗒Private empty constructor:
        ✔Prevents misuse of the class.
        ✔Signals to other developers: "This class is not meant to be instantiated."
        ✔Follows clean code and utility class conventions.
     */
    private BrandHelper() {}

    public static List<String> returnBrandOptions() {
        return Arrays.stream(Brand.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
