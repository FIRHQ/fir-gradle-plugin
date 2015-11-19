#使用Gradle Plugin发布应用到fir.im

***

###配置 
* 项目的 build.gradle（Top-level build.gradle，项目最外层的 build.gradle 文件）中添加，例如:
		
		buildscript {
  			repositories {
   				jcenter() 
   				  			 
   				//添加maven源  	
    			maven { 
           			url "http://maven.bughd.com/public"
    			}
  			}
  			dependencies {
        		classpath 'com.android.tools.build:gradle:1.3.0'
        		
        		//添加fir插件依赖
        		classpath 'im.fir:gradle-fir-publisher:1.0.0'
   			}
  		} 
 * 在要集成的项目中的 build.gradle 中添加配置，如下：
 		
 		.....
 		
 		
 		apply plugin: 'im.fir.plugin.gradle'
 		
 		//必填 
		fir{
		 	//必填 上传fir.im apk字段，否则无法上传APP到fir.im
    		apiToken 'CONFIG YOUR FIR.IM API_TOKEN'
    		// 可选 上传fir.im 中显示的changelog
    		changeLog 'from fir.im gradle plugin'
		}
		
		//可选 将每次打包 混淆代码生成的符号表自动上传到bughd.com
		bughd{
    		projectId 'CONFIG YOUR BUGHD.COM PROJECT_ID'
    		apiToken 'CONFIG YOUR BUGHD.COM API_TOKEN'
		}
		
		
		
		//注意
		buildTypes {
		 
		 	debug {
            	signingConfig signingConfigs.debug
        	}
        	
			//需要添加release配置
        	release {
        	    //需要打开混淆配置bughd中的项目中才能上传符号表
            	minifyEnabled true
            	proguardFiles getDefaultProguardFile('proguardandroid.txt'), 'proguard-rules.pro'
            	//需要使用正式证书签名，才能发布到fir.im
            	signingConfig signingConfigs.release
    		}
		}

		
		....
		
		


##### *提示：*

###### 查看fir.im api_token
***

请访问 [fir.im](http://fir.im/apps)，登录后，点击头像 选择 **API token** 进行查看
![ScreenShot](http://ww1.sinaimg.cn/large/6f260d67jw1exvzwzkfkrj20ye0hwmza.jpg)

###### 查看BugHD api_token
***

 **作用：** BugHD上传 ** *mapping.txt/dSYM* ** 文件API调用权限
 **注意：** 如果不需要上传混淆表不需要填写
 查看方法：请访问 [BugHD API token](http://bughd.com/account)，登录后，进行查看
 	
 ![ScreenShot](http://ww4.sinaimg.cn/large/6f260d67jw1exvzyp9z2xj20nl0cwjs8.jpg)
 

###### 查看BugHD project ID
***
  
**作用：** 判断上传到具体到哪个bughd.com项
 
**注意：** 如果填写过BugHD token后，该选项为必填

**查看方法：** 请访问 [BugHD Projects](http://bughd.com/projects)，登录后，找到你要上传符号表的项目后，进入该项目， 并选择 ** *项目设置* ** 选项卡，进行查看
![ScreenShot](http://ww1.sinaimg.cn/large/6f260d67jw1exvzzdv0dcj20n50ccjs5.jpg)


###使用

配置成功后，只需要如下的一条命令就可以发布应用到fir.im：

	gradle publishApkRelease 

** 注意 ** 

* publishApkRelease任务依附于gradle的assembleTask，也就意味着需要在工程的build.gradle中的buildTypes添加release配置并对APK签名，该插件才会正常运行。
*  需要在工程的build.gradle中的buildTypes添加release中打开混淆配置 bughd中的项目中才能上传符号表。
