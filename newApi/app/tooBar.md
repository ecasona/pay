今天学习下ToolBar(android.support.v7.widget.ToolBar),记录下学习进度
    先写上常用配置信息:
     <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="?attr/colorPrimary"
                app:popupTheme="@style/myAppBarOverlay" />
     再一步一步的了解。

     1.setNavigationIcon
     即设定 up button 的图标，还有一个点击监听setNavigationOnClickListener(new View.OnClickListener() {});
     2.setLogo
     APP 的图标。
     3.setTitle
     主标题。
     4.setSubtitle
     副标题。
     5.setOnMenuItemClickListener
     设定菜单各按鈕的动作;
     也可以在activity的 onOptionsItemSelected(MenuItem item)方法中设定个按钮的动作,但要在onCreateOptionsMenu(Menu menu)
  初始化菜单，菜单的布局则是在res/menu 目录下，示例:
                                      <menu xmlns:android="http://schemas.android.com/apk/res/android"
                                          xmlns:app="http://schemas.android.com/apk/res-auto"
                                          xmlns:tools="http://schemas.android.com/tools"
                                          tools:context="com.ecasona.newapi.MainActivity">
                                          <item
                                              android:id="@+id/action_settings"
                                              android:orderInCategory="100"
                                              android:title="@string/action_settings"
                                              android:icon="@mipmap/ic_launcher"
                                              app:showAsAction="never" />
                                      </menu>

    其中showAsAction则是菜单的显示位置,共有五个值：ifRoom、never、always、withText、collapseActionView可以混合使用。
    ifRoom    会显示在Item中，但是如果已经有4个或者4个以上的Item时会隐藏在溢出列表中。当然个数并不仅仅局限于4个，依据屏幕的宽窄而定
    never    永远不会显示。只会在溢出列表中显示，而且只显示标题，所以在定义item的时候，最好把标题都带上。
    always    无论是否溢出，总会显示。
    withText    withText值示意Action bar要显示文本标题。Action bar会尽可能的显示这个标题，但是，如果图标有效并且受到Action bar空间的限制，文本标题有可能显示不全。
    collapseActionView      声明了这个操作视窗应该被折叠到一个按钮中，当用户选择这个按钮时，这个操作视窗展开。否则，这个操作视窗在默认的情况下是可见的，并且即便在用于不适用的时候，也要占据操作栏的有效空间。一般要配合ifRoom一起使用才会有效果。

  看到菜单,就想设置其背景，分割线等等。
  背景设置:actionOverflowMenuStyle属性可以设置菜单弹出的位置,也可以通过android:popupBackground该属性设置菜单背景。
  分割线:须在android:dropDownListViewStyle中设置android:divider和android:dividerHeight

