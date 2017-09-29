package mobi.cangol.mobile.uiframe.demo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;

import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.navigation.DrawerNavigationFragmentActivity;
import mobi.cangol.mobile.navigation.TabDrawerNavigationFragmentActivity;
import mobi.cangol.mobile.uiframe.demo.R;
import mobi.cangol.mobile.uiframe.demo.Singleton;
import mobi.cangol.mobile.uiframe.demo.fragment.HomeFragment;
import mobi.cangol.mobile.uiframe.demo.fragment.MenuBottomFragment;
import mobi.cangol.mobile.uiframe.demo.fragment.MenuLeftFragment;
import mobi.cangol.mobile.uiframe.demo.fragment.SettingFragment;

@SuppressLint("ResourceAsColor")
public class DrawerActivity extends DrawerNavigationFragmentActivity {
	private static long back_pressed;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setStatusBarTintColor(R.color.red);
		this.setNavigationBarTintColor(R.color.black);
		setContentView(R.layout.activity_main);
		this.setFloatActionBarEnabled(true);
		this.getCustomActionBar().setBackgroundResource(R.color.red);
		if (savedInstanceState == null) {
			this.setMenuFragment(MenuLeftFragment.class,null);
			this.setContentFragment(HomeFragment.class, "HomeFragment", null,MenuBottomFragment.MODULE_HOME);
		}
		findViews();
		initViews(savedInstanceState);
		initData(savedInstanceState);
		this.setFloatActionBarEnabled(false);
        //this.initFragmentStack(R.id.content_frame);
        //if(savedInstanceState==null)this.replaceFragment(HomeFragment.class, "Home", null);
		Singleton.getInstance().setOnTestListener(new Singleton.OnTestListener() {
			@Override
			public void onTest() {
				setContentFragment(SettingFragment.class, "SettingFragment", null,MenuBottomFragment.MODULE_SETTING);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v("onStart " + System.currentTimeMillis());

	}

	@Override
	public void findViews() {
	}
	@Override
	public void initViews(Bundle savedInstanceState) {
		
	}
	@Override
	public void initData(Bundle savedInstanceState) {
		
	}

	@Override
	public void onBack() {
		if(back_pressed+2000>System.currentTimeMillis()){
			super.onBack();
			app.exit();
		}else{
			back_pressed=System.currentTimeMillis();
            showToast("Please on back");
		}
	}

	public int getContentFrameId() {
		return R.id.content_frame;
	}
}
