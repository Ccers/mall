package com.mall;
import org.mybatis.spring.annotation.MapperScan; // 别忘了导包
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mall.mapper.UserMapper;
import com.mall.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan("com.mall.mapper") // <--- 重点：添加这行，指向你的 Mapper 接口所在包
public class MallApplication {
    @Autowired
    private UserMapper userMapper;


    public static void main(String[] args) {
        //SpringApplication.run(MallApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(MallApplication.class, args);
        // 调用实例方法
        MallApplication app = new MallApplication();
        app.startupCheck(context);
    }
    public void startupCheck(ConfigurableApplicationContext context) {
        Environment env = context.getEnvironment();
        String appName = env.getProperty("spring.application.name", "mall商城系统");
        String port = env.getProperty("server.port", "8080");

        System.out.println("应用 '" + appName + "' 启动成功！");
        System.out.println("访问地址: http://localhost:" + port);
        System.out.println("Swagger地址: http://localhost:" + port + "/swagger-ui.html");
    }

}
