package com.yimei.finance.representation.common.result;

import lombok.*;

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
