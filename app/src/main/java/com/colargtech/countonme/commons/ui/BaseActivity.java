package com.colargtech.countonme.commons.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.colargtech.countonme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juancho.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.BaseActions {

    private final List<String> fragmentsActivityForResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void changeFragment(BaseFragment f) {
        changeFragment(f, false);
    }

    protected void changeFragment(BaseFragment f, boolean cleanStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (cleanStack) {
            clearBackStack();
        }
        ft.setCustomAnimations(
                R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        ft.replace(R.id.activity_main, f, f.getClass().getSimpleName());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (String fragmentName : fragmentsActivityForResult) {
            Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(fragmentName);
            if (fragmentByTag != null) {
                fragmentByTag.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /***************************************************************
     * BaseFragment.BaseActions
     */

    @Override
    public void setScreenTitle(int resTitle) {
        setTitle(resTitle);
    }

    @Override
    public void setScreenTitle(String title) {
        setTitle(title);
    }

    @Override
    public void registerFragmentActivityForResult(BaseFragment fragment) {
        fragmentsActivityForResult.add(fragment.getClass().getSimpleName());
    }
}
