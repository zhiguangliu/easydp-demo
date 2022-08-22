package cn.zhgliu.ezdpdemo.config;

import cn.zhgliu.ezdp.client.DataPermClient;
import cn.zhgliu.ezdp.client.impl.BaseDataPermClient;
import cn.zhgliu.ezdp.finder.DataPermMatchingModeFinder;
import cn.zhgliu.ezdp.finder.DataPermRuleFinder;
import cn.zhgliu.ezdp.finder.impl.http.HttpDataMatchingModeFinder;
import cn.zhgliu.ezdp.finder.impl.http.HttpDataPermRuleFinder;
import cn.zhgliu.ezdp.resolver.DataPermSqlResolver;
import cn.zhgliu.ezdp.resolver.impl.ali.druid.DruidSQLResolver;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasyDpConfig {

        @Value("${ezdp.server}")
    private String dataPermServer;

    @Value("${ezdp.subSystem}")
    private String subSystem;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(@Autowired DataPermClient dataPermClient) {
        MybatisPlusInterceptor i = new MybatisPlusInterceptor();
        i.addInnerInterceptor(new MybatisPlusDataPermInterceptor(dataPermClient));
        i.addInnerInterceptor(new PaginationInnerInterceptor());
        return i;
    }



    @Bean
    public DataPermMatchingModeFinder matchingModeFinder() {
        return new HttpDataMatchingModeFinder(dataPermServer);
    }

    @Bean
    public DataPermRuleFinder ruleFinder() {
        return new HttpDataPermRuleFinder(dataPermServer);
    }

    @Bean
    public DataPermSqlResolver resolver() {
        return new DruidSQLResolver();
    }

    @Bean
    public DataPermClient dataPermClient(@Autowired DataPermMatchingModeFinder matchingModeFinder,
                                         @Autowired DataPermRuleFinder ruleFinder,
                                         @Autowired DataPermSqlResolver resolver) {
        return new BaseDataPermClient(matchingModeFinder, ruleFinder,resolver,subSystem);
    }


}
