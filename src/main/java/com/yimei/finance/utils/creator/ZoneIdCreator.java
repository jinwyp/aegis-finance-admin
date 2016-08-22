package com.yimei.finance.utils.creator;

import java.time.ZoneId;

public class ZoneIdCreator implements Creator<ZoneId> {

    @Override
    public ZoneId create(Object source) {
        return ZoneId.of(((ZoneId) source).getId());
    }
}
