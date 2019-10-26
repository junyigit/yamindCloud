package com.yamind.cloud.modules.sys.dao;


import com.yamind.cloud.modules.sys.entity.SysPatientEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper
public interface SysPadientMapper extends BaseMapper<SysPatientEntity> {


        List<SysPatientEntity> findAll();

}
