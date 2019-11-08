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


    /**
     * 保存投诉意见反馈
     * @param ideaDataEntity
     * @return
     */
    public R saveIdea(IdeaDataEntity ideaDataEntity){
        int result = ideaManageMapper.save(ideaDataEntity);
        return CommonUtils.msgCustom("idea submit success!",result);
    }


    /**
     * 获取用户意见反馈列表
     * @param userId
     * @return
     */
    public List<IdeaDataEntity > listFroUserIdea(Long userId){
        List<IdeaDataEntity> ideaDataEntity = ideaManageMapper.listFroUserIdea(userId);
        return ideaDataEntity;
    }
}
