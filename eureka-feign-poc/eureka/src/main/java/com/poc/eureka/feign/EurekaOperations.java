package com.poc.eureka.feign;

import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.beans.factory.annotation.Value;

public class EurekaOperations {

    private String serverPort;
    private String ipAddress;

    @Value("${host.name}")
    private String hostName;

    @Value("${service.name}")
    private String serviceName;

    private  EurekaHttpMethods eurekaHttpMethodsService = Feign.builder().decoder(new GsonDecoder()).target(EurekaHttpMethods.class, "http://localhost:8080/eureka/v2/apps");

    public void register(){
        eurekaHttpMethodsService.registry("{\n" +
                "    \"instance\": {\n" +
                "        \"hostName\": \""+ hostName +"\",\n" +
                "        \"app\": \""+ serviceName +"\",\n" +
                "        \"vipAddress\": \"com.teste\",\n" +
                "        \"secureVipAddress\": \"com.teste\",\n" +
                "        \"ipAddr\": \""+ipAddress+"\",\n" +
                "        \"status\": \"STARTING\",\n" +
                "        \"port\": {\"$\": \"" + serverPort + "\", \"@enabled\": \"true\"},\n" +
                "        \"securePort\": {\"$\": \"8443\", \"@enabled\": \"true\"},\n" +
                "        \"healthCheckUrl\": \"http://localhost:"+serverPort+"/healthcheck\",\n" +
                "        \"statusPageUrl\": \"http://localhost:"+serverPort+"/status\",\n" +
                "        \"homePageUrl\": \"http://localhost:"+serverPort+"\",\n" +
                "        \"dataCenterInfo\": {\n" +
                "            \"@class\": \"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo\",\n" +
                "            \"name\": \"MyOwn\"\n" +
                "        }\n" +
                "    }\n" +
                "}", serviceName);
        eurekaHttpMethodsService.updateToUP(serviceName, hostName);
    }

    public void delete(){
        eurekaHttpMethodsService.delete(serviceName, hostName);
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
