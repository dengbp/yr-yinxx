package com.yr.management.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yr.management.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqMapper extends BaseMapper<LicenseTaskSeq> {
    long insertSeq(LicenseTaskSeq seq);
}
