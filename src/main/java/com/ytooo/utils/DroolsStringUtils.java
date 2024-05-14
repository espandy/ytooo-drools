package com.ytooo.utils;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

public class DroolsStringUtils {
    public static boolean isEmpty(String param) {
        return param == null || param.isEmpty();
    }

    /**
     * 验证决策表格式是否正确
     * 正确返回DRL文件
     * 错误返回 NULL字符串
     * @param filePath 文件存储地址
     * @param fileType 文件类型  XLS 和 CSV 两种
     * @return 返回DRL文件内容
     */
    public static String ValidationecisionTable(String filePath,String fileType) {
        String drl =null;
        try {
            File file = new File(filePath);
            InputStream is = Files.newInputStream(file.toPath());
            // DRL 解析类 ：compile 方法解析文件 可以解析 XLS 和 CSV 两种文件格式
            SpreadsheetCompiler conCompiler = new SpreadsheetCompiler();
            if("XLS".equals(fileType)) {
                drl = conCompiler.compile(is, InputType.XLS);
            }else if("CSV".equals(fileType)){
                drl = conCompiler.compile(is, InputType.CSV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drl;
    }

    /**
     * 执行drl文件并返回结果集
     * @param drl  drl文件内容
     * @param obj  所需执行的对象
     * @return 返回对象执行后的结果
     */
    public static Object ExecuteDRLFile(String drl,Object obj) {
        if(drl!=null) {
            KieHelper helper = new KieHelper();
            helper.addContent(drl, ResourceType.DRL);
            KieSession kieSession = helper.build().newKieSession();
            kieSession.insert(obj);
            int i = kieSession.fireAllRules();
            System.out.println("规则执行了"+i+"次");
        }else {
            System.out.println("文件解析错误，请检查决策表");
        }
        return obj;

    }
}