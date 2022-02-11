# 2020-12-17

## 1. setting.gradle

```
apply from: 'https://gitee.com/angcyo/gradle/raw/master/includeAllModule.gradle'
```

## 2. build.gradle (根目录下)

```
apply from: 'https://gitee.com/angcyo/gradle/raw/master/init.gradle'
```

## 3. build.gradle (app工程)

```
apply from: 'https://gitee.com/angcyo/gradle/raw/master/app.gradle'
```

## 4. build.gradle (lib工程)

```
apply from: 'https://gitee.com/angcyo/gradle/raw/master/libBase.gradle'
```

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

## 脚本说明

七牛云对象存储文件上传脚本.

## 使用方法

如果使用了之前的`init.gradle`脚本, 则此脚本自动依赖.

手动依赖, 请在`root`工程下的`build.gradle`文件中使用

```groovy
apply from: 'https://raw.githubusercontent.com/angcyo/gradle/master/qiniu.gradle'
//or
apply from: 'https://gitee.com/angcyo/gradle/raw/master/qiniu.gradle'
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

如果需要将下载地址转成二维码, 还需要申请: [点击申请APP_ID](https://github.com/MZCretin/RollToolsApi#%E8%A7%A3%E9%94%81%E6%96%B0%E6%96%B9%E5%BC%8F)

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
apply from: 'https://raw.githubusercontent.com/angcyo/gradle/master/pgyer.gradle'
//or
apply from: 'https://gitee.com/angcyo/gradle/raw/master/pgyer.gradle'
```

## 配置项

请在`gradle.properties`文件中配置:

```gradle
pgyer_api_key=xxx
pgyer_user_key=xxx
```

如果需要将下载地址转成二维码, 还需要申请: [点击申请APP_ID](https://github.com/MZCretin/RollToolsApi#%E8%A7%A3%E9%94%81%E6%96%B0%E6%96%B9%E5%BC%8F)

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
apply from: 'https://raw.githubusercontent.com/angcyo/gradle/master/aliyunOss.gradle'
//or
apply from: 'https://gitee.com/angcyo/gradle/raw/master/aliyunOss.gradle'
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

如果需要将下载地址转成二维码, 还需要申请: [点击申请APP_ID](https://github.com/MZCretin/RollToolsApi#%E8%A7%A3%E9%94%81%E6%96%B0%E6%96%B9%E5%BC%8F)

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

