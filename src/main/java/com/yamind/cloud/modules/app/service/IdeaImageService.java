package com.yamind.cloud.modules.app.service;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.IdeaDataEntity;
import com.yamind.cloud.modules.app.entity.IdeaImageEntity;

import java.util.List;

public interface IdeaImageService {



    //保存- 意见反馈内容
    R saveIdeaImage(IdeaImageEntity ideaImageEntity);

    List<IdeaImageEntity> listFroIdeaImg(Long ideaId);
}
