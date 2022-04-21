package cn.zhgliu.ezdpdemo.config;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class MybatisPlusDataPermInterceptorProvider  implements ObjectProvider<Interceptor[]> {
    public MybatisPlusDataPermInterceptorProvider() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Override
    public Interceptor[] getObject(Object... objects) throws BeansException {
        return new Interceptor[]{new MybatisDataPermInterceptor()};
    }

    @Override
    public Interceptor[ ]getIfAvailable() throws BeansException {
                return new Interceptor[]{new MybatisDataPermInterceptor()};

    }

    @Override
    public Interceptor[] getIfUnique() throws BeansException {
                return new Interceptor[]{new MybatisDataPermInterceptor()};

    }

    @Override
    public Interceptor[] getObject() throws BeansException {
                return new Interceptor[]{new MybatisDataPermInterceptor()};

    }
}
