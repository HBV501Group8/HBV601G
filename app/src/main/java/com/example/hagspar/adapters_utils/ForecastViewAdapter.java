package com.example.hagspar.adapters_utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.hagspar.ViewForecastFragment;
import com.example.hagspar.forecast.ForecastManager;

import java.util.HashMap;
import java.util.Map;

public class ForecastViewAdapter extends FragmentPagerAdapter {

    private final int mTabCount;
    private Fragment mFragment = null;
    private ForecastManager forecastManager;

    private static final Map<String, String> mSeriesNameLookup = new HashMap<String, String>();
    {
        mSeriesNameLookup.put("Mannfjoldi_is","Fjöldi íslenskra ríkisborgara");
        mSeriesNameLookup.put("Mannfjoldi_erl","Fjöldi erlendra ríkisborgara");
        mSeriesNameLookup.put("Atvinnul_rvk","Atvinnuleysi í Reykjavík");
        mSeriesNameLookup.put("Atvinnul_land","Atvinnuleysi");
        mSeriesNameLookup.put("Einkaneysla","Einkaneysla");
        mSeriesNameLookup.put("Samneysla","Samneysla");
        mSeriesNameLookup.put("Fjarmunamyndun","Fjármunamyndun");
        mSeriesNameLookup.put("Vara_ut","Útflutningur vara");
        mSeriesNameLookup.put("Vara_inn","Innflutningur vara");
        mSeriesNameLookup.put("Thjonusta_ut","Útflutningur þjónustu");
        mSeriesNameLookup.put("Thjonusta_inn","Innflutningur þjónustu");
        mSeriesNameLookup.put("VLF","Verg landsframleiðsla");
    }

    public ForecastViewAdapter(@NonNull FragmentManager fm, int tabCount, Context context)
    {
        super(fm);
        this.mTabCount = tabCount;
        forecastManager = ForecastManager.getInstance(context);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        for (int i = 0; i < mTabCount; i++) {
            if (i == position) {
                mFragment = ViewForecastFragment.newInstance(position);
                break;
            }
        }
        return mFragment;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mSeriesNameLookup.get(forecastManager.getCurrentForecast().getForecastResults().get(position).getName());
    }
}

