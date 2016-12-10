package com.yimei.finance.pay;

import com.google.common.collect.Maps;
import com.yimei.finance.pay.dto.CashAccountDTO;
import com.yimei.finance.pay.dto.TransferDTO;
import com.yimei.finance.pay.dto.TransferStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service(value = "aegisPgRestPayService")
@Transactional(readOnly = false)
public class RestPayService {

    private static final Logger logger = LoggerFactory.getLogger(RestPayService.class);

    @Value("${pg.restAddress}")
    private String restAddress;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 创建系统内账户
     *
     * @param companyId 公司id
     * @return {
     * "accId": "string",
     * "status": 1,
     * "errMess": "string"
     * }
     */
    public CashAccountDTO createAccount(String companyId) {
        final String url = restAddress + "/pg/createAccount";
        Map<String, Object> requestBody = Maps.newHashMap();
        // idType = 2 默认为用户id
        requestBody.put("idType", 2);
        // idValue  默认用户id
        requestBody.put("idValue", companyId);
        requestBody.put("operator", "jm_system_create");
        return restTemplate.postForEntity(url, requestBody, CashAccountDTO.class).getBody();
    }

    /**
     * 创建中信资金账户
     *
     * @param accountName    账户名称
     * @param cashAccountDTO 系统资金账户
     * @return {
     * "accNo": "string",
     * "status": 1,
     * "errMess": "string"
     * }
     */
    public CashAccountDTO createZXBankAccount(String accountName, CashAccountDTO cashAccountDTO) {
        final String url = restAddress + "/pg/createBankAccount";
        Map<String, Object> requestBody = Maps.newHashMap();
        requestBody.put("name", accountName);
        requestBody.put("bankId", 1);
        requestBody.put("accId", cashAccountDTO.getAccId());
        requestBody.put("operator", "jm_system_create");
        return restTemplate.postForEntity(url, requestBody, CashAccountDTO.class).getBody();
    }


    /**
     * 查询是否开通账户成功
     *
     * @param accId 系统账户id
     * @param accNo 资金账号id
     * @return {
     * "status": 1,
     * "errMess": "string"
     * }
     */
    public TransferStatusDTO queryAccountStatus(String accId, String accNo) {
        final String url = restAddress + "/pg/queryAccount";
        Map<String, Object> requestBody = Maps.newHashMap();
        requestBody.put("accId", accId);
        requestBody.put("accNo", accNo);
        requestBody.put("bankId", 1);
        requestBody.put("operator", "jm_system_create");
        return restTemplate.postForEntity(url, requestBody, TransferStatusDTO.class).getBody();
    }

    /**
     * 查询账户余额
     *
     * @param accId 系统账户id
     * @param accNo 资金账号id
     * @return {
     * "errMess": "string",
     * "status": 1,
     * "sjAmount": 0,
     * "kyAmount": 0,
     * "djAmount": 0
     * }
     */
    public CashAccountDTO queryCashAccountBalance(String accId, String accNo) {
        final String url = restAddress + "/pg/queryBalance";
        Map<String, Object> requestBody = Maps.newHashMap();
        requestBody.put("accId", accId);
        requestBody.put("accNo", accNo);
        requestBody.put("operator", "jm_system_create");
        return restTemplate.postForEntity(url, requestBody, CashAccountDTO.class).getBody();
    }

    /**
     * 交易状态查询
     *
     * @param clientNo 交易流水号
     * @return {
     * "status": 1,
     * "message": "string"
     * }
     */
    public TransferStatusDTO queryTransferStatus(String clientNo) {
        final String url = restAddress + "/pg/transferQuery";
        Map<String, Object> requestBody = Maps.newHashMap();
        requestBody.put("clientNo", clientNo);
        return restTemplate.postForEntity(url, requestBody, TransferStatusDTO.class).getBody();
    }

    /**
     * 资金账户转账
     * {
     * "targetAccNm": "string",
     * "srcAccId": "string",
     * "srcAccNm": "string",
     * "srcAccNo": "string",
     * "clientId": "string",
     * "targetAccId": "string",
     * "targetAccNo": "string",
     * "amount": 0,
     * "operator": "string",
     * "bankId": 1,
     * "memo": "string"
     * }
     *
     * @param transferDTO
     * @return {
     * "status": 1,
     * "message": "string"
     * }
     */
    public TransferStatusDTO cashAccountTransfer(TransferDTO transferDTO) {
        final String url = restAddress + "/pg/transferSame";
        return restTemplate.postForEntity(url, transferDTO, TransferStatusDTO.class).getBody();
    }


    /**
     * 提现
     * <p>
     * {
     * "sameBank": 0,
     * "srcAccId": "string",
     * "srcAccNo": "string",
     * "srcAccNm": "string",
     * "targetAccNo": "string",
     * "targetAccNm": "string",
     * "clientId": "string",
     * "amount": 0,
     * "targetBankName": "string",
     * "targetSubBranchName": "string",
     * "targetZFLHH": "string",
     * "bankId": 1,
     * "operator": "string",
     * "memo": "string"
     * }
     *
     * @param transferDTO
     * @return {
     * "status": 1,
     * "message": "string"
     * }
     */
    public TransferStatusDTO withdraw(TransferDTO transferDTO) {
        final String url = restAddress + "/pg/transferDiff";
        return restTemplate.postForEntity(url, transferDTO, TransferStatusDTO.class).getBody();
    }

}
