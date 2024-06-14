package com.keer.yudaovue.framework.common.util.date;

import java.time.LocalDateTime;

/**
 * 时间工具类
 * @author keer
 * @date 2024-06-06
 */
public class DateUtils {

    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    public static boolean isExpired(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time);
    }
}
