package pl.kelog.smsalerts;

import java.util.List;
import java.util.function.Supplier;

public class Utils {
    
    public static void assertPresent(List<String> values, Supplier<? extends RuntimeException> supplier) {
        values.forEach(value -> assertPresent(value, supplier));
    }
    
    public static void assertPresent(String value, Supplier<? extends RuntimeException> supplier) {
        if (isNullOrEmpty(value)) {
            throw supplier.get();
        }
    }
    
    private static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }
}
