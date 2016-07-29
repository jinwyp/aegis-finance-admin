package com.yimei.boot.utils;


import java.math.BigDecimal;
import java.util.List;

/**
 * Created by xiangyang on 16/4/13.
 */
public class NumberUtils {

    public static BigDecimal sum(final List<? extends BigDecimal> numbers) {
        BigDecimal nums = new BigDecimal("0");
        for (BigDecimal n : numbers) {
            if (n != null) {
                nums = nums.add(n);
            }
        }
        return nums;
    }

}
