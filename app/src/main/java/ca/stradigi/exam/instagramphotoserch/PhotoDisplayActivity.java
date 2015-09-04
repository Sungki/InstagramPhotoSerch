package ca.stradigi.exam.instagramphotoserch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;


public class PhotoDisplayActivity extends FragmentActivity {
    private ArrayList<InstagramData> instagramData;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);

        instagramData = getIntent().getParcelableArrayListExtra("dataInfo");
        int cur = getIntent().getExtras().getInt("current");

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(cur);
    }


    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.create(instagramData.size(), position, instagramData.get(position).getImgStandardUrl());
        }

        @Override
        public int getCount() {
              return instagramData.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onBackPressed() {
        callFinish();
    }

    public void onPrev(View v) {
        int cur = mViewPager.getCurrentItem();
        if(cur > 0)
            mViewPager.setCurrentItem(cur-1,true);
        else
            mViewPager.setCurrentItem(instagramData.size()-1,true);
    }

    public void onNext(View v) {
        int cur = mViewPager.getCurrentItem();
        if(cur < instagramData.size()-1)
            mViewPager.setCurrentItem(cur+1,true);
        else
            mViewPager.setCurrentItem(0, true);
    }

    public void onDelete(View v) {
        int cur = mViewPager.getCurrentItem();
        instagramData.remove(cur);

        if(instagramData.size()<=0) {
            callFinish();
        }
        else {
            mPagerAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(cur, true);
        }
    }

    private void callFinish() {
        Intent i = new Intent();
        i.putParcelableArrayListExtra("dataInfo", instagramData);

        int cur = mViewPager.getCurrentItem();
        i.putExtra("current", cur);

        setResult(RESULT_OK, i);
        finish();
    }
}
