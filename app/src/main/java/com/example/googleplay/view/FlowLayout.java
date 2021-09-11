package com.example.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FlowLayout extends ViewGroup {

    private int horizontalSpacing=15;
    private int verticalSpacing=15;

    private ArrayList<Line> lineList= new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 用来测量控件与子控件的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //遍历所有的子view,和总宽度进行比较
        //MeasureSpec:表示测量规则,由2个因素构成:size和mode,size:具体的大小,mode:测量模式,就是布局中的属性,如
        //wrap_content,match_parent和具体的dp值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //计算实际用于比较的宽,就是总宽度减去左右padding
        int noPaddingWidth=width-getPaddingLeft()-getPaddingRight();

        Line line=new Line();

        //遍历所有的子view
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);//获取子view
            child.measure(0,0);

            if(line.viewList.size()==0){
                line.addLineView(child);
            }else if(line.lineWidth+child.getMeasuredWidth()+horizontalSpacing>noPaddingWidth){
                lineList.add(line);

                line=new Line();
                line.addLineView(child);

            }else {
                line.addLineView(child);
            }

            if(i==(getChildCount()-1)){
                lineList.add(line);
            }

        }

        int height=getPaddingBottom()+getPaddingTop();
        for(Line l : lineList){
            height+=l.lineHeight;
        }
        height+=(lineList.size()-1)*verticalSpacing;

        //自己计算宽高
        setMeasuredDimension(width,height);
    }

    /**用来摆放控件与子控件的位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft=getPaddingLeft();
        int paddingTop=getPaddingTop();

        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);

            if(i>0){
                paddingTop+=lineList.get(i-1).lineHeight+verticalSpacing;
            }


            for (int j = 0; j < line.viewList.size(); j++) {
                View view = line.viewList.get(j);
                if(j==0){
                    view.layout(paddingLeft,paddingTop,paddingLeft+view.getMeasuredWidth(),paddingTop+view.getMeasuredHeight());
                }else {
                    View preView=line.viewList.get(j-1);
                    int left=preView.getRight()+horizontalSpacing;
                    view.layout(left,preView.getTop(),left+view.getMeasuredWidth(),preView.getBottom());
                }
            }

        }

    }

    //行对象,用来封装每一行的数据
    class Line{
        public ArrayList<View> viewList = new ArrayList<>();//用来存放当前行的View
        public int lineWidth;//用来记录当前行的宽,其实就是所有子view的宽+水平间距
        public int lineHeight;//当前行的高度,其实就是子view的高度

        public void addLineView(View view){
            if(!viewList.contains(view)){
                viewList.add(view); //将view对象添加到集合中

                if (viewList.size()==1){
                    lineWidth=view.getMeasuredWidth();
                }else{
                    lineWidth+=view.getMeasuredWidth()+horizontalSpacing;
                }

                lineHeight=view.getMeasuredHeight();

            }
        }

    }

}
