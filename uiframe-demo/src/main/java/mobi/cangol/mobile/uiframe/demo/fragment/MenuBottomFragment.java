package mobi.cangol.mobile.uiframe.demo.fragment;

import mobi.cangol.mobile.base.BaseMenuFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.uiframe.demo.ModuleMenuIDS;
import mobi.cangol.mobile.uiframe.demo.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MenuBottomFragment extends BaseMenuFragment{
	
	public TextView textView1;
	public TextView textView2;
	public TextView textView3;
	public TextView textView4;
	public TextView textView5;
	public TextView textView6;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_menu_bottom, container,false);
		return v;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findViews(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews(savedInstanceState);
		initData(savedInstanceState);
	}
	
	@Override
	protected void initData(Bundle savedInstanceState) {
		if(savedInstanceState!=null){
			updateFocus(this.getCurrentModuleId());
		} else {
			this.setCurrentModuleId(this.getCurrentModuleId());
		}
	}

	@Override
	protected void findViews(View v) {
		textView1=(TextView) v.findViewById(R.id.textView1);
		textView2=(TextView) v.findViewById(R.id.textView2);
		textView3=(TextView) v.findViewById(R.id.textView3);
		textView4=(TextView) v.findViewById(R.id.textView4);
		textView5=(TextView) v.findViewById(R.id.textView5);
		textView6=(TextView) v.findViewById(R.id.textView6);
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		textView1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(HomeFragment.class, "HomeFragment",null,ModuleMenuIDS.MODULE_HOME);
			}
		
		});
		textView2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(ListFragment.class, "ListFragment",null,ModuleMenuIDS.MODULE_LIST);
			}
		
		});
		textView3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(SettingFragment.class, "SettingFragment",null,ModuleMenuIDS.MODULE_SETTING);
			}
		
		});
		textView4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(SwitchFragment.class, "SwitchFragment",null,ModuleMenuIDS.MODULE_SWITCH);
			}
		
		});
		textView5.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(TabsFragment.class, "TabsFragment",null,ModuleMenuIDS.MODULE_TABS);
			}
		
		});
		textView6.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(TabPagesFragment.class, "TabPagesFragment",null,ModuleMenuIDS.MODULE_PAGES);
			}
		
		});
	}

	@Override
	protected void onContentChange(int moduleId) {
		if(this.getView()!=null){
			updateFocus(moduleId);
		}
	}

	private void updateFocus(int moduleId) {
		textView1.setSelected(ModuleMenuIDS.MODULE_HOME==moduleId);
		textView2.setSelected(ModuleMenuIDS.MODULE_LIST==moduleId);
		textView3.setSelected(ModuleMenuIDS.MODULE_SETTING==moduleId);
		textView4.setSelected(ModuleMenuIDS.MODULE_SWITCH==moduleId);
		textView5.setSelected(ModuleMenuIDS.MODULE_TABS==moduleId);
		textView6.setSelected(ModuleMenuIDS.MODULE_PAGES==moduleId);
	}
	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onClosed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onOpen() {
		// TODO Auto-generated method stub
		
	}

}
