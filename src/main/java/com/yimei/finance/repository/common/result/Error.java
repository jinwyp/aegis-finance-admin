package com.yimei.finance.repository.common.result;

import lombok.*;

/**
 * Created by liuxinjie on 16/8/9.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Error {
    @NonNull
    private Integer code = 1000;
    @NonNull
    private String message;
    private String field;

    public Integer getCode() {
        if (code == null) return 1000;
        return code;
    }

    public String getField() {
        if (field == null) return "unknown";
        return field;
    }
}
