package com.act.web.module.monitor.vo;

/**
 * LabelInfoVo
 * Description:
 * author: Administrator
 * 2017/11/20 0020
 */
public class LabelInfoVo {
    private Integer processStat;
    private Integer cpuStat;
    private Integer memStat;
    private Integer diskStat;

    public Integer getProcessStat() {
        return processStat;
    }

    public void setProcessStat(Integer processStat) {
        this.processStat = processStat;
    }

    public Integer getCpuStat() {
        return cpuStat;
    }

    public void setCpuStat(Integer cpuStat) {
        this.cpuStat = cpuStat;
    }

    public Integer getMemStat() {
        return memStat;
    }

    public void setMemStat(Integer memStat) {
        this.memStat = memStat;
    }

    public Integer getDiskStat() {
        return diskStat;
    }

    public void setDiskStat(Integer diskStat) {
        this.diskStat = diskStat;
    }
}
