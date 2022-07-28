package cn.zhgliu.ezdpdemo.config;


import cn.zhgliu.ezdp.client.DataPermClient;
import cn.zhgliu.ezdp.helper.DataPermHelper;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.SQLException;

// 获取执行的sql，获取sql的id，调用client，处理sql，将处理后的sql交还给mybatis执行
@Slf4j
public class MybatisPlusDataPermInterceptor implements InnerInterceptor {

    DataPermClient dataPermClient;

    public MybatisPlusDataPermInterceptor(DataPermClient dataPermClient) {
        this.dataPermClient = dataPermClient;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        String userId = DataPermHelper.getUserId();
        log.debug("User ID:{}", userId);
        if (userId != null && userId != "") {
            String sql = boundSql.getSql();
            String statementId = ms.getId();

            log.debug("Original SQL:{}", sql);
            log.debug("MYBATIS ID :{}", statementId);

            String newSql = dataPermClient.addPermissionCondition(sql, userId, statementId);
            log.debug(newSql);
            try {
                Field field = boundSql.getClass().getDeclaredField("sql");
                field.setAccessible(true);
                field.set(boundSql, newSql);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        DataPermHelper.clear();
    }

}
