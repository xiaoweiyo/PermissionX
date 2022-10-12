package com.example.xiaoweidev

import androidx.fragment.app.FragmentActivity

/**
 *@author xw
 *@创建者  PermissionX
 *@创建时间 2022/10/13 3:56
 *
 * 将 PermissionX 指定成单例类，是为了让 PermissionX 中的接口能够更加方便地被调用。
 */
object PermissionX {

    private const val TAG = "InvisibleFragment"

    /**
     * 定义了一个request()方法，这个方法接收一个 FragmentActivity 参数、一个可变长度的 permissions 参数列表，以及一个callback回调。
     * 其中，FragmentActivity 是 AppCompatActivity 的父类。
     *
     * 在request()方法中，首先获取 FragmentManager 的实例，然后调用 findFragmentByTag() 方法来判断传入的 Activity 参数中是否已经包含了指定 TAG 的 Fragment，
     * 也就是 InvisibleFragment。如果已经包含则直接使用该 Fragment，否则就创建一个新的 InvisibleFragment 实例，
     * 并将它添加到 Activity 中，同时指定一个 TAG。注意，在添加结束后一定要调用 commitNow() 方法，而不能调用 commit() 方法，
     * 因为 commit() 方法并不会立即执行添加操作，因而无法保证下一行代码执行时 InvisibleFragment 已经被添加到Activity 中了。
     */
    fun request(activity: FragmentActivity, vararg permissions: String, callback: PermissionCallback) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback, *permissions)
    }
}
