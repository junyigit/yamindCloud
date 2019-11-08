package com.yamind.cloud.modules.app.dao;

import com.yamind.cloud.modules.app.entity.IdeaImageEntity;
import com.yamind.cloud.modules.sys.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IdeaImageMapper extends BaseMapper<IdeaImageEntity> {


    List<IdeaImageEntity> listFroIdeaImg(Long ideaId);
}
