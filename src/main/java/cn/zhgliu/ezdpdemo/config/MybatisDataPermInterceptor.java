package cn.zhgliu.ezdpdemo.config;


import cn.zhgliu.ezdp.client.DataPermClient;
import cn.zhgliu.ezdp.helper.DataPermHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

// 获取执行的sql，获取sql的id，调用client，处理sql，将处理后的sql交还给mybatis执行
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Slf4j
public class MybatisDataPermInterceptor implements Interceptor {

    ApplicationContext applicationContext;

    private ExecutorService executorService;


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (executorService == null) {
            throw new RuntimeException("未配置正确的线程执行器，请检查MybatisDataPermInterceptor的配置。");
        }


        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];

        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }


        //获取到原始sql语句
        String sql = boundSql.getSql();
        String userId = DataPermHelper.getUserId();

        if (StringUtils.isBlank(userId) || StringUtils.contains(ms.getId(), "com.ecms.ecframe.dataperm")) {
            // 如果是没有获取到用户，或者当前查询是查询数据权限的，则直接查询。不用再处理数据权限了。
            return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        }


        String queryId = ms.getId();
        //TODO 由于目前无法设置插件顺序，所以暂时采取这种方式实现对分页信息查询和数据查询的同步控制。
        if (queryId.endsWith("_COUNT")) {
            queryId = queryId.substring(0, queryId.length() - 6);
        }
//            String afterSql = dataPermClient.addPermitionCondition(sql, userId, queryId);
        String afterSql = sql;


        Map<String, DataPermClient> beansOfType = applicationContext.getBeansOfType(DataPermClient.class);
        if (beansOfType.size() > 1) {
            throw new RuntimeException("ONE APPLICATION CAN ONLY USE ONE " +
                    "com.ecms.ecframe.datapermclient.service.DataPermClient INSTANCE.PLEASE CHECK YOUR CONFIG." +
                    "一个应用中，只能配置一个DataPermClient的实例，请检查配置文件");
        }
        DataPermClient client = applicationContext.getBean(DataPermClient.class);
        if (client == null) {
            throw new RuntimeException("系统中，没有配置正确的DataPermClient，如果不需要数据权限功能，请在配置文件中取消该interceptor的配置。");
        }
//        if (client instanceof DataPermMultiThreadClient) {
//            try {
//                DataPermMultiThreadClient mClient = (DataPermMultiThreadClient) client;
//                mClient.prepare(new EcDpClientParam(sql, userId, queryId));
//                Future f = executorService.submit(mClient);
//                afterSql = String.valueOf(f.get());
//            } catch (Exception e) {
//                if (e.getCause() instanceof DataPermRuleException) {
//                    throw e.getCause();
//                } else {
//                    throw e;
//                }
//            }
//
//        } else {
//            afterSql = client.addPermitionCondition(sql, userId, queryId);
//
//        }

        log.debug("最终sql的结果是：" + afterSql);

        //TODO 通过反射修改sql语句，此处方法比较暴力，后面应考虑改成mybati原生方式添加条件。
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, afterSql);
        //注：下面的方法可以根据自己的逻辑调用多次，在分页插件中，count 和 page 各调用了一次
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
