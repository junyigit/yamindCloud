package com.yamind.cloud.modules.app.service.impl;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.dao.IdeaManageMapper;
import com.yamind.cloud.modules.app.entity.IdeaDataEntity;
import com.yamind.cloud.modules.app.service.IdeaManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IdeaManageServiceImpl implements IdeaManageService {


    @Autowired
    IdeaManageMapper ideaManageMapper;

    public R saveIdea(IdeaDataEntity ideaDataEntity){

        int result = ideaManageMapper.save(ideaDataEntity);
        return CommonUtils.msgCustom("idea submit success!",result);
    }

    public List<IdeaDataEntity > listFroUserIdea(Long userId){
        List<IdeaDataEntity> ideaDataEntity = ideaManageMapper.listFroUserIdea(userId);
        return ideaDataEntity;
    }
}
