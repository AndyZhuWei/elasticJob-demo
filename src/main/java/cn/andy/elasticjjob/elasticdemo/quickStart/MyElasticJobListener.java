package cn.andy.elasticjjob.elasticdemo.quickStart;

import cn.andy.elasticjjob.elasticdemo.util.TimeUtil;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhuwei
 * @Date:2019/7/2 15:54
 * @Description: 定义任务监听器，统计每次任务执行的时间
 */
@Slf4j
public class MyElasticJobListener implements ElasticJobListener {

    private long beginTime = 0;

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();
        log.info("===>{} JOB BEGIN TIME:{}<===", shardingContexts.getJobName(), TimeUtil.mill2Time(beginTime));

    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        log.info("===>{} JOB END TIME: {},TOTAL CAST: {} <===",
                shardingContexts.getJobName(), TimeUtil.mill2Time(endTime), endTime - beginTime);
    }
}
