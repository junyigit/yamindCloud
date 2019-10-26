package com.yamind.cloud.modules.app.dao;

import com.yamind.cloud.modules.app.entity.IdeaDataEntity;
import com.yamind.cloud.modules.sys.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IdeaManageMapper extends BaseMapper<IdeaDataEntity> {

    List<IdeaDataEntity> listFroUserIdea(Long userId);
}
