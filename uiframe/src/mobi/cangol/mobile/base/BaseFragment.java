/**
 * Copyright (c) 2013 Cangol
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mobi.cangol.mobile.base;

import mobi.cangol.mobile.CoreApplication;
import mobi.cangol.mobile.Session;
import mobi.cangol.mobile.actionbar.ActionBar;
import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionMode.Callback;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.AppService;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment{
	protected String TAG = Utils.makeLogTag(BaseFragment.class);
	public static final int RESULT_CANCELED    = 0;
	/** Standard Fragment result: operation succeeded. */
	public static final int RESULT_OK = -1;
	private static final boolean LIFECYCLE=Utils.LIFECYCLE;
	private String title;
	private CustomFragmentManager stack;
	protected CoreApplication app;
	protected FragmentInfo mUpFragment;

	private long starttime;
	
	private int resultCode = RESULT_CANCELED;
	private Bundle resultData;
	
	abstract protected void findViews(View view);

	abstract protected void initViews(Bundle savedInstanceState);
	
	abstract protected void initData(Bundle savedInstanceState);
	
	abstract protected FragmentInfo getNavigtionUpToFragment();

    /**
     * 获取回调码
     * @return
     */
    protected int getResultCode() {
        return resultCode;
    }

    public float getIdletime(){
        return (System.currentTimeMillis()-starttime)/1000.0f;
    }

    /**
     * 获取自定义actionbar
     * @return
     */
	public ActionBar getCustomActionBar() {
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			return abActivity.getCustomActionBar();
		}
	}

    /**
     * 获取标题
     * @return
     */
	public String getTitle() {
		return title;
	}

    /**
     * 设置标题
     * @param title
     */
	public void setTitle(String title) {
		if(this.getParentFragment()!=null)return;
		this.title=title;
		if(getCustomActionBar()!=null)
		getCustomActionBar().setTitle(title);
	}

    /**
     * 设置标题
     * @param title
     */
	public void setTitle(int title) {
		if(this.getParentFragment()!=null)return;
		this.title=getString(title);
		if(getCustomActionBar()!=null)
		getCustomActionBar().setTitle(title);
	}

    /**
     * 返回view
     * @param id
     * @return
     */
	public final View findViewById(int id) {
		return this.getView().findViewById(id);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		TAG = Utils.makeLogTag(this.getClass());
		if(LIFECYCLE)Log.v(TAG,"onAttach");
	}

    /**
     * 设置状态栏颜色
     * @param color
     */
	public void setStatusBarColor(int color){
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			abActivity.setStatusBarTintColor(color);
		}
	}

    /**
     * 设置导航栏颜色
     * @param color
     */
	public void setNavigationBarTintColor(int color){
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			abActivity.setNavigationBarTintColor(color);
		}
	}

    /**
     * 开始progress模式
     */
	public void startProgress(){
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			 abActivity.getCustomActionBar().startProgress();
		}
	}

    /**
     * 停止progress模式
     */
	public void stopProgress(){
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			 abActivity.getCustomActionBar().stopProgress();
		}
	}

    /**
     * 开启自定义actionbar模式
     * @param callback
     * @return
     */
	public ActionMode startCustomActionMode(Callback callback){
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			return abActivity.startCustomActionMode(callback);
		}
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onCreate");
		setHasOptionsMenu(true);
		setMenuVisibility(true);
		app = (CoreApplication) this.getActivity().getApplication();
		if(savedInstanceState==null){
		}else{
			title=savedInstanceState.getString("title");
			if(null!=stack)stack.restoreState(savedInstanceState);
		}
	}

    /**
     * 初始化子fragment管理栈
     * @param containerId
     */
	protected void initFragmentStack(int containerId){
		if(null==stack)
		stack = CustomFragmentManager.forContainer(this.getActivity(), containerId,this.getChildFragmentManager());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(LIFECYCLE){Log.v(TAG, "onCreateView");
			starttime=System.currentTimeMillis();
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onViewCreated");
		if(this.getParentFragment()==null){
			getCustomActionBar().clearActionMenus();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onActivityCreated");
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			this.onMenuActionCreated(abActivity.getCustomActionBar().getActionMenu());
		}
	}
	

	@Override
	public void onPause() {
		super.onPause();
		if(LIFECYCLE)Log.v(TAG, "onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		if(LIFECYCLE)Log.v(TAG, "onResume "+getIdletime()+"s");
	}

	@Override
	public void onStart() {
		super.onStart();
		if(LIFECYCLE)Log.v(TAG, "onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		if(LIFECYCLE)Log.v(TAG, "onStop");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if(LIFECYCLE)Log.v(TAG, "onDetach");
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(LIFECYCLE)Log.v(TAG, "onDestroyView "+getIdletime()+"s");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(LIFECYCLE)Log.v(TAG, "onDestroy");
	}

    /**
     * actionbar menu创建方法
     * @param actionMenu
     * @return
     */
	protected boolean onMenuActionCreated(ActionMenu actionMenu) {
		
		return false;
	}

    /**
     * actionbar menu选择时间相应方法
     * @param action
     * @return
     */
	protected boolean onMenuActionSelected(ActionMenuItem action) {
		
		return false;
	}

    /**
     * 显示toast
     * @param resId
     */
	public void showToast(int resId){
		Toast.makeText(this.getActivity(),resId,Toast.LENGTH_SHORT).show();
	}

    /**
     * 显示toast
     * @param str
     */
	public void showToast(String str){
		Toast.makeText(this.getActivity(),str,Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(LIFECYCLE)Log.v(TAG, "onConfigurationChanged");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(LIFECYCLE)Log.v(TAG, "onActivityResult");
		
	}

    final public boolean onBackPressed() {
		if(LIFECYCLE)Log.v(TAG, "onBackPressed");
		if(null==stack)return false;
		if (stack.size() <= 1) {
			return false;
		} else {
			if (stack.peek().onBackPressed()) {
				return true;
			} else {
				stack.pop();
				return true;
			}
		}
	}

    /**
     * 返回当前fragment是否是单例的，如果是在当前的自定栈里，他只能存在一个
     * @return
     */
	public boolean isSingleton(){
		return false;
	}

    /**
     * 返回是否清除栈,一级fragment建议设置为true,二三级fragment建议设置为false
     * @return
     */
	public boolean isCleanStack(){
		return false;
	}

    /**
     * 返回左上角导航图标的事件处理结果
     * @return
     */
	public boolean onSupportNavigateUp() {
		
		return false;
	}

    /**
     * 将当前fragment弹出栈
     */
    final public void  popBackStack(){
		BaseFragment parent=(BaseFragment) this.getParentFragment();
		if(parent!=null){
			parent.getCustomFragmentManager().pop();
		}else{
			if(getActivity()==null){
				throw new IllegalStateException("getActivity is null");
			}else{
				CustomFragmentActivityDelegate bfActivity = (CustomFragmentActivityDelegate) this.getActivity();
				bfActivity.getCustomFragmentManager().pop();
			}
		}
	}

    /**
     * fragment之间的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
	public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
		if(LIFECYCLE)Log.v(TAG, "onFragmentResult");
    }

    /**
     * 设置回调的状态码
     * @param resultCode
     */
    final public void setResult(int resultCode){
		this.setResult(resultCode, null);
	}

    /**
     * 设置回调的状态码和参数
     * @param resultCode
     * @param resultData
     */
    final public void setResult(int resultCode,Bundle resultData){
			this.resultCode=resultCode;
			this.resultData=resultData;
	}

    /**
     * 通知返回回调
     * @hide
     */
    final public void notifyResult(){
		BaseFragment taget=(BaseFragment) getTargetFragment();
		if(taget!=null){
			taget.onFragmentResult(getTargetRequestCode(),resultCode, resultData);
		}else{
			throw new IllegalStateException("Target Fragment is null");
		}
	}

    /**
     * 获取子fragment管理栈
     * @return
     */
    final public CustomFragmentManager getCustomFragmentManager() {
		return stack;
	}

    /**
     * 替换fragment
     * @param fragmentClass
     */
	final public void replaceFragment(Class<? extends BaseFragment> fragmentClass){
		replaceFragment(fragmentClass, fragmentClass.getSimpleName(), null);
	}

    /**
     * 替换fragment
     * @param fragmentClass
     * @param args
     */
    final public void replaceFragment(Class<? extends BaseFragment> fragmentClass , Bundle args){
		replaceFragment(fragmentClass, fragmentClass.getSimpleName(), args);
	}

    /**
     * 替换fragment
     * @param fragmentClass
     * @param tag
     * @param args
     */
    final public void replaceFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		this.replaceFragment(fragmentClass, tag, args, null);
	}

    /**
     * 替换fragment,并要求请求回调
     * @param fragmentClass
     * @param tag
     * @param args
     * @param args
     */
    final public void replaceFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,int requestCode) {
		if(requestCode!=-1){
			this.replaceFragment(fragmentClass, tag, args,new CustomFragmentTransaction().setTargetFragment(this, requestCode));	
		}else{
			throw new IllegalStateException("requestCode!=-1");
		}
	}

    /**
     * 替换父类级fragment 带自定义动画
     * @param fragmentClass
     * @param tag
     * @param args
     * @param customFragmentTransaction
     */
    final public void replaceFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,CustomFragmentTransaction customFragmentTransaction){
		BaseFragment parent=(BaseFragment) this.getParentFragment();
		CustomFragmentManager stack=null;
		if(parent!=null){
			stack=parent.getCustomFragmentManager();
		}else{
			if(getActivity()==null){
				throw new IllegalStateException("getActivity is null");
			}else{
				CustomFragmentActivityDelegate bfActivity = (CustomFragmentActivityDelegate) this.getActivity();
				stack=bfActivity.getCustomFragmentManager();
			}
		}
		stack.replace(fragmentClass, tag,args,customFragmentTransaction);
		stack.commit();
	}

    /**
     * 替换父类级fragment
     * @param fragmentClass
     * @param tag
     * @param args
     */
    final public void replaceParentFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
        replaceParentFragment(fragmentClass, tag, args, null);
	}

    /**
     * 替换父类级fragment,并要求请求回调
     * @param fragmentClass
     * @param tag
     * @param args
     * @param requestCode
     */
    final public void replaceParentFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,int requestCode) {
		if(requestCode!=-1){
			this.replaceParentFragment(fragmentClass, tag, args,new CustomFragmentTransaction().setTargetFragment(this, requestCode));	
		}else{
			throw new IllegalStateException("requestCode!=-1");
		}
	}

    /**
     * 替换父类级fragment 带自定义动画
     * @param fragmentClass
     * @param tag
     * @param args
     * @param customFragmentTransaction
     */
    final public void replaceParentFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,CustomFragmentTransaction customFragmentTransaction){
		BaseFragment parent=(BaseFragment) this.getParentFragment();
		if(parent!=null){
			parent.replaceFragment(fragmentClass, tag, args, customFragmentTransaction);
		}else{
			throw new IllegalStateException("ParentFragment is null");
		}
	}

    /**
     * 替换子类级fragment
     * @param fragmentClass
     * @param tag
     * @param args
     */
    final public void replaceChildFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		replaceChildFragment(fragmentClass, tag, args,null);
	}

    /**
     * 替换子fragment 带自定义动画
     * @param fragmentClass
     * @param tag
     * @param args
     * @param customFragmentTransaction
     */
    final public void replaceChildFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,CustomFragmentTransaction customFragmentTransaction) {
		if(stack!=null){
			stack.replace(fragmentClass, tag,args,customFragmentTransaction);
			stack.commit();
		}else{
			throw new IllegalStateException("fragment'CustomFragmentManager is null, Pleaser initFragmentStack");
		}
		
	}

    /**
     * 获取AppService
     * @param name
     * @return
     */
	public AppService getAppService(String name) {
		return app.getAppService(name);
	}

    /**
     * 获取Session
     * @return
     */
	public Session getSession() {
		return app.getSession();
	}

    /**
     * 返回当前fragment是否有效
     * @return
     */
	public boolean isEnable() {
		if (null == getActivity() || !isAdded() || isRemoving() || isDetached()) {
			return false;
		}
		return true;
	}
	
}