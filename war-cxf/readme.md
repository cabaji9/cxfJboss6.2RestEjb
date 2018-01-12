## APACHE CXF REST FOR JBOSS 6.2 WITHOUT SPRING

This example configures a rest with call to ejb on jboss 6.2

It uses
 
Apache cxf 3.1.2 
And to use json - jackson-jaxb 2.9.3
Swagger 1.5.4 for rest documentation.
Bean validation 1.1 to validate the beans on the rest.




## COMPILE

mvn clean install

## DEPLOY

Copy war to deployments folder on jboss.

```
 cp target/cxf-web-1.0-SNAPSHOT.war [JBOSS-PATH]/standalone/deployments
```


## 

