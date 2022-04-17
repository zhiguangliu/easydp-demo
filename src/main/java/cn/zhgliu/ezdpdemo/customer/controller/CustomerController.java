package cn.zhgliu.ezdpdemo.customer.controller;


import cn.zhgliu.ezdp.helper.DataPermHelper;
import cn.zhgliu.ezdpdemo.customer.entity.Customer;
import cn.zhgliu.ezdpdemo.customer.service.ICustomerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
@RequestMapping("/customer/customer")
public class CustomerController {

    @Resource
    ICustomerService iCustomerService;

    @GetMapping("/listAll")
    public List<Customer> listAll(String userId) {
        List<Customer> list = iCustomerService.list(new QueryWrapper<>());
        return list;
    }

    @GetMapping("/listAllWithPerm")
    public List<Customer> listAllWithPerm(String userId) {
        DataPermHelper.applyPermission(userId, "cn.zhgliu.ezdpdemo.customer.mapper.CustomerMapper.selectList");
        List<Customer> list = iCustomerService.list(new QueryWrapper<>());
        return list;
    }

}
