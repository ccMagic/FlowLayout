package io.github.ccmagic.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LabelViewGroup labelViewGroup = findViewById(R.id.label_vg);

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
        labelViewGroup.setColorSeparationData(labelBeanList);
    }
}
