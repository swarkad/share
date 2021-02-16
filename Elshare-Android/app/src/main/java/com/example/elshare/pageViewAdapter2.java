package com.example.elshare;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

class PageViewAdapter2 extends FragmentPagerAdapter
{
    private Map<Integer,String> mFragmentTags;
    private FragmentManager mfragmentManager;
    private Context mcontext;

//    public PageViewAdapter2(@NonNull FragmentManager fm ) {
//        super(fm);
//    }
    public PageViewAdapter2(@NonNull FragmentManager fm,Context context) {
        super(fm);
        mfragmentManager=fm;
        mFragmentTags= new HashMap<Integer,String>();
        mcontext=context;
    }

    @Override
//    public Fragment getItem(int position)
//    {
//        Fragment fragment = null;
//
//        switch (position)
//        {
//            case 0:
//                fragment=new account_evdriver_frag();
//                break;
//
//            case 1:
//                fragment=new account_history_frag();
//
//        }
//        return fragment;
//    }

    public Fragment getItem(int arg0)
    {

        switch (arg0)
        {
            case 0:
                new account_evdriver_frag().updateUI();
                return  new account_evdriver_frag();

            case 1:
                return new AccountHistoryFragment();
            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }

    public Object instantiateItem (ViewGroup container,int position)
    {
        Object obj=super.instantiateItem(container,position);
        if(obj instanceof  Fragment)
        {
            Fragment f=(Fragment) obj;
            String tag=f.getTag();
            mFragmentTags.put(position,tag);
        }
        return obj;
    }
    public  Fragment getFragment(int position)
    {
        String tag=mFragmentTags.get(position);
        if(tag==null)
            return null;
        return mfragmentManager.findFragmentByTag(tag);
    }

}


