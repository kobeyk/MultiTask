package cn.bluethink.controller;

import cn.bluethink.model.GxTask;
import cn.bluethink.service.GxTaskService;
import cn.bluethink.task.GxTaskQuery;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>任务控制器</p>
 *
 * @author Appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/Appleyk
 * @date created on 下午 2:18 2019-10-25
 */
@CrossOrigin
@RestController
@RequestMapping("/task")
public class GxTaskController {

    @Resource(name = "defaultThreadPool")
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private GxTaskService taskService;

    @GetMapping("/query")
    public ResponseEntity query(){

        // 模拟10000个id
        List<Long> ids = new ArrayList<>();
        for (long i = 0; i <10000 ; i++) {
            ids.add(i);
        }

        // 将ids按多少个进行拆分
        List<List<Long>> parts = Lists.partition(ids, 50);

        // 有多少份，就有多少次请求，有多少次请求就有多少个task
        List<Callable<List<GxTask>>> tasks = new ArrayList<>();
        for (List<Long> part : parts) {
            GxTaskQuery taskQuery = new GxTaskQuery(taskService);
            taskQuery.setIds(part);
            tasks.add(taskQuery);
        }

        // 每一个任务（线程）执行的结果都是一个future对象，这个对象的数据就是List<GxTask>
        List<Future<List<GxTask>>> futures = new ArrayList<>();
        for (Callable<List<GxTask>> task : tasks) {
            // 提交任务 == 注意这时候并没有触发线程执行，就绪状态
            futures.add(executor.submit(task));
        }

        // 最终要查询的任务集
        List<GxTask> allTasks = new ArrayList<>();

        // 遍历执行结果，准备正式执行（get）
        for (Future<List<GxTask>> future : futures) {
            try {
                // 查询任务超过3s的直接弃掉（cancel取消任务）
                List<GxTask> taskList =future.get(3000,TimeUnit.MILLISECONDS );
                if(taskList !=null && taskList.size() != 0){
                    allTasks.addAll(taskList);
                }
            }catch (InterruptedException | ExecutionException | TimeoutException e){
                future.cancel(true);
            }
        }
        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("/queryNot")
    public ResponseEntity queryNot(){
        // 模拟10000个id
        List<Long> ids = new ArrayList<>();
        for (long i = 0; i <10000 ; i++) {
            ids.add(i);
        }
        return ResponseEntity.ok(taskService.queryByIds(ids));
    }

}
