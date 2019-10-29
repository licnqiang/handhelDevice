package cn.piesat.sanitation.common.netchange.event;

/**
 * 网络连接类
 * @author wangyj
 * @time 2018/8/14 14:01
 */

public class TokenLoseEvent {
    public boolean tokenIsLoase; //token是否失效
    public TokenLoseEvent(boolean tokenIsLoase) {
        this.tokenIsLoase=tokenIsLoase;
    }
}
