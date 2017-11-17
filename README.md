fir.im Gradle 插件帮助开发者使用一条指令发布应用至 fir.im，简单快速。

下面来看下如何配置、使用 Gradle Plugin。

## 开始配置
* 在项目的 build.gradle（Top-level build.gradle，项目最外层的 build.gradle 文件）中添加，例如:

		buildscript {
  			repositories {
   				jcenter()
   		   //添加fir maven源
    	        maven {
           	  url "http://maven.bughd.com/public"
    	        }
                 ...
  	       }
              dependencies {
                classpath 'com.android.tools.build:gradle:2.2.0'
   			 //添加fir插件依赖
        		classpath 'im.fir:gradle-fir-publisher:1.0.7'
              }
        }

* 在要集成的项目的 build.gradle 中添加配置，如下：

 		apply plugin: 'im.fir.plugin.gradle'// 必填
		fir{

        //必填 上传 fir.im apk 字段，否则无法上传 APP 到 fir.im
    		apiToken '替换为你的 fir.im API_TOKEN'

    	//可选 上传fir.im 中显示的changelog
    		changeLog '替换为你的更新日志'
		}

		//注意
		buildTypes {

		debug {
            	signingConfig signingConfigs.debug
        	}

		//需要添加 release 配置
        	release {
        	   //混淆配置打开时，才会自动上传符号表到 BugHD
            	minifyEnabled true
                proguardFiles getDefaultProguardFile('proguardandroid.txt'),  'proguard-rules.pro'
               //配置正式版签名证书信息，否则上传release版本是unsigned_apk，导致无法安装。
               signingConfig signingConfigs.release
    		}
		}


**以上信息中有部分内容需要替换配置才能生效，需要替换的信息有以下几个：**

- 1.fir.im 的 apiToken （必填）
- 2.fir.im 的 changeLog （可选）
- 3.buildTypes 中的 release 配置仅为示例，可根据项目的实际情况修改相应配置

### 相关提示：

#### 查看 fir.im api_token

**作用：** fir.im 上传 APP 的调用权限

**注意：** 如果需要自动上传应用为必填项

**查看方法：直接点击 [API token](http://fir.im/apps/apitoken) 进行查看.

<img src="http://7xju1s.com1.z0.glb.clouddn.com/image/6/cb/65b727983a7f4e6aa6d7464757d5d.png" width = "100%"  alt="fir.im" align=center />


###开始使用

首先，运行下./gradlew tasks 查看下插件是否已经集成成功，查看输出的Log里是否存在Fir.im tasks,如果看到以下截图，说明插件已经配置成功：

<img src="http://ww1.sinaimg.cn/large/801b780ajw1f86wefgl0ej20rf0dd434.jpg" width = "100%"  alt="fir.im" align=center />

然后根据自己要上传的apk选择对应的gradle命令就可以了。

例如我要上传生成出来的fir渠道的release类型的apk,只需要输入

    ./gradlew publishApkFirRelease

或者我要上传生成出来的fir渠道的debug类型的apk,只需要输入

    ./gradlew publishApkFirDebug

 如果build.gradle里没有配置多渠道信息，默认的上传命令会变成：

    ./gradlew publishApkDebug

   或者

    ./gradlew publishApkRelease



>注意

> - publishApkXXX 任务依附于 gradle 的 assembleTask，**如果要上传Release版本需要配置正式版本的签名。意味着需要在工程的 build.gradle 的 buildTypes 中添加 release 配置签名信息并对 APK 签名，该插件才会正常运行，否则上传的apk是unsign-release，会导致手机无法安装**。


