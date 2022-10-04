/**

 * FileName: EurekaApplication
 * Author:   benjamen
 * Date:     2022/8/29 14:15
 * Description: euraka
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.asd.backened;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <br>
 * 〈euraka〉
 *
 * @author Xuhao Guo and Yongyan Liu
 * @create 2022/8/29
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
@EnableEurekaServer // Declare that the current project is to complete the registry
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
        log.info("Project started successfully...");
    }
}