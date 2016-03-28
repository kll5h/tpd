-----------------------Steps of running daemon and web in command---------
1. cd to tilepay root folder
   execute "gradle clean build -x test"
   
2. cd to tilepay/daemon folder
   execute "gradle clean build -x test"

3. in tilepay/daemon folder
   java -Dspring.profiles.active=testnet -jar build/libs/daemon.jar
   
4. in tilepay root folder 
   java -Dspring.profiles.active=testnet,local-testnet -jar web/build/libs/tilepay-0.0.4.jar
--------------------RELEASE-----------------------------------------------
requirements: JDK 8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
create release: gradle createRelease
tilepay.zip will be created
---------------------------WALLET-----------------------------------------
build: gradle clean build -x test
run in testnet with connection to local|remote testnet daemon: java -Dspring.profiles.active=testnet,[local|remote]-testnet -jar web/build/libs/tilepay-0.0.4.jar
TODO: disable our daemon mainnet functionality for now. run in mainnet: java -Dspring.profiles.active=mainnet -jar web/build/libs/tilepay-[VERSION].jar
open in the browser: http://localhost:8888/

DB, log, wallet files can be found at:
WINDOWS: ..AppData/Roaming/tilepay
LINUX: user.home/tilepay
MAC: user.home/Library/Application Support/tilepay
--------------------------Version-----------------------------------------
[VERSION] number has to be specified manually
Version number can be updated from C:\Projects\tilepay\web\src\main\resources  version.properties
--------------------------Problems----------------------------------------
Problem: org.flywaydb.core.api.FlywayException: Validate failed. Found differences between applied migrations and...
Solution: remove db
-----------------------------------Daemon---------------------------------
cd daemon
gradle clean build -x test
run in testnet: java -Dspring.profiles.active=testnet -jar build/libs/daemon.jar

remote REST Json API: http://188.166.56.107:8081/getBalances/my7TMYXdUzHMyeJXZUuS7563QzQxfmZRLg
remote db testnet: jdbc:h2:tcp://188.166.56.107:9123/~/protocol-testnet

local REST Json API:
    http://localhost:8081/getBalances/my7TMYXdUzHMyeJXZUuS7563QzQxfmZRLg
    http://localhost:8081/running-info
local db testnet: jdbc:h2:tcp://localhost:9123/~/protocol-testnet

db file testnet location: userHome/protocol-testnet.mv.db
wallet file testnet location: userHome/daemon-testnet.wallet

https://testnet.counterwallet.io
testnet fee address: mvQRQAhN2KJia8PgH1xBdmeX3FgTRTdCST
existence normal someone dude bridge cloud meant fan melt nice stole surface
-----------------------------------IoTClient------------------------------
IoTClient project has a separate build file. Go to tilepay project ->IoTClient
gradle clean build
get the iotclient-0.1.0.jar file from  .../build/libs/ and run it on the raspberry 
java -jar iotclient-0.1.0.jar

in the registration form in the application you need to specify the IP address of the raspberry
-----------------------------------For developers-------------------------
Before commit run:
gradle clean test -x acceptance-tests:test

jenkins:
http://178.62.240.234:8080/jenkins/job/snapshot/
  
   