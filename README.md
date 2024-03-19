## 基于alphaPose，Xception，ecapa-Tdnn的多模态老年人辅助监护系统

### Python端为异常检测器
- 基于三种模型构建
- 多线程
- 音频和视频输入
- 检测到异常后写入数据库
### Java端为前后端及检测器管理系统
- 基于spring-boot 和 mybatis-Plus
- 使用mysql数据库
- 能够进行老人与护工注册登陆管理，检测器新建和丢弃及异常记录管理
- 能够定期生成监护日志
