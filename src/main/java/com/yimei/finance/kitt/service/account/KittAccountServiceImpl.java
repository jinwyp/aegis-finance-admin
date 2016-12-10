package com.yimei.finance.kitt.service.account;

import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.kitt.representation.account.EnumKittUserError;
import com.yimei.finance.kitt.representation.account.EnumKittUserFundAccountStatus;
import com.yimei.finance.kitt.representation.account.KittUserFundAccount;
import com.yimei.finance.kitt.representation.common.KittResult;
import com.yimei.finance.kitt.representation.company.KittCompany;
import com.yimei.finance.kitt.service.company.KittCompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service("kittAccountServiceImpl")
public class KittAccountServiceImpl {
    @Value("${service.address}")
    private String serviceAddress;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private KittCompanyServiceImpl companyService;

    /**
     * 获取资金账户
     */
    public Result findUserFundAccount(String companyName) {
        KittCompany company = companyService.findYMWCompanyByName(companyName);
        String url = serviceAddress + "/userfunderinfo/userFundAccount?userId={userId}";
        KittUserFundAccount userFundAccount = restTemplate.getForObject(url, KittUserFundAccount.class, new Object[]{company.getUserid()});
        if (userFundAccount == null) return Result.error(EnumKittUserError.该公司还没有在易煤网开通资金账户.toString());
        else if (userFundAccount.getStatus() == EnumKittUserFundAccountStatus.SUCCESS.id) return Result.success().setData(userFundAccount);
        else if (userFundAccount.getStatus() == EnumKittUserFundAccountStatus.OPENING.id) return Result.error(EnumKittUserError.该公司的资金账户正在开通中.toString());
        else if (userFundAccount.getStatus() == EnumKittUserFundAccountStatus.LOCKED.id) return Result.error(EnumKittUserError.该公司的资金账户已被锁定.toString());
        else if (userFundAccount.getStatus() == EnumKittUserFundAccountStatus.DISABLED.id) return Result.error(EnumKittUserError.FundAccount_Disabled);
        else return Result.error(EnumKittUserError.FundAccount_Disabled);
    }

    /**
     * 检查支付密码是否正确
     */
    public Result checkFundAccountPayPassword(String companyName, String password, BigDecimal cash) {
        KittCompany company = companyService.findYMWCompanyByName(companyName);
        String url = serviceAddress + "/fundAccount/passwordCheck?userId={userId}&paypassword={paypassword}&cash={cash}";
        KittResult kittResult = restTemplate.getForObject(url, KittResult.class, new Object[]{company.getUserid(), password, cash});
        if (kittResult.isSuccess()) return Result.success();
        else return Result.error(kittResult.getError());
    }

}
