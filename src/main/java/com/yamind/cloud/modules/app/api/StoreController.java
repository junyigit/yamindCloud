package com.yamind.cloud.modules.app.api;


import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.app.entity.ProductStoreEntity;
import com.yamind.cloud.modules.app.service.ProductStoreService;
import com.yamind.cloud.modules.sys.controller.AbstractController;
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
        R r = new R();
        List<ProductStoreEntity> productStoreEntityList =productStoreService.listForProductStoreList();
        if (productStoreEntityList !=null && productStoreEntityList.size()> 0){
            r.put("code",200);
            r.put("data",productStoreEntityList);
        }else{
            r.put("code",500);
            r.put("msg","商品正在上架中..");
        }
        return r;
    }
}
