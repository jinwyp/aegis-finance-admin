package com.yimei.finance.service.common.tools;

import com.yimei.finance.entity.common.Number;
import com.yimei.finance.repository.common.NumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NumberServiceImpl {
    @Autowired
    private NumberRepository numberRepository;

    public String getNextCode(String type) {
        Number number = new Number(type, LocalDate.now());
        numberRepository.save(number);
        int nums = numberRepository.findByTypeAndCreateDateAndIdLessThan(type, LocalDate.now(), number.getId()+1).size();
        String numsStr = "";
        if (nums > 999) numsStr = String.valueOf(nums);
        else if (nums > 99) numsStr = "0" + nums;
        else if (nums > 9) numsStr = "00" + nums;
        else numsStr = "000" + nums;
        return type + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + numsStr;
    }

    public String generateContractNo(String type) {
        Number number = new Number(type, LocalDate.now());
        numberRepository.save(number);
        int nums = numberRepository.findByTypeAndCreateDateAndIdLessThan(type, LocalDate.now(), number.getId()+1).size();
        String numsStr = "";
        if (nums > 9) numsStr = String.valueOf(nums);
        else numsStr = "0" + nums;
        return type + "-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + numsStr;
    }
}
