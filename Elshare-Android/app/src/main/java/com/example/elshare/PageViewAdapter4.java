package com.example.elshare;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


class PageViewAdapter4 extends FragmentPagerAdapter {


    public PageViewAdapter4(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {

        Fragment fragment = null;

        switch (position)
        {
            case 0:

                return new residential();
              //  break;

            case 1:
                return new CommercialFragment();
               // fragment=new comertial_t();
               // break;
            case 2:
                return new public_g();
               // fragment=new public_g();

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
