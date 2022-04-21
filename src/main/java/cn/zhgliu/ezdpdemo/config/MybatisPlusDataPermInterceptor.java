package cn.zhgliu.ezdpdemo.config;


import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

// 获取执行的sql，获取sql的id，调用client，处理sql，将处理后的sql交还给mybatis执行
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Slf4j
@Component
public class MybatisPlusDataPermInterceptor implements InnerInterceptor {

    public MybatisPlusDataPermInterceptor() {
        System.out.println("=====================MybatisPlusDataPermInterceptor====================");
    }

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        System.out.println("=================================willDoQuery");
        return false;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        System.out.println("=================================beforeQuery");

    }

    @Override
    public boolean willDoUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        System.out.println("=================================willDoUpdate");
        return false;
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        System.out.println("=================================beforeUpdate");

    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        System.out.println("=================================beforePrepare");

    }

    @Override
    public void beforeGetBoundSql(StatementHandler sh) {
        System.out.println("=================================beforeGetBoundSql");

    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("=================================setProperties");

    }
}
