FROM openjdk:20-jdk-slim-buster
EXPOSE 8070
COPY target/spring-template-0.0.2.war spring-template.war
ENTRYPOINT ["java","-jar","/spring-template.war"]


#FROM tomcat:10.0.26-jre8-temurin-jammy
#EXPOSE 8080
#COPY target/spring-template-0.0.2.war /usr/local/tomcat/webapps/tmp.war
#ENTRYPOINT ["/usr/local/tomcat/bin/catalina.sh", "run"]