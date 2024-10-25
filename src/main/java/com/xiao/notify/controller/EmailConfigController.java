package com.xiao.notify.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.model.entity.EmailConfig;
import com.xiao.notify.service.EmailConfigService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import static com.xiao.notify.common.ResultUtils.success;

/**
 * 邮箱配置表(EmailConfig)表控制层
 *
 * @author lh
 * @since 2024-10-22 21:29:46
 */
@RestController
@RequestMapping("email-config")
public class EmailConfigController {
    /**
     * 服务对象
     */
    @Resource
    private EmailConfigService emailConfigService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param emailConfig 查询实体
     * @return 所有数据
     */
    @GetMapping
    public BaseResponse selectAll(Page<EmailConfig> page, EmailConfig emailConfig) {
        return success(this.emailConfigService.page(page, new QueryWrapper<>(emailConfig)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public BaseResponse selectOne(@PathVariable Serializable id) {
        return success(this.emailConfigService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param emailConfig 实体对象
     * @return 新增结果
     */
    @PostMapping
    public BaseResponse insert(@RequestBody EmailConfig emailConfig) {
        return success(this.emailConfigService.save(emailConfig));
    }

    /**
     * 修改数据
     *
     * @param emailConfig 实体对象
     * @return 修改结果
     */
    @PutMapping
    public BaseResponse update(@RequestBody EmailConfig emailConfig) {
        return success(this.emailConfigService.updateById(emailConfig));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public BaseResponse delete(@RequestParam("idList") List<Long> idList) {
        return success(this.emailConfigService.removeByIds(idList));
    }
}

