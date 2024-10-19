drop table user;
# 用户表
CREATE TABLE user
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
) COMMENT '用户';