package com.yamind.cloud.modules.app.api;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.ProductStoreEntity;
import com.yamind.cloud.modules.app.service.ProductStoreService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/app/store")
public class StoreController extends AbstractController {

    @Autowired
    ProductStoreService productStoreService;


    /**
     * 获取商城产品list
     * @param request
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    public R storeList(HttpServletRequest request){
        List<ProductStoreEntity> productStoreEntityList =productStoreService.listForProductStoreList();
        // used this
        // if(CollectionUtils.isNotEmpty(productStoreEntityList)) { }
        if (productStoreEntityList !=null && productStoreEntityList.size()> 0){
            return R.customOk(productStoreEntityList);
        }
        return R.error("商品正在上架中..");
    }
}
