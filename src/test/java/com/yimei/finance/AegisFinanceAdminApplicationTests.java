package com.yimei.finance;

import com.yimei.finance.entity.tpl.UserTest;
import com.yimei.finance.repository.tpl.JpaRepositoryDemo;
import com.yimei.finance.service.common.tools.NumberServiceImpl;
import com.yimei.finance.service.tpl.JpaRollbackDemo;
import com.yimei.finance.utils.CodeUtils;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AegisFinanceAdminApplicationTests {
	@Autowired
	private JpaRepositoryDemo userRepository;
    @Autowired
    private JpaRollbackDemo jpaRollbackDemo;
	@Autowired
	private NumberServiceImpl numberService;

	@Test
	public void test001() throws BadHanyuPinyinOutputFormatCombination {
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("和略电子商务上海有限公司", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("和略电子商务上海有限公司", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("和略电子商务上海有限公司", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("和略电子商务上海有限公司", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("上海瑞易供应链管理有限公司", 4,  true)));
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(" ------------------------------------ ");
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
		System.out.println(numberService.generateContractNo(CodeUtils.GeneratePinYinCode("ABCDEFG", 4, true) + "-" + CodeUtils.GeneratePinYinCode("123456", 4,  true)));
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

        userRepository.deleteAll();
		// 创建10条记录
		userRepository.save(new UserTest("AAA", 10l));
		userRepository.save(new UserTest("BBB", 20l));
		userRepository.save(new UserTest("CCC", 30l));
		userRepository.save(new UserTest("DDD", 40l));
		userRepository.save(new UserTest("EEE", 50l));
		userRepository.save(new UserTest("FFF", 60l));
		userRepository.save(new UserTest("GGG", 70l));
		userRepository.save(new UserTest("HHH", 80l));
		userRepository.save(new UserTest("III", 90l));
		userRepository.save(new UserTest("JJJ", 100l));

		// 测试findAll, 查询所有记录
		Assert.assertEquals(10, userRepository.findAll().size());

		// 测试findByName, 查询姓名为FFF的User
		Assert.assertEquals(60, userRepository.findByName("FFF").getAge().longValue());

		// 测试findUser, 查询姓名为FFF的User
		Assert.assertEquals(60, userRepository.findUser("FFF").getAge().longValue());

		// 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的User
		Assert.assertEquals("FFF", userRepository.findByNameAndAge("FFF", 60l).getName());

		// 测试删除姓名为AAA的User
		userRepository.delete(userRepository.findByName("AAA"));

		// 测试findAll, 查询所有记录, 验证上面的删除是否成功
		Assert.assertEquals(9, userRepository.findAll().size());

	}

}
