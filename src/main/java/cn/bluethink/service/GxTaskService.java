package cn.bluethink.service;

import cn.bluethink.model.GxTask;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>任务接口实现</p>
 *
 * @author Appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/Appleyk
 * @date created on 下午 2:14 2019-10-25
 */
@Service
public class GxTaskService {
    public List<GxTask> queryByIds(List<Long> ids){
        List<GxTask> tasks = new ArrayList<>();
        for (Long id : ids) {
            tasks.add(new GxTask(id,"我是："+id));
        }
        return tasks;
    }
}
