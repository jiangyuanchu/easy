package com.lomoye.easy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.lomoye.easy.dao.ConfigurableSpiderMapper;
import com.lomoye.easy.domain.ConfigurableSpider;
import com.lomoye.easy.service.ConfigurableSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 2019/8/30 15:16
 * yechangjun
 */
@Slf4j
@Service
public class ConfigurableSpiderServiceImpl extends ServiceImpl<ConfigurableSpiderMapper, ConfigurableSpider> implements ConfigurableSpiderService {

    private static final String COMMON_FIELD = "job_uuid";
    /**
     * 保存爬取的数据
     */
    @Override
    public void saveData(List<LinkedHashMap<String, String>> datas, ConfigurableSpider spider, String uuid) {
        log.info("start saveData");
        if (datas == null || datas.isEmpty()) {
            log.info("datas empty");
            return;
        }
        //判断表是否存在
        try {
            doSaveData(datas, spider, uuid);
        } catch (Exception e) {
            log.error("saveData error", e);
        }

    }

    @Override
    public LinkedHashMap<String, String> parseFields(String fieldsJson) {
        if (Strings.isNullOrEmpty(fieldsJson)) {
            return new LinkedHashMap<>();
        }

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        JSONArray fields = JSON.parseArray(fieldsJson);
        for (Object fieldObj : fields) {
            JSONObject field = (JSONObject) fieldObj;
            map.put(field.get("key").toString(), field.get("value").toString());
        }
        return map;
    }

    private void doSaveData(List<LinkedHashMap<String, String>> datas, ConfigurableSpider metaInfo, String uuid) throws SQLException, ClassNotFoundException {
        //插入数据
        String sql = getSql(datas, metaInfo, uuid);
        log.info("doSaveData sql={}", sql);

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        try {
            statement.execute(sql);
        } catch (Exception e) {
            log.error("doSaveData error", e);
            //可能是因为表不存在 创建表试试
            createTable(metaInfo.getTableName(), statement, metaInfo.getFields());
            //再执行一遍
            statement.execute(sql);
        } finally {
            statement.close();
            connection.close();
        }


    }

    /**
     * CREATE TABLE job
     (
     uuid CHAR(32) NOT NULL COMMENT '主键uuid',
     name VARCHAR(30) NULL DEFAULT NULL COMMENT '任务名',
     PRIMARY KEY (uuid)
     );
     * @param tableName
     * @param statement
     * @param fields
     */
    private void createTable(String tableName, Statement statement, LinkedHashMap<String, String> fields) throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + "\n");
        StringBuilder fieldDefs = new StringBuilder("( ");
        fieldDefs.append("`").append("id").append("`").append(" BIGINT(20) NOT NULL AUTO_INCREMENT ").append(",\n");
        fields.forEach((k, v) -> {
            StringBuilder fieldDef = new StringBuilder();
            fieldDef.append("`").append(k).append("`").append(" VARCHAR(255) ").append(",\n");
            fieldDefs.append(fieldDef);
        });
        fieldDefs.append("`").append(COMMON_FIELD).append("`").append(" CHAR(64) ").append(",\n");
        fieldDefs.append("PRIMARY KEY (id) \n");
        fieldDefs.append(")");

        sql.append(fieldDefs);
        log.info("createTable sql={}", sql);
        statement.executeUpdate(sql.toString());
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        // 加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 创建连接
        return DriverManager.getConnection("jdbc:mysql://ip:port/temp?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true", "username", "password");
    }

    private String getSql(List<LinkedHashMap<String, String>> datas, ConfigurableSpider metaInfo, String uuid) {
        StringBuilder fields = new StringBuilder();
        metaInfo.getFields().forEach((k, v) -> {
            fields.append("`").append(k).append("`").append(",");
        });
        fields.append("`").append(COMMON_FIELD).append("`");//任务id字段

        StringBuilder sql = new StringBuilder("INSERT INTO `" + metaInfo.getTableName() + "` (" + fields + ") VALUES ");

        StringBuilder values = new StringBuilder();
        datas.forEach((data) -> {
            StringBuilder sqlValue = new StringBuilder();
            data.forEach((k, v) -> {
                sqlValue.append("'").append(v).append("'").append(",");
            });
            sqlValue.append("'").append(uuid).append("'");
            values.append(",(").append(sqlValue).append(" )");
        });

        sql.append(values.substring(1, values.length()));

        return sql.toString();
    }
}
