package com.yimei.finance.kitt.service.company;

import com.yimei.finance.kitt.representation.company.KittCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("kittCompanyServiceImpl")
public class KittCompanyServiceImpl {
    @Value("${service.address}")
    private String serviceAddress;
    @Autowired
    RestTemplate restTemplate;


    /**
     * 根据公司名称获取 易煤网公司
     */
    public KittCompany findYMWCompanyByName(String name) {
        String url = serviceAddress + "/user/company?name={name}";
        return restTemplate.getForObject(url, KittCompany. class,new Object[]{name});
    }

}
