package com.anwei.controller;

import com.anwei.dto.Exposer;
import com.anwei.dto.SeckillExecution;
import com.anwei.dto.SeckillResult;
import com.anwei.entity.Seckill;
import com.anwei.enums.SeckillStateEnum;
import com.anwei.exception.RepeatKillException;
import com.anwei.exception.SeckillCloseException;
import com.anwei.exception.SeckillException;
import com.anwei.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> list = seckillService.seckillList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value="/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(Model model, @PathVariable("seckillId") Long seckillId) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {"application/json;chartset=utf-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            result  = new SeckillResult<>(true, exposer);
        } else {
            result  = new SeckillResult<>(false, SeckillStateEnum.END.getStateInfo());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/kill", method = RequestMethod.POST, produces = "application/json;chartset=utf-8")
    public SeckillResult<SeckillExecution> kill(@PathVariable("seckillId") Long seckillId,
                                       @PathVariable("md5") String md5,
                                       @CookieValue(value = "userPhone", required = false) Long phone) {
        if (phone == null) {
            return new SeckillResult<>(true, "手机号未注册");
        }
        SeckillExecution se;
        SeckillResult<SeckillExecution> result;
        try {
            se = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<>(true, se);
        } catch (SeckillCloseException e) {
            se = new SeckillExecution(seckillId, SeckillStateEnum.END);
        } catch (RepeatKillException e) {
            se = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT);
        } catch (SeckillException e) {
            se = new SeckillExecution(seckillId, SeckillStateEnum.INNER);
        }
        return new SeckillResult<>(false, se);
    }

    @ResponseBody
    @RequestMapping(value = "time/now", method = RequestMethod.GET, produces = "application/json;chartset=utf-8")
    public SeckillResult<Long> time() {
        return new SeckillResult(true, new Date().getTime());
    }

}
