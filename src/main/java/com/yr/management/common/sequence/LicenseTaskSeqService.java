package com.yr.management.common.sequence;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.management.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqService extends IService<LicenseTaskSeq> {
    long getSeq();
}
