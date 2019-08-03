package test.wez.tabbar;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.wez.utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private int lastIndex;
    List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试更新提交Toast弹出代码
        Toast.makeText(this,"测试Toast",Toast.LENGTH_SHORT).show();
        initBottomNavigation();
        initData();
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new MessageActivity());
        mFragments.add(new AccountActivity());
        mFragments.add(new DiscoverActivity());
        mFragments.add(new ContactsActivity());
        setFragmentPosition(0);
    }

    private void initBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bv_bottomNavigation);
        // 解决当item大于三个时，非平均布局问题
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_message:
                        setFragmentPosition(0);
                        break;
                    case R.id.menu_contacts:
                        setFragmentPosition(1);
                        break;
                    case R.id.menu_discover:
                        setFragmentPosition(2);
                        break;
                    case R.id.menu_me:
                        setFragmentPosition(3);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void setFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.ll_frameLayout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }
}
