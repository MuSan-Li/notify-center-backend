package com.xiao.notify.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.model.entity.NotifyInfoLog;
import com.xiao.notify.service.NotifyInfoLogService;
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
 * 订阅信息记录表(NotifyInfoLog)表控制层
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@RestController
@RequestMapping("notify-info-log")
public class NotifyInfoLogController {
    /**
     * 服务对象
     */
    @Resource
    private NotifyInfoLogService notifyInfoLogService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param notifyInfoLog 查询实体
     * @return 所有数据
     */
    @GetMapping
    public BaseResponse selectAll(Page<NotifyInfoLog> page, NotifyInfoLog notifyInfoLog) {
        return success(this.notifyInfoLogService.page(page, new QueryWrapper<>(notifyInfoLog)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public BaseResponse selectOne(@PathVariable Serializable id) {
        return success(this.notifyInfoLogService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param notifyInfoLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    public BaseResponse insert(@RequestBody NotifyInfoLog notifyInfoLog) {
        return success(this.notifyInfoLogService.save(notifyInfoLog));
    }

    /**
     * 修改数据
     *
     * @param notifyInfoLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    public BaseResponse update(@RequestBody NotifyInfoLog notifyInfoLog) {
        return success(this.notifyInfoLogService.updateById(notifyInfoLog));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public BaseResponse delete(@RequestParam("idList") List<Long> idList) {
        return success(this.notifyInfoLogService.removeByIds(idList));
    }
}

