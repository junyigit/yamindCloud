package com.yamind.cloud.modules.sys.service;

import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysOrgEntity;
import com.yamind.cloud.modules.sys.entity.SysPatientEntity;
import com.yamind.cloud.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

public interface SysPatientService {


    Page<SysPatientEntity> listForPatient(Map<String, Object> params);


    R getPatientById(Long id);

    R updatePatient(SysPatientEntity role);

    R saveUser(SysPatientEntity user);

}
