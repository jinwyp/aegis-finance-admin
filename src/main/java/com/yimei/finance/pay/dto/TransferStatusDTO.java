package com.yimei.finance.pay.dto;

import lombok.Data;

@Data
public class TransferStatusDTO {


    /*1 处理中 2 成功 3 失败*/
    private Integer status;

    private String message;

    public boolean isProcessed() {
        if (this.getStatus() == 1) {
            return true;
        }
        return false;
    }

    public boolean isSuccess() {
        if (this.getStatus() == 2) {
            return true;
        }
        return false;
    }

    public boolean isFail() {
        if (this.getStatus() == 3) {
            return true;
        }
        return false;
    }


}
