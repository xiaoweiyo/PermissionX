package com.example.xiaoweidev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

/**
 *@author xw
 *@创建者  InvisibleFragment
 *@创建时间 2022/10/13 3:31
 */
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    /**
     * 定义了一个 callback 变量作为运行时权限申请结果的回调通知方式，并将它声明成了一种函数类型变量，该函数类型接收Boolean和List<String>这两种类型的参数，并且没有返回值。
     */

    private var callback: PermissionCallback? = null

    /**
     * 定义了一个 requestNow() 方法，该方法接收一个与 callback 变量类型相同的函数类型参数，
     * 同时还使用 vararg 关键字接收了一个可变长度的 permissions 参数列表。在 requestNow() 方法中，
     * 将传递进来的函数类型参数赋值给 callback 变量，然后调用 Fragment 中提供的 requestPermissions() 方法去立即申请运行时权限，
     * 并将 permissions 参数列表传递进去，这样就可以实现由外部调用方自主指定要申请哪些权限的功能了。
     */
    fun requestNow(cb: PermissionCallback, vararg permission: String) {
        callback = cb
        requestPermissions(permission, 1)
    }

    /**
     * 接下来还需要重写onRequestPermissionsResult()方法，并在这里处理运行时权限的申请结果。
     * 使用了一个 deniedList 列表来记录所有被用户拒绝的权限，然后遍历 grantResults 数组，
     * 如果发现某个权限未被用户授权，就将它添加到 deniedList 中。遍历结束后使用一个 allGranted 变量来标识是否所有申请的权限均已被授权，
     * 判断的依据就是 deniedList 列表是否为空。最后使用callback变量对运行时权限的申请结果进行回调。
     *
     * 另外注意，在 InvisibleFragment 中，我们并没有重写 onCreateView() 方法来加载某个布局，
     * 因此它自然就是一个不可见的 Fragment ，待会只需要将它添加到Activity 中即可。
     *
     * (Boolean,List<String>) -> Unit这种函数类型的写法进行优化的：
     * typealias PermissionCallback = (Boolean, List<String>) -> Unit
     * 
     * typealias关键字可以用于给任意类型指定一个别名，比如我们将 (Boolean, List<String>) -> Unit 的别名指定成了 PermissionCallback ，
     * 这样就可以使用 PermissionCallback 来替代之前所有使用 (Boolean, List<String>) -> Unit 的地方，从而让代码变得更加简洁易懂。
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val addGranted = deniedList.isEmpty()
            callback?.let { it(addGranted, deniedList) }
        }
    }
}