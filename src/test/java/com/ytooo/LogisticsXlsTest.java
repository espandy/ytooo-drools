package com.ytooo;

import com.ytooo.bean.OrderInfo;
import lombok.SneakyThrows;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogisticsXlsTest {
    @Autowired
    private KieSession session;

    @Autowired
    private KieBase kieBase;

    @SneakyThrows
    @Test
    public void compiler(){
        String drl;
        //String filePath="/Users/espandy/IdeaProjects/cnrmall-drools/src/main/resources/xls/dispatch.xls";
        String filePath="D:\\ProjectCode\\ytooo-drools\\src\\main\\resources\\xls\\dispatch.xls";
        File file = new File(filePath);
        InputStream is = Files.newInputStream(file.toPath());
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
//        String fileType = "xls";
//        String fileType = filePath.substring(filePath.lastIndexOf("."));
//        if ("xls".equals(fileType)) {
        drl = compiler.compile(is, InputType.XLS);
//        }
        System.out.println(drl);

        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        session = kieHelper.build().newKieSession();


        OrderInfo orderInfo2 = new OrderInfo();
        orderInfo2.setCategoryName("酒");
        orderInfo2.setWeight(2.00);
        orderInfo2.setPrice(800);

        session.insert(orderInfo2);
        session.fireAllRules();

        System.out.println(orderInfo2.getLogistics());
    }

    @Test
    public void test() {
        OrderInfo orderInfo1 = new OrderInfo();
        orderInfo1.setCategoryName("酒");
        orderInfo1.setWeight(3.00);
        orderInfo1.setPrice(100);

        OrderInfo orderInfo2 = new OrderInfo();
        orderInfo2.setCategoryName("烟");
        orderInfo2.setWeight(2.00);
        orderInfo2.setPrice(100);

//        OrderInfo orderInfo3 = new OrderInfo();
//        orderInfo3.setCategoryName("荼");
//        orderInfo3.setWeight(2.00);
//        orderInfo3.setPrice(800);

        session.insert(orderInfo1);
        session.insert(orderInfo2);
//        session.insert(orderInfo3);
        session.fireAllRules();

        System.out.println("o1"+Arrays.toString(orderInfo1.getLogistics().toArray()));
        System.out.println("o2"+Arrays.toString(orderInfo2.getLogistics().toArray()));
//        System.out.println("o3"+Arrays.toString(orderInfo3.getLogistics().toArray()));
    }

}
