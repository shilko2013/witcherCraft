package com.shilko.ru.witcher.controller.api;

import com.shilko.ru.witcher.entity.Thing;
import com.shilko.ru.witcher.service.CraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/craft")
public class CraftController {

    @Autowired
    private CraftService craftService;

    @Transactional
    @RequestMapping(value = "/tree/{strId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getTreeByThingId(@PathVariable("strId") String strId) {
        Thing thing;
        try {
            long id = Long.parseLong(strId);
            thing = craftService.getTreeByThingId(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
        return ResponseEntity.ok(thing);
    }

    @Transactional
    @RequestMapping(value = "/trees/{isAlchemy}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getTree(@PathVariable("isAlchemy") String isAlchemy) {
        List<Thing> things;
        try {
            boolean alchemy = Boolean.parseBoolean(isAlchemy);
            things = craftService.getAllThingsByIsAlchemy(alchemy);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal arguments");
        }
        return ResponseEntity.ok(things);
    }
}
