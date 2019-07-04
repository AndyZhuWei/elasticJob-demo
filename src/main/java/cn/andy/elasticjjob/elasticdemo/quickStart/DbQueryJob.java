package cn.andy.elasticjjob.elasticdemo.quickStart;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zhuwei
 * @Date:2019/7/3 14:35
 * @Description: 定时任务在两台主机上A,B同时运行.A处理id是奇数的数据，B处理Id为偶数的数据
 * 用传统的定时任务例如@Schedule注解能实现么？那当然是可以，直接写死硬编码即可。用Elastic-Job的话，则相当灵活，示例如下
 */
public class DbQueryJob implements SimpleJob {

    @Autowired
    //private XXXDao xxxDao;

    @Override
    public void execute(ShardingContext shardingContext) {
        String shardingParameter = shardingContext.getShardingParameter();
        //mod是对id取余后的结果
       // List<xxx> xxxlists=xxxDao.select("select * from table where mod="+shardingParameter);
     //   xxxlists.forEach(x->{
     //       System.out.println("参数："+shardingContext.getShardingParameter()+"状态"+x.getStatus());
      //  });
    }
}
