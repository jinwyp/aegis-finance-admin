/*
package com.yimei.boot.services;

import com.yimei.api.common.EnumConst;
import com.yimei.api.groupbuy.representations.GroupBuyQualification;
import com.yimei.api.mall.representations.order.Order;
import com.yimei.api.mall.representations.sellinfo.EnumDeliverymode;
import com.yimei.api.mall.representations.sellinfo.SellInfo;
import com.yimei.api.user.representations.Company;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

*/
/**
 *
 *//*

@Component
public class ContractService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContractService.class) ;

    @Autowired
    FreeMarker freeMarker ;


    */
/**
     * 获取团购电子合同内容
     * @param id
     * @param groupBuyQualification
     * @param company
     * @param serverurl
     * @return
     *//*

    public String contractContent(int id, GroupBuyQualification groupBuyQualification, Company company, String serverurl){
        try {
            return freeMarker.render("/contracts/depositContract", new HashMap<String, Object>() {{
                put("contractno", groupBuyQualification.getMarginscode());
                int year=0;
                int month=0;
                int day=0;
                if(groupBuyQualification.getConfirmtime()==null){
                    year=groupBuyQualification.getLastupdatetime().getYear();
                    month=groupBuyQualification.getLastupdatetime().getMonthValue();
                    day=groupBuyQualification.getLastupdatetime().getDayOfMonth();
                }else {
                    year=groupBuyQualification.getConfirmtime().getYear();
                    month=groupBuyQualification.getConfirmtime().getMonthValue();
                    day=groupBuyQualification.getConfirmtime().getDayOfMonth();
                }

                put("createtime", year + " 年 " + month +" 月 "+ day +" 日 ");
                //公司表信息
                put("companyname", company.getName());
                put("companyaddress", company.getAddress());
                put("companylegalpersonname", company.getLegalpersonname());
                put("companyphone", company.getPhone());
                put("companyopeningbank", company.getOpeningbank());
                put("companyaccount", company.getAccount());
                put("companyidentificationnumword", company.getIdentificationnumword());
                put("companyfax", company.getFax());
                put("companyzipcode", company.getZipcode());
                //签订时间
                put("sellsignyear", String.valueOf(LocalDate.now().getYear()));
                put("sellsignmonth", LocalDate.now().getMonthValue());
                put("sellsignday", LocalDate.now().getDayOfMonth());
                put("buysignyear", String.valueOf(LocalDate.now().getYear()));
                put("buysignmonth", LocalDate.now().getMonthValue());
                put("buysignday", LocalDate.now().getDayOfMonth());
                put("localhost", serverurl);
            }});
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(EnumConst.EXCEPTION_REMIND + " 获取订单正式合同出错");
        }
        return null;
    }

    */
/**
     * 获取订单正式合同内容
     *
     * @param order 订单对象
     *//*

//    @Override
    public String getSellInfoContractContent(Order order, SellInfo sellInfo, Company company, String serverURL) {
        try {
            return freeMarker.render(getSellInfoContractTypeByDeliveryMode(sellInfo.getDeliverymode()), new HashMap<String, Object>() {{
                //公司表信息
                put("companyname", company.getName());
                put("companyaddress", company.getAddress());
                put("companylegalpersonname", company.getLegalpersonname());
                put("companyphone", company.getPhone());
                put("companyopeningbank", company.getOpeningbank());
                put("companyaccount", company.getAccount());
                put("companyidentificationnumword", company.getIdentificationnumword());
                put("companyfax", company.getFax());
                put("companyzipcode", company.getZipcode());
                //order表信息
                put("contractno", order.getContractno());
                put("createtime", order.getCreatetime().toLocalDate());
                put("orderpid", order.getPid());
                put("orderamount", order.getAmount());
                put("orderprice", order.getPrice());
                put("orderdeliveryregion", order.getDeliveryregion());
                put("orderdeliveryprovince", order.getDeliveryprovince());
                put("orderdeliveryplace", order.getDeliveryplace().equals("其它") == true ? order.getOtherharbour() : order.getDeliveryplace());
                put("orderdeliverytime1year", String.valueOf(order.getDeliverytime1().getYear()));
                put("orderdeliverytime1month", order.getDeliverytime1().getMonthValue());
                put("orderdeliverytime1day", order.getDeliverytime1().getDayOfMonth());
                if (order.getDeliverymode().equals("场地自提")) {
                    put("orderdeliverytime2year", String.valueOf(order.getDeliverytime2().getYear()));
                    put("orderdeliverytime2month", order.getDeliverytime2().getMonthValue());
                    put("orderdeliverytime2day", order.getDeliverytime2().getDayOfMonth());
                }
                //sellinfo表信息
                if (sellInfo.getNCV() == 0 || sellInfo.getNCV02() == 0) {
                    put("sellInfoNCV", String.valueOf("--"));
                } else if (sellInfo.getNCV() == sellInfo.getNCV02()) {
                    put("sellInfoNCV", String.valueOf(sellInfo.getNCV()));
                } else {
                    put("sellInfoNCV", String.valueOf(sellInfo.getNCV() + "-" + sellInfo.getNCV02()));
                }
                if (sellInfo.getRS().compareTo(BigDecimal.ZERO) == 0 || sellInfo.getRS02().compareTo(BigDecimal.ZERO) == 0) {
                    put("sellInfoRS", String.valueOf("--"));
                } else if (sellInfo.getRS().compareTo(sellInfo.getRS02()) == 0) {
                    put("sellInfoRS", String.valueOf(sellInfo.getRS()));
                } else {
                    put("sellInfoRS", String.valueOf(sellInfo.getRS() + "-" + sellInfo.getRS02()));
                }
                put("sellInfoinspectorg", sellInfo.getInspectorg().equals("其它") ? sellInfo.getOtherinspectorg() : sellInfo.getInspectorg());
                put("localhost", serverURL);
            }});
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(EnumConst.EXCEPTION_REMIND + " 获取订单正式合同出错");
        }
        return null;
    }


    */
/**
     * 获取标准合同内容
     * @param deliverymode      供应信息提货方式
     *//*

//    @Override
    public String getSellInfoStandardContractContent(String deliverymode, String serverURL) {
        try {
            return freeMarker.render(getSellInfoContractTypeByDeliveryMode(deliverymode), new HashMap<String, Object>() {{
                put("localhost", serverURL);
            }});
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(EnumConst.EXCEPTION_REMIND + " 获取供应信息标准合同出错");
        }
        return null;
    }



    public String getSellInfoContractTypeByDeliveryMode(String deliverymode) {
        if (deliverymode.equals(EnumDeliverymode.港口平仓.toString())) {
            return "/contracts/portUnwinding";
        } else if (deliverymode.equals(EnumDeliverymode.到岸舱底.toString())) {
            return "/contracts/shoreBottom";
        } else if (deliverymode.equals(EnumDeliverymode.场地自提.toString())) {
            return "/contracts/spaceDeliveryPayAll";
        } else {
            return null;
        }
    }

}
*/
