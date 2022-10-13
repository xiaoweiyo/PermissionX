## PermissionX ##

PermissionX是一个用于简化Android运行时权限用法的开源库。

## 快速设置 ## 
添加如下配置将PermissionX引入到你的项目当中
    
	dependencies {
		...
    	implementation 'io.github.xiaoweiyo:glance:1.0.0'
	}

这样就可以使用了

##基本用法## 

使用PermissionX请求Android运行时权限非常简单。
例如。如果要申请CALL_PHONE,READ_CONTACTS权限，请先在**AndroidManifest.xml**中声明它们。
	
	<manifest package="com.example.permissionx"
    	xmlns:android="http://schemas.android.com/apk/res/android">

    	<uses-permission android:name="android.permission.CALL_PHONE" />
    	<uses-permission android:name="android.permission.READ_CONTACTS" />

	</manifest>


**然后就可以使用如下语法结构来申请运行时权限了：**
	
	  PermissionX.request(this, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS) { allGranted, deniedList ->
                if (allGranted) {
                    call()
                    Toast.makeText(this, "All permission are granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "You denied $deniedList", Toast.LENGTH_SHORT).show()
                }

将 FragmentActivity 或 Fragment 的任意实例传递给init方法，并在 permissions 方法中指定要请求的权限，然后调用request方法进行实际请求。

请求结果将在请求 lambda 中回调。allGranted表示您请求的所有权限是否由用户授予，可能是 true 或 false。grantList持有所有授予的权限，deniedList持有所有拒绝的权限。

现在，您可以在请求 lambda 中编写自己的逻辑来处理应用程序的特定情况。