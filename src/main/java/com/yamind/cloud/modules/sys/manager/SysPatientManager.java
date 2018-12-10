package com.yamind.cloud.modules.sys.manager;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.Query;
import com.yamind.cloud.modules.sys.entity.SysOrgEntity;
import com.yamind.cloud.modules.sys.entity.SysPatientEntity;

import java.util.List;

public interface SysPatientManager {



    List<SysPatientEntity> listForPadient(Page<SysPatientEntity> page, Query search);

    int savePatient(SysPatientEntity pat);

    int updatePatient(SysPatientEntity pat);

    SysPatientEntity getPatientById(Long id);
}
