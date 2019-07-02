package cn.andy.elasticjjob.elasticdemo.quickStart;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuwei
 * @Date:2019/7/2 14:17
 * @Description: Elastic-job总共提供了三种类型的定时任务：
 * Simple类型定时任务、Dataflow类型定时任务和Script类型定时任务
 */
@Slf4j
@Component
public class SimpleJobDemo implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info(String.format("------Thread ID: %s, " +
                        "任务总片数: %s," +
                        "当前分片项: %s," +
                        "当前分片项参数: %s," +
                        "当前任务名称: %s"  +
                        "当前任务参数: %s",
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem()),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter());
        switch(shardingContext.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                break;
            case 1:
                // do something by sharding item 1
                break;
            case 2:
                // do something by sharding item 2
                break;
            // case n: ...

        }
    }
}
