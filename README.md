
### 简介
本项目基于 组件化 + Arouter + Jetpack + Rxjava + Retrofit + AOP 等框架实现的一款开源项目。如有任何疑问或bug欢迎给我提[issues]((https://github.com/1170762202/WanAndroid/issues)),项目会一直维护下去，一起努力打造一个完美的app。
[源码地址](https://github.com/1170762202/WanAndroid)
喜欢的话，记得给个star哦!

### [1.2.1版本更新](https://github.com/1170762202/WanAndroid/releases)
* 新增国际化，兼容部分机型国际化失败

### [1.1.1版本更新](https://github.com/1170762202/WanAndroid/releases)
* module单独编译运行
* module-main迁移至app，并移除module-main



#### [历史版本](https://github.com/1170762202/WanAndroid/releases)

#### 首页有彩蛋哦，等你发现！！！

### 效果图
# [效果图加载不出来点我](https://www.jianshu.com/p/96830179c418)

![1.png](https://upload-images.jianshu.io/upload_images/4906229-2b91016927347d31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) | ![2.png](https://upload-images.jianshu.io/upload_images/4906229-f4a679ae485e111d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
|:-|:-|


![欢迎页.gif](https://upload-images.jianshu.io/upload_images/4906229-f904c725092c2f32.gif?imageMogr2/auto-orient/strip) | ![登录.gif](https://upload-images.jianshu.io/upload_images/4906229-825577eb221a70a5.gif?imageMogr2/auto-orient/strip) | ![首页.gif](https://upload-images.jianshu.io/upload_images/4906229-b9451cbd01f718c4.gif?imageMogr2/auto-orient/strip) |  ![收藏动画.gif](https://upload-images.jianshu.io/upload_images/4906229-93679f83ed8aa0f2.gif?imageMogr2/auto-orient/strip)
|:-|:-|:-|:-|


![公众号.gif](https://upload-images.jianshu.io/upload_images/4906229-d48f9cbd51cf4eb8.gif?imageMogr2/auto-orient/strip) | ![项目.gif](https://upload-images.jianshu.io/upload_images/4906229-80cbbffc39439e92.gif?imageMogr2/auto-orient/strip) | ![广场.gif](https://upload-images.jianshu.io/upload_images/4906229-188c6ab70160c07b.gif?imageMogr2/auto-orient/strip) | ![分享文章.gif](https://upload-images.jianshu.io/upload_images/4906229-8a2e58bd4c6dd95b.gif?imageMogr2/auto-orient/strip)
|:-|:-|:-|:-|

![1.gif](https://upload-images.jianshu.io/upload_images/4906229-ff3a3a02522c6a23.gif?imageMogr2/auto-orient/strip) | ![1.gif](https://upload-images.jianshu.io/upload_images/4906229-ff3a3a02522c6a23.gif?imageMogr2/auto-orient/strip) | ![1.gif](https://upload-images.jianshu.io/upload_images/4906229-ff3a3a02522c6a23.gif?imageMogr2/auto-orient/strip) | ![1.gif](https://upload-images.jianshu.io/upload_images/4906229-ff3a3a02522c6a23.gif?imageMogr2/auto-orient/strip) |
|:-|:-|:-|:-|

### 主要功能
* 首页、项目、广场、公众号、我的
* 登录、注册 动画交互
* 搜索页面共享元素动画过渡
* 项目页面仿高德地图滑动面板交互
* 广场页面tab跟随滑动系数渐变、列表采用谷歌爸爸的[flexboxlayout]([https://github.com/google/flexbox-layout](https://github.com/google/flexbox-layout)
)流式布局
* 广场页面根据滑动系数给indicator添加动画效果
* 公众号页面点击左上角为一个90°的arc交互动画，列表数据从下往上过渡的动画效果
* 我的页面仿百度外卖个人中心水波纹效果
* 整体采用[Material Design]([https://www.material.io/](https://www.material.io/)
)设计风格
* 首页有彩蛋哦！

### 项目目录结构

# 目录结构
```java
|- WanAndroid
||-- app // app 入口
    ||librarys //library库
      ||--library-aop// aop 封装（登录校验、点击）
      ||--library-db// room数据库封装
      ||--library-network// 网络请求封装（livedata+rxjava+retrofit）
      ||--library-base// 基础封装（BaseAc、BaseFg、BaseUtil等）
      ||--library-common//通用库（全局Context获取等）
      ||--library-widget// 控件封装
  ||--modules// 功能模块
    ||--module-home// 首页模块
    ||--module-login// 登录模块
    ||--module-project// 项目模块
    ||--module-square // 广场模块
    ||--module-public //公众号模块
    ||--module-mine//我的模块
    ||--module-web//网页模块
||-- README.md
 ```

#### module单独编译运行说明
gradle.properties 文件下有个"集成开发模式" 和 "组件开发模式"的切换开关 true表示组件独立运行，false表示一个library
````java
isRunModule=true
````






## 主要开源框架

*   [flexbox-layout](https://github.com/google/flexbox-layout)
*   [RxJava](https://github.com/ReactiveX/RxJava)

*   [RxAndroid](https://github.com/ReactiveX/RxAndroid)

*   [Retrofit](https://github.com/square/retrofit)

*   [okhttp](https://github.com/square/okhttp)

*   [Glide](https://github.com/bumptech/glide)

*   [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

*   [EventBus](https://github.com/greenrobot/EventBus)

*   [Arouter](https://github.com/alibaba/ARouter)

*   [ImmersionBar](https://github.com/gyf-dev/ImmersionBar)

*   [Particle](https://github.com/JeasonWong/Particle)

*   [banner](https://github.com/youth5201314/banner)

*   [LoadSir](https://github.com/KingJA/LoadSir)

*   [MagicIndicator](https://github.com/hackware1993/MagicIndicator)

*   [MMKV](https://github.com/Tencent/MMKV)

*   [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)

*   [AgentWeb](https://github.com/Justson/AgentWeb)

*   [aop](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx)

*   [PersistentCookieJar](https://github.com/franmontiel/PersistentCookieJar)


[点我下载或者扫码下载](https://github.com/1170762202/WanAndroid/blob/master/app/release/app-release.apk)


![1600940185(1).jpg](https://upload-images.jianshu.io/upload_images/4906229-85878a0fa91d042e.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


致谢
* [阿里矢量图](https://links.jianshu.com/go?to=https%3A%2F%2Fwww.iconfont.cn%2F)
* [WanAndroid](https://www.wanandroid.com/blog/show/2)
* [Jsonjia](https://github.com/Jsonjia/zjp-wandroid-master)

