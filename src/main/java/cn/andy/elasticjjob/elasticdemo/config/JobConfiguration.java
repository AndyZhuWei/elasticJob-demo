package cn.andy.elasticjjob.elasticdemo.config;

import cn.andy.elasticjjob.elasticdemo.quickStart.MyDataflowJobDemo;
import cn.andy.elasticjjob.elasticdemo.quickStart.MyElasticJobListener;
import cn.andy.elasticjjob.elasticdemo.quickStart.MyScriptJobDemo;
import cn.andy.elasticjjob.elasticdemo.quickStart.SimpleJobDemo;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhuwei
 * @Date:2019/7/2 17:47
 * @Description: 配置任务的JobConfiuration，配置job随容器一起启动
 */
@Configuration
public class JobConfiguration {

    @Autowired
    private ZookeeperRegistryCenter regCenter;

    /**
     * 配置任务监听器
     * @return
     */
    @Bean
    public ElasticJobListener elasticJobListener() {
        return new MyElasticJobListener();
    }

    /**
     * 配置简单任务详细信息
     * SimpleJobConfiguration
     */
    private LiteJobConfiguration getSimpleJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters) {

        //定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(
                jobClass.getName(),
                cron,
                shardingTotalCount).shardingItemParameters(shardingItemParameters).build();
        //定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(simpleCoreConfig,jobClass.getCanonicalName());
        //定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
        return simpleJobRootConfig;

     /*   return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(),cron,shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).build(),
                jobClass.getCanonicalName()
        )).overwrite(true).build();*/
    }

    /**
     * 配置简单任务启动器
     * @param simpleJobDemo
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
     */
   // @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJobDemo simpleJobDemo,
                                           @Value("${simplJob.cron}") final String cron,
                                           @Value("${simplJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${simplJob.shardingItemParameters}") final String shardingItemParameters) {
        MyElasticJobListener elasticJobListener = new MyElasticJobListener();
        return new SpringJobScheduler(simpleJobDemo,regCenter,
                getSimpleJobConfiguration(simpleJobDemo.getClass(),cron,shardingTotalCount,shardingItemParameters),elasticJobListener);
    }


    /**
     * 配置流式任务任务详细信息
     * DataFlowConfiguration
     */
    private LiteJobConfiguration getDataFlowConfiguration(final Class<? extends DataflowJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters) {

        //定义作业核心配置
        JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder(
                jobClass.getName(),
                cron,
                shardingTotalCount).shardingItemParameters(shardingItemParameters).build();
        //定义DATAFLOW类型配置
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(dataflowCoreConfig,jobClass.getCanonicalName(),true);
        //定义Lite作业根配置
        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfiguration).overwrite(true).build();
        return dataflowJobRootConfig;
    }

    /**
     * 配置流式任务启动器
     * @param myDataflowJob
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
     */
   // @Bean(initMethod = "init")
    public JobScheduler dataflowJobScheduler(final MyDataflowJobDemo myDataflowJob,
                                           @Value("${dataflowJob.cron}") final String cron,
                                           @Value("${dataflowJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${dataflowJob.shardingItemParameters}") final String shardingItemParameters) {
        MyElasticJobListener elasticJobListener = new MyElasticJobListener();
        return new SpringJobScheduler(myDataflowJob,regCenter,
                getDataFlowConfiguration(myDataflowJob.getClass(),cron,shardingTotalCount,shardingItemParameters),elasticJobListener);
    }


    /**
     * 配置Script任务任务详细信息
     * ScriptConfiguration
     */
    private LiteJobConfiguration getScriptConfiguration(final Class<? extends MyScriptJobDemo> jobClass,
                                                          final String cron,
                                                          final int shardingTotalCount,
                                                          final String shardingItemParameters) {

        //定义作业核心配置
        JobCoreConfiguration scriptCoreConfig = JobCoreConfiguration.newBuilder(
                jobClass.getName(),
                cron,
                shardingTotalCount).build();
        //定义Script类型配置
        ScriptJobConfiguration scriptJobConfiguration = new ScriptJobConfiguration(scriptCoreConfig,"test.sh");
        //定义Lite作业根配置
        LiteJobConfiguration scriptJobRootConfig = LiteJobConfiguration.newBuilder(scriptJobConfiguration).overwrite(true).build();
        return scriptJobRootConfig;
    }

    /**
     * 配置Script任务启动器
     * @param myScriptJobDemo
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
     */
    @Bean(initMethod = "init")
    public JobScheduler scriptJobScheduler(final MyScriptJobDemo myScriptJobDemo,
                                             @Value("${scriptJob.cron}") final String cron,
                                             @Value("${scriptJob.shardingTotalCount}") final int shardingTotalCount,
                                             @Value("${scriptJob.shardingItemParameters}") final String shardingItemParameters) {
        MyElasticJobListener elasticJobListener = new MyElasticJobListener();
        return new SpringJobScheduler(myScriptJobDemo,regCenter,
                getScriptConfiguration(myScriptJobDemo.getClass(),cron,shardingTotalCount,shardingItemParameters),elasticJobListener);
    }



}
