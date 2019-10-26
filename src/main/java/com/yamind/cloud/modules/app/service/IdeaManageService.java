package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.IdeaDataEntity;

import java.util.List;

public interface IdeaManageService {


    //保存- 意见反馈内容
    R saveIdea(IdeaDataEntity ideaDataEntity);


    List<IdeaDataEntity > listFroUserIdea(Long userId);
}
