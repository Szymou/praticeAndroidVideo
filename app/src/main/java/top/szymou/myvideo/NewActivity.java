package top.szymou.myvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class NewActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> {

    StandardGSYVideoPlayer detailPlayer;
    //    private String url = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4";
    private String url = "http://alvideo.ippzone.com/zyvd/98/90/b753-55fe-11e9-b0d8-00163e0c0248";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Intent intent = this.getIntent();
        Toast.makeText(this, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();

        detailPlayer = (StandardGSYVideoPlayer) findViewById(R.id.detail_player);
        //增加title
//        detailPlayer.getTitleTextView().setVisibility(View.GONE);
//        detailPlayer.getBackButton().setVisibility(View.GONE);
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);

        initVideoBuilderMode();

    }


    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.cat);
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setVideoTitle("这是测试视频啊")
                .setIsTouchWiget(true)
                .setRotateViewAuto(true)
                .setLockLand(false)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }


    /**
     * 是否启动旋转横屏，true表示启动
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }
}