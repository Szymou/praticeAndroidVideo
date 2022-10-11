package top.szymou.myvideo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import top.szymou.myvideo.databinding.FragmentDetailsVideoBinding;
import top.szymou.myvideo.databinding.FragmentVideoBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsVideoFragment extends Fragment {

    StandardGSYVideoPlayer detailPlayer;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;


    private FragmentDetailsVideoBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DetailsVideoFragment() {
        // Required empty public constructor
    }

    public static DetailsVideoFragment newInstance(String param1, String param2) {
        DetailsVideoFragment fragment = new DetailsVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_details_video, container, false);
        binding = FragmentDetailsVideoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toFirstF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DetailsVideoFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });


        detailPlayer = (StandardGSYVideoPlayer) binding.detailPlayer;
        String url = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4";
        //增加封面
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.cat);

        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(getActivity(), detailPlayer);
        //初始化不打开外部的旋转
         orientationUtils.setEnable(true);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle("测试视频")
                ///不需要旋转
                .setNeedOrientationUtils(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        //orientationUtils.setEnable(detailPlayer.isRotateWithSystem());
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                        // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
//                        if (orientationUtils != null) {
//                            //配合下方的onConfigurationChanged
//                            orientationUtils.setEnable(!lock);
//                        }
                    }
                }).build(detailPlayer);

        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(getContext(), true, true);
            }
        });

    }


}