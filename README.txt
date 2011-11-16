Websolr sample application in Java
==================================

This is a Book search Solr sample application written using Play framework
using Apache Solrj. This sample application can be easily deployed on
CloudBees RUN@cloud platform.
  

Run and Deploy on CloudBees RUN@cloud platform
==============================================

 Pre-requisites
 --------------
 1. Play framework 1.2.3
 2. JDK 6
 3. CloudBees account, free signup at https://grandcentral.cloudbees.com/account/signup
 4. Websolr index
    4.1 Signup to websolr subscription https://grandcentral.cloudbees.com/subscriptions/websolr
    4.2 Create index resource, https://grandcentral.beescloud.com/resources?service_name=websolr

 Test in local environment
 -------------------------
 1. git clone .....
 2. cd websolr-play-sample
 3. play dependencies
 4. Configuration
 
 $ cp conf/application.conf_template conf/application.conf
 
     #Websolr server
     books.websolr.url=...

    Enter websolr server URL. Click on 'Show config' on    
    https://grandcentral.beescloud.com/resources?service_name=websolr. 

     #CloudBees specific info - needed to deploy at RUN@cloud
     bees.api.key=...
     bees.api.secret=...
     bees.api.domain=...
     bees.api.name=...

    Get CloudBees keys from https://grandcentral.cloudbees.com/user/keys. Also 
    enter the application name and your account name/domain.
 
 5. play run => then logon to http://localhost:9000
 6. Add some books then search by Title

 Deploy at CloudBees
 -------------------
 
$ play bees:app:deploy
 
 For more info...
 ----------------
 Websolr: https://websolr.com/
 Apache Solr: http://wiki.apache.org/solr/Solrj
 Play: http://www.playframework.org/modules/cloudbees-0.2.2/home
