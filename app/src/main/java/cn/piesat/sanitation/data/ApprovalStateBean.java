package cn.piesat.sanitation.data;

import java.io.Serializable;

/**
 * @author lq
 * @fileName ApprovalState
 * @data on  2020/1/13 14:04
 * @describe TODO
 */
public class ApprovalStateBean implements Serializable{


    /**
     * TT : 2
     * SEQNO : 3
     * NODE_NAME : 集团审批
     * F_SEQNO : 4
     * FLOW_TYPE : 2
     * ROLE_ID : GDM
     * FLOW_ID : 1
     * ROLE_NAME : 集团副总
     * APP_FLOW_DEF_ID : 3
     * R_SEQNO : 4
     * T_SEQNO : 4
     */


    public int TT;    // "TT": 1 审核的节点   2未审核的几点
    public String SEQNO; //步骤
    public String NODE_NAME; //节点名称
    public String F_SEQNO;
    public String FLOW_TYPE;
    public String ROLE_ID;
    public String FLOW_ID;
    public String ROLE_NAME;  //"集团副总", 审核角色名称
    public String APP_FLOW_DEF_ID;
    public String R_SEQNO;
    public String T_SEQNO;
}
