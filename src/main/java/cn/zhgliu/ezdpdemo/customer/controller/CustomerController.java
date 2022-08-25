package cn.zhgliu.ezdpdemo.customer.controller;


import cn.zhgliu.ezdp.helper.DataPermHelper;
import cn.zhgliu.ezdpdemo.customer.entity.Customer;
import cn.zhgliu.ezdpdemo.customer.service.ICustomerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 客户 前端控制器
 * </p>
 *
 * @author zhgliu
 * @since 2022-04-17
 */
@RestController
@RequestMapping("/customer/customer")
public class CustomerController {

    @Resource
    ICustomerService iCustomerService;

    @GetMapping("/listAll")
    public List<Customer> listAll(String userId) {
        List<Customer> list = iCustomerService.list(new QueryWrapper<Customer>().like("customer_name","公司").like("customer_name","公司"));        return list;
    }

    @GetMapping("/listAllWithPerm")
    public List<Customer> listAllWithPerm(String userId) {
        DataPermHelper.applyPermission(userId);
        List<Customer> list = iCustomerService.list(new QueryWrapper<Customer>().like("customer_name","公司").like("customer_name","公司"));
        return list;
    }

    @GetMapping("/page")
    public IPage<Customer> page(String userId) {
        IPage<Customer> ret = iCustomerService.page(new Page<>(1, 5), new QueryWrapper<>());
        return ret;
    }

    @GetMapping("/pageWithPerm")
    public IPage<Customer> pageWithPerm(String userId,Integer current,Integer size) {
        DataPermHelper.applyPermission(userId);
        IPage<Customer> ret = iCustomerService.page(new Page<>(current, size), new QueryWrapper<Customer>().like("customer_name","公司").like("customer_name","公司"));
        return ret;
    }

}
