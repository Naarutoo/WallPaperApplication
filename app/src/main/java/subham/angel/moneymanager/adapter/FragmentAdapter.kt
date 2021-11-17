package subham.angel.moneymanager.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import subham.angel.moneymanager.fragment.BalanceFragment
import subham.angel.moneymanager.fragment.ExpenseFragment
import subham.angel.moneymanager.fragment.IncomeFragment

class MyAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return IncomeFragment()
            }
            1 -> {
                return ExpenseFragment()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                return BalanceFragment()
            }
            else -> return IncomeFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}