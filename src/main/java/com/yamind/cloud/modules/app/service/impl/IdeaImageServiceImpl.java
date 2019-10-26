package com.yamind.cloud.modules.app.service.impl;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.common.utils.CommonUtils;
import com.yamind.cloud.modules.app.dao.IdeaImageMapper;
import com.yamind.cloud.modules.app.entity.IdeaImageEntity;
import com.yamind.cloud.modules.app.service.IdeaImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IdeaImageServiceImpl implements IdeaImageService {

    @Autowired
    IdeaImageMapper ideaImageMapper;

    public R saveIdeaImage(IdeaImageEntity ideaImageEntity){
        int result = ideaImageMapper.save(ideaImageEntity);
        return CommonUtils.msgCustom("commit image success!",result);

    }

    public List<IdeaImageEntity> listFroIdeaImg(Long ideaId){

        return ideaImageMapper.listFroIdeaImg(ideaId);
    }
}
