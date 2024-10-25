package com.xiao.notify.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * page对象转换
 *
 * @author lh
 */
public class PageConverter {

    public static <T, R> Page convertPage(Page<T> page, Class<R> clazz) {
        Page<R> newPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<R> records = new ArrayList<>();
        for (T t : page.getRecords()) {
            records.add(BeanUtil.toBean(t, clazz));
        }
        newPage.setRecords(records);
        return newPage;
    }
}