package cn.edu.scnu.danmakutv.domain.user;

import cn.edu.scnu.danmakutv.domain.video.Video;
import lombok.Getter;

public class MomentContent {
    public MomentContent() {}
    public MomentContent(Video video) {
        this.video = video;
        type = 0;
    }

    /**
     * 0：视频，1：直播，2：动态专栏。目前只实现了视频
     */
    @Getter
    private int type;

    private Video video;

    /**
     * 如果本动态的内容是视频，就可以调用本函数来获取该视频。
     * @throws Exception 如果本动态的内容不是视频，但仍然调用此函数，就会抛出异常。
     */
    public Video getVideoContent() throws Exception {
        if(type == 0)
            return video;
        else throw new Exception("该动态的内容不是视频");
    }
}
