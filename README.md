# 2020-7-2

阿里镜像推荐:

```groovy
maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' }
```

# includeAllModule.gradle

自动包含当前文件夹中的所有的`module`, 通过是否存在`build.gradle`文件, 识别当前文件夹是否是`module`.

## 使用方式

在`settings.gradle`文件夹中加入:

```groovy
apply from: 'https://raw.githubusercontent.com/angcyo/gradle/master/includeAllModule.gradle'
//备用地址:
apply from: 'https://gitee.com/angcyo/gradle/raw/master/includeAllModule.gradle'
```

可以通过在同级文件夹中新建`ignore`文件, 加入需要忽略`include`的`module名`, 进行忽略操作.


# findAllModule.gradle

枚举所有`module`工程, 放在全局变量`allModule`中;

枚举所有`gradle`文件, 放在全局变量`allGradle`中;

在需要使用`module`的地方, 可以使用 `allModule.xxx`的方式获取:

>`xxx`表示包含`build.gradle`文件的`module`文件夹的名字

**比如:**

```groovy
dependencies {
    implementation project(allModule.library)

    implementation project(allModule.app)
    implementation project(allModule.refresh)
    implementation project(allModule.adapter)
    ...
}
```

在需要使用`gralde`文件的地方, 可以使用`allGradle.xxx`的方式获取:

>`xxx`表示包含`gradle`文件的文件名

**比如:**

```groovy
apply from: allGradle.app

apply from: allGradle.appBase
apply from: allGradle.aar
apply from: allGradle.key
apply from: allGradle.v7a
```

## 使用方式

在根目录的`build.gradle`文件夹中加入:

```groovy
apply from: 'https://raw.githubusercontent.com/angcyo/gradle/master/findAllModule.gradle'
//备用地址:
apply from: 'https://gitee.com/angcyo/gradle/raw/master/findAllModule.gradle'

```

或者 ** (推荐以下使用方式)**

```groovy
apply from: 'https://raw.githubusercontent.com/angcyo/gradle/master/init.gradle'
//备用地址:
apply from: 'https://gitee.com/angcyo/gradle/raw/master/init.gradle'
```

# qiniu.gradle

2020-7-6 七牛云对象存储文件上传脚本.

## 使用方法

如果使用了之前的`init.gradle`脚本, 则此脚本自动依赖.
手动依赖, 请在`root`工程下的`build.gradle`文件中使用`apply`

## 配置项

请在`gradle.properties`文件中配置:

`gradle.properties`文件有2个地方可以配置:


**1:**
win用户: 'C:\Users\用户名\.gradle\gradle.properties"'
mac用户: '/Users/用户名/.gradle/gradle.properties"'

文件不存在, 创建一个即可.

**2:**
工程根目录下的`gradle.properties`

文件不存在, 创建一个即可.

这2个地方的`gradle.properties`文件作用一致.


```gradle
# 必填配置项

# 下载域名前缀, 需要'/'结尾(在七牛云上配置的外链域名)
baseUrl=xxx

# 七牛ak
AccessKey=xxx

# 七牛sk
SecretKey=xxx

# 七牛文件存储Bucket名
Bucket=xxx
```

如果需要将下载地址转成二维码, 还需要配置:

参考:https://github.com/MZCretin/RollToolsApi#%E8%A7%A3%E9%94%81%E6%96%B0%E6%96%B9%E5%BC%8F

```gradle
app_id=xxx
app_secret=xxx
```

以上是固定配置, 当需要上传文件时, 还需要指定文件路径:

```gradle
apkPath=xxx
```

配置完成之后, 在`工程->Tasks->other`中找到`_qiniuUpload`, 双击运行, 即可执行上传.

# pgyer.gradle

2020-7-6 蒲公英文件上传脚本.

请在`gradle.properties`文件中配置:

```gradle
api_key=xxx
user_key=xxx
```

指定文件:

```gradle
apkPath=xxx
```

在`工程->Tasks->other`中找到`_pgyerUpload`, 双击运行, 即可执行上传.

