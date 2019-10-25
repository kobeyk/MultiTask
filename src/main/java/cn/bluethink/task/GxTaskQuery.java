package cn.bluethink.task;

import cn.bluethink.model.GxTask;
import cn.bluethink.service.GxTaskService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>任务查询线程</p>
 *
 * @author yukun24@126.com
 * @version V.1.0.1
 * @company 苏州中科蓝迪
 * @date created on 下午 2:09 2019-10-25
 */
public class GxTaskQuery implements Callable<List<GxTask>> {

    private List<Long> ids ;

    private GxTaskService taskService;

    public GxTaskQuery(GxTaskService taskService) {
        ids = new ArrayList<>();
        this.taskService = taskService;
    }

    @Override
    public List<GxTask> call() throws Exception {
        return taskService.queryByIds(this.ids);
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
