package ir.oviessi.singleactivitypattern

import android.app.Activity
import android.R.anim.slide_out_right
import android.R.anim.slide_in_left
import android.app.FragmentContainer
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.TextUtils.replace


/**
 * Created by abbas on 1/22/18.
 */


class NavigationManager(private val mFragmentManager: FragmentManager,private val container: Int) {

    init {
        mFragmentManager.addOnBackStackChangedListener{
            navigationListener?.invoke()
        }
    }

    val isRootFragmentVisible: Boolean
        get() = mFragmentManager.backStackEntryCount <= 1


    /**
     * Listener interface for navigation events.
     */
    var navigationListener:(()->Unit)?=null


    /**
     * Displays the next fragment
     *
     * @param fragment
     */
    fun open(fragment: Fragment) {
        openFragment(fragment, true, false)

    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean, isRoot: Boolean) {
        val fragTransaction = mFragmentManager.beginTransaction()

        if (isRoot)
            fragTransaction.replace(container, fragment, "ROOT")
        else
            fragTransaction.replace(container, fragment)

        fragTransaction.setCustomAnimations(R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left)
        if (addToBackStack)
            fragTransaction.addToBackStack(fragment.toString())
        fragTransaction.commit()
    }

    /**
     * pops every fragment and starts the given fragment as a new one.
     *
     * @param fragment
     */
    fun openAsRoot(fragment: Fragment) {
        popEveryFragment()
        openFragment(fragment, false, true)
    }


    /**
     * Pops all the queued fragments
     */
    private fun popEveryFragment() {
        mFragmentManager.popBackStackImmediate("ROOT", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    fun navigateBack():Boolean {
        if (mFragmentManager.backStackEntryCount==0) {
            return false
        } else {
            mFragmentManager.popBackStackImmediate()
            return true
        }
    }


}