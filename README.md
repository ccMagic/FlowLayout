
# FlowLayout

#### 项目介绍
1、自定义流式布局；

2、自定义标签显示，支持标签点击操作、自动换行（流式布局）、边框和文字内容颜色独立定义、字体大小设置、边框宽度设置、单个标签内边距设置、标签之间横纵向间距设置等。

[源代码:https://github.com/ccMagic/FlowLayout](https://github.com/ccMagic/FlowLayout)

#### 使用说明

1. 单独设置每个标签 LabelViewGroup.LabelBean的文本、字体颜色、边框颜色；

```
   LabelViewGroup.LabelBean labelBean1 = new LabelViewGroup.LabelBean("火影忍者", R.color.orangeRed, R.color.orangeRed);
   LabelViewGroup.LabelBean labelBean2 = new LabelViewGroup.LabelBean("对某飞行员的回忆", R.color.mediumAquamarine, R.color.mediumAquamarine);
   LabelViewGroup.LabelBean labelBean3 = new LabelViewGroup.LabelBean("天空之城", R.color.mediumAquamarine, R.color.mediumVioletred);
   LabelViewGroup.LabelBean labelBean4 = new LabelViewGroup.LabelBean("进击的巨人", R.color.btnBlue, R.color.btnBlue);
   LabelViewGroup.LabelBean labelBean5 = new LabelViewGroup.LabelBean("东京食尸鬼", R.color.mediumPurple, R.color.mediumPurple);
   LabelViewGroup.LabelBean labelBean6 = new LabelViewGroup.LabelBean("刀剑神域", R.color.mediumAquamarine, R.color.orangeRed);

   List<LabelViewGroup.LabelBean> labelBeanList = new ArrayList<>();
   labelBeanList.add(labelBean1);
   labelBeanList.add(labelBean2);
   labelBeanList.add(labelBean3);
   labelBeanList.add(labelBean4);
   labelBeanList.add(labelBean5);
   labelBeanList.add(labelBean6);
```


2. 将标签存放到List中；
3.  调用标签的setColorSeparationData()方法设置标签并显示
        public void setColorSeparationData(List<LabelBean> labelBeanList)

```
  labelViewGroup.setColorSeparationData(labelBeanList);
```
4.流式布局使用,直接当普通的布局使用即可
```
  <io.github.ccmagic.flowlayout.FlowLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:background="@color/white"
        android:padding="20dp"
        ccmagic:flow_view_margin_X="16dp"
        ccmagic:flow_view_margin_Y="13dp">

        <View
            android:layout_width="20dp"
            android:layout_height="10dp"
            android:background="@color/btnBlue" />

        <View
            android:layout_width="100dp"
            android:layout_height="75dp"
            android:background="@color/mediumVioletred" />

        <View
            android:layout_width="6dp"
            android:layout_height="70dp"
            android:background="@color/mediumPurple" />

        <View
            android:layout_width="120dp"
            android:layout_height="10dp"
            android:background="@color/orangeRed" />

        <View
            android:layout_width="20dp"
            android:layout_height="100dp"
            android:background="@color/violet" />

        <View
            android:layout_width="20dp"
            android:layout_height="30dp"
            android:background="@color/mediumAquamarine" />
    </io.github.ccmagic.flowlayout.FlowLayout>
```

![](http://wx2.sinaimg.cn/large/bcc7d265gy1frviylhcfej20aa0ibaag.jpg)
