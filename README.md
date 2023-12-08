系统架构（从上往下）
前端：vue3，vuex
反向代理服务器和流量网关：nginx
服务网关：gateway
微服务注册中心：nacos
微服务远程调用：openfeign
微服务熔断降级：sentinel
分布式事务：seata
缓存和延时任务队列：redis
安全框架：springsecurity，jwt
消息队列：rabbitmq，kafka，rocketmq
对象存储服务：minio
容器部署：docker
视频压缩工具：ffmpeg（后续可能更换）
持久化框架：mybatisplus
数据库：MySQL

后续更新：分表分库，多例模型，集群搭建，日志服务，更换网关为higress，引入k8s实现集群搭建，实现个性化推荐，引入elasticsearch实现搜索，加入游戏中心，商城，利用kafka实现实时热点计算，高并发压力测试，负载均衡测试，数据一致性测试
