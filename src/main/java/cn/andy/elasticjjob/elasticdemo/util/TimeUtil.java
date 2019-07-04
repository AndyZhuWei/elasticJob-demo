package cn.andy.elasticjjob.elasticdemo.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author: zhuwei
 * @Date:2019/7/2 15:58
 * @Description:
 */
public class TimeUtil {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Object mill2Time(long endTime) {
       return LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneId.of("Asia/Shanghai"));
    }
}
