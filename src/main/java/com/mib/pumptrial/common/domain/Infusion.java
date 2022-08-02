package com.mib.pumptrial.common.domain;

import java.math.BigDecimal;

/**
 * @Description 胰岛素输注实体类
 * @Author ming
 * @Date 2022/7/12 14:08
 **/
public class Infusion extends MessageDTO {


    /**
     * 输注剂量 单位 U
     */
    private BigDecimal dose;
    /**
     * 开始很快吃饭TT(临时记录)
     */
    private Boolean fastEat;
    /**
     * 不打大剂量，只记录
     */
    private Boolean onlyRecord;

    public Infusion() {
    }

    public Infusion(BigDecimal dose, Boolean fastEat, Boolean onlyRecord) {
        this.dose = dose;
        this.fastEat = fastEat;
        this.onlyRecord = onlyRecord;
    }

    public BigDecimal getDose() {
        return dose;
    }

    public void setDose(BigDecimal dose) {
        this.dose = dose;
    }

    public Boolean getFastEat() {
        return fastEat;
    }

    public void setFastEat(Boolean fastEat) {
        this.fastEat = fastEat;
    }

    public Boolean getOnlyRecord() {
        return onlyRecord;
    }

    public void setOnlyRecord(Boolean onlyRecord) {
        this.onlyRecord = onlyRecord;
    }

    @Override
    public String toString() {
        return "Infusion{" +
                "dose=" + dose +
                ", fastEat=" + fastEat +
                ", onlyRecord=" + onlyRecord +
                '}';
    }
}
