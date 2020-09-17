# StarBar
星星评分
```
implementation 'com.github.SinoHao:StarBar:1.0'
```
##使用方法
```
    <com.hao.starbar.StarBarView
        android:id="@+id/starBar"
        android:layout_width="150dp"
        android:layout_height="50dp"
        app:star_empty="@drawable/ic_star_empty"
        app:star_full="@drawable/ic_star_full"
        app:star_half="@drawable/ic_star_half"
        app:star_padding="5dp"
        app:star_ratable="true" />
```
##设置空星样式
```
app:star_empty="@drawable/ic_star_empty"
```
##设置半星样式
```
app:star_half="@drawable/ic_star_half"
```
##设置满星样式
```
app:star_half="@drawable/ic_star_half"
```
##分数改变监听
```
StarBarView starBar = findViewById(R.id.starBar);
starBar.setOnScoreChangeListener(new StarBarView.OnScoreChangeListener() {
            @Override
            public void onScoreChange(int score) {
                 //满分10分，一颗星2分
            }
        });
```
