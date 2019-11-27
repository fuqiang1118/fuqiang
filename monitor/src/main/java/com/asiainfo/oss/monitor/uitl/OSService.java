package com.asiainfo.oss.monitor.uitl;


import ch.ethz.ssh2.Connection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.apache.commons.io.IOUtils;
import org.mapstruct.BeforeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Statement;
import java.util.*;

/**
 * @program: monitor
 * @description: linux主机监控
 * @author: fuqiang
 * @date: 2019-11-14 17:12
 **/
@Component("OSUtils")
public class OSService {

    private static final Logger log = LoggerFactory.getLogger(OSService.class);

    /**
     * 功能：获取Linux系统cpu使用率
     */
    public float cpuUsage(Connection conn) {
        try {
            Map<?, ?> map1 = cpuinfo(conn);
            Thread.sleep(5 * 1000);
            Map<?, ?> map2 = cpuinfo(conn);

            long user1 = Long.parseLong(map1.get("user").toString());
            long nice1 = Long.parseLong(map1.get("nice").toString());
            long system1 = Long.parseLong(map1.get("system").toString());
            long idle1 = Long.parseLong(map1.get("idle").toString());

            long user2 = Long.parseLong(map2.get("user").toString());
            long nice2 = Long.parseLong(map2.get("nice").toString());
            long system2 = Long.parseLong(map2.get("system").toString());
            long idle2 = Long.parseLong(map2.get("idle").toString());

            long total1 = user1 + system1 + nice1;
            long total2 = user2 + system2 + nice2;
            float total = total2 - total1;

            long totalIdle1 = user1 + nice1 + system1 + idle1;
            long totalIdle2 = user2 + nice2 + system2 + idle2;
            float totalidle = totalIdle2 - totalIdle1;

            float cpusage = (total / totalidle) * 100;
            System.out.println("cpu使用率:" + cpusage + "%");
            return cpusage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 功能：CPU使用信息
     */
    public Map<?, ?> cpuinfo(Connection conn) {

        String result = RemoteCommandUtil.execute(conn, "cat /proc/stat");
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            inputs = new InputStreamReader(IOUtils.toInputStream(result));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 功能：内存使用率
     */
    public long memoryUsage(Connection conn) {
        Map<String, Object> map = new HashMap<String, Object>();
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        try {
            String result = RemoteCommandUtil.execute(conn, "cat /proc/meminfo");
            //inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
            inputs = new InputStreamReader(IOUtils.toInputStream(result));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null)
                    break;
                int beginIndex = 0;
                int endIndex = line.indexOf(":");
                if (endIndex != -1) {
                    String key = line.substring(beginIndex, endIndex);
                    beginIndex = endIndex + 1;
                    endIndex = line.length();
                    String memory = line.substring(beginIndex, endIndex);
                    String value = memory.replace("kB", "").trim();
                    map.put(key, value);
                }
            }

            long memTotal = Long.parseLong(map.get("MemTotal").toString());
            System.out.println("内存总量" + memTotal + "KB");
            long memFree = Long.parseLong(map.get("MemFree").toString());
            System.out.println("剩余内存" + memFree + "KB");
            long memused = memTotal - memFree;
            System.out.println("已用内存" + memused + "KB");
            long buffers = Long.parseLong(map.get("Buffers").toString());
            long cached = Long.parseLong(map.get("Cached").toString());

            double usage = (double) (memused - buffers - cached) / memTotal * 100;
            System.out.println("内存使用率" + usage + "%");

            return memFree;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }


    /**
     * 主入口
     *
     * @param args
     */
    public static void main(String[] args) {
        OSService osUtils = new OSService();
        Connection conn = RemoteCommandUtil.login("","","");
        //1. 创建计时器类
        Timer timer = new Timer();
        //2. 创建任务类
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //cup使用率
                float cpuUsage = osUtils.cpuUsage(conn);
                System.out.println(cpuUsage);
//        if(cpuUsage > 10.0 ){
//            SendMail.sendMail("xxxxx@qq.com", "服务器cpu使用率过高，请注意查看", "服务器提醒");
//        }
                //内存使用情况
                long memoryUsage = osUtils.memoryUsage(conn);
//        if((memoryUsage/1024) < 100){
//            SendMail.sendMail("xxxxx@qq.com","服务器内存剩余空间不足，请注意查看", "服务器提醒");
//        }
                System.out.println(memoryUsage);
            }
        };
        timer.schedule(task, 1000, 1000 * 10);

    }

    @Autowired
    private EmailService emailService;
    /**
     * 定时监控CPU和内存使用率（异常则发送邮件）
     */
    //@Scheduled(cron = "0 */1 * * * ?")
    public void monitorCPU() {
        Connection conn = RemoteCommandUtil.login("","","");
        log.info("定时监控CPU和内存使用率任务开启");
        float cpuUsage = cpuUsage(conn);
        log.info("cpuUsage:" + cpuUsage);
        if (cpuUsage > 10.0) {
            emailService.sendSimpleEmail(new String[]{"1156517308@qq.com"}, "服务器cpu使用率过高，请注意查看", "测试内容");
        }
        long memoryUsage = memoryUsage(conn);
        log.info("memoryUsage:" + memoryUsage);
        if ((memoryUsage / 1024) < 100) {
            emailService.sendSimpleEmail(new String[]{"1156517308@qq.com"}, "服务器内存剩余空间不足，请注意查看", "测试内容");
        }
    }


}
