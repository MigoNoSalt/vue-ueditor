# vue-ueditor

vue+ueditor+springboot的整合

本例代码基于ueditor1.4.3.3源码改编。

前端：
    vue-ueditor对ueditor进行了简单的封装，实现双向绑定

后端：
    适配springboot配置文件读取，提取部分配置项

ueditor.config=config.json 配置文件存放地址（classpath）

ueditor.unified=true 是否统一上传路径（图片上传，视频上传...）

ueditor.upload-path=E:/upload/ 文件上传存放地址

ueditor.url-prefix=http://localhost:8080/upload/ 文件显示路径前缀

