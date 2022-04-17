package cn.zhgliu.ezdpdemo.customer.service.impl;

import cn.zhgliu.ezdpdemo.customer.entity.Customer;
import cn.zhgliu.ezdpdemo.customer.mapper.CustomerMapper;
import cn.zhgliu.ezdpdemo.customer.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户 服务实现类
 * </p>
 *
 * @author zhgliu
 * @since 2022-04-17
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
