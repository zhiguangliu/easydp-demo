package cn.zhgliu.ezdpdemo.config;


import cn.zhgliu.ezdp.client.DataPermClient;
import cn.zhgliu.ezdp.helper.DataPermHelper;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

// 获取执行的sql，获取sql的id，调用client，处理sql，将处理后的sql交还给mybatis执行
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Slf4j
public class MybatisPlusDataPermInterceptor implements InnerInterceptor {

    DataPermClient dataPermClient;

    public MybatisPlusDataPermInterceptor(DataPermClient dataPermClient) {
        this.dataPermClient = dataPermClient;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        System.out.println("=================================beforeQuery");

        String sql = boundSql.getSql();
        String userId = DataPermHelper.getUserId();
        String statementCode = DataPermHelper.getStatementCode();

        String id = ms.getId();
        System.out.println(sql);
        System.out.println(userId);
        System.out.println(statementCode);
        System.out.println(id);
        if (id.equals(statementCode)) {

            dataPermClient.addPermissionCondition(sql, userId, statementCode);


        }

    }

}
