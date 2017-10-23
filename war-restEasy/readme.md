##APACHE CXF REST FOR JBOSS 6.2 WITHOUT SPRING

This example configures a rest with call to ejb on jboss 6.2

It uses
 
Apache cxf 2.6 other versions are not working due to classpath errors.
And to use json - jackson-jaxb



##COMPILE

mvn clean install

##DEPLOY

Copy war to deployments folder on jboss.

```
 cp target/cxf-web-1.0-SNAPSHOT.war [JBOSS-PATH]/standalone/deployments
```


