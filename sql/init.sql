# 用户表
drop table if exists userInfo;
CREATE TABLE userInfo
(
    id            BIGINT auto_increment PRIMARY KEY COMMENT 'id',
    username      VARCHAR(128)                       NULL COMMENT '用户昵称',
    user_account  VARCHAR(64)                       NULL COMMENT '账号',
    user_password VARCHAR(64)                       NOT NULL COMMENT '密码',
    avatar_url    VARCHAR(1024)                      NULL COMMENT '用户头像',
    gender        TINYINT                            NULL COMMENT '性别',
    phone         VARCHAR(32)                       NULL COMMENT '电话',
    email         VARCHAR(256)                       NULL COMMENT '邮箱',
    user_status   INT      DEFAULT 0                 NOT NULL COMMENT '状态 0 - 正常',
    user_role     INT      DEFAULT 0                 NOT NULL COMMENT '用户角色 0 - 普通用户 1 - 管理员',
    create_time   datetime DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time   datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_delete     TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否删除'
) COMMENT '用户信息表';

drop table if exists notify_config;
create table notify_config
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(512)                       null comment '订阅名称',
    `desc`      text                               null comment '描述',
    status      int                                null comment '状态',
    remarks     text                               null comment '备注',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '订阅配置表';

drop table if exists notify_info_log;
create table notify_info_log
(
    id               bigint auto_increment comment 'id' primary key,
    notify_config_id bigint                             null comment '订阅配置id',
    status           int                                null comment '发送状态',
    remarks          text                               null comment '备注',
    create_time      datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time      datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete        tinyint  default 0                 not null comment '是否删除'
) comment '订阅信息记录表';

drop table if exists email_config;
create table email_config
(
    id          bigint auto_increment comment 'id' primary key,
    host        varchar(128)                       null comment '渠道方(host)',
    port        varchar(32)                        null comment '端口',
    username    varchar(256)                       null comment '用户名',
    password    varchar(256)                       null comment '密码',
    config      text                               null comment '配置信息json',
    remarks     text                               null comment '备注',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '邮箱配置表';
