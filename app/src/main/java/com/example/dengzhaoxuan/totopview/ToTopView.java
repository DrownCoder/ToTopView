package com.example.dengzhaoxuan.totopview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by dengzhaoxuan on 2017/3/17.
 */

public class ToTopView extends RelativeLayout {
    private int mTextSize;
    /* The default content text size*/
    private static final int DEFAULT_CONTENT_TEXT_SIZE = 30;
    private int mLineColor;
    private int mBacimg;

    private ImageView mIvTop;
    private LinearLayout mLlProgress;
    private TextView mTvProgress;
    private TextView mTvMax;

    private int mScrollY = 0;

    public ToTopView(Context context) {
        this(context, null);
    }

    public ToTopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context, attrs);
    }

    private void Init(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ToTopView);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ToTopView_topTextSize,
                DEFAULT_CONTENT_TEXT_SIZE);
        mLineColor = typedArray.getColor(R.styleable.ToTopView_lineColor,
                getResources().getColor(R.color.colorAccent));
        mBacimg = typedArray.getResourceId(R.styleable.ToTopView_btnImg, R.mipmap.btn_bring_to_top);
        typedArray.recycle();
        InitView(context);
    }

    private void InitView(Context context) {
        //图片回到顶部
        mIvTop = new ImageView(context);
        mIvTop.setImageResource(mBacimg);
        LayoutParams params = new LayoutParams(
                DensityUtils.dp2px(context, 48), DensityUtils.dp2px(context, 48));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mIvTop.setLayoutParams(params);
        //滑动过程中显示进度
        mLlProgress = new LinearLayout(context);
        mLlProgress.setOrientation(LinearLayout.VERTICAL);
        mLlProgress.setBackgroundResource(R.drawable.bg_totop_progress);
        LayoutParams llparams = new LayoutParams(
                DensityUtils.dp2px(context, 48), LayoutParams.MATCH_PARENT);
        mLlProgress.setLayoutParams(llparams);
        mLlProgress.setGravity(Gravity.CENTER);
        //进度
        mTvProgress = new TextView(context);
        mTvProgress.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTvProgress.setGravity(Gravity.CENTER);
        LayoutParams tvparams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mTvProgress.setLayoutParams(tvparams);
        //横线
        View line = new View(context);
        line.setBackgroundColor(mLineColor);
        LayoutParams lineParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 1));
        line.setLayoutParams(lineParams);
        line.setPadding(DensityUtils.dp2px(context, 5), 0, DensityUtils.dp2px(context, 5), 0);
        //总量
        mTvMax = new TextView(context);
        mTvMax.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTvMax.setGravity(Gravity.CENTER);
        mTvMax.setLayoutParams(tvparams);
        mLlProgress.addView(mTvProgress);
        mLlProgress.addView(line);
        mLlProgress.addView(mTvMax);

        addView(mIvTop);
        addView(mLlProgress);
    }

    /**
     * 滑动过程中
     * 显示进度，隐藏Img
     */
    public void onScrolling() {
        mIvTop.setVisibility(GONE);
        mLlProgress.setVisibility(VISIBLE);
    }

    /**
     * 滑动停止
     */
    public void onShowState() {
        mIvTop.setVisibility(VISIBLE);
        mLlProgress.setVisibility(GONE);
    }

    /**
     * 设置进度
     *
     * @param progress
     * @param max
     */
    public void setProgress(int progress, int max) {
        mTvProgress.setText(String.valueOf(progress));
        mTvMax.setText(String.valueOf(max));
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mScrollY >= getResources().getDisplayMetrics().heightPixels) {
                    setVisibility(View.VISIBLE);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //停止滑动
                        onShowState();
                    }
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        //滑动
                        onScrolling();
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mScrollY += dy;

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int count = manager.getItemCount();
                int lastItemPosition = manager.findLastVisibleItemPosition();
                setProgress(lastItemPosition, count);

                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
