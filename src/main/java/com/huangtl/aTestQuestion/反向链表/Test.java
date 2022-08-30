package com.huangtl.aTestQuestion.反向链表;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

/**
 * @Description
 * @Author huangtl
 * @Date 2021/12/27 11:35
 * @Since
 **/
public class Test {

    public static void main2(String[] args) {
        Node head = new Node(1);
        Node next1 = new Node(2);
        Node next2 = new Node(3);
        head.setNext(next1);
        next1.setNext(next2);

        System.out.println(head);

        Node pre = null;
        Node next =null;
        while (head!=null){
            next = head.getNext();//防止后续节点找不回来
            head.setNext(pre);
            pre = head;
            head = next;
        }
        System.out.println(pre);
    }

    private static final String DB_URL = "jdbc:mysql://10.10.62.103:13306";
    private static final String DB_NAME = "dtp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Yf123456";
    private static final String FILE_OUTPUT_DIR = "C:\\Users\\Jw_Tech\\Desktop\\dbdoc\\";

    // 可以设置 Word 或者 Markdown 格式
    private static final EngineFileType FILE_OUTPUT_TYPE = EngineFileType.WORD;
    private static final String DOC_FILE_NAME = "数据库表设计文档";
    private static final String DOC_VERSION = "V1.0.0";
    private static final String DOC_DESCRIPTION = "数据库表设计描述";

    public static void main(String[] args) {
        // 创建 screw 的配置
        Configuration config = Configuration.builder()
                // 版本
                .version(DOC_VERSION)
                // 描述
                .description(DOC_DESCRIPTION)
                // 数据源
                .dataSource(buildDataSource())
                // 引擎配置
                .engineConfig(buildEngineConfig())
                // 处理配置
                .produceConfig(buildProcessConfig())
                .build();

        // 执行 screw，生成数据库文档
        new DocumentationExecute(config).execute();
    }

    /**
     * 创建数据源
     */
    private static DataSource buildDataSource() {
        // 创建 HikariConfig 配置类
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setJdbcUrl(DB_URL + "/" + DB_NAME);
        hikariConfig.setUsername(DB_USERNAME);
        hikariConfig.setPassword(DB_PASSWORD);
        // 设置可以获取 tables remarks 信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        // 创建数据源
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 创建 screw 的引擎配置
     */
    private static EngineConfig buildEngineConfig() {
        return EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir(FILE_OUTPUT_DIR)
                // 打开目录
                .openOutputDir(false)
                // 文件类型
                .fileType(FILE_OUTPUT_TYPE)
                // 文件类型
                .produceType(EngineTemplateType.freemarker)
                // 自定义文件名称
                .fileName(DOC_FILE_NAME)
                .build();
    }

    /**
     * 创建 screw 的处理配置，一般可忽略
     * 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
     */
    private static ProcessConfig buildProcessConfig() {
        return ProcessConfig.builder()
                // 根据名称指定表生成
                .designatedTableName(Collections.<String>emptyList())
                // 根据表前缀生成
                .designatedTablePrefix(Collections.<String>emptyList())
                // 根据表后缀生成
                .designatedTableSuffix(Collections.<String>emptyList())
                // 忽略表名
                .ignoreTableName(Arrays.asList("test", "mytable","role","t_role","t_user"))
                // 忽略表前缀
                //.ignoreTablePrefix(Collections.singletonList("t_"))
                // 忽略表后缀
                //.ignoreTableSuffix(Collections.singletonList("_test"))
                .build();
    }

}
