# 2022-10-22

请在`gradle.properties`文件中配置脚本服务器(请不要以`/`结尾):

## 国内 gitee

```
gradleHost=https://gitee.com/angcyo/gradle/raw
```

## 国内 gitcode

```
gradleHost=https://gitcode.net/angcyo/gradle/-/raw
```

## 国外 github

```
gradleHost=https://raw.githubusercontent.com/angcyo/gradle
gradleHost=https://raw.githubusercontent.com/angcyo/gradle/refs/heads #2025-05-09
```

# 2020-12-17

## 1. setting.gradle

```
apply from: "$gradleHost/master/includeAllModule.gradle"
```

## 2. build.gradle (根目录下)

```
apply from: "$gradleHost/master/init.gradle"
```

## 3. build.gradle (app工程)

```
apply from: "$gradleHost/master/app.gradle"
```

## 4. build.gradle (lib工程)

```
apply from: "$gradleHost/master/libBase.gradle"
```

# 2020-7-2

阿里镜像推荐:

```groovy
maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' }
```

# common.gradle

## 脚本说明

提供了一些`公共的方法`.

```gradle
apply from: "$gradleHost/master/common.gradle"
```

## 脚本使用

在更目录下的`build.gradle`文件用引用, 即可在全部的`build.gradle`中使用公共方法.

# includeAllModule.gradle

自动包含当前文件夹中的所有的`module`, 通过是否存在`build.gradle`文件, 识别当前文件夹是否是`module`.

## 使用方式

在`settings.gradle`文件夹中加入:

```groovy
apply from: "$gradleHost/master/includeAllModule.gradle"
```

可以通过在同级文件夹中新建`ignore`文件, 加入需要忽略`include`的`module名`, 进行忽略操作.

# findAllModule.gradle

枚举所有`module`工程, 放在全局变量`allModule`中;

枚举所有`gradle`文件, 放在全局变量`allGradle`中;

在需要使用`module`的地方, 可以使用 `allModule.xxx`的方式获取:

> `xxx`表示包含`build.gradle`文件的`module`文件夹的名字

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

> `xxx`表示包含`gradle`文件的文件名

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
apply from: "$gradleHost/master/findAllModule.gradle"
```

或者 ** (推荐以下使用方式)**

```groovy
apply from: "$gradleHost/master/init.gradle"
```

# qiniu.gradle

## 脚本说明

七牛云对象存储文件上传脚本.

## 使用方法

如果使用了之前的`init.gradle`脚本, 则此脚本自动依赖.

手动依赖, 请在`root`工程下的`build.gradle`文件中使用

```groovy
apply from: "$gradleHost/master/qiniu.gradle"
```

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
qiniuBaseUrl=xxx

# 七牛ak
qiniu_ak=xxx

# 七牛sk
qiniu_sk=xxx

# 七牛文件存储Bucket名
qiniu_bucket=xxx
```

如果需要将下载地址转成二维码,
还需要申请: [点击申请APP_ID](https://github.com/MZCretin/RollToolsApi#%E8%A7%A3%E9%94%81%E6%96%B0%E6%96%B9%E5%BC%8F)

```gradle
qr_api_app_id=xxx
qr_api_app_secret=xxx
```

## 配置上传的文件

可以通过`qiniuApkPath`指定需要上传的文件,多个文件用`;`分割.

如果未指定`qiniuApkPath`, 则会上传项目`工程根目录/.apk`文件夹下面所有的`apk`文件.

```gradle
qiniuApkPath=xxx
```

配置完成之后, 点击`Sync Now`, 之后在`工程Gradle窗口->Tasks->angcyo`中找到`_qiniuUpload`, 双击运行, 即可执行上传任务.

# pgyer.gradle

## 脚本说明

蒲公英文件上传脚本.

## 使用方法

如果使用了之前的`init.gradle`脚本, 则此脚本自动依赖.

手动依赖, 请在`root`工程下的`build.gradle`文件中使用

```groovy
apply from: "$gradleHost/master/pgyer.gradle"
```

## 配置项

请在`gradle.properties`文件中配置:

```gradle
pgyer_api_key=xxx
pgyer_user_key=xxx
```

如果需要将下载地址转成二维码,
还需要申请: [点击申请APP_ID](https://github.com/MZCretin/RollToolsApi#%E8%A7%A3%E9%94%81%E6%96%B0%E6%96%B9%E5%BC%8F)

```gradle
qr_api_app_id=xxx
qr_api_app_secret=xxx
```

## 配置上传的文件

可以通过`pgyerApkPath`指定需要上传的文件,多个文件用`;`分割.

如果未指定`pgyerApkPath`, 则会上传项目`工程根目录/.apk`文件夹下面所有的`apk`文件.

```gradle
pgyerApkPath=xxx
```

配置完成之后, 点击`Sync Now`, 之后在`工程Gradle窗口->Tasks->angcyo`中找到`_pgyerUpload`, 双击运行, 即可执行上传任务.

# aliyunOss.gradle

## 脚本说明

阿里云文件上传脚本.

## 使用方法

如果使用了之前的`init.gradle`脚本, 则此脚本自动依赖.

手动依赖, 请在`root`工程下的`build.gradle`文件中使用

```groovy
apply from: "$gradleHost/master/aliyunOss.gradle"
```

## 配置项

请在`gradle.properties`文件中配置:

```gradle
aliyunOssAccessKeyId=xxx
aliyunOssAccessKeySecret=xxx
aliyunOssBucketName=xxx

aliyunOssBaseUrl=xxx
aliyunOssEndpoint=xxx

```

如果需要将下载地址转成二维码,
还需要申请: [点击申请APP_ID](https://github.com/MZCretin/RollToolsApi#%E8%A7%A3%E9%94%81%E6%96%B0%E6%96%B9%E5%BC%8F)

```gradle
qr_api_app_id=xxx
qr_api_app_secret=xxx
```

## 配置上传的文件

可以通过`aliyunOssFilePath`指定需要上传的文件,多个文件用`;`分割.

如果未指定`aliyunOssFilePath`, 则会上传项目`工程根目录/.apk`文件夹下面所有的`apk`文件.

```gradle
aliyunOssFilePath=xxx
```

配置完成之后, 点击`Sync Now`, 之后在`工程Gradle窗口->Tasks->angcyo`中找到`_aliyunOssUpload`, 双击运行, 即可执行上传任务.

# local.gradle

## 脚本说明

读取本地`local.properties`文件, 并将配置信息写入`rootProject.ext`

## 脚本使用

```gradle
apply from: "$gradleHost/master/local.gradle"
```

# publish.gradle

## 脚本说明

用于生成`pom`文件,和对应的`aar`文件

## 脚本使用

```gradle
apply from: "$gradleHost/master/publish.gradle"
```

同步之后, 会在对应模块的`Tasks`列表出现`publishing->publish`任务, 双击运行即可构建生成对应文件.

## 脚本配置项

配置项需要放在`gradle.properties`文件中.

```
#aar的group id
libGroupId=xxx

#发布的版本名
libVersionName=master-SNAPSHOT

#win系统aar文件输出路径
libWinRepo=E:/maven

#mac系统aar文件输出路径
libMacRepo=/Users/maven
```
