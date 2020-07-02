# 2020-7-2

阿里镜像推荐:

```groovy
maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' }
```

# includeAllModule

自动包含当前文件夹中的所有的`module`, 通过是否存在`build.gradle`文件, 识别当前文件夹是否是`module`.

## 使用方式

在`settings.gradle`文件夹中加入:

```groovy
apply from: 'https://raw.githubusercontent.com/angcyo/gradle/master/includeAllModule.gradle'
//备用地址:
apply from: 'https://gitee.com/angcyo/gradle/raw/master/includeAllModule.gradle'
```

可以通过在同级文件夹中新建`ignore`文件, 加入需要忽略`include`的`module名`, 进行忽略操作.


# findAllModule

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

或者

```groovy
apply from: 'https://raw.githubusercontent.com/angcyo/gradle/master/init.gradle'
//备用地址:
apply from: 'https://gitee.com/angcyo/gradle/raw/master/init.gradle'
```