package cn.edu.scnu.danmakutv.constant;

import cn.edu.scnu.danmakutv.enums.GenderType;

import java.time.LocalDate;

public interface UserProfilesDefaultConstant {
    public static final String DEFAULT_NICKNAME_PREFIX = "dmk_";

    public static final GenderType DEFAULT_GENDER = GenderType.MALE;

    // TODO : 后续为url
    public static final String DEFAULT_AVATAR = "null";

    public static final String DEFAULT_SIGN = "这个人很懒，什么都没有留下~";

    public static final LocalDate DEFAULT_BIRTHDAY = LocalDate.of(1990, 1, 1);

    public static final int DEFAULT_COIN = 10;
}
