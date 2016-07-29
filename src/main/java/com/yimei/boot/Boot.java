package com.yimei.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hary on 16/3/17.
 */
@Configuration
//@Import({
//        ExtFreeMarkerConfig.class,
//        ExtJacksonConfig.class,
//        ExtRedisConfig.class,
//})
@ComponentScan
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class Boot {


    static Logger logger = LoggerFactory.getLogger(Boot.class);
    @Bean
    MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("100MB");
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }

    // 生产环境, 从默认的路径下搜索jar文件, 将其添加到类路径, 然后加载这些模块
    private static List<Class<?>> getModuleListFromPath() throws ClassNotFoundException {
        List<Class<?>> klasses = new ArrayList<Class<?>>();

        Path base = Paths.get("modules");

        if (!Files.exists(base)) {
            return klasses;
        }
        if (!Files.isDirectory(base)) {
            return klasses;
        }

        File[] moduleJars = base.toFile().listFiles();

        registerServiceJar(moduleJars);

        for (File file : moduleJars) {
            String name = file.toString();

            Pattern pattern = Pattern.compile("^modules\\Saegis-site-(\\w+)-(\\d+).(\\d+).(\\d+)(-SNAPSHOT)?.jar$");
            Matcher matcher = pattern.matcher(file.toString());

            if (!matcher.matches()) {
                continue;
            }
            String moduleName = matcher.group(1);
            String canonicalName = "com.yimei.site." + moduleName + "." + StringUtils.capitalize(moduleName);
            System.out.println("begin load module " + moduleName + "(" + canonicalName + ")");
//            Loader.registerJar(file);
            klasses.add(Class.forName(canonicalName));
        }
        return klasses;
    }

    // 
    private static void registerServiceJar(File[] files) {
        for (File file : files) {
            String name = file.toString();

            Pattern pattern = Pattern.compile("^modules\\Saegis-service(-(\\w+)){1,}-(\\d+).(\\d+).(\\d+)(-SNAPSHOT)?.jar$");
            Matcher matcher = pattern.matcher(file.toString());

            if (!matcher.matches()) {
                continue;
            }

            System.out.println("begin register service jar : " + file.toString());
//            Loader.registerJar(file);
        }
    }

    public static void run(Class<?> mainClass, String[] args) throws ClassNotFoundException, IOException {

        List<Class<?>> klasses = null;
        klasses = getModuleListFromPath();

        SpringApplicationBuilder builder = new SpringApplicationBuilder();

        Class<?>[] sources = new Class<?>[klasses.size() + 1];
        sources[0] = mainClass;
        int i = 1;
        for (Class<?> klass : klasses) {
            sources[i++] = klass;
        }
        Environment env = builder.sources(sources).run(args).getEnvironment();
        logger.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));

    }
}
