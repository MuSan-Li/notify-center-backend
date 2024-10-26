# 用户表
drop table if exists t_user_info;
create table t_user_info
(
    id            bigint auto_increment comment 'id'
        primary key,
    username      varchar(128)                       null comment '用户昵称',
    user_account  varchar(64)                        null comment '账号',
    user_password varchar(64)                        not null comment '密码',
    avatar_url    varchar(1024)                      null comment '用户头像',
    gender        tinyint                            null comment '性别',
    phone         varchar(32)                        null comment '电话',
    email         varchar(256)                       null comment '邮箱',
    user_status   int      default 0                 not null comment '状态 0 - 正常',
    user_role     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint  default 0                 not null comment '是否删除'
)
    comment '用户';

drop table if exists t_notify_config;
create table t_notify_config
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(512)                       null comment '订阅名称',
    content     text                               null comment '内容',
    notify_type int                                null comment '通知方式',
    corn        varchar(256)                       null comment '通知表达式 corn表达式',
    remarks     text                               null comment '备注',
    user_id     bigint                             null comment '用户ID',
    status      int                                null comment '状态',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除'
)
    comment '订阅配置表';

drop table if exists t_notify_log;
create table t_notify_log
(
    id                 bigint auto_increment comment 'id'
        primary key,
    notify_config_id   bigint                             null comment '订阅配置id',
    notify_config_name varchar(512)                       null comment '订阅名称',
    status             int                                null comment '发送状态',
    notify_type        int                                null comment '通知方式',
    remarks            text                               null comment '备注',
    user_id            bigint                             null comment '用户Id',
    create_time        datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete          tinyint  default 0                 not null comment '是否删除'
)
    comment '订阅信息记录表';


drop table if exists t_email_config;
create table t_email_config
(
    id          bigint auto_increment comment 'id'
        primary key,
    host        varchar(128)                       null comment '渠道方(host)',
    port        varchar(32)                        null comment '端口',
    username    varchar(256)                       null comment '用户名',
    password    varchar(256)                       null comment '密码',
    props       text                               null comment '配置信息json',
    remarks     text                               null comment '备注',
    status      int                                null comment '状态',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除'
)
    comment '邮箱配置表';
