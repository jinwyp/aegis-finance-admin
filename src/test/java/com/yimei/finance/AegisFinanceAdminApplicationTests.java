package com.yimei.finance;

import com.yimei.finance.admin.service.company.AdminCompanyServiceImpl;
import com.yimei.finance.admin.service.finance.FinanceOrderServiceImpl;
import com.yimei.finance.admin.service.user.AdminGroupServiceImpl;
import com.yimei.finance.admin.service.user.AdminUserServiceImpl;
import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.kitt.service.account.KittAccountServiceImpl;
import com.yimei.finance.kitt.service.company.KittCompanyServiceImpl;
import com.yimei.finance.site.service.finance.SiteFinanceOrderServiceImpl;
import com.yimei.finance.tpl.entity.UserTest;
import com.yimei.finance.tpl.repository.JpaRepositoryDemo;
import com.yimei.finance.tpl.service.JpaRollbackDemo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AegisFinanceAdminApplicationTests {
	@Autowired
	private JpaRepositoryDemo userRepository;
    @Autowired
    private JpaRollbackDemo jpaRollbackDemo;
	@Autowired
	private AdminGroupServiceImpl groupService;
	@Autowired
	private AdminUserServiceImpl userService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private FinanceOrderServiceImpl financeOrderService;
	@Autowired
	private AdminSession adminSession;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private SiteFinanceOrderServiceImpl siteFinanceOrderService;
	@Autowired
	private AdminCompanyServiceImpl adminCompanyService;
	@Autowired
	private KittCompanyServiceImpl kittCompanyService;
	@Autowired
	private KittAccountServiceImpl accountService;


	@Test
	public void test001() {

		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		Date aaa = new Date(System.currentTimeMillis());
		System.out.println(" ------------------------------------ " + (aaa.toLocalDate().getYear()));

		System.out.println(kittCompanyService.findYMWCompanyByName("易煤网"));
		System.out.println(kittCompanyService.findYMWCompanyByName("上海瑞易供应链管理有限公司"));
		System.out.println(accountService.findUserFundAccount("易煤网"));


//		UserObject user = new UserObject("14", 0L);

//
//        List<String> keyList = identityService.getUserInfoKeys("14");
//        System.out.println(" ------- " + keyList);
//
//        System.out.println(" ------------------------------------- ");
//        System.out.println(" ------------------------------------- ");
//        System.out.println(" ------------------------------------- ");

//		List<Attachment> attachmentList = taskService.getTaskAttachments("c24upstream");

//		FinanceOrderObject financeOrderObject = new FinanceOrderObject("MYD");
//
//		User user = new User(1, "15618177577", 1, "易煤网", "审核通过");
//		for (int i=0; i<100; i++) {
//			siteFinanceOrderService.customerApplyFinanceOrder(financeOrderObject, user);
//		}


	}

    @Test
    public void testRollback() {
        userRepository.deleteAll();
        UserTest rollback = new UserTest("rollback",111l);
        UserTest unrollback = new UserTest("norollback",222l);
        try {
            jpaRollbackDemo.savePersonWithRollBack(rollback);
        } catch (Exception e) {
            System.out.println("do nothing");
        }

        Assert.assertEquals(null,userRepository.findByName("rollback"));

        try {
            jpaRollbackDemo.savePersonWithoutRollBack(unrollback);
        } catch (Exception e) {
            System.out.println("do nothing");
        }



        Assert.assertEquals("norollback",userRepository.findByName("norollback").getName());


    }


	@Test
	public void testJpa() throws Exception {

		LocalDate firstDay = LocalDate.parse("2017-01-01");
		while (firstDay.getYear() == 2017) {
			System.out.println(firstDay + " : " + firstDay.getDayOfWeek() + " : " + firstDay.getDayOfWeek().getValue());
			firstDay = firstDay.plusDays(1L);
		}

	}

}
