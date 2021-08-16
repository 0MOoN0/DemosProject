package com.example.mybatisplus.demo.gen;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator2 {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        //============= 经常修改的自定义属性
        String talbeNames = "user";


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = "D:/temp/gen";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("YL");
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/tx?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent("gen.com.test");
        pc.setEntity("domain");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setXml("mappers");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                HashMap<String, Object> map = new HashMap<>();
                // searchAO排除的字段
                Set<String> searchAOExclude = new HashSet<>();
                searchAOExclude.add("createTime");
                searchAOExclude.add("isDelete");
                searchAOExclude.add("createBy");
                searchAOExclude.add("updateBy");
                searchAOExclude.add("updateTime");
                // editAO排除的字段
                Set<String> editAoExclude = new HashSet<>();
                editAoExclude.add("updateTime");
                editAoExclude.add("createTime");
//                editAoExclude.add("updateBy");
//                editAoExclude.add("createBy");
                editAoExclude.add("isDelete");
                // VO排除的字段
                Set<String> voExclude = new HashSet<>();
                voExclude.add("updateTime");
                voExclude.add("updateBy");
                voExclude.add("createBy");
                voExclude.add("isDelete");

                map.put("searchAOExclude", searchAOExclude);
                map.put("editAoExclude", editAoExclude);
                map.put("voExclude", voExclude);
                setMap(map);
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";
        String searchaoTemplatePath = "/templates/searchao.java.ftl";
        String editaoTemplatePath = "/templates/editao.java.ftl";
        String voTemplatePath = "/templates/vo.java.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        // SearchAO
        focList.add(new FileOutConfig(searchaoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                ;
                return projectPath + "/src/main/java/gen/com/test/ao/" + tableInfo.getEntityName()
                         + "SearchAO" + StringPool.DOT_JAVA;
            }
        });
        // EditAO
        focList.add(new FileOutConfig(editaoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                ;
                return projectPath + "/src/main/java/gen/com/test/ao/" + tableInfo.getEntityName()
                         + "EditAO" + StringPool.DOT_JAVA;
            }
        });
        // VO
        focList.add(new FileOutConfig(voTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                ;
                return projectPath + "/src/main/java/gen/com/test/vo/" + tableInfo.getEntityName()
                         + "VO" + StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        // Service接口模板
        String ServiceTemplatePath = "templates/service.java";
        templateConfig.setService(ServiceTemplatePath);
        // ServiceImpl实现类模板
        String ServiceImplTemplatePath = "templates/serviceImpl.java";
        templateConfig.setServiceImpl(ServiceImplTemplatePath);


        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(talbeNames.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}