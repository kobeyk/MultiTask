package cn.bluethink.model;

/**
 * <p>任务</p>
 *
 * @author yukun24@126.com
 * @version V.1.0.1
 * @company 苏州中科蓝迪
 * @date created on 下午 2:11 2019-10-25
 */
public class GxTask {

    private Long id ;
    private String name;

    public GxTask(){

    }

    public GxTask(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
