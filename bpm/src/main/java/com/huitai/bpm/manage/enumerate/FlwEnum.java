package com.huitai.bpm.manage.enumerate;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: FlwEnum <br>
 * @date 2020-12-16 17:56 <br>
 */
public enum FlwEnum implements FlwBaseEnum {

    BUTTON_SUBMIT("提 交", "primary"),
    BUTTON_TRANSFER("转 办", "warning"),
    BUTTON_GO_BACK("退 回", "danger"),
    BUTTON_TERMINATION("终 止", "danger");

    private String label;
    private String type;

    FlwEnum(String label, String type) {
        this.label = label;
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
