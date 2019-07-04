package cn.andy.elasticjjob.elasticdemo.quickStart;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhuwei
 * @Date:2019/7/4 10:16
 * @Description: 流式作业
 */
@Slf4j
@Component
public class MyDataflowJobDemo implements DataflowJob<String> {

    private static final ThreadLocal<Integer> LOOP_COUNTER = new ThreadLocal<>();
    //每次获取流处理循环次数
    private static final int LOOP_TIMES = 10;
    //计数器
    private static final AtomicInteger COUNTER = new AtomicInteger(1);


    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        Integer current = LOOP_COUNTER.get();
        if(current == null) {
            current = 1;
        } else {
            current += 1;
        }
        LOOP_COUNTER.set(current);
        log.info(Thread.currentThread()+"----------current----------"+current);
        if(current > LOOP_TIMES) {
            log.info("\n\n\n\n");
            return null;
        } else {
            int shardingItem = shardingContext.getShardingItem();
            List<String> datas = Arrays.asList(getData(shardingItem),getData(shardingItem), getData(shardingItem));
            return datas;
        }
    }

    private String getData(int shardingItem) {
        return shardingItem + "-" + COUNTER.getAndIncrement();
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        log.info(Thread.currentThread() + "--------" +data);
    }
}
