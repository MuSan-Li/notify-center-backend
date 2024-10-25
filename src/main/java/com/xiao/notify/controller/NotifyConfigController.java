package com.xiao.notify.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.model.domain.notify.request.SendNotifyRequest;
import com.xiao.notify.model.entity.NotifyConfig;
import com.xiao.notify.service.NotifyConfigService;
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
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

import static com.xiao.notify.common.ResultUtils.success;

/**
 * 订阅配置表(SendNotifyRequest)表控制层
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@RestController
@RequestMapping("notify-config")
public class NotifyConfigController {

    /**
     * 服务对象
     */
    @Resource
    private NotifyConfigService notifyConfigService;

    /**
     * 分页查询所有数据
     *
     * @param page         分页对象
     * @param notifyConfig 查询实体
     * @return 所有数据
     */
    @GetMapping
    public BaseResponse selectAll(Page<NotifyConfig> page, NotifyConfig notifyConfig) {
        return success(notifyConfigService.page(page, new QueryWrapper<>(notifyConfig)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public BaseResponse selectOne(@PathVariable Serializable id) {
        return success(notifyConfigService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param notifyConfig 实体对象
     * @return 新增结果
     */
    @PostMapping
    public BaseResponse insert(@RequestBody NotifyConfig notifyConfig) {
        return success(notifyConfigService.save(notifyConfig));
    }

    /**
     * 修改数据
     *
     * @param notifyConfig 实体对象
     * @return 修改结果
     */
    @PutMapping
    public BaseResponse update(@RequestBody NotifyConfig notifyConfig) {
        return success(notifyConfigService.updateById(notifyConfig));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public BaseResponse delete(@RequestParam("idList") List<Long> idList) {
        return success(notifyConfigService.removeByIds(idList));
    }


    /**
     * 订阅通知
     *
     * @param notifyRequest
     * @return 是否发送成功
     */
    @PostMapping("sendNotify")
    public BaseResponse<Boolean> sendNotify(@RequestBody SendNotifyRequest notifyRequest, HttpServletRequest request) {
        return success(notifyConfigService.sendNotify(notifyRequest, request));
    }

}

