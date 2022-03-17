模块划分：
1、enter：SDK的入口，提供Fragment和Activity，供外部跳转和引用
2、render：UI渲染模块，外部传入DSL的JSON文件，内部会解析成一个View回调给外部使用
3、runtime：对V8引擎的分装与释放
4、bridge：需要用到的一些增强
5、Log：一些运行中打印的日志处理
6、cache：对DSL，JS文件的缓存，预加载等处理逻辑
    文件夹层级：
    dsl：(存放的页面数据)
    	v1:(版本号)
    		md5:(MD5路径)
    		    fileName:(文件名)
    code:（存放的JS代码）
    	v1:(版本号)
    		md5:(MD5路径)
    		    fileName:(文件名)

    {
        "pageName": "xxxxxxx",    // 页面名称
        "dsl": {    // DSL资源key
          "version": "1.0",     // 版本名称
          "md5": "",    // 资源Md5值
          "url": "xxxxxx.kratos"    // 资源地址,注意资源后缀是特殊的，防止被抓取
        },
        "code": {    // 下发脚本资源Key，怕叫的太明显有问题
          "version": "1.0",    // 版本名称
          "md5": "",    // 资源Md5值
          "url": "xxxxxx.ktcode"    // 资源地址,注意资源后缀是特殊的，防止被抓取
        }
    }

注意：外部不允许直接调用2、3、4、5、6模块，所有与外部的交互都封装在enter模块中，由enter模块统一接收并传递给其它模块