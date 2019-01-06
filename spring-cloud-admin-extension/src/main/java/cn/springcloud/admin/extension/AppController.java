package cn.springcloud.admin.extension;

import de.codecentric.boot.admin.server.services.InstanceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自己扩展定制的应用Controller
 */
@RequestMapping("/admin")
@RestController
public class AppController {

    @Autowired
    private InstanceRegistry registry;

    @GetMapping(path = "/applications")
    public String applications() {
        return "sss";
    }
}
