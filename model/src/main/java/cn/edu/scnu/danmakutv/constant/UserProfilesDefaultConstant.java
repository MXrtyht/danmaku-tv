package cn.edu.scnu.danmakutv.constant;

import cn.edu.scnu.danmakutv.enums.GenderType;

import java.time.LocalDate;

public interface UserProfilesDefaultConstant {
    String DEFAULT_NICKNAME_PREFIX = "dmk_";

    GenderType DEFAULT_GENDER = GenderType.MALE;

    // TODO : 后续为url
    String DEFAULT_AVATAR = "null";

    String DEFAULT_SIGN = "这个人很懒，什么都没有留下~";

    LocalDate DEFAULT_BIRTHDAY = LocalDate.of(1990, 1, 1);

    Integer DEFAULT_COIN = 10;
}
