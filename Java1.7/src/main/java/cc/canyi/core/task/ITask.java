package cc.canyi.core.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class ITask implements Runnable{
    @Getter
    @Setter
    private boolean cancel;

    /**
     * 不要重写 请将代码写到业务方法 business 中
     */
    @Override
    public void run() {
        if(cancel) return;
        business();
    }

    /**
     * 业务代码 需要重写
     */
    public void business() {

    }
}
