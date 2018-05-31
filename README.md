
# FlowLayout

#### 项目介绍
1、自定义流式布局；

2、自定义标签显示，支持标签点击操作、自动换行（流式布局）、边框和文字内容颜色独立定义、字体大小设置、边框宽度设置、单个标签内边距设置、标签之间横纵向间距设置等。

#### 使用说明

1. 单独设置每个标签 LabelViewGroup.LabelBean的文本、字体颜色、边框颜色；
![自定义标签属性介绍](http://wx3.sinaimg.cn/large/bcc7d265gy1friv4jwhhrj20nq0i2acp.jpg)

图1.自定义标签属性介绍

2. 将标签存放到List中；
3.  调用标签的setColorSeparationData()方法设置标签并显示
        public void setColorSeparationData(List<LabelBean> labelBeanList)


![自定义标签使用说明](http://wx3.sinaimg.cn/large/bcc7d265gy1friv4ka4tfj217q0dwafl.jpg)

图2.自定义标签使用说明

![自自定义标签显示效果](http://wx4.sinaimg.cn/large/bcc7d265gy1friv4jlkdyj209s0ha3yv.jpg)

图3.自自定义标签显示效果
