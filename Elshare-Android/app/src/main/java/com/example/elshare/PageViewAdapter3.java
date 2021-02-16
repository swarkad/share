package com.example.elshare;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PageViewAdapter3 extends FragmentPagerAdapter
{


    public PageViewAdapter3(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment=new edit_listing_frag();
                break;

            case 1:
                fragment=new vacation_frag();

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
